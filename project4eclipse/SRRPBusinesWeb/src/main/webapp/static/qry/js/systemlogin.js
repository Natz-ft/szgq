		function disabledButton()
		{
			var btn = document.getElementsByTagName("input");
			for(i=0;i<btn.length;i++)
			{
				if(btn[i].type=="button")
				{
					btn[i].disabled="disabled";
				}
			}
		}
		function MM_swapImgRestore() { 
		  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
		}
		function MM_preloadimg() {
		  var d=document; if(d.img){ if(!d.MM_p) d.MM_p=new Array();
		    var i,j=d.MM_p.length,a=MM_preloadimg.arguments; for(i=0; i<a.length; i++)
		    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
		}
		
		function MM_findObj(n, d) { 
		  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
		    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
		  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
		  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
		  if(!x && d.getElementById) x=d.getElementById(n); return x;
		}
		
		function MM_swapImage() {
		  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
		   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
		}

		function init(){
			var password =document.all("form:pd1");
			if (password == null){
			}else{
		 		password.focus();
			}
		}
		document.onkeydown=function(event)
		{
			var userAgent = navigator.userAgent;
			var firefox = false;
			if(userAgent.indexOf("Firefox")>-1){
				firefox = true;
			}
			
			var event = event?event:((window.event)?window.event:null);
			if ((event.altKey)&&
			      ((event.keyCode==37)||   
			       (event.keyCode==39)||  
			       event.keyCode==13))     
			  {
			    event.returnValue=false;
			    return false;
			  }
			  
			  if 
			  (
			      // 屏蔽退格删除键
			      (event.keyCode==8 && (event.srcElement==null || (event.srcElement.type!="text" && event.srcElement.type!="textarea" && event.srcElement.type!="password")))  ||
			      (event.keyCode==116)||                 
			      (event.ctrlKey && event.keyCode==82)){ 
			     event.keyCode=0;                                                                         
			     event.returnValue=false;
			  }
			  if (event.keyCode==122){event.keyCode=0;event.returnValue=false;} 
			  if (event.ctrlKey && event.keyCode==78) event.returnValue=false;   // 屏蔽
																					
			  if (event.shiftKey && event.keyCode==121)event.returnValue=false;  // 屏蔽
			  
			  if(firefox){
				  if (event.target.tagName == "A" && event.shiftKey)
					  event.returnValue = false; 
				  
			  }else{
				  if (event.srcElement.tagName == "A" && event.shiftKey)
					  event.returnValue = false; 
			  }
																// 加鼠标左键新开一网页
			  if ((event.altKey)&&(event.keyCode==115))             
			  {
			      window.showModelessDialog("about:blank","","dialogWidth:1px;dialogheight:1px");
			      return false;
			  }
			  if(firefox){
				  if(event.target.type=="text"||event.target.type=='password'){
					  	if(event.keyCode==222){   
					  		event.returnValue=false;
					  		return false;
					  	}
					  }
			  }else{
				  if(event.srcElement.type=="text"||event.srcElement.type=='password'){
					  	if(event.keyCode==222){   
					  		event.returnValue=false;
					  		return false;
					  	}
					  }
			  }
			  if(firefox){
				  if(event.keyCode==13&&event.target.type=='password'){
				  	  var obj = document.getElementById("form:loginBt");
				  	  clickObj(obj);
				  }
			  }else{
				  if(event.keyCode==13&&event.srcElement.type=='password'){
				  	  document.all["form:loginBt"].click();	
				  }
			  }
			  
		};
		function clickObj(obj){
			var evt = document.createEvent("MouseEvents");
			evt.initEvent("click",true,true);
			obj.dispatchEvent(evt);
		}
		// 选择机构
		function OpenDeptSearchWindow()
		{
			var xposition = 0;
		    var yposition = 0;
		    var returnURL = "";
		    if ((parseInt(navigator.appVersion) >= 4 )) 
			{
		        xposition = (screen.width - 800) / 2;
		        yposition = (screen.height - 600) / 2;
		    }
		    var res = window.showModalDialog("departmentlist.jsf");
		    if (res == null || res == "") 
			{
		        return;
		    } 
		    else 
			{
		    	// 根据传回的机构信息，设置form中值
		    	var array = res.split("$$"); 
		    	alert("111");
				document.all["userinfovo.sdn"].value=array[0];
		    	var deptwholename = array[1];
				document.all["deptname"].innerHTML=deptwholename;
		    }
		}
		
		var cookies = new Object();
		function extractCookies()
		{
		   var name, value;
		   var beginning, middle, end;
		   for (name in cookies)
		   {
			     cookies = new Object();
			     break;
		   }
		   beginning = 0;
		   while (beginning < document.cookie.length)
		   {
			     middle = document.cookie.indexOf('=', beginning);
			     end = document.cookie.indexOf(';', beginning);
			     if (end == -1)
			       end = document.cookie.length;
			     if ( (middle > end) || (middle == -1) )
			     {
			       name = document.cookie.substring(beginning, end);
			       value = "";
			      }
			      else
			      {
			        name = document.cookie.substring(beginning, middle);
			        value = document.cookie.substring(middle + 1, end);
			      }
			      cookies[name] = unescape(value);
			      beginning = end + 2;
		   }
		}	

		function autoLoad(deptid,deptwholename)
		{	
			var flag = document.all('form:flag').value;
			if(deptid!=null&&deptid!=""&&deptid!=undefined&&flag=='0')
			{				
				document.getElementById('form:deptid').value=deptid;			
				document.getElementById('form:depthiddenname').value=deptwholename;				
				var fireOnThis = document.all("mybutton");
				fireOnThis.click();				
			}    
		}	
		function onLoad(){
			extractCookies();
			autoLoad(cookies["com.cfcc.ebss.deptid"],cookies["com.cfcc.ebss.deptname"]);		
			document.all('form:spassword').focus();
		}
		function saveDept(deptid,deptname){
		  	document.cookie = "cookiesenabled=true";
		  	extractCookies();
			
		  	if (!cookies["cookiesenabled"]) {
			    return;
		  	}
			  
			document.cookie = "com.cfcc.ebss.deptid="+deptid+";expires=Sun, 01-Dec-2015 08:00:00 GMT;path=<%=request.getContextPath()%>";
		  	document.cookie = "com.cfcc.ebss.deptname="+deptname+";expires=Sun, 01-Dec-2015 08:00:00 GMT;path=<%=request.getContextPath()%>";
		}	
		function xxx(objid)
		{
			window.open('<%=request.getContextPath()%>/page/systemmanage/infopublish/infopublishdetail.jsf?objid='+objid,'Popup','width=500, height=460, toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=no, top=200,left=350');
		}	
		function validate()
		{	
			var deptdn = document.all('form:deptid').value;
			var depthiddenname = document.all('form:depthiddenname').value;
			if (isWhitespace(deptdn))
			{
				alert("请选择部门!");
				return false;
			}
			var userlist = document.all('form:userlist').value;
			if (isWhitespace(userlist))
			{
				alert("请选择用户!");
				return false;
			}		
			var spassword = document.all('form:spassword').value;
			if (isWhitespace(spassword))
			{
				alert("请录入密码!");
				return false;
			}		
			saveDept(deptdn,depthiddenname);
			var fireOnThis = document.all("loginhidden");
			fireOnThis.click();
		}
