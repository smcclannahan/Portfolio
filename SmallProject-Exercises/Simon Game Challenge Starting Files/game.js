let buttonColors = ["red", "blue", "green", "yellow"];
let gamePattern = [];
let userClickedPattern = [];
let levelCount = 0;

function nextSequence(){
    let randomNumber = Math.floor(4 * Math.random());
    let randomColor = buttonColors[randomNumber];
    gamePattern.push(randomColor);

    levelCount++;
    $("#level-title").text("Level " + levelCount);
    $("#" + randomColor).fadeOut(100).fadeIn(100);
    playSound(randomColor);
}

$(".btn").click(function(){
    let userChosenColor = $(this).attr("id");
    userClickedPattern.push(userChosenColor);
    playSound(userChosenColor);
    animatePress(userChosenColor);
    checkAnswer(userClickedPattern.length - 1);
});

$(document).keydown(function(event){
    if(levelCount == 0){
        nextSequence();
    }
})

function playSound(name){
    let colorSound = new Audio("sounds/" + name + ".mp3");
    colorSound.play();
}

function animatePress(currentColor){
    $("#" + currentColor).addClass("pressed");
    setTimeout(function() {
        $("#" + currentColor).removeClass("pressed");
    }, 100);
}

function checkAnswer(currentLevel){
    if(userClickedPattern[currentLevel] == gamePattern[currentLevel]){
        console.log("Correct");
        if(userClickedPattern.length == gamePattern.length){
            setTimeout(function(){nextSequence()}, 1000);
        }

    }
    else{
        console.log("Wrong");
        playSound("wrong");
        $("body").addClass("game-over");
        setTimeout(function(){
            $("body").removeClass("game-over")
        }, 200);
        $("#level-title").text("Game Over, Press Any Key to Restart");
        reset();
    }
}

function reset(){
    levelCount = 0;
    gamePattern.length = 0;
    userClickedPattern.length = 0;

}