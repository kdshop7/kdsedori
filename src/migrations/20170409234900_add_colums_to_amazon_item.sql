alter table amazon_item add image_url varchar(256);
alter table amazon_item add is_preorder int default 0;
alter table amazon_item add is_deleted int default 0;
alter table amazon_item add is_bid int default 0;
alter table amazon_item add is_success_bid int default 0;