<img src="https://capsule-render.vercel.app/api?type=waving&color=BDBDC8&height=150&section=header" />

# [오운식뭐] 뉴스피드 프로젝트

---
<p>운동과 식단을 포스팅하고, 서로의 포스팅을 조회하며, 댓글을 달 수 있는 서비스입니다.</p> 

### 노션에서 보기 (API명세서, ERD다이어그램, 와이어프레임 확인가능)
> https://www.notion.so/9987-e097fafdc73f4431988212afe3b17f03

---
### Team
| <img src="https://avatars.githubusercontent.com/u/19195930?v=4" width="150" height="150"/> |<img src="https://avatars.githubusercontent.com/u/148612321?v=4" width="150" height="150"/>| <img src="https://avatars.githubusercontent.com/u/154957716?v=4" width="150" height="150"/> | <img src="https://avatars.githubusercontent.com/u/69907023?v=4" width="150" height="150"/> |
|:------------------------------------------------------------------------------------------:|:-:|:-------------------------------------------------------------------------------------------:|:------------------------------------------------------------------------------------------:|
|                     김용태<br/>[@potatoboi](https://github.com/potatoboi)                     |구동현<br/>[@kudongku](https://github.com/kudongku)|                       서예진<br/>[@OZIIJIN](https://github.com/OZIIJIN)                        |                   이재형<br/>[@LeeJaeHyung](https://github.com/LeeJaeHyung)                   |

---
### 기술스택
<img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white">
<img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white">
<img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
<img src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black">
<img src="https://img.shields.io/badge/css-1572B6?style=for-the-badge&logo=css3&logoColor=white">
<img src="https://img.shields.io/badge/html5-E34F26?style=for-the-badge&logo=html5&logoColor=white">
<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white">
<img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white">
<img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">


---

### 구현 완료 기능
- 회원가입 기능
  - 비밀번호는 암호화됩니다.
  - id는 중복이 안됩니다.
  - email도 중복이 안됩니다.
  - requestDto에 validation이 설정되어있습니다.
- 로그인 기능
  - filter에서 로그인기능을 구현했습니다.
  - 로그인 성공시 jwt 토큰이 쿠키에 들어갑니다.
- 프로필 수정 기능
  - 한줄 소개를 수정할 수 있습니다.
  - 이전 비밀번호와 다음 비밀번호를 입력하면 비밀번호가 바뀝니다.
- 게시물 작성 조회, 수정, 삭제 기능
  - 게시물 조회는 인가 없이 가능합니다.
  - 조회를 제외한 나머지 기능들은 전부 인증,인가되어야 합니다.
  - 본인의 글은 본인만 수정 삭제가 가능합니다.
  - front에서 게시물 수정 삭제시 새로고침됩니다.
  - 작성자 본인이 작성한 글을 모아서 확인할 수 있습니다.
- 메인 index페이지에서 다른 사용자들의 게시물들을 한 눈에 볼 수 있습니다.
- 댓글 CRUD
  - 게시물에 댓글을 작성하고, 본인의 댓글은 수정과 삭제가 가능합니다.
  - 댓글 조회를 제외한 기능은 인증, 인가가 가능해야 합니다.
  - front에서 댓글 작성, 수정, 삭제시 새로고침이 기능합니다.
- 회원가입 시 이메일 인증 기능을 포함합니다.
  - 이메일을 입력하고 인증키 보내기를 누르면, 해당 이메일에 암호화된 인증키가 전송됩니다.
  - 인증키를 입력하고 회원가입을 누르면, 인증키의 유효성을 판단하고, 결과값을 return합니다.
- 프론트엔드 만들기
  - 백엔드에서 제공되는 API를 통해 서버와 통신합니다.
  - 와이어 프레임에 나온 명세를 최대한 지켰습니다.

---


###### 기술스택 출처: https://cocoon1787.tistory.com/689
###### 팀원표 출처: https://profile-table-md.vercel.app/
<img src="https://capsule-render.vercel.app/api?type=waving&color=BDBDC8&height=150&section=footer" />
