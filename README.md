KooabaConnect
=============

Phonegap plugin to create image recognition request with kooaba

Requests to the kooaba service need to be sent with headers that are now restricted through javascript requiring the use of native code.

Plugin is called with:
cordova.exec(<Success function>, <Fail function>, "KooabaConnect", "connect", [contenttype,auth,date,body]);

Where:
	contenttype = Content-Type header
	auth = Authorization header for Kooaba's authentication
	date = Date header for Kooaba's authentication
	body = Base64 encoded body content for Kooaba's recognition service