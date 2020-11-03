# montyhall

This is a Spring Boot Microservice that simulates rounds of the Monty Hall Game.

https://en.wikipedia.org/wiki/Monty_Hall_problem

The simulations are triggered through two RESTful Endpoints.  Each requires two parameters:
- $(iterations): integer value of the number of rounds to simulate
- $(switch): boolean value of whether to switch your initial selection

The first endpoint can be accessed with an HTTP GET method,
and the parameters are sent as path variables:

GET /api/play/$(iterations)/$(switch) 

The second endpoint can be accessed with an HTTP POST method,
and the parameters are sent as JSON in the body:

POST /api/play/<br>
`{
    "iterations": $(iterations),
    "switch": $(switch)
}`

Both endpoints return a JSON response:<br>
`{
    "wins": $(wins), 
    "attempts": $(attempts),
    "results": [{
        "doorOne": $(door),
        "doorTwo": $(door),
        "doorThree": $(door),
        "originalChoice": $(choice),
        "selectedChoice": $(choice),
        "win": $(result)
    }]
}`

$(wins) and $(attempts) are integer values of the total number of wins and attempts for the request.<br>
$(door) is a string value of whether a car or goat was behind a door for each individual attempt.<br>
$(choice) is an integer value of a door number for each individual attempt.<br>
$(result) is a boolean value of whether the game was won or lost for each individual attempt.<br>

This application also logs the output using a fixed width format on info, and it uses emojis to show results.
Please be aware that your runtime environment will need emoji support to display logs successfully.

As there can be Integer.MAX_VALUE iterations, you may not want to show individual results after a certain threshold.
That can be set in application.properties as output.max_results_to_show.  If not set it will default to 1000.
The JSON response will contain an empty results array if this limit is reached.
Otherwise, it will have an entry for each attempt.

A sample front end UI is also included in this application.  It can be reached at the context root:

GET /

Please note this application runs on port 8080 by default.

To create an executable jar:
`mvn package`

To run the application:
`java -jar montyhall.jar`

JaCoCo is used to verify test code coverage.