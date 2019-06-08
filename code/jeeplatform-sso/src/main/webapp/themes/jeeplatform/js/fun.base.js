;function rand(mi,ma){   
	var range = ma - mi;
	var out = mi + Math.round( Math.random() * range) ;	
	return parseInt(out);
};	

function getViewSize(){
	var de=document.documentElement;
	var db=document.body;
	var viewW=de.clientWidth==0 ?  db.clientWidth : de.clientWidth;
	var viewH=de.clientHeight==0 ?  db.clientHeight : de.clientHeight;
	return Array(viewW,viewH);
}