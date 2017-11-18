package chainreaction;

import javafx.scene.Group;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.animation.*;
import javafx.util.Duration;
import java.io.*;

/**
* <h1>Representing Cells!</h1>
* The class GridBox defines the Fields 
* and Methods required for Individual cells.
* <p>
*
* @author  shailesh
* @version 1.0
* @since   2017-08-11
*/
public class GridBox implements Serializable{
        /**
         * Determines the radius of the balls.
         */
        public int radius;
        
        
        /**
         * Determines the index of the color field of
         * class App allotted to the cell .
         * Determines which player is allowed to click
         * in this cell.
         */
        public int Id=-1;
        
        
        /**
         * Determines the colour of the balls in the cell.
         */
        transient Color color=null;
        
        
        /**
         * Determines the displacement from the centre of the
         * balls in the cell.
         */
	public double setCord[][]={{0,0},{13,-5},{13,5},{0,5}};
        
        
        /**
         * Shows the grid graphically.
         */
	transient Rectangle rect1;
        
        
        /**
         * Determines the number of balls in the cell.
         */
	public int count=-1;
        
        
        /**
         * Determines the critical Mass of the cell.
         */
	public int criticalCount;
        
        
        /**
         * Stores the reference to balls in the grid.
         */
	transient Sphere arr[]=new Sphere[4];
        
        
        /**
         * Group Object in which all the balls are added 
         * to set Rotation as a whole.
         */
	transient Group gp=new Group();
        
        /**
         * Adds rotation to gp.
         */
        transient RotateTransition tr;
        
        
        /**
         * Changes the colour of the balls in the cell
         * i.e changes the color field of the cell.
         * @param box reference to the cell whose color field
         * is to be changed.
         */
	public void changeColor(GridBox box){
		color=box.color;
		for(int i=0;i<=count;++i){
			PhongMaterial material=new PhongMaterial();
			material.setDiffuseColor(color);
			arr[i].setMaterial(material);
		}
	}
        
        
        /**
         * Creates a new ball and sets it reference through arr[count].
         */
	public void  setSphere(){
		Sphere tmp=arr[count];
		tmp.setRadius(radius);
                if(radius==9){
                    tmp.setTranslateX(this.rect1.getX()+10.0+setCord[count][0]);
                    tmp.setTranslateY(this.rect1.getY()+20.0+setCord[count][1]);
                }
                else{
                    tmp.setTranslateX(this.rect1.getX()+20.0+setCord[count][0]);
                    tmp.setTranslateY(this.rect1.getY()+30.0+setCord[count][1]);
                }
                PhongMaterial material=new PhongMaterial();
		material.setDiffuseColor(color);
		arr[count].setMaterial(material);
	}
        
        
        /**
         * Returns the reference to ball referenced by arr[i].
         * @param i Index of the arr from where the reference of the desired 
         * ball is to be taken.
         * @return returns the reference to the ball referenced by arr[i].
         */
	public Sphere getSphere(int i){ return arr[i];}
        
        
        /**
         * Method to get the count of the balls in the cell.
         * @return count of balls.
         */
	public int getCount(){return count;}

        
        /**
         * Sets the critical mass of a cell.
         * @param c value to be set as critical mass.
         */
	public void setCrCount(int c){criticalCount=c;}
        
        
        /**
         * Increments the count of balls.
         */
	public void incrCount(){ ++count; }
        
        
        /**
         * Adds a new ball and sets the rotation of the group
         * of balls as a whole.
         * @return returns reference to the ball added and the cell.
         */
	public Object[] addInGroup(){
            arr[count]=new Sphere();
            setSphere();
            gp.getChildren().add(arr[count]);
            gp.getChildren().remove(tr);
            tr=new RotateTransition();
            tr.setNode(gp);
            tr.setDuration(Duration.millis(1000));
            tr.setByAngle(360);
            tr.setCycleCount(Timeline.INDEFINITE); 
            tr.setAutoReverse(false); 
            tr.play();
            Object objArr[]=new Object[2];
            objArr[0]=this;
            objArr[1]=arr[count];
            return objArr;
	}
        
        
        /**
         * Removes a ball from the cell.
         * @return returns the reference to the removed ball.
         */
	public Sphere removeFromGroup(){
		Sphere tmp=arr[count];
		gp.getChildren().remove(arr[count]);
		--count;
		return tmp;
	}
        
        
        /**
         * Checks if the count of balls is less than critical mass.
         * @return true if count is less than critical mass.
         * false otherwise.
         */
	public boolean check(){
		return count<(criticalCount-1);
	}
        
        
        /**
         * Creates the cell graphically by determining the dimensions
         * orientation, and colour of the rectangle and the radius of
         * the ball.
         * @param i row index of the cell.
         * @param j column index of the cell.
         */
	public void setCell(int i,int j){
            if(App.row==9){
                rect1=new Rectangle(20+j*60,50+i*60,60,60);
                radius=10;
            }
            else {
                rect1=new Rectangle(20+j*35,50+i*35,35,35);
                radius=9;
            }
            rect1.setTranslateZ(100.0);
            rect1.setFill(Color.TRANSPARENT);
            rect1.setStrokeWidth(2);
            rect1.setStroke(Color.SLATEBLUE);
	}
        
}