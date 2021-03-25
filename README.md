# 질문답변 게시판

▶ [Heroku 배포 URL](https://damp-lowlands-18176.herokuapp.com/)

## 기능별 URL convention
### HomeController
웰컴 페이지

- GET `/`
    - redirect:/questions
  
### UserController

회원 목록 조회

- GET `/users`
  - user/userList
  
회원가입

- GET `/users/signup`
    - user/userSignup
- POST `/users/signup`
    - redirect:/users
  
회원 프로필 조회

- GET `/users/{id}`
    - user/userProfile
  
로그인

- GET `/users/login`
    - user/loginForm
- POST `/users/login`
    - if user가 없거나 password가 맞지 않을 때 : redirect:/users/login
    - else : redirect:/
  
로그아웃

- GET `/users/logout`
  - redirect:/

회원정보 수정
- GET `/users/{id}/updateForm`
    - if 세션이 없거나 아이디가 맞지 않을 때 : redirect:/users/login
    - else : user/userUpdateForm
- PUT `/users/{id}/updateForm`
    - if 세션이 없거나 아이디가 맞지 않을 때 : redirect:/users/login
    - if 기존 비밀번호가 일치하지 않을 때 : redirect:/users/{id}/updateForm
    - else : redirect:/users
  
### QuestionController

질문 목록 조회

- GET `/questions`
  - index
  
질문하기

- GET `/questions/form`
    - qna/questionInputForm
- POST `/questions/form`
    - redirect:/

질문 조회 (상세보기)

- GET `/questions/{id}`
    - qna/questionDetail
