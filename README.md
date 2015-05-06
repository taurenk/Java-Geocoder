<h2>PinPointGeocoder2</h2>
<h4>PinPoint Geocoding Engine</h4>
PinPoint Geocoder 2 is an open source geocoding service implemented in Java using Spring Boot, Hibernate, and PostgreSQL.

PinPoint was iniatially "prototyped" up in Python but due to many factors [learning Java/Spring one of them], I am re-writing it.

<h4>About the App</h4>
The application is split up into standard layers: Controller, Service, Repository, and Model with the exception of the Geocoder module.

The geocoder module contains the algorithms to support the geocoding process. It also contains the regular expression class and data conversion tools in the library sub-module. 

<h4>API Usage</h4>
Their are currently 3 endpoints to get data from PinPoint; 

<h5>Geocoding API</h5>

    http://localhost:8080/api/geocoder/geocode?address=6%20caputo%20drive%20manorville%20ny%2011949
<h5>Places API</h5>

    http://localhost:8080/api/place/placeByZip?zipcode=11949
<h5>AddrFeat API</h5>

    http://localhost:8080/api/addrfeat/byFullname?fullname=CAPUTO%20DR
    