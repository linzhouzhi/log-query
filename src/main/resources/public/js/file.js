$("#file-query").click(function(){
    show_result();
});

$("#remote-exe").click(function(){
    var data = {};
    data.cmd = $("[name='input-command']").val();
    data.hostList = [{"ip":"","username":"","password":""}];
    console.log( data );
    post("/remote-exe", data, function(obj){
        console.log(obj.res);
        show_tabs(obj.res, 5);
    }, function(){
        console.log("error log")
    });
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
