-- create database "rococo-museum" with owner postgres;

create extension if not exists "uuid-ossp";

create table if not exists countries
(
    id                      UUID        unique        not null default uuid_generate_v1(),
    name                    varchar(50) unique        not null,
    primary key (id, name)
);

alter table countries
    owner to postgres;

create table if not exists cities
(
    id                      UUID unique              not null default uuid_generate_v1()  primary key,
    city                    varchar(50)              not null,
    country                 UUID                     not null,
    constraint fk_cities_countries foreign key (country) references countries (id)
);

alter table cities
    owner to postgres;

create table if not exists museums
(
    id                      UUID unique        not null default uuid_generate_v1()  primary key,
    title                   varchar(50)        not null,
    description             varchar(255),
    photo                   bytea,
    geo                     UUID,
    constraint fk_museums_cities foreign key (geo) references cities (id)
);

alter table museums
    owner to postgres;

insert into countries(name) values ('Fiji') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Tanzania') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Western Sahara') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Canada') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('United States') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Kazakhstan') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Uzbekistan') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Papua New Guinea') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Argentina') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Chile') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Democratic Republic of the Congo') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Somalia') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Kenya') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Sudan') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Chad') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Haiti') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Dominican Republic') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Russia') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Bahamas') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Falkland Islands') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Norway') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Greenland') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Timor-Leste') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('South Africa') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Lesotho') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Mexico') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Uruguay') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Brazil') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Bolivia') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Peru') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Colombia') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Panama') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Costa Rica') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Nicaragua') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Honduras') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('El Salvador') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Guatemala') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Belize') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Venezuela') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Guyana') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Suriname') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('France') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Ecuador') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Puerto Rico') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Jamaica') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Cuba') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Zimbabwe') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Botswana') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Namibia') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Senegal') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Mali') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Mauritania') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Benin') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Niger') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Nigeria') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Cameroon') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Togo') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Ghana') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Côted Ivoire') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Guinea') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Guinea-Bissau') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Liberia') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Sierra Leone') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Burkina Faso') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Central African Republic') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Republic of the Congo') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Gabon') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Equatorial Guinea') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Zambia') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Malawi') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Mozambique') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Eswatini') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Angola') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Burundi') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Israel') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Lebanon') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Madagascar') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('The Gambia') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Tunisia') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Algeria') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Jordan') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('United Arab Emirates') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Qatar') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Kuwait') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Iraq') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Oman') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Vanuatu') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Cambodia') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Thailand') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Lao PDR') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Myanmar') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Vietnam') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Dem. Rep. Korea') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Republic of Korea') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Mongolia') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('India') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Bangladesh') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Bhutan') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Nepal') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Pakistan') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Afghanistan') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Tajikistan') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Kyrgyzstan') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Turkmenistan') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Iran') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Syria') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Armenia') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Sweden') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Belarus') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Ukraine') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Poland') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Austria') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Hungary') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Moldova') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Romania') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Lithuania') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Latvia') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Estonia') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Germany') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Bulgaria') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Greece') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Turkey') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Albania') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Croatia') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Switzerland') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Luxembourg') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Belgium') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Netherlands') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Portugal') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Spain') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Ireland') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('New Caledonia') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('New Zealand') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Australia') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Sri Lanka') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('China') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Taiwan') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Italy') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Denmark') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('United Kingdom') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Iceland') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Azerbaijan') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Georgia') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Philippines') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Malaysia') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Brunei Darussalam') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Slovenia') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Finland') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Slovakia') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Czech Republic') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Eritrea') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Japan') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Paraguay') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Yemen') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Saudi Arabia') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Cyprus') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Morocco') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Egypt') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Libya') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Ethiopia') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Djibouti') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Uganda') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Rwanda') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Bosnia and Herzegovina') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Macedonia') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('Trinidad and Tobago') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;
insert into countries(name) values ('South Sudan') ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name;