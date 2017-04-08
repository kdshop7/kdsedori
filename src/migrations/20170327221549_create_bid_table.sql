-- bid 
create table bid ( 
id int not null auto_increment primary key, 
asin varchar(10) unique, 
sales_price int, 
shipping_costs int, 
memo varchar(2048), 
created datetime, 
updated datetime 
);

