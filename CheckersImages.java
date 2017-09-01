import java.awt.Color;

/* This class takes care of all the fun images that don't affect the game mechanics
 * Images change depending on how intense the game becomes
 * 
 * Comments done
 */

public class CheckersImages {

	// All images, declared as member variables 
	// All normal bear images
	EZImage normalBearImg = EZ.addImage("normbear.png", -600, -600);
	EZImage normalBearCapImg = EZ.addImage("normbearcap.png", -600, -600);
	EZImage normalBearTakImg = EZ.addImage("normbeartaken.png", -600, -600);
	
	// All normal bunny images
	EZImage normalBunImg = EZ.addImage("normbun.png", -600, -600);
	EZImage normalBunCapImg = EZ.addImage("normbuncap.png", -600, -600);
	EZImage normalBunTakImg = EZ.addImage("normbuntaken.png", -600, -600);
	
	// All king bear images
	EZImage KingBearImg = EZ.addImage("kingbear.png", -600, -600);
	EZImage KingBearEatImg = EZ.addImage("kingbearate.png", -600, -600);
	
	// All king bunny images
	EZImage KingBunImg = EZ.addImage("kingbun.png", -600, -600);
	EZImage KingBunEatImg = EZ.addImage("kingbunate.png", -600, -600);
	
	// Darkness images when game gets exciting
	EZImage fireImg = EZ.addImage("fire.png", 500, 364);
	EZImage darkTitleImg = EZ.addImage("darktitle.png", 500, 25);
	EZImage darkBottomImg = EZ.addImage("darkbottom.png", 500, 570);
	
	// Normal title/bottom images
	EZImage titleImg = EZ.addImage("title.png", 500, 25);
	EZImage bottomImg = EZ.addImage("bottom.png", 500, 570);

	// Useful places to translateTo, stored as constants
	// Position of P2 bear character
	private static final int BEARX = 863;		
	private static final int BEARY = 270;
	
	// Position of P1 bunny character
	private static final int BUNX = 133;	
	private static final int BUNY = 290;

	// Determines whether the player has gotten their first king
	boolean king1 = false;
	boolean king2 = false;

	// Constructor
	CheckersImages(){
		// Hides all images
		hide();
		bottomImg.hide();
		darkBottomImg.hide();
		darkTitleImg.hide();
		fireImg.hide();
		
		// Shows only the relevant images for normal gameplay
		normal();
	}

	// Initializes background, images, and king booleans. Used at the start of the game
	void init(){
		
		// Sets first king acquired booleans to false
		king1 = false;
		king2 = false;
		
		// Sets background to green
		EZ.setBackgroundColor(new Color(190,242,158));
		
		// Translates all images to the appropriate place
		normalBearImg.translateTo(BEARX,BEARY);
		normalBunImg.translateTo(BUNX,BUNY);
		normalBearImg.translateTo(BEARX,BEARY);
		normalBunImg.translateTo(BUNX,BUNY);
		normalBearCapImg.translateTo(BEARX,BEARY);
		normalBunCapImg.translateTo(BUNX,BUNY);
		normalBearTakImg.translateTo(BEARX,BEARY);
		normalBunTakImg.translateTo(BUNX,BUNY);
		KingBearImg.translateTo(BEARX+25,BEARY+50);
		KingBunImg.translateTo(BUNX,BUNY+25);
		KingBearEatImg.translateTo(BEARX+25,BEARY+50);
		KingBunEatImg.translateTo(BUNX,BUNY+25);
		
		// Hides darkness images
		darkBottomImg.hide();
		darkTitleImg.hide();
		fireImg.hide();
		
		// Makes sure normal title and bottom images are showing
		titleImg.show();
		bottomImg.show();
		
		// Makes sure only the normal player images are showing
		normal();
	}

	
	// Hide all player images
	void hide(){
		normalBearImg.hide();
		normalBunImg.hide();
		normalBearCapImg.hide();
		normalBunCapImg.hide();
		normalBearTakImg.hide();
		normalBunTakImg.hide();
		KingBearImg.hide();
		KingBunImg.hide();
		KingBearEatImg.hide();
		KingBunEatImg.hide();
	}

	// Shows normal player images
	// This is when both players show neutral images
	void normal(){
		
		// Hides all player images
		hide();
		
		// Checks to see if darkness activated
		darkness();
		
		// Chooses whether to show the king or normal version of the player
		if (king2) KingBearImg.show();
		if (king2==false) normalBearImg.show();
		if (king1) KingBunImg.show();
		if (king1== false) normalBunImg.show();

	}
	
	// Shows capture by P1 images
	// This is when the bear is upset (unless it's a king) and the bunny is happy
	void p1Cap(){
		// Hides all player images
		hide();
		
		// Checks to see if darkness activated
		darkness();
		
		// Chooses whether to show the king or normal version of the player
		if (king2) KingBearImg.show();				// Neutral king bear
		if (king2==false) normalBearTakImg.show();	// Angry bear
		if (king1) KingBunEatImg.show();			// King bunny after a snack
		if (king1==false) normalBunCapImg.show();	// Happy bunny
	}

	
	// Shows capture by P2 images
	// This is when the bunny is upset (unless it's a king) and the bear is happy
	void p2Cap(){
		// Hides all player images
		hide();
		
		// Checks to see if darkness activated
		darkness();
		
		// Chooses whether to show the king or normal version of the player
		if (king2) KingBearEatImg.show();			// King bear after a snack
		if (king2==false) normalBearCapImg.show();	// Happy bear
		if (king1) KingBunImg.show();				// Neutral king bunny
		if (king1==false) normalBunTakImg.show();	// Angry bunny
	}

	// Changes the 1st king acquired boolean to true depending on which player got their king
	void kinged(int player){
		if (player==1) king1=true;
		if (player==2) king2=true;
	}

	// Darkness...
	// Images become very dark once both players have a king
	void darkness(){
		if (king1 && king2){
			titleImg.hide();
			bottomImg.hide();
			darkTitleImg.show();
			darkBottomImg.show();
			fireImg.show();
			EZ.setBackgroundColor(Color.BLACK);
		}
	}

	// Returns whether or not the player has acquired their first king
	boolean returnKing(int player){
		if (player==1) return king1;
		if (player==2) return king2;
		else return false;
	}

}
