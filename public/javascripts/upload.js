$(document).ready(function () {

    var options = {
        beforeSend: function () {
            $("#ajaxprogressbar").width('0%').html("");
        },
        uploadProgress: function (event, position, total, percentComplete) {
            $("#ajaxprogressbar").width(percentComplete + '%').html(percentComplete + '%');
        },
        success: function () {
            $("#ajaxprogressbar").width('100%').html("100%");
        },
        complete: function (response) {
            $("#message").html(response.responseText);
        },
        error: function () {
            $("#message").html("<font color='red'> ERROR: unable to upload files</font>");
        }

    };

    $("#myForm").ajaxForm(options);

});