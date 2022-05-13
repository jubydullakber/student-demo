# Demo  App
 Its a demo Student crud Application.
 There are 5 API's for doing crud operation like get,get all,create,update and delete.

# Steps to Setup
# Clone the application 
 https://github.com/jubydullakber/student-demo.git

 open src/main/resources/application.properties

 change spring.datasource.url,spring.datasource.driverClassName,spring.datasource.username and spring.datasource.password as per your DB installation

# Build and run the app using maven

  mvn clean install
  java -jar target/demo-0.0.1-SNAPSHOT.jar
  Alternatively, you can run the app without packaging it using -

  mvn spring-boot:run
  The app will start running at http://localhost:8100

# Api Documentation
 For Api Documentation used Open API url http://localhost:8080/swagger-ui/index.html

# Postmant Collention
 Postman collection also find in this repo[student].

# Container
For Containerize used Docker.can run with command docker-compose up.

# Trace
For distributed tracing used zipkin server.url : http://localhost:9411/zipkin/

## Explore Rest APIs
* Find all active student /api/v1/student
* Find active student by name /api/v1/student/{name}
* Create a student :8080/api/v1/student
* Update a student/api/v1/student/{name}
* Delete a student /api/v1/student/{name}
