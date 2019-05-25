# hotelbooking service

<b>Hotel Booking Service Application</b>

<b>Tech-stack:</b>

Spring boot

java8

Postgres

Ajax, Jquery

maven

unit test - mockito/Junit 

<b>UI feature:</b>

hotel-company landing page

hotel list page

room list page based on hotel id and arrival and departure dates

book a room from pop-up modal

admin page to view calendar 


<b>Application dependencies:</b>

    • spring boot back end services persists data to postgres, the node should have a pre installed postgres instance to start the application.

    • Target node for deployment needs oauth authorization enabled for google calendar api event write scope.
    • Java 8 runtime environment is needed to start the application.
    >>>> credential Json for calendar api should be placed at the path : src/main/resources/
    
<b>Note:</b> application has been deployed at heroku : https://hotelluxury.herokuapp.com
admin page with calendar: https://hotelluxury.herokuapp.com/admin.html
**from heroku to calendar event creation is not working currently due to oauth issue. still work in progress.

<b>Architecture</b>

Database:

	postgres database with thre tables:
	
    • hotels
    • rooms
    • booking
 	










hotels have one to many relation with rooms. Hibernate has been used to maintain this relationship .



<b>Future Architecture breaking monolithic model</b>

![alt text](https://github.com/saschak07/sortingtest/blob/master/Untitled%20Diagram.png)













The core functionalities are exposed to ui and external world through REST APIs the details are as follows:

get all hotels:

end-point : http://localhost:9000/all-hotels
Method : GET

SuccessResponse:
```json
[
  {
    "hotelIds": "string",
    "hotelName": "string",
    "address": "string",
    "contactNo": "string",
    "hotelImage": "string",
    "rooms": [
      {
        "roomIds": "string",
        "roomNumber": "string",
        "roomType": "string",
        "amenities": "string",
        "roomImage": "string"
      }
    ]
  }
]
```
getHotelById:

end-point : http://localhost:9000/hotel/{hotelId}
method: GET

success response:
```json
{
  "hotelIds": "string",
  "hotelName": "string",
  "address": "string",
  "contactNo": "string",
  "hotelImage": "string",
  "rooms": [
    {
      "roomIds": "string",
      "roomNumber": "string",
      "roomType": "string",
      "amenities": "string",
      "roomImage": "string"
    }
  ]
}
```
AvailableRoom

End-point: 
http://localhost:9000/available-rooms

method: POST

request body:
```json
{
  "hotelId": "string",
  "startDate": "string",
  "endDate": "string"
}
```
response body:
```json
{
  "hotelIds": "string",
  "hotelName": "string",
  "address": "string",
  "contactNo": "string",
  "hotelImage": "string",
  "rooms": [
    {
      "roomIds": "string",
      "roomNumber": "string",
      "roomType": "string",
      "amenities": "string",
      "roomImage": "string"
    }
  ]
}
```
BookRoom

endpoint: http://localhost:9000/book-room

method: POST

requestBody:
```json
{
  "hotelId": "string",
  "hotelName": "string",
  "roomNo": "string",
  "startDate": "string",
  "endDate": "string",
  "guestName": "string",
  "guestEmail": "string"
}
```
response body:
```json
{
  "bookingIds": "string",
  "hotelId": "string",
  "hotelName": "string",
  "roomNo": "string",
  "startDate": "string",
  "endDate": "string",
  "guestName": "string",
  "guestEmail": "string"
}
```
Get booking details by booking id:

end-point: http://localhost:9000/booking/{bookingId}

method: GET

response: 
```json
{
  "bookingIds": "string",
  "hotelId": "string",
  "hotelName": "string",
  "roomNo": "string",
  "startDate": "string",
  "endDate": "string",
  "guestName": "string",
  "guestEmail": "string"
}
```
Adding new Hotels:
endpoint: http://localhost:9000/add-hotel

method: POST

request body:
```json
{
  "hotelIds": "string",
  "roomNumber": "string",
  "roomType": "string",
  "amenities": "string"
}
```
response body:
```json
{
  "hotelIds": "string",
  "hotelName": "string",
  "address": "string",
  "contactNo": "string",
  "rooms": [
    {
      "roomIds": "string",
      "roomNumber": "string",
      "roomType": "string",
      "amenities": "string"
    }
  ]
}
```
Add new Rooms:

endpoint: http://localhost:9000/add-rooms

method : POST
```json
request body:
{
  "hotelIds": "string",
  "roomNumber": "string",
  "roomType": "string",
  "amenities": "string"
}
```
response body:
```json

{
  "hotelIds": "string",
  "hotelName": "string",
  "address": "string",
  "contactNo": "string",
  "rooms": [
    {
      "roomIds": "string",
      "roomNumber": "string",
      "roomType": "string",
      "amenities": "string"
    }
  ]
}
```
