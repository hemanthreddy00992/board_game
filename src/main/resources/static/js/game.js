$(".commentBtn").click(function() {
    $(".game-single-content").append( $(".new-comment"));
    $(".commentBtn").attr("hidden", "true");
});

// $(".submitComment").click(function() {
//     var content = $("textarea").val();
//     var pathname = window.location.pathname;
//     window.location.href = pathname + "?content=" + content;
// })