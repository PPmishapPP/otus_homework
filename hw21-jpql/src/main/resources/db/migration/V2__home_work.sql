alter table client
    add address_id bigint;

create table address
(
    id     bigint not null primary key,
    street varchar(255)
);

create table phone
(
    id        bigint not null primary key,
    number    varchar(15),
    client_id bigint
);

alter table phone
    add constraint client_fk
        foreign key (client_id) references client;

alter table client
    add constraint address_fk
        foreign key (address_id) references address;