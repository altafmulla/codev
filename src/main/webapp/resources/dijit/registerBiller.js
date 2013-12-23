

define([
    	"dojo/_base/declare",
    	"dijit/_WidgetBase",
    	"dijit/_OnDijitClickMixin",
    	"dijit/_TemplatedMixin",
    	"dijit/_WidgetsInTemplateMixin",
    	"dojo/text!./templates/registerBiller.html",
    	"dojo/store/Memory",
    	"dijit/form/FilteringSelect",
    ],function(declare, _WidgetBase, _OnDijitClickMixin, _TemplatedMixin,_WidgetsInTemplateMixin, template,Memory,FilteringSelect){

    	return declare([_WidgetBase, _OnDijitClickMixin, _TemplatedMixin,_WidgetsInTemplateMixin,], {
    		//	set our template
    		templateString: template,
    		 widgetsInTemplate: true,
    		//	some properties
    		baseClass: "someWidget",


    		//	define an onClick handler
    		_onClick: function(){
    		},

    		postCreate: function(){
    			this._societyReferenceData();
    			this._apartmentBuildingsReferenceData();
    			this._apartmentsReferenceData();
    			//this._ownershipTypeReferenceData();
    			this._utilityTypeReferenceData();
    			
    		},
    		
    		_societyReferenceData: function(){
    			var xhrArgs = {
  				      url: "welcome/"+"referenceData/"+"society",
  				      handleAs: "json",
  				      load: function(data){
  				    	var societyStore = new Memory({
  							idProperty: "abbreviation",
  							data: data
  						});
  				    	var select = new FilteringSelect({
  							name: "complex",
  							placeHolder: "Select Apartment Complex",
  							store: societyStore,
  							onChange: function(type) {
  	    						dijit.byId('building').query = {filter: (type == null) ? "*" : type};
  	    					}
  						}, "complex");
  						//select.startup();
  				      },
  				      error: function(error){
  				    	 console.log(error);
  				      }
  				    };
    			 var deferred = dojo.xhrGet(xhrArgs);
    		},
    		_apartmentBuildingsReferenceData: function(){
    			var xhrArgs = {
    				      url: "welcome/"+"referenceData/"+"apartmentBuildings",
    				      handleAs: "json",
    				      load: function(data){
    				    	var societyStore = new Memory({
    							idProperty: "abbreviation",
    							data: data
    						});
    				    	
    				    	var select = new FilteringSelect({
    							name: "building",
    							placeHolder: "Select Apartment Building",
    							store: societyStore,
      							onChange: function(type) {
      	    						dijit.byId('apartment').query = {filter: (type == null) ? "*" : type};
      	    					}
    						}, "building");
    						//select.startup();
    				      },
    				      error: function(error){
    				    	 console.log(error);
    				      }
    				    };
      			 var deferred = dojo.xhrGet(xhrArgs);
    		},
    		_apartmentsReferenceData: function(){
    			var xhrArgs = {
  				      url: "welcome/"+"referenceData/"+"apartments",
  				      handleAs: "json",
  				      load: function(data){
  				    	var societyStore = new Memory({
  							idProperty: "abbreviation",
  							data: data
  						});
  				    	
  				    	var select = new FilteringSelect({
  							name: "apartment",
  							placeHolder: "Select Apartments",
  							store: societyStore
  						}, "apartment");
  						//select.startup();
  				      },
  				      error: function(error){
  				    	 console.log(error);
  				      }
  				    };
    			 var deferred = dojo.xhrGet(xhrArgs);
    		},
    		_ownershipTypeReferenceData :function(){
    			var xhrArgs = {
    				      url: "welcome/"+"referenceData/"+"ownershipType",
    				      handleAs: "json",
    				      load: function(data){
    				    	var societyStore = new Memory({
    							idProperty: "abbreviation",
    							data: data
    						});
    				    	
    				    	var select = new FilteringSelect({
    							name: "ownershipType",
    							placeHolder: "Select Ownership Type",
    							store: societyStore
    						}, "ownershipType");
    						//select.startup();
    				      },
    				      error: function(error){
    				    	 console.log(error);
    				      }
    				    };
      			 var deferred = dojo.xhrGet(xhrArgs);
    		},
			_utilityTypeReferenceData: function(){
				var xhrArgs = {
  				      url: "welcome/"+"referenceData/"+"utilityType",
  				      handleAs: "json",
  				      load: function(data){
  				    	var societyStore = new Memory({
  							idProperty: "abbreviation",
  							data: data
  						});
  				    	
  				    	var select = new FilteringSelect({
  							name: "utilitytype",
  							placeHolder: "Select Utility Type",
  							store: societyStore
  						}, "utilitytype");
  						//select.startup();
  				      },
  				      error: function(error){
  				    	 console.log(error);
  				      }
  				    };
    			 var deferred = dojo.xhrGet(xhrArgs);
			}
    		
    		
    		
    	});
    });