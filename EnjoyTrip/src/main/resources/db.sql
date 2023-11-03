create table board (
    no int AUTO_INCREMENT primary key,
    title varchar(200) not null,
    member_id varchar(30) not null,
    content varchar(2000) not null,
    hit int default 0,
    register_time TIMESTAMP default CURRENT_TIMESTAMP,
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
    age int not null
)
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;