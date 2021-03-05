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
        let hrefOfItem = $(item).children().first().attr("href")
        if (currHref === hrefOfItem) {
            $(item).addClass("active")
        }
    })
})