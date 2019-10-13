<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<link rel="stylesheet" href="/webjars/bootstrap/4.3.1/css/bootstrap.min.css">
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css" integrity="sha384-oS3vJWv+0UjzBfQzYUhtDYW+Pj2yciDJxpsK1OYPAYjqT085Qq/1cq5FLXAZQ7Ay" crossorigin="anonymous">
<link rel="stylesheet" href="/css/styles.css">
<script src="/webjars/jquery/jquery.min.js" ></script>
<script src="/webjars/bootstrap/4.3.1/js/bootstrap.min.js" ></script>
<script src="/webjars/sockjs-client/sockjs.min.js"></script>
<script src="/webjars/stomp-websocket/stomp.min.js"></script>
<script src="/js/app.js"></script>
</head>
<body>
<div class="overlay"></div>

    <svg viewBox="0 0 36 36" class="circular-chart spinning">
      <path class="circle-bg"
          d="M18 2.0845
            a 15.9155 15.9155 0 0 1 0 31.831
            a 15.9155 15.9155 0 0 1 0 -31.831"
        />
      <path class="circle"
        stroke-dasharray="75, 100"
        d="M18 2.0845
          a 15.9155 15.9155 0 0 1 0 31.831
          a 15.9155 15.9155 0 0 1 0 -31.831"
      />
      <text x="18" y="20.35" class="percentage"></text>
    </svg>

  <div class="container mt-4">
    <%@include file="navigation.jsp" %>
   <div class="tab-content" id="nav-tabContent">
    <c:forEach var="category" items="${categories}" varStatus="status">
      <c:if test="${not empty category.movies}">
        <div class="tab-pane fade <c:if test="${status.first}">show active</c:if>" id="category${status.index}" role="tabpanel">
          <div class="row">
          <%@include file="movies.jsp" %>
          </div>
        </div>
      </c:if>
    </c:forEach>
    </div>
    <div class="d-block d-sm-none">
      <%@include file="navigation.jsp" %>
    </div>
  </div>


</body>
</html>
