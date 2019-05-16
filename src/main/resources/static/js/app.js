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
    stompClient.send("/app/update", {}, JSON.stringify({'date': $('.pagination .active').data('date')}));
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

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#update" ).click(function() {
      $('.overlay').show();
      $('.circular-chart').show();
      connect();
    });

});
