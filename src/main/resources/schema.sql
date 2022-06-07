drop table if exists orders_detail;

drop table if exists orders;

drop table if exists cart_item;

drop table if exists product;

drop table if exists customer;

create table customer
(
    id       bigint       not null auto_increment,
    username varchar(255) not null unique,
    password varchar(255) not null,
    nickname varchar(255) not null,
    age      integer      not null,
    primary key (id)
) engine=InnoDB default charset=utf8mb4;

-- alter table customer
--     add unique key (username);

create table product
(
    id        bigint       not null auto_increment,
    name      varchar(255) not null,
    price     integer      not null,
    thumbnail varchar(255),
    primary key (id)
) engine=InnoDB default charset=utf8mb4;

create table cart_item
(
    id          bigint not null auto_increment,
    customer_id bigint not null foreign key references customer(id),
    product_id  bigint not null foreign key references product(id),
    quantity    integer default 0,
    primary key (id)
) engine=InnoDB default charset=utf8mb4;

-- alter table cart_item
--     add constraint fk_cart_item_to_customer
--         foreign key (customer_id) references customer (id);

-- alter table cart_item
--     add constraint fk_cart_item_to_product
--         foreign key (product_id) references product (id);

create table orders
(
    id          bigint not null auto_increment,
    customer_id bigint not null foreign key references customer(id),
    primary key (id)
) engine=InnoDB default charset=utf8mb4;

-- alter table orders
--     add constraint fk_orders_to_customer
--         foreign key (customer_id) references customer (id);

create table orders_detail
(
    id         bigint  not null auto_increment,
    orders_id  bigint  not null foreign key references orders(id),
    product_id bigint  not null foreign key references product(id),
    quantity   integer not null,
    primary key (id)
) engine=InnoDB default charset=utf8mb4;

-- alter table orders_detail
--     add constraint fk_orders_detail_to_orders
--         foreign key (orders_id) references orders (id);

-- alter table orders_detail
--     add constraint fk_orders_detail_to_product
--         foreign key (product_id) references product (id);

INSERT INTO product (name, price, thumbnail)
VALUES (싱싱한 감자, 10000, https://storybook.takealook.kr/image/potato.jpg);
INSERT INTO product (name, price, thumbnail)
VALUES (안싱싱한 감자, 10000, https://storybook.takealook.kr/image/potato.jpg);
INSERT INTO product (name, price, thumbnail)
VALUES (약간싱싱한 감자, 10000, https://storybook.takealook.kr/image/potato.jpg);
INSERT INTO product (name, price, thumbnail)
VALUES (맛있는 감자, 10000, https://storybook.takealook.kr/image/potato.jpg);

