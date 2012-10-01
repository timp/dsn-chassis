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
        addressOutputID: null,
        longitudeInputID: null,
        latitudeInputID: null,
        marker: null,

        /**
         * Constructor
         */
        init: function(element) {
            var map = this;

            // Init object attributes
            var placeOnPage = Dom.getElementsByClassName("fb-map-gmap-div", null, element)[0];
            map.element = element;
            map.addressOutputID = Dom.getElementsByClassName("fb-map-address", null, element)[0].id;
            map.longitudeInputID = Dom.getElementsByClassName("fb-map-longitude", null, element)[0].id;
            map.latitudeInputID = Dom.getElementsByClassName("fb-map-latitude", null, element)[0].id;

            var myOptions = {
               		zoom: 9,
               		mapTypeId: google.maps.MapTypeId.ROADMAP
            };

            map.gmap = new google.maps.Map(placeOnPage, myOptions);
            map.geocoder = new google.maps.Geocoder();
           
            // Set location
            var initialLatitude = Document.getValue(map.latitudeInputID);
            var initialLongitude = Document.getValue(map.longitudeInputID);
            if (initialLatitude != "" && initialLongitude != "") {
                var latLng = new google.maps.LatLng(new Number(initialLatitude), new Number(initialLongitude));
                map.updateMarkerFromLatLng(latLng);
            } else {
                map.updateMarkerFromAddress();
            }
        },
        
        updateMarkerFromAddress: function () {
        	var map = this;
        	 var address = Document.getValue(map.addressOutputID);;
        	    map.geocoder.geocode( { 'address': address}, function(results, status) {
        	      if (status == google.maps.GeocoderStatus.OK) {
        	    	  map.updateMarkerFromLatLng(results[0].geometry.location);
        	      } 
        	    });

        },

        updateMarkerFromLatLng: function(latLng) {
            var map = this;
            map.gmap.setCenter(latLng);
            if (map.marker == null) {
                map.marker = new google.maps.Marker( {
                		map: map.gmap,
                		position: latLng,
                		draggable: true
                	});
                
                google.maps.event.addListener(map.marker, "dragend", function(event) { map._updateLongLat(event.latLng); });
            } else {
                map.marker.setPosition(latLng);
            }
            map._updateLongLat(latLng);
        },

        _updateLongLat: function(longLat) {
            Document.setValue(this.longitudeInputID, longLat.lng());
            Document.setValue(this.latitudeInputID, longLat.lat());
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
