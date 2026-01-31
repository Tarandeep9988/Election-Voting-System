package election.util;

import java.util.Scanner;

public class InputUtil {
  private static final Scanner sc = new Scanner(System.in);

  public static void askContinueOrExit() {
    System.out.println("Press Enter to continue or type 'exit' to quit:");
    String input = sc.nextLine();
    if (input.equalsIgnoreCase("exit")) {
      System.out.println("Exiting the election system. Goodbye!");
      System.exit(0);
    }
    System.out.println("Continuing...");
  }

  public static int MainMenuChoice() {
    int choice = -1;
    try {
      System.out.println("Main Menu:");
      System.out.println("1. Plurality Election");
      System.out.println("2. Runoff Election");
      System.out.println("3. Tideman Election");
      System.out.println("4. Exit");
      System.out.print("Enter your choice (1-4): ");

      choice = Integer.valueOf(sc.nextLine());
    } catch (Exception e) {
      // Do nothing, will return -1
    }
    return choice;
  }

  public static String[] getCandidateNames() {
    System.out.print("Enter the names of the candidates (space-separated): ");
    String input = sc.nextLine();
    return input.trim().split("\\s+");
  }

  public static int[] getVotes(String[] candidateNames) {
    int[] votes = new int[candidateNames.length];
    while (true) {
      System.out.println("Vote your candidate by their id");
      for (int i = 0; i < candidateNames.length; i++) {
        System.out.println(i + ": " + candidateNames[i]);
      }
      System.out.print("Enter candidate id (type done to finish): ");
      try {
        String input = sc.nextLine();
        if (input.equalsIgnoreCase("done")) {
          break;
        }
        int voteId = Integer.valueOf(input);
        if (voteId < 0 || voteId >= candidateNames.length) {
          System.out.println("Invalid candidate id. Please try again.");
          continue;
        }
        votes[voteId]++;
      } catch (Exception e) {
        System.out.println("Invalid input. Please enter a valid candidate id or 'done'.");
      }
    }
    return votes;
  }
}
