package com.rohitraj;

import java.io.File;
import java.util.Scanner;

public class Game {
    private String randomMovie;
    private StringBuffer wrongGuess;
    private char[] maskedMovieName;
    private Scanner userInputScanner, movieFileScanner;
    private File file;
    private int guessUsed, noOfMovie;
    private char guessCharacter;
    private String[] movieList;

    public Game() {
        wrongGuess = new StringBuffer();
        randomMovie = "";
        userInputScanner = new Scanner(System.in);
        try {
            file = new File("movies.txt");
            movieFileScanner = new Scanner(file);
        } catch (Exception e) {
            System.out.println("Error Occurred. " + e);
            System.exit(0);
        }
    }

    public void listMovieNamesInArray() {

        /**
         * Method used to list the movies from the movieFileScanner to the array movieList.
         */
        StringBuffer temp = new StringBuffer("");
        while (movieFileScanner.hasNextLine()) {
            temp.append(movieFileScanner.nextLine()).append(",");
            noOfMovie++;
        }
        movieList = new String[noOfMovie];
        movieList = temp.toString().split(",");
    }

    public void setRandomMovie() {

        /**
         * Setter, used to set randomMovie field to any value(movie) from the movieList array.
         */
        listMovieNamesInArray();
        int random = (int) (Math.random() * noOfMovie);
        randomMovie = movieList[random].toLowerCase();
    }

    public void setMaskedMovieName() {

        /**
         * Setter used to initialise the array maskedMovieName to '_' or ' ' or '\'' .
         */
        maskedMovieName = new char[randomMovie.length()];
        for (int i = 0; i < randomMovie.length(); i++) {
            if (randomMovie.charAt(i) == ' ') {
                maskedMovieName[i] = ' ';
            } else {
                maskedMovieName[i] = '_';
            }
        }
    }

    public void display(boolean hasWon) {

        /**
         * Method used to display the result.
         *
         * @param hasWon used to check if the player won the match or not.
         */
        if (hasWon) {
            System.out.println("You win!");
            System.out.println("You have guessed '" + randomMovie + "' correctly.");
            System.exit(0);
        } else {
            if (guessUsed < 10) {
                System.out.println("You are guessing: " + new String(maskedMovieName));
                System.out.println("You have guessed (" + guessUsed + ") wrong letters: " + wrongGuess);
                System.out.println();
                gameStart();
            } else {
                System.out.println("You are guessing: " + new String(maskedMovieName));
                System.out.println("You have guessed (" + guessUsed + ") wrong letters: " + wrongGuess);
                System.out.println("No more attempt left. Try again.");
                System.out.println("The correct movie was: " + randomMovie);
                System.exit(0);
            }
        }
    }

    public void gameStart() {

        /**
         * Method where the game starts and contains the win logic.
         */
        System.out.print("Guess a letter: ");
        String guessString = userInputScanner.nextLine().toLowerCase();
        //Condition, if the user input a string instead of a character.
        if (guessString.length() > 1) {
            System.out.println("You should enter a letter not word/sentence. Enter again. \n");
            gameStart();
        } else {
            guessCharacter = guessString.charAt(0);

            //Condition, if the user input the space character which is not needed.
            if (guessCharacter == ' ') {
                System.out.println("No need to enter \"Space\". It is already included. Enter again. \n");
                gameStart();
            } else if (!(Character.isLetterOrDigit(guessCharacter) || guessCharacter == '\'')) { // Condition, for the inputted character is a valid letter or digit or '\''.
                System.out.println("You should input a valid letter/digit i.e. from 'a' to 'z' or from '0' to '9' or '\''. Enter again. \n");
                gameStart();
            } else if (wrongGuess.indexOf(String.valueOf(guessCharacter)) > -1) {  // Condition, if the user enter the same wrong guess character.
                System.out.println("You had already guessed the letter '" + guessCharacter + "' Enter again. \n");
                gameStart();
            } else if (new String(maskedMovieName).indexOf(guessCharacter) > -1) {  //Condition, if the user enter the same right guess character.
                System.out.println("You had already guessed the letter '" + guessCharacter + "' Enter again. \n");
                gameStart();
            }
        }
        int indexNumber = -1;
        boolean guessedCorrect = false;

        //Checking the value if guessCharacter exist in the randomMovie array.
        while (randomMovie.indexOf(guessCharacter, (indexNumber + 1)) != -1) {
            indexNumber = randomMovie.indexOf(guessCharacter, (indexNumber + 1));
            maskedMovieName[indexNumber] = guessCharacter;
            guessedCorrect = true;
        }

        if (new String(maskedMovieName).equals(randomMovie)) {
            display(true);
        } else if (guessedCorrect) {
            display(false);
        } else {
            guessUsed++;
            wrongGuess.append(guessCharacter).append(" ");
            display(false);
        }
    }
}
