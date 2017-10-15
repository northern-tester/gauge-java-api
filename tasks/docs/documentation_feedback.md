_High level Improvements_

* Code snippets in the languages that your integrators tend to use, helps to guide efficient integration too, plus encourage good practice such as OPTIONS calls before other verbs.
* Overall principles for development on the front page would be nice
    * Such as endeavouring to sort arrays server side rather than asking the client to do it
    * Or only exposing the data needed to fulfil statelessness  
    * Or what the versioning strategy is
* Provide contacts details with a common issues section to repel repeat askers

_Specific Documentation Improvements_

_Homepage_

* the madeup bullet point gives the http status code, not the description, its trivial to do both, so tabulate this sections  with url, description and code expected.
* there are two unauthorized urls which APPEAR the same when you navigate to it
* the system health endpoint has the http verb indicator, whereas the rest do not, is there value in showing all verbs available on each endpoint for quick reference?

_Checking Individual Pages_

* Code and description match this oracle - https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html

* 204 No Response
    * This should be 204 No Content, as there is a response.
    * The GET/internal_server_error documentation response section is Status 200 No Content, should be Status 200 OK

* 500 Internal Server Error
    * The GET/internal_server_error documentation response section is Status 500 Internal Server Error, should be Status 200 OK
* 400 Bad Request 
    * Could have an enhanced description: The request could not be understood by the server due to malformed syntax. The client SHOULD NOT repeat the request without modifications.
    * The description for POST/bad_request indicates a 503 would be returned
* 403 Forbidden
    * Again the description could do more than repeat the code itself: The server understood the request, but is refusing to fulfill it.
    * The GET/forbidden documentation response section is Status 200 Forbidden, should be Status 200 OK
* 503 Gateway timeout
    * A 503 is a service unavailable not a gateway timeout (504)! A POST request itself returns a 503 Service Unavailable. I would consult to see which is wrong, docs or endpoint itself
    * The GET/gateway_timeout documentation response section is Status 200 Gateway timeout, should be Status 200 OK
* 401 Unauthorised
    * The json example in the GET/unauthorised/last documentation is incomplete. The actual endpoint returns more data.
    * {"unauthorized": [{"lastUpdated": "2017-03-30T14:54:23Z"}, {"mediaTypeUsed": "application/vnd.json","bodyReceived": {"test": 123}}]} is returned
    * Only {"unauthorized": {"lastUpdated": "2017-03-30T14:54:23Z"}} is shown in the docs on this page


* Healthcheck Page
    * Doesn't really tell me what is healthy? Receiving a 200 back is only one possible indicator of health, some diagnostics would be good, Disk space, I/O, CPU or even response time against a benchmark.

_Other_

* When returning any documentation route, the container logs report this:

	```
	2017-10-13T18:27:01.192959559Z  Cannot render console from 172.17.0.1! Allowed networks: 127.0.0.1, ::1, 127.0.0.0/127.255.255.255
	```

* This is a problem with rendering the Rails Web Console based on the IP of the host machine being disallowed in config and can be turned off with the nicely named config.web_console.whiny_requests = false