package chainreaction;

import java.io.*;
import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
* <h1>Handling Game play!</h1>
* The class App handles :
* The UI for Game play
* And the functionalities of Game play .
* <p>
*
* @author  shailesh
* @version 1.0
* @since   2017-08-11
*/
public class App extends Application {
        /**
         * Keeps track of whether a click is supposed to 
         * initiate an action or not.
         */
        public static boolean startValue=true;
        
        
        /**
         * A Button with Side DropDown to either go back 
         * to menu or restart the game.
         */
        public static SplitMenuButton dropDown=new SplitMenuButton();
        
        
        /**
         * Determines the length of a horizontal transition.
         */
        public int hLineTo;
        
        
        /**
         * Determines the length of a vertical transition.
         */
        public int vLineTo;
        
        
        /**
         * Rectangle to display the Colour assigned to 
         * the player who is to play on the current turn.
         */
        public static Rectangle rect=new Rectangle(120,15,30,20);
        
        
        /**
         * Text to display the number of the player 
         * who is to play on the current turn.
         */
        public static Text text=new Text(20,30,"Player 1");
        
        
        /**
         * Keeps track of the number of transitions.
         */
        public static int transitions=0;
        
        
        /**
         * Reference to the Stage on which the Game Play is being displayed.
         */
	public static Stage tstage;
        
        
        /**
         * Keeps track of the Colours assigned to the players.
         */
        public static Color[] color={Color.RED,Color.BLUE,Color.GREEN,Color.SLATEBLUE,Color.BEIGE,Color.CYAN,Color.MAGENTA,Color.OLIVE};
        
        
        /**
         * Keeps track of the number of players.
         */
        public static int players=4;
        
        
        /**
         * Root Node of the scene.
         */
	public static Group root;
        
        
        /**
         * Keeps track of the number of rows in the Grid.
         */
	public static int row;
        
        
        /**
         * Keeps track of the number of columns in the Grid.
         */
        public static int col;
        
        
        /**
         * Determines the current Index in use of the color array storing 
         * the Colour assigned to the player.
         * Also determines the player who is to play on 
         * the current turn.
         */
        public static int colorId=0;
        
        
        /**
         * 2 Dimensional array to change the X and y 
         * coordinate of the current cell to visit
         * all the orthogonal cells.
         */
	public static int changeDir[][]={{0,-1},{-1,0},{0,1},{1,0}};
        
        
        /**
         * 2 Dimensional array of row x col size with each element representing 
         * a cell of the grid.
         */
	public static GridBox grid[][]=new GridBox[row][col];
        
        
        /**
         * Object of Class JavaFx.scene.Scene.
         * Works as a container for all the content.
         */
	public static Scene scene;
        
        
        /**
        * This method is used to fill the colour in the
        * rectangle showing the colour assigned to the
        * player whose is to play on the current turn
        * @param id index of the Colour to assigned from the color array
        */
        public void setRectColor(int id){
            rect.setFill(color[id]);
        }
        
        
        /**
        * This method is used to check whether the cell with the mentioned arguments is 
        * in bounds. 
        * @param i X coordinate of the cell
        * @param j Y coordinate of the cell
        * @return boolean This returns whether the cell is in bounds
        */
	public static boolean isvalid(int i,int j){
		if(i<row && i>=0 && j<col && j>=0)return true;
		return false;
	}
        
        
        /**
        * This method is used to set the size of the grid. 
        * This method also initialises the root node,
        * Grid, scene and the length of the path used in transition
        * of the balls
        * @param row parameter denoting the length of row
        * @param col parameter denoting the length of column
        */
	public void setGridSize(int row,int col){
                root=new Group();
		this.row=row;
		this.col=col;
		grid=new GridBox[row][col];
		scene=new Scene(root,400,640);
                if(row==9){
                    hLineTo=60;
                    vLineTo=60;
                }
                else{
                    hLineTo=35;
                    vLineTo=35;
                }
	}
        
        
        /**
        * This method is used to scan the Grid and Check if the
        * colours of all the balls are same and equal to id. 
        * This method also changes the Id of cell whose color is equal to the ref.        
        * @param ref Reference Colour to be checked for
        * @param setId The Id of the cell where the initial splitting took place 
        */
        public void checkForWinner(Color ref,int setId){
            --transitions;
            boolean value=true;
            
                for(int i=0;i<row;++i){
                    for(int j=0;j<col;++j){
                        if(grid[i][j].color==ref)grid[i][j].Id=setId;
                        if(grid[i][j].count==-1)grid[i][j].Id=-1;
                        if(grid[i][j].color==ref || grid[i][j].color==null)value=true;
                        else {
                            value=false;
                            break;
                        }
                    }
                    if(!value)break;
                }
                
            if(value && transitions==0){
                startValue=false;
                try{
                    PopupController.app=this;
                    PopupController.id=setId;
                    PopupController.st=tstage;
                    Parent popup= FXMLLoader.load(getClass().getResource("popup.fxml"));
                    Scene popupScene=new Scene(popup);
                    Stage st=new Stage();
                    st.setScene(popupScene);
                    st.show();
                }
                catch(Exception e){}
            }
        }
        
        
        /**
        * This method is used to handle the splitting of the balls
        * using the Depth First Search algorithm and PathTransitions.
        * It also calls the checkWinner function at the end of Transitions
        * to scan for the winner. Also increments the transition variable
        * @param cordinates Coordinates of the parent Cell
        * @param ind  Id of the root cell i.e the cell where initial splitting took place
        */
	public void dfs(int[] cordinates,int ind){
		int src_i=cordinates[0],src_j=cordinates[1];
		for(int i=0;i<4;++i){
			if(isvalid(src_i+changeDir[i][0],src_j+changeDir[i][1])){
				GridBox t=grid[src_i+changeDir[i][0]][src_j+changeDir[i][1]];
				Sphere tmp=grid[src_i][src_j].removeFromGroup();
				double x=tmp.getTranslateX(),y=tmp.getTranslateY();
				int tmpCord[]={src_i+changeDir[i][0],src_j+changeDir[i][1]};
				root.getChildren().add(tmp);
				Path path=new Path();
				path.getElements().add(new MoveTo(x,y));
				if(changeDir[i][0]==0 ){
					if(changeDir[i][1]<0)path.getElements().add(new HLineTo(x-hLineTo));
					else path.getElements().add(new HLineTo(x+hLineTo));
				}
				else {
					if(changeDir[i][0]<0)path.getElements().add(new VLineTo(y-vLineTo));
					else path.getElements().add(new VLineTo(y+vLineTo));
				}
                                ++transitions;
				PathTransition tr=new PathTransition();
				tr.setNode(tmp);
				tr.setCycleCount(1);
				tr.setDuration(Duration.millis(150));
				tr.setPath(path);
				tr.onFinishedProperty().set(e->{
                                    root.getChildren().remove(tmp);
                                    t.incrCount();
                                    setSphereAction(t.addInGroup(),tmpCord);
                                    grid[tmpCord[0]][tmpCord[1]].changeColor(grid[src_i][src_j]);
                                    if(!t.check()){
					dfs(tmpCord,ind);
                                    }
                                    checkForWinner(grid[src_i][src_j].color,ind);
				});
				tr.play();
			}
		}
	}
        
        
        /**
        * This method is used to . This is
        * a the simplest form of a class method, just to
        * show the usage of various javadoc Tags.
        * @param objArr This is the first paramter to addNum method
        * @param cordinates  This is the second parameter to addNum method
        */
        public void setSphereAction(Object[] objArr,int[] cordinates){
            GridBox tmp=(GridBox)objArr[0];
            Sphere sphere=(Sphere)objArr[1];
            sphere.setOnMouseClicked(e->{
               startSplit(tmp,cordinates) ;
            });
        }
        
        
        /**
        * This method is used to start the Splitting of the balls.
        * Before starting the splitting the function also saves the 
        * state of the game for the purpose of the Undo Action.
        * It also checks whether the click is made on the right cells,
        * if not then throws a pop up window indicating the invalid selection.
        * @param currCell Current Cell on which the click is pressed
        * @param cordinates  Coordinates of the cell
        */
	public void startSplit(GridBox currCell,int[] cordinates){
            saveLastAction();
            if(currCell.count==-1){
                currCell.color=color[colorId];
                currCell.Id=colorId;
            }
            if(currCell.Id==colorId && startValue){
                currCell.incrCount();
                if(currCell.check()){
                   setSphereAction( currCell.addInGroup(),cordinates);
                }
                else{
                    currCell.addInGroup();
                    dfs(cordinates,currCell.Id);
                    System.out.println(transitions);
                }
                if(++colorId==players)colorId=0;
                    text.setText("Player "+(colorId+1));
                    rect.setFill(color[colorId]);
            }
            else if(startValue){
                try{
                    //clearScene();
                    Parent invalidPopup= FXMLLoader.load(getClass().getResource("clickException.fxml"));
                    Scene popupScene=new Scene(invalidPopup);
                    Stage st=new Stage();
                    st.setScene(popupScene);
                    st.show();
                }
                catch(Exception e){}
            }
        }
        
        
        /**
        * This method initialises individual cells of the grid. 
        * Calls the setCell function of individual cells. 
        * Defines the action to performed on Mouse Click.
        */
	public void intialize(){
		for(int i=0;i<row;++i){
			for(int j=0;j<col;++j){
				int cordinates[]={i,j};
				grid[i][j]=new GridBox();
				grid[i][j].setCell(i,j);
				GridBox tmp=grid[i][j];
				if(i==0 || i==row-1){
					if(j==0 || j==col-1)grid[i][j].setCrCount(2);
					else grid[i][j].setCrCount(3);
				}
				else if(j==0 || j==col-1)grid[i][j].setCrCount(3);
				else grid[i][j].setCrCount(4);
				grid[i][j].rect1.setOnMouseClicked(e->{
                                    startSplit(tmp,cordinates);
                            });
			}
		}
	}
        
        
        /**
        * This method is used to clear the Scene of the Game play. 
        * Sets all the components to their default value.
        */
        public void clearScene(){
            if(root!=null) this.root.getChildren().clear();
            startValue=true;
            dropDown.getItems().clear();
            this.colorId=0;
            this.transitions=0;
            text.setText("PLayer 1");
        }
        
        
        /**
        * The main entry point for all JavaFX applications. 
        * The start method is called after the init method has returned,
        * and after the system is ready for the application to begin running.
        * @param stage The primary Stage constructed by the platform.
        * Display the grid .
        * Sets all the button Actions.
        * Calls the function to initialise the grid.
        * Sets all the components to their default value.
        * Adds all the components to the root node.
        * @param stage The primary Stage constructed by the platform.
        */
	@Override
	public void start(Stage stage){
		tstage=stage;
                tstage.setOnCloseRequest(e->{
                   handleStageClosingButton();
                });
		stage.setTitle("CHAIN REACTION");
		Button save=new Button("Save");
		Button undo=new Button("Undo");
		intialize();
		System.out.println("start");
		for(int i=0;i<row;++i){
			for(int j=0;j<col;++j){
				root.getChildren().add(grid[i][j].rect1);
				root.getChildren().add(grid[i][j].gp);
			}
		}
		save.setStyle(" -fx-text-fill: white; -fx-font-family: Arial Narrow; -fx-font-weight: bold; -fx-background-color: linear-gradient(#61a2b1, #2A5058); -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );");
		undo.setStyle(" -fx-text-fill: white; -fx-font-family: Arial Narrow; -fx-font-weight: bold; -fx-background-color: linear-gradient(#61a2b1, #2A5058); -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );");		save.setLayoutX(220);
		save.setLayoutX(220);
		save.setLayoutY(600);
		undo.setLayoutX(60);
		undo.setLayoutY(600);
		save.setPrefSize(100,35);
		undo.setPrefSize(100,35);
                save.setOnAction(e->{
                   saveAction();
                });
                undo.setOnAction(e->{
                    undoAction();
                });
                dropDown.setText("Back to Menu");
                dropDown.setLayoutX(260);
                dropDown.setLayoutY(10);
                dropDown.setPrefSize(125,30);
                MenuItem restart=new MenuItem("Restart");
                dropDown.getItems().add(restart);
                dropDown.setOnAction(e->{
                    try{
                        clearScene();
                        SaveClass lol=new SaveClass(grid,row,col,colorId);
                        ObjectOutputStream out=new ObjectOutputStream(new FileOutputStream("saveAction.txt"));
                        out.writeObject(lol);
                        Parent mainPage= FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
                        Scene popupScene=new Scene(mainPage);
                        Stage st=new Stage();
                        st.setScene(popupScene);
                        st.show();
                        tstage.close();
                    }
                    catch(Exception ex){}
                });
                restart.setOnAction(e->{
                    try{
                        clearScene();
                        start(tstage);
                    }
                    catch(Exception ex){}
                });
                text.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
                text.setFill(Color.SLATEBLUE);
                rect.setFill(color[colorId]);
                rect.setStrokeWidth(1);
                rect.setStroke(Color.BLACK);
		root.getChildren().addAll(save,undo,text,rect,dropDown);
                scene.getStylesheets().add("cssSheet/DropBoxStyleSheet.css");
		stage.setScene(scene);
		stage.show();
	}
        
        
    /**
     * Main method which calls the launch method to Display the UI and start the game.
     * @param args the command line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    
   /**
   * This method is used to save the state of the game.
   * Uses serialisation using ObjectOutputStream on Object of class SaveClass.
   * Unlike saveAction saves the state in file saveLastAction.txt instead of saveAction.txt.
   */
    public void saveLastAction(){
         SaveClass lol=new SaveClass(grid,row,col,colorId);
    	try{
    		ObjectOutputStream out=new ObjectOutputStream(new FileOutputStream("saveLastAction.txt"));
    		out.writeObject(lol);
    	}
    	catch(Exception e){
    		System.out.println(e.toString());
    	}
    }
    
    
   /**
   * This method is used to achieve the previous state of the game.
   * Uses deserialisation using ObjectInputStream on Object of Class SaveClass.
   */
    public void undoAction(){
        if(startValue){
            try{
                    ObjectInputStream in=new ObjectInputStream(new FileInputStream("saveLastAction.txt"));
                    SaveClass lol=(SaveClass)in.readObject();
                    //root.getChildren().clear();
                    clearScene();
                    start(tstage);
                    this.colorId=lol.clrId;
                    this.text.setText("Player "+(this.colorId+1));
                    this.rect.setFill(this.color[this.colorId]);
                    for(int i=0;i<row;++i){
                            for(int j=0;j<col;++j){
                                    System.out.println("color "+lol.box[i][j].Id+" count "+lol.box[i][j].count);
                                    grid[i][j].Id=lol.box[i][j].Id;
                                    if(grid[i][j].Id!=-1)grid[i][j].color=color[grid[i][j].Id];
                                    else grid[i][j].color=null;
                                    for(int k=0;k<=lol.box[i][j].count;++k){
                                            grid[i][j].incrCount();
                                            grid[i][j].addInGroup();
                                    }
                            }
                    }
                    lol=null;
            }
            catch(Exception e){
                    System.out.println(e.toString());
            }
        }
    }
    
    
   /**
   * This method is used to save the current state of the Game . 
   * Unlike the saveLastAction this method saves the current state of the game in the
   * file saveAction.txt instead of saveLastAction.txt .
   * Uses ObjectOutputStream to write the object of SaveClass type. 
   */
    public void saveAction(){
        SaveClass Save=new SaveClass(grid,row,col,colorId);
        try{
            ObjectOutputStream out=new ObjectOutputStream(new FileOutputStream("saveAction.txt"));
            out.writeObject(Save);
        }
        catch(Exception e){
        System.out.println(e.toString());}
    }
    
    
   /**
   * This method is used to Save the Sate of the game whenever the closing button is Pressed.
   * Calls the saveAction function whenever the the closing button is pressed
   * before closing the stage.
   */
    public void handleStageClosingButton(){
        saveAction();
    }
}
