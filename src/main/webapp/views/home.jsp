<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<title>Home</title>
<link rel="stylesheet" href="resources/style/demo.css" media="screen">
<link rel="stylesheet" href="resources/style/style.css" media="screen">
<link rel="stylesheet"
	href="//ajax.googleapis.com/ajax/libs/dojo/1.9.2/dijit/themes/claro/claro.css" media="screen">
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
<script src="//ajax.googleapis.com/ajax/libs/dojo/1.9.2/dojo/dojo.js"></script>

</head>
<body class="claro">
	Testing...
	<!-- overlay -->
	<div id="loadingOverlay" class="pageOverlay">
		<div class="loadingMessage">Loading...</div>
	</div>
	<div id="appLayout" class="demoLayout"
		data-dojo-type="dijit/layout/BorderContainer"
		data-dojo-props="design: 'sidebar'">
		<div class="centerPanel" data-dojo-type="dijit/layout/TabContainer"
			id="tabs" data-dojo-props="region: 'center',tabPosition: 'bottom'">
			<div data-dojo-type="dijit/layout/ContentPane"
				data-dojo-props="title: 'About'">

				

<c:forEach items="${userSummary}" var="userSummaryvar">
    <h2>${userSummaryvar.firstName}</h2>
</c:forEach>
<c:choose>
<c:when test="${not empty billDetailList}">
<c:forEach items="${billDetailList}" var="billDetail" varStatus="status" >
    <p>${billDetail.billId}  :<a href="revenuereport/html?billId=${status.count}" target="_blank">View HTML</a>
     <a href="revenuereport/PDF?billId=${status.count}" target="_blank">View PDF</a>
     <button type="button" id="UpdateBill${status.count}" value="submit" title="UpdateBill">Update Bill
    
</button>

     </p>
     
</c:forEach>
</c:when>
<c:otherwise>

You have no registered Bills.Please register your bills from the Register Bills option. 

</c:otherwise>
</c:choose>
<div id="result2"></div>
<p>

<div class="searchButtonColumn">
				<button id="RegisterBillerBtn">Register Biller</button>
			</div>
</p>
			


			</div>
			
		</div>
		<div class="edgePanel" data-dojo-type="dijit/layout/ContentPane"
			data-dojo-props="region: 'top'">
			<a href="<c:url value="/j_spring_security_logout" />" > Logout</a>
			<div class="searchInputColumn">
				<div class="searchInputColumnInner">
					<input id="searchTerms" placeholder="search terms">
				</div>
			</div>
			<div class="searchButtonColumn">
				<button id="searchBtn">Search</button>
			</div>
			<div class="searchButtonColumn">
				<button id="searchSocietyBtn">Search Society</button>
			</div>
			<div id="response2"></div>
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
		require([ "demo/app", "demo/dijit/registerBiller","dijit/form/ComboBox","dojo/data/ItemFileWriteStore","dojo/data/ItemFileReadStore", "dijit/form/CheckBox","dijit/form/Button","dijit/form/DateTextBox","dijit/form/NumberTextBox","dojo/domReady!" ], function(demoApp,registerBiller,select,store,readstore,checkbox,button, dateBox, numberBox) {
			demoApp.init();
		});
	</script>

</body>
</html>
