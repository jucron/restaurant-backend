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
* View list of all clients with the chose Status (OPEN/CLOSED)
* Check-in a new client by assigning an existing Table and a new Order
* Check-out a Client by assigning a checkout-time and changing the Table, Order status

Note about Status: OPEN is the same as Active/In_Use. CLOSED is the same as Deactivated/Not_In_Use.
### Order management
* Fetch a Client's specific Order
* Assign a waiter to an Order
* Assign a cook to an Order
### Table management 
* Create a Table with a unique number
* View list of all tables and their status
* Assign a Waiter to an 'OPEN' Table

Note: Table status is managed by Client check-in/out
### Menu management 
* Create a new Menu with a name (for example: "dinner menu") 
* View list of Menu's
* Fetch a Menu by its name

Note: Menu will hold the time registry in which the last Meal/Beverage was created
### Consumption management
* Create a Consumption for a Client's Order with quantity and a Consumable 
* Update an existing Consumption's quantity
* Delete an existing Consumption
* Fetch Client's Consumption List and total value
### Consumable management 
* Create a Consumable, with type (like Meal/Beverage), name and value. Associate it with an existing Menu
* Delete a Meal/Beverage, also removing its Menu's reference
### Workers management _(work in progress)_
* Create a worker account, with:
  - type (Waiter, Cook, etc.);
  - any name;
  - Login account with unique username and password
* Deactivate a worker
### Login management _(work in progress)_
* Password safely encoded and stored in database
* Password reset the password of any worker login account

### Database Diagram
For the database modeling, a diagram with each table and relationships was created for the SportsBuddy application:
<img src="https://user-images.githubusercontent.com/79875515/165074101-f08285b1-23c3-4c1d-b42d-e5c54270631d.png" width=75% height=75%>

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
