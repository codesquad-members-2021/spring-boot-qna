INSERT INTO USER (id, user_id, password, name, email) VALUES (1, 'starve', '1234', '스타브', 'starve@slipp.net');
INSERT INTO USER (id, user_id, password, name, email) VALUES (2, 'test', '1234', 'test', 'test@slipp.net');

INSERT INTO QUESTION (id, writer_id, title, contents, created_Date_Time, answer_count, deleted) VALUES (1, 1, '1234', 'test', CURRENT_TIMESTAMP(), 0, false);
