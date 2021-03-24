안녕하세요! 백엔드반 노을👦 입니다!

QnA 미션 과정이 끝나가고 있어서, 
속도를 내고자 미션3와 비슷한 미션4를 한 브랜치에서 작업하고 PR요청 드리게 되었습니다.

이번 미션을 통해 `구현한 기능`은 다음과 같습니다!

| 미션 3. 로그인 구현   | 미션 4. 로그인 구현            | 그 외                                                        |
| --------------------- | ------------------------------ | ------------------------------------------------------------ |
| ✅ 로그인 기능         | ✅ 객체-관계 매핑               | ✅ 로그인한 사용자만 글쓰기 가능한 기능                       |
| ✅ 개인정보 수정 기능  | ✅ 답변 생성/수정/삭제          | ✅ 아이디 중복/로그인 실패에 대한 예외 처리 (페이지 이동)     |
| ✅ 로그인 기능         | ✅ Controller, Service 분리     | ✅ `@ExceptionHandler` 와 `@ControllerAdvise` 를 통한 예외 처리 |
| ✅ 질문 생성/수정/삭제 | ✅ (선택) Soft Delete, 삭제조건 | ✅ 에러 페이지 생성 ( `templates/error/404~500..`)            |

💫<u>배포링크</u>: https://noel-boot-qna.herokuapp.com/



### 궁금한점

1️⃣ 컨트롤러와 서비스를 처음으로 분리해보았는데 올바르게 했는지 궁금합니다.

2️⃣ @ExceptionHandler 를 이용해 예외처리를 시도해보았는데, 이러한 핸들러를 통해 View를 리턴해주는 방식이 옳은 방식인지 궁금합니다.

3️⃣ 객체를 find를 할 당시에 삭제 boolean 검사를 하고, 유효한 객체만 리턴하도록 `Soft Delete`를 구현하였는데, 올바르게 했는지 궁금합니다.

4️⃣ 아래와 같이, 맵핑되는 주소는 동일한대  스프링 에노테이션을 이용해  함수 오버로딩과 같이 구현을 했는데 올바른 방식인지 궁금합니다!

```java
@GetMapping("/questions/{id}") 
private String modifyQuestionButton(Parameter){..}

@PutMapping("/questions/{id}") 
private String modifyQuestion(Parameter){..}

@DeleteMapping("/questions/{id}") 
private String deleteQuestion(Parameter){..}
```



부족한 코드지만 시간내어 리뷰해주셔서 감사합니다! 😁