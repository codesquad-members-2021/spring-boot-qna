$(".submit-write button[type=submit]").click(addAnswer);

function addAnswer(e) {
    e.preventDefault();
    var queryString = $(".submit-write").serialize();

    var url = $(".submit-write").attr("action");
    $.ajax({
        type : 'post',
        url : url,
        data : queryString,
        dataType : 'json',
        error: onError,
        success : onSuccess,
    });
}

function onError(){
    console.log("failure");
}
function onSuccess(){
    console.log("success");
}

String.prototype.format = function () {
    var args = arguments;
    return this.replace(/{(\d+)}/g, function (match, number) {
        return typeof args[number] != 'undefined'
            ? args[number]
            : match
            ;
    });
};
