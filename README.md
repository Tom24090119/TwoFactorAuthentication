# Two-factor Authentication Using Spring Security, Spring Data JPA, Spring Boot

## Some other dependencies used 

## 1. Lombok : to generate boiler plate code
## 2. MySQL Driver : To store details of users in MYSQL

## Process 
    1. User with first send username and password to the server.
    2. Server will try to authenticate using the username and password that was sent.
    3. On successfull username and password authentication, server will send back a response saying that otp and been 
        sent.
    4. User will again send a request this time with OTP that can sent back to user. 
    5. Server will try to authenticate the OTP sent and the otp that exists in the database.
    6. If this authentication is successful then user is directly sent to the controller
    7. Else error response is sent back to user.


