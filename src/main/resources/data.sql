
INSERT INTO USER (USER_ID, PASSWORD, NAME, EMAIL) VALUES ('javajigi', 'tester', '자바지기', 'javajigi@slipp.net');
INSERT INTO USER (USER_ID, PASSWORD, NAME, EMAIL) VALUES ('sanjigi', 'devops', '산삼을찾아', 'sanjigi@slipp.net');
INSERT INTO USER (USER_ID, PASSWORD, NAME, EMAIL) VALUES ('dormamu', 'villrun', '돌마무', 'dormamamu@mcu.com');
INSERT INTO USER (USER_ID, PASSWORD, NAME, EMAIL) VALUES ('muyaho', 'grandfa', '알래스카무야호', 'MBC@muhandozen.co.kr');
INSERT INTO USER (USER_ID, PASSWORD, NAME, EMAIL) VALUES ('dev', '1', 'devtesters', 'deee@eeeev.com');


INSERT INTO QUESTION (ID, WRITER_ID, TITLE, CONTENTS, CREATED_DATE_TIME)
VALUES (null,1, '자바스크립트 함 배워보쉴?', '자바스크립..',{ts '2021-03-05 18:23:32'});

INSERT INTO QUESTION (ID, WRITER_ID, TITLE, CONTENTS, CREATED_DATE_TIME)
VALUES (null,5, '오늘저녁은 치킨이닭', '베그왕김베그..',{ts '2021-03-19 11:11:11'});


INSERT INTO ANSWER (CONTENTS, CREATED_DATE_TIME, QUESTION_ID, WRITER_ID)
VALUES ('양념치킨을 고려해보면 어떨까 달콤하고',{ts '2021-03-24 12:34:56'},2,5);

INSERT INTO ANSWER (CONTENTS, CREATED_DATE_TIME, QUESTION_ID, WRITER_ID)
VALUES ('그돈이면 차라리 따끈하고 뜨끈한 국밥이 네그릇인데',{ts '2021-03-24 12:34:56'},2,5);
