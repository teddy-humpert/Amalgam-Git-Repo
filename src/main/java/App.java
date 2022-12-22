import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.Scanner;

// input word
// generate array of word
// generate list of all possible shuffles of array
// connect to word database
// check all possible combos against database
// print list

// need to fix factorial calculation to account for duplicate letters in a word.

public class App {

    private final Scanner keyboard = new Scanner(System.in);
    private static List<String> dictionary = new ArrayList<>();
    private static List<String> wordShuffles = new ArrayList<>();

    private List<String> dataset = new ArrayList<>();

    public static void main(String[] args) {
        App amalgam = new App();
        amalgam.loadData();
        amalgam.run();
    }

    private void loadData() {
        String filePath = "src/main/resources/wordList.txt";
        File bookFile = new File(filePath);
        boolean isFileFound = false;

        try (Scanner fileInput = new Scanner(bookFile)) {
            isFileFound = true;
            while (fileInput.hasNextLine()) {
                String dictionaryWord = fileInput.nextLine();
                dictionary.add(dictionaryWord);
//                System.out.println(dictionaryWord);
            }
        } catch (FileNotFoundException fnfe) {
            System.out.println("The file was not found: " + bookFile.getAbsolutePath());
        }
    }


    public void run() {
        while (true) {
            printTitleCard();
            printMainMenu();
            int mainMenuSelection = promptForMenuSelection("Please choose an option: ");
            if (mainMenuSelection == 1) {

                String testWord = promptForString("Enter your word: ");
                long startTime = System.nanoTime();
                long testWordLength = testWord.length();

                System.out.println("PARSING WORD...");


                List<Character> testWordArray = new ArrayList<>();

                for (int i = 0; i < testWord.length(); i++) {
                    testWordArray.add(testWord.charAt(i));
                }
                long factorial = Factorial(testWordLength);

                System.out.println("THERE ARE " + factorial + " POSSIBLE 'UNIQUE' ARRANGEMENTS OF LETTERS IN " + testWord.toUpperCase() + " ...");
                System.out.println("This part may take a while ...");

                int lineCount = 0;
                int foundCount = 0;

                while (lineCount < 50000) {
                    lineCount++;
                    Collections.shuffle(testWordArray);
                    String mashedWord = WordBuilder(testWordArray);


                    if (!wordShuffles.contains(mashedWord)) {
                        wordShuffles.add(mashedWord);

                    }
                }


                String outputString = "";
                List<String> checkedWords = new ArrayList<>();
                List<String> foundWords = new ArrayList<>();
                System.out.println("CREATING A BUNCH OF 3 - " + testWordLength + " CHARACTER LONG WORDS FROM THOSE...");

                for (String word : wordShuffles) {
                    for (int z = 3; z <= testWord.length(); z++) {
                        int take = z;
                        String checkedWord = word.substring(0, take);

                        checkedWords.add(checkedWord);
                    }
                }
                System.out.println("CHECKING ALL THOSE WORDS AGAINST A DICTIONARY...");
                System.out.println("This part may also take a while ...");
                for (String checked : checkedWords) {
                    if (dictionary.contains(checked) && !foundWords.contains(checked)) {
                        foundWords.add(checked);
                        foundCount++;
                    }
                }
                System.out.println("ASSEMBLING YOUR RESULTS");
                Collections.sort(foundWords);

                for (String word : foundWords) {
                    outputString += word.toUpperCase() + "\n";
                }

                System.out.println("************RESULTS************");
                System.out.println(outputString);
                long endTime   = System.nanoTime();
                long totalTime = endTime - startTime;
                System.out.println("Search took " + totalTime/1000000000 + " seconds.");
                System.out.println("Found : " + foundCount + " total word(s).");
                promptForReturn();
            }
//            if (mainMenuSelection == 2) {
////                playTwoPlayer();
//                break;
//            }
//            if (mainMenuSelection == 3) {
//                displayPastResults();
//            }
            if (mainMenuSelection == 0) {
                break;
            }
            break;
        }

    }

    private void printTitleCard() {
        System.out.println("*****************");
        System.out.println("WELCOME TO AMALGAM");
        System.out.println("*****************");
        System.out.println();
    }

    private void printMainMenu() {
        System.out.println("1: Input Your Word of Choice");
//        System.out.println("2: Play Two Player Game");
//        System.out.println("3: Display Past Results");
        System.out.println("0: Exit");
        System.out.println();
    }

