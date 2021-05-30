create table if not exists users (id int AUTO_INCREMENT,
name string,
phone_number varchar(20),
 PRIMARY KEY (`id`)
);

create table if not exists goods ( id int AUTO_INCREMENT,
name string,
price double,
description string,
status int,
 PRIMARY KEY (`id`)
)


create table if not exists orders (
id int AUTO_INCREMENT,
user_id int,
commodities string, --商品列表
status int,
total_prices double,
create_time int,
update_time int,
PRIMARY KEY (`id`),
foreign key (user_id) references users(id)
);

