/**
 * See klass haldab kogumängu
 */

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Constructor is empty
 */
public class Model {
    /**
     * mämgus kasutatav minimum arv
     */
    private final int MINIMUM = 1;
    /**
     * maksimum arv mängus
     */
    private final int MAXIMUM = 100;
    /**
     * Failinimi, kuhu tulemused salvestatakse.
     */

    private final String filename = "scoreboard.txt";
    /**
     * Scanner-objekt, mida kasutatakse kasutaja sisendi saamiseks.
     */
    private final Scanner scanner = new Scanner(System.in);
    /**
     * Arvuti poolt valitud number.
     */
    private int pcnumber;
    /**
     * Mängija tehtud katsete arv.
     */
    private int steps;
    /**
     * Booleani väärtus, mis näitab, kas mäng on lõppenud või mitte.
     */
    private boolean gameOver;
    /**
     * Tulemuste nimekiri.
     */
    private List<Content> scoreboard = new ArrayList<>();
    /**
     * Näitab menüüd ja küsib valikut kasutajalt.
     */

    // loome menüü
    public void showMenu() {
        System.out.println("1. Play");
        System.out.println("2. Scoreboard");
        System.out.println("3. Exit");
        System.out.print("Enter 1, 2, or 3: ");
        int choice = scanner.nextInt();
        // System.out.println(choice);  // TEST
        switch (choice) {
            case 1 -> {
                // System.out.println("Play"); // Test
                setupGame();
                letsPlay();
            }
            case 2 -> {
                // näitab skooritabelit ja menüüd
                showScoreboard();
                showMenu();
                System.out.println("Scoreboard");
            }
            case 3 -> {
                System.out.println("Bye, bye..");
                System.exit(0);
            }
            default -> // any wrong choice
                    showMenu(); // show menu again
        }


    }
    /**
     * Loob uue mängu seadistused.
     */
    private void setupGame() {
        pcnumber = ThreadLocalRandom.current().nextInt(MINIMUM, MAXIMUM + 1);
        steps = 0;
        gameOver = false;

    }
    /**
     * Küsib mängijalt arvu, kontrollib kas arvuti genereeritud arvuga võrdub ning annab vastavaid vihjeid.
     */

    private void ask() {
        System.out.printf("Input number between %d - %d: ", MINIMUM, MAXIMUM);
        int userNumber = scanner.nextInt();
        steps += 1;
        if (userNumber > pcnumber && userNumber != 10000) {
            System.out.println("Smaller");
        } else if (userNumber < pcnumber && userNumber != 10000) {
            System.out.println("Bigger");
        } else if (userNumber == pcnumber && userNumber != 10000) {
            System.out.printf("You guessd the number in %d steps.%n", steps);
            gameOver = true;
        } else { // 10000 backdoor
            System.out.printf("You found the back door!! The correct number is %d%n", pcnumber);
        }
    }
    /**
     * Mängib mängu seni, kuni arvuti arvatakse ära. Pärast küsib mängijalt nime ning salvestab selle koos sammude arvuga faili.
     */
    private void letsPlay() {
        while (!gameOver) {
            ask();
        }
        askName();
        showMenu();

    }
    /**
     * Küsib mängijalt nime ja kirjutab selle koos mängu sammude arvuga faili.
     */
    private void askName() {
        System.out.println("Enter your name");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String name;
        try {
            name = br.readLine();
            if (name.strip().length() > 1) {
                writeToFile(name);

            } else {
                System.out.println("Name is too short!");
                askName();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * salvestab mängija nime ja tulemuse faili
     * @param name mängija nimi
     */
    private void writeToFile(String name) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename, true))) {
            String line = name + ";" + steps;
            bw.write(line);
            bw.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * kuvab edetabel
     */
    private void showScoreboard() {
        readFromFile();
        System.out.println();  // empty line
        for(Content c : scoreboard) {
            c.showData();
        }
        System.out.println(); // empty line



    }

    /**
     * Loeb failist skooritabeli andmed ja loob nendest uued Content objektid
     */
    private void readFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            scoreboard = new ArrayList<>();
            for (String line; (line = br.readLine()) != null; ) {
                String name = line.split(";")[0];
                int steps = Integer.parseInt(line.split(";")[1]);
                scoreboard.add(new Content(name, steps));

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}


