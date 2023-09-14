# 🧾 ChatFia

> 채팅(Chatting) + 마피아 게임(Mafia)의 합성어, 실시간 채팅으로 마피아 게임을 즐길 수 있는 웹 서비스

## 📎 서비스 이용
[웹 사이트]()


## 🎇 프로젝트 소개

#### 📅 진행 기간 

- 2023.08.09 ~ 2023.09.14 (5주)

#### 🎯 기획 의도
인터넷에서 함께 즐길 수 있는 웹 게임에 대한 수요는 꾸준히 증가하고 있습니다. 또한 어몽어스, 구스구스덕, 마피아 42 등 마피아 게임 콘텐츠에 대한 인기는 계속 해서 늘어나고 있습니다. 
유일하게 웹에서 할 수 있었던 마피아 챗 게임 서비스는 2023년에 종료가 되었고, 타 마피아 게임 콘텐츠도 모바일 전용이거나 게임 설치를 해야 하는 불편함이 있습니다.
챗피아(ChatFia)는 웹 접속만으로도 사용자들과 실시간 채팅으로 게임을 즐길 수 있다는 큰 차별성이 있습니다. 이러한 흐름에 따라 사용자들에게 쉽게 즐거움을 선사하는 서비스를 제공하고자 기획 되었습니다.

#### ⚙️ 주요 기능

| **기능**            | **설명**                                                     |
| ------------------- | ------------------------------------------------------------ |
| **채팅방 목록 검색/조회**     | 게임 진행중/대기중에 대한 모든 채팅방을 검색하고, 조회할 수 있습니다. |
| **채팅방 생성** |  제목, 인원수, 비밀번호를 설정하여 채팅방을 생성할 수 있습니다. |
| **채팅방 공유**       |  카카오톡 공유하기, URL 복사를 통해 채팅방 공유가 가능합니다. |
| **채팅**  | 사회자 역할을 하는 admin 기준으로 게임을 진행할 수 있습니다.  |
| **게임 기록 조회**       | 게임기록에 대한 횟수/승률을 한 눈에 확인할 수 있습니다. |
| **프로필 설정/변경**     | 프로필 이미지, 닉네임을 설정하고 변경할 수 있습니다. |



## ⛑ 아키텍처

![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/092d462b-b820-45d5-a497-8c7c7146574b/26673efc-4c07-4fdb-aa05-db1d04ab6550/Untitled.png)


## 🛠️ 기술 스택

#### 💻 프론트엔드

#### 🛢 백엔드

#### 📡 인프라

## 🗃 ERD

