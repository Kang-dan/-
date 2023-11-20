use enjoytrip;

create table board (
    board_no int AUTO_INCREMENT primary key,
    board_title varchar(200) not null,
    member_id varchar(30) not null,
    sido_code int not null,
    board_content varchar(2000) not null,
    board_hit int default 0,
    board_love int default 0,
    board_x int not null,
    board_y int not null,
    board_register_time TIMESTAMP default CURRENT_TIMESTAMP,
    FOREIGN KEY(member_id) REFERENCES members(member_id)
)
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

create table members (
    member_id varchar(30) not null primary key,
    member_pw varchar(60) not null,
    member_name varchar(60) not null,
    join_date TIMESTAMP default CURRENT_TIMESTAMP,
    member_email varchar(100) not null,
    age int not null,
    member_board_count int default 0,
    token varchar(1000) null DEFAULT null
)
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

create table attraction_festival (
	content_id int PRIMARY key,
    content_type_id int not null,
    title varchar(200) not null,
    addr1 varchar(200),
    addr2 varchar(200),
    tel varchar(50),
    first_image varchar(200),
    sido_code int,
    gugun_code int,
    latitude decimal(20, 17),
    longitude decimal(20, 17),
    mlevel varchar(2),
    event_start_date varchar(20),
    event_end_date varchar(20)    
);

create table members_likes (
	no int primary key auto_increment,
    member_id varchar(30) not null,
    content_id int default null,
    content_festival_id int default null,
    content_title varchar(100) not null,
    FOREIGN KEY(member_id) REFERENCES members(member_id),
    FOREIGN KEY(content_id) REFERENCES attraction_info(content_id)
);