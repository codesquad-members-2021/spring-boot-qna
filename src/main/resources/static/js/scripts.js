String.prototype.format = function () {
    var args = arguments;
    return this.replace(/{(\d+)}/g, function (match, number) {
        return typeof args[number] != 'undefined'
            ? args[number]
            : match
            ;
    });
};

$(document).ready(() => {//powered by pyro
    let currHerf = location.pathname
    $(".sub-nav-li").each((index, item) => {
      let hrefOfItem = $(item).children().first().attr('href');
      if (currHref === hrefOfItem) {
        $(item).addClass('active');
      }
    });

    $('#id-button-submit-answer').on('click', createAnswer);
    $('#id-button-delete-answer').click(deleteAnswer);
})

function deleteAnswer(event) {
    event.preventDefault();
    let deleteForm = $(this).parents('form');
    let url = deleteForm.attr('action');
    console.log("deleteForm : ",deleteForm);
    console.log("url : ",url);
    $.ajax({
        type : 'delete',
        url : url,
        dataType : 'json',
        success : function (data, status) {
            $(this).closest('article').remove();
            decreaseAnswerCount();
        },
        error : function () {
            console.log("답변 삭제 실패");
        }

    });

}


function createAnswer(event) {
    console.log("createAnswer");
    event.preventDefault();
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
    let template = answerTemplate.format(data.writer.userId, data.createdDateTime, data.contents, data.questionId, data.id);
    $(".qna-comment-slipp-articles").append(template);
    $("textarea[name=contents]").val("");
    let commentCountElement = $(".qna-comment-count").children(':last');
    let commentCount = commentCountElement.html();
    console.log("commentCount" , commentCount);
    commentCountElement.text(parseInt(commentCount)+1);
}

function onCreateAnswerFailed() {
    console.log("create Answer Failed");
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