## 🐱‍💻 팀 소개
| 팀원     | 담당                      | GitHub                                           |
| -------- | ------------------------- | ------------------------------------------------ |
| 이우영 👑 |    | [@ujumom](https://github.com/ujumom)  |
| 이나연   |           |            |
| 이희건   |           |             |
| 양아름   |           |            |
| 정수현   |           |           |
| 조희수   |          |      |

## 🐱‍💻 이렇게 함께 일했어요.
<details> <summary>Git</summary> <div markdown="1">

- Git-flow를 사용했어요.
![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/092d462b-b820-45d5-a497-8c7c7146574b/809fd08b-baf4-4d62-a786-39cded4ad153/Untitled.png)

**branch 생성 규칙**
- 모든 기능은 develop 브랜치에서 새 브랜치를 생성하여 작업
- [기능]/[이슈 번호] 로 브랜치 생성
- issue template 작성 - 브랜치 생성 - 작업 완료 - pull request templates 작성 - develop merge - test 

**사용한 issue Templates**
![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/a5626901-0f88-4e72-8152-d545876467f4/Untitled.png)

**사용한 Pull Request Templates**
![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/f83c771e-86fb-4879-8de7-639caa6b83ec/Untitled.png)

**commit 생성 규칙**
#✨ feat: <새로운 기능>
#🚚 chore: 코드 의미에 영향을 주지 않는 변경사항 (형식 지정, 세미콜론 누락, gitignore 등)
#👷 ci: CI, 자동화 기능
#📝 docs: 문서의 추가, 수정, 삭제
#♻️ refactor: 코드 리팩토링
#🧪 test: 테스트 추가, 수정, 삭제 (비즈니스 로직에 변경 없음)
#💄 style: 코드 스타일 혹은 포맷 등에 관한 커밋
#⏪ revert: 깃 revert
#⚡️ perf: 퍼포먼스 상향
#🐛 fix: 버그

</div> </details>



## 💡 프로젝트 구조
<details>
    <summary>자세히</summary>

├─java
│  └─com
│      └─springles
│          │  SpringlesApplication.java
│          │
│          ├─config
│          │      LoginFailureHandler.java
│          │      LoginSuccessHandler.java
│          │      MailConfig.java
│          │      QueryDslConfig.java
│          │      RedisConfig.java
│          │      RedisInitializer.java
│          │      SwaggerConfig.java
│          │      TimeConfig.java
│          │      WebSecurityConfig.java
│          │      WebSocketStompConfig.java
│          │
│          ├─controller
│          │  ├─api
│          │  │      ChatRoomController.java
│          │  │      CookieController.java
│          │  │      MemberController.java
│          │  │
│          │  ├─message
│          │  │      MessageController.java
│          │  │      VoteController.java
│          │  │
│          │  └─ui
│          │          ChatRoomUiController.java
│          │          ChatUiController.java
│          │          MemberUiController.java
│          │
│          ├─domain
│          │  ├─base
│          │  │      .keep
│          │  │
│          │  ├─constants
│          │  │      BaseEnumCode.java
│          │  │      ChatRoomCode.java
│          │  │      GamePhase.java
│          │  │      GameRole.java
│          │  │      GameRoleNum.java
│          │  │      Level.java
│          │  │      ProfileImg.java
│          │  │      ResponseCode.java
│          │  │
│          │  ├─dto
│          │  │  ├─chatroom
│          │  │  │      ChatRoomCreateResponseDto.java
│          │  │  │      ChatRoomReqDTO.java
│          │  │  │      ChatRoomResponseDto.java
│          │  │  │      ChatRoomUpdateReqDto.java
│          │  │  │
│          │  │  ├─cookie
│          │  │  │      CookieSetRequest.java
│          │  │  │
│          │  │  ├─member
│          │  │  │      MemberCreateRequest.java
│          │  │  │      MemberDeleteRequest.java
│          │  │  │      MemberInfoResponse.java
│          │  │  │      MemberLoginRequest.java
│          │  │  │      MemberLoginResponse.java
│          │  │  │      MemberProfileCreateRequest.java
│          │  │  │      MemberProfileRead.java
│          │  │  │      MemberProfileResponse.java
│          │  │  │      MemberProfileUpdateRequest.java
│          │  │  │      MemberRecordResponse.java
│          │  │  │      MemberSimpleProfileResponse.java
│          │  │  │      MemberUpdateRequest.java
│          │  │  │      MemberVertifIdRequest.java
│          │  │  │      MemberVertifPwRequest.java
│          │  │  │      PlayerInfoRequest.java
│          │  │  │      PlayerInfoResponse.java
│          │  │  │
│          │  │  ├─message
│          │  │  │      DayDiscussionMessage.java
│          │  │  │      DayEliminationMessage.java
│          │  │  │      NightVoteMessage.java
│          │  │  │      RoleExplainMessage.java
│          │  │  │
│          │  │  ├─response
│          │  │  │      PlayerStatus.java
│          │  │  │      ResResult.java
│          │  │  │
│          │  │  └─vote
│          │  │          ConfirmResultResponseDto.java
│          │  │          GameSessionVoteRequestDto.java
│          │  │          VoteResultResponseDto.java
│          │  │
│          │  └─entity
│          │          BlackListToken.java
│          │          ChatRoom.java
│          │          GameRecord.java
│          │          GameSession.java
│          │          Member.java
│          │          MemberGameInfo.java
│          │          MemberRecord.java
│          │          Player.java
│          │          RefreshToken.java
│          │          Vote.java
│          │          VoteInfo.java
│          │
│          ├─exception
│          │  │  CustomException.java
│          │  │  ErrorResponse.java
│          │  │  GlobalExceptionHandler.java
│          │  │
│          │  └─constants
│          │          ErrorCode.java
│          │
│          ├─game
│          │  │  ChatMessage.java
│          │  │  DayDiscussionManager.java
│          │  │  DayEliminationManager.java
│          │  │  DayToNightManager.java
│          │  │  GameSessionManager.java
│          │  │  MessageManager.java
│          │  │  NightVoteManager.java
│          │  │  RoleManager.java
│          │  │
│          │  └─task
│          │          VoteFinTimerTask.java
│          │
│          ├─jwt
│          │      JwtExceptionFilter.java
│          │      JwtTokenFilter.java
│          │      JwtTokenUtils.java
│          │
│          ├─repository
│          │  │  BlackListTokenRedisRepository.java
│          │  │  ChatRoomJpaRepository.java
│          │  │  GameRecordJpaRepository.java
│          │  │  GameSessionRedisRepository.java
│          │  │  MemberGameInfoJpaRepository.java
│          │  │  MemberJpaRepository.java
│          │  │  MemberRecordJpaRepository.java
│          │  │  PlayerRedisRepository.java
│          │  │  RefreshTokenRedisRepository.java
│          │  │  VoteRedisRepository.java
│          │  │  VoteRepository.java
│          │  │
│          │  ├─custom
│          │  │      ChatRoomJpaRepositoryCustom.java
│          │  │      MemberJpaRepositoryCustom.java
│          │  │
│          │  ├─impl
│          │  │      ChatRoomJpaRepositoryImpl.java
│          │  │      MemberJpaRepositoryImpl.java
│          │  │
│          │  └─support
│          │          Querydsl4RepositorySupport.java
│          │
│          ├─service
│          │  │  ChatRoomService.java
│          │  │  CookieService.java
│          │  │  GameSessionVoteService.java
│          │  │  MemberService.java
│          │  │
│          │  └─impl
│          │          ChatRoomServiceImpl.java
│          │          CookieServiceImpl.java
│          │          GameSessionVoteServiceImpl.java
│          │          MemberServiceImpl.java
│          │
│          └─valid
│                  ValidationGroups.java
│                  ValidationSequence.java
│
└─resources
│  application-dev.yml
│  application-prod.yml
│  application-redis.yml
│  application.yml
│  data.sql
│
├─static
│  ├─css
│  │      basic.css
│  │
│  ├─images
│  │  │  icon_chatroom_info.png
│  │  │  icon_lv_info_btn.png
│  │  │  lock.png
│  │  │  logo.png
│  │  │  profile.png
│  │  │  profile_01.jpg
│  │  │  profile_02.jpg
│  │  │  profile_03.jpg
│  │  │  profile_04.jpg
│  │  │  profile_05.jpg
│  │  │  profile_06.jpg
│  │  │  search.png
│  │  │
│  │  └─level
│  │          ASSOCIATE.png
│  │          BEGINNER.png
│  │          BOSS.png
│  │          CAPTAIN.png
│  │          SOLDIER.png
│  │          UNDERBOSS.png
│  │
│  └─js
│          stomp.js
│
└─templates
│  chat-lobby.html
│  chat-room.html
│  rooms.html
│
├─fragments
│      footer.html
│      header.html
│
├─home
│      add.html
│      index.html
│
├─layouts
│      basic.html
│
└─member
      login.html
      member-info.html
      member-sign-out.html
      my-page.html
      profile-change.html
      profile-settings.html
      sign-up.html
      vertification-id.html

</details>




<details> <summary>개발 환경</summary> <div markdown="1">

## **Development Environment**


</div> </details>



