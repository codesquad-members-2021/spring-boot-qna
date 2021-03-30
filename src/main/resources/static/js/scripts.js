$(".answer-write button[type=submit]").click(addAnswer);

function addAnswer(e) {
    e.preventDefault();
    console.log("click!!");

    var queryString = $(".answer-write").serialize();
    console.log("query: " + queryString);

    var url = $(".answer-write").attr("action");
    console.log("url: " + url);

    $.ajax({
        type: 'post',
        url: url,
        data: queryString,
        dataType: 'json',
        error: onError,
        success: function (data, status) {
            console.log("Success");
            console.log("data: " + data);
            console.log("status: " + status);
            var answerTemplate = $("#answerTemplate").html();
            var template = answerTemplate.format(data.writer.name, data.formattedCreatedDateTime, data.contents, data.question.id, data.id);
            $(".qna-comment-slipp-articles").append(template);
            $(".answer-write textarea").val("");
        }
    });
}

$(document).on('click', '.delete-answer-form button[type=submit]', deleteAnswer);

function deleteAnswer(e) {
    e.preventDefault();

    var deleteBtn = $(this);
    var deleteAnswerForm = $(this).parent();
    var url = deleteAnswerForm.attr('action');
    console.log("url: " + url);

    $.ajax({
        type: 'delete',
        url: url,
        dataType: 'json',
        error: onError,
        success: function (data, status) {
            console.log("Success");
            deleteBtn.closest('article').remove();
        }
    })
}

function onError(xhr, status) {
    console.log("Error");
    console.log("xhr: " + xhr);
    console.log("status: " + status);
    alert(xhr.responseText);
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
