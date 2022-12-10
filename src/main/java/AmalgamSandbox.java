import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AmalgamSandbox {

    private static List<String> dictionary = new ArrayList<>();
    private static List<String> wordShuffles = new ArrayList<>();

    public void loadData() {
        // the goal here would be to read through entire text and generate invididual Entries per line.
        // and then be able to sort through those.
        // read - delimit - set
//        String filePath = "C:\\Users\\Student\\teddyCode\\Amalgam-Git-Repo\\src\\main\\resources\\wordList.txt";
//        File bookFile = new File(filePath);
//        boolean isFileFound = false;
//        int lineCount = 0;
//
//        try (Scanner fileInput = new Scanner(bookFile)) {
//            isFileFound = true;
//            while (fileInput.hasNextLine()) {
//                lineCount++;
//                String dictionaryWord = fileInput.nextLine();
//                dictionary.add(dictionaryWord);
//
//            }
//
//        } catch (FileNotFoundException fnfe) {
//            System.out.println("The file was not found: " + bookFile.getAbsolutePath());
//        }
    }

    public static void main(String[] args) {

        //LOAD DATA
        String filePath = "C:\\Users\\Student\\teddyCode\\Amalgam\\src\\main\\resources\\wordList.txt";
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

        // CREATE AMALGAM
        String testWord = "chapter";
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
        List <String> checkedWords = new ArrayList<>();
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

        for (String word : foundWords) {
            outputString += word + "\n";
        }
        System.out.println(outputString);


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


    public static String FindWords(List<String> wordShuffles, List<String> dictionary, String testWord) {
//         file reader
//         test all Strings in list
//         populate new list of all words in wordList.txt?
//         substrings 0,3 to 0, testWord.length()
//         need a moving TAKE
        String outputString = "";
        List<String> foundWords = new ArrayList<>();
        for (String word : wordShuffles) {
            for (int i = 3; i < testWord.length(); i++) {
                int take = i;
                //substring land -- moving take index in place
                // now needs to apply to each line of wordShuffles against full list of dictionary
//            for (String word : wordShuffles) {
                String checkedWord = word.substring(0, take);
                System.out.println(checkedWord);
                if (dictionary.contains(checkedWord)) {
                    foundWords.add(testWord);
                }
            }
        }

        for (int h = 0; h < foundWords.size(); h++) {
            outputString += foundWords.get(h) + "\n";
        }

        return outputString;
    }

    // worked out how to get the factorial...
    public static long Factorial(long bigNumber) {
        long fact = 1;
        for (int i = 2; i <= bigNumber; i++) {
            fact = fact * i;
        }
        return fact;
    }

    public static void LogFoundWord(String foundWord) {
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strDate = dateFormat.format(date);
        String auditPath = "wordList.txt";
        File logFile = new File(auditPath);
        try (PrintWriter log = new PrintWriter(new FileOutputStream(logFile, true))) {
            log.println(foundWord);
        } catch (
                FileNotFoundException fnfe) {
            System.out.println("*** Unable to open log file: " + logFile.getAbsolutePath());
        }
    }

}
