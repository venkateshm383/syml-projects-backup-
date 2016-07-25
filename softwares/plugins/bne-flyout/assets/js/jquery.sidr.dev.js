/*! Sidr - v1.2.1 - 2013-11-06
 * https://github.com/artberri/sidr
 * Copyright (c) 2013 Alberto Varela; Licensed MIT */

/*
 *	== Custom BNE Edits ==
 *
 *	1) 	June 9 2014 - changed body css overflow-x to overflow (prevents scrolling on pushed/covered body).
 *	2) 	Aug 18 2014 - Fixed bug where body classes were not added if sidr displacement was set to slide.
 *	3) 	Nov 13, 2014 - Add Top and Bottom locations
 *	4) 	Nov 27, 2014 - Add scrollbar test and moved html/body overflow css into bne-flyout.css using .flyout-lock and .flyout-margin class selectors.
 *	5) 	March 20, 2015 - Add fix for windows 8 devices that use touch and can use a mouse.
 *	6)	April 20, 2015 - Removed "visible" check when sidr opens.
 *
*/


;(function( $ ){

	// Detect OSX Scrollbars Test
	// From: https://gist.github.com/lutzissler/97f81c9f3aefdf7700e8
	$(function () {
		var scrolltest = $('<div style="width:100px;height:100px;overflow:scroll;position:absolute;top:-9999px;"/>'),
			scrolltestDom = scrolltest.get(0);
		scrolltest.appendTo("body");
		if (scrolltestDom.offsetWidth === scrolltestDom.clientWidth) {
			$("html").addClass("hiddenscroll");
		} else {
			$("html").addClass("shownscroll");
		}
		scrolltest.remove();

	});

	// Begin Sidr JS
	var sidrMoving = false,
		sidrOpened = false;

	// Private methods
	var privateMethods = {
    	// Check for valids urls
		// From : http://stackoverflow.com/questions/5717093/check-if-a-javascript-string-is-an-url
		isUrl: function (str) {
			var pattern = new RegExp('^(https?:\\/\\/)?'+ // protocol
				'((([a-z\\d]([a-z\\d-]*[a-z\\d])*)\\.)+[a-z]{2,}|'+ // domain name
				'((\\d{1,3}\\.){3}\\d{1,3}))'+ // OR ip (v4) address
				'(\\:\\d+)?(\\/[-a-z\\d%_.~+]*)*'+ // port and path
				'(\\?[;&a-z\\d%_.~+=-]*)?'+ // query string
				'(\\#[-a-z\\d_]*)?$','i'); // fragment locator

			if(!pattern.test(str)) {
				return false;
      		} else {
	  			return true;
      		}
    	},

		// Loads the content into the menu bar
		loadContent: function($menu, content) {
			$menu.html(content);
    	},

		// Add sidr prefixes
		addPrefix: function($element) {
			var elementId = $element.attr('id'),
				elementClass = $element.attr('class');
			if(typeof elementId === 'string' && '' !== elementId) {
				$element.attr('id', elementId.replace(/([A-Za-z0-9_.\-]+)/g, 'sidr-id-$1'));
      		}
	  		if(typeof elementClass === 'string' && '' !== elementClass && 'sidr-inner' !== elementClass) {
	  			$element.attr('class', elementClass.replace(/([A-Za-z0-9_.\-]+)/g, 'sidr-class-$1'));
      		}
	  		$element.removeAttr('style');
    	},

		execute: function(action, name, callback) {
			// Check arguments
			if(typeof name === 'function') {
				callback = name;
				name = 'sidr';
      		} else if(!name) {
	  			name = 'sidr';
      		}

	  		// Declaring
	  		var $menu = $('#' + name),
	  			$body = $($menu.data('body')),
	  			$html = $('html'),
	  			menuWidth = $menu.outerWidth(true),
	  			menuHeight = $menu.outerHeight(true),
	  			speed = $menu.data('speed'),
	  			side = $menu.data('side'),
	  			displace = $menu.data('displace'),
	  			onOpen = $menu.data('onOpen'),
	  			onClose = $menu.data('onClose'),
	  			bodyAnimation,
	  			menuAnimation,
	  			scrollTop,
	  			bodyClass = (name === 'sidr' ? 'sidr-open' : 'sidr-open ' + name + '-open');


	  		// BNE EDIT - Removing the visible check allows us to not have to use "display: none" CSS.
	  		// This then allows iframes and Google Maps to display properly after the flyout is open.

	  		// Open Sidr
	  		//if('open' === action || ('toggle' === action && !$menu.is(':visible') ) ) {
	  		if('open' === action || 'toggle' === action  ) {

	  			// Check if we can open it
	  			//if( $menu.is(':visible') || sidrMoving ) {
	  			//	return;
	  			//}

	  			// If another menu opened close first
	  			if(sidrOpened !== false) {
	  				methods.close(sidrOpened, function() {
	  					methods.open(name);
	  				});

	  				return;
        		}

				// Lock sidr
				sidrMoving = true;

				// Sidr Location
				// BNE EDIT - Added Top and Bottom locations
				if(side === 'left') {
					bodyAnimation = {left: menuWidth + 'px'};
					menuAnimation = {left: '0px'};
        		} else if(side === 'right') {
					bodyAnimation = {right: menuWidth + 'px'};
					menuAnimation = {right: '0px'};
        		} else if(side === 'top') {
					bodyAnimation = {top: menuHeight + 'px'};
					menuAnimation = {top: '0px'};
        		} else if(side === 'bottom') {
					bodyAnimation = {top: '-' + menuHeight + 'px'};
					menuAnimation = {bottom: '0px'};
        		}

				// Prepare page if container is body
				if($body.is('body')){
					//scrollTop = $html.scrollTop();
					//$html.css('overflow-x', 'hidden').scrollTop(scrollTop);
					$html.addClass('flyout-lock flyout-margin');
        		}


				// Open menu
				if(displace){
					$body.addClass('sidr-animating').css({
						width: $body.width(),
						position: 'absolute'
          			}).animate(bodyAnimation, speed, function() {
		  				$(this).addClass(bodyClass);
          			});
        		} else {
	        		$body.addClass('sidr-animating');
					setTimeout(function() {
						// BNE EDIT - body classes were not being added if slide displacement.
						//$(this).addClass(bodyClass);
						$('body').addClass(bodyClass);
          			}, speed);
        		}

				$menu.css('display', 'block').animate(menuAnimation, speed, function() {
					sidrMoving = false;
					sidrOpened = name;
					// Callback
					if(typeof callback === 'function') {
						callback(name);
          			}
		  			$body.removeClass('sidr-animating');
        		});

				// onOpen callback
				onOpen();


	  		// Close Sidr
      		} else {
	  			// Check if we can close it
	  			if( !$menu.is(':visible') || sidrMoving ) {
	  				return;
	  			}

	  			// Lock sidr
	  			sidrMoving = true;

				// Sidr Location
				// BNE EDIT - Added Top and Bottom locations
				if(side === 'left') {
					bodyAnimation = {left: 0};
					menuAnimation = {left: '-' + menuWidth + 'px'};
				} else if(side === 'right') {
					bodyAnimation = {right: 0};
					menuAnimation = {right: '-' + menuWidth + 'px'};
				} else if(side === 'top') {
					bodyAnimation = {top: 0};
					menuAnimation = {top: '-' + menuHeight + 'px'};
				} else if(side === 'bottom') {
					bodyAnimation = {top: 0};
					menuAnimation = {bottom: '-' + menuHeight + 'px'};
				}


				// Close menu
				if($body.is('body')){
					//scrollTop = $html.scrollTop();
					//$html.removeAttr('style').scrollTop(scrollTop);
					$html.removeClass('flyout-lock flyout-margin');
				}

				$body.addClass('sidr-animating').animate(bodyAnimation, speed).removeClass(bodyClass);
				$menu.animate(menuAnimation, speed, function() {
					$menu.removeAttr('style').hide();
					$body.removeAttr('style');
					//$('html').removeAttr('style');
					sidrMoving = false;
					sidrOpened = false;

					// Callback
					if(typeof callback === 'function') {
						callback(name);
					}

					$body.removeClass('sidr-animating');
				});

				// onClose callback
				onClose();
			}
		}
	};

	// Sidr public methods
	var methods = {
    	open: function(name, callback) {
			privateMethods.execute('open', name, callback);
		},
    	close: function(name, callback) {
			privateMethods.execute('close', name, callback);
		},
    	toggle: function(name, callback) {
			privateMethods.execute('toggle', name, callback);
		},
		reload: function(name, source, callback){
        	parseSource(name, source);
		}
	};

	$.sidr = function( method ) {

    	if ( methods[method] ) {
			return methods[method].apply( this, Array.prototype.slice.call( arguments, 1 ));
		} else if ( typeof method === 'function' || typeof method === 'string' || ! method ) {
			return methods.toggle.apply( this, arguments );
		} else {
			$.error( 'Method ' + method + ' does not exist on jQuery.sidr' );
		}
	};

	$.fn.sidr = function( options ) {

    	var settings = $.extend( {
			name          : 'sidr',         // Name for the 'sidr'
			speed         : 300,            // Accepts standard jQuery effects speeds (i.e. fast, normal or milliseconds)
			side          : 'left',         // Accepts 'left' or 'right'
			source        : null,           // Override the source of the content.
			renaming      : true,           // The ids and classes will be prepended with a prefix when loading existent content
			body          : 'body',         // Page container selector,
			displace      : true,           // Displace the body content or not
			onOpen        : function() {},  // Callback when sidr opened
			onClose       : function() {}   // Callback when sidr closed
		},
		options);

		var name = settings.name,
        	$sideMenu = $('#' + name);

		// If the side menu do not exist create it
		if( $sideMenu.length === 0 ) {
			$sideMenu = $('<div />')
			.attr('id', name)
			.appendTo($('body'));
		}

		// Adding styles and options
		$sideMenu.addClass('sidr').addClass(settings.side).data({
			speed		: settings.speed,
			side		: settings.side,
			body		: settings.body,
			displace	: settings.displace,
			renaming	: settings.renaming,
			onOpen		: settings.onOpen,
			onClose		: settings.onClose
		});

		parseSource(name, settings.source );

		return this.each(function(){
			var $this = $(this),
				data = $this.data('sidr');

			// If the plugin hasn't been initialized yet
			if ( ! data ) {

				$this.data('sidr', name);
				if('ontouchstart' in document.documentElement) {
					$this.bind('touchstart', function(e) {
						var theEvent = e.originalEvent.touches[0];
						this.touched = e.timeStamp;
					});
					$this.bind('touchend', function(e) {
						var delta = Math.abs(e.timeStamp - this.touched);
						if(delta < 200) {
							e.preventDefault();
							methods.toggle(name);
						}
					});
					// 	BNE EDIT
					//	Fix for Windows 8 devices that use touch and a mouse.
					$this.click(function(e) {
						e.preventDefault();
						methods.toggle(name);
					});
				} else {
					$this.click(function(e) {
						e.preventDefault();
						methods.toggle(name);
					});
				}
			}
		});
	};

    /**
     * Parsing source
     * @access private
     * @param string name
     * @param string|function source
     */
    function parseSource(name, source){

        var $sideMenu = $('#' + name);

        // The menu content
        if(typeof source === 'function') {
            var newContent = source(name);
            privateMethods.loadContent($sideMenu, newContent);
        }
        else if(typeof source === 'string' && privateMethods.isUrl(source)) {
            $.get(source, function(data) {
                privateMethods.loadContent($sideMenu, data);
            });
        }
        else if(typeof source === 'string') {
            var htmlContent = '',
                selectors = source.split(',');

            $.each(selectors, function(index, element) {
                htmlContent += '<div class="sidr-inner">' + $(element).html() + '</div>';
            });

            // Renaming ids and classes
            if( $sideMenu.data('renaming') ) {
                var $htmlContent = $('<div />').html(htmlContent);
                $htmlContent.find('*').each(function(index, element) {
                    var $element = $(element);
                    privateMethods.addPrefix($element);
                });
                htmlContent = $htmlContent.html();
            }

            privateMethods.loadContent($sideMenu, htmlContent);

        }
        else if(source !== null) {
            $.error('Invalid Sidr Source');
        }
    }

})( jQuery );