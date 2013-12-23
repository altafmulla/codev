<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<title>Home</title>
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
	<script src="resources/home.js"></script>
</head>
<body class="claro">
	<h1>Hello world!</h1>
<h1 id="greeting">Hello</h1>
<link rel="stylesheet" href="resources/dojo/1.9.1/dijit/themes/claro/claro.css">
	


	<h1>Hello Spring MySQL!</h1>

	<h4>Database Info:</h4>
	DataSource:
	<c:out value="${dbinfo}" />
	</br>

	<h4>Current States:</h4>
	<c:if test="${not empty states}">
		<p>
			<c:forEach var="state" items="${states}">
				<c:out value="${state}" />
				</br>
			</c:forEach>
		</p>
	</c:if>
	<c:if test="${empty states}">
		<p>No States found</p>
	</c:if>


</body>
</html>
