INSERT INTO USER (id, user_id, password, name, email, deleted) VALUES (1, 'starve', '1234', '스타브', 'starve@slipp.net', false);
INSERT INTO USER (id, user_id, password, name, email, deleted) VALUES (2, 'kyu', '1234', 'kyu', 'kyu@slipp.net', false);

INSERT INTO QUESTION (id, writer_id, title, contents, create_date, answer_count, deleted) VALUES (1, 1, '1234', 'test', CURRENT_TIMESTAMP(), 0, false);
