<h1 align="center">Welcome to EXIT 👋</h1>
<p>
  <img alt="Version" src="https://img.shields.io/badge/version-0.6.0-blue.svg?cacheSeconds=2592000" />
  <a href="https://lab.ssafy.com/s03-webmobile1-sub2/s03p12a501/blob/master/README.md" target="_blank">
    <img alt="Documentation" src="https://img.shields.io/badge/documentation-yes-brightgreen.svg" />
  </a>
  <a href="https://opensource.org/licenses/MIT" target="_blank">
    <img alt="License: MIT" src="https://img.shields.io/badge/License-MIT-yellow.svg" />
  </a>
</p>
<p>
 <img alt="Docker" src="https://img.shields.io/badge/Docker-black?logo=Docker&logoColor=1488C6" />
   <img alt="Node.js" src="https://img.shields.io/badge/Node.js-339933?logo=Node.js&logoColor=white" />
    <img alt="Vuetify" src="https://img.shields.io/badge/Vuetify-1867C0?logo=vuetify&logoColor=white" />
    <img alt="Vue.js" src="https://img.shields.io/badge/Vue.js-4FC08D?logo=vue.js&logoColor=white" />
    <img alt="Spring" src="https://img.shields.io/badge/Spring-6DB33F?logo=Spring&logoColor=white" />
    <img alt="MariaDB" src="https://img.shields.io/badge/MariaDB-003545?logo=MariaDB&logoColor=white" />
</p>
<p>
    <img alt="Amazon AWS" src="https://img.shields.io/badge/Amazon AWS-232F3E?logo=Amazon AWS&logoColor=white" />
    <img alt="Jira Software" src="https://img.shields.io/badge/Jira Software-0052CC?logo=Jira Software&logoColor=white" />
    <img alt="GitLab" src="https://img.shields.io/badge/GitLab-FCA121?logo=GitLab&logoColor=white" />
    <img alt="Jenkins" src="https://img.shields.io/badge/Jenkins-D24939?logo=Jenkins&logoColor=white" />
</p>

> Create your own portfolio blog using 'EXIT'

### 🏠 [Homepage](i3a501.p.ssafy.io)

## What it is

#### 개발자와 곧 개발자가 될 사람들을 위한 포트폴리오 블로그

자신이 개발한 프로그램, 혹은 코드를 사람들과 공유하고 서로 윈윈 

## Main Feature

- 이미지, 게시글, 코딩블럭을 통한 코드 업로드
- 팔로우한 유저의 코드보기
- 유저들의 게시글과 코드에 댓글 남기기

## Develop Period

- 20200713-20200821

## 기술 스택

- Front-end: Vue.cli, node.js
- Back-end: Spring, Docker,MariaDB
- Env & etc: AWS-EC2, labssafy, Jira, Jenkins
- API를 이용하여 서버 구동
- Vue.cli와 router를 이용하여 SPA를 구현함으로써 빠른 UI/UX 향상

## REST API

#### 유저 정보

---

- GET /user/{uid} //유저 데이터 불러오기
- PUT /user/{uid} //유저 데이터 수정하기

#### 게시물

---

- GET /post/{range}/{key_word} // Post 검색하기
- GET /post/{p_id} // Post 상세 페이지
- PUT /post/update // Post 수정
- DELETE /post/delete/{u_id}  // Post 삭제
- GET /search/{selected}/{word} // 게시물 검색 

### ✨ [Demo](i3a501.p.ssafy.io)

## Install

```sh
yarn install
```

## Run 

### Spring Boot

```
run WebBlogApplication.java  as Spring Boot App
```

### Vue.Cli

```sh
yarn serve
```

### DB

```
Docker start mariadb
docker exec -it mariadb mysql -u root -p
```

### Vuetify
```
vue install vuetify
```

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