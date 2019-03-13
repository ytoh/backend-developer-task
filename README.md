Simple web app for storing and retrieving GPS data enriched by nearby places retrieved from here.com as described in [assignment](assignment.pdf)

## Build

```
> ./gradlew clean build
```

## Run

```
> java -Dhere-api.app-id=<your app id> -Dhere-api.app-code=<your app code> -jar build/libs/databreakers-developer-task-0.0.1-SNAPSHOT.jar
```

## Use

Insert testing data:

```
> curl -s -H "Content-type: application/xml" -d @src/test/resources/example.gpx http://localhost:8080/routes
```

List all routes:

```
> curl -s http://localhost:8080/routes
```

Note: Listing includes pagination and HAL links.

Retrieve route detail:

```
> curl -s http://localhost:8080/routes/1
```
