NoResponse
==========

tags: no_response

This spec file details the interaction with the no_response endpoint and the expected results
of those actions
     
No Response is returned when doing a POST to the /no_response endpoint
----------------------------------------------------------------------

* Post to the "no_response" endpoint
* Then the response will be "No Content"
* The response code should be "204"

OK is returned when doing a GET to the /no_response endpoint
------------------------------------------------------------

* Get to the "no_response" endpoint
* Then the response will be "OK"
* The response code should be "200"

Details of the last response is returned when doing a GET to /no_response/last endpoint
---------------------------------------------------------------------------------------

* Get to the "no_response/last" endpoint
* Then the response content-type will be "application/json; charset=utf-8"
* The response body for "no_response" should contain "application/json" and "123"

