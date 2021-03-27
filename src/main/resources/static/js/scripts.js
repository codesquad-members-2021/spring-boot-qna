String.prototype.format = function () {
    var args = arguments;
    return this.replace(/{(\d+)}/g, function (match, number) {
        return typeof args[number] != 'undefined'
            ? args[number]
            : match
            ;
    });
};

$(document).ready(() => { // Powered By Pyro
    let currHref = location.pathname
    $(".sub-nav-li").each((index, item) => {
        let hrefOfItem = $(item).children().first().attr("href");
        if (currHref === hrefOfItem) {
            $(item).addClass("active");
        }
    });
    //$(".delete-answer-form button[type='submit']").click(deleteAnswer);
    $('#id-button-submit-answer').on('click', createAnswer);
    $(".delete-answer-form button[type='submit']").on('click', deleteAnswer);
})

function deleteAnswer(event) {
    event.preventDefault();
    let deleteForm = $(this).parents("form");
    let url = deleteForm.attr("action");
    $.ajax({
        type: 'delete',
        url: url,
        success: (data, status) => {
            $(this).closest("article").remove();
            decreaseAnswerCount();
        },
        error: () => {
            alert("답변 삭제에 실패했습니다!");
        }
    });
}

function createAnswer() {
    let formElement = $(".submit-write");
    let queryString = formElement.serialize(); //form data들을 자동으로 묶어준다.
    let url = formElement.attr("action");
    $.ajax({
        type: 'post',
        url: url,
        data: queryString,
        success: onCreateAnswerSuccess,
        error: onCreateAnswerFailed
    });
}

function onCreateAnswerSuccess(data, status) {
    let answerTemplate = $("#answerTemplate").html();
    let template = answerTemplate.format(
        data.writer.name,
        data.createdDateTime,
        data.contents,
        data.questionId,
        data.id
    );
    let articlesListElement = $('.qna-comment-slipp-articles').append(template);
    $('#answerContents').val('');
    increaseAnswerCount();
    let deleteFormElement = articlesListElement.children(':last').find(".delete-answer-form button[type='submit']");
    deleteFormElement.on('click', deleteAnswer);
}

function onCreateAnswerFailed(data, status) {
    alert("답변 생성에 실패했습니다!");
}

function increaseAnswerCount() {
    let countElement = $('#id-qna-comment-count');
    let value = parseInt(countElement.text()) + 1;
    countElement.html(value);
}

function decreaseAnswerCount() {
    let countElement = $('#id-qna-comment-count');
    let value = parseInt(countElement.text()) - 1;
    countElement.html(value);
}

