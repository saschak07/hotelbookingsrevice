$(document).ready(function () {

    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/all-hotels",
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            var json ="";
            $.each(data, function(index, hotel){
             json =json + "<div class=\"col-lg-4 room_col magic_up\">"+
            "<div class=\"room\">"+
                "<div class=\"room_image\"><img src=\"images/room_1.jpg\" alt=\"https://unsplash.com/@jonathan_percy\"></div>"
                +"<div class=\"room_content text-center\">"
                    +"<div class=\"room_title\">"+hotel.hotelName+"</div>"
                    +"<div class=\"room_text\"><p> Address:"
                    +hotel.address+"</p>"
                    +"</div>"
                    +"<a href=\"room.html?hotelId="+hotel.hotelIds+"\" class=\"button_container room_button\"><div class=\"button text-center\"><span>Book Now</span></div></a>"
                +"</div>"
            +"</div>"
        +"</div>"});
            $('#hotel_list').html(json);

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

});