var eldercare={
	drawElderCareList:function(){
		$(".inf").addClass('hide');
		$("#eldercareshow").removeClass('hide');
		$('#ss').slider({
    		mode: 'h',
    		tipFormatter: function(value){
				return value + '%';
    		}
		});
	},
}