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
    console.log(data);
    var answerTemplate = $("#answerTemplate").html();
    var template = answerTemplate.format(data.writer.userId, data.formatCreateDateTime, data.contents, data.id, data.id);
     $(".qna-comment-slipp-articles").prepend(template);

     $(".answer-write textarea").val("");

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
