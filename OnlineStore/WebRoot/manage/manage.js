function showUploadUnit(){
	$("#changeImage").bind("click",function(){
		$("#uploadUnit").removeAttr("hidden");
		this.remove();
	});
	
}

$(function(){
	showUploadUnit();
});