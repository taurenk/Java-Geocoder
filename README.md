<h2>PinPointGeocoder2</h2>
<h4>PinPoint Geocoding Engine</h4>
PinPoint Geocoder 2 is an open source geocoding service implemented in Java using Spring Boot, Hibernate, and PostgreSQL.

PinPoint was iniatially "prototyped" up in Python but due to many factors [learning Java/Spring one of them], I am re-writing it.

<h4>About the App</h4>


<h4>Usage - in Test </h4>
    http://localhost:8080/api/geocoder/geocode?address=6%20caputo%20drive%20manorville%20ny%2011949
    {"addressString":"6 CAPUTO DRIVE MANORVILLE NY 11949","number":"6","street":null,"city":null,"potential_cities":null,"state":"NY","zip":"11949"}