package hangman_game;

import java.util.ArrayList;
import java.lang.String;
import java.lang.StringBuilder;
import java.lang.Character;

// This class handle the game hang man with a word and the method like add letter..
public class Hang_man_word
{
    private String word; //the word to guss
    private StringBuilder state_guss; //  it will be  _____ and be filled as nedded
    private int letter_count; // how many letter guessed
    private ArrayList<Integer> Hash[];  // hash tabel for each letter stor the index it in

    public Hang_man_word(String word) {
        this.word = word.toUpperCase();
        Hash = new ArrayList[26];
        this.state_guss = new StringBuilder();
        letter_count = 0;

        for (int i = 0; i < word.length(); i++)
        {
            this.state_guss.append("_ ");

            // fill the hash table
            if(Hash[this.word.charAt(i)-'A'] == null)
            {
                Hash[this.word.charAt(i) - 'A'] = new ArrayList<>();
                Hash[this.word.charAt(i) - 'A'].add(i);
            }
            else
                Hash[this.word.charAt(i) - 'A'].add(i);
        }


    }

    // get mehtods
    public String getWord() {return word;}
    public StringBuilder getState_guss() {return state_guss;}
    public int getLetter_count() {return letter_count;}

    // if the letter in the word return true and fill it in state_guss else return false
    public boolean guess_letter(char letter)
    {
        if(!Character.isAlphabetic(letter)) // if not alphatbet
            return false;
        letter = Character.toUpperCase(letter);
        if(Hash[letter-'A'] == null)
            return false;
        for (int i : Hash[letter-'A']) // all the index in hash that letter apperars in word
        {
            state_guss.setCharAt(2*i, letter); /// X2 = for every char we put '_ ' so one _  and space
            letter_count++;
        }
        return true;
    }

    public boolean Did_I_Win() // if al the letter gussed
    {
        return letter_count == this.word.length();
    }

}
