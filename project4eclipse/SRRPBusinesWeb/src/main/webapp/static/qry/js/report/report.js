(function(){
//auto show & hide
	$.fn.smint=function(options){
		$(this).addClass('smint');
		var settings=$.extend({'scrollSpeed':500},options);
		return $('.smint').each(function(){
			if(settings.scrollSpeed){var scrollSpeed=settings.scrollSpeed}
			var stickyTop=$('.smint').offset().top;
			var stickyMenu=function(){
				var scrollTop=$(window).scrollTop();
				if (scrollTop > 10){
					$('.smint').css("position","fixed");
					$('.smint').css("left",(document.body.clientWidth-900-120-160)/2);
					$('.smint').css("top",10);
					}else{
						$('.smint').css("position","absolute");
						$('.smint').css("left","-140px");
						$('.smint').css("top","10px");
						}
				};
			stickyMenu();
			$(window).scroll(function(){stickyMenu();});
			$(window).resize(function(){stickyMenu();});
			});
		}

	$(document).ready(function(){
		$('#smintBox').smint({'scrollSpeed':1000});
	})
})();




