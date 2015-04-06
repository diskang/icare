 function init(){
 	$.ajax({
            type: "get",
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            url: "/gero",
            timeout:3000,
            success: function (msg) {
                var str = "";
                for (i in msg) {
                    str += '<a href="'+msg[i].name+'.html"><img src="'+msg[i].logoUrl+'" alt="image loss">';
                    str += '</a>';
                }
                $("#geros-list").append(str);
                $(function() {
                    $('#dg-container').gallery();
                });
            }
        });
}
