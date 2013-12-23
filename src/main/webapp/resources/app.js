define([
	"dojo/dom", 
	"dojo/dom-style", 
	"dojo/dom-class", 
	"dojo/dom-construct", 
	"dojo/dom-geometry", 
	"dojo/string", 
	"dojo/on", 
	"dojo/aspect", 
	"dojo/keys", 
	"dojo/_base/config",
	"dojo/_base/lang", 
	"dojo/_base/fx", 
	"dijit/registry", 
	"dojo/parser",
	"dijit/layout/ContentPane", 
	"dojox/data/FlickrRestStore", 
	"dojox/image/LightboxNano",
	"dojo/text!demo/dijit/welcomePage.html",
	"dojo/text!demo/dijit/registerBillerPage.html",
	"dojo/text!demo/dijit/updateBillPage.html",
	"dojo/dom-form",
	"dojo/query",
	"demo/module"
], function(dom, domStyle, domClass, domConstruct, domGeometry, string, on, aspect, keys, config, lang, baseFx, registry, parser, ContentPane, FlickrRestStore, LightboxNano,welcome,registerBiller,updateBiller,domForm,query) {
	var store = null,
		preloadDelay = 500,
		flickrQuery = config.flickrRequest || {},

		itemTemplate = '<img src="${thumbnail}">${title}',
		itemClass = 'item',
		itemsById = {},

		largeImageProperty = "media.l", // path to the large image url in the store item
		thumbnailImageProperty = "media.t", // path to the thumb url in the store item

		startup = function() {

			// create the data store
			var flickrStore = store = new FlickrRestStore();

			// build up and initialize the UI
			initUi();

			// put up the loading overlay when the 'fetch' method of the store is called
			aspect.before(store, "fetch", function() {
					startLoading(registry.byId("tabs").domNode);
				});
		},

		endLoading = function() {
			// summary: 
			// 		Indicate not-loading state in the UI

			baseFx.fadeOut({
				node: dom.byId("loadingOverlay"),
				onEnd: function(node){
					domStyle.set(node, "display", "none");
				}
			}).play();
		},

		startLoading = function(targetNode) {
			// summary: 
			// 		Indicate a loading state in the UI

			var overlayNode = dom.byId("loadingOverlay");
			if("none" == domStyle.get(overlayNode, "display")) {
				var coords = domGeometry.getMarginBox(targetNode || document.body);
				domGeometry.setMarginBox(overlayNode, coords);

				// N.B. this implementation doesn't account for complexities
				// of positioning the overlay when the target node is inside a 
				// position:absolute container
				domStyle.set(dom.byId("loadingOverlay"), {
					display: "block",
					opacity: 1
				});
			}
		},
		initUi = function() {
			// summary: 
			// 		create and setup the UI with layout and widgets

			// create a single Lightbox instnace which will get reused
			lightbox = new LightboxNano({});

			// set up ENTER keyhandling for the search keyword input field
			on(dom.byId("searchTerms"), "keydown", function(evt){
				if(evt.keyCode == keys.ENTER){
					evt.preventDefault();
					//doSearch();
					doSearchSociety();
				}
			});

			// set up click handling for the search button
			on(dom.byId("searchBtn"), "click", doSearch);
			
			// set up click handling for the search Society button
			//on(dom.byId("searchSocietyBtn"), "click", doSearchSociety);
			dojo.connect(dojo.byId("searchSocietyBtn"), "click",doSearchSociety);
			dojo.connect(dojo.byId("RegisterBillerBtn"), "click",doRegisterBillerPage);
			
			
			
			launchUpdateButton();
			
			
			endLoading();
		},
		launchUpdateButton = function() {
			count =1;
			query('[title$=\"UpdateBill\"]').forEach(function(node){
				//alert(node);
				//alert(dojo.byId("UpdateBill1"));
				dojo.connect(dojo.byId("UpdateBill"+count), "click",function(evt){
					
					var contr = registry.byId("tabs"); 
				       // contr.innerHTML="Welcome to "+data.fullName;
				        

					var xhrArgs = {
				      url: "welcome/"+"retreiveBill/"+node.id.substring(10),
				      handleAs: "json",
				      headers: { "Content-Type": "application/json"},
				      load: function(data){
				    	 console.log("retreiveBill success"+data);

	// create the new tab panel for this search
						var panel = new ContentPane({
							title: "Update bill",
							content: lang.replace(updateBiller, data),
							//content:data,
							closable: true
						});
						contr.addChild(panel);
						// make this tab selected
						contr.selectChild(panel);
						
		    			dojo.connect(dojo.byId("UpdateBillBtn"), "click",_doUpdateBill);
				      },
				      error: function(error){
				    	  console.log("retreiveBill failed");
				    	 console.log(error);
				      }
				    };

			 var deferred = dojo.xhrGet(xhrArgs);
					
				
					
				});
				count++;
				
			});
		},
		doSearch = function() {
			// summary: 
			// 		inititate a search for the given keywords
			var terms = dom.byId("searchTerms").value;
			if(!terms.match(/\w+/)){
				return;
			}
			var listNode = createTab(terms);
			var results = store.fetch({
				query: lang.delegate(flickrQuery, {
					text: terms
				}), 
				count: 10,
				onItem: function(item){
					// first assign and record an id
					// render the items into the <ul> node
					var node = renderItem(item, listNode);
				},
				onComplete: endLoading
			});
		},
		doSearchSociety = function(event) {
			
			console.debug("searchSocietyBtn clicked");
			var xhrArgs = {
				      url: "welcome/"+"society/"+dom.byId("searchTerms").value,
				      //postData: dojo.toJson({key1:"value1",key2:{key3:"value2"}}),
				      handleAs: "json",
				      load: function(data){
				        dojo.byId("response2").innerHTML = "Message posted.";
				        var contr = registry.byId("tabs"); 
				       // contr.innerHTML="Welcome to "+data.fullName;
				        
				        
				     // create the new tab panel for this search
						var panel = new ContentPane({
							title: data.society.name, 
							content: lang.replace(welcome, data),
							closable: true
						});
						contr.addChild(panel);
						// make this tab selected
						contr.selectChild(panel);
				        
				        
				      },
				      error: function(error){
				        // We'll 404 in the demo, but that's okay.  We don't have a 'postIt' service on the
				        // docs server.
				        dojo.byId("response2").innerHTML = "Message posted.";
				      }
				    }
				    dojo.byId("response2").innerHTML = "Message being sent...";
				    // Call the asynchronous xhrPost
				    var deferred = dojo.xhrGet(xhrArgs);
				  
		},
		_doRegisterBiller= function(){
			var formJson = domForm.toJson("myform");
			console.debug (formJson);
			console.debug("_doRegisterBiller clicked");
			var xhrArgs = {
				      url: "welcome/"+"referenceData/"+"registerBiller",
				      postData: domForm.toJson("myform"),
				      handleAs: "json",
				      headers: { "Content-Type": "application/json"},
				      load: function(data){
				    	 console.log("registerBiller success");
				      },
				      error: function(error){
				    	  console.log("registerBiller failed");
				    	 console.log(error);
				      }
				    };
			 var deferred = dojo.xhrPost(xhrArgs);
		},
		_doUpdateBill= function(){
			var formJson = domForm.toJson("updateForm");
			console.debug (formJson);
			console.debug("_doUpdateBill clicked");
			var xhrArgs = {
				      url: "welcome/"+""+"updateBill",
				      postData: domForm.toJson("updateForm"),
				      handleAs: "json",
				      headers: { "Content-Type": "application/json"},
				      load: function(data){
				    	 console.log("_doUpdateBill success");
				      },
				      error: function(error){
				    	  console.log("_doUpdateBill failed");
				    	 console.log(error);
				      }
				    };
			 var deferred = dojo.xhrPost(xhrArgs);
		},
		
		doRegisterBillerPage = function(event) {
			
			console.debug("RegisterBtn clicked");
			var xhrArgs = {
				      url: "welcome/"+"registerBillerPage",
				      //postData: dojo.toJson({key1:"value1",key2:{key3:"value2"}}),
				      handleAs: "json",
				      load: function(data){
				        dojo.byId("response2").innerHTML = "Register Message posted.";
				        var contr = registry.byId("tabs"); 
				       // contr.innerHTML="Welcome to "+data.fullName;
				        
				        
				     // create the new tab panel for this search
						var panel = new ContentPane({
							title: "Register Biller",
							content: lang.replace(registerBiller, data),
							closable: true
						});
						contr.addChild(panel);
						// make this tab selected
						contr.selectChild(panel);
		    			dojo.connect(dojo.byId("RegisterBtn"), "click",_doRegisterBiller);
				        
				      },
				      error: function(error){
				        // We'll 404 in the demo, but that's okay.  We don't have a 'postIt' service on the
				        // docs server.
				        dojo.byId("response2").innerHTML = "Register Message error.";
				      }
				    }
				    dojo.byId("response2").innerHTML = "Register Message being sent...";
				    // Call the asynchronous xhrPost
				    var deferred = dojo.xhrGet(xhrArgs);
				  
		},
updateBillPage = function() {
			
			console.debug("Update Bill clicked");
			dom.byId("result2").innerHTML += "Thank you! ";
			 var contr = registry.byId("tabs"); 
							       // contr.innerHTML="Welcome to "+data.fullName;
							        
							        
							     // create the new tab panel for this search
									/*var panel = new ContentPane({
										title: "Update bill",
										content: 'Update biller Page'+billId,
										closable: true
									});
									contr.addChild(panel);
									// make this tab selected
									contr.selectChild(panel);*/
				  
		},
		showImage = function (url, originNode){
			// summary: 
			// 		Show the full-size image indicated by the given url
			lightbox.show({ 
				href: url, origin: originNode 
			});
		},
		createTab = function (term, items) {
			// summary: 
			// 		Handle fetch results

			var contr = registry.byId("tabs"); 
			var listNode = domConstruct.create("ul", {
				"class": "demoImageList",
				"id": "panel" + contr.getChildren().length
			});

			// create the new tab panel for this search
			var panel = new ContentPane({
				title: term, 
				content: listNode,
				closable: true
			});
			contr.addChild(panel);
			// make this tab selected
			contr.selectChild(panel);

			// connect mouse click events to our event delegation method
			var hdl = on(listNode, "click", onListClick);
			return listNode;
		},

		showItemById = function (id, originNode) {
			var item = itemsById[id];
			if(item) {
				showImage(lang.getObject(largeImageProperty, false, item), originNode);
			}
		},
		onListClick = function (evt) {
			var node = evt.target, 
				containerNode = registry.byId("tabs").containerNode;

			for(var node = evt.target; (node && node !==containerNode); node = node.parentNode){
				if(domClass.contains(node, itemClass)) {
					showItemById(node.id.substring(node.id.indexOf("_") +1), node);
					break;
				}
			}
		},
		renderItem = function(item, refNode, posn) {
			// summary: 
			// 		Create HTML string to represent the given item
			itemsById[item.id] = item;
			var props = lang.delegate(item, {
				thumbnail: lang.getObject(thumbnailImageProperty, false, item)
			});

			return domConstruct.create("li", {
				"class": itemClass,
				id: refNode.id + "_" + item.id,
				innerHTML: string.substitute(itemTemplate, props)
			}, refNode, posn);
		};

	return {
		init: function() {
			startLoading();

			// register callback for when dependencies have loaded
			startup();

		}
	};

});