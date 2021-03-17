$(".answer-write button[type=submit]" ).click(addAnswer);

function addAnswer(e){
    e.preventDefault();
    var queryString = $(".answer-write").serialize();
    var url = $(".answer-write").attr("action");

    $.ajax({
        type : "post",
        url : url,
        data : queryString,
        dataType : "json",
        error : onError,
        success : onSuccess
    });
}

function onError(){
}

function onSuccess(data, status){
    console.log(data)
}

String.prototype.format = function() {
  var args = arguments;
  return this.replace(/{(\d+)}/g, function(match, number) {
    return typeof args[number] != 'undefined'
        ? args[number]
        : match
        ;
  });
};
