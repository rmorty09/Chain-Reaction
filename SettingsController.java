package chainreaction;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
* <h1>Settings For New Game!</h1>
* Intializes the Controller class.
* Handles the settings for a new game and all the changes 
* made are reflected back in the class App.
* <p>
* @author shailesh
*/
public class SettingsController implements Initializable {

    /**
     * Handles the action to be taken whenever the Button corresponding to 
     * any player is pressed.
     * loads the Controller for the customization page and sets the id
     * of the controller to the number of the player whose corresponding 
     * button is pressed.
     * @param event corresponds to the event of the Player 
     * Button corresponding to every player.
     * @throws IOException
     */
    @FXML
    private void handlePlayerButton(ActionEvent event)throws IOException{
        Button bt=(Button)((Node)event.getSource());
        String str[]=bt.getText().split(" ");
        CustomizationController.id=Integer.parseInt(str[1]);
        Parent menu= FXMLLoader.load(getClass().getResource("customization.fxml"));
        Scene menuScene=new Scene(menu);
        Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(menuScene);
        stage.show();
    }
    
    
    /**
     * handles the action whenever the back button is pressed.
     * loads the controller for the menu page.
     * @param event corresponds to the event of the back button being pressed.
     * @throws IOException 
     */
    @FXML
    private void handleBackButton(ActionEvent event)throws IOException{
        FXMLLoader loader=new FXMLLoader(getClass().getResource("menu.fxml"));
        Parent menu= loader.load();
        Scene menuScene=new Scene(menu);
        Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(menuScene);
        stage.show();
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
    }    
    
}
