INSERT INTO USER (user_id, password, name, email, created_date) VALUES ('javajigi', 'test', '자바지기', 'javajigi@slipp.net', CURRENT_TIMESTAMP());
INSERT INTO USER (user_id, password, name, email, created_date) VALUES ('sanjigi', 'test', '산지기', 'sanjigi@slipp.net', CURRENT_TIMESTAMP());
INSERT INTO USER (user_id, password, name, email, created_date) VALUES ('123', '123', '123', '123@slipp.net', CURRENT_TIMESTAMP());
INSERT INTO USER (user_id, password, name, email, created_date) VALUES ('abc', 'abc', 'abc', 'abc@slipp.net', CURRENT_TIMESTAMP());

INSERT INTO QUESTION (contents, created_date, title, writer_id, count_of_answers) VALUES ('테스트1', CURRENT_TIMESTAMP(), '주중 모각코', '3', 0);
INSERT INTO QUESTION (contents, created_date, title, writer_id, count_of_answers) VALUES ('테스트2', CURRENT_TIMESTAMP(), '주말 모임', '3', 0);
