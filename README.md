<h1 align="center">Welcome to EXIT 👋</h1>
<p>
<div></div>
  <img alt="Version" src="https://img.shields.io/badge/version-1.1-blue.svg?cacheSeconds=2592000" />
  <a href="https://lab.ssafy.com/s03-webmobile1-sub2/s03p12a501/blob/master/README.md" target="_blank">
    <img alt="Documentation" src="https://img.shields.io/badge/documentation-yes-brightgreen.svg" />
  </a>
  <a href="https://opensource.org/licenses/MIT" target="_blank">
    <img alt="License: MIT" src="https://img.shields.io/badge/License-MIT-yellow.svg" />
  </a>
</p>
<p>
<div>Front & Back</div>
 <img alt="Docker" src="https://img.shields.io/badge/Docker-black?logo=Docker&logoColor=1488C6" />
   <img alt="Node.js" src="https://img.shields.io/badge/Node.js-339933?logo=Node.js&logoColor=white" />
   <img alt="Java" src="https://img.shields.io/badge/Java-007396?logo=Java&logoColor=white" />
    <img alt="Vuetify" src="https://img.shields.io/badge/Vuetify-1867C0?logo=vuetify&logoColor=white" />
    <img alt="Vue.js" src="https://img.shields.io/badge/Vue.js-4FC08D?logo=vue.js&logoColor=white" />
    <img alt="CSS3" src="https://img.shields.io/badge/CSS3-1572B6?logo=CSS3&logoColor=white" />
    <img alt="HTML5" src="https://img.shields.io/badge/html5-E34F26?logo=HTML5&logoColor=white" />
    <img alt="Spring" src="https://img.shields.io/badge/Spring-6DB33F?logo=Spring&logoColor=white" />
    <img alt="MariaDB" src="https://img.shields.io/badge/MariaDB-003545?logo=MariaDB&logoColor=white" />
    <img alt="GitHub" src="https://img.shields.io/badge/GitHub-181717?logo=GitHub&logoColor=white" />
</p>
<p>
<div>Env</div>
    <img alt="Amazon AWS" src="https://img.shields.io/badge/Amazon AWS-232F3E?logo=Amazon AWS&logoColor=white" />
    <img alt="Jira Software" src="https://img.shields.io/badge/Jira Software-0052CC?logo=Jira Software&logoColor=white" />
    <img alt="GitLab" src="https://img.shields.io/badge/GitLab-FCA121?logo=GitLab&logoColor=white" />
    <img alt="Jenkins" src="https://img.shields.io/badge/Jenkins-D24939?logo=Jenkins&logoColor=white" />
    <img alt="Visual studio Code" src="https://img.shields.io/badge/VisualStudioCode-007ACC?logo=Visual studio Code&logoColor=white" />
    <img alt="NPM" src="https://img.shields.io/badge/NPM-CB3837?logo=NPM&logoColor=white" />
    <img alt="Apache Maven" src="https://img.shields.io/badge/Apache Maven-C71A36?logo=Apache Maven&logoColor=white" />
    <img alt=" Yarn" src="https://img.shields.io/badge/Yarn-2C8EBB?logo=Yarn&logoColor=white" />

</p>

> Create your own portfolio blog using 'EXIT'


### 🏠 [Homepage](http://i3a501.p.ssafy.io/)

## What it is

#### 개발자와 곧 개발자가 될 사람들을 위한 포트폴리오 블로그

자신이 개발한 프로그램, 혹은 코드를 사람들과 공유하고 서로 윈윈 

## Main Feature

- 인피니트 스크롤을 활용한 메인 페이지 구현
- 소셜 oauth 를 통한 로그인 구현
- 에디터 tiptap을 이용한 글쓰기
- 검색 기능을 통한 포스트 보기
- 이미지, 게시글, 코딩블럭을 통한 코드 업로드
- 팔로우한 유저의 코드보기
- 유저들의 게시글과 코드에 댓글 남기기

## Develop Period

- 20200713-20200821

## 기술 스택

- Front-end: Vue.cli, node.js, Vuetify, Vuex, bootstrap
- Back-end: Spring, Docker, MariaDB
- Env & etc: AWS-EC2, labssafy, Jira, Jenkins
- API를 이용하여 서버 구동
- Oauth2.0을 이용한 소셜 로그인
- Tiptap을 이용한 글쓰기 방식에 다양한 기능제공
- Vue.cli와 router를 이용하여 SPA를 구현함으로써 빠른 UI/UX 향상

