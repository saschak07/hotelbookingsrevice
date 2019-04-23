$(document).ready(function () {
    $('#loader').hide();
    var queryString = decodeURIComponent(window.location.search);
    queryString = queryString.substring(1);
    var queries = queryString.split("=");
    var hotelname ="";
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/hotel/"+queries[1],
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            hotelname = data.hotelName;
            var json ="<div class=\"home_title\">"+data.hotelName+"</div>"+"<br>"+
            "<div class=\"home_subtitle\">select dates to check available rooms</div>"
            $('#hotel_title').html(json);

            console.log("SUCCESS : ", data);

        },
        error: function (e) {

            var json = "<div class=\"col-lg-4 room_col magic_up\">"+
            "<div class=\"room\">"+
                    "<div class=\"room_text\">"
                        +"<p>Oops data could not be loaded!!! support team will work on this!</p>"
                    +"</div>"
                +"</div>"
            +"</div>"
        +"</div>";
            $('#feedback').html(json);

            console.log("ERROR : ", e);
           

        }
    });

    $("#search_room").click(function(event){
        $('#loader').show();
        event.preventDefault();
        var search = {};
        search["hotelId"] = queries[1];
        search["startDate"] = $("#startDate").val() + "T12:00:00";
        search["endDate"] = $("#endDate").val() + "T12:00:00";
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/available-rooms",
            data: JSON.stringify(search),
            dataType: 'json',
            cache: false,
            timeout: 600000,
            success: function (data) {
                $('#loader').hide();
                var json = "";
                if(data.rooms.length>0){
                 json ="<div class=\"container\">"
				+"<div class=\"row\">"
					+"<div class=\"col\">"
						+"<div class=\"section_title_container text-center\">"
							+"<div class=\"section_subtitle\"> list of available rooms</div>"
							+"<div class=\"section_title\"><h2>select one of your choice</h2></div>"
						+"</div>"
					+"</div>"
				+"</div>"
				+"<div class=\"row room_row\" >"
                 $.each(data.rooms, function(index, room){
                            json =json + "<div class=\"col-lg-4 room_col magic_up\">"+
                            "<div class=\"room\">"+
                            "<div class=\"room_image\"><img src=\""+room.roomImage+"\" alt=\"https://unsplash.com/@jonathan_percy\"></div>"
                            +"<div class=\"room_content text-center\">"
                            +"<div class=\"room_title\">"+data.hotelName+"</div>"
                            +"<div class=\"room_title\"> Room no.: "+room.roomNumber+"</div>"
                            +"<div class=\"room_title\"> Room Type: "
                            +room.roomType+"</div>"
                            +"<div class=\"room_text\"><p> Available amenities: "
                            +room.amenities+"</p>"
                            +"</div>"
                            +"<a href=\"javascript:void(0)\" onclick=\"activate_booking('"+queries[1]+"','"+$("#startDate").val()
                            +"','"+$("#endDate").val()+"','"+hotelname+"','"+room.roomNumber+
                            "')\" class=\"button_container room_button\"><div class=\"button text-center\"><span>Book Now</span></div></a>"
                            +"</div>"
                            +"</div>"
                            +"</div>"
                            
                            });
                 }
                 else {
                     json = "<div class=\"container\">"
                     +"<div class=\"row\">"
                         +"<div class=\"col\">"
                             +"<div class=\"section_title_container text-center\">"
                                 +"<div class=\"section_title\"><h2>All Rooms are boked during this time, Please search for other dates</h2></div>"
                             +"</div>"
                         +"</div>"
                     +"</div>";
                 }
                 json=json+'</div></div>'
                    $('#room_list').html(json);

            console.log("SUCCESS : ", data);

        },
            error: function (e) {
                $('#loader').hide();
                json = "<div class=\"container\">"
                +"<div class=\"row\">"
                    +"<div class=\"col\">"
                        +"<div class=\"section_title_container text-center\">"
                            +"<div class=\"section_title\"><h2>"+e.responseJSON.message+"</h2></div>"
                        +"</div>"
                    +"</div>"
                +"</div>";
                $('#room_list').html(json);
                console.log("Error : ", e); 
            }
        });
    });

    

});


function activate_booking(hotelId,startDate,endDate,hotelname,roomNumber){
        
    var dialog = new BootstrapDialog({
        message: function(dialogRef){
            var $message = $('<div><h2>Confirm your booking details!!<h2></div><br>');
            var $inputName = $('<input type="text" placeholder="Guest full name" class="form-control" id="name"><br>');
            var $inputEmail = $('<input type="text" placeholder="Guest email address" class="form-control" id="email"> <br>');
            var $message1 = $('<div>From:</div><br>');
            var $inputstartDate = $('<input type="date" placeholder="From : dd/mm/yy" class="form-control" id="str_date"> <br>');
            var $message2 = $('<div>To:</div><br>');
            var $inputendDate = $('<input type="date"  placeholder="To : dd/mm/yy" class="form-control" id="end_Date"> <br>');
            var $confirmBooking = $('<button class="btn btn-primary btn-lg btn-block">Confirm</button>');
            var $button3 = $('<button class="btn btn-primary btn-lg btn-block">Close</button>');
            $button3.on('click', {dialogRef: dialogRef}, function(event){
                event.data.dialogRef.close();
            });
            $confirmBooking.on('click', {dialogRef: dialogRef}, function(event){
                event.preventDefault();
                var name = event.data.dialogRef.getModalBody().find('#name').val();
                var email = event.data.dialogRef.getModalBody().find('#email').val();
                var startDate = event.data.dialogRef.getModalBody().find('#str_date').val();
                var endDate = event.data.dialogRef.getModalBody().find('#end_Date').val();
                book_room(hotelId,hotelname,roomNumber,name,email,startDate,endDate);
            });
            $message.append($inputName)
            $message.append($inputEmail);
            $message.append($message1);
            $message.append($inputstartDate);
            $message.append($message2);
            $message.append($inputendDate);
            $message.append($confirmBooking)
            $message.append($button3)
    
            return $message;
        },
        closable: false
    });
    dialog.realize();
    dialog.getModalHeader().hide();
    dialog.getModalFooter().hide();
    dialog.getModalBody().css('background-color', '#0088cc');
    dialog.getModalBody().css('color', '#fff');
    dialog.open();
};

function book_room(hotelId,hotelname,roomNumber,name,email,startDate,endDate){
    var bookingReq = {};
    bookingReq["hotelId"]=hotelId;
    bookingReq["hotelName"]=hotelname;
    bookingReq["roomNo"]=roomNumber;
    bookingReq["startDate"]=startDate+"T14:00:00";
    bookingReq["endDate"]=endDate+"T12:00:00";
    bookingReq["guestName"]=name;
    bookingReq["guestEmail"]=email;
    $('#loader').show();
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/book-room",
        data: JSON.stringify(bookingReq),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            $('#loader').hide();
            alert("Success!!!! your booking has been confirmed")
        console.log("SUCCESS : ", data);
        window.location = 'confirmation.html?booking_Id='+data.bookingIds;

    },
        error: function (e) {
            alert("Error while booking room!....Support team is looking into the issue")
            console.log("ERROR : ", e);
        }
    });
}