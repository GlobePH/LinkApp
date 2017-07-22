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
            overflow: hidden;
        }
        .backmaps {
            background: #fbfffd;
            width: 100%;
            height: 100%;
        }
        .list {
            background-color: rgba(255, 255, 255, 0.7);
            -webkit-border-radius: 4px;
            -moz-border-radius: 4px;
            border-radius: 4px;
            left: 8px;
            margin-top: 8%;
            position: absolute;
            top: 110vh;
            z-index: 500;
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
                <a href="#" class="bttn">About</a>
            </div>
        </h1>

        <canvas style="overflow-x: hidden"></canvas>
    </header>
</div>

<!--LIST-->
<div class="onepage">
    <h2><span class="title-main">EVACUATION LIST AND MAPS</span></h2>
    <div class="backmaps" id="map"></div>


    <div class="col-md-4 list">
        <table class="table">
            <thead>
            <tr><th class="col-md-4">Evacuation Info</th></tr>
            </thead>
            <tbody>
            <tr>
                <td>@foreach($evacuations as $evacuation)
            <li class="list-group-item">
                <span id="evac_click{{ $evacuation->id }}" role="button"  data-toggle="collapse" data-parent="#accordion" href="#evacuation{{ $evacuation->id }}" aria-expanded="true" aria-controls="collapseOne">{{ $evacuation->name }}</span>
                    <div id="evacuation{{ $evacuation->id }}" class="panel-collapse collapse out" role="tabpanel" aria-labelledby="headingOne">
                        <strong>Address: </strong>{{ $evacuation->address }}<br>
                        <strong>No. of Families: </strong>{{ $evacuation->family_no }}<br>
                        <strong>Coordinator: </strong>{{ $evacuation->coordinator }}
            <p class="pull-right"><a href=""><small>View Full Details</small></a></p>
        </div>
    </li>
    @endforeach</td>
                <td hidden><input type="number" id="latitude"><input type="number" id="longitude"></td>
            </tr>
            </tbody>
        </table>
    </div>
</div>


<!--MODAL-->
@foreach($evacuations as $evacuation)
<div class="modal fade" tabindex="-1" role="dialog" id="evac_modal{{ $evacuation->id }}">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header bg-primary">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true" style="color: white;">&times;</span></button>
                <div class="modal-title text-center">
                    <h4 style="color: white;">{{ $evacuation->name }}</h4>
                    @if($evacuation->status == 1)
                    <span class="label label-success" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><span class="glyphicon glyphicon-ok" aria-hidden="true"></span> Available</span>
                    @elseif($evacuation->status == 2)
                    <span class="label label-warning" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><span class="glyphicon glyphicon-lock" aria-hidden="true"></span> Not Available</span>
                    @else:
                    <span class="label label-danger" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span> Full</span>
                    @endif
                </div>
            </div>
            <div class="modal-body">
                <strong>Address: </strong>{{ $evacuation->address }}<br>
                <strong>No. of Families: </strong>{{ $evacuation->family_no }}<br>
                <strong>Coordinator: </strong>{{ $evacuation->coordinator }}
                </ul>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
@endforeach



</body>
<script type="text/javascript" src="{{ asset('js/main.js') }}"></script>
<script>
var markers=[];
var map=null;
function initMap() {
    <?php $i=0; ?>
    @foreach($evacuations as $evacuation)
var uluru{{ $i+1 }} = {lat: {{ $evacuation->latitude }}, lng: {{ $evacuation->longitude }}};
{{--// var marker{{ $i+1 }} = new google.maps.Marker({--}}
{{--//    position: uluru{{ $i+1 }},--}}
{{--//    map: map,--}}
{{--//    title: '{{ $evacuation->name }}'--}}
{{--//  });--}}
<?php $i++; ?>
@endforeach
map = new google.maps.Map(document.getElementById('map'), {
zoom: 14,
center: {lat: 14.558387269243477, lng: 121.13609073337398},

});

<?php $i=-1; ?>
@foreach($evacuations as $evacuation)
<?php $i++; ?>
var iconName = "";
@if($evacuation->status == 1)
iconName = "house-green";
@elseif($evacuation->status == 2)
iconName = "house-yellow";
@else
iconName = "house-red";
@endif
var marker = null;

var evac_click{{ $evacuation->id }} = document.getElementById('evac_click{{ $evacuation->id }}');
evac_click{{ $evacuation->id }}.addEventListener('click', function() {
    for (var asdf=0; asdf<markers.length; asdf++) {
    markers[asdf].setMap(null);
    }
    document.getElementById('latitude').value = {{ $evacuation->latitude }};
    document.getElementById('longitude').value = {{ $evacuation->longitude }};
    var evacuation_name = '{{ $evacuation->name }}';
    {{--// plot(map,$('#evac_modal1').modal('show');--}}
    marker = new google.maps.Marker({
    position: {lat: {{ $evacuation->latitude }}, lng: {{ $evacuation->longitude }}},
    map: map,
    title: '{{ $evacuation->name }}'
    });
    //    	map = new google.maps.Map(document.getElementById('map'), {
    //   zoom: 16,
    //   center: {lat: {{ $evacuation->latitude }}, lng: {{ $evacuation->longitude }}},

    // });
    map.panTo({lat: {{ $evacuation->latitude }}, lng: {{ $evacuation->longitude }}});
    marker.setMap(map);
    markers.push(marker);

    marker.addListener('click', function() {
        $('#evac_modal{{ $evacuation->id }}').modal('show');
    });
{{--// window.location.href = "{{ base_url() }}evacuation-direction/{{ $evacuation->id }}";--}}
});


@endforeach

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