update amazon_item set is_deleted = 1, updated = now() where asin in (select asin from bid where sales_price < 0);
