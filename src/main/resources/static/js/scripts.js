String.prototype.format = function () {
    var args = arguments;
    return this.replace(/{(\d+)}/g, function (match, number) {
        return typeof args[number] != 'undefined'
            ? args[number]
            : match
            ;
    });
};

let regExToFindCurrentPage = /(?<=\bpageNumber=\b)\d{1,10}/;

$(document).ready(() => {
    focusCurrentNaviTab();
    focusCurrentPage();
    $('#id-button-submit-answer').on('click', createAnswer);
    $(".delete-answer-form button[type='submit']").on('click', deleteAnswer);
})

function focusCurrentNaviTab() {
    let currentHref = location.pathname;
    $(".sub-nav-li").each((index, item) => {
        let hrefOfItem = $(item).children().first().attr('href');
        if (currentHref === hrefOfItem) {
            $(item).addClass('active');
        }
    });
}

function focusCurrentPage() {
    let matchResult = location.href.match(regExToFindCurrentPage);
    if (!matchResult) {
        return;
    }
    let currentPageNumber = matchResult.toString();
    if (currentPageNumber) {
        let wrapper = $('#id-question-pagination-wrapper');
        for (let el of wrapper.children()) {
            let currentElement = $(el);
            let pageNumberOfElement = currentElement.children().first().attr('href')
                .match(regExToFindCurrentPage).toString();
            if (currentPageNumber === pageNumberOfElement) {
                currentElement.addClass('active');
            }
        }
    }
}

function deleteAnswer(event) {
    event.preventDefault();
    let deleteForm = $(this).parents('form');
    let url = deleteForm.attr('action');

    $.ajax({
        type: 'delete',
        url: url,
        success: (data, status) => {
            $(this).closest('article').remove();
            decreaseAnswerCount();
        },
        error: () => {
            alert('답변 삭제에 실패했습니다!');
        }
    });
}

function createAnswer() {
    let formElement = $(".submit-write");
    let queryString = formElement.serialize();
    let url = formElement.attr('action');

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
    let deleteFormElement = articlesListElement.children(':last').find('.delete-answer-form button[type="submit"]');
    deleteFormElement.on('click', deleteAnswer);
}

function onCreateAnswerFailed(data, status) {
    alert('답변 생성에 실패했습니다!');
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
