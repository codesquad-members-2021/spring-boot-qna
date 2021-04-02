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

    //@Todo 뭐하는건지 공부해보기

    $('#id-button-submit-answer').on('click', createAnswer);
    //$(".delete-answer-form button[type='submit']").on('click', deleteAnswer);
    //$(".delete-answer-form button[type='submit']").on('click', ".delete-answer-form button[type='submit']", deleteAnswer);
    $(".link-delete-article").click(deleteAnswer);
})

function deleteAnswer(event) {
    event.preventDefault();
    // 클릭한 버튼의 상위 form 태그의 url(action 속성)을 구하면
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
//questionId << 변환은 안될듯? >> question_id
function onCreateAnswerSuccess(data, status) {
    //console.log(status , " : " , data);
    let answerTemplate = $("#answerTemplate").html();
    let template = answerTemplate.format(data.writer.userId, data.createdDateTime, data.contents, data.questionId, data.id);
    //console.log("answerTemplate : " , answerTemplate);
    //console.log("template : " , template);
    $(".qna-comment-slipp-articles").append(template);
    $("textarea[name=contents]").val("");

    //qna-comment-count 이런속성을 가진 아이를 가져온다
    // 아이를 가져온거에서 >> 값을 추출해서 +1 한다음에 다시 ㅇ적용한디
    let commentCountElement = $(".qna-comment-count").children(':last');
    //console.log("commentCountElement : ",commentCountElement);
    let commentCount = commentCountElement.html();
    console.log("commentCount" , commentCount);
    commentCountElement.text(parseInt(commentCount)+1);
    //@Todo 제이쿼리는 >> 클래스 셀렉터는 .쩜! 을
    // 태그의 id속성은 # 샵 으로구분한다
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

