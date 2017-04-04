$(document).ready(function() {
    $('#list').click(function(event){event.preventDefault();
    				$('#products .item').addClass('list-group-item');});
    $('#grid').click(function(event){event.preventDefault();$('#products .item').removeClass('list-group-item');
    $('#products .item').addClass('grid-group-item');});

    var cars = document.querySelectorAll('.thumbnail');



	  $('#searchbutton').click(function() {

	  	var min =parseInt($('#min').val());
	  	var max =parseInt($('#max').val());
	  	var search =$('#search').val().toUpperCase();

	  	console.log(search);
	  	$("#products").empty();

	  	if(isNaN(min) && isNaN(max) && search==0){
	  		for(var i=0; i<cars.length;i++){
                $('<div class="item  col-xs-4 col-lg-4" id="'+i+'">').appendTo('#products');
                $('</div>').appendTo('#products');
                $(cars[i]).appendTo('#'+i+'');

			}

		}else if((min>=0 || isNaN(min)) && max>0 && search==0){
            for(var i=0; i<cars.length;i++){
                var priceToSplit = cars[i].querySelector('p').textContent.split(' ');
                if((priceToSplit[1]>=min || isNaN(min)) && priceToSplit[1]<=max) {
                    $('<div class="item  col-xs-4 col-lg-4" id="' + i + '">').appendTo('#products');
                    $('</div>').appendTo('#products');
                    $(cars[i]).appendTo('#' + i + '');
                }
            }
		}
        else if((min>=0 || isNaN(min))  && max>0 && search!=0){
            for(var i=0; i<cars.length;i++){
                var priceToSplit = cars[i].querySelector('p').textContent.split(' ');
                var name = cars[i].querySelector('h4').textContent.toUpperCase();
                var manufacturer = cars[i].querySelector('h3').textContent.split(' ');
                if((priceToSplit[1]>=min || isNaN(min)) && priceToSplit[1]<=max
					&&(search===name || search===manufacturer[1].toUpperCase())) {
                    $('<div class="item  col-xs-4 col-lg-4" id="' + i + '">').appendTo('#products');
                    $('</div>').appendTo('#products');
                    $(cars[i]).appendTo('#' + i + '');
                }
            }
        }
        else if((isNaN(min) || min==0) && (isNaN(max)|| max==0) && search!=0){
            for(var i=0; i<cars.length;i++){
                var name = cars[i].querySelector('h4').textContent.toUpperCase();
                var manufacturer = cars[i].querySelector('h3').textContent.split(' ');
                if(search===name || search===manufacturer[1].toUpperCase()) {
                    $('<div class="item  col-xs-4 col-lg-4" id="' + i + '">').appendTo('#products');
                    $('</div>').appendTo('#products');
                    $(cars[i]).appendTo('#' + i + '');
                }
            }
        }
        else if((isNaN(min) || min>=0) && isNaN(max) && search==0){
            for(var i=0; i<cars.length;i++){
                var priceToSplit = cars[i].querySelector('p').textContent.split(' ');
                if((priceToSplit[1]>=min || isNaN(min))) {
                    $('<div class="item  col-xs-4 col-lg-4" id="' + i + '">').appendTo('#products');
                    $('</div>').appendTo('#products');
                    $(cars[i]).appendTo('#' + i + '');
                }
            }
        }


	  });
});




