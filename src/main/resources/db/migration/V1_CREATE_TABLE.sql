create table hibernate_sequence (
    next_val bigint
) engine=MyISAM;

insert into hibernate_sequence values ( 1 );
insert into hibernate_sequence values ( 1 );

CREATE TABLE currency (
    id BIGINT   NOT NULL AUTO_INCREMENT,
    id_number   varchar (64) not null,
    symbol      varchar (64) not null,
    price_usd   varchar (64) not null,
    PRIMARY KEY (id)

) ENGINE = MyISAM;

INSERT INTO currency(id_number, symbol, price_usd)
VALUE ('80', 'ETH', '2893.49'),
       ('90','BTC', '42027.50'),
       ('48543', 'SOL', '92.08');



CREATE TABLE user (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name        varchar (64) not null,
    symbol      varchar (64) not null,
    save_price  varchar (64) not null,
    complete    BOOLEAN NOT NULL
    PRIMARY KEY (id)

)    ENGINE = MyISAM;