String.prototype.format = function() {
  var args = arguments;
  return this.replace(/{(\d+)}/g, function(match, number) {
    return typeof args[number] != 'undefined'
        ? args[number]
        : match
        ;
  });
};

$(document).ready(() => {//powered by pyro
  /*
  let currHerf = location.pathname
  $(".sub-nav-li").each((index, item) => {

    let hrefOfItem = $(item).children().first().attr('href');
    if (currHref === hrefOfItem) {
      $(item).addClass('active');
    }
  });
  */
  //@Todo 뭐하는건지 공부해보기

  $('#id-button-submit-answer').on('click', createAnswer);
  //$(".delete-answer-form button[type='submit']").on('click', deleteAnswer);
})


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

function onCreateAnswerSuccess() {
  console.log("Success");
}


function onCreateAnswerFailed() {
  console.log("Failed T.T");
}

