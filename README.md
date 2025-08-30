# ABOUT THIS REPOSITORY

Ready-to-use mock data server. You can quickly create HTTP API's which returns mock data.

<br><br><br>

## Usage (How to create HTTP API with mock data)

There are 3 way to generate mock data:

- ### 1 - RandomDataGenerator

  You can generate fake response with random data.
  
  See this DEMO method: ["MockServerDemoController class --> simpleFakeData() method"](./src/main/java/com/demo/mockservice/controller/MockServerDemoController.java#L29)

<br>

- ### 2 - Dynamic Response

  The server will response you with the data which you send it in a special field.

  You don't even need to write a code for that.

  See this DEMO method: ["MockServerDemoController class --> dynamicResponseXML() method"](./src/main/java/com/demo/mockservice/controller/MockServerDemoController.java#L53)

<br>

- ### 3 - Response from file

  You can return any file as a response.

  See this DEMO method: ["MockServerDemoController class --> resultFromFile() method"](./src/main/java/com/demo/mockservice/controller/MockServerDemoController.java#L41)

<br><br><br>

## HTTP Request and Response Logs

You can check the request and response from:

- Only latest 100 request and response:

  <http://localhost:8088/actuator/httpexchanges>

- Server console logs

<br><br><br>

## Swagger Docs

You may need to test mock services directly from HTML (Web) UI of Swagger.

- Swagger HTML (Web) UI

  <http://localhost:8088/swagger-ui.html>
