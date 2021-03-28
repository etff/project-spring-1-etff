/* 시작시 샘플데이터 입력 */
INSERT INTO MEET (ID, CREATED_AT, UPDATED_AT, COUNT, DETAIL, LOCATION, STARTED_AT, TIME, MESSAGE, TITLE)
VALUES (1, current_timestamp , current_timestamp, 5, '스타벅스', '홍대', '2021-04-01', '10:00 ~ 14:00', 'study', 'mogaco');
INSERT INTO MEET (ID, CREATED_AT, UPDATED_AT, COUNT, DETAIL, LOCATION, STARTED_AT, TIME, MESSAGE, TITLE)
VALUES (2, current_timestamp , current_timestamp, 5, '커피빈스', '강남', '2021-04-03', '10:00 ~ 14:00', 'study', 'mogaco1');
INSERT INTO MEET (ID, CREATED_AT, UPDATED_AT, COUNT, DETAIL, LOCATION, STARTED_AT, TIME, MESSAGE, TITLE)
VALUES (3, current_timestamp , current_timestamp, 5, '스피드 스터디룸', '종로', '2021-04-15', '10:00 ~ 14:00', 'study', 'mogaco2');
INSERT INTO MEET (ID, CREATED_AT, UPDATED_AT, COUNT, DETAIL, LOCATION, STARTED_AT, TIME, MESSAGE, TITLE)
VALUES (4, current_timestamp , current_timestamp, 5, '맥도날드', '이대', '2021-04-20', '10:00 ~ 14:00', 'study', 'mogaco3');
INSERT INTO MEET (ID, CREATED_AT, UPDATED_AT, COUNT, DETAIL, LOCATION, STARTED_AT, TIME, MESSAGE, TITLE)
VALUES (5, current_timestamp , current_timestamp, 5, '스타벅스', '성수', '2021-04-04', '10:00 ~ 14:00', 'study', 'mogaco4');
INSERT INTO STUDY (ID, SUBJECT, MEET_ID) VALUES (1, 'NODEJS', 1);
INSERT INTO STUDY (ID, SUBJECT, MEET_ID) VALUES (2, 'NODEJS', 2);
INSERT INTO STUDY (ID, SUBJECT, MEET_ID) VALUES (3, 'NODEJS', 3);
INSERT INTO STUDY (ID, SUBJECT, MEET_ID) VALUES (4, 'NODEJS', 4);
INSERT INTO STUDY (ID, SUBJECT, MEET_ID) VALUES (5, 'NODEJS', 5);
