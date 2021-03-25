$(".answer-write input[type=submit]").click(addAnswer);

function addAnswer(e) {
    console.log("Hello World!");
    e.preventDefault();

    var queryString = $(".answer-write").serialize();
    console.log(queryString);

    var url = $(".answer-write").attr("action");
    console.log(url);

    $.ajax({
        type: 'post',
        url: url,
        data: queryString,
        dataType: 'json',
        error: error,
        success: ok
    });
}

function error() {
}

function ok(data, status) {
    console.log(data);

    var answerTemplate = $("#answerTemplate").html();
    var template = answerTemplate.format(data.writer.userId, data.formattedDateTime, data.contents, data.question.id, data.id);
    $(".qna-comment-slipp-articles").prepend(template);

    $("textarea[name=contents]").val("");
}

$(".qna-comment-slipp-articles").on('click', 'a.link-delete-article', deleteAnswer);

function deleteAnswer(e) {
    e.preventDefault();

    var deleteButton = $(this);
    var url = deleteButton.attr("href");
    console.log(url);

    $.ajax({
        type: 'delete',
        url: url,
        dataType: 'json',
        error: function (xhr, status) {
            console.log("error");
        },
        success: function (data, status) {
            console.log(data);
            deleteButton.closest("article").remove();
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
}
