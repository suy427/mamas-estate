# mamas estate  
[![Build Status](https://travis-ci.org/suy427/mamas-estate.svg?branch=master)](https://travis-ci.org/suy427/mamas-estate) [![Coverage Status](https://coveralls.io/repos/github/suy427/mamas-estate/badge.svg?branch=master&service=github)](https://coveralls.io/github/suy427/mamas-estate?branch=master)  
    
simple management service for real-estate

## Tech Stack
* Java (1.8)
* Spring boot (2.2.2 RELEASE)
* QueryDSL
* JPA(Hibernate) - Spring Data Jpa (2.2.4 RELEASE)  
* Elasticsearch (future)
* Logstash (future)


## Modules  
- #### mamas-search
  * basic client, estate CRUD service
  * REST API with Spring Boot
    
- #### mamas-insight (future)
  * aggregation and visualization with ELK
---------------------------
## Features
#### client management
* basic client information CRUD
* client search
  - with basic personal information (name, phone number etc..)
  - with activity tendency (trade tendency, possession tendency etc..)

#### estate management
* basic estate information CRUD
  - include actual picture of estate (future)
  - linked with actual map (future)
  
* estate search
  - with basic estate information (name, address, area, type, owner, price etc..)
  - with trade tendency (local tendency, price tendency etc..)

#### history management (bid, contract)
* basic history data CRUD
* history search
  - with specified estate, user information
    

#### aggregations (future)
* client data
  - sailing tendency, purchase tendency, trade success tendency etc..

* estate data
  - bid tendency, local tendency, distance tendency etc..
---------------------------
## EndPoint
* base-url ```/mamas```  
  
#### REST API  
  
##### ```/users```, ```/estates```, ```/contracts```, ```/bids```
* create user info   
```http
POST /users/user 
```
```json
{
  "name" : "user name",
  "phone" : "phone number",
  "role" : "user role"
}
```
* search user info   
```http
GET /users 
```
```json
{
  "name" : "user name",
  "phone" : "phone number",
  "role" : "role",
  "bidDate" : {
    "minimum" : "bid date start",
    "maximum" : "bid date end"
  },
  "page" : "1",
  "size" : "10",
  "sort" : [
    {
      "property" : "name", 
      "direction" : "ASC or DESC"
    },
    {
      "property" : "bidDate.mimimum",
      "direction" : "ASC or DESC"
    }
  ]
}
```
* get user info
```http
GET /users/user_id 
```
* update user info
```http
PUT /users/user_id
```
```json
  {
    "name" : "user name",
    "phone" : "phone",
    "role" : "user role"
  }
```
* delete user info (soft delete)
```http
PUT /users/user_id
```

* delete user info (hard delete)
```http
DELETE /users/user_id
```

* The rest(estates, contracts, bids) is the same way
