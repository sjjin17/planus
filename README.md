# Planus

![자율PJT_서울_5반_A505_UCC경진대회](/uploads/c481ac038a1e230f2e2fabdbe14a2aee/자율PJT_서울_5반_A505_UCC경진대회.mp4)

공동 작업이 가능한 여행 스케줄러 서비스

## 기획 배경

대부분의 경우 사람들은 어디로 여행을 떠날 것인지, 어떤 장소에 들릴 것인지, 이동 경로나 식당 등의 여행 계획을 대략적으로나마 세운 후 여행을 떠나게 됩니다. 

만약 혼자 여행을 한다면 자유롭게 여행 계획을 짤 수 있겠지만, 여러 명이서 여행을 
가게 되는 경우에는 다음과 같이 3가지의 문제점을 마주하게 됩니다. 

각자 가고 싶은 관광지가 서로 다르며, 이러한 의견들을 실시간으로 취합하기 어렵고, 
구성원들 중 한 명이 대표로 책임지고 여행 일정을 짜야 한다는 점입니다.

이러한 문제점을 해결하기 위해, 저희는 여러 명이 동시에 여행 일정을 짤 수 있는 서비스를 
기획하였습니다.

## 프로젝트 기간 : 2022.10.11 ~ 2022.11.18

## 팀원 소개

이나경: 팀장, BE, FE

김승준: BE, FE, Infra

김지건: BE, FE

양희경: BE, FE

장세진: BE, FE

정다은: BE, FE

## 핵심기능

- 버킷리스트
    1. 추천관광지, 장소검색을 통해 버킷리스트 등록
    
    ![버킷리스트](/uploads/246cf121e2fcbca8906c726f307390b0/버킷리스트.gif)

- 일정
    1. 각 날짜별로 일정을 등록
    2. 일정 간 순서 변경
    3. 경로 검색 기능

    ![일정](/uploads/ffb8f4b6f9e10e2ea85e8336effa816d/일정.gif)
    
- 완료일정
    1. 작업이 완료된 여행 일정을 이미지로 저장
    2. SNS 공유
    3. 완료된 일정을 복사하여 새로운 여행 일정 생성

    ![완료일정](/uploads/898304ab4976906bf8c6d03d528737e7/완료일정.gif)

- 커뮤니티
    1. 커뮤니티 기능을 통해 사용자가 완료한 일정을 공유

    ![커뮤니티](/uploads/6358038d8935922ffe3c1aea15a75633/커뮤니티.gif)

### 기술 특장점

- 장소 검색 API
    - Google maps API를 통한 장소 검색 기능을 제공

    ![장소검색](/uploads/fd151c42f8c8c3ce96e018cfb7a40d89/장소검색.gif)

- 경로 API
    - Google maps API를 통한 대중교통 길찾기 정보 제공
    - NaverAPI를 통항 자동차 길찾기 소요시간 정보

    ![길찾기](/uploads/9825883b96c26da3571618a8d3fbecb8/길찾기.gif)

- 추천관광지, 행사정보
    - 한국관광공사 제공 API를 통한 행사 정보 제공

    ![행사정보](/uploads/333a7084ea4a877d6d6f36139613d7b1/행사정보.gif)

    - 추천 관광지 정보 제공

    ![추천장소](/uploads/d8d67f8fd8a489476b01e293b68e0c61/추천장소.gif)

- 웹소켓, Redis
    - 공동작업을 위한 웹소켓 사용
    - 빠른 데이터 접근을 위한 Redis 사용
    

---

## 그 외 기능

- 소셜 로그인(KaKao)

## 기능명세서

![기능명세서](/uploads/c0c5e19ee79398bd3f1c7af32f828f51/기능명세서.png)

## ERD

![erd](/uploads/a0e888e3c17a54e42d9050bc32a5c7f0/erd.png)

## 아키텍처구조

![아키텍처](/uploads/bd83217d140c311538f2f5095e1852bb/아키텍처.png)

## 와이어프레임

![와이어프레임](/uploads/e5189b6a424bb7ed8c4ac72a9982cebf/와이어프레임.png)

## 개발 환경 및 기술 스택

<img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">

<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white">

<img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white">

<img src="https://img.shields.io/badge/vue.js-4FC08D?style=for-the-badge&logo=vue.js&logoColor=white">

<img src="https://img.shields.io/badge/vuetify-1867C0?tyle=for-the-badge&logo=Vuetify&logoColor=white">

<img src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black">

<img src="https://img.shields.io/badge/css-1572B6?style=for-the-badge&logo=css3&logoColor=white">

<img src="https://img.shields.io/badge/amazonaws-232F3E?style=for-the-badge&logo=amazonaws&logoColor=white">

<img src="https://img.shields.io/badge/apache tomcat-F8DC75?style=for-the-badge&logo=apachetomcat&logoColor=white">

<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">

<img src="https://img.shields.io/badge/NGINX-009639?style=for-the-badge&logo=NGINX&logoColor=white">

<img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=Docker&logoColor=white">

<img src="https://img.shields.io/badge/Jenkins-D24939?style=for-the-badge&logo=Jenkins&logoColor=white">

<img src="https://img.shields.io/badge/redis-%23DD0031.svg?style=for-the-badge&logo=redis&logoColor=white">

### 백엔드

- IDE : IntelliJ 2022
- JAVA : 11
- Framework : Spring boot
- Build : Gradle 7.5
- WAS : Tomcat
- DBMS : MySql 8.0
- DB API : JPA
- WebSocket : Stomp

### 프론트엔드

- IDE : VS Code
- Framework :
    - Vue2
    - Vuetify
- WebSocket : SockJS
- WebRTC : OpenVidu

### Infra

- AWS
    - S3
    - EC2
- Docker
- Nginx
- Jenkins

### 협업

- Git
- Jira
