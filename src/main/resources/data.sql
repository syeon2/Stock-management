drop table region;

create table teststockmanagement.region
(
    id         integer unsigned primary key auto_increment,
    name       varchar(40) not null,
    created_at timestamp   not null,
    updated_at timestamp   not null
)
