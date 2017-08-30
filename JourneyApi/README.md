## Journey Getter API

Takes a start location, an end location, and returns the first line to take, time of start and whether
or not a disruption has happened.

###Example usage
GET:  __/journey?from=gunnersbury&to=kentish%20town__

Response:

    {
        "name": "District Line towards Upminster", 
        "isDisrupted": "true", 
        "arrivalTime": "13:02:00", 
	"changes": ["Victoria Station", "Euston Station"]
    }
