function fillSelectOptionsWithNumberRange(id, selected, start, stop) {
  // Select dropdown menu
  var select = $("#" + id).select();
  // Fill it in with numbers
  for (var i = start; i <= stop; i++) {
    select.append("<option>" + i + "</option>");
  }
  // Set default to game's value
  select.val(selected);
};

function setCheckedCategories() {
  // Get checked categories
  var categoryInput = $('#categoryModalBody > div > input')
  for (var i = 0; i < categoryInput.length; i++) {
    if (categories.includes(Number(categoryInput[i].value))) {
      $(categoryInput[i]).attr("checked", true);
    }
  };
}

function setCheckedMechanics() {
  // Get checked categories
  var mechanicInput = $('#mechanicModalBody > div > input')
  for (var i = 0; i < mechanicInput.length; i++) {
    if (mechanics.includes(Number(mechanicInput[i].value))) {
      $(mechanicInput[i]).attr("checked", true);
    }
  };
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
  // var catParam = '&category=' + categories.slice(0, -1);
  var catParam = '&category=' + categories;

  // Get checked mechanics
  var checkedMechanics = $('#mechanicModalBody > div > input:checked')
  var mechanics = '';
  for (var i = 0; i < checkedMechanics.length; i++) {
    mechanics += checkedMechanics[i].value + ',';
  };
  // slice to cut off trailing ","
  // var mechParam = '&mechanic=' + mechanics.slice(0, -1);
  var mechParam = '&mechanic=' + mechanics;

  // Get form parameters
  var values = $("form").serialize();

  // Send POST request with params
  updateGame(values, catParam, mechParam)

  // window.location.href = "/game/success";
});

function updateGame(values, catParam, mechParam) {
  url = "/game/success";
  params = values + catParam + mechParam;
  // console.log(params);
  var xhr = new XMLHttpRequest();
  xhr.open("PUT", url, true);
  xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
  xhr.send(params);
  
  xhr.onreadystatechange = function() {
    if (xhr.readyState == 4 && xhr.status == 200) {
        window.location = "/game/success";
    }
  }
};


$(document).ready(function () {
  // Disable form action
$('#editForm').submit(function(event) {
  event.preventDefault();  
});

  // Populate drop-down menus
  window.fillSelectOptionsWithNumberRange("min-players", minPlayers, 1, 200)
  window.fillSelectOptionsWithNumberRange("max-players", maxPlayers, 1, 200)
  window.fillSelectOptionsWithNumberRange("age", minAge, 1, 18)

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

  // Mark appropriate categories as checked
  setCheckedCategories();
  setCheckedMechanics();

  // Set DELETE request if delete button pressed
  $("#confirmDelete").click(function () {
    $('input[name="_method"]').attr('value', 'delete');
    $('form').submit();
  });

});





// Form valdation
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