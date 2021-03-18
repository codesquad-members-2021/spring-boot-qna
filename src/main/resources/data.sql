INSERT INTO USER (id, user_id, password, name, email) VALUES (1, 'javajigi', 'test', '자바지기', 'javajigi@slipp.net');
INSERT INTO USER (id, user_id, password, name, email) VALUES (2, 'yeon', '1234', '나연', 'yeon@slipp.net');

INSERT INTO QUESTION (id, writer_id, title, contents, post_time) VALUES (1, 2, '자바를 배울까요 파이썬을 배울까요', '무엇이 좋을까요', CURRENT_TIMESTAMP);
