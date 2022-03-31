[![CircleCI](https://circleci.com/gh/jucron/restaurant-backend/tree/master.svg?style=svg)](https://circleci.com/gh/jucron/restaurant-backend/tree/master)

[![codecov](https://codecov.io/gh/jucron/restaurant-backend/branch/master/graph/badge.svg?token=O0BKY0JHG4)](https://codecov.io/gh/jucron/restaurant-backend)

# Restaurant Backend Rest Application

A restaurant application in which clients, waiters and kitchen staff can interact together. Tables are checked-in, orders can be processed and communicated to the cooks. 
This is the backend piece, used to provide endpoints and send REST response to the frontend. This application was created entirely by Test-Driven-Development technique.


It is more properly to Test using the Postman with Authorization Header:
[Postman api-rest-security Workspace](https://go.postman.co/workspace/My-Workspace~b20ca6f3-aa32-48c5-8c9c-bb8dc06af4dd/collection/18570764-335e581f-b4ff-4667-a0e8-d4f2b15456c9)

### Motivation:

This project is a great opportunity to practice complex database modeling and object-oriented design. A restaurant management app is a real-world challenge worth solving.  

### Website of the App:
>_todo: link (work in progress)_

### Documentation
Check the Swagger Api Documentation: [_work in progress_]

## Features of this API 
### Client management
* View list of all clients and their details
* Check-in a new client by assigning a Table number and Order
* Verify Client's Order and get the list of consumption
* Check-out a client and leave important data for future analysis
### Order management
* Verify active Orders so that staff can take action
* Assign a waiter to an Order
* Assign a cook to an Order
### Table management 
* Create a Table with a unique number
* View list of all tables and their status
* Assign a Waiter to an 'OPEN' Table

Note: Table status is managed by Client check-in/out
### Menu management _(work in progress)_
* Create a new Menu with a name (for example: "dinner menu") 
* View list of Menu's
* Fetch a Menu by its name

Note: Menu will hold the time registry in which the last Meal/Beverage was created
### Meals and Beverages management _(work in progress)_
* Create a Meal/Beverage and associate it with an existing Menu
* Take Client's Order: assigning a Meal/Beverage to An Order from an existing Menu
* Remove a Meal/Beverage from an Order
* Delete a Meal/Beverage, also removing its Menu's reference
### Workers management _(work in progress)_
* Create a worker, with type (Waiter, Cook, etc.), and associate with a Login account
* Deactivate a worker
### Login management _(work in progress)_
* Workers are able to create an account with unique username and e-mail
* Password safely encoded and stored in database
* Password reset of any worker login account
* Option to update Name and Password


### Database Diagram
For the database modeling, a diagram with each table and relationships was created for the SportsBuddy application:
<img src="https://user-images.githubusercontent.com/79875515/161033714-b250f6e0-665c-4028-898f-d6922cee06bf.png" width=75% height=75%>

## Getting Started
### Data Initialization _work in progress_
* Use the file [configure-postgres.sql](https://github.com/jucron/SportsBuddy/blob/master/src/main/resources/scripts/configure-postgres.sql) in order to give your PostGresSQL database the necessary configuration. This way we can properly use the different profiles: `dev` and `prod`.
* Use the file [start_data-postgres.sql](https://github.com/jucron/SportsBuddy/blob/master/src/main/resources/scripts/start_data-postgres.sql) to initialize tables and constraints in your PostGresSQL database. So just run the application for the first time, it will automatically input data if not existent.

### Via Docker _work in progress_
1. After changes run `docker build -t restaurantbackend .` in a project root directory to build application image.
2. After image is fully built, run `docker run -d -p 8080:8080 restaurantbackend` to start the image
3. With a browser, access the app via http://localhost:8080/
4. (Optional) run `docker logs -t <container>` to see the logs
#### Notes:
Database for testing is in-memory type, for fast and convenient usage. 
## Conceptual Design
> Object Oriented Programming

> Dependency Injection (Design pattern)

> MVC Structure (Model, View, Controller)

> Test-Driven-Development

> CI/CD

>REST API
## Technology
- Java
- Spring-Boot
- Java Persistence API (JPA)
- Hibernate (object relational mapping)
- Maven (build, dependencies)
- JUnit
- Mockito
- Spring Security (JWT Token)
- Rest Controller
- Code coverage
- CircleCI 
