insert into user
(user_id, user_name, phone, role, is_active)
values
(111, '김덕배', '11', 'AGENT', true),
(222, '박숙자', '12', 'OTHER', true),
(333, '오정미', '13', 'OTHER', true),
(444, '황성욱', '14', 'AGENT', true),
(555, '이정호', '15', 'OTHER', true);

insert into estate
(
estate_id, estate_name, address1, address2, address3
, owner, area, contract_type, estate_type
, market_min_price, market_max_price
, owner_min_price, owner_max_price
, status, is_active
)
values
(101, '아이파크', '분당구', '정자동', '57-1', 111, 100.59, 'RENT', 'VILLA', 100, 1000, 900, 1200, 'ONSALE', true),
(202, '쉐르빌', '분당구', '수내동', '12-1', 111, 200.59, 'SALE', 'VILLA', 100, 700, 900, 1200, 'ONSALE', true),
(303, '파빌리온', '분당구', '백현동', '7-3', 222, 300.59, 'RENT', 'APT', 100, 1000, 900, 1200, 'SOLD', true),
(404, '현대아파트', '북구', '장성동', '5-4', 444, 400.59, 'SALE', 'LAND', 100, 1000, 900, 1200, 'UNDERSALE', true),
(505, '세운상가', '남구', '효자동', '7-11', 111, 500.59, 'MONTH', 'STORE', 100, 1000, 900, 1200, 'ONSALE', true);

insert into bid 
(bid_id, is_active, created_date, modified_date, action, max_price, min_price, status, estate, user)
 values 
 (3, true, null, null, 'BUY', 20, 10, null, 505, 222);
