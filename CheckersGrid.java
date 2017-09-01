/* 05/01/2016 maybe it's doNE OMG
 */

public class CheckersGrid {

	// Member variables
	protected int[][] grid = new int[8][8];	// The grid/board 
	private static final int EMPTY = 0;		// Empty space
	private static final int PIECE_1 = 1;	// Player 1 normal piece
	private static final int PIECE_2 = 2;	// Player 2 normal piece
	private static final int KING_1 = 3;	// Player 1 king piece
	private static final int KING_2 = 4;	// Player 2 king piece

	// Constructor
	CheckersGrid(){

		// Clears the board
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				grid[i][j]=EMPTY;
			}
		}

		// Puts each piece into starting position
		for(int i=0; i<12; i++){

			// Puts first 4 pieces in left most column (of respective starting positions)
			if (i<4){
				grid[(2*i)+1][0]=PIECE_1;
				grid[2*i][5]=PIECE_2;
			}

			// Puts next 4 pieces in middle column (of respective starting positions)
			if (i>3 && i<8){
				grid[2*(i%4)][1]=PIECE_1;
				grid[2*(i%4)+1][6]=PIECE_2;
			}

			// Puts last 4 pieces in right most column (of respective starting positions)
			if (i>7 && i<12){ 
				grid[2*(i%4)+1][2]=PIECE_1;
				grid[2*(i%4)][7]=PIECE_2;
			}
		}
	}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	// Board functions  	

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// Initialize board
	void init(){

		// Clears the board
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				grid[i][j]=EMPTY;
			}
		}

		// Puts each piece into starting position
		for(int i=0; i<12; i++){

			// Puts first 4 pieces in left most column (of respective starting positions)
			if (i<4){
				grid[(2*i)+1][0]=PIECE_1;
				grid[2*i][5]=PIECE_2;
			}

			// Puts next 4 pieces in middle column (of respective starting positions)
			if (i>3 && i<8){
				grid[2*(i%4)][1]=PIECE_1;
				grid[2*(i%4)+1][6]=PIECE_2;
			}

			// Puts last 4 pieces in right most column (of respective starting positions)
			if (i>7 && i<12){ 
				grid[2*(i%4)+1][2]=PIECE_1;
				grid[2*(i%4)][7]=PIECE_2;
			}
		}
	}

	// Gets current value of a space on board
	int returnValue(int row,int column){
		return grid[row][column];
	}

	// Assigns a value to a space on board
	void assignValue(int row,int column,int type){
		grid[row][column]=type;
	}

	// Prints out the board useful for finding stupid shit
	void printBoard(){
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				System.out.print(grid[i][j] + " ");
			}
			System.out.println();
		}
	}

	// Checks if input space is actually on the board
	boolean onBoard(int row,int column){
		if ((row>=0 && row<=7) && (column>=0 && column<=7)){
			return true;
		} else return false;
	}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	// First click logic : Check if piece clicked on has a valid move  	

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~	

	// FOR PLAYER 1: Check if first click has valid move
	boolean checkP1Click(int clickRow, int clickColumn, boolean king){

		// Checks if it can move up/down or capture up/down
		if(onBoard(clickRow+2,clickColumn+2) && onBoard(clickRow-2,clickColumn+2)){

			// Checks if it can move up/down
			if(grid[clickRow+1][clickColumn+1]==0 || grid[clickRow-1][clickColumn+1]==0){
				return true;
			} 

			// Checks if it can capture up/down
			if ((grid[clickRow+2][clickColumn+2]==0 && (grid[clickRow+1][clickColumn+1]%2==0 && grid[clickRow+1][clickColumn+1]!=0)) || (grid[clickRow-2][clickColumn+2]==0 && (grid[clickRow-1][clickColumn+1]%2==0 && grid[clickRow-1][clickColumn+1]!=0))) {
				return true;
			} else return false;
		}

		// Checks if it can move up/down or capture up or down
		if(onBoard(clickRow+1,clickColumn+1) && onBoard(clickRow-1,clickColumn+1)){

			// Checks if it can move up/down
			if(grid[clickRow+1][clickColumn+1]==0 || grid[clickRow-1][clickColumn+1]==0){
				return true;
			} 

			// If on 2nd to bottom row, check if it can move up/down
			if(clickRow==6){
				if(grid[clickRow+1][clickColumn+1]==0 || grid[clickRow-1][clickColumn+1]==0){
					return true;
				} 

				// Check if it can capture up
				if ((grid[clickRow-2][clickColumn+2]==0)  && grid[clickRow-1][clickColumn+1]%2==0 && grid[clickRow-1][clickColumn+1]!=0) {
					return true;
				} else return false;
			} 

			// If on 2nd to top row, check if it can move up/down
			if(clickRow==1){
				if(grid[clickRow+1][clickColumn+1]==0 || grid[clickRow-1][clickColumn+1]==0){
					return true;
				} 

				// Check if it can capture down
				if ((grid[clickRow+2][clickColumn+2]==0)  && grid[clickRow+1][clickColumn+1]%2==0 && grid[clickRow+1][clickColumn+1]!=0) {
					return true;
				} else return false;

			} else return false;
		}

		// Case if it can move/capture only up
		if(clickRow==7){

			// Check if it can move up
			if(grid[clickRow-1][clickColumn+1]==0){
				return true;
			} 

			// Check if it can capture up
			if ((grid[clickRow-2][clickColumn+2]==0)  && grid[clickRow-1][clickColumn+1]%2==0 && grid[clickRow-1][clickColumn+1]!=0) {
				return true;
			} else return false;
		}

		// Case if it can move/capture only down
		if(clickRow==0){

			// Failsafe to prevent program from checking off the board
			if(clickColumn == 7){
				return false;
			}
			
			// Check if it can move down
			if(grid[clickRow+1][clickColumn+1]==0){
				return true;
			} 

			// Check if it can capture down
			if ((grid[clickRow+2][clickColumn+2]==0)  && grid[clickRow+1][clickColumn+1]%2==0 && grid[clickRow+1][clickColumn+1]!=0) {
				return true;
			} else return false;
		}
		else return false;
	}

	// FOR PLAYER 2: Check if first click has valid move
	boolean checkP2Click(int clickRow, int clickColumn, boolean king){

		// Checks if it can move up/down or capture up/down
		if(onBoard(clickRow+2,clickColumn-2) && onBoard(clickRow-2,clickColumn-2)){

			// Checks if it can move up/down
			if(grid[clickRow+1][clickColumn-1]==0 || grid[clickRow-1][clickColumn-1]==0){
				return true;
			} 

			// Checks if it can capture up/down
			if ((grid[clickRow+2][clickColumn-2]==0 && grid[clickRow+1][clickColumn-1]%2==1) || ( grid[clickRow-2][clickColumn-2]==0 && grid[clickRow-1][clickColumn-1]%2==1)) {
				return true;
			} else return false;
		}

		// Checks if it can move up/down or capture up or down
		if(onBoard(clickRow+1,clickColumn-1) && onBoard(clickRow-1,clickColumn-1)){

			// Checks if it can move up/down
			if(grid[clickRow+1][clickColumn-1]==0 || grid[clickRow-1][clickColumn-1]==0){
				return true;
			} 

			// If on 2nd to bottom row, check if it can move up/down
			if(clickRow==6){
				if(grid[clickRow+1][clickColumn-1]==0 || grid[clickRow-1][clickColumn-1]==0){
					return true;
				} 

				// Check if it can capture up
				if ((grid[clickRow-2][clickColumn-2]==0)  && (grid[clickRow-1][clickColumn-1]%2==1)) {
					return true;
				} else return false;
			}

			// If on 2nd to top row, check if it can move up/down
			if(clickRow==1){
				if(grid[clickRow+1][clickColumn-1]==0 || grid[clickRow-1][clickColumn-1]==0){
					return true;
				} 

				// Check if it can capture down
				if ((grid[clickRow+2][clickColumn-2]==0)  && (grid[clickRow+1][clickColumn-1]%2==1)) {
					return true;
				} else return false;

			} else return false;
		}

		// Case if it can move/capture only up
		if(clickRow==7){
			
			// Failsafe to prevent program from checking off the board
			if(clickColumn == 0){
				return false;
			}
			// Check if it can move up
			if(grid[clickRow-1][clickColumn-1]==0){
				return true;
			} 

			// Check if it can capture up
			if ((grid[clickRow-2][clickColumn-2]==0)  && (grid[clickRow-1][clickColumn-1]%2==1)) {
				return true;
			} else return false;
		}

		// Case if it can move/capture only down
		if(clickRow==0){

			// Check if it can move down
			if(grid[clickRow+1][clickColumn-1]==0){
				return true;
			} 

			// Check if it can capture down
			if ((grid[clickRow+2][clickColumn-2]==0)  && (grid[clickRow+1][clickColumn-1]%2==1)) {
				return true;
			} else return false;
		}

		else return false;
	}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	// Second click logic: Normal move only  	

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~	

	// FOR PLAYER 1: Check valid move
	boolean checkP1Move(int clickRow, int clickColumn, int row,int column){

		// Checks if it can move up/down
		if(onBoard(clickRow+1,clickColumn+1) && onBoard(clickRow-1,clickColumn+1) && onBoard(row,column)){
			if((clickRow+1==row || clickRow-1==row) && clickColumn+1==column && grid[row][column]==0){
				return true;
			} else return false;
		}

		// Case if it can move only up
		if(clickRow==7 && clickColumn<7){
			if(clickRow-1==row && clickColumn+1==column && grid[row][column]==0){
				return true;
			} else return false;
		}

		// Case if it can move only down
		if(clickRow==0 && clickColumn<7){
			if(clickRow+1==row && clickColumn+1==column && grid[row][column]==0){
				return true;
			} else return false;
		}
		else return false;
	}

	// FOR PLAYER 2: Check valid move
	boolean checkP2Move(int clickRow, int clickColumn, int row,int column){

		// Checks if it can move up/down 
		if(onBoard(clickRow+1,clickColumn-1) && onBoard(clickRow-1,clickColumn-1) && onBoard(row,column)){
			if((clickRow+1==row || clickRow-1==row) && clickColumn-1==column && grid[row][column]==0){
				return true;
			} else return false;
		}

		// Case if it can move only up
		if(clickRow==7 && clickColumn>0){
			if(clickRow-1==row && clickColumn-1==column && grid[row][column]==0){
				return true;
			} else return false;
		}

		// Case if it can move only down
		if(clickRow==0 && clickColumn>0){
			if(clickRow+1==row && clickColumn-1==column && grid[row][column]==0){
				return true;
			} else return false;
		}
		else return false;
	}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	// Second click logic: Capturing only  	

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~		

	// FOR PLAYER 1: Checks if can capture
	boolean checkP1Capture(int clickRow, int clickColumn, int row,int column){

		// Checks if it can move up/down or capture up/down
		if(onBoard(clickRow+2,clickColumn+2) && onBoard(clickRow-2,clickColumn+2) && onBoard(row+1,column-1) && onBoard(row-1,column-1)){
			if ((clickRow+2==row || clickRow-2==row) && clickColumn+2==column && ((grid[clickRow+1][clickColumn+1]%2==0 || grid[clickRow+1][clickColumn+1]!=0) || (grid[clickRow-1][clickColumn+1]%2==0 && grid[clickRow-1][clickColumn+1]!=0))) {
				return true;
			} else return false;
		}
		if(clickColumn<6) {
			// Case if it can move/capture only up
			if(clickRow==7){
				if (clickRow-2==row && clickColumn+2==column &&  grid[row+1][column-1]%2==0 && grid[row+1][column-1]!=0) {
					return true;
				} else return false;
			}

			// Case if it can move up/down and capture only up
			if(clickRow==6){
				if (clickRow-2==row && clickColumn+2==column && grid[row+1][column-1]%2==0 && grid[row+1][column-1]!=0) {
					return true;
				} else return false;
			}

			// Case if it can capture down next to edge
			if(clickRow==5 && row==7){
				if (clickRow+2==row && clickColumn+2==column && grid[row-1][column-1]%2==0 && grid[row-1][column-1]!=0) {
					return true;
				} else return false;
			}

			// Case if it can move/capture only down
			if(clickRow==0){
				if (clickRow+2==row && clickColumn+2==column &&  grid[row-1][column-1]%2==0 && grid[row-1][column-1]!=0) {
					return true;
				} else return false;
			}

			// Case if it can move up/down and capture only down
			if(clickRow==1){
				if (clickRow+2==row && clickColumn+2==column &&  grid[row-1][column-1]%2==0 && grid[row-1][column-1]!=0) {
					return true;
				} else return false;
			}

			// Case if it can move and capture up/down
			if(clickRow==2 && row==0){
				if (clickRow-2==row && clickColumn+2==column && grid[row+1][column-1]%2==0 && grid[row+1][column-1]!=0) {
					return true;
				} else return false;
			} else return false;
		} else return false;
	}


	// FOR PLAYER 2: Checks if can capture
	boolean checkP2Capture(int clickRow, int clickColumn, int row,int column){

		// Checks if it can move up/down or capture up/down
		if(onBoard(clickRow+2,clickColumn-2) && onBoard(clickRow-2,clickColumn-2) && onBoard(row+1,column+1) && onBoard(row-1,column+1)){
			if ((clickRow+2==row || clickRow-2==row) && clickColumn-2==column && (grid[row+1][column+1]%2==1 || grid[row-1][column+1]%2==1)) {
				return true;
			} else return false;
		}

		if(clickColumn>1){
			// Case if it can move/capture only up
			if(clickRow==7){
				if (clickRow-2==row && clickColumn-2==column &&  grid[row+1][column+1]%2==1) {
					return true;
				} else return false;
			}

			// Case if it can move up/down and capture only up
			if(clickRow==6){
				if (clickRow-2==row && clickColumn-2==column && grid[row+1][column+1]%2==1) {
					return true;
				} else return false;
			}

			// Case if it can move and capture up/down
			if(clickRow==5 && row==7){
				if (clickRow+2==row && clickColumn-2==column && grid[row-1][column+1]%2==1) {
					return true;
				} else return false;
			}

			// Case if it can move/capture only down
			if(clickRow==0){
				if (clickRow+2==row && clickColumn-2==column &&  grid[row-1][column+1]%2==1) {
					return true;
				} else return false;
			}

			// Case if it can move up/down and capture only down
			if(clickRow==1){
				if (clickRow+2==row && clickColumn-2==column &&  grid[row-1][column+1]%2==1) {
					return true;
				} else return false;
			}

			// Case if it can move and capture up/down
			if(clickRow==2 && row==0){
				if (clickRow-2==row && clickColumn-2==column && grid[row+1][column+1]%2==1) {
					return true;
				} else return false;
			} else return false;
		} else return false;
	}

	// FOR PLAYER 1: Returns captured piece row value
	int returnP1CaptureX(int clickRow, int clickColumn, int row, int column){
		if(onBoard(clickRow+2,clickColumn+2) && onBoard(clickRow-2,clickColumn+2) && onBoard(row+1,column-1) && onBoard(row-1,column-1)){
			if ((clickRow+2==row) && clickColumn+2==column && (grid[row-1][column-1]%2==0 && grid[row-1][column-1]!=0)) {
				return row-1;
			}
			if ((clickRow-2==row) && clickColumn+2==column && (grid[row+1][column-1]%2==0 && grid[row+1][column-1]!=0)) {
				return row+1;
			} else return row;
		} 

		if(clickColumn<6){
			if(clickRow==7){
				if (clickRow-2==row && clickColumn+2==column &&  grid[row+1][column-1]%2==0 && grid[row+1][column-1]!=0) {
					return row+1;
				} else return row;
			}

			// Case if it can move up/down and capture only up
			if(clickRow==6){
				if (clickRow-2==row && clickColumn+2==column && grid[row+1][column-1]%2==0 && grid[row+1][column-1]!=0) {
					return row+1;
				} else return row;
			}

			// Case if it can move and capture up/down
			if(clickRow==5 && row==7){
				if (clickRow+2==row && clickColumn+2==column && grid[row-1][column-1]%2==0 && grid[row-1][column-1]!=0) {
					return row-1;
				} else return row;
			}		

			// Case if it can move/capture only down
			if(clickRow==0){
				if (clickRow+2==row && clickColumn+2==column &&  grid[row-1][column-1]%2==0 && grid[row-1][column-1]!=0) {
					return row-1;
				} else return row-1;
			}

			// Case if it can move up/down and capture only down
			if(clickRow==1){
				if (clickRow+2==row && clickColumn+2==column &&  grid[row-1][column-1]%2==0 && grid[row-1][column-1]!=0) {
					return row-1;
				} else return row;
			}

			// Case if it can move and capture up/down
			if(clickRow==2 && row==0){
				if (clickRow-2==row && clickColumn+2==column && grid[row+1][column-1]%2==0 && grid[row+1][column-1]!=0) {
					return row+1;
				} else return row;
			} else return row;
		} else return row;
	}

	// FOR PLAYER 1: Returns captured piece column value
	int returnP1CaptureY(int clickRow, int clickColumn, int row, int column){
		if(onBoard(clickRow+2,clickColumn+2) && onBoard(clickRow-2,clickColumn+2) && onBoard(row+1,column-1) && onBoard(row-1,column-1)){
			if ((clickRow+2==row) && clickColumn+2==column && (grid[row-1][column-1]%2==0 && grid[row-1][column-1]!=0)) {
				return column-1;
			}
			if ((clickRow-2==row) && clickColumn+2==column && (grid[row+1][column-1]%2==0 && grid[row+1][column-1]!=0)) {
				return column-1;
			} else return column;
		} 
		if(clickColumn<6){
			if(clickRow==7){
				if (clickRow-2==row && clickColumn+2==column && grid[row+1][column-1]%2==0 && grid[row+1][column-1]!=0) {
					return column-1;
				} else return column;
			}

			// Case if it can move up/down and capture only up
			if(clickRow==6){
				if (clickRow-2==row && clickColumn+2==column && grid[row+1][column-1]%2==0 && grid[row+1][column-1]!=0) {
					return column-1;
				} else return column;
			}

			// Case if it can move and capture up/down
			if(clickRow==5 && row==7){
				if (clickRow+2==row && clickColumn+2==column && grid[row-1][column-1]%2==0 && grid[row-1][column-1]!=0) {
					return column-1;
				} else return column;
			}

			// Case if it can move/capture only down
			if(clickRow==0){
				if (clickRow+2==row && clickColumn+2==column &&  grid[row-1][column-1]%2==0 && grid[row-1][column-1]!=0) {
					return column-1;
				} else return column;
			}

			// Case if it can move up/down and capture only down
			if(clickRow==1){
				if (clickRow+2==row && clickColumn+2==column &&  grid[row-1][column-1]%2==0 && grid[row-1][column-1]!=0) {
					return column-1;
				} else return column;
			}

			// Case if it can move and capture up/down
			if(clickRow==2 && row==0){
				if (clickRow-2==row && clickColumn+2==column && grid[row+1][column-1]%2==0 && grid[row+1][column-1]!=0) {
					return column-1;
				} else return column;
			} else return column;
		} else return column;
	}

	// FOR PLAYER 2: Returns captured piece row value
	int returnP2CaptureX(int clickRow, int clickColumn, int row, int column){
		if(onBoard(clickRow+2,clickColumn-2) && onBoard(clickRow-2,clickColumn-2) && onBoard(row+1,column+1) && onBoard(row-1,column+1)){
			if ((clickRow+2==row) && clickColumn-2==column && (grid[row-1][column+1]%2==1)) {
				return row-1;
			}
			if ((clickRow-2==row) && clickColumn-2==column && (grid[row+1][column+1]%2==1)) {
				return row+1;
			} else return row;
		} 
		if(clickColumn>1){
			if(clickRow==7){
				if (clickRow-2==row && clickColumn-2==column &&  grid[row+1][column+1]%2==1) {
					return row+1;
				} else return row;
			}

			// Case if it can move up/down and capture only up
			if(clickRow==6){
				if (clickRow-2==row && clickColumn-2==column && grid[row+1][column+1]%2==1) {
					return row+1;
				} else return row;
			}

			// Case if it can move and capture up/down
			if(clickRow==5 && row==7){
				if (clickRow+2==row && clickColumn-2==column && grid[row-1][column+1]%2==1) {
					return row-1;
				} else return row;
			}

			// Case if it can move/capture only down
			if(clickRow==0){
				if (clickRow+2==row && clickColumn-2==column &&  grid[row-1][column+1]%2==1) {
					return row-1;
				} else return row-1;
			}

			// Case if it can move up/down and capture only down
			if(clickRow==1){
				if (clickRow+2==row && clickColumn-2==column &&  grid[row-1][column+1]%2==1) {
					return row-1;
				} else return row;
			}

			// Case if it can move and capture up/down
			if(clickRow==2 && row==0){
				if (clickRow-2==row && clickColumn-2==column && grid[row+1][column+1]%2==1) {
					return row+1;
				} else return row;
			} else return row;
		} else return row;
	}

	// FOR PLAYER 2: Returns captured piece column value
	int returnP2CaptureY(int clickRow, int clickColumn, int row, int column){
		if(onBoard(clickRow+2,clickColumn-2) && onBoard(clickRow-2,clickColumn-2) && onBoard(row+1,column+1) && onBoard(row-1,column+1)){
			if ((clickRow+2==row) && clickColumn-2==column && (grid[row-1][column+1]%2==1)) {
				return column+1;
			}
			if ((clickRow-2==row) && clickColumn-2==column && (grid[row+1][column+1]%2==1)) {
				return column+1;
			} else return column;
		} 
		if(clickColumn>1){
			if(clickRow==7){
				if (clickRow-2==row && clickColumn-2==column &&  grid[row+1][column+1]%2==1) {
					return column+1;
				} else return column;
			}

			// Case if it can move up/down and capture only up
			if(clickRow==6){
				if (clickRow-2==row && clickColumn-2==column && grid[row+1][column+1]%2==1) {
					return column+1;
				} else return column;
			}

			// Case if it can move and capture up/down
			if(clickRow==5 && row==7){
				if (clickRow+2==row && clickColumn-2==column && grid[row-1][column+1]%2==1) {
					return column+1;
				} else return column;
			}

			// Case if it can move/capture only down
			if(clickRow==0){
				if (clickRow+2==row && clickColumn-2==column &&  grid[row-1][column+1]%2==1) {
					return column+1;
				} else return column;
			}

			// Case if it can move up/down and capture only down
			if(clickRow==1){
				if (clickRow+2==row && clickColumn-2==column &&  grid[row-1][column+1]%2==1) {
					return column+1;
				} else return column;
			}

			// Case if it can move and capture up/down
			if(clickRow==2 && row==0){
				if (clickRow-2==row && clickColumn-2==column && grid[row+1][column+1]%2==1) {
					return column+1;
				} else return column;
			} else return column;
		} else return column;
	}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	// Promotion  	

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~	

	// Right now these are set to promote early to make things easy to test
	// PLAYER 1: Promotes the piece if it reaches the end of the board
	boolean checkP1Promotion(int row, int column){
		if(column==7){
			grid[row][column]=3;
			return true;
		} else return false;
	}

	// PLAYER 2: Promotes the piece if it reaches the end of the board
	boolean checkP2Promotion(int row, int column){
		if(column==0){
			grid[row][column]=4;
			return true;
		} else return false;
	}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	// Multiple capture check: Checks if any more captures available  	

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	// FOR PLAYER 1: Check if first click has valid capture
	boolean checkP1CapClick(int clickRow, int clickColumn){

		//System.out.println(clickRow + " "+ clickColumn);
		// Checks if it's still towards the left side of the board
		if(clickColumn<6){

			// Checks if it can capture up/down
			if(onBoard(clickRow+2,clickColumn+2) && onBoard(clickRow-2,clickColumn+2)){

				// Checks if it can capture up/down
				if ((grid[clickRow+2][clickColumn+2]==0 && (grid[clickRow+1][clickColumn+1]%2==0 && grid[clickRow+1][clickColumn+1]!=0)) || (grid[clickRow-2][clickColumn+2]==0 && (grid[clickRow-1][clickColumn+1]%2==0 && grid[clickRow-1][clickColumn+1]!=0))) {
					return true;
				} else return false;
			}

			// Checks if it can capture up or down
			if(onBoard(clickRow+1,clickColumn+1) && onBoard(clickRow-1,clickColumn+1)){

				// If on 2nd to bottom row
				if(clickRow==6){ 

					// Check if it can capture up
					if ((grid[clickRow-2][clickColumn+2]==0)  && grid[clickRow-1][clickColumn+1]%2==0 && grid[clickRow-1][clickColumn+1]!=0) {
						return true;
					} else return false;
				} 

				// If on 2nd to top row
				if(clickRow==1){

					// Check if it can capture down
					if ((grid[clickRow+2][clickColumn+2]==0)  && grid[clickRow+1][clickColumn+1]%2==0 && grid[clickRow+1][clickColumn+1]!=0) {
						return true;
					} else return false;

				} else return false;
			}

			// Case if it can capture only up
			if(clickRow==7){

				// Check if it can capture up
				if ((grid[clickRow-2][clickColumn+2]==0)  && grid[clickRow-1][clickColumn+1]%2==0 && grid[clickRow-1][clickColumn+1]!=0) {
					return true;
				} else return false;
			}

			// Case if it can capture only down
			if(clickRow==0){

				// Check if it can capture down
				if ((grid[clickRow+2][clickColumn+2]==0)  && grid[clickRow+1][clickColumn+1]%2==0 && grid[clickRow+1][clickColumn+1]!=0) {
					return true;
				} else return false;
			} else return false;
		} else return false;
	}

	// FOR PLAYER 2: Check if first click has valid capture
	boolean checkP2CapClick(int clickRow, int clickColumn){

		//Check if it's still towards the right side of the board
		if(clickColumn>1){

			// Checks if it can capture up/down
			if(onBoard(clickRow+2,clickColumn-2) && onBoard(clickRow-2,clickColumn-2)){

				// Checks if it can capture up/down
				if ((grid[clickRow+2][clickColumn-2]==0 && grid[clickRow+1][clickColumn-1]%2==1) || ( grid[clickRow-2][clickColumn-2]==0 && grid[clickRow-1][clickColumn-1]%2==1)) {
					return true;
				} else return false;
			}

			// Checks if it can capture up or down
			if(onBoard(clickRow+1,clickColumn-1) && onBoard(clickRow-1,clickColumn-1)){

				// If on 2nd to bottom row
				if(clickRow==6){ 

					// Check if it can capture up
					if ((grid[clickRow-2][clickColumn-2]==0)  && (grid[clickRow-1][clickColumn-1]%2==1)) {
						return true;
					} else return false;
				}

				// If on 2nd to top row
				if(clickRow==1){

					// Check if it can capture down
					if ((grid[clickRow+2][clickColumn-2]==0)  && (grid[clickRow+1][clickColumn-1]%2==1)) {
						return true;
					} else return false;

				} else return false;
			}

			// Case if it can capture only up
			if(clickRow==7){

				// Check if it can capture up
				if ((grid[clickRow-2][clickColumn-2]==0)  && (grid[clickRow-1][clickColumn-1]%2==1)) {
					return true;
				} else return false;
			}

			// Case if it can capture only down
			if(clickRow==0){

				// Check if it can capture down
				if ((grid[clickRow+2][clickColumn-2]==0)  && (grid[clickRow+1][clickColumn-1]%2==1)) {
					return true;
				} else return false;
			}else return false;
		}
		else return false;
	}
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	// King movement check: Check movement for king 
	// Player shouldn't matter as both type of kings move the same way

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	boolean checkKingMove(int clickRow, int clickColumn, int row, int column){
		
		// Check left side || right side
		if(checkP1Move(clickRow, clickColumn, row, column) || checkP2Move(clickRow, clickColumn, row, column)){
			return true;
		}
		else return false;
	}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	// P1 King click check: check if p1 king can move/capture

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	boolean checkP1KingClick(int clickRow, int clickColumn){
		
		// Check right side
		if(checkP1Click(clickRow, clickColumn, true)){
			return true;
		}
		
		// Now we check left side
		// Checks if it can move up/down or capture up/down
		if(onBoard(clickRow+2,clickColumn-2) && onBoard(clickRow-2,clickColumn-2)){

			// Checks if it can move up/down
			if(grid[clickRow+1][clickColumn-1]==0 || grid[clickRow-1][clickColumn-1]==0){
				return true;
			} 

			// Checks if it can capture up/down
			if ((grid[clickRow+2][clickColumn-2]==0 || grid[clickRow-2][clickColumn-2]==0)  && ((grid[clickRow+1][clickColumn-1]%2==0 && grid[clickRow+1][clickColumn-1]!=0) || (grid[clickRow-1][clickColumn-1]%2==0 && grid[clickRow-1][clickColumn-1]!=0))) {
				return true;
			} else return false;
		}

		// Checks if it can move up/down or capture up or down
		if(onBoard(clickRow+1,clickColumn-1) && onBoard(clickRow-1,clickColumn-1)){

			// Checks if it can move up/down
			if(grid[clickRow+1][clickColumn-1]==0 || grid[clickRow-1][clickColumn-1]==0){
				return true;
			} 

			// If on 2nd to bottom row, check if it can move up/down
			if(clickRow==6){
				if(grid[clickRow+1][clickColumn-1]==0 || grid[clickRow-1][clickColumn-1]==0){
					return true;
				} 

				// Check if it can capture up
				if ((grid[clickRow-2][clickColumn-2]==0)  && (grid[clickRow-1][clickColumn-1]%2==0 && grid[clickRow-1][clickColumn-1]!=0)) {
					return true;
				} else return false;
			}

			// If on 2nd to top row, check if it can move up/down
			if(clickRow==1){
				if(grid[clickRow+1][clickColumn-1]==0 || grid[clickRow-1][clickColumn-1]==0){
					return true;
				} 

				// Check if it can capture down
				if ((grid[clickRow+2][clickColumn-2]==0)  && (grid[clickRow+1][clickColumn-1]%2==0 && grid[clickRow+1][clickColumn-1]!=0)) {
					return true;
				} else return false;

			} else return false;
		}

		// Case if it can move/capture only up
		if(clickRow==7){

			// Check if it can move up
			if(grid[clickRow-1][clickColumn-1]==0){
				return true;
			} 

			// Check if it can capture up
			if ((grid[clickRow-2][clickColumn-2]==0)  && (grid[clickRow-1][clickColumn-1]%2==0 && grid[clickRow-1][clickColumn-1]!=0)) {
				return true;
			} else return false;
		}

		// Case if it can move/capture only down
		if(clickRow==0){

			// Check if it can move down
			if(grid[clickRow+1][clickColumn-1]==0){
				return true;
			} 

			// Check if it can capture down
			if ((grid[clickRow+2][clickColumn-2]==0)  && (grid[clickRow+1][clickColumn-1]%2==0 && grid[clickRow+1][clickColumn-1]!=0)) {
				return true;
			} else return false;
		}

		else return false;
		
	}
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	// P1 King capture check: Check capture for king 

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// Check if p1's king made a valid capture
	boolean checkP1KingCapture(int clickRow, int clickColumn, int row, int column){
		
		// Right side movement is the same as a normal piece
		if(checkP1Capture(clickRow, clickColumn, row, column)){
			return true;
		}
		
		// Now we check left side movement
		// Checks if it can move up/down or capture up/down
		if(onBoard(clickRow+2,clickColumn-2) && onBoard(clickRow-2,clickColumn-2) && onBoard(row+1,column-1) && onBoard(row-1,column-1)){
	          if ((clickRow+2==row || clickRow-2==row) && clickColumn-2==column && ((grid[clickRow+1][clickColumn-1]%2==0 && grid[clickRow+1][clickColumn-1]!=0) || (grid[clickRow-1][clickColumn-1]%2==0 && grid[clickRow-1][clickColumn-1]!=0))) {
	              return true;
	          } else return false;
	    }

		if(clickColumn>1){
		// Case if it can move/capture only up
			if(clickRow==7){
				if (clickRow-2==row && clickColumn-2==column &&  grid[row+1][column+1]%2==0 && grid[row+1][column+1]!=0) {
					return true;
				} else return false;
			}

			// Case if it can move up/down and capture only up
			if(clickRow==6){
				if (clickRow-2==row && clickColumn-2==column && grid[row+1][column+1]%2==0 && grid[row+1][column+1]!=0) {
					return true;
				} else return false;
			}

			// Case if it can move and capture up/down
			if(clickRow==5 && row==7){
				if (clickRow+2==row && clickColumn-2==column && grid[row-1][column+1]%2==0 && grid[row-1][column+1]!=0) {
					return true;
				} else return false;
			}

			// Case if it can move/capture only down
			if(clickRow==0){
				if (clickRow+2==row && clickColumn-2==column &&  grid[row-1][column+1]%2==0 && grid[row-1][column+1]!=0) {
					return true;
				} else return false;
			}

			// Case if it can move up/down and capture only down
			if(clickRow==1){
				if (clickRow+2==row && clickColumn-2==column &&  grid[row-1][column+1]%2==0 && grid[row-1][column+1]!=0) {
					return true;
				} else return false;
			}

			// Case if it can move and capture up/down
			if(clickRow==2 && row==0){
				if (clickRow-2==row && clickColumn-2==column && grid[row+1][column+1]%2==0 && grid[row+1][column+1]!=0) {
					return true;
				} else return false;
			} else return false;
		}
		return false;
	}
	
	// Function that returns x coordinate of p1's king's captured piece
	int returnP1KingCaptureX(int clickRow, int clickColumn, int row, int column){
		//Pass: ClickRow: 2 ClickColumn: 5 row: 0 column: 3
		System.out.println("X -- clickRow:" + clickRow + " clickColumn:" + clickColumn + " row:" + row + " column:" + column);
		// First check right side of king piece
		if(column > clickColumn){
			
			//System.out.println("Check right side");
			if(onBoard(clickRow+2,clickColumn+2) && onBoard(clickRow-2,clickColumn+2) && onBoard(row+1,column-1) && onBoard(row-1,column-1)){
				if ((clickRow+2==row) && clickColumn+2==column && (grid[row-1][column-1]%2==0 && grid[row-1][column-1]!=0)) {
					return row-1;
				}
				if ((clickRow-2==row) && clickColumn+2==column && (grid[row+1][column-1]%2==0 && grid[row+1][column-1]!=0)) {
					return row+1;
				} else return row;
			} 

			if(clickColumn<6){
				if(clickRow==7){
					if (clickRow-2==row && clickColumn+2==column &&  grid[row+1][column-1]%2==0 && grid[row+1][column-1]!=0) {
						return row+1;
					} else return row;
				}

				// Case if it can move up/down and capture only up
				if(clickRow==6){
					if (clickRow-2==row && clickColumn+2==column && grid[row+1][column-1]%2==0 && grid[row+1][column-1]!=0) {
						return row+1;
					} else return row;
				}

				// Case if it can move and capture up/down
				if(clickRow==5 && row==7){
					if (clickRow+2==row && clickColumn+2==column && grid[row-1][column-1]%2==0 && grid[row-1][column-1]!=0) {
						return row-1;
					} else return row;
				}		

				// Case if it can move/capture only down
				if(clickRow==0){
					if (clickRow+2==row && clickColumn+2==column &&  grid[row-1][column-1]%2==0 && grid[row-1][column-1]!=0) {
						return row-1;
					} else return row-1;
				}

				// Case if it can move up/down and capture only down
				if(clickRow==1){
					if (clickRow+2==row && clickColumn+2==column &&  grid[row-1][column-1]%2==0 && grid[row-1][column-1]!=0) {
						return row-1;
					} else return row;
				}

				// Case if it can move and capture up/down
				if(clickRow==2 && row==0){
					if (clickRow-2==row && clickColumn+2==column && grid[row+1][column-1]%2==0 && grid[row+1][column-1]!=0) {
						return row+1;
					} else return row;
				} else return row;
			}
		}
		
		// Now check left side of king piece
		if(column < clickColumn){
			System.out.println("Check left side");
			if(onBoard(clickRow+2,clickColumn-2) && onBoard(clickRow-2,clickColumn-2) && onBoard(row+1,column+1) && onBoard(row-1,column+1)){
				if ((clickRow+2==row) && clickColumn-2==column && (grid[row-1][column+1]%2==0 && grid[row-1][column+1]!=0)) {
					return row-1;
				}
				if ((clickRow-2==row) && clickColumn-2==column && (grid[row+1][column+1]%2==0 && grid[row+1][column+1]!=0)) {
					//System.out.println("Row1");
					return row+1; 
				} else return row;
			}
		
			if(clickColumn>1){
				if(clickRow==7){
					if (clickRow-2==row && clickColumn-2==column &&  (grid[row+1][column+1]%2==0 && grid[row+1][column+1]!=0)) {
						return row+1;
					} else return row;
				}

				// Case if it can move up/down and capture only up
				if(clickRow==6){
					if (clickRow-2==row && clickColumn-2==column && grid[row+1][column+1]%2==0 && grid[row+1][column+1]!=0) {
						return row+1;
					} else return row;
				}

				// Case if it can move and capture up/down
				if(clickRow==5 && row==7){
					if (clickRow+2==row && clickColumn-2==column && grid[row-1][column+1]%2==0 && grid[row-1][column+1]!=0) {
						return row-1;
					} else return row;
				}
				
				// Case if it can move and capture up towards the edge
				if(clickRow==2 && row==0){
					if(clickRow-2 == row && clickColumn-2==column && grid[row+1][column+1]%2==0 && grid[row+1][column+1]!=0){
						return row+1;
					} else return row;
				}

				// Case if it can move/capture only down
				if(clickRow==0){
					if (clickRow+2==row && clickColumn-2==column &&  grid[row-1][column+1]%2==0 && grid[row-1][column+1]!=0) {
						return row-1;
					} else return row-1;
				}
				
				// Case if it can move up/down and capture only down
				if(clickRow==1){
					if (clickRow+2==row && clickColumn-2==column &&  grid[row-1][column+1]%2==0 && grid[row-1][column+1]!=0) {
						return row-1;
					} else return row;
				}else return row;
			}
		}
		return row;
	}		
	
	// Function that returns y coordinate of p1's king's captured piece
	int returnP1KingCaptureY(int clickRow, int clickColumn, int row, int column){
		
		//System.out.println("Y -- clickRow:" + clickRow + " clickColumn:" + clickColumn + " row:" + row + " column:" + column);
		// First check right side of king piece
		if(column > clickColumn){
			//System.out.println("Check right side");
			if(onBoard(clickRow+2,clickColumn+2) && onBoard(clickRow-2,clickColumn+2) && onBoard(row+1,column-1) && onBoard(row-1,column-1)){
				if ((clickRow+2==row) && clickColumn+2==column && (grid[row-1][column-1]%2==0 && grid[row-1][column-1]!=0)) {
					return column-1;
				}
				if ((clickRow-2==row) && clickColumn+2==column && (grid[row+1][column-1]%2==0 && grid[row+1][column-1]!=0)) {
					return column-1;
				} else return column;
			} 
		
			if(clickColumn<6){
				if(clickRow==7){
					if (clickRow-2==row && clickColumn+2==column && grid[row+1][column-1]%2==0 && grid[row+1][column-1]!=0) {
						return column-1;
					} else return column;
				}

				// Case if it can move up/down and capture only up
				if(clickRow==6){
					if (clickRow-2==row && clickColumn+2==column && grid[row+1][column-1]%2==0 && grid[row+1][column-1]!=0) {
						return column-1;
					} else return column;
				}

				// Case if it can move and capture up/down
				if(clickRow==5 && row==7){
					if (clickRow+2==row && clickColumn+2==column && grid[row-1][column-1]%2==0 && grid[row-1][column-1]!=0) {
						return column-1;
					} else return column;
				}

				// Case if it can move/capture only down
				if(clickRow==0){
					if (clickRow+2==row && clickColumn+2==column &&  grid[row-1][column-1]%2==0 && grid[row-1][column-1]!=0) {
						return column-1;
					} else return column;
				}

				// Case if it can move up/down and capture only down
				if(clickRow==1){
					if (clickRow+2==row && clickColumn+2==column &&  grid[row-1][column-1]%2==0 && grid[row-1][column-1]!=0) {
						return column-1;
					} else return column;
				}

				// Case if it can move and capture up/down
				if(clickRow==2 && row==0){
					if (clickRow-2==row && clickColumn+2==column && grid[row+1][column-1]%2==0 && grid[row+1][column-1]!=0) {
						return column-1;
					} else return column;
				} else return column;
			}
		}
			
		// Now check left side of king piece
		if(column < clickColumn){
			//System.out.println("Check left side");
			if(onBoard(clickRow+2,clickColumn-2) && onBoard(clickRow-2,clickColumn-2) && onBoard(row+1,column+1) && onBoard(row-1,column+1)){
				if ((clickRow+2==row) && clickColumn-2==column && (grid[row-1][column+1]%2==0 && grid[row-1][column+1]!=0)) {
					return column+1;
				}
				if ((clickRow-2==row) && clickColumn-2==column && (grid[row+1][column+1]%2==0 && grid[row+1][column+1]!= 0)) {
					return column+1;
				} else return column;
			} 
		
			if(clickColumn>1){
				if(clickRow==7){
					if (clickRow-2==row && clickColumn-2==column &&  grid[row+1][column+1]%2==0 && grid[row+1][column+1]!=0) {
						return column+1;
					} else return column;
				}

				// Case if it can move up/down and capture only up
				if(clickRow==6){
					if (clickRow-2==row && clickColumn-2==column && grid[row+1][column+1]%2==0 && grid[row+1][column+1]!=0) {
						return column+1;
					} else return column;
				}

				// Case if it can move and capture up/down
				if(clickRow==5 && row==7){
					if (clickRow+2==row && clickColumn-2==column && grid[row-1][column+1]%2==0 && grid[row-1][column+1]!=0) {
						return column+1;
					} else return column;
				}

				// Case if it can move/capture only down
				if(clickRow==0){
					if (clickRow+2==row && clickColumn-2==column &&  grid[row-1][column+1]%2==0 && grid[row-1][column+1]!=0) {
						return column+1;
					} else return column;
				}

				// Case if it can move up/down and capture only down
				if(clickRow==1){
					if (clickRow+2==row && clickColumn-2==column &&  grid[row-1][column+1]%2==0 && grid[row-1][column+1]!=0) {
						return column+1;
					} else return column;
				}

				// Case if it can move and capture up/down
				if(clickRow==2 && row==0){
					if (clickRow-2==row && clickColumn-2==column && grid[row+1][column+1]%2==0 && grid[row+1][column+1]!=0) {
						return column+1;
					} else return column;
				} else return column;
			}
		}
		return column;
	}			

	// Function that checks if p1's king can capture again
	boolean checkP1KingCapClick(int clickRow, int clickColumn){
		
		// First check for right side since that'll be the same
		if(checkP1CapClick(clickRow, clickColumn)){
			return true;
		}
		
		// Now check left side
		//Check if it's still towards the right side of the board
		if(clickColumn>1){

			// Checks if it can capture up/down
			if(onBoard(clickRow+2,clickColumn-2) && onBoard(clickRow-2,clickColumn-2)){

				// Checks if it can capture up/down
				if ((grid[clickRow+2][clickColumn-2]==0 || grid[clickRow-2][clickColumn-2]==0) && ((grid[clickRow+1][clickColumn-1]%2==0 && grid[clickRow+1][clickColumn-1]!=0)  || (grid[clickRow-1][clickColumn-1]%2==0 && grid[clickRow-1][clickColumn-1]!=0))) {
					return true;
				} else return false;
			}

			// Checks if it can capture up or down
			if(onBoard(clickRow+1,clickColumn-1) && onBoard(clickRow-1,clickColumn-1)){

				// If on 2nd to bottom row
				if(clickRow==6){ 

					// Check if it can capture up
					if ((grid[clickRow-2][clickColumn-2]==0)  && (grid[clickRow-1][clickColumn-1]%2==0 && grid[clickRow-1][clickColumn-1]!=0)) {
						return true;
					} else return false;
				}

				// If on 2nd to top row
				if(clickRow==1){

					// Check if it can capture down
					if ((grid[clickRow+2][clickColumn-2]==0)  && (grid[clickRow+1][clickColumn-1]%2==0 && grid[clickRow+1][clickColumn-1]!=0)) {
						return true;
					} else return false;

				} else return false;
			}

			// Case if it can capture only up
			if(clickRow==7){

				// Check if it can capture up
				if ((grid[clickRow-2][clickColumn-2]==0)  && (grid[clickRow-1][clickColumn-1]%2==0 && grid[clickRow-1][clickColumn-1]!=0)) {
					return true;
				} else return false;
			}

			// Case if it can capture only down
			if(clickRow==0){

				// Check if it can capture down
				if ((grid[clickRow+2][clickColumn-2]==0)  && (grid[clickRow+1][clickColumn-1]%2==0 && grid[clickRow+1][clickColumn-1]!=0)) {
					return true;
				} else return false;
			}else return false;
		}
		return false;
	}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	// P2 King click check: Check if P2 king can move/capture 

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	boolean checkP2KingClick(int clickRow, int clickColumn){
		
		// Check left side
		if(checkP2Click(clickRow, clickColumn, true)){
			return true;
		}

		// Now check right side
		// Checks if it can move up/down or capture up/down
		if(onBoard(clickRow+2,clickColumn+2) && onBoard(clickRow-2,clickColumn+2)){

			// Checks if it can move up/down
			if(grid[clickRow+1][clickColumn+1]==0 || grid[clickRow-1][clickColumn+1]==0){
				return true;
			} 

			// Checks if it can capture up/down
			if ((grid[clickRow+2][clickColumn+2]==0 || grid[clickRow-2][clickColumn+2]==0)  && (grid[clickRow+1][clickColumn+1]%2==1  || grid[clickRow-1][clickColumn+1]%2==1)) {
				return true;
			} else return false;
		}

		// Checks if it can move up/down or capture up or down
		if(onBoard(clickRow+1,clickColumn+1) && onBoard(clickRow-1,clickColumn+1)){

			// Checks if it can move up/down
			if(grid[clickRow+1][clickColumn+1]==0 || grid[clickRow-1][clickColumn+1]==0){
				return true;
			} 

			// If on 2nd to bottom row, check if it can move up/down
			if(clickRow==6){
				if(grid[clickRow+1][clickColumn+1]==0 || grid[clickRow-1][clickColumn+1]==0){
					return true;
				} 

				// Check if it can capture up
				if ((grid[clickRow-2][clickColumn+2]==0)  && grid[clickRow-1][clickColumn+1]%2==1) {
					return true;
				} else return false;
			} 

			// If on 2nd to top row, check if it can move up/down
			if(clickRow==1){
				if(grid[clickRow+1][clickColumn+1]==0 || grid[clickRow-1][clickColumn+1]==0){
					return true;
				} 

				// Check if it can capture down
				if ((grid[clickRow+2][clickColumn+2]==0)  && grid[clickRow+1][clickColumn+1]%2==1) {
					return true;
				} else return false;

			} else return false;
		}

		// Case if it can move/capture only up
		if(clickRow==7){

			// Check if it can move up
			if(grid[clickRow-1][clickColumn+1]==0){
				return true;
			} 

			// Check if it can capture up
			if ((grid[clickRow-2][clickColumn+2]==0)  && grid[clickRow-1][clickColumn+1]%2==1) {
				return true;
			} else return false;
		}

		// Case if it can move/capture only down
		if(clickRow==0){

			// Check if it can move down
			if(grid[clickRow+1][clickColumn+1]==0){
				return true;
			} 

			// Check if it can capture down
			if ((grid[clickRow+2][clickColumn+2]==0)  && grid[clickRow+1][clickColumn+1]%2==1) {
				return true;
			} else return false;
		}
		else return false;		

	}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	// P2 King capture check: Check capture for king 

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~	
	
	// Check if p2's king made a valid capture
	boolean checkP2KingCapture(int clickRow, int clickColumn, int row, int column){
		
		// Left side capture is the same as a normal piece
		if(checkP2Capture(clickRow, clickColumn, row, column)){
			return true;
		}
		
		// Now check right side
		// Checks if it can move up/down or capture up/down
		if(onBoard(clickRow+2,clickColumn+2) && onBoard(clickRow-2,clickColumn+2) && onBoard(row+1,column-1) && onBoard(row-1,column-1)){
			if ((clickRow+2==row || clickRow-2==row) && clickColumn+2==column && ((grid[clickRow+1][clickColumn+1]%2==1) || (grid[clickRow-1][clickColumn+1]%2==1))) {
				return true;
			} else return false;
		}
		if(clickColumn<6) {
			// Case if it can move/capture only up
			if(clickRow==7){
				if (clickRow-2==row && clickColumn+2==column &&  grid[row+1][column-1]%2==1) {
					return true;
				} else return false;
			}

			// Case if it can move up/down and capture only up
			if(clickRow==6){
				if (clickRow-2==row && clickColumn+2==column && grid[row+1][column-1]%2==1) {
					return true;
				} else return false;
			}

			// Case if it can capture down next to edge
			if(clickRow==5 && row==7){
				if (clickRow+2==row && clickColumn+2==column && grid[row-1][column-1]%2==1) {
					return true;
				} else return false;
			}

			// Case if it can move/capture only down
			if(clickRow==0){
				if (clickRow+2==row && clickColumn+2==column &&  grid[row-1][column-1]%2==1) {
					return true;
				} else return false;
			}

			// Case if it can move up/down and capture only down
			if(clickRow==1){
				if (clickRow+2==row && clickColumn+2==column &&  grid[row-1][column-1]%2==1) {
					return true;
				} else return false;
			}

			// Case if it can move and capture up/down
			if(clickRow==2 && row==0){
				if (clickRow-2==row && clickColumn+2==column && grid[row+1][column-1]%2==1) {
					return true;
				} else return false;
			} else return false;
		} 
		return false;

	}
	
	// Function that returns x coordinate of p2's king's captured piece
	int returnP2KingCaptureX(int clickRow, int clickColumn, int row, int column){
		
		//System.out.println("X -- clickRow:" + clickRow + " clickColumn:" + clickColumn + " row:" + row + " column:" + column);
		// First check right side of king piece
		if(column > clickColumn){

			//System.out.println("Check right side");
			if(onBoard(clickRow+2,clickColumn+2) && onBoard(clickRow-2,clickColumn+2) && onBoard(row+1,column-1) && onBoard(row-1,column-1)){
				if ((clickRow+2==row) && clickColumn+2==column && (grid[row-1][column-1]%2==1)) {
					return row-1;
				}
				if ((clickRow-2==row) && clickColumn+2==column && (grid[row+1][column-1]%2==1)) {
					return row+1;
				} else return row;
			} 

			if(clickColumn<6){
				if(clickRow==7){
					if (clickRow-2==row && clickColumn+2==column &&  grid[row+1][column-1]%2==1) {
						return row+1;
					} else return row;
				}

				// Case if it can move up/down and capture only up
				if(clickRow==6){
					if (clickRow-2==row && clickColumn+2==column && grid[row+1][column-1]%2==1) {
						return row+1;
					} else return row;
				}

				// Case if it can move and capture up/down
				if(clickRow==5 && row==7){
					if (clickRow+2==row && clickColumn+2==column && grid[row-1][column-1]%2==1) {
						return row-1;
					} else return row;
				}		

				// Case if it can move/capture only down
				if(clickRow==0){
					if (clickRow+2==row && clickColumn+2==column &&  grid[row-1][column-1]%2==1) {
						return row-1;
					} else return row-1;
				}

				// Case if it can move up/down and capture only down
				if(clickRow==1){
					if (clickRow+2==row && clickColumn+2==column &&  grid[row-1][column-1]%2==1) {
						return row-1;
					} else return row;
				}

				// Case if it can move and capture up/down
				if(clickRow==2 && row==0){
					if (clickRow-2==row && clickColumn+2==column && grid[row+1][column-1]%2==1) {
						return row+1;
					} else return row;
				} else return row;
			}
		}
		
		// Now check left side of king piece
		if(column < clickColumn){
			//System.out.println("Check left side");
			if(onBoard(clickRow+2,clickColumn-2) && onBoard(clickRow-2,clickColumn-2) && onBoard(row+1,column+1) && onBoard(row-1,column+1)){
				if ((clickRow+2==row) && clickColumn-2==column && (grid[row-1][column+1]%2==1)) {
					return row-1;
				}
				if ((clickRow-2==row) && clickColumn-2==column && (grid[row+1][column+1]%2==1)) {
					return row+1;
				} else return row;
			}
		
			if(clickColumn>1){
				if(clickRow==7){
					if (clickRow-2==row && clickColumn-2==column &&  (grid[row+1][column+1]%2==1)) {
						return row+1;
					} else return row;
				}

				// Case if it can move up/down and capture only up
				if(clickRow==6){
					if (clickRow-2==row && clickColumn-2==column && grid[row+1][column+1]%2==1) {
						return row+1;
					} else return row;
				}

				// Case if it can move and capture up/down
				if(clickRow==5 && row==7){
					if (clickRow+2==row && clickColumn-2==column && grid[row-1][column+1]%2==1) {
						return row-1;
					} else return row;
				}

				// Case if it can move and capture up towards the edge
				if(clickRow==2 && row==0){
					if(clickRow-2 == row && clickColumn-2==column && grid[row+1][column+1]%2==1){
						return row+1;
					} else return row;
				}
				
				// Case if it can move/capture only down
				if(clickRow==0){
					if (clickRow+2==row && clickColumn-2==column &&  grid[row-1][column+1]%2==1) {
						return row-1;
					} else return row-1;
				}

				// Case if it can move up/down and capture only down
				if(clickRow==1){
					if (clickRow+2==row && clickColumn-2==column &&  grid[row-1][column+1]%2==1) {
						return row-1;
					} else return row;
				}else return row;
			}
		}
		return row;
	}
	
	// Function that returns y coordinate of p2's king's captured piece
	int returnP2KingCaptureY(int clickRow, int clickColumn, int row, int column){
		
		//System.out.println("Y -- clickRow:" + clickRow + " clickColumn:" + clickColumn + " row:" + row + " column:" + column);
		// First check right side of king piece
		if(column > clickColumn){
			//System.out.println("Check  side");
			if(onBoard(clickRow+2,clickColumn+2) && onBoard(clickRow-2,clickColumn+2) && onBoard(row+1,column-1) && onBoard(row-1,column-1)){
				if ((clickRow+2==row) && clickColumn+2==column && (grid[row-1][column-1]%2==1)) {
					return column-1;
				}
				if ((clickRow-2==row) && clickColumn+2==column && (grid[row+1][column-1]%2==1)) {
					return column-1;
				} else return column;
			} 
		
			if(clickColumn<6){
				if(clickRow==7){
					if (clickRow-2==row && clickColumn+2==column && grid[row+1][column-1]%2==1) {
						return column-1;
					} else return column;
				}

				// Case if it can move up/down and capture only up
				if(clickRow==6){
					if (clickRow-2==row && clickColumn+2==column && grid[row+1][column-1]%2==1) {
						return column-1;
					} else return column;
				}

				// Case if it can move and capture up/down
				if(clickRow==5 && row==7){
					if (clickRow+2==row && clickColumn+2==column && grid[row-1][column-1]%2==1) {
						return column-1;
					} else return column;
				}

				// Case if it can move/capture only down
				if(clickRow==0){
					if (clickRow+2==row && clickColumn+2==column &&  grid[row-1][column-1]%2==1) {
						return column-1;
					} else return column;
				}

				// Case if it can move up/down and capture only down
				if(clickRow==1){
					if (clickRow+2==row && clickColumn+2==column &&  grid[row-1][column-1]%2==1) {
						return column-1;
					} else return column;
				}

				// Case if it can move and capture up/down
				if(clickRow==2 && row==0){
					if (clickRow-2==row && clickColumn+2==column && grid[row+1][column-1]%2==1) {
						return column-1;
					} else return column;
				} else return column;
			}
		}
			
		// Now check left side of king piece
		if(column < clickColumn){
			//System.out.println("Check left side");
			if(onBoard(clickRow+2,clickColumn-2) && onBoard(clickRow-2,clickColumn-2) && onBoard(row+1,column+1) && onBoard(row-1,column+1)){
				if ((clickRow+2==row) && clickColumn-2==column && (grid[row-1][column+1]%2==1)) {
					return column+1;
				}
				if ((clickRow-2==row) && clickColumn-2==column && (grid[row+1][column+1]%2==1)) {
					return column+1;
				} else return column;
			} 
		
			if(clickColumn>1){
				if(clickRow==7){
					if (clickRow-2==row && clickColumn-2==column &&  grid[row+1][column+1]%2==1) {
						return column+1;
					} else return column;
				}

				// Case if it can move up/down and capture only up
				if(clickRow==6){
					if (clickRow-2==row && clickColumn-2==column && grid[row+1][column+1]%2==1) {
						return column+1;
					} else return column;
				}

				// Case if it can move and capture up/down
				if(clickRow==5 && row==7){
					if (clickRow+2==row && clickColumn-2==column && grid[row-1][column+1]%2==1) {
						return column+1;
					} else return column;
				}

				// Case if it can move/capture only down
				if(clickRow==0){
					if (clickRow+2==row && clickColumn-2==column &&  grid[row-1][column+1]%2==1) {
						return column+1;
					} else return column;
				}

				// Case if it can move up/down and capture only down
				if(clickRow==1){
					if (clickRow+2==row && clickColumn-2==column &&  grid[row-1][column+1]%2==1) {
						return column+1;
					} else return column;
				}

				// Case if it can move and capture up/down
				if(clickRow==2 && row==0){
					if (clickRow-2==row && clickColumn-2==column && grid[row+1][column+1]%2==1) {
						return column+1;
					} else return column;
				} else return column;
			}
		}
		return column;
	}

	// Function to check is p2's king can capture again
	boolean checkP2KingCapClick(int clickRow, int clickColumn){
		
		// First check left hang side since it'll be the same for normal or king piece
		if(checkP2CapClick(clickRow, clickColumn)){
			return true;
		}
		
		// Now check right hand side
		// Checks if it's still towards the left side of the board
		if(clickColumn<6){

			// Checks if it can capture up/down
			if(onBoard(clickRow+2,clickColumn+2) && onBoard(clickRow-2,clickColumn+2)){

				// Checks if it can capture up/down
				if ((grid[clickRow+2][clickColumn+2]==0 || grid[clickRow-2][clickColumn+2]==0)  && (grid[clickRow+1][clickColumn+1]%2==1 || grid[clickRow-1][clickColumn+1]%2==1)) {
					return true;
				} else return false;
			}

			// Checks if it can capture up or down
			if(onBoard(clickRow+1,clickColumn+1) && onBoard(clickRow-1,clickColumn+1)){

				// If on 2nd to bottom row
				if(clickRow==6){ 

					// Check if it can capture up
					if ((grid[clickRow-2][clickColumn+2]==0)  && grid[clickRow-1][clickColumn+1]%2==1) {
						return true;
					} else return false;
				} 

				// If on 2nd to top row
				if(clickRow==1){

					// Check if it can capture down
					if ((grid[clickRow+2][clickColumn+2]==0)  && grid[clickRow+1][clickColumn+1]%2==1) {
						return true;
					} else return false;

				} else return false;
			}

			// Case if it can capture only up
			if(clickRow==7){

				// Check if it can capture up
				if ((grid[clickRow-2][clickColumn+2]==0)  && grid[clickRow-1][clickColumn+1]%2==1) {
					return true;
				} else return false;
			}

			// Case if it can capture only down
			if(clickRow==0){

				// Check if it can capture down
				if ((grid[clickRow+2][clickColumn+2]==0)  && grid[clickRow+1][clickColumn+1]%2==1) {
					return true;
				} else return false;
			} else return false;
		}
		return false;
	}
}
