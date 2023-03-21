# Getting Started

#### Building

Prerequisites:  JDK 17, Gradle version at least 7.4.1

From project home directory run following command:
```js  
  ./gradlew clean build
```

If test coverage report is needed run following command

```js  
./gradlew clean build jacocoTestReport
```

The test report can be then located under location: /build/jacocoHtml/index.html

#### Running spring boot app

Prerequisites:

Set environment variable <mark>GIT_HUB_API_TOKEN</mark>

From project home directory run following command:

```js   
  java -jar ./build/libs/reporetriever-0.0.1-SNAPSHOT-plain.jar
```
Upon start the app will be available by the url: http://localhost:8080/info/


#### Building a Docker image

From project root directory execute following commands to build an image and run service docker container:

```js
 docker build -t reporetriever .                                                                                          
    
 docker run -p 8080:8080 reporetriever \
  -e GIT_HUB_API_TOKEN='<KEY_VALUE>'                                                                              

```
Actuator URL:
http://localhost:8080/reporetriever/actuator

Swagger URL:
http://localhost:8080/reporetriever/swagger-ui/index.html

Open API docs:
http://localhost:8080/reporetriever/v3/api-docs