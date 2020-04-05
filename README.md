# Mama's estate  
[![Build Status](https://travis-ci.org/suy427/mamas-estate.svg?branch=trash/rebaser)](https://travis-ci.org/suy427/mamas-estate) [![Coverage Status](https://coveralls.io/repos/github/suy427/mamas-estate/badge.svg?branch=trash/rebaser)](https://coveralls.io/github/suy427/mamas-estate?branch=trash/rebaser)  
    
simple management service for real-estate

## Tech Stack
* Java (1.8)
* Spring boot (2.2.2 RELEASE)
* JPA (Hibernate)
* QueryDSL
* Spring Data Jpa (2.2x.4 RELEASE)  
* Elasticsearch
* Logstash


## Modules  
- #### mamas-search
  *
- #### mamas-insight
  *
---------------------------
#### 고객관리
* 고객 CRUD
* 고객 검색 (이름, 번호, 역할)

#### 매물관리
* 매물 CRUD
* 매물 검색 (이름, 주소, 평수, 매물타입, 거래타입, 주인, 거래상태, 가격)
  * 사진, 지도 연동

#### 통계
* 고객통계
  * 많이 판매, 많이 구매, 최근 구매, 최근 내놓은사람, 매매자와 매수자 매칭(가격기준)

* 매물 통계
  * 호가 많은, 최근 나온, 동네정보, 매물간 거리