## 진행 상황

# V 1.01  2020-08-07
### Frontend   
-유저 jwt연결/ 각 페이지 라우터 수정 / 프론트엔드 수정 코드 통합   
-댓글 페이지 작성/ 게시글 페이지 수정      
-포스트 업데이트 페이지 라우터 및 버튼 연결   
### Backend   
-게시글 상세보기 URL 수정/   
-댓글 DB생성/ 댓글 CRUD/Swagger Test 완료   
-Tag 검색 쿼리 추가   
-프로필 이미지 디비 생성 및 CRUD   

# V 1.02  2020-08-08
### Frontend
- `moment` 이용 - 서버 시간 기록 형식 수정, 댓글 시간 만들기
- 로그인 동의 rule적용,
- 포스트 상세 페이지 줄바꿈 적용


# V 1.03  2020-08-14
### Frontend
- 소셜 로그인 - 깃허브, 카카오 완성
- 포스트 관련 에디터 적용 - 리스트, 디테일, 업데이트 with Tiptap
- 포스트, 댓글, 유저의 프로필 사진 등록 및 나타내기
- 전체 페이지 스타일 수정
- 검색페이지 결과없음 반영

### Backend   
- 사진 이미지 디비 수정
- 소셜 로그인 데이타 수정
- 유저 오류 해결

# V 1.1  2020-08-20
### EXIT 총 정리
- 블로그 이미지 적용
- 유저 프로필 생성 및 글 목록 작성
- 에디터 코드블럭 색상 추가
- 기능 수정 및 테스트

### 배포 이력   

#### V1.0  : 2020.07.31 09:00   
#### V1.01 : 2020.08.07 09:00
#### V1.02 : 2020.08.14 20:00
#### V1.1 : 2020.08.20 10:00

## REST API

#### 유저

---

- GET /user/detail/{uid}    : 유저 데이터 불러오기
- PUT /user/update  :유저 데이터 수정하기
- DELETE /user/delete/{uid} : 회원 탈퇴

#### 게시물

---

- GET /feature/board/detail/{uid}/{id}  : 해당 유저가 게시물의 상세 정보 확인
- GET /featrue/board/list/{id}  : 모든 게시글의 정보 반환 
- GET /feature/board/list/{id}  : 해당 게시글의 상세 정보 반환
- PUT /feature/board/update : 게시글 수정하기 
- POST /feature/board/write : 게시글 쓰기 
- DELETE /feature/board/delete/{id}     : 게시물 삭제

### 댓글 기능 

- GET /feature/comment/detail/{uid}/{id}/comments : 게시글 번호에 해당하는 댓글 정보 반환 
- DELETE /feature/comment/list/detail/comments/{idx} : 해당 댓글을 삭제하단다
- POST /feature/comment/list/detail/comments/{id}/wirte : 새로운 댓글 작성 
- 

### 좋아요 기능 
 - GET /heart/{bid}     : 해당 게시글의 하트 수 반환 
 - GET /like/{bid}/{uid}    :해당 게시글의 좋아요 상태 변환 
 - 

### 검색 기능 

 -GET /search/{select}/{keyword}    : 키워드에 따라 검색 

---
#### 계정 관리
 - GET /accoutn/key_alter : 이메일 인증 
 - GET /account/login : 로그인 
 - GET /accoutn/signup : 회원가입 
 - POST /user/socialsignin : 소셜 로그인 
 - POST /user/info : 회원정보 가져오기 

## Author

👨 **Youngwook Go**

* Github: [@gyw8526](https://github.com/gyw8526)

👩  **Yunji Na**

* Github: [@dmdekf](https://github.com/dmdekf)

👩  **Soyun Jeon**

* Github: [@twoposition](https://lab.ssafy.com/twoposition)

👨 **Hyung jin Jeon**

* Github: [@wjsgudwls89](https://github.com/wjsgudwls89)



## Show your support

Give a ⭐️ if this project helped you!

## 📝 License

Copyright © 2020 Youngwook Go, Yunji Na, Soyun Jeon, Hyung jin Jeon

***
_This README was generated with ❤️ by [readme-md-generator](https://github.com/kefranabg/readme-md-generator)_

