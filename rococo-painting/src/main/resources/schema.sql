-- create database "rococo-painting" with owner postgres;

create extension if not exists "uuid-ossp";

create table if not exists paintings
(
    id                      UUID unique        not null default uuid_generate_v1(),
    title                   varchar(50)        not null,
    description             varchar(255),
    content                 bytea,
    museum                  UUID,
    artist                  UUID,
    primary key (id)
);

alter table paintings
    owner to postgres;

