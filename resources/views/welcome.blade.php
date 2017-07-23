<html>
<head>
    <title>LINK APP</title>
    <link rel="stylesheet" href="{{ asset('css/bootstrap.min.css') }}">
    <link rel="stylesheet" type="text/css" href="{{ asset('css/main.css') }}">
    <link rel="stylesheet" href="{{ asset('css/font-awesome.min.css') }}">
    <link rel="stylesheet" href="{{ asset('css/font-awesome.css') }}">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css"
          type="text/css">

  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
   <!--Import jQuery before export.js-->
    <script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>

    <style>
        .onepage {
            width: 100%;
            height: 100vh;
            overflow: hidden;
        }
        .backmaps {
            /*background: #fbfffd;*/
            width: 100%;
            height: 83%;
            display: block;
        }
        .list {
            position: absolute;
            top: 130vh;
            z-index: 500;
            left: 8px;
            background-color: rgba(255, 255, 255, 0.7);
        }
    </style>
</head>

<body>

<!--TOP-->
<div class="onepage">
    <header>
        <h1><span>LINK APP</span>
            <br>
            <p></p>
            <div class="flex">
                <button type="button" class="bttn" data-toggle="modal" data-target="#myModalNews">News</button>
            </div>
        </h1>

        <canvas style="overflow-x: hidden"></canvas>
    </header>
</div>

<!--LIST-->
<div class="onepage">
    <h2><span class="title-main">EVACUATION LIST AND MAPS</span></h2>
    <div class="backmaps" id="map"></div></div>


    <div class="col-md-4 list">
        <table class="table">
            <thead>
            <tr><th class="col-md-4">Evacuation Info</th></tr>
            </thead>
            <tbody>
            <tr>

                <td>
                <ul class="list-group">
                        <?php foreach($evacuations as $evacuation): ?>
                            <li class="list-group-item">
                                <span id="evac_click<?= $evacuation->id ?>" role="button"  data-toggle="collapse" data-parent="#accordion" href="#evacuation<?= $evacuation->id ?>" aria-expanded="true" aria-controls="collapseOne"><?= $evacuation->name ?></span>
                                <div id="evacuation<?= $evacuation->id ?>" class="panel-collapse collapse out" role="tabpanel" aria-labelledby="headingOne">
                                    <strong>Address: </strong><?= $evacuation->address ?><br>
                                    <strong>Description: </strong><?= $evacuation->description ?><br>
                                    <!-- <p class="pull-right"><a href=""><small>View Full Details</small></a></p> -->
                                </div>
                            </li>
                        <?php endforeach; ?>
                    </ul>
                </td>
                <td hidden><input type="number" id="latitude"><input type="number" id="longitude"></td>

            </tr>
            </tbody>
        </table>
    </div>

<!--MODAL FOR TWITTER/NEWS -->
 <div class="modal fade" id="myModalNews" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">Twitter News - PAGASA-DOST</h4>
        </div>
        <div class="modal-body">
          <a class="twitter-timeline" href="https://twitter.com/dost_pagasa" data-show-replies="true" data-height="450">Tweets by PAGASA-DOST</a> <script async src="//platform.twitter.com/widgets.js" charset="utf-8"></script>
        </div>
    </div>
        
      </div>
      
    </div>
  
<div class="onepage" style="text-align: center; height: 10%; ">
<p style="color: black">&copy; 2017 Kings Dev. All Rights Reserved</p>
</div>

</body>


