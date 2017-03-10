<!DOCTYPE html>
<html>
<head>
    <title>WMeter</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        .container{
            width: 600px;
            margin: auto;
        }
        input[type="text"]{
            width: 100%;
            margin-bottom: 10px;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>WMeter3</h2>
    <hr>
    <br>
    <form method="POST" action="">
        <label for="url">URL</label>
        <input type="text" id="url" name="url"/>
        <label for="usercount">User Count</label>
        <input type="text" id="usercount" name="usercount"/>
        <input type="button" value="submit" id="submitbtn"/>
    </form>
</div>

<script src="js/jquery-3.1.1.min.js"></script>
<script>
    $('#submitbtn').click(function(){
        var url = $('#url').val();
        var usercount = $('#usercount').val();
        $.ajax({
            url: 'JMeterServlet?action=RunJMeter&url='+url+'&usercount='+usercount,
            success: function(response){
                setInterval(function (){
                    $.ajax({
                        url: 'JMeterServlet?action=checkStatus?id='+response,
                    });
                },1000);
            }
        });
    });



</script>

</body>
</html>