String.prototype.format = function() {
  var args = arguments;
  return this.replace(/{(\d+)}/g, function(match, number) {
    return typeof args[number] != 'undefined'
        ? args[number]
        : match
        ;
  });
};

document.addEventListener('DOMContentLoaded', function() {
  var navItems = document.querySelectorAll('.nav-item a');
  for (var i = 0, len = navItems.length; i < len; i++) {
    if (navItems[i].getAttribute("href") === location.pathname) {
      navItems[i].parentElement.className += " active";
    }
  }
}, false);
