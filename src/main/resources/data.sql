drop table region;
drop table center;

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
    name       varchar(40) not null,
    region_id  integer unsigned not null,
    created_at timestamp   not null,
    updated_at timestamp   not null
);
