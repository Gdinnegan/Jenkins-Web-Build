
$(document).ready(function(){
    $.ajax({
        url: "/api/jobs",
        error: function(jqxhr, status, exception) {
            alert('Exception:', exception);
        },
        success: function(data) {
            for (var i = 0, len = data.length; i < len; i++) {
              job = data[i]
              $("#jobTable tbody").append("<tr><td>" + job.displayName + "</td></tr>");
            }
        }
    })
//    $("h1").click(function(){
  //      alert("The paragraph was clicked.");
//    });
});

