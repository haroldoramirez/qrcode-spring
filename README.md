# Instagram Integration API with Spring

To follow the steps in this tutorial, you will need the correct version of Java and sbt. The tutorial requires:

* Java Software Developer's Kit (SE) 21
* Maven 3.6.3

To check your Java version, enter the following in a command window:

```bash
java -version
```

To check your sbt version, enter the following in a command window:

```bash
mvn -version
```

If you do not have the required versions, follow these links to obtain them:

* [Java SE](https://docs.aws.amazon.com/corretto/latest/corretto-17-ug/downloads-list.html)
* [Maven](https://maven.apache.org/download.cgi)

## Build and run the project

The project was created with [Spring Initializr](https://start.spring.io/) 

To build and run the project:

1. Use a command window to change into the example project directory, for example: `cd qrcode-spring`

2. Build the project. Enter: `mvn clean install` or `.\mvnw clean install`. 

4. Run with: `java -jar target/registros-1.0.0-SNAPSHOT.jar` The project starts the embedded HTTP server. Since this downloads libraries and dependencies, the amount of time required depends partly on your connection's speed.

5. After the message `br.com.registros.RegistrosApplication : Started RegistrosApplication, ...` displays, enter the following URL in a browser: <http://localhost:8080>

The Spring responds: Index Page!

## Informações Adicionais

N/A
