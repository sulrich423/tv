var stompClient = null;

var progress = 0;

function connect() {
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/movies', function (greeting) {
            showGreeting(JSON.parse(greeting.body).value);
        });
        sendData();
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}

function sendData() {
    stompClient.send("/app/update", {}, JSON.stringify({'date': $('.page-item:nth-child(2)').data('date')}));
}

function showGreeting(message) {
  if(message != 'done') {
    progress++;
    var value = parseInt(progress * 100 / message);
    $('.circular-chart').removeClass('spinning');
    $('.circle').attr('stroke-dasharray', value + ', 100');
    $('.circular-chart text').html(value + '%')
  } else {
    $('.overlay').hide();
    $('.circular-chart').hide();
    disconnect();
    progress = 0;
    location.reload();
  }
}

function calcHeight() {
  var rows = $('.row:visible');
  for(var i = 0; i < rows.length; i++) {
    var boxes = $(rows[i]).find('.mybox');
    var numberOfTilesPerLine = Math.min(Math.floor($(window).width() / boxes.width()),4);
    for(var j = 0; j < boxes.length; j+=numberOfTilesPerLine) {
      for(var k = 0; k < $(boxes[j]).children().length; k++) {
        $(boxes.slice(j, j + numberOfTilesPerLine)
            .map(function(i,e) {
              return $(e).children()[k]
            }
          )).equalizeHeights();
        $(boxes.slice(j, j + numberOfTilesPerLine)
            .map(function(i,e) {
              $(e).parent().parent().height($(boxes[j]).outerHeight());
              $(e).css('visibility','visible');
            }
          ));
      }
    }
  }
}


$(function () {
    $( "#update" ).click(function() {
      $('.overlay').show();
      $('.circular-chart').show();
      connect();
    });

    $('.record, .recorded').click(function() {
      var el = $(this)
      $.post('/toggleRecord/', {"id" : $(this).data('id')}, function(responseData) {
        if (!responseData.success) {
          el.toggleClass('record recorded');
          el.addClass('editable');
        }
      });
      el.toggleClass('record recorded');
      el.removeClass('editable');
    });

    $.fn.equalizeHeights = function() {
      var maxHeight = this.map(function( i, e ) {
        return Math.round($( e ).height());
      }).get();
      return this.height( Math.max.apply( this, maxHeight ) );
    };

    $(document).ready(calcHeight);

    $('a[data-toggle="list"]').on('shown.bs.tab', function (e) {
      calcHeight();
    })

    $('.do-rotate').click(function() {
      $(this).parent().parent().parent().toggleClass('rotate');
    });

    $('.update-single').click(function() {
      $.post('/updateSingle/', {"id" : $(this).data('id'), "url" : $(this).parent().parent().children('input').val() }, function(responseData) {
        if (responseData.success) {
          location.reload();
        }
      });
    });


});