    private void FindWord() {
        // for now just using the testWordArray
        // first step is to generate all possible shuffles.
        // shuffle to 1 if !contains into a list.

        String testWord = promptForString("Enter your word: ");
        long testWordLength = testWord.length();

        List<Character> testWordArray = new ArrayList<>();

        for (int i = 0; i < testWord.length(); i++) {
            testWordArray.add(testWord.charAt(i));
        }


        int lineCount = 0;

        while (wordShuffles.size() < Factorial(testWordLength)) {
            Collections.shuffle(testWordArray);
            String mashedWord = WordBuilder(testWordArray);
//                    testWordArray.toString();
//            String mashedWord = shuffledWord.join("", shuffledWord);

            if (!wordShuffles.contains(mashedWord)) {
                wordShuffles.add(mashedWord);
                // TESTING TO SEE THAT IT'S POPULATING CORRECTLY
                lineCount++;
//                System.out.println(lineCount + " " + mashedWord);
                //wordbuilder works yay
            }
        }


//        for (int z = 0; z < wordShuffles.size(); z++) {
//            lineCount++;
//            System.out.println(lineCount);
//            System.out.println(wordShuffles.get(z));
//        }
//
        // maybe i need to break this down further
        String outputString = "";
        List<String> checkedWords = new ArrayList<>();
        List<String> foundWords = new ArrayList<>();
        for (String word : wordShuffles) {
//            System.out.println(lineCount);
            // ok need to lose brackets, commas and spaces for each word....
            for (int z = 3; z <= testWord.length(); z++) {
                int take = z;
                //substring land -- moving take index in place
                // now needs to apply to each line of wordShuffles against full list of dictionary
//            for (String word : wordShuffles) {
                String checkedWord = word.substring(0, take);
//                System.out.println(checkedWord);
                //checkedword is happening
                // so maybe create a checkedword list and then loop that...
                checkedWords.add(checkedWord);
            }
        }
        System.out.println("SYSTEM PROCESSING");
        for (String checked : checkedWords) {
//            System.out.println(checked);
            if (dictionary.contains(checked)) {
                foundWords.add(checked);
//                LogFoundWord(word);
            }
        }
//        System.out.println(foundWords);
        Collections.sort(foundWords);

        for (String word : foundWords) {
            outputString += word.toUpperCase() + "\n";
        }
//        return outputString;

    }

    public static String WordBuilder(List<Character> testWordArray) {
        String testWordString = testWordArray.toString();
        String sb = "";

        for (int i = 0; i < testWordString.length(); i++) {
            if (Character.isAlphabetic(testWordString.charAt(i))) {
                sb += testWordString.charAt(i);
            }
        }
        return sb;
    }

    private void displayResults(int guessCount) {
        System.out.println("******************");
        if (guessCount == 1) {
            System.out.println("YOU GOT IT IN " + guessCount + " GUESS!!");
            System.out.println("THAT'S FREAKING INCREDIBLE!");
        }
        if (guessCount > 1 && guessCount < 6) {
            System.out.println("YOU GOT IT IN " + guessCount + " GUESSES!!");
            System.out.println("VERY NICE!");
        }
        if (guessCount >= 6) {
            System.out.println("YOU GOT IT IN " + guessCount + " GUESSES!!");
            System.out.println("THAT WAS CLOSE!");
        }
        System.out.println("******************");
    }

    private int promptForMenuSelection(String prompt) {
        System.out.print(prompt);
        int menuSelection;
        try {
            menuSelection = Integer.parseInt(keyboard.nextLine());
        } catch (NumberFormatException e) {
            menuSelection = -1;
        }
        return menuSelection;
    }

    private String promptForString(String prompt) {
        System.out.print(prompt);
        return keyboard.nextLine();
    }

    public Map<Character, Integer> WordMap(String word) {
        //idea here is to generate a map that can be used to test existence of letters in guess.
        List<Character> wordSplit = new ArrayList<>();
        for (int i = 0; i < word.length(); i++) {
            wordSplit.add(word.charAt(i));
        }
        Map<Character, Integer> output = new HashMap<>();

        for (Character letter : wordSplit) {
            if (!output.containsKey(letter)) {
                output.put(letter, 1);
            } else {
                output.put(letter, output.get(letter) + 1);
            }
        }
        return output;
    }

    private void promptForReturn() {
        System.out.println("Press RETURN to continue.");
        keyboard.nextLine();
        run();
    }

    public static long Factorial(long bigNumber) {
        long fact = 1;
        for (int i = 2; i <= bigNumber; i++) {
            fact = fact * i;
        }
        return fact;
    }

    private void logSuccessResults(int guessCount) {
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strDate = dateFormat.format(date);
        String auditPath = "wordList.txt";
        File logFile = new File(auditPath);
        try (PrintWriter log = new PrintWriter(new FileOutputStream(logFile, true))) {
            log.println("Successful in " + guessCount + " guesses on " + strDate);
        } catch (
                FileNotFoundException fnfe) {
            System.out.println("*** Unable to open log file: " + logFile.getAbsolutePath());
        }
    }

    private void logFailResults(int guessCount) {
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strDate = dateFormat.format(date);
        String auditPath = "wordList.txt";
        File logFile = new File(auditPath);
        try (PrintWriter log = new PrintWriter(new FileOutputStream(logFile, true))) {
            log.println("Unsuccessful on " + strDate);
        } catch (FileNotFoundException e) {
            System.out.println("*** Unable to open log file: " + logFile.getAbsolutePath());
        }
    }

    private void displayPastResults() {
        String filePath = "wordList.txt";
        File logFile = new File(filePath);
        try (Scanner fileInput = new Scanner(logFile)) {
            while (fileInput.hasNextLine()) {
                System.out.println(fileInput.nextLine());
            }
        } catch (FileNotFoundException fnfe) {
            System.out.println("The file was not found: " + logFile.getAbsolutePath());
        }
    }

}
