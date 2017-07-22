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
            background: #fbfffd;
            width: 100%;
            height: 100%;
            position: absolute;
            z-index: -500;
        }
        .list {
            background-color: #ffffff;
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
var markers=[];
var map=null;
function initMap() {
    @foreach($evacuations as $i -> $evacuation)
var uluru{{ $i+1 }} = {lat: {{ $evacuation->latitude }}, lng: {{ $evacuation->longitude }};
// var marker<?= $i+1 ?> = new google.maps.Marker({
//    position: uluru<?= $i+1 ?>,
//    map: map,
//    title: '<?= $evacuation->name ?>'
//  });
<?php endforeach; ?>
map = new google.maps.Map(document.getElementById('map'), {
zoom: 14,
center: {lat: 14.558387269243477, lng: 121.13609073337398},

});
var trafficLayer = new google.maps.TrafficLayer();
trafficLayer.setMap(map);
<?php foreach($evacuations as $i => $evacuation): ?>
var iconName = "";
<?php if($evacuation->status == 1): ?>
iconName = "house-green";
<?php elseif($evacuation->status == 2): ?>
iconName = "house-yellow";
<?php else: ?>
iconName = "house-red";
<?php endif; ?>

// var marker<?= $i+1 ?> = new google.maps.Marker({
//    position: uluru<?= $i+1 ?>,
//    map: map,
//    title: '<?= $evacuation->name ?>',
//    icon: '<?= base_url() ?>assets/image/marker/' + iconName + '.png'
//  });

// marker<?= $i+1 ?>.addListener('click', function() {
//    	$('#evacuation<?= $evacuation->id ?>').modal('show');
//    	// window.location.href = "<?= base_url() ?>evacuation-direction/<?= $evacuation->id ?>";
//  });
var marker = null;

var evac_click<?= $evacuation->id ?> = document.getElementById('evac_click<?= $evacuation->id ?>');
evac_click<?= $evacuation->id ?>.addEventListener('click', function() {
for (var asdf=0; asdf<markers.length; asdf++) {
markers[asdf].setMap(null);
}
document.getElementById('latitude').value = <?= $evacuation->latitude ?>;
document.getElementById('longitude').value = <?= $evacuation->longitude ?>;
var evacuation_name = '<?= $evacuation->name ?>';
// plot(map, evacuation_name);
// marker<?= $evacuation->id ?>.setMap(null);
marker = new google.maps.Marker({
position: {lat: <?= $evacuation->latitude ?>, lng: <?= $evacuation->longitude ?>},
map: map,
title: '<?= $evacuation->name ?>'
});
//    	map = new google.maps.Map(document.getElementById('map'), {
//   zoom: 16,
//   center: {lat: <?= $evacuation->latitude ?>, lng: <?= $evacuation->longitude ?>},

// });
map.panTo({lat: <?= $evacuation->latitude ?>, lng: <?= $evacuation->longitude ?>});
marker.setMap(map);
trafficLayer.setMap(map);
markers.push(marker);

console.log("size: "+markers.length);
console.log(markers);
marker.addListener('click', function() {
$('#evac_modal<?= $evacuation->id ?>').modal('show');
});
// window.location.href = "<?= base_url() ?>evacuation-direction/<?= $evacuation->id ?>";
});


<?php endforeach; ?>

//http://www.iconsdb.com/icons/preview/orange/house-xxl.png

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
}

function handleLocationError(browserHasGeolocation, infoWindow, pos) {
infoWindow.setPosition(pos);
infoWindow.setContent(browserHasGeolocation ?
'Error: The Geolocation service failed.' :
'Error: Your browser doesn\'t support geolocation.');
infoWindow.open(map);
}

function plot(map, name) {
var latitude = document.getElementById('latitude').value;
var longitude = document.getElementById('longitude').value;
var uluru_new = {lat: latitude, lng: longitude};
var marker_new = new google.maps.Marker({
position: uluru_new,
map: map,
title: name
});
}
</script>
<script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDfkZ3u8lAHGja2T6FJMctKLYxeIacMpMI&callback=initMap">
</script>

</html>