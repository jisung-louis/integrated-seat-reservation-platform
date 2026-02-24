drop database if exists project;
create database project;
use project;


create table user(
    no int auto_increment,
    constraint primary key(no),
    id varchar(30) not null unique,
    password varchar(30) not null,
    name varchar(30) not null,
    isAdmin boolean default false
);


create table store(
    no int auto_increment,
    constraint primary key (no),
    name varchar(30) not null,
    category varchar(10) not null,
    owner_no int not null,
    address varchar(50) not null, 
    contact varchar(15) not null,
    email varchar(50) not null,
    bh_weekdays varchar(20) not null,
    bh_saturday varchar(20) not null,
    bh_sunday varchar(15) not null,
    status int default 1
);


create table seat(
    code varchar(30) not null,
    constraint primary key (code), 
    store_no int not null,
    colcode varchar(10) not null,
    rownum varchar(10) not null,
    status int default 0
);


create table reservation(
    no int auto_increment,
    constraint primary key (no),
    user_no int not null,
    seat_code varchar(30) not null,
    reservedAt datetime default now()
);
-- A. User 데이터
-- INSERT INTO user (no, id, password, name, isAdmin) VALUES 
-- (1, 'admin', '1234', '관리자', true),
-- (2, 'hong_gd', 'pass1', '홍길동', false),
-- (3, 'kim_chul', 'pass2', '김철수', false),
-- (4, 'lee_young', 'pass3', '이영희', false),
-- (5, 'park_min', 'pass4', '박민수', false),
-- (6, 'choi_ji', 'pass5', '최지우', false),
-- (7, 'jung_ho', 'pass6', '정호석', false),
-- (8, 'kang_daniel', 'pass7', '강다니엘', false),
-- (9, 'song_min', 'pass8', '송민호', false),
-- (10, 'iu_love', 'pass9', '아이유', false);

-- B. Store 데이터 
-- INSERT INTO store (no, name, category, owner_no, address, contact, email, bh_weekdays, bh_saturday, bh_sunday, status) VALUES 
-- (1, '스타벅스 강남점', '카페', 2, '서울시 강남구', '02-111-1111', 'star_kangnam@test.com', '07:00-22:00', '08:00-22:00', '09:00-21:00', 1),
-- (1, '맥도날드 홍대점', '패스트푸드', 1, '서울시 마포구', '02-222-2222', 'mac_hong@test.com', '24시간', '24시간', '24시간', 1),
-- (3, 'BBQ 치킨 역삼점', '치킨', 3, '서울시 강남구', '02-333-3333', 'bbq_yeok@test.com', '12:00-24:00', '12:00-01:00', '12:00-23:00', 1),
-- (4, '김밥천국 종로점', '분식', 3, '서울시 종로구', '02-444-4444', 'gim_jong@test.com', '06:00-21:00', '06:00-20:00', '휴무', 1),
-- (5, '투썸플레이스 신촌', '카페', 4, '서울시 서대문구', '02-555-5555', 'two_sin@test.com', '08:00-23:00', '08:00-23:00', '09:00-22:00', 1),
-- (6, '맛있는 국밥', '한식', 4, '서울시 영등포구', '02-666-6666', 'guk_young@test.com', '09:00-21:00', '09:00-21:00', '10:00-20:00', 1),
-- (7, '피자헛 건대점', '피자', 5, '서울시 광진구', '02-777-7777', 'pizza_gun@test.com', '11:00-22:00', '11:00-23:00', '11:00-22:00', 1),
-- (8, '초밥의 신', '일식', 5, '서울시 송파구', '02-888-8888', 'sushi_song@test.com', '11:30-22:00', '11:30-22:00', '11:30-21:00', 1),
-- (9, '베스킨라빈스', '디저트', 6, '서울시 강동구', '02-999-9999', 'br_kang@test.com', '10:00-23:00', '10:00-23:00', '10:00-23:00', 1),
-- (10, '서브웨이 을지로', '샌드위치', 6, '서울시 중구', '02-000-0000', 'sub_eul@test.com', '08:00-22:00', '09:00-21:00', '09:00-20:00', 1);

-- C. Seat 데이터
-- INSERT INTO seat (code, store_no, colcode, rownum, status) VALUES 
-- ('1-A-3', 1, 'A', '3', 0),
-- ('1-A-4', 1, 'A', '4', 0),
-- ('1-A-5', 1, 'A', '5', 0),
-- ('1-A-6', 1, 'A', '6', 0),
-- ('1-A-7', 1, 'A', '7', 0),
-- ('1-B-1', 1, 'B', '1', 0),
-- ('1-B-2', 1, 'B', '2', 0),
-- ('1-B-3', 1, 'B', '3', 0),
-- ('1-B-4', 1, 'B', '4', 0),
-- ('1-B-5', 1, 'B', '5', 0),
-- ('1-B-6', 1, 'B', '6', 0),
-- ('1-B-7', 1, 'B', '7', 0),
-- ('2-A-1', 2, 'A', '1', 0),
-- ('2-A-2', 2, 'A', '2', 0),
-- ('2-A-3', 2, 'A', '3', 0),
-- ('2-B-1', 2, 'B', '1', 0),
-- ('3-A-1', 3, 'A', '1', 0),
-- ('3-B-1', 3, 'B', '1', 0);

-- D. Reservation 데이터
-- INSERT INTO reservation (no, user_no, seat_code, reservedAt) VALUES 
-- (1, 1, '1-A-3', '2026-02-01 14:00:00'),
-- (2, 2, '1-A-4', '2026-02-01 14:00:00'),
-- (3, 3, '1-A-5', '2026-02-01 14:00:00'),
-- (4, 4, '1-fA-6', '2026-02-01 14:00:00'),
-- (5, 1, '1-A-7', '2026-02-01 14:00:00'),
-- (6, 1, '1-B-1', '2026-02-01 14:00:00'),
-- (7, 5, '1-B-2', '2026-02-02 10:00:00'),
-- (8, 6, '1-B-3', '2026-02-02 11:30:00'),
-- (9, 2, '1-B-4', '2026-02-03 12:00:00'),
-- (10, 3, '1-B-5', '2026-02-03 13:00:00');