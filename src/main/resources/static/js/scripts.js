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
    //$('#id-button-delete-answer').click(deleteAnswer);
    //$(".answer-write button[type='submit']").on("click", deleteAnswer);
    $(document).on("click", "a.link-delete-article", deleteAnswer);
})

function deleteAnswer(event) {
    event.preventDefault();
    let deleteForm = $(this);
    let url = deleteForm.attr('href');
    console.log("deleteForm : ",deleteForm);
    console.log("url : ",url);
    $.ajax({
        type : 'delete',
        url : url,
        dataType : 'json',
        success : function (data,status) {
            //$(this).closest('article').remove();
            //deleteForm.closest('article').remove();

            //decreaseAnswerCount();
        //데이터가 안넘어오니까 작동을 안한다.. >> 적어도 불리언을 리턴해줘서 해결했다!!
            //물리적으로 가장 가까운 아티클을 리무브하는데
            // 디비는 사라지는데, 동작안하는거는 에이작스 동작안해서

            if (data) {
                deleteForm.closest(".article").remove();
            }

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

