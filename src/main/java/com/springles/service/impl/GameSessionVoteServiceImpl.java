package com.springles.service.impl;

import com.springles.config.TimeConfig;
import com.springles.domain.constants.GamePhase;
import com.springles.domain.constants.GameRole;
import com.springles.domain.dto.game.GameSessionVoteRequest;
import com.springles.domain.entity.GameSession;
import com.springles.domain.entity.Player;
import com.springles.exception.CustomException;
import com.springles.exception.constants.ErrorCode;
import com.springles.game.GameSessionManager;
import com.springles.game.task.VoteFinTimerTask;
import com.springles.redisPubSub.Publisher;
import com.springles.repository.PlayerRedisRepository;
import com.springles.repository.VoteRepository;
import com.springles.service.GameSessionVoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class GameSessionVoteServiceImpl implements GameSessionVoteService {

    private final VoteRepository voteRepository;
    private final GameSessionManager gameSessionManager;
    private final PlayerRedisRepository playerRedisRepository;


    @Override
    public void startVote(Long roomId, int phaseCount, GamePhase phase, LocalDateTime time, Map<Long, GameRole> players) {
        log.info("Room {} start Vote for {}", roomId, phase);
        voteRepository.startVote(roomId, phaseCount, phase, players);
        Timer timer = new Timer();
        VoteFinTimerTask task = new VoteFinTimerTask(this);
        task.setRoomId(roomId);
        task.setPhaseCount(phaseCount);
        task.setPhase(phase);
        timer.schedule(task, TimeConfig.convertToDate(time));
    }

    @Override
    public void endVote(Long roomId, int phaseCount, GamePhase phase) {
        // 이미 끝났다면
        if(voteRepository.isEnd(roomId, phaseCount)) {
            return;
        }
        else {
            Map<Long, Long> vote = voteRepository.getVoteResult(roomId);
            log.info("Room {} end Vote for {}", roomId, phase);
            voteRepository.endVote(roomId, phase);
            publishRedis(roomId, vote);
        }
    }

    @Override
    public Map<Long, Long> vote(Long roomId, Long playerId, GameSessionVoteRequest request) {
        // 유효한 투표가 아니라면 예외 발생
        if(!voteRepository.isValid(playerId, request.getPhase())) {
            throw new CustomException(ErrorCode.VOTE_NOT_VALID);
        }
        log.info("Room {} Player {} Voted At {}", roomId, playerId, request.getPhase());
        return voteRepository.vote(roomId, playerId, request.getVote());
    }

    @Override
    public Map<Long, Long> nightVote(Long roomId, Long playerId, GameSessionVoteRequest request, GameRole role) {
        // 유효한 투표가 아니라면 예외 발생
        if(!voteRepository.isValid(playerId, request.getPhase())) {
            throw new CustomException(ErrorCode.VOTE_NOT_VALID);
        }
        return voteRepository.nightVote(roomId, playerId, request.getVote(), role);

    }

    @Override
    public Map<Long, Boolean> confirmVote(Long roomId, Long playerId, GameSessionVoteRequest request) {
        // 유효한 투표가 아니라면 예외 발생
        if(!voteRepository.isValid(playerId, request.getPhase())) {
            throw new CustomException(ErrorCode.VOTE_NOT_VALID);
        }
        return voteRepository.confirmVote(roomId, playerId);
    }

    @Override
    public Map<Long, Boolean> getConfirm(Long roomId, Long playerId, GameSessionVoteRequest request) {
        GameSession gameSession = gameSessionManager.findGameByRoomId(roomId);
        Player player = playerRedisRepository.findById(playerId).get();

        // 이미 종료된 투표라면 에러 발생
        if (voteRepository.isEnd(roomId, gameSession.getPhaseCount()))
            throw new CustomException(ErrorCode.ENDED_VOTE);

        // 밤이 아니거나 사용자가 살아 있지 않다면
        if (gameSession.getGamePhase() != GamePhase.NIGHT_VOTE || !player.isAlive()) {
            return voteRepository.getConfirm(roomId, playerId);
        }
        else {
            return voteRepository.getNightConfirm(roomId, player.getRole());
        }
    }

    @Override
    public Map<Long, Boolean> getNightConfirm(Long roomId, Long playerId, GameSessionVoteRequest request, GameRole role) {
        // 유효한 투표가 아니라면 예외 발생
        if(!voteRepository.isValid(playerId, request.getPhase())) {
            throw new CustomException(ErrorCode.VOTE_NOT_VALID);
        }
        return voteRepository.getNightConfirm(roomId, role);

    }

    @Override
    public Map<Long, Long> getVoteResult(Long roomId, GameSessionVoteRequest request) {
        return voteRepository.getVoteResult(roomId);
    }

    private void publishRedis(Long roomId, Map<Long, Long> vote) {

    }

//    private List<String> getSuspiciousList(GameSession gameSession, Map<Long, Long> voteResult) {
//        List<String> suspiciousList = new ArrayList<>();
//
//        // <투표받은 사람, 투표수>로 정리
//        Map<Long, Integer> voteNum = new HashMap<Long, Integer>();
//        int voteCnt = 0; // 총 투표 수
//        for (Long vote : voteResult.values()) {
//            // 투표한 결과가 없을 때
//            if (vote == null) {
//                continue;;
//            }
//            voteCnt++;
//            voteNum.put(vote, voteNum.getOrDefault(vote, 0) + 1); // 투표 수 업데이트
//        }
//
//        // 의심되는 사람 찾기
//        int alivePlayer = gameSession.getAliveCivilian() +
//                gameSession.getAliveDoctor() +
//                gameSession.getAlivePolice() +
//                gameSession.getAliveMafia();
//        if (voteCnt > (alivePlayer - 1) / 2) {
//            List<Long> suspects = new ArrayList<>(voteNum.keySet());
//            // 투표 수 오름차순
//            Collections.sort(suspects, (o1, o2) -> voteNum.get(o2).compareTo(voteNum.get(o1)));
//
//            List<Player> players = playerRedisRepository.findByRoomId(gameSession.getRoomId()); // 게임에 참여 중인 player 정보
//            Map<Long, Player> playerMap = players.stream().collect(Collectors.toMap(Player::getMemberId, p -> p));
//
//            int voteMax = voteNum.get(suspects.get(0)); // 최다 득표수
//            for (Long suspect : suspects) {
//
//            }
//        }
//    }
    private Long getEliminationPlayer(GameSession gameSession, Map<Long, Long> voteResult) {
        // 최다 득표수 플레이어를 죽이는 메소드
        Long deadPlayerId = null;
        Map<Long, Integer> voteNum = new HashMap<>();
        int voteCnt = 0;
        for (Long vote : voteResult.values()) {
            if (vote == null) { // 투표 결과가 없을 때
                continue;
            }
            voteCnt++;
            voteNum.put(vote, voteNum.getOrDefault(vote, 0) + 1);
        }

        // 살아 있는 플레이어 수
        int alivePlayer = gameSession.getAliveCivilian() +
                gameSession.getAliveDoctor() +
                gameSession.getAlivePolice() +
                gameSession.getAliveMafia();

        if (voteCnt > (alivePlayer - 1) / 2) { // 과반수 이상이 투표에 참여했을 때

            // 최다 득표 수 구하기
            Integer max = voteNum
                    .entrySet() // 맵의 각 엔트리(키와 값의 쌍)를 스트림으로 변환
                    .stream()// 맵의 엔트리 스트림을 생성
                    .max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1)
                    // 스트림의 요소 중에서 최대 값
                    // 만약 entry1의 값이 entry2의 값보다 크면 1을 반환하고, 그렇지 않으면 -1을 반환
                    .get()// .max(...) 메서드로 찾은 최대 값을 가져옴 이 최대 값은 엔트리 객체
                    .getValue(); // 엔트리 객체의 값을 가져옴

            // 최다 득표한 Player List
            List<Long> deadList = voteNum.entrySet().stream()
                    .filter(entry -> entry.getValue() == max)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());

            // 한 명일 경우
            if (deadList.size() == 1) {
                deadPlayerId = deadList.get(0);
            }
        }
        return deadPlayerId;
    }

    private Map<GameRole, Long> getNightVoteResult(GameSession gameSession, Map<Long, Long> voteResult) {
        List<Player> players = playerRedisRepository.findByRoomId(gameSession.getRoomId());
        // <memberId, player>로 만든 맵
        Map<Long, Player> playerMap = players.stream().collect(Collectors.toMap(Player::getMemberId, p -> p));

        // 마피아가 아닌 직업들 결과에 담기
        // <어떤 직업, 누구한테 투표했는지>
        Map<GameRole, Long> result = voteResult.entrySet().stream()
                .filter(e -> playerMap.get(e.getKey()).getRole() != GameRole.MAFIA) // 직업이 마피아가 아니고
                .filter(e -> e.getValue() != null) // 투표값이 존재하고
                .collect(Collectors.toMap(e -> playerMap.get(e.getKey()).getRole(), e -> e.getValue()));

        // 마피아의 투표만 따로 저장하기
        // <투표받은 사람 <투표한사람>>
        Map<Long, List<Long>> mafiaVote = voteResult.keySet().stream()
                .filter(key -> playerMap.get(key).getRole() == GameRole.MAFIA) // 마피아일 때
                .filter(key -> voteResult.get(key) != null) // 투표가 존재할 때
                .collect(Collectors.groupingBy(key -> voteResult.get(key)));

        // 마피아의 투표였을 때
        if (mafiaVote.size() > 0) {
            // 최다 득표 수
            Integer max = mafiaVote
                    .entrySet() // 맵의 각 엔트리(키와 값의 쌍)를 스트림으로 변환
                    .stream()// 맵의 엔트리 스트림을 생성
                    .max((entry1, entry2) -> entry1.getValue().size() > entry2.getValue().size() ? 1 : -1)
                    // 스트림의 요소 중에서 최대 값
                    // 만약 entry1의 값이 entry2의 값보다 크면 1을 반환하고, 그렇지 않으면 -1을 반환
                    .get()// .max(...) 메서드로 찾은 최대 값을 가져옴 이 최대 값은 엔트리 객체
                    .getValue()
                    .size(); // 엔트리 객체의 값을 가져옴
            // 최다 득표인 Player List
            List<Long> deadList = mafiaVote.entrySet().stream().filter(entry -> entry.getValue().size() == max) // 최다 득표수랑 같은 애의
                    .map(Map.Entry::getKey).collect(Collectors.toList());

            // 한 명일 경우
            if (deadList.size() == 1) {
                result.put(GameRole.MAFIA, deadList.get(0));
            }
        }
        return result;
    }
}