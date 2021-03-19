# 질문답변 게시판

▶ [Heroku 배포 URL](https://damp-lowlands-18176.herokuapp.com/)

## 기능별 URL convention
### HomeController
웰컴 페이지

- GET `/`
    - redirect:/questions
  
### UserController

회원 목록 조회

- GET `/user`
  - userList
  
회원가입

- GET `/user/new`
    - userSignup
- POST `/user`
    - redirect:/user
  
회원 프로필 조회

- GET `/user/{id}`
    - ModelAndView("userProfile")

비밀번호 확인

- GET `/user/{userId}/password-check`
    - passwordCheckForm
- POST `/user/{userId}/password-check`
    - if 비밀번호 일치) userUpdateForm
    - if 비밀번호 불일치) redirect:/

회원정보 수정

- POST `/user/{userId}/edit`
    - redirect:/user
  
### QuestionController

질문 목록 조회

- GET `/questions`
  - index
  
질문하기

- GET `/questions/new`
    - questionInputForm
- POST `/questions`
    - redirect:/

질문 조회 (상세보기)

- GET `/questions/{index}`
    - questionDetail
