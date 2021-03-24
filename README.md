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

비밀번호 확인

- GET `/users/{id}/password`
    - passwordCheckForm
- POST `/users/{id}/password`
    - if 비밀번호 일치) user/userUpdateForm
    - if 비밀번호 불일치) redirect:/

회원정보 수정

- PUT `/users/{id}`
    - redirect:/users
  
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
    - questionDetail
