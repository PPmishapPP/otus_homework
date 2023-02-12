create table client
(
    id   bigserial not null primary key,
    name varchar(50)
);

create table address
(
    id        bigserial not null primary key,
    street    varchar(255),
    client_id bigint
);

create table phone
(
    id        bigserial not null primary key,
    number    varchar(15),
    client_id bigint
);

alter table phone
    add constraint client_fk
        foreign key (client_id) references client;

alter table address
    add constraint client_fk
        foreign key (client_id) references client;