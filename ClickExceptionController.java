package chainreaction;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.stage.Stage;

/**
* <h1>Click Exception Handler!</h1>
* Intializes the Controller class.
* Handles the exception whenever a click is made during
* the game in a invalid cell.
* <p>
* @author shailesh
*/
public class ClickExceptionController implements Initializable {

    /**
     * Handles the action whenever the OK Button on the 
     * ClickException Pop up page is clicked.
     * Closes the pop up window whenever OK Button on the 
     * ClickException Pop up page is clicked.
     * @param event corresponds to the event of the OK Button being clicked.
     */
    @FXML
    public void handleOkButton(ActionEvent event){
        Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
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
