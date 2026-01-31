package election;

import election.util.InputUtil;
import election.util.OutputUtil;
import election.algorithm.Plurality;
import election.algorithm.Runoff;
import election.algorithm.Tideman;

public class Main {
  public static void main(String[] args) {
    OutputUtil.printWelcomeMessage();
    InputUtil.askContinueOrExit();

    // Take input for main menu choice
    while (true) {
      int choice = InputUtil.MainMenuChoice();
      if (choice != 1 && choice != 2 && choice != 3 && choice != 4) {
        System.out.println("Invalid choice. Please try again.");
        continue;
      }
      switch (choice) {
        case 1 -> {
          System.out.println("Run Plurality Election...");
          Plurality plurality = new Plurality();
          plurality.run();
        }
        case 2 -> {
          System.out.println("Run Runoff Election...");
          Runoff runoff = new Runoff();
          runoff.run();
        }
        case 3 -> {
          System.out.println("Run Tideman Election...");
          Tideman tideman = new Tideman();
          tideman.run();
        }
        case 4 -> {
          OutputUtil.exitApplication();
        }
      }
    }
  }
}
