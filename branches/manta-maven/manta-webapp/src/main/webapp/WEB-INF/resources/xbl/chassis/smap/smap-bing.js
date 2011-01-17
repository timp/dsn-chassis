/**
 * Copyright (C) 2011 WWARN.
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

    var geocodingID = null;
    /**
     * Map object constructor
     */
    var ChassisMap = function(element, id) { this.init(element, id); }
    ChassisMap.prototype = {

        /**
         * Attributes
         */
    	mapID: null,
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
        init: function(element, id) {
            var map = this;

            // Init object attributes
            map.mapID = id;
            var placeOnPage = Dom.getElementsByClassName("fb-map-gmap-div", null, element)[0];
            map.element = element;
            map.addressOutputID = Dom.getElementsByClassName("fb-map-address", null, element)[0].id;
            map.longitudeInputID = Dom.getElementsByClassName("fb-map-longitude", null, element)[0].id;
            map.latitudeInputID = Dom.getElementsByClassName("fb-map-latitude", null, element)[0].id;

            var myOptions = {
            			credentials:"AnPD5BIrpP1C7YX07eKAs9MEi6jhdU6DiS90oTpEX8wJrAoWyPasOA0p3vNZmtKb",
            			mapTypeId: Microsoft.Maps.MapTypeId.road,
            			zoom: 9
            			};               		
            
            map.gmap = new Microsoft.Maps.Map(placeOnPage, myOptions);
           
            // Set location
            var initialLatitude = Document.getValue(map.latitudeInputID);
            var initialLongitude = Document.getValue(map.longitudeInputID);
            if (initialLatitude != "" && initialLongitude != "") {
                var latLng = new Microsoft.Maps.Location(new Number(initialLatitude), new Number(initialLongitude));
                map.updateMarkerFromLatLng(latLng);
            } else {
                map.updateMarkerFromAddress();
            }
        },
        
        MakeGeocodeRequest: function(credentials)
        {
        	var map = this;
        	
        	geocodingID = map.mapID;
        	var address = Document.getValue(map.addressOutputID);
        	var geocodeRequest = "https://dev.virtualearth.net/REST/v1/Locations/" + address + "?output=json&jsonp=myCallback&key=" + credentials;

        	map.CallRestService(geocodeRequest);
        },

        CallRestService: function (request) {
        	var script = document.createElement("script");
        	script.setAttribute("type", "text/javascript");
        	script.setAttribute("src", request);
        	document.body.appendChild(script);
        },
        
        GeocodeCallback: function (result) {   
        	var map = this;
        	if (result &&
                    result.resourceSets &&
                    result.resourceSets.length > 0 &&
                    result.resourceSets[0].resources &&
                    result.resourceSets[0].resources.length > 0) {
        		var loc = result.resourceSets[0].resources[0];
        		var country = loc.address.countryRegion;
        		//map.setAddressSelection(country);
        		var point = loc.point;
        		var location = new Microsoft.Maps.Location(point.coordinates[0], point.coordinates[1]);

        		map.updateMarkerFromLatLng(location);
            }
	    	
        },


        updateMarkerFromAddress: function () {
        	var map = this;
        	 
        	//map.gmap.getCredentials(map.MakeGeocodeRequest);
        	var myOptions = "AnPD5BIrpP1C7YX07eKAs9MEi6jhdU6DiS90oTpEX8wJrAoWyPasOA0p3vNZmtKb";
        	map.MakeGeocodeRequest(myOptions);
        },

        updateMarkerFromLatLng: function(latLng) {
            var map = this;
            map.gmap.setView({center:latLng});
            if (map.marker == null) {
            	map.marker = new Microsoft.Maps.Pushpin (latLng, { draggable: true, text: '1'});
                Microsoft.Maps.Events.addHandler(map.marker, "mouseup", function(event) { map._updateLongLat(event.target.getLocation()); })
                map.gmap.entities.push(map.marker);
            } else {
                map.marker.setLocation(latLng);
            }
            map._updateLongLat(latLng);
        },

        _updateLongLat: function(longLat) {
            Document.setValue(this.longitudeInputID, longLat.longitude);
            Document.setValue(this.latitudeInputID, longLat.latitude);
        },
        
        setAddressSelection: function (country) {
        	var map = this;
        	var curr = Document.getValue("country");
        	Document.setValue("country", country);
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
                chassisMaps[mapID] = new ChassisMap(container, mapID);
            }
        },

        /**
         * Called when the address provided to the component changes.
         */
        addressXFormsValueChanged: function(target) {
            var container = YAHOO.util.Dom.getAncestorByClassName(target, "xbl-cggh-smap");
            var mapID =  container.id;
            chassisMaps[mapID].updateMarkerFromAddress();
        },
        geocode: function(target) {
            var mapID =  geocodingID;
        	var maps = chassisMaps;
            var map = chassisMaps[mapID];
            map.GeocodeCallback(target);
        }
    };

})();

function myCallback(event) {
	ORBEON.widget.MapEvents.geocode(event);
}