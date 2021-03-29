$(".submit-write button[type=submit]").click(addAnswer);

function addAnswer(e) {
    e.preventDefault();
    var queryString = $(".submit-write").serialize();

    var url = $(".submit-write").attr("action");
    $.ajax({
        type: 'post',
        url: url,
        data: queryString,
        dataType: 'json',
        error: onError,
        success: onSuccess,
    });
}

function onError() {
    console.log("failure");
}

function onSuccess(data, status) {
    console.log("success");
    var answerTemplate = $("#answerTemplate").html();
    var template = answerTemplate.format(data.writer.userId, data.createdAt, data.contents, data.question.id, data.id);
    $(".qna-comment-slipp-articles").prepend(template);
    $("textarea[name=contents]").val("");
}

$(document).on('click', "a.link-delete-article", deleteAnswer);

function deleteAnswer(e) {
    e.preventDefault();
    var url = $(this).attr("href");

    $.ajax({
        type: 'delete',
        url: url,
        dataType: 'json',
        error: function (xhr, status) {
            console.log('failure');
        },
        success: function (data, status) {
            console.log(data);
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
