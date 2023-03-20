let dice = document.querySelector(".dice");
let p1DieImage = document.querySelector(".img1");
let p2DieImage = document.querySelector(".img2");
let header = document.querySelector(".instruction");

let p1Die = 0;
let p2Die = 0;
let diceImage = "images/dice";

function rollDie(playerDie, player){
    let dieValue = Math.floor((Math.random() * 6) + 1);
    if(player == 1){
        p1Die = dieValue;
    }
    else{
        p2Die = dieValue;
    }
    playerDie.setAttribute("src", diceImage + dieValue + ".png");
}

function compareDice(p1Die, p2Die){
    if(p1Die > p2Die){
        header.innerHTML = "Player 1 Wins!";
    }
    else if(p1Die < p2Die){
        header.innerHTML = "Player 2 Wins!";
    }
    else{
        header.innerHTML = "It's a tie!";
    }
}

p1DieImage.addEventListener('load', rollDie(p1DieImage, 1),false);
p2DieImage.addEventListener('load', rollDie(p2DieImage, 2),false);
header.addEventListener('load', compareDice(p1Die, p2Die), false);