<script type="text/javascript" src="{{ asset('js/main.js') }}"></script>
<script>
    var markers=[];
    var map=null;
    var prev_evacid=null;
    var curr_evacid=null;

    // Weather
    var address = "Metro Manila";
    var cities = ["Albay", "Bohol", "Batangas", "Bulacan", "Cavite", "Laguna", "Metro Manila", "Pampanga", "Pangasinan", "Quezon", "Rizal", "Tarlac", "Zambales"];

    var lats = [13.1775, 9.8500, 13.9450, 14.9968, 14.2456, 14.1407, 14.6091, 15.0794, 15.8949, 13.9347, 14.6037, 15.4755, 15.5082]; 

    var lngs = [123.5280, 124.1435, 121.1312, 121.1710, 120.8786, 121.4692, 121.0223, 120.6200, 120.2863, 121.9473, 121.3084, 120.5963, 119.9698];

    var count;
    var curDIV = document.getElementById("current");
    var forDIV = document.getElementById("forecast");
    var city;   
    var conditions = [];
    var coords = [];
    var weather_marker = null;
    function initMap() {
        map = new google.maps.Map(document.getElementById('map'), {
        zoom: 14,
        center: {lat: 14.558387269243477, lng: 121.13609073337398}
        });

        <?php $i=-1; ?>
        @foreach($evacuations as $evacuation)
            <?php $i++; ?>
            var marker = null;

            var evac_click{{ $evacuation->id }} = document.getElementById('evac_click{{ $evacuation->id }}');

            marker = new google.maps.Marker({
                    position: {lat: {{ $evacuation->latitude }}, lng: {{ $evacuation->longitude }}},
                    map: map,
                    title: '{{ $evacuation->name }}'
                });
            marker.setMap(map);
            // markers.push(marker);
            evac_click{{ $evacuation->id }}.addEventListener('click', function() {
                // for (var asdf=0; asdf<markers.length; asdf++) {
                //     markers[asdf].setMap(null);
                // }

                document.getElementById('latitude').value = {{ $evacuation->latitude }};
                document.getElementById('longitude').value = {{ $evacuation->longitude }};
                document.getElementById('evac_click{{ $evacuation->id }}').setAttribute('style', 'font-weight: bold;');
                var evacuation_name = '{{ $evacuation->name }}';

                if(curr_evacid != {{ $evacuation->id }}) {
                    prev_evacid = curr_evacid;
                }

                curr_evacid = {{ $evacuation->id }};

                if(prev_evacid != null) {
                    document.getElementById('evacuation'+prev_evacid).setAttribute('class', 'panel-collapse collapse out');
                    document.getElementById('evac_click'+prev_evacid).setAttribute('style', '');
                }


                // map = new google.maps.Map(document.getElementById('map'), {
                //     zoom: 14,
                //     center: {lat: {{ $evacuation->latitude }}, lng: {{ $evacuation->longitude }}}
                //     });

                // marker = new google.maps.Marker({
                //     position: {lat: {{ $evacuation->latitude }}, lng: {{ $evacuation->longitude }}},
                //     map: map,
                //     title: '{{ $evacuation->name }}'
                // });

                map.panTo({lat: {{ $evacuation->latitude }}, lng: {{ $evacuation->longitude }}});
                // map.zoom(15);
                // map.center({lat: {{ $evacuation->latitude }}, lng: {{ $evacuation->longitude }}});
                // marker.setMap(map);
                // markers.push(marker);

                // marker.addListener('click', function() {
                //     $('#evac_modal{{ $evacuation->id }}').modal('show');
                // });
            });
        @endforeach

        infoWindow = new google.maps.InfoWindow;

        // Try HTML5 geolocation.
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(function(position) {
                var pos = {
                lat: position.coords.latitude,
                lng: position.coords.longitude
                };

                infoWindow.setPosition(pos);
                infoWindow.setContent('You are here.');
                infoWindow.open(map);
                //map.setCenter(pos);
                }, function() {
                handleLocationError(true, infoWindow, map.getCenter());
            });
        } else {
            // Browser doesn't support Geolocation
            handleLocationError(false, infoWindow, map.getCenter());
        }


        for(count = 0; count < cities.length; count++){
            city = cities[count];
            // coords.push({lat: lats[count],lng: lngs[count]});
            currentWeather(city, map, coords, count, lats, lngs);
        }
        // console.log("Conditions: " + conditions);
        // console.log(coords);
    }

    function handleLocationError(browserHasGeolocation, infoWindow, pos) {
        infoWindow.setPosition(pos);
        infoWindow.setContent(browserHasGeolocation ?
        'Error: The Geolocation service failed.' :
        'Error: Your browser doesn\'t support geolocation.');
        infoWindow.open(map);
    }

    function currentWeather(city, map, coords, count, lats, lngs){
        Weather.getCurrent( city, function( current ) {
            //alert(current.conditions());
            // conditions.push(current.conditions());
            var iconName = "";
            if(current.conditions() == 'light rain'){
                iconName = "/light-rain";
            } else if(current.conditions() == 'clear sky'){
                iconName = "/clear-sky";
            } else if(current.conditions() == 'broken clouds'){
                iconName = "/broken-clouds";
            } else {
                iconName = "/few-clouds";
            }


            weather_marker = new google.maps.Marker({
              position: {lat: lats[count],lng: lngs[count]},
              map: map,
              title: 'Location: ' + city + "\n" + 
                      'Condition: ' + current.conditions() + "\n" +
                      'Temperature: ' + Weather.kelvinToFahrenheit(current.temperature()).toFixed(2) + "\xB0F or " +
                Weather.kelvinToCelsius(current.temperature()).toFixed(2) + "\xB0C",
              icon: '{{ asset("image/weather-icons") }}' + iconName + '.png'
            });
            // weather_marker.setMap(map);
        });
    }
    
</script>
<script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDfkZ3u8lAHGja2T6FJMctKLYxeIacMpMI&callback=initMap">
</script>
<script type="text/javascript" src="{{ asset('js/bootstrap.min.js') }}"></script>
<script type="text/javascript" src="{{ asset('js/weather-master/lib/weather.js') }}"></script>

</html>