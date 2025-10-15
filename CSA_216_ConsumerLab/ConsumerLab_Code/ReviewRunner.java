
class ReviewRunner {
  public static void main(String[] args) {
    System.out.println("Sentiment of 'good': " + Review.sentimentVal("good"));
    System.out.println("Total sentiment of review.txt: " + Review.totalSentiment("review.txt"));
    System.out.println("Star rating: " + Review.starRating("review.txt"));
    System.out.println("\nFake positive review:\n" + Review.fakeReview("review.txt", true));
  }
}