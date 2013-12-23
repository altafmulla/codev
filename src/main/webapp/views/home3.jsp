<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<title>Home</title>
<link rel="stylesheet" href="resources/style/demo.css" media="screen">
<link rel="stylesheet" href="resources/style/style.css" media="screen">
<link rel="stylesheet"
	href="resources/dojo/1.9.1/dijit/themes/claro/claro.css" media="screen">
<script>
	var dojoConfig = (function() {
		var base = location.href.split("/");
		base.pop();
		base = base.join("/");

		return {
			async : true,
			isDebug : true,
			parseOnLoad : true,
			packages : [ {
				name : "demo",
				location : base + "/resources"
			} ],
			flickrRequest : {
				apikey : "8c6803164dbc395fb7131c9d54843627",
				sort : [ {
					attribute : "datetaken",
					descending : true
				} ]
			}
		};
	})();
</script>
<script src="resources/dojo/1.9.1/dojo/dojo.js"></script>

</head>
<body class="claro">
	
	<!-- overlay -->
	<div id="loadingOverlay" class="pageOverlay">
		<div class="loadingMessage">Loading...</div>
	</div>
	<div id="appLayout" class="demoLayout"
		data-dojo-type="dijit/layout/BorderContainer"
		data-dojo-props="design: 'sidebar'">
		<div class="centerPanel" data-dojo-type="dijit/layout/StackContainer"
			id="tabs" data-dojo-props="region: 'center'">
			<div data-dojo-type="dijit/layout/ContentPane"
				data-dojo-props="title: 'About'">

				<h2>Flickr keyword photo search</h2>
				<p>Each search creates a new tab with the results as thumbnails</p>
				<p>Click on any thumbnail to view the larger image</p>

			</div>
			
		</div>
		<div class="edgePanel" data-dojo-type="dijit/layout/ContentPane"
			data-dojo-props="region: 'top'">
			<div class="searchInputColumn">
				<div class="searchInputColumnInner">
					<input id="searchTerms" placeholder="search terms">
				</div>
			</div>
			<div class="searchButtonColumn">
				<button id="searchBtn">Search</button>
			</div>
		</div>
		<div class="edgePanel" data-dojo-type="dijit/layout/ContentPane"
			data-dojo-props="region: 'bottom'">
			<div data-dojo-type="dijit/layout/StackController"
				data-dojo-props="containerId:'contentStack'"></div>
		</div>
		<div id="leftCol" class="edgePanel"
			data-dojo-type="dijit/layout/ContentPane"
			data-dojo-props="region: 'left', splitter: true">Sidebar
			content (left)</div>
	</div>
	<!-- load dojo -->
	
	<script>
		require([ "demo/app", "dojo/domReady!" ], function(demoApp) {
			demoApp.init();
		});
	</script>

</body>
</html>
