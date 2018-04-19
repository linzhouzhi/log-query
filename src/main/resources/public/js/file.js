$(document).ready(function () {
    $("#checkbox-ip-list").prepend();
    init_ipList();
});
function init_ipList() {
    $.get("/host-list", function (obj) {
        var res = obj.res;
        var str = "";
        for(var i =0; i < res.length; i++){
            str += '<div class="checkbox ip-checkbox"> <label> <input type="checkbox"> ' + res[i]["ip"] + ' </input> </label> </div>';
        }
        $("#checkbox-ip-list").prepend( str );
    });

}
$("#go-path").click(function () {
    var data = {};
    data.path = $("[name='input-path']").val();
    data.host = {"ip":"192.168.31.147","username":"lzz","password":"363216"};
    post("/file-list", data, function(obj){
        console.log(obj.res);
        var boxstr = '<div class="panel panel-default" style="padding:15px">';
        for(var key in obj.res){
            boxstr += '<div class="checkbox"><label><input type="checkbox" name="file-checkbox" value="'+ obj.res[key] +'"> <span class="go-file">' + obj.res[key] + '</span> </input></label></div>';
        }
        boxstr += "</div>";
        $("#checkbox-file-list").html( boxstr );
    }, function(){
        console.log("error log")
    });
});


$("#file-query").click(function(){
    var chk_value =[];
    $('input[name="file-checkbox"]:checked').each(function(){
        chk_value.push($(this).val());
    });
    var data = {};
    data.files = chk_value;
    data.query = $("[name='input-command']").val();
    data.path = $("[name='input-path']").val();
    data.hostList = [{"ip":"192.168.31.147","username":"lzz","password":"363216"}];
    post("/file-query", data, function(obj){
        console.log(obj.res);
        show_tabs(obj.res, 5);
    }, function(){
        console.log("error log")
    });
});

$("#add-host-button").click(function () {
    $("#add-host-modal").modal("show");
});
$("#save-host-modal").click(function () {
    var data = {};
    data.service = $('[name="modal-service"]').val();
    data.ip = $('[name="modal-ip"]').val();
    data.username = $('[name="modal-username"]').val();
    data.password = $('[name="modal-password"]').val();
    post("/add-host", data, function(obj){
        console.log(obj.res);
        $("#add-host-modal").modal("hide");
    }, function(){
        console.log("error log");
        $("#add-host-modal").modal("hide");
    });
});

$("#remote-exe").click(function(){
    var data = {};
    data.cmd = $("[name='input-command']").val();
    data.hostList = [{"ip":"192.168.31.147","username":"lzz","password":"363216"}];
    post("/remote-exe", data, function(obj){
        console.log(obj.res);
        show_tabs(obj.res, 5);
    }, function(){
        console.log("error log")
    });
});

$(document).on("dblclick",".go-file",function () {
    var path = $(this).text();
    var old_path = $("[name='input-path']").val();
    $("[name='input-path']").val( old_path + path + "/");
    $("#go-path").click();
});

function show_result(){
    var res = {};
    res["name1"] = "hello name1";
    res["name2"] = "hello name2";
    res["name3"] = "hello name3";
    res["name4"] = "hello name4";
    res["name5"] = "hello name5";
    res["name6"] = "hello name6";
    res["name7"] = "hello name7";
    show_tabs(res, 5);
}

function show_tabs(res, tab_count){
    var tabs_ul = '<ul class="nav nav-tabs" role="tablist">';
    var tab_content = '<div class="tab-content">';
    var i = 0;
    for(var key in res){
        var active = "";
        var isexpanded = "false";
        if( i == 0 ){
            isexpanded = "true";
            active = 'active';
        }
         i++;
        if( i < tab_count ){
            tabs_ul += '<li role="presentation" class="' + active + '"><a href="#' + key + '" id="' + key + '-tab" role="tab" data-toggle="tab" aria-controls="' + key + '" aria-expanded="' + isexpanded + '">' + res[key]["ip"] + '</a></li>';
        }
        console.log( key );
        tab_content += '<div role="tabpanel" class="tab-pane fade ' + active + ' in" id="' + key + '" aria-labelledby="' + key + '-tab"><pre style="border:0px">' + res[key]["con"]+ '</pre></div>';
    }
    if( i > tab_count - 1 ){
        tabs_ul += '<li role="presentation" class="dropdown"><a href="#" id="myTabDrop1" class="dropdown-toggle" data-toggle="dropdown" aria-controls="myTabDrop1-contents" aria-expanded="false">more... <span class="caret"></span></a>';
        tabs_ul += '<ul class="dropdown-menu" aria-labelledby="myTabDrop1" id="myTabDrop1-contents">';
        var i = 0;
        for(var key in res){
            if( i >= tab_count - 1 ){
               tabs_ul += '<li><a href="#'+ key + '" role="tab" id="'+ key + '-tab" data-toggle="tab" aria-controls="'+ key + '">' + res[key]["ip"] + '</a></li>';
            }
            i++;
        }
        tabs_ul += "</ul></li>"
    }
    tabs_ul += "</ul>";
    tab_content += "</div>";
    $("#ip-tabs").html( tabs_ul + tab_content );
}
