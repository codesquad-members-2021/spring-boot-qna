$('.submit-write button[type=submit]').click(addAnswer);

function addAnswer(e) {
  e.preventDefault();
  let queryString = $('.submit-write').serialize();
  let url = $('.submit-write').attr('action');
  $.ajax({
    type: 'post',
    url : url,
    data : queryString,
    error : onError,
    success: addAnswerToBody,
  });
}

function onError() {
  console.log('error');
}

function addAnswerToBody(data, status) {
  let answerTemplate = $('#answerTemplate').html();
  let template = answerTemplate.format(
      data.id,
      data.questionId,
      data.writer.id,
      data.comment,
      data.time,
      data.writer.name
  );
  $('.qna-comment-slipp-articles').append(template);
  let count = $('.qna-comment-count').children('strong');
  count.text(Number(count.text()) + 1);
  $('textarea[name=comment]').val('');
}

String.prototype.format = function() {
  var args = arguments;
  return this.replace(/{(\d+)}/g, function(match, number) {
    return typeof args[number] != 'undefined'
        ? args[number]
        : match
        ;
  });
};
