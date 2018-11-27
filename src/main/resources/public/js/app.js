
$(document).ready(function(){
    $.ajax({
        url: "/api/jobs",
        error: function(jqxhr, status, exception) {
            alert('Exception:', exception);
        },
        success: function(data) {
            for (var i = 0, len = data.length; i < len; i++) {
              job = data[i]
              urlPath = '/api/builds/' + job.job_id
              htmlString = `<tr><td>${job.display_name}</td><td><a href=${urlPath}>View Builds History</a></td><</tr>`
              $("#jobTable tbody").append(htmlString);

            }
        }
    })
});








