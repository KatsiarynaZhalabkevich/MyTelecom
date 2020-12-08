# My Telecom
Application for regular users to manage account (choosing plans, traffic, adding balance, updating profile info) and admin (registration, managing plans, promotions, sales etc; blocking users; getting statistics information)
# TelegramBot

### Technologies:
> Java 14, Apache Tomcat 9, JDBC,MySQL 8, Java EE (Java Servlets, JSP, JSTL), Log4j2, Junit, JavaScript (data validation on page), CSS, Bootstrap (page layout), Maven.

### Start the app:
> http://localhost:8080/MyTelecom/
### App
#### User role
> - Registration: http://localhost:8080/MyTelecom/users/new
> - Tariffs for Internet: http://localhost:8080/MyTelecom/tariffs
> - A private page: http://localhost:8080/MyTelecom/users/user
> - Edit profile page: http://localhost:8080/MyTelecom/users/user/edit
#### Admin role
> - Admin page: http://localhost:8080/MyTelecom/users/admin
> - Tariffs for Internet (all, create, update, delete): http://localhost:8080/MyTelecom/users/admin/tariffs
> - A private page (as ordinal user): http://localhost:8080/MyTelecom/users/user
> - Users page (block, update balance, delete, withdraw payment for a month): http://localhost:8080/MyTelecom/users/user/tariffs
> - User's tariffs page: http://localhost:8080/MyTelecom/users/user/tariffs
> - Statistic page: http://localhost:8080/MyTelecom/users/admin/statistic
> - Promotions page (not ready): http://localhost:8080/MyTelecom/promotions
## Main Settings
### JDBC:
> src/main/resources/db.properties
### SQL scripts:
> src/main/resources/sql
### Logins and passwords:
> - User: user5 123456
> - Admin: admin 123456
> - BD: telecom_user 123456
## Test Settings
### JDBC:
> src/main/resources/db.properties
### SQL scripts:
> src/main/resources/sql


