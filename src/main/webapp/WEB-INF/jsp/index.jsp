<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>ZSJ Routes Export</title>
  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <link rel="stylesheet" href="/resources/demos/style.css">
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
  <script src="https://unpkg.com/leaflet@1.0.1/dist/leaflet.js"></script>
  <link rel="stylesheet" href="https://unpkg.com/leaflet@1.0.1/dist/leaflet.css" />
  <script>
  $( function() {
    var availableTags = [
       <c:set var="totalZsj" value="${fn:length(zsj)}" />
       <c:forEach items="${zsj}" var="entry" varStatus="zsjCounter">
              "${entry.value.nazev} - id:${entry.key}"
              <c:if test="${zsjCounter.count < totalZsj}">
              ,
              </c:if>
       </c:forEach>
    ];
    $( "#zsjfrom" ).autocomplete({
      source: availableTags
    });
  } );
  </script>
</head>

<body>
<h1>Zsj routes</h1>
<p>Generates SHP of routes from one ZSJ to all ZSJ in our database</p>
<div class="ui-widget" style="height: 100px;">
  <form action="/export" method="POST" ><br>
    <label for="tags">Zsj name (id): </label>
    <input id="zsjfrom" name="zsjfrom"/>
    <input type="submit" value="Generate SHP">
  </form>
</div>

<div id="mapid" style="width: 600px; height: 400px;"></div>
<script>
    function showExtent() {
        var key = document.getElementById("key");
        key.value = mymap.getBounds().getWest() + "," + mymap.getBounds().getSouth() + "," + mymap.getBounds().getEast() + "," + mymap.getBounds().getNorth();
    }

    function myZoomHandler() {
        var currentZoom = map.getZoom();
        if (currentZoom > 15 && map.hasLayer(heatmapLayer) == false) {
            district_boundary.addTo(mymap);
        } else {

        }
    }

	var mymap = L.map('mapid').setView([49.7, 16.2], 8);
	L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpandmbXliNDBjZWd2M2x6bDk3c2ZtOTkifQ._QA7i5Mpkd_m30IGElHziw', {
		maxZoom: 18,
		attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, ' +
			'<a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, ' +
			'Imagery © <a href="http://mapbox.com">Mapbox</a>',
		id: 'mapbox.streets'
	}).addTo(mymap);

	var district_boundary = new L.geoJson();

    $.ajax({
    dataType: "json",
    url: "JSON",
    success: function(data) {
        $(data.features).each(function(key, data) {
            district_boundary.addData(data);
        });
    }
    }).error(function() {});
</script>

</body>
</html>

<!--

<form action="/export" method="POST" ><br>
    <p>Zsj from: <select name="zsjfrom" multiple>
    <c:forEach items="${zsj}" var="entry">
        <option value="${entry.key}">${entry.value.nazev}</option>
    </c:forEach>
    </select>
    </p>
    <p>Zsj to: <select name="zsjto" multiple>
        <c:forEach items="${zsj}" var="entry">
            <option value="${entry.key}">${entry.value.nazev}</option>
        </c:forEach>
        </select>
        </p>
    <input type="submit" value="Generate SHP">
</form>
-->



