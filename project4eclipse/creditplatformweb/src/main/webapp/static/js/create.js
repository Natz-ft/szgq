$(function(){
	//要初始化的东西 
	 create_init("dialog-form","formID"); 
}  )
	
	
function create_successprocess(data) {
	if (data.msg =="1") {
	 		return true;
 	}else{
 		return false;
 	}
}