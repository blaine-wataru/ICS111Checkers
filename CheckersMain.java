import java.awt.Color;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class CheckersMain {

	public static void main(String[] args) throws java.io.IOException{

		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

		//Game setup  	

		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

		// Sets up an EZ window w/ a green background
		EZ.initialize(1000,600);
		EZ.setBackgroundColor(new Color(190,242,158));

		// This is a button clicking sfx.
		EZSound clickSound = EZ.addSound("pop.wav");

		// Declare variables for different sections of game
		int gameStart=0; 	// When gameStart!=1, shows title screen (TITLE MODE). 
		// When gameStart==1, game plays. (PLAY MODE) Triggered by start button.

		// Contains all the start stuffs
		GameStart start = new GameStart();
		
		// Contains all the game over stuffs
		GameOver over = new GameOver();

		// Keeps track of which player's turn it is
		int turn = 1;

		// Keeps track of whether the game is over or not
		boolean gameOver = false;

		// Declares if capture chain can happen
		// FALSE = no capture done or no possible captures / TRUE = previous capture done and possible captures available
		boolean cap = false;

		// Declare which stage of the move
		// FALSE = choose piece to move / TRUE = choose space to move to
		boolean move = false;

		// Remember clicked piece values 
		int pClickX = 0;		// X position
		int pClickY = 0;		// Y position
		int pClickPiece = 0;	// Position in array list

		// Keeps track of each player's score
		int score1 = 0;
		int score2 = 0;

		// Add text for the turns, captures, and promotions
		CheckersText allText = new CheckersText();
		
		// Adds the image for the board
		CheckersImages allImage = new CheckersImages();
		EZImage board = EZ.addImage("board.png", 500, 300);
		board.hide();

		// Sets up grid board
		CheckersGrid gridBoard = new CheckersGrid();

		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

		//File reading		*DON'T TOUCH*

		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   

		// Set up 2d array w/ all possible board positions
		int boardPositionX[][] = new int[8][8];
		int boardPositionY[][] = new int[8][8];

		// Set up scanner to read file w/ all possible board positions
		Scanner positionScanner = new Scanner(new FileReader("boardposi.txt"));   	 

		// File format: x    y

		// Loops over each column and row in the 2D array and stores the X and Y positions in the 2D array
		for (int i=0; i<8; i++){   	 // For each row
			for (int j=0; j<8; j++){    // For each column
				boardPositionX[i][j]=positionScanner.nextInt();    //Stores the 1st int as the x position
				boardPositionY[i][j]=positionScanner.nextInt();    //Stores the 2nd int as the y position
			}
		}

		// Closes the scanner to avoid angry java
		positionScanner.close();

		// Set up 2d array w/ all possible board positions
		int boardLeftBoundX[][] = new int[8][8];
		int boardRightBoundX[][] = new int[8][8];
		int boardTopBoundY[][] = new int[8][8];
		int boardBottomBoundY[][] = new int[8][8];

		// Set up scanner to read file w/ boundaries of each square on the board
		Scanner boundaryScanner = new Scanner(new FileReader("boardbound.txt"));   	 

		// File format: left    right    top    bottom

		// Loops over each column and row in the 2D array and stores the X and Y positions in the 2D array
		for (int i=0; i<8; i++){   	 // For each row
			for (int j=0; j<8; j++){    // For each column
				boardLeftBoundX[i][j]=boundaryScanner.nextInt();    //Stores the 1st int as the left bound x position
				boardRightBoundX[i][j]=boundaryScanner.nextInt();   //Stores the 2nd int as the right bound x position
				boardTopBoundY[i][j]=boundaryScanner.nextInt();    	//Stores the 3rd int as the top bound y position
				boardBottomBoundY[i][j]=boundaryScanner.nextInt();  //Stores the 4th int as the bottom bound y position
			}
		}

		// Closes the scanner to avoid angry java
		boundaryScanner.close();

		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

		//Pieces setup		*DON'T TOUCH*

		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~  	 

		// Set up 12 checker piece images in arrayList for each player
		// Use arrayList so we can remove pieces at will
		ArrayList<EZImage> p1Piece = new ArrayList<EZImage>();
		ArrayList<EZImage> p2Piece = new ArrayList<EZImage>();

		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

		// Actual game starts here

		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ 
		
		while(true){

			// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~			

			// (TITLE MODE): Updates while game is on title screen

			// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

			// Updates while game is on the title screen
			while(gameStart==0){

				// Check if game should move on to start
				if(start.gameStartValue()){
					gameStart=1;				// Changes variable in the main that starts the game
				}

				// Swimming turtle animation
				start.dance();				// Moves dancers

				// Checks if buttons are pressed
				start.checkAllButton();

				// Refreshes the screen
				EZ.refreshScreen();
			}

			// Executes once the start button is pressed
			if(gameStart==1){

				// Show the board
				board.show();

				// Create the background and turn text
				allImage.init();
				allImage.normal();
				allText.pushBackText();
				allText.showTurn(1);

				// Reads the gridBoard object and puts pieces in where there are 1's and 2's
				for(int i=0;i<8;i++){
					for(int j=0;j<8;j++){
						if(gridBoard.returnValue(i, j)==1){
							p1Piece.add(EZ.addImage("piece1.png",boardPositionX[i][j], boardPositionY[i][j]));
						}
						if(gridBoard.returnValue(i, j)==2){
							p2Piece.add(EZ.addImage("piece2.png",boardPositionX[i][j], boardPositionY[i][j]));
						}
						if(gridBoard.returnValue(i, j)==3){
							p1Piece.add(EZ.addImage("king1.png",boardPositionX[i][j], boardPositionY[i][j]));
						}
						if(gridBoard.returnValue(i, j)==4){
							p2Piece.add(EZ.addImage("king2.png",boardPositionX[i][j], boardPositionY[i][j]));
						}
					}
				}
				
				// Update window
				EZ.refreshScreen();

			}

			//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

			//Gameplay 	

			//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

			// While game not over
			while(gameOver==false && gameStart==1){
				
				// Refresh screen
				EZ.refreshScreen();
				
				// Update mouse cursor position 
				int cursorX = EZInteraction.getXMouse();
				int cursorY = EZInteraction.getYMouse();
				
				// If it's player 1's turn
				if (turn==1){
					
					// If piece is clicked on
					if(EZInteraction.wasMouseLeftButtonReleased()){
						
						// Go through all spaces
						for(int i=0; i<8; i++){
							for(int j=0; j<8; j++){

								// If our cursor within the bounds of a space
								if(cursorX > boardLeftBoundX[i][j] && cursorX < boardRightBoundX[i][j]){
									if(cursorY > boardTopBoundY[i][j] && cursorY < boardBottomBoundY[i][j]){

										// Check if the piece in that space is (1) Player 1 normal piece & (2) Has a valid move
										if(gridBoard.returnValue(i, j) == 1){

											if((cap==false && gridBoard.checkP1Click(i, j, false)) || (cap==true && gridBoard.checkP1CapClick(i, j))){
												
												// Reset text and background
												allImage.normal();
												allText.pushBackText();

												// Store the piece's x and y position to be used later when moving it 
												pClickX = i; pClickY = j;
												
												// Run through each piece in the array list
												for(int k=0; k<p1Piece.size(); k++){	

													// Picks out the piece that was clicked and stores the value
													if(p1Piece.get(k).isPointInElement(cursorX, cursorY)){
														pClickPiece = k;
														move=true;

														System.out.println("Check ok 1");

													}	
												}
											}
										}	

										// Check if the piece in that space is (1) Player 1 king piece & (2) Has a valid move
										if(gridBoard.returnValue(i, j) == 3 ){
											if((cap==false && gridBoard.checkP1KingClick(i, j)) || (cap==true && gridBoard.checkP1KingCapClick(i, j))){

												// Reset text and background
												allImage.normal();
												allText.pushBackText();

												// Store the piece's x and y position to be used later when moving it 
												pClickX = i; pClickY = j;

												// Run through each piece in the array list
												for(int k=0; k<p1Piece.size(); k++){	

													// Picks out the piece that was clicked and stores the value
													if(p1Piece.get(k).isPointInElement(cursorX, cursorY)){
														pClickPiece = k;
														move=true;

														System.out.println("Check ok K1");

													}	
												}
											}
										}
									} 
								} 
							}	 
						}
					}

					// Move phase
					// Now we are moving a piece
					while(move == true){

						// If you right click, resets selected piece
						if(EZInteraction.wasMouseRightButtonReleased()){
							move = false;
							System.out.println("P1 Selection undone. You can pick another piece to move.");
						}

						// If we click a space to go to
						if(EZInteraction.wasMouseLeftButtonReleased()){

							// Update cursor points with space clicked
							cursorX = EZInteraction.getXMouse();
							cursorY = EZInteraction.getYMouse();	

							// Go through all spaces
							for(int i=0; i<8; i++){
								for(int j=0; j<8; j++){

									// If our cursor within the bounds of a space
									if(cursorX > boardLeftBoundX[i][j] && cursorX < boardRightBoundX[i][j]){
										if(cursorY > boardTopBoundY[i][j] && cursorY < boardBottomBoundY[i][j]){

											// If the selected piece is a normal piece
											if(gridBoard.returnValue(pClickX, pClickY) == 1){

												// Check if the space is (1) Empty & (2) Within range of the piece
												if(gridBoard.checkP1Move(pClickX,pClickY,i,j)){

													// Update the game window by moving the piece
													EZ.removeEZElement(p1Piece.get(pClickPiece));
													p1Piece.add(EZ.addImage("piece1.png",boardPositionX[i][j], boardPositionY[i][j]));

													// Update the grid by moving the piece
													gridBoard.assignValue(pClickX, pClickY, 0);
													gridBoard.assignValue(i, j, 1);		

													// If the piece is is at the end of the board, promote it to a king
													// Also show text and background change
													if(gridBoard.checkP1Promotion(i, j)){
														allImage.kinged(1);
														allImage.normal();
														allText.showPromote(1);
														EZ.removeEZElement(p1Piece.get(p1Piece.size()-1));
														p1Piece.add(EZ.addImage("king1.png",boardPositionX[i][j], boardPositionY[i][j]));
														gridBoard.assignValue(i, j, 3);
													}

													// Switch to Player 2's turn and reset move phase
													allText.showTurn(2);
													turn = 2;
													move = false;

													System.out.println("Move complete 1");
												} 

												// Check if the space is (1) Empty & (2) Within range of the piece & (3) Has an opposing player's piece to capture
												else if(gridBoard.checkP1Capture(pClickX,pClickY,i,j)){

													// Show capture text and background
													allImage.p1Cap();
													allText.showCapture(1);

													/* Nonsense check
													 * System.out.println("look1" + gridBoard.returnP1CaptureX(pClickX,pClickY,i,j) + gridBoard.returnP1CaptureY(pClickX,pClickY,i,j));									
													 */

													// Update the game window by moving the piece and removing the captured piece
													EZ.removeEZElement(p1Piece.get(pClickPiece));
													EZ.removeEZElement(EZ.getTopElementContainingPoint(boardPositionX[gridBoard.returnP1CaptureX(pClickX,pClickY,i,j)][gridBoard.returnP1CaptureY(pClickX,pClickY,i,j)], boardPositionY[gridBoard.returnP1CaptureX(pClickX,pClickY,i,j)][gridBoard.returnP1CaptureY(pClickX,pClickY,i,j)]));
													p1Piece.add(EZ.addImage("piece1.png",boardPositionX[i][j], boardPositionY[i][j]));

													// Update the grid by moving the piece and removing the captured piece
													gridBoard.assignValue(pClickX, pClickY, 0);
													gridBoard.assignValue(i, j, 1);
													gridBoard.assignValue(gridBoard.returnP1CaptureX(pClickX,pClickY,i,j),gridBoard.returnP1CaptureY(pClickX,pClickY,i,j),0);

													// If the piece is is at the end of the board, promote it to a king
													// Also show text and background change
													if(gridBoard.checkP1Promotion(i, j)){
														allImage.kinged(1);
														allImage.p1Cap();
														allText.showPromote(1);
														EZ.removeEZElement(p1Piece.get(p1Piece.size()-1));
														p1Piece.add(EZ.addImage("king1.png",boardPositionX[i][j], boardPositionY[i][j]));
														gridBoard.assignValue(i, j, 3);
													}

													System.out.println("Capture complete 1");

													// Set cap to true to allow multiple captures if possible
													cap=true;

													// If no more captures available
													if(gridBoard.checkP1CapClick(i, j)==false){
														
														//Switch to player 2's turn
														allText.showTurn(2);
														turn = 2;	
														
														cap = false;	// Set cap to false 
													}

													//Increase score and update onscreen text
													score1++;
													//score1Text.setMsg("Player 1 Score: " + score1);

													// End move portion of turn
													move = false;

												} 
												else System.out.println("Can't move to that space 1"); // If selected space is not a valid move
											}

											// If the selected piece is a king
											if(gridBoard.returnValue(pClickX, pClickY) == 3){

												// Check if the space is (1) Empty & (2) Within range of the piece
												if(gridBoard.checkKingMove(pClickX,pClickY,i,j)){

													// Update the game window by moving the piece
													EZ.removeEZElement(p1Piece.get(pClickPiece));
													p1Piece.add(EZ.addImage("king1.png",boardPositionX[i][j], boardPositionY[i][j]));

													// Update the grid by moving the piece
													gridBoard.assignValue(pClickX, pClickY, 0);
													gridBoard.assignValue(i, j, 3);		

													// Switch to Player 2's turn and reset move phase
													allText.showTurn(2);
													turn = 2;
													move = false;

													System.out.println("Move complete 1");
												}
												// Check if the space is (1) Empty & (2) Within range of the piece & (3) Has an opposing player's piece to capture
												else if(gridBoard.checkP1KingCapture(pClickX,pClickY,i,j)){

													// Show capture text and background change
													allImage.p1Cap();
													allText.showCapture(1);

													// Nonsense check
													//gridBoard.printBoard();
													System.out.println("Pass: pClickX: " + pClickX + " pClickY: " + pClickY + " i: " + i + "j: " + j);
													System.out.println("look1" + gridBoard.returnP1KingCaptureX(pClickX,pClickY,i,j) + gridBoard.returnP1KingCaptureY(pClickX,pClickY,i,j));									

													// Update the game window by moving the piece and removing the captured piece
													EZ.removeEZElement(p1Piece.get(pClickPiece));
													EZ.removeEZElement(EZ.getTopElementContainingPoint(boardPositionX[gridBoard.returnP1KingCaptureX(pClickX,pClickY,i,j)][gridBoard.returnP1KingCaptureY(pClickX,pClickY,i,j)], boardPositionY[gridBoard.returnP1KingCaptureX(pClickX,pClickY,i,j)][gridBoard.returnP1KingCaptureY(pClickX,pClickY,i,j)]));
													p1Piece.add(EZ.addImage("king1.png",boardPositionX[i][j], boardPositionY[i][j]));

													// Update the grid by moving the piece and removing the captured piece
													gridBoard.assignValue(pClickX, pClickY, 0);
													gridBoard.assignValue(i, j, 3);
													gridBoard.assignValue(gridBoard.returnP1KingCaptureX(pClickX,pClickY,i,j),gridBoard.returnP1KingCaptureY(pClickX,pClickY,i,j),0);

													System.out.println("Capture complete K1");

													// Set cap to true to allow multiple captures if possible
													cap=true;

													// If no more captures available
													if(gridBoard.checkP1KingCapClick(i, j)==false){
														
														//Switch to player 2's turn	
														allText.showTurn(2);
														turn = 2;
														
														cap = false;	// Set cap to false 
													}

													//Increase score and update onscreen text
													score1++;
													//score1Text.setMsg("Player 1 Score: " + score1);

													// End move portion of turn
													move = false;

												} 
												else System.out.println("Can't move to that space 1"); // If selected space is not a valid move
											}	
										}
									}
								}
							}
						}	


						// Refresh screen
						EZ.refreshScreen();

					}



				}

				// If it's player 2's turn
				if (turn==2){

					// If piece is clicked on
					if(EZInteraction.wasMouseLeftButtonReleased()){

						// Go through all spaces
						for(int i=0; i<8; i++){
							for(int j=0; j<8; j++){

								// If our cursor within the bounds of a space
								if(cursorX > boardLeftBoundX[i][j] && cursorX < boardRightBoundX[i][j]){
									if(cursorY > boardTopBoundY[i][j] && cursorY < boardBottomBoundY[i][j]){

										// Check if the piece in that space is (1) Player 2 normal piece & (2) Has a valid move
										if(gridBoard.returnValue(i, j) == 2){

											if((cap==false && gridBoard.checkP2Click(i, j, false)) || (cap==true && gridBoard.checkP2CapClick(i, j))){

												// Reset text and background
												allImage.normal();
												allText.pushBackText();

												// Store the piece's x and y position to be used later when moving it 
												pClickX = i; pClickY = j;

												// Run through each piece in the array list
												for(int k=0; k<p2Piece.size(); k++){	

													// Picks out the piece that was clicked and stores the value
													if(p2Piece.get(k).isPointInElement(cursorX, cursorY)){
														pClickPiece = k;
														move=true;

														System.out.println("Check ok 2");

														/* Nonsense checks delete later
														 * System.out.println(pClickX); 
														 * gridBoard.printBoard();
														 */
													}	

												}
											}
										}	

										// Check if the piece in that space is (1) Player 1 king piece & (2) Has a valid move
										if(gridBoard.returnValue(i, j) == 4 ){

											//System.out.println(gridBoard.checkKingClick(i,j));
											if((cap==false && gridBoard.checkP2KingClick(i, j)) || (cap==true && gridBoard.checkP2KingCapClick(i, j))){

												// Reset text and background
												allImage.normal();
												allText.pushBackText();

												// Store the piece's x and y position to be used later when moving it 
												pClickX = i; pClickY = j;

												// Run through each piece in the array list
												for(int k=0; k<p2Piece.size(); k++){	

													// Picks out the piece that was clicked and stores the value
													if(p2Piece.get(k).isPointInElement(cursorX, cursorY)){
														pClickPiece = k;
														move=true;

														System.out.println("Check ok K2");

														/* Nonsense checks delete later
														 * System.out.println(pClickX); 
														 * gridBoard.printBoard();
														 */
													}	

												}
											}
										}
									} 
								} 
							}	 
						}
					}

					// Move phase
					// Now we are moving a piece
					while(move == true){

						// If you right click, resets selected piece
						if(EZInteraction.wasMouseRightButtonReleased()){
							move = false;
							System.out.println("P2 Selection undone. You can pick another piece to move.");
						}

						// If we click a space to go to
						if(EZInteraction.wasMouseLeftButtonReleased()){

							// Update cursor points with space clicked
							cursorX = EZInteraction.getXMouse();
							cursorY = EZInteraction.getYMouse();	

							// Go through all spaces
							for(int i=0; i<8; i++){
								for(int j=0; j<8; j++){

									// If our cursor within the bounds of a space
									if(cursorX > boardLeftBoundX[i][j] && cursorX < boardRightBoundX[i][j]){
										if(cursorY > boardTopBoundY[i][j] && cursorY < boardBottomBoundY[i][j]){

											// If the selected piece is a normal piece
											if(gridBoard.returnValue(pClickX, pClickY) == 2){

												// Check if the space is (1) Empty & (2) Within range of the piece
												if(gridBoard.checkP2Move(pClickX,pClickY,i,j)){

													// Update the game window by moving the piece
													EZ.removeEZElement(p2Piece.get(pClickPiece));
													p2Piece.add(EZ.addImage("piece2.png",boardPositionX[i][j], boardPositionY[i][j]));

													// Update the grid by moving the piece
													gridBoard.assignValue(pClickX, pClickY, 0);
													gridBoard.assignValue(i, j, 2);		

													// If the piece is is at the end of the board, promote it to a king
													// Also show text and background change
													if(gridBoard.checkP2Promotion(i, j)){
														allImage.kinged(2);
														allImage.normal();
														allText.showPromote(2);
														EZ.removeEZElement(p2Piece.get(p2Piece.size()-1));
														p2Piece.add(EZ.addImage("king2.png",boardPositionX[i][j], boardPositionY[i][j]));
														gridBoard.assignValue(i, j, 4);
													}

													// Switch to Player 1's turn and reset move phase
													allText.showTurn(1);
													turn = 1;
													move = false;

													System.out.println("Move complete 2");
												} 

												// Check if the space is (1) Empty & (2) Within range of the piece & (3) Has an opposing player's piece to capture
												else if(gridBoard.checkP2Capture(pClickX,pClickY,i,j)){

													// Show capture text and background change
													allImage.p2Cap();
													allText.showCapture(2);

													/* Nonsense check
													 * System.out.println("look1" + gridBoard.returnP2CaptureX(pClickX,pClickY,i,j) + gridBoard.returnP2CaptureY(pClickX,pClickY,i,j));									
													 */

													// Update the game window by moving the piece and removing the captured piece
													EZ.removeEZElement(p2Piece.get(pClickPiece));
													EZ.removeEZElement(EZ.getTopElementContainingPoint(boardPositionX[gridBoard.returnP2CaptureX(pClickX,pClickY,i,j)][gridBoard.returnP2CaptureY(pClickX,pClickY,i,j)], boardPositionY[gridBoard.returnP2CaptureX(pClickX,pClickY,i,j)][gridBoard.returnP2CaptureY(pClickX,pClickY,i,j)]));
													p2Piece.add(EZ.addImage("piece2.png",boardPositionX[i][j], boardPositionY[i][j]));

													// Update the grid by moving the piece and removing the captured piece
													gridBoard.assignValue(pClickX, pClickY, 0);
													gridBoard.assignValue(i, j, 2);
													gridBoard.assignValue(gridBoard.returnP2CaptureX(pClickX,pClickY,i,j),gridBoard.returnP2CaptureY(pClickX,pClickY,i,j),0);

													// If the piece is is at the end of the board, promote it to a king
													// Also show text and background change
													if(gridBoard.checkP2Promotion(i, j)){
														allImage.kinged(2);
														allImage.p2Cap();
														allText.showPromote(2);
														EZ.removeEZElement(p2Piece.get(p2Piece.size()-1));
														p2Piece.add(EZ.addImage("king2.png",boardPositionX[i][j], boardPositionY[i][j]));
														gridBoard.assignValue(i, j, 4);
													}

													System.out.println("Capture complete 2");

													// Set cap to true to allow multiple captures if possible
													cap=true;

													// If no more captures available
													if(gridBoard.checkP2CapClick(i, j)==false){
														
														// Switch to player 1's turn
														allText.showTurn(1);
														turn = 1;
														
														cap = false;	// Set cap to false 
													}

													//Increase score and update onscreen text
													score2++;
													//score2Text.setMsg("Player 2 Score: " + score2);

													// End move portion of turn
													move = false;

												} 
												else System.out.println("Can't move to that space 2"); // If selected space is not a valid move
											}

											// If the selected piece is a king
											if(gridBoard.returnValue(pClickX, pClickY) == 4){

												// Check if the space is (1) Empty & (2) Within range of the piece
												if(gridBoard.checkKingMove(pClickX,pClickY,i,j)){

													// Update the game window by moving the piece
													EZ.removeEZElement(p2Piece.get(pClickPiece));
													p2Piece.add(EZ.addImage("king2.png",boardPositionX[i][j], boardPositionY[i][j]));

													// Update the grid by moving the piece
													gridBoard.assignValue(pClickX, pClickY, 0);
													gridBoard.assignValue(i, j, 4);		

													// Switch to Player 1's turn and reset move phase
													allText.showTurn(1);
													turn = 1;
													move = false;

													System.out.println("Move complete K2");
												}
												// Check if the space is (1) Empty & (2) Within range of the piece & (3) Has an opposing player's piece to capture
												else if(gridBoard.checkP2KingCapture(pClickX,pClickY,i,j)){
													
													// Show capture text and background
													allImage.p2Cap();
													allText.showCapture(2);

													// Nonsense check
													//gridBoard.printBoard();
													//System.out.println("look2" + gridBoard.returnP2CaptureX(pClickX,pClickY,i,j) + gridBoard.returnP2CaptureY(pClickX,pClickY,i,j));									

													// Update the game window by moving the piece and removing the captured piece
													EZ.removeEZElement(p2Piece.get(pClickPiece));
													EZ.removeEZElement(EZ.getTopElementContainingPoint(boardPositionX[gridBoard.returnP2KingCaptureX(pClickX,pClickY,i,j)][gridBoard.returnP2KingCaptureY(pClickX,pClickY,i,j)], boardPositionY[gridBoard.returnP2KingCaptureX(pClickX,pClickY,i,j)][gridBoard.returnP2KingCaptureY(pClickX,pClickY,i,j)]));
													p2Piece.add(EZ.addImage("king2.png",boardPositionX[i][j], boardPositionY[i][j]));

													// Update the grid by moving the piece and removing the captured piece
													gridBoard.assignValue(pClickX, pClickY, 0);
													gridBoard.assignValue(i, j, 4);
													gridBoard.assignValue(gridBoard.returnP2KingCaptureX(pClickX,pClickY,i,j),gridBoard.returnP2KingCaptureY(pClickX,pClickY,i,j),0);

													System.out.println("Capture complete K2");

													// Set cap to true to allow multiple captures if possible
													cap=true;

													// If no more captures available
													if(gridBoard.checkP2KingCapClick(i, j)==false){
														
														// Switch to player 1's turn
														allText.showTurn(1);
														turn = 1;
														
														cap = false;	// Set cap to false 
													}

													//Increase score and update onscreen text
													score2++;
													//score2Text.setMsg("Player 2 Score: " + score2);

													// End move portion of turn
													move = false;

												} 
												else System.out.println("Can't move to that space 2"); // If selected space is not a valid move
											}	
										}
									}
								}
							}
						}	


						// Refresh screen
						EZ.refreshScreen();

					}



				}

				// If either player has no pieces left the game is over
				// If player1 wins
				if(score1==12){
					gameOver = true;
					// Sets up the images for the game over screen
					over.setup1(allImage.returnKing(1),allImage.returnKing(2));
					// Refreshes screen to reflect changes
					EZ.refreshScreen();

				}	
				// If player2 wins
				if(score2==12){
					gameOver = true;
					// Sets up the images for the game over screen
					over.setup2(allImage.returnKing(1),allImage.returnKing(2));
					// Refreshes screen to reflect changes
					EZ.refreshScreen();

				}	

			}

			while (gameOver && gameStart==1){
				 
			// Checks if replay button was clicked 
			if (over.checkReplayButton()){		
				over.hide();		// Hides game over screen and replay button 
				clickSound.play();	// The button pressing sound for the replay button

				
				
				// Run through each piece in the array list
				for(int k=0; k<p1Piece.size(); k++){	
					EZ.removeEZElement(p1Piece.get(k));
				}
				
				// Run through each piece in the array list
				for(int k=0; k<p2Piece.size(); k++){	
					EZ.removeEZElement(p2Piece.get(k));
				}
				
				// Clear the arraylists to allow a new game to start
				p2Piece.clear();
				p1Piece.clear();

				// Resets game parts
				gridBoard.init();
				score1=0;
				score2=0;
				turn=1;
				gameOver=false;			// Lets game resume play
				
			}

			// Refreshes screen. Much important. 
			EZ.refreshScreen();
			}
			
			// If player 1 has no pieces player 2 wins
			if(p1Piece.size() == 0){
				System.out.println("Player 2 wins!");
			}

			// If player 2 has no pieces player 1 wins
			if(p2Piece.size() == 0){
				System.out.println("Player 1 wins!");
			}

			// Refreshes screen to reflect changes
			EZ.refreshScreen();

		}

	}

}
