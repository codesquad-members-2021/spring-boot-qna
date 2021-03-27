INSERT INTO USER (id, user_id, password, name, email) VALUES (1, 'shion', 'test', '시온', 'test@test.com');
INSERT INTO USER (id, user_id, password, name, email) VALUES (2, 'sub', 'test', '섭이', 'aaa@test.com');

INSERT INTO QUESTION(id, contents, deleted, title, writer_id, create_date_time, count_of_answer) VALUES(1, '내용입니다', false, '안녕하세요', 1, CURRENT_TIMESTAMP(), 0);
INSERT INTO QUESTION(id, contents, deleted, title, writer_id, create_date_time, count_of_answer) VALUES(2, '내용입니다', false, '섭섭합니다', 2, CURRENT_TIMESTAMP(), 0);
