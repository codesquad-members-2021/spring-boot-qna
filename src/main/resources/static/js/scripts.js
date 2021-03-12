$(".answer-write input[type=submit]").click(addAnswer);

function addAnswer(e) {
    console.log("click me");
    e.preventDefault();

    var queryString = $(".answer-write").serialize();
    console.log("query : " + queryString);

    var url = $(".answer-write").attr("action");

    console.log("url : " + url);
    $.ajax({
        type: 'post',
        url: url,
        data: queryString,
        dataType: 'json',
        error: onError,
        success: onSuccess
    });
}

function onError() {
    console.log("error");
}

function onSuccess(data, status) {
    console.log(data);
    var answerTemplate = $("#answerTemplate").html();
    var template = answerTemplate.format(data.author.name, data.formattedDate, data.contents, data.question.id, data.id);
    $(".qna-comment-slipp-articles").prepend(template);
    $("textarea[name=contents]").val("");
}

$(document).on('click', '.link-delete-article', deleteAnswer);

function deleteAnswer(e) {
    e.preventDefault();
    var deleteButton = $(this);
    var url = deleteButton.attr("href");
    console.log("url : " + url)

    $.ajax({
        type: 'delete',
        url: url,
        dataType: 'json',
        error: function (xhr, status) {
            console.log("error")
        },
        success: function (data, status) {
            console.log("success")
            if (data) {
                deleteButton.closest("article").remove();
            } else {
                alert("작성자 본인만 삭제 가능합니다.");
            }
        }
    });
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
