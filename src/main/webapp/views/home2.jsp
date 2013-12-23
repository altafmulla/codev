<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<title>Home</title>
<link rel="stylesheet" href="resources/style/demo.css" media="screen">
		<link rel="stylesheet" href="resources/style/style.css" media="screen">
		<link rel="stylesheet" href="resources/dojo/1.9.1/dijit/themes/claro/claro.css" media="screen">
<script src="resources/dojo/1.9.1/dojo/dojo.js"
		data-dojo-config="isDebug:1, async:1"></script>
	<script>
		require([ "dojo/dom", "dojo/fx", "dojo/domReady!" ], function(dom, fx) {
			// The piece we had before...
			var greeting = dom.byId("greeting");
			greeting.innerHTML += " from Dojo!";

			// ...but now, with a fun animation!
			fx.slideTo({
				top : 100,
				left : 200,
				node : greeting
			}).play();
		});
	</script>
</head>
<body class="claro">
	<div id="appLayout" class="demoLayout" data-dojo-type="dijit/layout/BorderContainer" data-dojo-props="design: 'sidebar'">
			<div class="centerPanel" data-dojo-type="dijit/layout/StackContainer" data-dojo-props="region: 'center',id: 'contentStack'">
				<div data-dojo-type="dijit/layout/ContentPane" title="Group 1">
					<h4>Group 1 Content</h4>
					<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et
					dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex
					ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu
					fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt
					mollit anim id est laborum.</p>
				</div>
				<div data-dojo-type="dijit/layout/ContentPane" title="Group Two">
					<h4>Group 2 Content</h4>
				</div>
				<div data-dojo-type="dijit/layout/ContentPane" title="Long Tab Label for this One">
					<h4>Group 3 Content</h4>
				</div>
			</div>
			<div class="edgePanel" data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region: 'top'">Header content (top)</div>
			<div class="edgePanel" data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region: 'bottom'">
				<div data-dojo-type="dijit/layout/StackController" data-dojo-props="containerId:'contentStack'"></div>
			</div>
			<div id="leftCol" class="edgePanel" data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region: 'left', splitter: true">
				Sidebar content (left)
			</div>
		</div>
		<!-- load dojo -->
		<script>
			require(["dojo/parser", "dijit/layout/BorderContainer", "dijit/layout/ContentPane",
					"dijit/layout/StackContainer", "dijit/layout/StackController", "dojo/domReady!"], function(parser){
				parser.parse();
			});
		</script>

</body>
</html>
