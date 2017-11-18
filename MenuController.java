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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
* <h1>Main menu of the new Game!</h1>
* Intializes the Controller class.
* Provides the option of selection of grid type, 
* number of players and access to settings for the game.
* <p>
* @author shailesh
*/
public class MenuController implements Initializable {
    
    /**
     * A dropDown Menu to select the number of players.
     */
    @FXML private ChoiceBox chBox;
    
    
    /**
     * A reference to the Object of class App for the purpose of
     * calling the start function of App to start the game play.
     */
    private App app;
    
    
    /**
     * Initializes app as a new App object.
     */
    public void setApp(){
        app=new App();
        app.clearScene();
    }
    
    
    /**
     * handles the action whenever the settings button is pressed.
     * loads the controller for the settings page.
     * @param event corresponds to the event of the back to settings button being pressed.
     * @throws IOException 
     */
    @FXML
    private void handleSettingsButton(ActionEvent event)throws IOException{
        Parent menu= FXMLLoader.load(getClass().getResource("settings.fxml"));
        Scene menuScene=new Scene(menu);
        Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(menuScene);
        stage.show();
    }
    
    
    /**
     * handles the action whenever the back button is pressed.
     * loads the controller for the startup page.
     * @param event corresponds to the event of the back button being pressed.
     * @throws IOException 
     */
    @FXML
    private void handleBackButton(ActionEvent event)throws IOException{
        Parent menu= FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene menuScene=new Scene(menu);
        Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(menuScene);
        stage.show();
    }
    
    
    /**
     * handles the action whenever the play button is pressed.
     * sets the Grid dimensions as 9 X 6 and calls the start function of app.
     * @param event corresponds to the event of the back to menu button being pressed.
     * @throws IOException 
     */
    @FXML
    private void handlePLayButton(ActionEvent event)throws IOException{
        setApp();
        app.setGridSize(9,6);
        Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        app.start(stage);
    }
    
    
    /**
     * handles the action whenever the Play With HD Grid button is pressed.
     * Sets the Grid dimensions as 15 x 10 and calls the start function of app.
     * @param event corresponds to the event of the back to menu button being pressed.
     * @throws IOException 
     */
    @FXML
    private void handleHDButton(ActionEvent event)throws IOException{
        setApp();
        app.setGridSize(15,10);
        Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        app.start(new Stage());
        stage.close();
    }
    
    
    /**
     * handles the action whenever the OK button on the menu page is pressed.
     * Sets the number of players for the new game.
     * @param event corresponds to the event of the OK button on the menu page being pressed.
     * @throws IOException 
     */
    @FXML
    private void handleDropDown(ActionEvent event)throws IOException{
        String str[]=((String)chBox.getValue()).split(" ");
        app.players=Integer.parseInt(str[0]);
    }
    
    
    /**
     * Allows the implementing class to perform any necessary 
     * post-processing on the content.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param rb The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       this.chBox.getItems().addAll("2 Players","3 Players","4 Players","5 Players","6 Players","7 PLayers","8 Players");
       this.chBox.setValue(App.players+" Players");
       this.chBox.setTooltip(new Tooltip("Select Players"));
       this.chBox.setId("ch");
    }    
    
}
