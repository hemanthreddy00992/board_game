$("#myActBtn").click(function() {
    window.location.href = "/account";
});

$("#addGameBtn").click(function() {
    window.location.href = "/add";
});

function selectGame(id) {
    document.location.href = "/game/" + id;
 };