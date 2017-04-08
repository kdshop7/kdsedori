-- amazon_item 
create table amazon_item ( 
id int not null auto_increment primary key, 
asin varchar(10) unique, 
title varchar(512), 
browse_node varchar(32) , 
sales_rank int, 
price int, 
lowest_new_price int, 
lowest_used_price int, 
yahoo_auction_url varchar(2048), 
yahoo_auction_hit_count int, 
total_used int, 
memo varchar(2048), 
created datetime, 
updated datetime 
);

