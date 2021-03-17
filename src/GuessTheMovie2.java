import java.io.*;
import java.util.*;;
import static java.lang.Character.toLowerCase;

public class GuessTheMovie2 {
    private static ArrayList <String> movies = new ArrayList<String>();

    private static String randomFilm(String fileName) throws FileNotFoundException{
        String randomFilm = "";
        File file = new File(fileName);
        Scanner scan = new Scanner(file);
        int i = 0;
        while(scan.hasNextLine()) {
            movies.add(scan.nextLine());
            i++;
        }
        int randomLine = (int) (Math.random()*(i))+1;
        System.out.println("line: "+randomLine);
        return movies.get(randomLine-1).toLowerCase();
    }

    private static String hiddenMovie(String randomFilm) {
        String underscores = "";
        for(int i = 0; i < randomFilm.length(); i++) {
            if(Character.isLetter(randomFilm.charAt(i))) {
                underscores += "_";
            } else {
                underscores += randomFilm.charAt(i);
            }
        }
        return underscores;
    }

    private static boolean wrongOrNot(String film, char currGuess) {
        boolean wrong = false;

        if(film.indexOf(currGuess) == -1) {
            wrong = true;
        }
        return wrong;
    }

    private static String revealedPart(String movie, String hiddenPart, char c) {
        String newStr = "";
        for(int i = 0; i < movie.length(); i++){
            if(movie.charAt(i) == c) {
                newStr += movie.charAt(i);
            } else {
                newStr += hiddenPart.charAt(i);
            }
        }
        return newStr;
    }

    private static boolean playAgain() {
        boolean restart = false;
        System.out.println("Try again? (y/n)");
        Scanner input = new Scanner(System.in);
        char ans = input.next().charAt(0);
        if(ans == 'y' || ans == 'Y') {
            restart = true;
        }
        return restart;
    }

    public static void play() throws FileNotFoundException{
        String movie = randomFilm("movies.txt");
        String hidden = hiddenMovie(movie);
        int wrongGuesses = 0;
        String wrongLetters = "";
        while(wrongGuesses < 10) {
            System.out.println("You are guessing: " + hidden);
            System.out.print("You have guessed (" + wrongGuesses + ") wrong letters:");
            System.out.println(wrongLetters);
            System.out.print("Guess a letter:");
            Scanner input = new Scanner(System.in);
            char currGuess = toLowerCase(input.next().charAt(0));
            if(Character.isLetter(currGuess)) {
                if (wrongOrNot(movie, currGuess)) {
                    if (!wrongLetters.contains("" + currGuess)) {
                        wrongLetters += " " + currGuess;
                        wrongGuesses++;
                    }
                } else {
                    hidden = revealedPart(movie, hidden, currGuess);
                }
            } else {
                System.out.println("****ONLY LETTERS ARE ALLOWED****");
            }

            if (!hidden.contains("_")) {
                System.out.println("**** YOU WIN! ****");
                System.out.println("You have guessed '" + hidden + "' correctly.");
                System.exit(0);
            }
        }
        System.out.println("**** YOU LOOSE **** ");
        if(playAgain()) {
            play();
        }
    }

    public static void main(String[] args) throws FileNotFoundException{
        play();
    }
}
