package com.rohitraj;

public class Main {

    public static void main(String[] args)throws Exception {
	// write your code here
        System.out.println("Guess the movie name by entering one letter/digit at a time. You will loose if you guessed 10 wrong letters. \n");
        Game game = new Game();
        game.setRandomMovie();
        game.setMaskedMovieName();
        game.display(false);
        game.gameStart();
    }
}
