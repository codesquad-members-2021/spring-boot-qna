$(".answer-write input[type=submit]").click(addAnswer);

function addAnswer(e) {
    e.preventDefault();

    var queryString = $(".answer-write").serialize();
    console.log("queryString : " + queryString);

    var url = $(".answer-write").attr("action");
    var fullURL = "http://localhost:8080/" + url;
    console.log("fullURL : " + fullURL);

    $.ajax({
        type: 'post',
        url: fullURL,
        data: queryString,
        dataType: 'json',
        error: onError,
        success: onSuccess
    });
}

function onError(){
    console.log("error : ");
}

function onSuccess(data, status){
    console.log(status, data);
    var answerTemplate = $("#answerTemplate").html();
    var template = answerTemplate.format(data.writer.userId, data.formattedCreatedDate,
                                        data.contents, data.question.questionId, data.answerId);

    $(".qna-comment-slipp-articles").prepend(template);
    $("textarea[name=contents]").val("");
}

$(".qna-comment-slipp-articles").on("click", "a.link-delete-answer", deleteAnswer);

function deleteAnswer(e){
    e.preventDefault();

    console.log("click event");

    var deleteBtn = $(this);
    var url = $(this).attr("href");
    console.log("url: " + url);

    $.ajax({
        type: 'delete',
        url: url,
        dataType: 'json',
        error: function(xhr, status){
            console.log("error");
        },
        success: function(data, status){
             console.log("data : " + data);

             if(!data.valid) {
                console.log("not valid");
             }

               deleteBtn.closest("article").remove();
         },
    })
}

$(".qna-comment-slipp-articles").on("click", "a.link-modify-article", updateAnswer);

function updateAnswer(e){
    e.preventDefault();

    console.log("update... later");
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
