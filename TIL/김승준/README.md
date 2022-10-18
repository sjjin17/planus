# 10.11.

## 아이디어 정하기

## 하고 싶은 분야

클라우드

- 빅데이터? 데이터 연산?
- AI학습?

금융권(블록체인,AI,빅데이터,클라우드,메타버스)

- 오픈뱅킹api 사용하기? [https://www.openbanking.or.kr/apt/content/openapi](https://www.openbanking.or.kr/apt/content/openapi)

## 사용하고 싶은 기술스택

- MongoDB (NoSQL) - 나경
- 무중단배포 - 나경
- 쿠버네티스 (무중단배포?) - 승준
- 스프링 시큐리티 (jwt or session 추후논의?) - 지건

## 하기 싫은

- 로그인(할 사람 있으면 노상관) - 나경
- 게임 - 나경
- OpenCV - 나경
- 빅데이터(내가 안하면 상관없음) - 승준
- IoT - 희경
- 모바일App(디자인 내가 안하면 상관없음) - 나경
- 아토믹디자인 - 나경
- 메타버스 - 나경
- 3D - 나경

## 하고 싶은

- 배포 - 승준
- 시큐리티 - 지건

## 신기한거

- [https://github.com/features/copilot](https://github.com/features/copilot)
- [https://gflix.kr/ai가-그림-그려주는-사이트-best8/](https://gflix.kr/ai%EA%B0%80-%EA%B7%B8%EB%A6%BC-%EA%B7%B8%EB%A0%A4%EC%A3%BC%EB%8A%94-%EC%82%AC%EC%9D%B4%ED%8A%B8-best8/)
- [https://gflix.kr/ai가-소설-써주는-사이트-best-4/](https://gflix.kr/ai%ea%b0%80-%ec%86%8c%ec%84%a4-%ec%8d%a8%ec%a3%bc%eb%8a%94-%ec%82%ac%ec%9d%b4%ed%8a%b8-best-4/)

## 주제

- 사용자가 사용한 결과물이 실제로 나오거나 서비스 참여를 재밌게 할 수 있는 or 시연이 눈으로 보이는 서비스 - 나경 (아니어도 상관없습니다 ㅋㅋㅋ)
  - 예시) 위 3개, 모여봐요웹캠으로, 오늘 라이브 (블록체인+AI 본인인증), RideUs? 영어이름 추천, 유튜버추천 등등등
- 공동구매 플랫폼 - 희경

---

# 10.12.

| 승준          | 지건          | 희경    | 나경         | 세진             | 다은             |
| ------------- | ------------- | ------- | ------------ | ---------------- | ---------------- |
| 물품 빌려쓰기 | 공동 구매     | 캠핑장  | ai그림그리기 | 통합온라인서점   | ai 그림그려주기  |
| ai 그림       | 물품 빌려쓰기 | 음성api | 회비관리     | 유기 동물        | 공동 구매 플랫폼 |
|               |               |         |              | 공동 구매 플랫폼 |                  |

물품빌려쓰기 - 2

ai그림 - 3

공동구매 - 3

캠핌장 - 1

음성 - 1

회비관리 - 1

서점 - 1

유기동물 - 1

1. 물품 빌려쓰기

- 핵심기능
  - 당근마켓처럼 물건/돈/시간 등을 입력해 등록함 (빌릴 사람, 빌려줄 사람 둘 다 가능)
  - 채팅
  - 마이페이지 or 상점페이지
  - 지도 api (거래 위치 마커로 띄워주기…?)
- 부기능
  - 커뮤니티
  - 리뷰
- 고려해야할것
  - 같은 지역 사람끼리 매칭해야함
  - 반납 안하는 것 어떻게 관리할지
  - 파손문제?
  - 채팅 실시간 or 일반 댓글처럼? (구현 방법에따라 소켓서버 필요)

2. ai 그림그려주기

- 핵심기능
  - 사진업로드or사진찍기
  - 그림그려주기(이미지변환) →그림체 선택 등의 설정..?을 뭐라고하지prompt?
- 부기능
  - 커뮤니티
  - sns공유하기
  - 마이페이지 (사진보관)
- 고려해야할것
  - 단순히 기존에 있는 모델의 사용성을 편리하게 하는 것이 아닌 차별성이 필요할 듯
  - 이미지 서버(ex. s3) 따로 둘것인지 (→ s3 쓸 경우 업로드 잘 안되는 문제 발생 가능성….)

3. 공동구매 플랫폼

- 핵심기능
  - 공동구매 주최자가 게시글 작성(품목, 디자인, 가격 등)
  - 구매할 사람이 신청, 인원이 모집되면 모집 완료
  - 주최자는 구매 진행 상황, 배부 일정 등을 공유
  - ~~계좌를 주최자 통장이 아닌 회사 계좌로 하고~~
- 부기능
  - 공구 물품 제작 업체 계약 후 플랫폼 입점
  - 자체 폼 만들어서 명단 관리 쉽게
- 고려해야할것
  - 일정 안지키면 어떤 패널티?
  - 거래내역 진위여부
  - 먹튀?

| 승준 | 지건 | 희경      | 나경 | 세진          | 다은            |
| ---- | ---- | --------- | ---- | ------------- | --------------- |
| 그림 | 물품 | 물품-그림 | 그림 | 물품 빌려쓰기 | ai 그림그려주기 |
| 그림 | 그림 | 그림      | 그림 | 그림          | 그림            |

---

# 10.13.

## 기능 구체화

- 핵심기능
  - 사진업로드 (인물 사진만)
  - 사진 업로드하면 사람 감지해서 누끼따고 사람 감지안되면 알림메시지같은거 띄워주기
  - 배경 제거된 사진을 그림으로 변환 (사람+배경 따로)
  - 자동 prompt - 넣고싶다
    - 안되면 prompt, 그림체 추천
  - 그림체 선택 - 넣고싶다
  - 그림으로 변환(사람+배경 합치기)
  - 그림 저장
  - 그림 sns 공유하기
- 부기능
  - 마이페이지 (회원전용)
    - 다이어리
      - 감정 같이 기입
      - 문장추천 같이 기입
    - 앨범
      - 폴라로이드 느낌 - 날짜,한줄메모
  - 닮은 캐릭터 찾기
  - 커뮤니티 (회원전용)
    - 변환된 그림
    - 선택한 프롬프트
    - 선택한 그림체
    - 해당 프롬프트로 변환할 수 있는 바로가기?
    - 전시회 느낌
  - 사진촬영

## stable diffusion 관련 링크

- [(github)webui](https://github.com/AUTOMATIC1111/stable-diffusion-webui)

- [가이드](https://www.reddit.com/r/StableDiffusion/wiki/guide/)

- [EC2에서 돌리기](https://medium.com/@meadowrun/how-to-run-stable-diffusion-on-ec2-e447333d820)

- [도커라이징 (FastAPI, tensordock )](https://towardsdatascience.com/launch-a-web-api-for-stable-diffusion-under-45-seconds-bbd88cfe41d8)

- [(github)rest-api(mac, homebrew, Nodejs, python)](https://github.com/yuanqing/stable-diffusion-rest-api)

- [(github)bentoml로 stable-diffusion 배포](https://github.com/bentoml/stable-diffusion-bentoml)

- [cog 이용한 api (apachi license)](https://replicate.com/stability-ai/stable-diffusion)

---

# 10.14.

# 공동 여행 스케줄러

### Web-PC

### 회원전용

## 컨셉

여러명이 동시에 여행 계획을 짤 수 있게 해주는 서비스

대중교통, 지도 API를 활용해 해당 여행지 관련 정보를 제공해 여행 계획 수립에 도움을 준다

## 핵심기능

- 회원가입
- 로그인, 로그아웃

1. (방장) 여행 제목, 여행 장소(여러개선택), 일정 선택
2. 링크 공유
3. 링크로 들어가면 로그인
4. 일정에 추가하시겠습니까?→예 누르면 공유된 일정 목록에 추가되고 참여 가능

---

<동시작업>

버킷리스트

1. 이름으로 장소 검색
2. 장소 추천 (많이 선택한 장소 + 우리가 기본으로 넣어놓은거 )
3. 검색 목록에서 클릭하면 장소 등록
4. 버킷리스트 목록에 추가
5. 지도에 마커 표시
6. 버킷리스트에서 장소 삭제
7. (방장)버킷리스트에서 관리자가 장소 선택해서 일정에 등록
8. (방장)등록할때 시간 설정
9. (방장)일정 내에서 순서 변경 가능
10. (방장)일정에서 삭제한 장소는 버킷리스트로 이동
11. (방장)대중교통 or 자가용(택시) 경로, 소요시간 제공
12. (방장)일정 생성(몇시~몇시+장소, 중간에 이동수단+이동소요시간)
13. 계획 임시저장
14. 완료(저장(이미지 or 엑셀), 공유(이미지 or 엑셀))

---

## 필수부가기능

1. 마이페이지
   1. 내가만든 여행 일정
   2. 공유받은 여행 일정
   3. 회원탈퇴

## 선택부가기능

1. 주변 관광지 추천(위도, 경도 기준)
2. 관광지 정보 제공
   1. 설명
   2. 별점
   3. 찜하기
3. 커뮤니티
   1. 계획표를 다른 사람들에게도 공유해서 다른 사람이 직접 적용도 가능하게끔
   2. 완료(게시글등록, 저장(이미지 or 엑셀), 공유(이미지 or 엑셀))
4. 음성채팅 or 채팅

## 고려해야할 것

1. 동시작업 → 소켓서버 구축 필요할거 같음

   → 팀미팅때 질문하기 : 시큐리티, 부하

2. 지도와 길찾기는 구글 api 사용 (부분유료임 라이선스 필요)
   1. [https://aidenlim.dev/19](https://aidenlim.dev/19)

## 참고

[https://www.myro.co.kr/](https://www.myro.co.kr/)

## api(관광지 데이터)

[https://www.visitjeju.net/kr/visitjejuapi](https://www.visitjeju.net/kr/visitjejuapi)

[https://api.visitkorea.or.kr/](https://api.visitkorea.or.kr/)

---

# 10.17.

## 기능명세서

![기능명세서](/uploads/cedf77be211df92c76f07e3b07170a66/기능명세서.png)


## 역할분담

| 기능 | 담당자 | 희경 | 나경 | 세진 | 다은 |
| --- | --- | --- | --- | --- | --- |
| 회원 crud+시큐리티
마이페이지(프로필수정) | 지건 |  |  |  |  |
| 버킷리스트 웹소켓, crud(mysql, redis)
데이터(공공데이터포털 관광지정보) | 세진 |  |  | 당첨 |  |
| 일정 웹소켓, crud(mysql, redis)
길찾기(대중교통/자가용/도보) 경로 api | 희경 | 당첨 |  |  |  |
| 방 관리((메인페이지에서의) 날짜, 지역, 방(웹소켓) 생성, 초대, 참가자·접속자 표시)
메인페이지(네이버 축제 정보) | 나경 |  | 당첨 |  |  |
| 지도 api (마커, 선 연결, 검색, 지역선택)
완료 페이지(저장,공유,복사)
마이페이지 일정목록 | 다은 |  |  |  | 당첨 |
| 배포 + CI/CD
데이터(공공데이터포털 관광지정보) + 장소추천 | 승준 |  |  |  |  |


## 기술스택

### 백엔드

- IDE : IntelliJ
- JAVA : 11
- Framework : Spring boot
- Build : Gradle
- WAS : Tomcat
- DBMS : MySql 8, Redis
- DB API : JPA
- WebSocket : Stomp

### 프론트엔드

- IDE : VS Code
- Framework :
    - Vue2
    - Vuetify
- WebSocket : SockJS

### Infra

- AWS
    - EC2
    - S3
- Kubernetes or Docker
- Nginx
- Jenkins

### 협업

- Git
- Jira

---

# 10.18.

## 와이어프레임

[와이어프레임(figma)](https://www.figma.com/file/P5fOhSGIxv63ccy8UT4tpC/Untitled?node-id=9%3A113)

### 컬러팔레트

[컬러팔레트(figma)](https://www.figma.com/file/P5fOhSGIxv63ccy8UT4tpC/Untitled?node-id=0%3A1)

---
