INSERT INTO USER(USER_ID, PASSWORD, NAME, EMAIL)
VALUES ('janeljs', '1234', '지선', 'jisunlim@gmail.com');
INSERT INTO USER(USER_ID, PASSWORD, NAME, EMAIL)
VALUES ('java', 'test', '자바', 'java@gmail.com');
INSERT INTO QUESTION(AUTHOR_ID, TITLE, CONTENTS, DATE)
VALUES (1, 'Spring - 405 Http method DELETE is not supported',
        'Well I have a strange problem with executing a "DELETE" HTTP request in Spring.', CURRENT_TIMESTAMP);
INSERT INTO QUESTION(AUTHOR_ID, TITLE, CONTENTS, DATE)
VALUES (2, 'IntelliJ IDEA: Boolean method XX is always inverted',
        'I am getting warning " Boolean method is always inverted " on running lint in IntelliJ.', CURRENT_TIMESTAMP);
