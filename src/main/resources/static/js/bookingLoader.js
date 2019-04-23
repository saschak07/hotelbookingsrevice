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
            
            var json =' <div class="section_title"><h2>Congrats, your booking has been confirmed</h2></div>'+
            ' <table >'+
            '<tr><td text-align = "left"><div class="section_subtitle"> Guest name: </td><td text-align = "right"> '+data.guestName+'</td> </tr></div><br>'+
            '<tr><td text-align = "left"><div class="section_subtitle"> Guest email: </td><td text-align = "right">  '+data.guestEmail+'</td> </tr> </div><br>'+
            '<tr><td text-align = "left"><div class="section_subtitle"> Date of arrival: </td><td text-align = "right"> '+data.startDate+' </td> </tr></div><br>'+
            '<tr><td text-align = "left"><div class="section_subtitle"> Date of departure: </td><td text-align = "right"> '+data.endDate+' </td> </tr></div><br>'+
            '<tr><td text-align = "left"><div class="section_subtitle">  Stay at: </td><td text-align = "right"> '+data.hotelName+'</td> </tr> </div><br>'+
            '<tr><td text-align = "left"><div class="section_subtitle">  Room no.: </td><td text-align = "right"> '+data.roomNo+ '</td> </tr></div><br>"'+
            '</table>';
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