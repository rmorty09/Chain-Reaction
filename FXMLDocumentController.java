package chainreaction;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
* <h1>Startup Menu!</h1>
* The FXMLDocument controller setups the startup menu
* for the game.
* <p>
*
* @author  shailesh
* @version 1.0
* @since   2017-08-11
*/
public class FXMLDocumentController implements Initializable {
    /**
     * Reference to an object of class App.
     */
    public App app;
    
   
    /**
     * Handles the action to be taken whenever the new Game Button
     * is pressed.
     * loads the menu for a new game.
     * @param event corresponds to the event of clicking the new Game button.
     * @throws IOException 
     */
    @FXML
    private void handleStartButton(ActionEvent event) throws IOException{
        Parent menu= FXMLLoader.load(getClass().getResource("menu.fxml"));
        Scene menuScene=new Scene(menu);
        Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(menuScene);
        stage.show();
    }
    
    
    
    /**
     * Handles the action to be taken whenever the resume button is pressed.
     * loads the last saved game if any else takes no action.
     * @param event corresponds to the event of clicking the resume button.
     */
    @FXML
    private void handleResumeButton(ActionEvent event){
        Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        File file=new File("saveAction.txt");
        if(file.length()!=0){
            app=new App();
            try{
    		ObjectInputStream in=new ObjectInputStream(new FileInputStream("saveAction.txt"));
    		SaveClass lol=(SaveClass)in.readObject();
    		app.setGridSize(lol.box.length,lol.box[0].length);
    		app.start(new Stage());
    		app.colorId=lol.clrId;
                app.text.setText("Player "+(app.colorId+1));
                app.rect.setFill(app.color[app.colorId]);
    		for(int i=0;i<app.row;++i){
    			for(int j=0;j<app.col;++j){
    				app.grid[i][j].Id=lol.box[i][j].Id;
                                if(app.grid[i][j].Id!=-1)app.grid[i][j].color=app.color[app.grid[i][j].Id];
                                else app.grid[i][j].color=null;
    				for(int k=0;k<=lol.box[i][j].count;++k){
    					app.grid[i][j].incrCount();
                                        int cord[]={i,j};
    					app.setSphereAction(app.grid[i][j].addInGroup(),cord);
    				}
    			}
    		}
                app.text.setText("Player "+(app.colorId+1));
                app.setRectColor(app.colorId);
    		lol=null;
                stage.close();
    	}
    	catch(Exception e){
    		e.printStackTrace();
            }
        }
    }
    
    
    /**
     * Handles the action to be taken whenever the exit button is pressed.
     * Closes the stage.
     * @param event corresponds to the event of clicking the exit button.
     */
    @FXML
    private void handleExitButton(ActionEvent event){
       Stage stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
       stage.close();
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
