_Automation Enhancments_
========================

_Fixes_
=======

* Bad Request - fixed response code from incorrectly expected 403 to 400.
* Made_Up:
	* Fixed the GET test to be response Not Found and response code should be 404
	* Added existing steps for Then the  and the response code for the madeup last endpoint
	* These tests are a little trite and prove the same thing over and over.
* Added tag to specs/InternalServerError.spec - tags: internal_server_error
* Deleted duplicate file asserions.java to fix duplicate step error
* Created two steps:
	* Retrieve the content type for the response, added to generic gets code, to add headers to datastore.
	* Add httpResponse.body to datastore and created a JSON assert structure to check media type and the content of the test node json of the POSTED json.


_Enhancements_
==============

* I would add schema validation tests within. The attempts of the */last* endpoints to return null or empty data suggest a layer of checking might be missing.
* Clear up the made up tests ideally, remove all but one.
* Renamed gateway_timeout file and associated tags
* Corrected tests to include 204 No Content, not No Response
* Enhance the JSON assert type tests with a table to be more explicit.
* The JSON structure is horrible. I would ask my developers to remove the dependency on the name of the request (bad_request for example) in the JSON.