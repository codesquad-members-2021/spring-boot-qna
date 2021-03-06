INSERT INTO USER(USER_ID, PASSWORD, NAME, EMAIL)
VALUES ('janeljs', '1234', '제인', 'jane@gmail.com');
INSERT INTO USER(USER_ID, PASSWORD, NAME, EMAIL)
VALUES ('trevi', 'test', '트레비', 'lemonsparkling@gmail.com');
INSERT INTO QUESTION(AUTHOR_ID, TITLE, CONTENTS, DATE)
VALUES (1, 'Spring - 405 Http method DELETE is not supported',
        'Well I have a strange problem with executing a "DELETE" HTTP request in Spring.', CURRENT_TIMESTAMP);
INSERT INTO QUESTION(AUTHOR_ID, TITLE, CONTENTS, DATE)
VALUES (2, 'IntelliJ IDEA: Boolean method XX is always inverted',
        'I am getting warning " Boolean method is always inverted " on running lint in IntelliJ.', CURRENT_TIMESTAMP);
INSERT INTO QUESTION(AUTHOR_ID, TITLE, CONTENTS, DATE)
VALUES (2, 'Unique index or primary key violation: "PRIMARY KEY ON PUBLIC.CAR(ID) : While calling the POST service',
        'When I hit the RESTAPI post service, I get the below exception. \n Unique index or primary key violh2-1.4.197.jar:1.4.197] at org.h2.jdbc.JdbcPreparedStatement.executeUpdateInternal(JdbcPreparedStatement.java:199) ~[h2-1.4.197.jar:1.4.197] at org.h2.jdbc.JdbcPreparedStatement.executeUpdate(JdbcPreparedStatement.java:153) Any idea what is wrong in the code?',
        CURRENT_TIMESTAMP);
