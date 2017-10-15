_Top Three Highlights_
=====================

* Consistency is key in API creation, integrators need a consistent experience. JSON structure across certain endpoints is inconsistent, as is some instances of naming.
* Data is often submitted in an incorrect and unsanitised state, the errors in the */last* endpoints with null and empty data suggest greater error handling required.

_Risks_
=======

* Couldn't login to the container to tail application logs, had to make do with container logs - tidied them up with docker logs --details --follow --timestamps b7c596b22fc3 to make them more explicit in format:
```
2017-10-13T17:52:13.533092558Z  Array values in the parameter to `Gem.paths=` are deprecated.
2017-10-13T17:52:13.533131330Z  Please use a String or nil.
2017-10-13T17:52:13.533140024Z  An Array ({"GEM_PATH"=>["/usr/local/bundle"]}) was passed in from bin/rails:3:in `load'
2017-10-13T17:52:15.052172009Z  [2017-10-13 17:52:15] INFO  WEBrick 1.3.1
```
* Access to a product owner as information emerged during testing, had to infer from single oracle 

_Application Wide Issues_
=========================

* The OPTIONS method is missing on endpoints, a useful pattern to offer it for integrators to check availability or capability before transaction.
* Consistency in POST method responses, minimise the repetition where possible, restating status codes can be removed.
* Performing last call GET methods on null POST's returns a parsing error. Depending on client side validation is a dangerous game unless explicity agreed.

_Individual Sessions on Endpoints_
==================================

_Approach_
==========
* Interrogate endpoints listed in documentation 
* Attempt to generate errors
* Check headers for consistency
* Tail container logs for feedback

_/madeup - Represents any endpoint which will return a 404 response_
====================================================================

* GET the /madeup endpoint on that exact URL
* GET the /supercalifragilisticexpialidocious to check a 404 on something other than /madeup
* The supercalifragilisticexpialidocious endpoint behaves the same as the madeup endpoint.

_/internal_server_error - Represents an internal_server_error response_
=======================================================================

* POST json to internal_server_error endpoint, response code is 500, original request body is returned in response receivedRequest element.
* POST invalid json to inter_servernal_error endpoint, response code is 400, handling a bad request.
* POST null json returns a 500 response code, but with no error. receivedRequest array in response is empty
* POST empty json returns a 500 response code, but with no error. receivedRequest array in response is empty
* OPTIONS and DELETE on /internal_server_error gives a 404 status code error
* GET to internal_server_error/last after posting null or empty json - error is thrown as json is malformed, although you do get a 200 OK response code
```
Error: Parse error on line 1:
...on","bodyReceived": }]}
-----------------------^
Expecting 'STRING', 'NUMBER', 'NULL', 'TRUE', 'FALSE', '{', '['
```
* GET to internal_server_error/last after posting valid json:
```
{
    "internal_server_error": [
        {
            "lastUpdated": "2017-10-15T12:48:50Z"
        },
        {
            "mediaTypeUsed": "application/json",
            "bodyReceived": {
                "test": 123
            }
        }
    ]
}
```
* GET to internal_server_error/last after posting valid text:
```
{
    "internal_server_error": [
        {
            "lastUpdated": "2017-10-15T12:46:42Z"
        },
        {
            "mediaTypeUsed": "text/plain",
            "bodyReceived": "{'test':'123'}"
        }
    ]
}
```
* GET /internal_server_error/last - returns a 200 OK response code
* GET /internal_server_error/last - time aligns with current server time, although doesn't have the UTC adjustment

_/unauthorized - Represents an unauthorized response_
=====================================================
* POST to /unauthorized with valid json - the response body contains much more information than the POST/internal_server_error endpoint, which reflects the problem in the documentation. I would argue that the 'unauthorised' part is unnecessary, as you have the response code anyway.
```
{
    "unauthorised": [
        {
            "statusCode": 401,
            "error": "Unauthorized",
            "message": "Invalid token",
            "attributes": {
                "error": "Invalid token"
            }
        },
        {
            "receivedRequest": [
                {
                    "test": 123
                }
            ]
        }
    ]
}
```
* POST to /unauthorized with valid json - response contains both us english and british spelling of authorized. Standardise with http standards.
* POST to /unauthorized with valid json - 401 Unauthorized code is however returned, along with request body in the response.
* POST to /unauthorised with valid json and basic auth attempt (admin/admin as credentials) - no visible change to response
* POST to /unauthorised with invalid json - response code is 400, handling a bad request, body is html.
* POST to /unauthorised with text - using {"test":123} (which is valid json as the request body) returns an error _Expected ',' instead of 't'_, removing the "" from the input triggers a correct response in JSON.
* GET /unauthorized/last - the json returned from this endpoint uses the US English spelling of unauthorized:
```
{
    "unauthorized": [
        {
            "lastUpdated": "2017-10-15T13:16:31Z"
        },
        {
            "mediaTypeUsed": "application/json",
            "bodyReceived": {
                "test": 123
            }
        }
    ]
}
```

* GET /unauthorised/last - returns a 200 OK response code when valid json can be returned.
* GET /unauthorised/last - time aligns with current server time, although doesn't have the UTC adjustment
* VIEW /unauthorised/last - returns a 500 status code, be careful of those unused verbs available in tools, but not in your application.

/no_response - Represents an no_response response
=================================================
* POST to /no_response endpoint with valid json - returns a 204 No Content, but not the json originally submitted in the response body. This matches the documentation oracle.
* POST to /no_response with invalid json - returns a 400 bad request
* POST to /no_response with null or empty json - returns a 204 no content
* POST to /no_response with large json file - generated by using https://www.json-generator.com/#
* GET to /no_response/last - returns a 200 OK response code from a valid json file.
* GET to /no_response/last - time aligns with current server time, although doesn't have the UTC adjustment
* GET to /no_response/last with null or empty last POST to /no_response - Unexpected '}' error message is received, as per other endpoints.
* GET to /no_response/last with large json body. Returned complete file with 200 status code.

/bad_request - Represents an bad_request response
=================================================

* POST to /bad_request with more complex json:
```
{
	"test": {
		"test":123, 
		"test2": 457
	},
	"other": {
		"test":123, 
		"test2": 457
	}
}
```
* Returned 400 Bad Request Code, original request body is reflected in response.
* POST to /bad_request with large json file - generated by using https://www.json-generator.com/#
* GET to /bad_request/last with large json body. Returned complete file with a 200 status code.

/forbidden - Represents an forbidden response
=================================================

* POST to /forbidden with valid json - status code 403 is returned
* GET to forbidden/last - returns a status code of 999, should return 200
* GET to /forbidden/last - time aligns with current server time, although doesn't have the UTC adjustment

/gateway_timeout - Represents an gateway_timeout response
=================================================

* POST to /gateway_timeout with valid json - status code 403 is returned
* GET to gateway_timeout/last - returns a status code of 200
* GET to /gateway_timeout/last - time aligns with current server time, although doesn't have the UTC adjustment

/health - ping endpoint to check is alive status
=================================================

* GET to manage/health - returns a status code of 200