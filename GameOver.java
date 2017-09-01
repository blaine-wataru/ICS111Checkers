/* This contains all the images and methods for the replay screen after someone wins the game
 * 
 * Comments done
 */

public class GameOver {
	
	// Member variables
	// Images used if the game ends without entering darkness mode
	EZImage p1WinImage;			// The p1 win image
	EZImage p2WinImage;			// The p2 win image
	EZImage replayImage;		// The replay button
	
	
	// Images used if the game ends in darkness mode
	EZImage darkP1WinImage;		// The dark p1 win image
	EZImage darkP2WinImage;		// The dark p2 win image
	EZImage darkReplayImage;	// The dark replay button

	// Constructor
	GameOver(){
		// Sets up images used if game ends without entering darkness mode
		p1WinImage = EZ.addImage("p1win.png", -1000, -1000);			// Adds image for P1 winning
		p2WinImage = EZ.addImage("p2win.png", -1000, -1000);			// Adds image for P2 winning
		replayImage = EZ.addImage("replay.png", -1000, -1000);			// Adds image for replay button
		
		// Sets up images used if game ends while in darkness mode
		darkP1WinImage = EZ.addImage("darkp1win.png", -1000, -1000);	// Adds image for P1 winning
		darkP2WinImage = EZ.addImage("darkp2win.png", -1000, -1000);	// Adds image for P2 winning
		darkReplayImage = EZ.addImage("darkreplay.png", -1000, -1000);	// Adds image for replay button
	}

	// Sets up the game over screen if player 1 wins
	void setup1(boolean king1, boolean king2){
		
		// If game ends while in darkness mode
		if (king1 && king2) {
			darkP1WinImage.pullToFront();			// Pulls game over image over everything else
			darkP1WinImage.translateTo(500,300);	// Brings game over image to center of screen
			darkReplayImage.pullToFront();			// Brings replay button to front over the game over screen
			darkReplayImage.translateTo(500,350);	// Move replay button to screen
		} 
		
		// If game ends while not in darkness mode
		else {
			p1WinImage.pullToFront();				// Pulls game over image over everything else
			p1WinImage.translateTo(500,300);		// Brings game over image to center of screen
			replayImage.pullToFront();				// Brings replay button to front over the game over screen
			replayImage.translateTo(500,350);		// Move replay button to screen
		}
	}

	// Sets up the game over screen if player 1 wins
	void setup2(boolean king1, boolean king2){
		
		// If game ends in darkness mode
		if (king1 && king2) {
			darkP2WinImage.pullToFront();			// Pulls game over image over everything else
			darkP2WinImage.translateTo(500,300);	// Brings game over image to center of screen
			darkReplayImage.pullToFront();			// Brings replay button to front over the game over screen
			darkReplayImage.translateTo(500,350);	// Move replay button to screen
		} 
		
		// If game ends while not in darkness mode
		else {
			p2WinImage.pullToFront();				// Pulls game over image over everything else
			p2WinImage.translateTo(500,300);		// Brings game over image to center of screen
			replayImage.pullToFront();				// Brings replay button to front over the game over screen
			replayImage.translateTo(500,350);		// Move replay button to screen
		}
	}

	// Checks if replay button (dark or normal) is pressed or not
	boolean checkReplayButton(){
		if (replayImage.isPointInElement(EZInteraction.getXMouse(), EZInteraction.getYMouse()) && 
				EZInteraction.wasMouseLeftButtonReleased())	return true;
		if (darkReplayImage.isPointInElement(EZInteraction.getXMouse(), EZInteraction.getYMouse()) && 
				EZInteraction.wasMouseLeftButtonReleased())	return true;
		else return false;
	}

	// Hides the images once it's time to replay
	void hide(){
		p1WinImage.translateTo(-1000,-1000);		// Move P1 win game over image off screen
		p2WinImage.translateTo(-1000,-1000);		// Move P2 win game over image off screen
		darkP1WinImage.translateTo(-1000,-1000);	// Move dark P1 win game over image off screen
		darkP2WinImage.translateTo(-1000,-1000);	// Move dark P2 win game over image off screen
		replayImage.translateTo(-1000,-1000);		// Move replay image off screen
		darkReplayImage.translateTo(-1000,-1000);	// Move dark replay image off screen
	}
}
