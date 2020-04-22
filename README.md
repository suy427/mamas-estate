# mamas estate  
[![Build Status](https://travis-ci.org/suy427/mamas-estate.svg?branch=master)](https://travis-ci.org/suy427/mamas-estate) [![Coverage Status](https://coveralls.io/repos/github/suy427/mamas-estate/badge.svg?branch=master)](https://coveralls.io/github/suy427/mamas-estate?branch=master)  
    
simple management service for real-estate

   
## Tech Stack
* Java (1.8)
* Spring boot (2.2.2 RELEASE)
* JPA (Hibernate)
* QueryDSL
* Spring Data Jpa (2.2.4 RELEASE)  
* Elasticsearch
* Logstash


## Modules  
- #### mamas-search
  * basic client, estate CRUD service
  * REST API with Spring Boot
    
- #### mamas-insight
  * aggregation and visualization with ELK
---------------------------

## Features
=======

#### client management
* basic client information CRUD
* client search
  - with basic personal information (name, phone number etc..)
  - with activity tendency (trade tendency, possession tendency etc..)

#### estate management
* basic estate information CRUD
  - include actual picture of estate
  - linked with actual map
    
* estate search
  - with basic estate information (name, address, area, type, owner, price etc..)
  - with trade tendency (local tendency, price tendency etc..)

#### aggregations
* client data
  - sailing tendency, purchase tendency, trade success tendency etc..

* estate data
  - bid tendency, local tendency, distance tendency etc..
 
