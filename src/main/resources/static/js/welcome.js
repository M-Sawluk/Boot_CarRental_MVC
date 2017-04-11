$(document).ready(function(){
  $('.scroll').click(function() {
    if (location.pathname.replace(/^\//,'') == this.pathname.replace(/^\//,'')
    && location.hostname == this.hostname) {
      var $target = $(this.hash);
      $target = $target.length && $target
      || $('[name=' + this.hash.slice(1) +']');
      if ($target.length) {
        var targetOffset = $target.offset().top;
        $('html,body')
        .animate({scrollTop: targetOffset}, 1000);
       return false;
      }
    }
  });
});

setTimeout(function () {
    $('#btt').addClass('slide-in-elliptic-right-fwd').css('display','block');
    $('#arrow').addClass('slide-in-elliptic-right-fwd').css('display','block');
},800)

var http = new XMLHttpRequest();
var url ='http://localhost:8080/rest/cars';
var method = 'GET';
http.open(method,url);

http.onreadystatechange=function () {
    if(http.readyState == XMLHttpRequest.DONE && http.status === 200){
        var data=JSON.parse(http.responseText);

        appendCarouselSlides(data);

    }else if(http.readyState === XMLHttpRequest.DONE){
        console.log('error');
    }
}
http.send();


function appendCarouselSlides(data){

    setTimeout(function () {

        for (var i = 0; i < data.length; i++) {
            var pic = "url(../cars/" + data[i].carId.toUpperCase() + ".png)";
            $('#' + i).css('background', pic.toString());
            $('<div style="padding-top: 367px ; padding-left: 30px" align="left" id="line' + i + '">').appendTo('#' + i);
            $('</br>').appendTo('#line' + i);
            $('</br>').appendTo('#line' + i);
            $('<span class="fontz"><b>' + data[i].manufacturer + '  ' + data[i].name + '</b></span></br>').appendTo('#line' + i);
            $('<span class="fontz"><b>Price: ' + data[i].price + ' zl/day </b></span></br>').appendTo('#line' + i);
            $('<span><a href="/renting?carId=' + data[i].carId + '"} class="btn btn-block btn-gradient-blue">Rent!</a></span>')
                .appendTo('#line' + i);
        }
    },50)


}

$(document).ready(function(){
    $('.carousel').carousel();

    setInterval(function(){
        $('.carousel').carousel('next');
    }, 5000);
});