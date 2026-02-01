package election.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

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

  public static List<int[]> getBallots(String[] candidateNames) {
    List<int[]> ballots = new ArrayList<>();
    int ballotCount = 0;
    
    System.out.println("\n=== Ranked Ballot Collection ===");
    System.out.println("Enter ranked ballots by candidate ID (space-separated).");
    System.out.println("You can rank all candidates or just some (partial ballots allowed).");
    System.out.println("Type 'done' when finished.\n");
    
    // Display candidates once
    System.out.println("Available candidates:");
    for (int i = 0; i < candidateNames.length; i++) {
      System.out.println("  " + i + ": " + candidateNames[i]);
    }
    System.out.println();
    
    while (true) {
      System.out.print("Ballot #" + (ballotCount + 1) + " preferences (e.g., '0 2 1' or 'done'): ");
      String line = sc.nextLine().trim();
      
      // Check for exit
      if (line.equalsIgnoreCase("done")) {
        if (ballotCount == 0) {
          System.out.println("Warning: No ballots entered. Using empty ballot list.");
        }
        break;
      }
      
      // Skip empty lines
      if (line.isEmpty()) {
        System.out.println("Empty ballot skipped. Please enter at least one preference.\n");
        continue;
      }
      
      // Parse ballot
      String[] parts = line.split("\\s+");
      int[] ballot = new int[parts.length];
      boolean valid = true;
      Set<Integer> seen = new HashSet<>();
      
      for (int i = 0; i < parts.length; i++) {
        try {
          int id = Integer.parseInt(parts[i]);
          
          // Validate range
          if (id < 0 || id >= candidateNames.length) {
            System.out.println("Error: Invalid candidate ID '" + id + "' (valid range: 0-" + (candidateNames.length - 1) + ").");
            valid = false;
            break;
          }
          
          // Check for duplicates
          if (seen.contains(id)) {
            System.out.println("Error: Duplicate ID '" + id + "' in ballot.");
            valid = false;
            break;
          }
          
          seen.add(id);
          ballot[i] = id;
          
        } catch (NumberFormatException e) {
          System.out.println("Error: '" + parts[i] + "' is not a valid integer.");
          valid = false;
          break;
        }
      }
      
      // Add valid ballot
      if (valid && ballot.length > 0) {
        ballots.add(ballot);
        ballotCount++;
        System.out.println("✓ Ballot accepted. Preferences: " + formatBallot(ballot, candidateNames) + "\n");
      } else if (!valid) {
        System.out.println("✗ Ballot rejected. Please try again.\n");
      }
    }
    
    return ballots;
  }
  
  private static String formatBallot(int[] ballot, String[] candidateNames) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < ballot.length; i++) {
      if (i > 0) sb.append(" > ");
      sb.append(candidateNames[ballot[i]]);
    }
    return sb.toString();
  }
}
