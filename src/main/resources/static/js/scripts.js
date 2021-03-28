$(".answer-write button[type=submit]").click(addAnswer);

function addAnswer(e) {
  e.preventDefault();

  var queryString = $(".answer-write").serialize();
  var url = $(".answer-write").attr("action");
  $.ajax({
    type: 'post',
    url: url,
    data: queryString,
    dataType: 'json',
    error: function (xhr, status) {
      alert("error")
    },
    success: function (data, status) {
      console.log(data)
      var answerTemplate = $("#answerTemplate").html();
      var template = answerTemplate.format(data.writer.userId, data.formattedCreatedDate, data.contents, data.question.id, data.id);
      $(".qna-comment-slipp-articles").prepend(template);
      $("textarea[name=contents]").val("");
    }
  });
}

$(".qna-comment-slipp-articles").on("click", ".delete-answer-form button[type='submit']", deleteAnswer);

function deleteAnswer(e) {
  e.preventDefault();
  var deleteBtn = $(this);
  var url = deleteBtn.parent().attr("action");
  console.log("url :" + url);

  $.ajax({
    type : 'delete',
    url : url,
    dataType: 'json',
    error : function (xhr, status) {
      console.log("error")
    },
    success : function (data, status) {
      console.log(data);
      if(data.valid) {
        deleteBtn.closest("article").remove();
      } else {
        alert(data.errorMessage);
      }
    }
  })
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
