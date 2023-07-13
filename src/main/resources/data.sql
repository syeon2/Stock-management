drop table region;
drop table center;
drop table attendance;
drop table item_category;
drop table employee;
drop table item;

create table teststockmanagement.region
(
    id         integer unsigned primary key auto_increment,
    name       varchar(40) not null,
    created_at timestamp   not null,
    updated_at timestamp   not null
);

create table teststockmanagement.center
(
    id         integer unsigned primary key auto_increment,
    name       varchar(40)      not null,
    region_id  integer unsigned not null,
    created_at timestamp        not null,
    updated_at timestamp        not null
);

create table teststockmanagement.attendance
(
    id          bigint unsigned primary key auto_increment,
    work_status varchar(40)      not null,
    employee_id bigint unsigned  not null,
    center_id   integer unsigned not null,
    created_at  timestamp        not null,
    updated_at  timestamp        not null
);

create table teststockmanagement.item_category
(
    id         integer unsigned primary key auto_increment,
    name       varchar(40)      not null,
    center_id  integer unsigned not null,
    created_at timestamp        not null,
    updated_at timestamp        not null
);

create table teststockmanagement.employee
(
    id                   bigint unsigned primary key auto_increment,
    name                 varchar(40)      not null,
    phone                varchar(60)      not null,
    employee_status      varchar(40)      not null,
    item_packaging_count integer          not null default 0,
    working_day          timestamp        not null,
    center_id            integer unsigned not null,
    created_at           timestamp        not null,
    updated_at           timestamp        not null
);

create table teststockmanagement.item
(
    id               bigint unsigned primary key auto_increment,
    name             varchar(255)     not null,
    quantity         integer unsigned not null,
    item_category_id integer unsigned not null,
    created_at       timestamp        not null,
    updated_at       timestamp        not null
);
