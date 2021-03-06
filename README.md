CREDIT SYSTEM
-
*Before You Use the App, make sure you have read everything, especially the "How To Run The App".*

This app's main function is to gather customer credit requests, calculate customers credit limits and send 
them SMS notification as well as returning credit limit status.

**The app requests :**
* Identification Number (As main parameter)
* Customer Name, Surname
* Telephone Number (Unique credential)
* Monthly Income

The Customer declares these basic information inputs on sign-up step. The app defines a random credit score value. Once 
the information is complete, app calculates credit limit and whether the customer can get a loan. If result is positive, 
customer gets notified with SMS.

**Credit Calculation Rules :**
* If Customers credit score is below 500 -> then his credit request is rejected.
* If Customers credit score is above 500 and his income is below 5000 -> Customer gets credit limit of 10.000.
* If Customers credit score is above 500 and his income is above 5000 -> Customer gets credit limit of 20.000.
* If Customers credit score is above 1000 -> Customers credit limit gets calculated by Customers Income power of Credit 
Limit Multiplier, which is 4 by default.


Requirements And Tech Used
-
You will need to clone the SMS Sender app. They should work at the same time:
* https://github.com/AerenN/PayCore-Project-SMS-Sender

Java 11, H2 DB, Maven, Hibernate, RabbitMQ, Postman

Optional: Docker. Html pages lack configuration.

Junit5, Mockito ve assertJ used in testing. H2 is simple, easy setup and doesn't keep space.
RabbitMQ has just one job to get event. Nothing confusing.

Ports:
* App: p 8080
* SMS Service : p 9000
* RabbitMQ : 5672 / 15672

How To Run The App
-
Clone everything in the repository. You will need Postman for making requests and getting responses. Make sure the SMS
Sender app is running and RabbitMQ service is up.

**Key Points**:
* Keep the *data.sql* file, it contains roles for RoleRepository. App will not be able to define roles without it.
* I highly recommend you to import *Credit Calculator & Request.postman_collection.json* which contains essential
  controller paths with parameter and body examples.
* When you sign-in, if the token doesn't get saved automatically, you can find it in the response *header* -> ***Cookies***
* I deliberately commented out *@Size* or numeric like validations. You can set user credentials as "1" and test quicker.
  Just don't leave blank.


![Inspirational-Wallpaper(1)](https://user-images.githubusercontent.com/81401869/155888882-2a0b48c0-58d0-410b-8ee5-812c4c549eb6.jpg)


How Does The App Work?
-

The app starts with sign-up request where user defines basic information and role.
* *firstNameLastName* : Customer Name.
* *username* : Identification Number as this will be main query input.
* *phoneNumber* : Unique phone number.
* *income* : A double value of monthly income.
* *password* : Required for authentication.
* *role* : Choose one of ["user"],["staff"],["admin"]

The roleRepository contains 3 roles as defined above. Customer information gets saved in database with this information.
Once you have successfully created an account, you will need to sign-in with your username and password. The 
app will generate you a JWT, you can get the token from header in the response. You will need that token for 
rest of the requests.

When Customer send credit request with username, CreditScoreService generates 1 of 3 possible cases of credit score. So 
creditScore will be one of **[350, 750, 1250]** for test purposes. CreditCalculationService gets creditScore, checks given 
income and on approval -> sends CreditApprovedEvent to RabbitMQ and returns credit requested boolean True. 
This request saved to CreditTable in the database with help of CreditLimitRepository.

On the helper SMS Service App, SMS Service listens and with a consumer, gets the CreditApprovedEvent and runs single 
method of SendSMS, which posts message.

The credit result can be checked with username in main app.


********************************************************************

Thank You!
