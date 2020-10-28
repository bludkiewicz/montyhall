# montyhall

https://en.wikipedia.org/wiki/Monty_Hall_problem

This is a Spring Boot Application that simulates rounds of the Monty Hall Game.

The simulations are triggered through two RESTful Endpoints.  Each requires two parameters:
- $(iterations): integer value of the number of rounds to simulate
- $(switch): boolean value of whether to switch your initial selection

The first endpoint can be accessed with an HTTP GET method,
and the parameters are sent as path variables:

GET /api/play/$(iterations)/$(switch) 

The second endpoint can be accessed with an HTTP POST method,
and the parameters are sent as JSON in the body:

POST /api/play/<br>
{ "iterations": $(iterations), "switch": $(switch) }

Please note both endpoints run on port 8080 by default.

As there can be Integer.MAX_VALUE iterations, you may not want to show individual results after a certain threshold.
That can be set in application.properties as output.max_results_to_show.  If not set it will default to 1000.

To create an executable jar:
`mvn package`

To run the application:
`java -jar montyhall.jar`

JaCoCo is used to verify test code coverage.

This application logs the output on info, and it uses emojis to show results.
Please be aware that your runtime environment will need emoji support to display properly.