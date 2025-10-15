import java.util.Scanner;
import java.io.File;
import java.util.HashMap;
import java.util.ArrayList;


public class Review {

  private static HashMap<String, Double> sentiment = new HashMap<String, Double>();
  private static ArrayList<String> posAdjectives = new ArrayList<String>();
  private static ArrayList<String> negAdjectives = new ArrayList<String>();

  static {
   
    try {
      Scanner input = new Scanner(new File("cleanSentiment.csv"));
      while (input.hasNextLine()) {
        String[] temp = input.nextLine().split(",");
        sentiment.put(temp[0], Double.parseDouble(temp[1]));
      }
      input.close();
    } catch (Exception e) {
      System.out.println("Error reading or parsing cleanSentiment.csv");
    }

    
    try {
      Scanner input = new Scanner(new File("positiveAdjectives.txt"));
      while (input.hasNextLine()) {
        posAdjectives.add(input.nextLine().trim());
      }
      input.close();
    } catch (Exception e) {
      System.out.println("Error reading or parsing positiveAdjectives.txt\n" + e);
    }

   
    try {
      Scanner input = new Scanner(new File("negativeAdjectives.txt"));
      while (input.hasNextLine()) {
        negAdjectives.add(input.nextLine().trim());
      }
      input.close();
    } catch (Exception e) {
      System.out.println("Error reading or parsing negativeAdjectives.txt");
    }
  }

  
  public static String textToString(String fileName) {
    String temp = "";
    try {
      Scanner input = new Scanner(new File(fileName));

      
      while (input.hasNext()) {
        temp = temp + input.next() + " ";
      }
      input.close();

    } catch (Exception e) {
      System.out.println("Unable to locate " + fileName);
    }
    
    return temp.trim();
  }

  
  public static double sentimentVal(String word) {
    try {
      return sentiment.get(word.toLowerCase());
    } catch (Exception e) {
      return 0;
    }
  }

  
  public static String getPunctuation(String word) {
    String punc = "";
    for (int i = word.length() - 1; i >= 0; i--) {
      if (!Character.isLetterOrDigit(word.charAt(i))) {
        punc = punc + word.charAt(i);
      } else {
        return punc;
      }
    }
    return punc;
  }

  
  public static String removePunctuation(String word) {
    while (word.length() > 0 && !Character.isAlphabetic(word.charAt(0))) {
      word = word.substring(1);
    }
    while (word.length() > 0 && !Character.isAlphabetic(word.charAt(word.length() - 1))) {
      word = word.substring(0, word.length() - 1);
    }

    return word;
  }

  
  public static String randomPositiveAdj() {
    int index = (int) (Math.random() * posAdjectives.size());
    return posAdjectives.get(index);
  }

  
  public static String randomNegativeAdj() {
    int index = (int) (Math.random() * negAdjectives.size());
    return negAdjectives.get(index);
  }

  
  public static String randomAdjective() {
    boolean positive = Math.random() < .5;
    if (positive) {
      return randomPositiveAdj();
    } else {
      return randomNegativeAdj();
    }
  }

  
  public static double totalSentiment(String fileName) {
    String reviewText = textToString(fileName);
    double total = 0.0;

    
    String[] words = reviewText.split(" ");
    for (String word : words) {
      String cleanedWord = removePunctuation(word);
      total += sentimentVal(cleanedWord);
    }
    return total;
  }

  
  public static int starRating(String fileName) {
    double sentiment = totalSentiment(fileName);

    if (sentiment < 0) return 1;
    else if (sentiment < 5) return 2;
    else if (sentiment < 10) return 3;
    else if (sentiment < 15) return 4;
    else return 5;
  }

  
  public static String fakeReview(String fileName, boolean positive) {
    String reviewText = textToString(fileName);
    String[] words = reviewText.split(" ");
    String result = "";

    for (String word : words) {
      if (word.startsWith("*")) {
        String punctuation = getPunctuation(word);
        String newAdj = positive ? randomPositiveAdj() : randomNegativeAdj();
        result += newAdj + punctuation + " ";
      } else {
        result += word + " ";
      }
    }
    return result.trim();
  }
}


