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
        error: onAddAnswerError,
        success: onAddAnswerSuccess
    });
}

function onAddAnswerError(xhr, status) {
    alert(JSON.stringify(xhr));
}

function onAddAnswerSuccess(data, status) {
    var answerTemplate = $("#answerTemplate").html();
    var template = answerTemplate.format(
        data.writer.userId,
        data.formattedCreatedDate,
        data.contents,
        data.id,
        data.id
    );
    $(".qna-comment-slipp-articles").prepend(template);
    $(".answer-write textarea").val("");
}

$(document).on("click", ".answer-delete", removeAnswer);

function removeAnswer(e) {
    e.preventDefault();

    var url = $(this).attr("href");
    var deleteComponent = $(this);
    $.ajax({
        type: 'delete',
        url: url,
        dataType: 'json',
        error: onRemoveAnswerError,
        success: function (data, status) {
            deleteComponent.closest(".article").remove();
        }
    });
}

function onRemoveAnswerError(xhr, status) {
    alert("자신의 글만 삭제할 수 있습니다.");
}
