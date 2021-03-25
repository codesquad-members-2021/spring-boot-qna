-- USER 쿼리문 --
INSERT INTO USER (USER_ID, PASSWORD, NAME, EMAIL)
VALUES ('javajigi', 'javajigi', '자바지기', 'javajigi@slipp.net');
INSERT INTO USER (USER_ID, PASSWORD, NAME, EMAIL)
VALUES ('sanjigi', 'pw', '산삼을찾아', 'sanjigi@slipp.net');
INSERT INTO USER (USER_ID, PASSWORD, NAME, EMAIL)
VALUES ('dormamu', 'villrun', '돌마무', 'dormamamu@mcu.com');
INSERT INTO USER (USER_ID, PASSWORD, NAME, EMAIL)
VALUES ('muyaho', 'mudo', '알래스카무야호', 'MBC@muhandozen.co.kr');
INSERT INTO USER (USER_ID, PASSWORD, NAME, EMAIL)
VALUES ('dev', '1', 'devtesters', 'deee@eeeev.com');
INSERT INTO USER (USER_ID, PASSWORD, NAME, EMAIL)
VALUES ('test', '1', 'test_holly', 'holly@moly.com');

-- QUESTION 쿼리문 --
INSERT INTO QUESTION (WRITER_ID, TITLE, CONTENTS, CREATED_DATE_TIME, DELETED)
VALUES (1, '자바스크립트 함 배워보쉴?', '자바스크립..', {ts '2021-03-05 18:23:32'}, FALSE);

INSERT INTO QUESTION (WRITER_ID, TITLE, CONTENTS, CREATED_DATE_TIME, DELETED)
VALUES (5, '오늘저녁은 치킨이닭', '베그왕김베그..', {ts '2021-03-19 11:11:11'}, FALSE);

-- ANSWER 쿼리문 --
INSERT INTO ANSWER (CONTENTS, CREATED_DATE_TIME, QUESTION_ID, WRITER_ID, DELETED)
VALUES ('양념치킨을 고려해보면 어떨까 달콤하고', {ts '2021-03-24 12:34:56'}, 2, 5, FALSE);

INSERT INTO ANSWER (CONTENTS, CREATED_DATE_TIME, QUESTION_ID, WRITER_ID, DELETED)
VALUES ('그돈이면 차라리 따끈하고 뜨끈한 국밥이 네그릇인데', {ts '2021-03-24 12:34:56'}, 2, 5,FALSE);

INSERT INTO ANSWER (CONTENTS, CREATED_DATE_TIME, QUESTION_ID, WRITER_ID, DELETED)
VALUES ('삭제된 댓글은 보이지 않아야 합니다', {ts '2021-02-15 12:34:56'}, 2, 5,TRUE);
