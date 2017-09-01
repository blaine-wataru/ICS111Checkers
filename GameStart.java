/* This contains all the images and methods for the title screen before the game starts
 * 
 * Comments done
 */

public class GameStart {

	// Member variables
	boolean gameStart;		// This is the switch between the start screen and the actual game
	EZImage titleImage;		// This is the title screen
	EZImage startImage;		// This is the start button
	EZImage howToImage;		// This is the how to play button
	EZImage infoImage;		// This is the info screen
	EZImage closeImage;		// This is the close button
	EZImage danceImage;		// This is the dancing bunny and bear
	EZSound clickSound;		// This is the button clicking sound
	
	// Constructor
	GameStart(){
		titleImage = EZ.addImage("title.png",500, 25);		// This is the title, at top
		startImage = EZ.addImage("start.png", 250, 560);	// This is the start button, in place
		howToImage = EZ.addImage("howto.png", 750, 560);	// This is the how to play button, in place
		infoImage = EZ.addImage("info.png",-1000,-1000);	// This is the info screen, off screen
		closeImage = EZ.addImage("close.png",-1000,-1000);	// This is the close button, off screen
		danceImage = EZ.addImage("dance.png", -150, 300);	// This is the dancing bear and bunny, off screen

		clickSound = EZ.addSound("pop.wav");				// Adds the clicking sound
		
		titleImage.pullToFront();							// Title screen to front
		startImage.pullToFront();							// Start button to front 
		howToImage.pullToFront();							// How to button to front
		danceImage.pullToFront();							// Dance image to front 
		gameStart = false;									// Makes sure game doesn't start yet 

	}

	// Lets the bear and bunny dance around during the beginning
	void dance(){

		// Checks if image is on screen in window
		if (checkDanceOnScreen()){		
			danceImage.translateBy(2,0);		// Move the bear and bunny to the right
			danceImage.translateTo(danceImage.getXCenter(),300+75*Math.cos(danceImage.getXCenter()/28.66));	// Uses cosine function to move the image to the correct height
			danceImage.rotateBy(Math.cos(danceImage.getXCenter()/28.66)/2);	// Gives the image a wobble
		} 

		// When the image moves off screen
		else {							
			moveDanceBack();					// Moves bear and bunny back to left side of screen
		}
	}

	// Checks if image is off screen and returns TF value
	boolean checkDanceOnScreen(){
		if (danceImage.getXCenter()<1150) return true;
		else return false;
	}

	// Moves the image back on screen
	void moveDanceBack(){
		danceImage.translateTo(-150, 300);		// Resets image position to far left to restart the walk across
	}

	// Checks if start button is pressed
	void checkStartButton(){
		if (startImage.isPointInElement(EZInteraction.getXMouse(), EZInteraction.getYMouse())) {	// Checks if cursor on start button
			if ( EZInteraction.wasMouseLeftButtonReleased()){										// Checks if mouse was clicked

				clickSound.play();					// Plays the button click sound

				EZ.removeEZElement(titleImage);		// Removes title image
				EZ.removeEZElement(startImage);		// Removes start button
				EZ.removeEZElement(howToImage);		// Removes how to button
				EZ.removeEZElement(infoImage);		// Removes info screen
				EZ.removeEZElement(closeImage);		// Removes close button
				EZ.removeEZElement(danceImage);		// Removes dancing bear and bunny images

				gameStart=true;						// Changes gameStart variable so it can be changed in the main
			}
		}
	}

	// Checks if how to button is pressed
	void checkHowToButton(){
		if (howToImage.isPointInElement(EZInteraction.getXMouse(), EZInteraction.getYMouse())) {	// Checks if cursor on how to play button
			if ( EZInteraction.wasMouseLeftButtonReleased()){										// Checks if mouse was clicked

				clickSound.play();					// Plays the button click sound

				infoImage.translateTo(500,300);		// Moves the info screen to the middle
				infoImage.pullToFront();			// Pulls it up front
				closeImage.translateTo(925,65);		// Moves the close button to the corner of the info screen
				closeImage.pullToFront();			// Pulls it up front above the info screen

			}
		}
	}

	// Checks if close button is pressed
	void checkCloseButton(){
		if (closeImage.isPointInElement(EZInteraction.getXMouse(), EZInteraction.getYMouse())) {	// Checks if cursor on close button
			if ( EZInteraction.wasMouseLeftButtonReleased()){										// Checks if mouse was clicked

				clickSound.play();						// Plays the button click sound

				infoImage.translateTo(-1000,-1000);		// Hides info screen
				closeImage.translateTo(-1000,-1000);	// Hides close button
			}
		}
	}

	// Checks all buttons if any are pressed
	void checkAllButton(){
		checkStartButton();
		checkHowToButton();
		checkCloseButton();
	}

	// Checks if this (GameStart) object is done doing it's thing 
	boolean gameStartValue(){
		if (gameStart==true) return true;
		else return false;
	}

}
