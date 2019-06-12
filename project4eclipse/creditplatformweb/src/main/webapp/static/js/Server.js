/*---------------------------Server,Page-------------------------*/
var Server = {};
Server.RequestMap = {};
Server.Pool = [];

Server.getXMLHttpRequest = function(){
	for(var i=0;i<Server.Pool.length;i++){
		if(Server.Pool[i][1]=="0"){
			Server.Pool[i][1] = "1";
			return Server.Pool[i][0];
		}
	}
	var request;
	if (window.XMLHttpRequest){
		request = new XMLHttpRequest();
	}else if(window.ActiveXObject){
		for(var i =5;i>1;i--){
      try{
        if(i==2){
					request = new ActiveXObject( "Microsoft.XMLHTTP" );
        }else{
					request = new ActiveXObject( "Msxml2.XMLHTTP." + i + ".0" );
        }
      }catch(ex){}
    }
	}
	Server.Pool.push([request,"1"]);
	return request;
}

Server.loadURL = function(url,func){
	var Request = Server.getXMLHttpRequest();
	Request.open("GET", url, true);
	Request.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
	Request.setRequestHeader('contentType', 'text/html;charset=uft-8');
	Request.onreadystatechange = function(){
		if (Request.readyState==4&&Request.status==200) {
			try{
				if(func){
					func(Request.responseText);
				};
			}finally{
				for(var i=0;i<Server.Pool.length;i++){
					if(Server.Pool[i][0]==Request){
						Server.Pool[i][1] = "0";
						break;
					}
				}
				Request = null;
				func = null;
			}
		}
	}
	Request.send(null);
}

Server.loadScript = function(url){
	document.write('<script type="text/javascript" src="' + Server.ContextPath+url + '"><\/script>') ;
}

Server.loadCSS = function(url){
	if(isGecko){
		var e = document.createElement('LINK') ;
		e.rel	= 'stylesheet' ;
		e.type	= 'text/css' ;
		e.href	= url ;
		document.getElementsByTagName("HEAD")[0].appendChild(e) ;
	}else{
		document.createStyleSheet(url);
	}
}

Server.getOneValue = function(methodName,dc,func){//dc�ȿ���һ��DataCollection��Ҳ������һ��ֵ
	if(dc&&dc.prototype==DataCollection.prototype){
		Server.sendRequest(methodName,dc,func);
	}else{
		var dc1 = new DataCollection();
		dc1.add("_Param0",dc);
		Server.sendRequest(methodName,dc1,func);
	}
}


Server.sendRequest = function(sUrl,dataCollection,func,id,waitMsg){
	var url = Server.ContextPath+sUrl;
	if(dataCollection){
		url += encodeURL(htmlEncode(dataCollection.toXML()));
	}
	Server._ResponseDC = null;
	Request.onreadystatechange = function(){Server.onRequestComplete(Request,func);};
	Request.send(url);
}

Server.onRequestComplete = function(Request,func){
	if (Request.readyState==4&&Request.status==200) {
		try{
			var xmlDoc = Request.responseXML;
			var dc = new DataCollection();
			if(xmlDoc){
				if(dc.parseXML(xmlDoc)){
					dc["Status"] = dc.get("_ZVING_STATUS");
					dc["Message"] = dc.get("_ZVING_MESSAGE");
					if(dc.get("_ZVING_SCRIPT")){
						eval(dc.get("_ZVING_SCRIPT"));
					}
				}
				Server._ResponseDC = dc;
				xmlDoc = null;
			}else{
					dc["Status"] = 0;
					dc["Message"] = "���������쳣,δ��ȡ�����!";
			}
			if(func){
				func(dc);
			}
		//}catch(ex){
		//	alert("Server.onRequestComplete:"+ex.message+"\t"+ex.lineNumber);
		}finally{
			for(var i=0;i<Server.Pool.length;i++){
				if(Server.Pool[i][0]==Request){
					Server.Pool[i][1] = "0";
					break;
				}
			}
			Request = null;
			func = null;
		}
	}
}

Server.getResponse = function(){
	return Server._ResponseDC;
}

Server.Events = {};
Server.registerEvent = function(methodName,func){//������methodʱִ����Ӧ�ĺ���ú���ֵΪfalse�˳���ã������̨����ָ��
	var arr = Server.Events[methodName];
	if(!arr){
		arr = [];
	}
	arr.push(func);
}

Server.executeRegisteredEvent = function(methodName,dc){
	var arr = Server.Events[methodName];
	if(!arr){
		return true;
	}
	for(var i=0;i<arr.length;i++){
		if(!arr[i].apply(null,[dc,methodName])){
			return false;
		}
	}
	return true;
}



