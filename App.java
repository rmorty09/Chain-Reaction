import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.animation.*;
import javafx.util.Duration;
import java.io.*;
class GridLine extends Line implements Serializable{
	public void setLine(double x1,double y1,double x2,double y2){
      this.setStartX(x1);
      this.setStartY(y1);
      this.setEndX(x2);
      this.setEndY(y2);
   }
}
class GridBox extends Rectangle implements Serializable{
	public double setCord[][]={{0,0},{13,-5},{13,5},{0,5}};
	public Rectangle rect1;
	public Rectangle rect2;
	public GridLine line1=new GridLine();
	public GridLine line2=new GridLine();
	public GridLine line3=new GridLine();
	public GridLine line4=new GridLine();
	public int count=0;
	private int criticalCount;
	private Sphere arr[]=new Sphere[4];
	public Group gp=new Group();
	public void  setSphere(){
		Sphere tmp=arr[count];
		tmp.setRadius(10);
        tmp.setTranslateX(this.rect1.getX()+20.0+setCord[count][0]);
        tmp.setTranslateY(this.rect1.getY()+30.0+setCord[count][1]);
	}

	public int getCount(){return count;}

	public void setCrCount(int c){criticalCount=c;}

	public void incrCount(){++count;
		System.out.println(count);}

	public void addInGroup(){
		arr[count]=new Sphere();
		setSphere();
		gp.getChildren().add(arr[count]);
		RotateTransition tr=new RotateTransition();
		tr.setNode(gp);
      	tr.setDuration(Duration.millis(600));
      	tr.setByAngle(360);
      	tr.setCycleCount(Timeline.INDEFINITE); 
      	tr.setAutoReverse(false); 
      	tr.play();
	}

	public void removeFromGroup(){
		for(int i=0;i<criticalCount;++i){
			gp.getChildren().remove(arr[count]);
			--count;
		}
	}

	public boolean check(){
		return count==criticalCount;
	}

	public void setgrid(int i,int j){
		rect1=new Rectangle(20+j*60,20+i*60,60,60);
        rect1.setFill(Color.TRANSPARENT);
        rect1.setStrokeWidth(1);
        rect1.setStroke(Color.RED);

        rect2=new Rectangle(29+j*57,33+i*57,57,57);
        rect2.setFill(Color.TRANSPARENT);
        rect2.setStrokeWidth(1);
        rect2.setStroke(Color.RED);  

        line1.setLine(20.0+j*60.0, 20.0+i*60.0, 29.0+j*57.0, 33.0+i*57.0);
        line2.setLine(60.0+20.0+j*60.0, 20.0+i*60.0, 57.0+29.0+j*57.0, 33.0+i*57.0);
        line3.setLine(20.0+j*60.0, 60.0+20.0+i*60.0, 29.0+j*57.0, 57.0+33.0+i*57.0);
        line4.setLine(60.0+20.0+j*60.0, 60.0+20.0+i*60.0, 57.0+29.0+j*57.0, 57.0+33.0+i*57.0);

        line1.setStrokeWidth(1);
        line2.setStrokeWidth(1);
        line3.setStrokeWidth(1);
        line4.setStrokeWidth(1);

        line1.setStroke(Color.RED);
        line2.setStroke(Color.RED);
        line3.setStroke(Color.RED);
        line4.setStroke(Color.RED);
	}
	
}
public class App extends Application implements Serializable
{
	public static GridBox grid[][]=new GridBox[9][6];
	public static void intialize(){
		for(int i=0;i<9;++i){
			for(int j=0;j<6;++j){
				grid[i][j]=new GridBox();
				grid[i][j].setgrid(i,j);
				GridBox tmp=grid[i][j];
				if(i==0 || i==8){
					if(j==0 || j==5)grid[i][j].setCrCount(2);
					else grid[i][j].setCrCount(3);
				}
				else grid[i][j].setCrCount(4);
				grid[i][j].rect2.setOnMouseClicked(e->{
					if(!tmp.check()){
						tmp.incrCount();
						tmp.addInGroup();
					}
					else{
						tmp.removeFromGroup();
					}
				});
			}
		}
	}
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage){
		stage.setTitle("CHAIN REACTION");
		Group root=new Group();
		intialize();
		System.out.println("start");
		for(int i=0;i<9;++i){
			for(int j=0;j<6;++j){
				GridBox tmp=grid[i][j];
				root.getChildren().add(grid[i][j]);
				root.getChildren().add(grid[i][j].rect1);
				root.getChildren().add(grid[i][j].rect2);
				root.getChildren().add(grid[i][j].line1);
				root.getChildren().add(grid[i][j].line2);
				root.getChildren().add(grid[i][j].line3);
				root.getChildren().add(grid[i][j].line4);
				root.getChildren().add(grid[i][j].gp);
			}
		}
		Scene scene=new Scene(root,400,590,Color.BLACK);
		stage.setScene(scene);
		stage.show();
	}

}