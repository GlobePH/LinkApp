  var geocoder;
  var map;
  
  function initialize() {
    var geocoder = new google.maps.Geocoder();
  }

  function codeAddress(address) {
    var geocoder = new google.maps.Geocoder();
    geocoder.geocode( { 'address': address}, function(results, status) {
      if (status == 'OK') {
        var latlngg = results[0].geometry.location.lat() +" "+ results[0].geometry.location.lng();
        alert(latlngg);
      } else {
        alert('Geocode was not successful for the following reason: ' + status);
      }
    });
  }
