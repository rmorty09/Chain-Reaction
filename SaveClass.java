package chainreaction;

import java.io.Serializable;

/**
* <h1>Storing the Grid!</h1>
* The class SaveClass stores 
* the reference to the elements of the 
* grid of Class App.
* <p>
*
* @author  shailesh
* @version 1.0
* @since   2017-08-11
*/
public class SaveClass implements Serializable{
    /**
     * Stores the colorId of App Object.
     */
    int clrId;
    
    /**
     * Stores the references to the elements of 
     * field grid of Class App.
     */
    GridBox box[][];
    public SaveClass(GridBox[][] bx,int row,int col,int clrId){
		this.clrId=clrId;
		box=new GridBox[row][col];
		for(int i=0;i<row;++i){
			for(int j=0;j<col;++j){
				box[i][j]=bx[i][j];
			}
		}
	}
}
