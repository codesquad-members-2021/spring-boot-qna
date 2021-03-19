INSERT INTO USER (ID, USER_ID, PASSWORD, NAME, EMAIL, CREATED_DATE_TIME, MODIFIED_DATE_TIME)
VALUES (1, 'Riddler', '1234', 'Edward Nygma', 'riddle@me.this', {ts '2021-03-12 18:10:32'}, {ts '2021-03-12 18:10:32'});
INSERT INTO USER (ID, USER_ID, PASSWORD, NAME, EMAIL, CREATED_DATE_TIME, MODIFIED_DATE_TIME)
VALUES (2, 'Scarecrow', '1234', 'Jonathan Crane', 'crow@bar.me', {ts '2021-03-12 18:11:32'},
        {ts '2021-03-12 18:10:32'});

INSERT INTO QUESTION (ID, TITLE, CONTENTS, CREATED_DATE_TIME, MODIFIED_DATE_TIME, WRITER_ID)
VALUES (NULL,
        'Riddle: "What can''t you have for breakfast and lunch."',
        'Answer: Dinner',
        {ts '2021-03-12 18:23:32'},
        {ts '2021-03-12 18:23:32'},
        1);

INSERT INTO ANSWER(ID, CONTENTS, QUESTION_ID, CREATED_DATE_TIME, MODIFIED_DATE_TIME, WRITER_ID)
values (NULL, 'Ha!', 1, {ts '2021-03-12 18:25:32'}, {ts '2021-03-12 18:25:32'}, 1);
