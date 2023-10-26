import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
/**
 * Write a description of class WordleGame here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class WordleGame
{
    private String secret;
    private JFrame frame;
    private JPanel panel1; //words guessed
    private JPanel panel2; //keyboard
    private JButton [] guesses;
    private JButton [] keyboard;
    private int currentGuess;

    public WordleGame() {
        frame = new JFrame();
        frame.setLayout(new GridLayout(2, 1));
        panel1 = new JPanel(new GridLayout(6, 5));
        panel2 = new JPanel(new GridLayout(3, 9));
        guesses = new JButton[30];
        keyboard = new JButton[27];
        String [] keys = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N",
                "O","P","Q","R","S","T","U","V","W","X","Y","Z", "ENTER"};
        currentGuess = 0;

        //initialize buttons and add to panel
        for (int i = 0; i < 30; i++) {
            guesses[i] = new JButton("");
            panel1.add(guesses[i]);
        }
        for (int i = 0; i < 27; i++) {
            keyboard[i] = new JButton(keys[i]);
            panel2.add(keyboard[i]);
        }
        for (int i = 0; i < 27; i++) {
            keyboard[i].addActionListener(new ActionListenerLetter(this));
        }


        frame.add(panel1);
        frame.add(panel2);
        frame.setSize(600, 800);
        frame.setVisible(true);

        //choose a random word
        Scanner sc = new Scanner("possibleAnswers.txt");
        for (int i = 0; i < (int) (Math.random() * 10000); i++)
            secret = sc.nextLine();
    }
    public int getCurrentGuess() {
        return currentGuess;
    }
    public void setCurrentGuess(int x) {
        currentGuess = x;
    }
    public void setLetter(int index, String letter) {
        guesses[index].setText(letter);
    }
    public static void main (String [] args) {
        WordleGame x = new WordleGame();

    }
    public static Color[] colorize(String secret, String guess) {
        Color[] answers = new Color[secret.length()];
        Map<Character, Integer> map = new HashMap<>();
        // create a count of how many of each letter is in the secret word
        for (int i = 0; i < secret.length(); i++) {
            Character letter = secret.charAt(i);
            if (map.containsKey(letter))
                map.put(letter, map.get(letter) + 1);
            else
                map.put(letter, 1);
        }
        // determine the colors for each letter

        // do the greens first
        for (int i = 0; i < guess.length(); i++) {
            Character letter = guess.charAt(i);
            if (secret.charAt(i) == letter) {
                answers[i] = Color.green;
                map.put(letter, map.get(letter) - 1);
            }
        }

        // yellow or gray
        for (int i = 0; i < guess.length(); i++) {
            Character letter = guess.charAt(i);
            if (secret.charAt(i) == letter)
                continue; // if green answer color is already set
            if (map.containsKey(letter) && map.get(letter) > 0) {
                answers[i] = Color.yellow;
                map.put(letter, map.get(letter) - 1);
            } else {
                answers[i] = Color.gray;
            }
        }
        return answers;
    }
    public String getKey(int index) {
        return keyboard[index].getText();
    }
    public String getSecret() {
        return "happy";
    }
    public void setButtonColor(int index, Color color) {
        guesses[index].setBackground(color);
        guesses[index].setOpaque(true);
        guesses[index].setBorderPainted(false);
    }

    public static void main(String[] args) {
        WordleGame x = new WordleGame();
    }
}