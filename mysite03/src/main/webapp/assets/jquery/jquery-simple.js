// 간단 jQuery 만들기

let jQuery = function(param) {
	if(typeof(param)	=== 'function') {
		window.addEventListener('load', param);
		return;
	}
	
	if(typeof(param) === 'string') {
		let elements = document.querySelectorAll(param);
		return new _jQuery(elements);
	}	
}

let _jQuery = function(elements) {
	this.length = elements.length;
	for(let i = 0; i < elements.length; i++) {
		this[i] = elements[i];		
	}
}

_jQuery.prototype.jquery = 'jquery.simple.0.0.1';
_jQuery.prototype.css = function(name, value) {
	for(let i = 0; i < this.length; i++){
		this[i].style[name] = value;
	}	
}

_jQuery.prototype.click = function(handler) {
	for(let i = 0; i < this.length; i++){
		this[i].addEventListener('click', handler);
	}	
}

_jQuery.prototype.get = function(index) {
	return this[index];
}



let $ = jQuery;