$ ->
  $.get "/scores", (data) ->
    $.each data, (index, score) ->
      $("#scores").append $("<li>").text score.username+ " "+ score.highScore 
      