[![CircleCI](https://circleci.com/gh/jucron/spring5-mvc-rest/tree/master.svg?style=svg)](https://circleci.com/gh/jucron/spring5-mvc-rest/tree/master)
[![codecov](https://codecov.io/gh/jucron/spring5-mvc-rest/branch/master/graph/badge.svg?token=SCGLGCHWQ6)](https://codecov.io/gh/jucron/spring5-mvc-rest)

# Restaurant Backend Rest Application

A restaurant application in which clients, waiters and kitchen staff can interact together. Tables are checked-in, orders can be processed and communicated to the cooks. 
This is the backend piece, used to provide endpoints and send REST response to the frontend. This application was created entirely by Test-Driven-Development technique.


It is more properly to Test using the Postman with Authorization Header:
[Postman api-rest-security Workspace](https://go.postman.co/workspace/My-Workspace~b20ca6f3-aa32-48c5-8c9c-bb8dc06af4dd/collection/18570764-335e581f-b4ff-4667-a0e8-d4f2b15456c9)

### Motivation:

A restaurant application in which clients, waiters and kitchen staff can interact together. Tables are checked-in, orders can be processed and communicated to the cooks.
This is the backend piece, used to provide endpoints and send REST response to the frontend. This application was created entirely by Test-Driven-Development technique.

### Website of the App:
>_todo: link (work in progress)_

### Documentation
Check the Swagger Api Documentation: [_work in progress_]

## Features of this API _(work in progress)_
### Workers Login
* Workers are able to create an account with unique username and e-mail
* Password safely encoded and stored in database
* Credentials check and login with session
* Password recovery of any worker login account
* Option to update Name and Password

_todo: gif with login demonstration (work in progress)_

### Table management
* Check Tables that are available to assign new clients
* Active Tables numbers cannot be associated to new clients
### Client management
* Check-in a new client by assigning a table number and order
* Verify client's order and get list of consumption
* Check-out a client and leave important data for future analysis
### Order management
* Take Client's Order by assigning meals and beverages from an existing Menu
* Verify active Orders so that staff can take action
* Assign a waiter and a cook to an Order
### Menu management
* Create a new Menu with a name (for example: "dinner menu") 
* Create meals and beverages associated with this Menu


### Database Diagram
For the database modeling, a diagram with each table and relationships was created for the SportsBuddy application:
<img src="https://user-images.githubusercontent.com/79875515/159723248-5b927f7b-5ba7-465b-9b60-8484963e5ecf.png" width=75% height=75%>

## Getting Started
### Data Initialization
* Use the file [configure-postgres.sql](https://github.com/jucron/SportsBuddy/blob/master/src/main/resources/scripts/configure-postgres.sql) in order to give your PostGresSQL database the necessary configuration. This way we can properly use the different profiles: `dev` and `prod`.
* Use the file [start_data-postgres.sql](https://github.com/jucron/SportsBuddy/blob/master/src/main/resources/scripts/start_data-postgres.sql) to initialize tables and constraints in your PostGresSQL database. So just run the application for the first time, it will automatically input data if not existent.

### Via Docker
1. After changes run `docker build -t restaurantbackend .` in a project root directory to build application image.
2. After image is fully built, run `docker run -d -p 8080:8080 restaurantbackend` to start the image
3. With a browser, access the app via http://localhost:8080/
4. (Optional) run `docker logs -t <container>` to see the logs
#### Notes:
Database for testing is in-memory type, for fast and convenient usage. 
You won't be able to send the message via e-mail, unless account details are changed in application.properties file
## Technology
- Java
- Spring-Boot
- Java Persistence API (JPA)
- Hibernate (object relational mapping)
- Thymeleaf (Frontend java engine)
- Maven (build, dependencies)
- Testing
- Spring Security (Form based login)
- HTML
- Bootstrap
- Code coverage
- CircleCI
