function Progress(taskID,title,w,h){
	this.TaskID = taskID;
	this.Title = title;
	if(h){
		this.Height = h;
	}else{
		this.Height = 150;
	}
	if(w){
		this.Width = w;
	}else{
		this.Width = 400;
	}
}

Progress.getTopLevelWindow = function(){
	var pw = window;
	while(pw!=pw.parent){
		pw = pw.parent;
	}
	return pw;
}

Progress.prototype.show = function(func){
	var pw = Progress.getTopLevelWindow();
	var diag = new Dialog("DialogProgress"+this.TaskID);
	diag.Width = this.Width;
	diag.Height = this.Height;
	diag.Title = this.Title;
	diag.URL = "javascript:void(0);";
	var id = this.TaskID;
	diag.OKEvent = function(){
		Progress.stop(id);
	};
	diag.show();

	var win = pw.document.getElementById("_DialogFrame_"+diag.ID).contentWindow;
	var doc = win.document;
	doc.open();
	doc.write("<style>.progressBox{border:#ddd 1px solid}");
	doc.write(".progressBg{ background:#ddd url(" + IMAGESPATH + "progressBg.gif);}");
	doc.write(".progressLight{font-size:5px; line-height:5px; color:#99bb00; background:#99bb00}");
	doc.write(".progressShadow{font-size:5px; line-height:6px; color:#779911; background:#779911}");
	doc.write("table,td{border-collapse: collapse; border-spacing: 0;}</style>");
	doc.write("<body oncontextmenu='return false;'></body>") ;
	var arr = [];
	arr.push("<table width='100%' height='100%' border='0'><tr>");
	arr.push("  <td align='center' valign='middle'>");
	arr.push("	<div id='Info' style='text-align:left;width:100%;font-size:12px'>&nbsp;</div><br>");
	arr.push("      <table width='100%' border='1' cellpadding='1' cellspacing='0' class='progressBox'>");
	arr.push("      <tr><td class='progressBg'>");
	arr.push("				<table width='1%' border='0' cellpadding='0' cellspacing='0' id='tableP'>");
	arr.push("          <tr><td class='progressLight'>-</td></tr>");
	arr.push("          <tr><td class='progressShadow'>-</td></tr>");
	arr.push("        </table></td></tr></table></td></tr></table>");
	var div = doc.createElement("div");
	div.innerHTML = arr.join('');
	doc.body.appendChild(div);
	doc.close();
	//diag.ParentWindow.$$D = diag;
	diag.contentWindow = win;
	diag.okButton.value = "取消任务";
	diag.cancelButton.value = "关闭窗口";
	diag.onComplete = func;
	this.dialog = diag;
	Progress.getInfo(this.TaskID);
}

Progress.getInfo = function(taskID){
	var url = CTXPATH + "/main/progressInfo?id=" + taskID + "&" + Math.random();
	var $t = new Progress.Tools();
	$t.get({
		'url' : url,
		'success' : function(msg){
			var diag = Dialog.getInstance("DialogProgress"+taskID);
			if(diag == null){
				return;//有可能已经被用户手工关闭
			}
			var response = eval('(' + msg + ')');
			if(!response.success){
				Dialog.alert(response.message);
			}else{
				var info = response.data.currentInfo;
				if(info!=null){
					diag.contentWindow.document.getElementById("Info").innerHTML = info;
				}
				if(response.data.completeFlag == "1"){
					diag.contentWindow.document.getElementById("tableP").width = "100%";
					diag.okButton.style.display = "none";
					if(response.data.errorFlag != "1"){
						if(diag.onComplete){
							try{
								diag.onComplete();
							}catch(ex){
								alert(ex.message);
							}
						}
					}
				}else{
					var percent = response.data.percent;
					if(percent=="0"){
						percent = "1";
					}
					diag.contentWindow.document.getElementById("tableP").width = percent + "%";
					window.setTimeout(function(){
						Progress.getInfo(taskID);
					},1000);
				}
			}
		},
		'error' : function(msg){
			Dialog.alert("ERROR:" + msg);
		}
	});
}

Progress.stop = function(taskID){
	var url = CTXPATH + "/main/stopProgress?id=" + taskID + "&" + Math.random();
	var $t = new Progress.Tools();
	$t.get({
		'url' : url,
		'success' : function(msg){
			var response = eval('(' + msg + ')');
			function isStopComplete(){
				$t.get({
					'url' : url,
					'success' : function(msg2){
						var response2 = eval('(' + msg2 + ')');
						if(response2.success){
							Dialog.alert("任务己取消");
							Dialog.getInstance("DialogProgress"+taskID).close();
						}else{
							window.setTimeout(isStopComplete,1000,taskID);
						}
					},
					'error' : function(msg){
						Dialog.alert(msg);
					}
				});
			}
			Dialog.getInstance("DialogProgress"+taskID).cancelButton.value = "正在取消任务...";
			Dialog.getInstance("DialogProgress"+taskID).cancelButton.disabled = true;
			isStopComplete();
		},
		'error' : function(msg){
			Dialog.alert("ERROR:" + msg);
		}
	});
}

Progress.Tools = function(){
	//ajax
	var _getXmlHttp = function() {
		var xmlhttp = null;
		if (window.XMLHttpRequest) {
			xmlhttp=new XMLHttpRequest();
		} else {
			if (window.ActiveXObject) {
				xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
			}
		}
		return xmlhttp;
	};
	var _stateChange = function(xmlHttp, oArgs){
		if ( xmlHttp.readyState == 4 ) {
			var text = xmlHttp.responseText;
			if ( xmlHttp.status == 200 ) {
				oArgs.success(text);
			} else {
				oArgs.error(text);
			}
		}
	};
	this.get = function(oArgs){
		var _xmlHttp = _getXmlHttp();
		_xmlHttp.onreadystatechange = function(){
			_stateChange(_xmlHttp, oArgs);
		};
		_xmlHttp.open("GET", oArgs.url, true);
		_xmlHttp.send(null);
	};
	//get
	this.getObj = function(id){
		return document.getElementById(id);
	};
	this.trim = function(str){
		return str.replace(/(^\s*)|(\s*$)/g,"");
	};
};
