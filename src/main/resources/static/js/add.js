function fillSelectOptionsWithNumberRange(id, start, stop) {
  var select = $("#" + id).select();
  for (var i = start; i <= stop; i++) {
    select.append("<option>" + i + "</option>");
  }
}

$("#submitBtn").click(function () {
  $('html,body, button').css('cursor','wait');

  // Get checked categories
  var checkedCategories = $('#categoryModalBody > div > input:checked')
  var categories = '';
  for (var i = 0; i < checkedCategories.length; i++) {
    categories += checkedCategories[i].value + ',';
  };
  // slice to cut off trailing ","
  var catParam = '&category=' + categories.slice(0, -1);

  // Get checked mechanics
  var checkedMechanics = $('#mechanicModalBody > div > input:checked')
  var mechanics = '';
  for (var i = 0; i < checkedMechanics.length; i++) {
    mechanics += checkedMechanics[i].value + ',';
  };
  // slice to cut off trailing ","
  var mechParam = '&mechanic=' + mechanics.slice(0, -1);

  // Get form parameters
  var values = $("form").serialize();

  // Send POST request with params
  addGame(values, catParam, mechParam)
});

function addGame(values, catParam, mechParam) {
  url = "/game/success";
  params = values + catParam + mechParam;
  // console.log(params);
  var xhr = new XMLHttpRequest();
  xhr.open("POST", url, true);
  xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
  xhr.send(params);
  
  xhr.onreadystatechange = function() {
    if (xhr.readyState == 4 && xhr.status == 200) {
        window.location = "/game/success";
    }
  }
};

// Form validation
(function () {
  'use strict';
  window.addEventListener('load', function () {
    // Fetch all the forms we want to apply custom Bootstrap validation styles to
    var forms = document.getElementsByClassName('needs-validation');
    // Loop over them and prevent submission
    var validation = Array.prototype.filter.call(forms, function (form) {
      form.addEventListener('submit', function (event) {
        if (form.checkValidity() === false) {
          event.preventDefault();
          event.stopPropagation();
        }
        form.classList.add('was-validated');
      }, false);
    });
  }, false);
})();

$(document).ready(function () {
  // Populate dropdown menus
  fillSelectOptionsWithNumberRange("min-players", 1, 200)
  fillSelectOptionsWithNumberRange("max-players", 1, 200)
  fillSelectOptionsWithNumberRange("age", 1, 18)

  // Generate category checkboxes
  var addedCategoriesRow = '';
  for (var key in categoryDict) {
    var catName = categoryDict[key];
    addedCategoriesRow += '<div class="form-check form-check-inline"><input class="form-check-input" type="checkbox" id="' + catName + '" value="' + key + '"><label class="form-check-label modal-label" for="' + catName + '">' + catName + '</label></div>';
  }
  $('#categoryModalBody').html(addedCategoriesRow);

  // Generate mechanic checkboxes
  var addedMechanicsRow = '';
  for (var key in mechanicDict) {
    var mechName = mechanicDict[key];
    addedMechanicsRow += '<div class="form-check form-check-inline"><input class="form-check-input" type="checkbox" id="' + mechName + '" value="' + key + '"><label class="form-check-label modal-label" for="' + mechName + '">' + mechName + '</label></div>';
  }
  $('#mechanicModalBody').html(addedMechanicsRow);

});