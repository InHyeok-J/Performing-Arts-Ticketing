insert into `member`(`id`, `name`)
values (1, '김철수'),
       (2, '안철수1'),
       (3, '안철수2'),
       (4, '안철수3'),
       (5, '안철수4'),
       (6, '안철수5'),
       (7, '안철수6'),
       (8, '안철수7'),
       (9, '안철수8'),
       (10, '안철수9');

insert into `performance`(`id`, `name`, `description`, `run_time`, `start_date`, `end_date`)
values (1, '공연1', '공연1입니다', 100, '2025-01-01', '2025-03-01');

insert into `performance_seat_class`(`class_type`, `price`, `performance_id`)
values ('VIP', 10000, 1),
       ('R', 5000, 1);

insert into `performance_session`(`id`, `performance_id`, `start_date_time`)
values (1, 1, '2025-02-08 09:00:00');

insert into `seat`(`class_type`, `price`, `session_id`, `seat_status`, `row_index`, `column_index`, `floor`)
values ('VIP', 10000, 1, 'UN_RESERVE', 1, 1, 1),
       ('VIP', 10000, 1, 'UN_RESERVE', 1, 2, 1),
       ('R', 5000, 1, 'UN_RESERVE', 1, 3, 1),
       ('R', 5000, 1, 'UN_RESERVE', 1, 4, 1);

insert into `discount_policy`(`policy_type`, `name`, `performance_seat_class_id`, `percent`)
values ('PERCENT', '특가 무조건 할인!', 1, 0.1),
       ('PERCENT', '특가 무조건 할인!', 2, 0.1);
