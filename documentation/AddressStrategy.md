<h2>Address Geocoding Strategy</h2>

<h4> Spring Framework </h4>
PinPoint is run on Spring Boot. Will document this in depth later.

<h4>Strategy</h4>
1. The initial address string will be passed into the Geocoder.
2. String will be passed to AddressParser, where it will be "Pre-Parsed":
  - Cleanse string of unwanted characters
  - Find Zipcode, State, Number
  - Identify potential cities
  - Return Address Object [created from String]
3. Find "place" candidates:
  - IF Zip is found: Lookup Places by Zip
    - IF zipcode matched city: Move on
  - ELSE: Places by potential city
    - IF places found: Move on
4. Pass Address object to - "Post Parse" 
  - Due to nature of address - Directional data/street types within city/street name, wait until potential cities are found in order to parse out the street name.
  - Identify pre/post direction, street type, street name
5. Find Street by Name/city
  - Retieve candidate list of potential "AddrFeats"
6. Rank
7. Geocode Highest Ranked Candidate


<h4>City Geocoder</h4>
1. Search Places for given Zipcode.
    - If place.city EXACTLY matches a potential city -> found.
    - If place.city FUZZY matches a potential city -> found?
2. If no Match is found:
    - Pull data based on potential "cities" [tokenize data]
    - Find and extract out city
        - Metaphone Match
        - String Distance match


<h4>Street Geocoder</h4>
1. Post Parse Street Address [Potentially]
    - Strip out pre/post types
    - Strip out pre/post directionals
2. Query DB for potential addresses
3. Rank
    
<h4>Geocode Levels</h4>
There are currently 4 levels of geocode 'accuracy', as seen below. description is in brackets:
0 - No Match Found [PinPoint could not find a match]
1 - City/Zip Match [PinPoint matched given city/zip code]
2 - Street Level Match: Street [Found given street, lat/lon places on start of street]
3 - Street Level Match: Interpolated [Found given street, interpolated potential lat/lon coords]

