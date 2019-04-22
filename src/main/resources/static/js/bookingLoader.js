$(document).ready(function () {
    var queryString = decodeURIComponent(window.location.search);
    queryString = queryString.substring(1);
    var queries = queryString.split("=");
    
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/booking/"+queries[1],
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            
            var json =' <div class="section_title"><h2>Congrats, your booking has been confirmed</h2></div><br>'+
            '<div class="section_subtitle"> Guest name:  '+data.guestName+' </div><br>'+
            '<div class="section_subtitle"> Guest email:  '+data.guestEmail+' </div><br>'+
            '<div class="section_subtitle"> Date of arrival:  '+data.startDate+' </div><br>'+
            '<div class="section_subtitle"> Date of departure:  '+data.endDate+' </div><br>'+
            '<div class="section_subtitle"> <h2> Stay at:  '+data.hotelName+'</h2> </div><br>'+
            '<div class="section_subtitle"> <h2> Room no.:  '+data.roomNo+ '</h2></div><br>"';
            $('#booking').html(json);
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
            $('#booking').html(json);

            console.log("ERROR : ", e);
           

        }
    });
})