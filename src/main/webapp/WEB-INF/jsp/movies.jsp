<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:forEach items="${category.movies}" var="movie" varStatus="rowStatus">
  <div class="flip-card col-12 col-sm-6 col-md-4 col-lg-3 box bg-white rounded">
    <div class="flip-card-inner">
      <div class="flip-card-front mybox shadow" >
        <div>
          <h3>
            <c:if test="${not empty movie.imdbUrl}">
              <a href="${movie.imdbUrl}" target="_blank">
            </c:if>
                ${movie.title}
          <c:if test="${movie.isNew()}">
            <span class="icon-new">neu</span>
          </c:if>
            <c:if test="${not empty movie.imdbUrl}">
              </a>
            </c:if>
          </h3>
          <div class="mb-4">
            ${movie.originalTitle}
          </div>
        </div>
        <div>
          <div class="critic-data">
            <a href="${movie.tvSpielfilmDetailUrl}" target="_blank">
              <span class="mr-2 editorial-rating small${movie.tvSpielfilmRating}">
                <c:if test="${movie.tipp}">
                  <span class="icon-tip">TIPP</span>
                </c:if>
              </span>
            </a>
            <span class="imdbRating" data-toggle="modal" data-target="#exampleModal${movie.id}">
              <c:if test="${not empty movie.imdbRating}">
                <strong><span>${movie.imdbRating}</span></strong><span class="grey">/</span><span class="grey">10</span>
              </c:if>
            </span>

            <div class="modal fade" id="exampleModal${movie.id}" tabindex="-1" data-backdrop="false" role="dialog" aria-labelledby="exampleModalLabel${movie.id}" aria-hidden="true">
              <div class="modal-dialog" role="document">
                <div class="modal-content">
                  <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel${movie.id}">Change IMDb-Infos</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                      <span aria-hidden="true">&times;</span>
                    </button>
                  </div>
                  <div class="modal-body">
                    <div class="input-group mb-3">
                    <input type="text" class="form-control" placeholder="IMDb-Url" aria-label="IMDb-Url" aria-describedby="basic-addon2" name="url">
                      <div class="input-group-append">
                        <button data-id="${movie.id}" class="update-single btn btn-outline-secondary" type="button">Submit</button>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <span class="metascore_w larger tvshow ${movie.metacriticClass}">${movie.metacriticRating}</span>
          </div>
          <div class="mt-2">
            <b>
              ${movie.awards}
            </b>
          </div>
        </div>
          <div class="mt-4">
            <span>${movie.genre}</span>
            <span>${movie.country}</span>
            <span>${movie.year}</span>
            <c:if test="${not empty movie.stars}">
              <div>
                Darsteller: ${movie.stars}
              </div>
            </c:if>
            <div>
              Regie: ${movie.director}
            </div>
          </div>
          <div class="d-flex justify-content-center">
            <div class="flex-column">
              <c:forEach var="airingData" items="${movie.airingDatas}">
                <div class="airing-data d-flex align-items-center justify-content-around">
                  <span class="chl_bg_m c-${airingData.channel.toLowerCase()}" title="${airingData.channel}"></span>
                  <span class="mr-1">${airingData.date}</span>
                  <span>${airingData.time}</span>
                  <c:if test="${airingData.canRecord}">
                    <c:set var="recordClass">
                      <c:choose>
                        <c:when test="${airingData.recorded}">
                          recorded
                        </c:when>
                        <c:otherwise>
                          record
                        </c:otherwise>
                      </c:choose>
                    </c:set>
                    <span class="${recordClass} editable" data-id="${airingData.id}"><span class="dot"></span><span class="text">REC</span></span>
                  </c:if>
                </div>
              </c:forEach>
            </div>
          </div>
        <div id="carouselExampleIndicators${movie.id}" class="carousel slide mt-4" data-ride="carousel" data-interval="false" data-interval="10">
          <ol class="carousel-indicators">
          <c:forEach items="${movie.images}" varStatus="status">
            <li data-target="#carouselExampleIndicators${movie.id}" data-slide-to="${status.index}" <c:if test="${status.first}">class="active"</c:if>></li>
          </c:forEach>
          </ol>
          <div class="carousel-inner">
            <c:forEach var="image" items="${movie.images}" varStatus="status">
            <div class="carousel-item <c:if test="${status.first}">active</c:if>">
              <img class="d-block" src="${image}" alt="First slide">
            </div>
            </c:forEach>
          </div>
          <a class="carousel-control-prev" href="#carouselExampleIndicators${movie.id}" role="button" data-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="sr-only">Previous</span>
          </a>
          <a class="carousel-control-next" href="#carouselExampleIndicators${movie.id}" role="button" data-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="sr-only">Next</span>
          </a>
        </div>
        <i class="fas fa-redo do-rotate"></i>
      </div>
      <div class="flip-card-back shadow">
        <div>
            ${movie.description}
        </div>
        <i class="fas fa-undo do-rotate"></i>
      </div>
    </div>
  </div>
</c:forEach>
