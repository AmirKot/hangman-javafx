package hangman_game;

import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Scanner;
import java.util.ArrayList;

public class Game
{
    private String File_name; // or path
    private ArrayList<String> words;
    private Hang_man_word hang_man_word;

    private int index =0; // the index of the word in the array
    private int len =0; // the number of the


    private boolean flag =false;// if file is opend

    public Game(String file_name)
    {
        this.words = new ArrayList<>();
        this.File_name = file_name;

        int attempts = 0;
        boolean success = false;

        // 🔁 Try loading the file up to 3 times
        while (attempts < 3 && !(success = loadWordsFromFile()))
        {
            attempts++;

            // 📥 Ask user for a new file path if the current one fails
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("📁 File Not Found");
            dialog.setHeaderText("Couldn't open file: " + this.File_name);
            dialog.setContentText("Enter another file path:");
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent())
                this.File_name = result.get();
            else
                break; // ❌ User canceled
        }

        // 🚨 Fallback to default file if user failed 3 times or canceled
        if (!success)
        {
            if (!loadDefaultFile())
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Default File Error 😞");
                alert.setHeaderText("Could not load default word list.");
                alert.setContentText("Please ensure 'default_words.txt' exists in resources.");
                alert.showAndWait();
                throw new RuntimeException("Failed to load any word list.");
            }
        }

        // ✅ Initialize game with first word
        this.len = this.words.size();
        this.hang_man_word = new Hang_man_word(this.words.get(index++));
    }

    private boolean loadDefaultFile()
    {
        try
        {
            InputStream input = getClass().getResourceAsStream("/hangman_game/default_words.txt");
            if (input == null)
                return false;

            Scanner scanner = new Scanner(input);
            while (scanner.hasNextLine())
                this.words.add(scanner.nextLine().trim());

            scanner.close();
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    public void setFile_name(String file_name) // chnage according to the new file name
    {

        if (!flag)
            throw new RuntimeException("No valid file was provided. Exiting program.");
        this.hang_man_word = new Hang_man_word(this.words.get(0));
    }

    //  Load the word list from the file (true = success)
    private boolean loadWordsFromFile()
    {
        try
        {
            Scanner scanner = new Scanner(new File(this.File_name));
            while (scanner.hasNextLine())
            {
                this.words.add(scanner.nextLine());
            }
            scanner.close();
            return true; //  Success
        }
        catch (FileNotFoundException e)
        {
            return false; //  File missing
        }
    }


    // get methods
    public String getGussWord() {return this.hang_man_word.getWord();}
    public StringBuilder getStateGuss(){return this.hang_man_word.getState_guss();}
    public String getFile_name() {return this.File_name;}

    // start new game
    public void  start_new_game()
    {
        this.index = (this.index + 1) % this.len; // make sure to stay in range
        this.hang_man_word = new Hang_man_word(this.words.get(index));
    }

    // game actions
    public boolean did_I_Win() {return this.hang_man_word.Did_I_Win();}
    public int  getLetter_count() {return this.hang_man_word.getLetter_count();}
    public boolean guss_letter(char letter){return this.hang_man_word.guess_letter(letter);}



}
