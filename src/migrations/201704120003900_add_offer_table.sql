
create table offer ( 
 id int not null auto_increment primary key,
 asin varchar(10) ,
 cond varchar(20),
 price int,
 shipping_costs int,
 is_fba int default 0,
 no int,
 last_crawled dateTime,
 created dateTime,
 updated dateTime
);


