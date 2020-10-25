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