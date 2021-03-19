String.prototype.format = function() {
  var args = arguments;
  return this.replace(/{(\d+)}/g, function(match, number) {
    return typeof args[number] != 'undefined'
        ? args[number]
        : match
        ;
  });
};

$(".answer-write button[type=submit]").click(addAnswer);

function addAnswer(e) {
  e.preventDefault();
  console.log("click !");

  var queryString = $(".answer-write").serialize();
  var url = $(".answer-write").attr("action");
  console.log("queryString : " + queryString);
  console.log("url : " + url);

  $.ajax({
    type: 'post',
    url: url,
    data: queryString,
    dataType: 'json',
    error: onError,
    success: onSuccess,
  });
}

function onError(data, status) {
  console.log("Error");
  console.log("data : " + JSON.stringify(data));
}

function onSuccess(data, status) {
  console.log("Success");
  console.log("data : " + JSON.stringify(data));
  var answersTemplate = $("#answerTemplate").html();
  var template = answersTemplate.format(data.writer.userId, data.createdDateTime, data.contents, data.questionId, data.id);
  $(".qna-comment-slipp-articles").prepend(template);
  $("textarea[name=contents]").val("");
}
