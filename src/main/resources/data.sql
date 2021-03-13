INSERT INTO USER (id, user_id, password, name, email)
VALUES (1, 'Riddler', '1234', 'Edward Nygma', 'riddle@me.this');
INSERT INTO USER (id, user_id, password, name, email)
VALUES (2, 'Scarecrow', '1234', 'Jonathan Crane', 'crow@bar.me');

INSERT INTO QUESTION (ID, TITLE, CONTENTS, REPORTING_DATE_TIME, WRITER_ID)
VALUES (NULL,
        'Riddle: "What can''t you have for breakfast and lunch."',
        'Answer: Dinner',
        {ts '2021-03-12 18:23:32'},
        1);

INSERT INTO ANSWER(ID, CONTENTS, QUESTION_ID, REPORTING_DATE_TIME, WRITER_ID)
values (NULL, 'Ha!', 1, {ts '2021-03-12 18:25:32'}, 1);
