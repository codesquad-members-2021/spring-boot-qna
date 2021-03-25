# 질문답변 게시판

▶ [Heroku 배포 URL](https://damp-lowlands-18176.herokuapp.com/)

## 기능별 URL convention
### HomeController
웰컴 페이지(=질문 목록 조회)

- GET `/`
    - index
  
### UserController

회원 목록 조회

- GET `/users`
  - user/userList
  
회원가입

- GET `/users/signup`
    - user/userSignup
- POST `/users/signup`
    - redirect:/users
  
로그인

- GET `/users/login`
    - user/loginForm
- POST `/users/login`
    - redirect:/
  
로그아웃

- GET `/users/logout`
  - redirect:/

회원 프로필 조회

- GET `/users/{id}`
  - user/userProfile

회원정보 수정
- GET `/users/{id}/updateForm`
    - user/userUpdateForm
- PUT `/users/{id}/updateForm`
    - redirect:/users
  
### QuestionController
  
질문하기

- GET `/questions/form`
    - qna/questionInputForm
- POST `/questions/form`
    - redirect:/

질문 조회 (상세보기)

- GET `/questions/{questionId}`
    - qna/questionDetail

질문 수정

- GET `/questions/{questionId}/updateForm`
    - qna/questionUpdateForm
- POST `/questions/{questionId}/updateForm`
    - redirect:/questions/" + questionId

질문 삭제

- DELETE
