Project Title

-------------------------

Zissa Inventory System springboot application



Prerequisites
--------------------------

For building and running the application you need:

JDK 1.10
Maven 3.5.4
spring boot 2.0.5.RELEASE
Mysql 5.1



Ways to run the application

---------------------------


1)Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the main method in the de.codecentric.springbootsample.Application class from your IDE.

Alternatively you can use the Spring Boot Maven plugin like so:

mvn spring-boot:run



2) Build an executable JAR

You can run the application from the command line using:

mvn spring-boot:run

Or you can build a single executable JAR file that contains all the necessary dependencies, classes, and resources with:

mvn clean package

Then you can run the JAR file with:

java -jar target/*.jar