package hangman_game;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx. scene. control. TextInputDialog;
import javafx.event.ActionEvent;
import java.util.Optional;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;
import javafx.animation.FadeTransition;
import javafx.scene.Node;


enum Part_men // part of the hang_man to show
{
    Clear,
    Head_man,
    man_body,
    right_hand,left_hand,
    right_leg,left_leg
}

public class Controller {

    private Part_men part_men;

    private Game game;

    @FXML
    private Circle Head_man;

    @FXML
    private GridPane gird_pane;

    @FXML
    private Line left_hand;

    @FXML
    private Line left_leg;

    @FXML
    private Line man_body;

    @FXML
    private Line right_hand;

    @FXML
    private Line right_leg;

    @FXML
    private TextFlow word_text;

    @FXML
    private Button new_word;

    @FXML
    private Label Used_Letters;

    @FXML
    public void initialize()
    {

        /// get The file name
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("File Input");
        dialog.setHeaderText("Enter the file name:");
        dialog.setContentText("File:");
        Optional<String> result = dialog.showAndWait();

        if (result.isPresent())
        {
            String file_name = "Myfile.txt" ;//result.get();
            game = new Game(file_name);
            create_Button_Grid();

            setInitialWord(game.getStateGuss().toString());
            reset_men();
            part_men =Part_men.Clear;
        }
    }

    private void create_Button_Grid()
    {

        int column =7,row =4;
        int i=0 ,j=0;
        for(char letter='A'; letter<='Z'; letter++)
        {
            Button button=new Button(String.valueOf(letter)); // create new button
            button.setPrefSize(60,60);
            button.setId(String.valueOf(letter));
            button.setText(String.valueOf(letter));

            final char cur_letter =letter; // to use letter in next line it sholud be final
            button.setOnAction(e->click_button(button,cur_letter));

            // add to grid

            gird_pane.add(button,j,i);
            // column [0,6] , row[0,3]
            j++;
            i += j<column ?0:1; // if we finish the row to get to the next row
            j%=column; // make sure the column stay in range (rest when finish row)
        }
    }

    private void click_button(Button button, char letter) {
        if (left_leg.isVisible() || this.game.did_I_Win()) {
            return; // game is over, prevent more input
        }

        button.setDisable(true); // don't let it be clicked again

        boolean isCorrect = this.game.guss_letter(letter);

        // Color feedback
        if (isCorrect) {
            button.setStyle("-fx-background-color: lightgreen;");
        } else {
            button.setStyle("-fx-background-color: lightcoral;");
            show_next_part();
        }

        // Update used letters label
        String current = Used_Letters.getText();
        Used_Letters.setText(current.equals("Used Letters:")
                ? "Used Letters: " + letter
                : current + " , " + letter);

        // Show result text
        if (left_leg.isVisible())
        {
            PauseTransition pause = new PauseTransition(Duration.seconds(2));
            pause.setOnFinished(e -> {
                setInitialWord("Game Over!!!\nClick 'New word' to try again.");
            });
            pause.play();
        } else if (this.game.did_I_Win())
        {
            PauseTransition pause = new PauseTransition(Duration.seconds(2));
            pause.setOnFinished(e -> {
                setInitialWord(this.game.getGussWord().toString() + ", Congratulations! You won!!\nClick on 'New word' to play again.");
            });
            pause.play();
        } else {
            setInitialWord(this.game.getStateGuss().toString());
        }
    }


    ///  make all the part not visable
    private void reset_men()
    {
        Head_man.setVisible(false);
        man_body.setVisible(false);
        right_hand.setVisible(false);
        left_hand.setVisible(false);
        right_leg.setVisible(false);
        left_leg.setVisible(false);
    }


    /// make the next body part of the hang_man visable
    private void show_next_part()
    {
        switch (part_men)
        {
            case Clear:
                animateBodyPart(Head_man);
                part_men = Part_men.Head_man;
                break;

            case Head_man:
                animateBodyPart(man_body);
                part_men = Part_men.man_body;
                break;

            case man_body:
                animateBodyPart(right_hand);
                part_men = Part_men.right_hand;
                break;

            case right_hand:
                animateBodyPart(left_hand);
                part_men = Part_men.left_hand;
                break;

            case left_hand:
                animateBodyPart(right_leg);
                part_men = Part_men.right_leg;
                break;

            case right_leg:
                animateBodyPart(left_leg);
                part_men = Part_men.left_leg;
                break;
        }


    }

    @FXML
    public void Start_new_game(ActionEvent event) {
        reset_men();
        this.Used_Letters.setText("Used Letters:");
        this.game.start_new_game();
        setInitialWord(this.game.getStateGuss().toString());
        part_men = Part_men.Clear;

        // Reset all letter buttons
        for (char letter = 'A'; letter <= 'Z'; letter++) {
            Button button = (Button) gird_pane.lookup("#" + letter);
            if (button != null) {
                button.setDisable(false);
                button.setVisible(true); // just in case
                button.setStyle(""); // remove custom colors
            }
        }
    }


    // set the text for 'word_text'
    public void setInitialWord(String state) {
        Text text = new Text(state);
        //text.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        word_text.getChildren().setAll(text);


    }

    private void animateBodyPart(Node part)
    {
        part.setOpacity(0);
        part.setVisible(true);
        FadeTransition fade = new FadeTransition(Duration.millis(500), part);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
    }

}