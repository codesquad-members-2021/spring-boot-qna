$(".answer-write button[type=submit]").click(addAnswer);

function addAnswer(e) {
    e.preventDefault();
    console.log("clicked");

    var queryString = $(".answer-write").serialize();
    var url = $(".answer-write").attr("action");

    $.ajax({
        type: 'post',
        url: url,
        data : queryString,
        dataType : 'json',
        error : onError,
        success : onSuccess
    });
}

function onError() {
}

function onSuccess(data) {
    console.log(data);
    var answerTemplate = $("#answerTemplate").html();
    var template = answerTemplate.format(data.writer.name, data.formattedTimeCreated, data.contents, data.id);
    $(".qna-comment-slipp-articles").prepend(template);
    $("textarea[name=contents]").val("");
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
