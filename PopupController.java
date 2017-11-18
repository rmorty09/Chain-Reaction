package chainreaction;
import chainreaction.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
* <h1>Pop up Window to declare the Winner!</h1>
* Intializes the Controller class.
* Handles the option of whether the player wants to start
* the game again or to go back to the menu after the winner 
* has been decided.
* <p>
* @author shailesh
*/
public class PopupController implements Initializable {

    /**
     * Reference to a Object of class App for
     * the purpose of restarting the game.
     */
    public static App app;
    
    
    /**
     * Determines the number of the winning player.
     */
    public static int id;
    
    
    /**
     * Reference to the stage of the App object
     * referenced by app.
     */
    public static Stage st;
    
    /**
     * Text to display the Winning player.
     */
    @FXML public Text text;
    
    
    
    /**
     * handles the action whenever the start again button is pressed.
     * clears the scene of the app and calls the start function of app
     * essentially restarting the game.
     * @param event corresponds to the event of the back button being pressed.
     */
    @FXML
    public void handleStartButton(ActionEvent event){
        app.clearScene();
        app.start(app.tstage);
        Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }
    
    
    /**
     * handles the action whenever the back to Menu button is pressed.
     * loads the controller for the menu page.
     * @param event corresponds to the event of the back to menu button being pressed.
     * @throws IOException 
     */
    @FXML
    public void handleBackToMenuButton(ActionEvent event) throws IOException{
       // app.changeToMenu();
        Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        Parent menu= FXMLLoader.load(getClass().getResource("menu.fxml"));
        Scene menuScene=new Scene(menu);
        stage.setScene(menuScene);
        stage.show();
        st.close();
    }
    
    
    
    /**
     * Allows the implementing class to perform any necessary 
     * post-processing on the content.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param rb The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.text.setFont(Font.font("Veranda", FontWeight.BOLD,18));
        text.setText(" Winner is Player "+(id+1));
        //app.tstage.close();
    }    
    
}
