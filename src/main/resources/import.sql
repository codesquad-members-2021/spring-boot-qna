INSERT INTO USER (ID, USER_ID, PASSWORD, NAME, EMAIL) VALUES (1, 'august', '1234', 'Jacob', 'august@naver.com' );
INSERT INTO USER (ID, USER_ID, PASSWORD, NAME, EMAIL) VALUES (2, 'hello', '1212', 'Bred', 'hello@google.com' );
INSERT INTO USER (ID, USER_ID, PASSWORD, NAME, EMAIL) VALUES (3, 'legend', 'password', 'Tristan', 'legend@google.com' );

INSERT INTO QUESTION (ID, WRITER_ID, TITLE, CONTENTS, CREATED_DATE_TIME, DELETED) VALUES (1, 1, 'How to avoid the “Circular view path” exception?', 'How to avoid the “Circular view path” exception with Spring MVC test?', CURRENT_TIMESTAMP, FALSE );
INSERT INTO QUESTION (ID, WRITER_ID, TITLE, CONTENTS, CREATED_DATE_TIME, DELETED) VALUES (2, 2, 'How to Disable “typeMismatch” exception in Spring?', 'Can I disable the typeMismatch error, or should I go about all of this some other way?', CURRENT_TIMESTAMP, FALSE );

INSERT INTO ANSWER (ID, CONTENTS, CREATED_DATE_TIME, DELETED, QUESTION_ID, WRITER_ID) VALUES (1, 'Maybe it is not the essential problem.', CURRENT_TIMESTAMP, FALSE, 1, 2 );
INSERT INTO ANSWER (ID, CONTENTS, CREATED_DATE_TIME, DELETED, QUESTION_ID, WRITER_ID) VALUES (2, 'What about changing debugging option to lower level?', CURRENT_TIMESTAMP, FALSE, 1, 3 );
INSERT INTO ANSWER (ID, CONTENTS, CREATED_DATE_TIME, DELETED, QUESTION_ID, WRITER_ID) VALUES (3, 'In that case, just follow the direction of error messages.', CURRENT_TIMESTAMP, FALSE, 2, 1 );
INSERT INTO ANSWER (ID, CONTENTS, CREATED_DATE_TIME, DELETED, QUESTION_ID, WRITER_ID) VALUES (4, 'The short answer: declare members of your backing object as String.', CURRENT_TIMESTAMP, FALSE, 2, 3 );
INSERT INTO ANSWER (ID, CONTENTS, CREATED_DATE_TIME, DELETED, QUESTION_ID, WRITER_ID) VALUES (5, 'Tell me if the solution is not working.', CURRENT_TIMESTAMP, FALSE, 2, 3 );
)
