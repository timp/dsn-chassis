/**
 * Copyright (C) 2009 Orbeon, Inc.
 *
 * This program is free software; you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation; either version
 * 2.1 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * The full text of the license is available at http://www.gnu.org/copyleft/lesser.html
 */
(function() {
    var Dom = YAHOO.util.Dom,
        Event = YAHOO.util.Event,
        Lang = YAHOO.lang,
        Document = ORBEON.xforms.Document;

    /**
     * Singleton with information about about each map, indexed by ID of the component.
     */
    var chassisMaps = {};

    /**
     * Map object constructor
     */
    var ChassisMap = function(element) { this.init(element); }
    ChassisMap.prototype = {

        /**
         * Attributes
         */
        element: null,
        gmapDiv: null,
        gmap: null,
        geocoder: null,
        keyOutputID: null,
        /* Commented out for reference but not used as reverse geocoding doesn't work too well
        countryInputID: null,
        regionInputID: null,
        districtInputID: null,
        localityInputID: null,
        */
        addressInputID: null,
        longitudeInputID: null,
        latitudeInputID: null,
        marker: null,

        /**
         * Constructor
         */
        init: function(element) {
            var map = this;

            // Init object attributes
            map.gmapDiv = Dom.getElementsByClassName("fb-map-gmap-div", null, element)[0];
            map.element = element;
            /* These need to be set up in the xbl file and as attributes on the cggh:smap element
            map.countryInputID = Dom.getElementsByClassName("fb-map-country", null, element)[0].id;
            map.regionInputID = Dom.getElementsByClassName("fb-map-region", null, element)[0].id;
            map.districtInputID = Dom.getElementsByClassName("fb-map-district", null, element)[0].id;
            map.localityInputID = Dom.getElementsByClassName("fb-map-locality", null, element)[0].id;
            */
            map.addressOutputID = Dom.getElementsByClassName("fb-map-address", null, element)[0].id;
            map.longitudeInputID = Dom.getElementsByClassName("fb-map-longitude", null, element)[0].id;
            map.latitudeInputID = Dom.getElementsByClassName("fb-map-latitude", null, element)[0].id;

            // Create map with its controls
            map.gmap = new GMap2(map.gmapDiv);
            map.gmap.addControl(new GSmallZoomControl3D());
            map.gmap.addControl(new GScaleControl());
            map.gmap.addControl(new GOverviewMapControl());
            map.geocoder = new GClientGeocoder();

            // Set location
            var initialLatitude = Document.getValue(map.latitudeInputID);
            var initialLongitude = Document.getValue(map.longitudeInputID);
            if (initialLatitude != "" && initialLongitude != "") {
                var latLng = new GLatLng(new Number(initialLatitude), new Number(initialLongitude));
                map.updateMarkerFromLatLng(latLng);
            } else {
                map.updateMarkerFromAddress();
            }
        },

        updateMarkerFromAddress: function() {
            var map = this;
            var address = Document.getValue(map.addressOutputID);
            this.geocoder.getLatLng(address, function(latLng) {
                if (latLng != null) {
                    map.updateMarkerFromLatLng(latLng);
                }
            });
        },

        updateMarkerFromLatLng: function(latLng) {
            var map = this;
            map.gmap.setCenter(latLng, 13);
            if (map.marker == null) {
                map.marker = new GMarker(latLng, {draggable: true});
                map.gmap.addOverlay(map.marker);
                GEvent.addListener(map.marker, "dragend", function(latLng) { map._updateLongLat(latLng); });
            } else{
                map.marker.setLatLng(latLng);
                map._updateLongLat(latLng);
            }
        },

        _updateLongLat: function(longLat) {
        	var map = this;
            Document.setValue(map.longitudeInputID, longLat.lng());
            Document.setValue(map.latitudeInputID, longLat.lat());
            /* It works but not that well and takes too long
            map.geocoder.getLocations(longLat, function (response){
            	if (!response || response.Status.code != 200) {
            	    alert("Status Code:" + response.Status.code);
            	  } else {
            	    place = response.Placemark[0];
            	    address = place.AddressDetails;
            	    if (address.Accuracy > 4) {
            	    	locality = address.Country.Locality.LocalityName;
            	    	Document.setValue(map.localityInputID, locality);
            	    }
            	    point = new GLatLng(place.Point.coordinates[1],place.Point.coordinates[0]);
            	    marker = new GMarker(point);
            	    map.gmap.addOverlay(marker);
            	    marker.openInfoWindowHtml(
            	        '<b>orig latlng:</b>' + response.name + '<br/>' + 
            	        '<b>latlng:</b>' + place.Point.coordinates[1] + "," + place.Point.coordinates[0] + '<br>' +
            	        '<b>Status Code:</b>' + response.Status.code + '<br>' +
            	        '<b>Status Request:</b>' + response.Status.request + '<br>' +
            	        '<b>Address:</b>' + place.address + '<br>' +
            	        '<b>Accuracy:</b>' + place.AddressDetails.Accuracy + '<br>' +
            	        '<b>Country code:</b> ' + place.AddressDetails.Country.CountryNameCode);
            	  }

            });
            */
        }
    };

    ORBEON.widget.MapEvents = {

        /**
         * Called when the group containing the map becomes enabled.
         */
        mapContainerXFormsEnabled: function(target) {
            var container = YAHOO.util.Dom.getAncestorByClassName(target, "xbl-cggh-smap");
            var mapID =  container.id;
            if (!Lang.isObject(chassisMaps[mapID])) {
                chassisMaps[mapID] = new ChassisMap(container);
            }
        },

        /**
         * Called when the address provided to the component changes.
         */
        addressXFormsValueChanged: function(target) {
            var container = YAHOO.util.Dom.getAncestorByClassName(target, "xbl-cggh-smap");
            var mapID =  container.id;
            chassisMaps[mapID].updateMarkerFromAddress();
        }
    };

})();
