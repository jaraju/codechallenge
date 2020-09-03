# codechallenge

This is the solution to find whether there is a connectivity existing between two given cities (Source , Destination).  A REST get operation called /connected will respond back to us with either yes/no.


    • Clear differentiation is maintained using package structure for controller, and service classes.


    • A contract has been provided using interfaces that will be extended by service classes.


    • Service classes :ConnectCityServiceImpl.java is having the main responsibility to execute business logic to find if there is a route existing either directly or indirectly between the two given cities by taking help from another service class LoadConnectCityDataService.java whose responsibilities include loading the routes from the text and maintain in the connectivity map so that ConnectCityServiceImpl.java’s logic can be applied to find the connectivity.


    • City connectivity information will be loaded to the map only once with the first request and all the sub sequent requests will use same.
      
      
    • Plugins or dependencies used : 
     
        ◦  Maven for building, test and running.
        
        ◦ Swagger for viewing the coverage (Swagger-2 and Swagger-UI)
        
        ◦ Jacoco maven plugin for gathering the code coverage data.
        
        ◦ Junit for unit testing


Examples:

yes  –> http://localhost:8080/connected?origin=Boston&destination=Newark

yes  –> http://localhost:8080/connected?origin=Boston&destination=Philadelphia

no   –> http://localhost:8080/connected?origin=Philadelphia&destination=Albany


Build, execute test and generate coverage using : mvn clean install

Run Project using : mvn spring-boot:run 

## Coverage

```shell
Please execute  mvn clean install  and note that coverage is > 90 % and find the same with : codechallenge/target/site/jacoco/index.html
```

## Documentation
Please use [Swagger UI](http://localhost:8080/swagger-ui.html) for trying connected get call.

Click here for : [API Documentation](http://localhost:8080/v2/api-docs)