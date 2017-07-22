<html>
<head>
    <link rel="stylesheet" href="{{ asset('css/bootstrap.min.css') }}">
    <link rel="stylesheet" type="text/css" href="{{ asset('css/main.css') }}">
    <link rel="stylesheet" href="{{ asset('css/font-awesome.min.css') }}">
    <link rel="stylesheet" href="{{ asset('css/font-awesome.css') }}">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css"
          type="text/css">
    <style>
        .onepage {
            width: 100%;
            height: 100vh;
            overflow-x: hidden;
        }
        .backmaps {
            background: #f0f;
            width: 100%;
            height: 100%;
            position: absolute;
            z-index: -500;
        }
        .list {
            background-color: #dedede;
            -webkit-border-radius: 4px;
            -moz-border-radius: 4px;
            border-radius: 4px;
            left: 8px;
            height: 70%;
        }
    </style>
</head>

<body>

<!--TOP-->
<div class="onepage">
    <header>
        <h1><span>LINK APP</span>
            <br>
            <p>a social app</p>
            <div class="flex">
                <a href="#" class="bttn" style="margin-right: 10%">About</a>
                <a href="#" class="bttn">Login</a>
            </div>
        </h1>

        <canvas style="overflow-x: hidden"></canvas>
    </header>
</div>

<!--LIST-->
<div class="onepage">
    <div class="backmaps"></div>
    <h2><span class="title-main">EVACUATION LIST AND MAPS</span></h2>
    <div class="col-md-4 list">
        <table class="table">
            <thead>
            <tr><th class="col-md-4">LISTS</th><th>MAP</th></tr>
            </thead>
            <tbody>
            <tr>
                <td>1</td><td>MAP</td>
            </tr>
            </tbody>
        </table>
    </div>
</div>





</body>
<script type="text/javascript" src="{{ asset('js/main.js') }}"></script>
<script>

</script>

</html>