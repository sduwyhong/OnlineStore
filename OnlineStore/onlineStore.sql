create database onlineStore;
use onlineStore;
create table categories(
	id varchar(100) primary key,
	name varchar(100) not null unique,
	description varchar(255)
);
create table books(
	id varchar(100) primary key,
	name varchar(100) not null unique,
	author varchar(100) not null,
	price float(8,2) not null,
	path varchar(100),
	fileName varchar(100),
	description varchar(255),
	categoryId varchar(100),
	constraint category_id_fk foreign key (categoryId) references categories(id)
);
create table customers(
	id varchar(100) primary key,
	username varchar(100) not null unique,
	password varchar(100) not null,
	nickname varchar(100) not null
);
create table customers(
	id varchar(100) primary key,
	username varchar(100) not null unique,
	password varchar(100) not null,
	nickname varchar(100) not null,
	email varchar(100) not null,
	code varchar(100) not null,
	actived bit(1)
);
create table orders(
	id varchar(100) primary key,
	quantity int,
	price float(8,2),
	status int,
	customerId varchar(100),
	constraint customer_id_fk foreign key (customerId) references customers(id)
);
create table orderItems(
	id varchar(100) primary key,
	quantity int,
	price float(8,2),
	bookId varchar(100) not null,
	orderId varchar(100) not null,
	constraint book_id_fk foreign key (bookId) references books(id),
	constraint order_id_fk foreign key (orderId) references orders(id)
);