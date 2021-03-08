$(document).ready(function () {
    $('.detailsbutton').on('click', function (event) {
        $inputField = $(this).parent().siblings('td');
        $editConfirm = $(this).siblings('.editconfirm');
        if($inputField.attr('id')=='tarea'){
            $actualInput = $inputField.children('textarea');
        }
        else {
        $actualInput = $inputField.children('input');
    }
        $editConfirm.css({visibility: 'hidden'});
        if ($(this).text() === "Edit") {
            $(this).html("Confirm");
            $actualInput.attr('readonly', false);
            event.preventDefault();
        } else {
            dataString = $("#detailsform").serialize();
            var parameter = $($actualInput).val();
            dataString = "&" + $actualInput.attr('name') + "=" + parameter;


            $.ajax({
                type: "POST",
                url: "studentdetails",
                data: dataString,

                success: function (data, textStatus, jqXHR) {
                    //display success message
                    if (data === "Success") {
                        $editConfirm.css({visibility: 'visible'});
                        $editConfirm.css({color: 'green'});
                        $editConfirm.html('&check; updated.');
                    }
                    //display error message
                    else {
                        $editConfirm.css({visibility: 'visible'});
                        $editConfirm.css({color: 'red'});
                        $editConfirm.html('&#x2716; error.')
                    }
                },

                //If there was no resonse from the server
                error: function (jqXHR, textStatus, errorThrown) {
                    $inputField.append("<small class=\"editerror\">&#x2716; thrownerror.</small> " + textStatus);
                }
            });
            $(this).html("Edit");
            $actualInput.attr('readonly', true);
            event.preventDefault();
        }
    });
});

