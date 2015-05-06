<h2>PinPointGeocoder2</h2>

<h4>About the PinPoint Geocoding Project</h4>
PinPoint is an open sourced geocoding service built on Java, Spring, Hibernate, PostgreSQL, US Census Data, and hardwork! 

The application is split up into quite a few layers: Controller, Service, Repository, and Model with the addition of the  Geocoder module.

The geocoder module contains the algorithms to support the geocoding process - address parsing and  interpolation for example. It also contains the regular expression class and data conversion tools in the library sub-module. 

The database required for this app sits on a seperate server and must be built. I have the build scripts used to build the original database, but much has changed and they need updating. I will release the new [and improved] vagrant scripts very soon. If you would like access to data sets I have created, please contact me.

<h4>API Usage</h4>
Their are currently 3 endpoints to get data from PinPoint - based on the data sets used during the geocoding process. 

<h5>Geocoding API</h5>
This api takes an address string and attempts to parse and geocode said string.

    http://localhost:8080/api/geocoder/geocode?address=6%20caputo%20drive%20manorville%20ny%2011949
<h5>Places API</h5>
Places API will return city/state/zip data based off of inputted zipcode.

    http://localhost:8080/api/place/placeByZip?zipcode=11949
<h5>AddrFeat API</h5>
AddrFeat refers to the U.S. Census AddrFeat table, which contains street level dat about given adresses. 

    http://localhost:8080/api/addrfeat/byFullname?fullname=CAPUTO%20DR
    
