import java.awt.Color;

public class CheckersText {
	
	// Class to manage all text for the game
	// Create text for turn, promotion, and capture
	EZText p1TurnText = EZ.addText(125,570,"Player 1's turn ", new Color(255,110,135), 24);
	EZText p2TurnText = EZ.addText(875,570,"Player 2's turn", new Color(103,65,44), 24);
	EZText p1CaptureText = EZ.addText(350,570,"Player 1 Capture!", new Color(255,110,135), 24);
	EZText p2CaptureText = EZ.addText(650,570,"Player 2 Capture!", new Color(103,65,44), 24);
	EZText p1PromoteText = EZ.addText(350,570,"Player 1 Promote!", new Color(255,110,135), 24);
	EZText p2PromoteText = EZ.addText(650,570,"Player 2 Promote!", new Color(103,65,44), 24);
	
	// Constructor hides all text
	CheckersText(){
		pushBackText();
		p1TurnText.hide();
		p2TurnText.hide();
	}
	
	// Hide all text unrelated to turns
	void pushBackText(){
		p1CaptureText.pushToBack();
		p2CaptureText.pushToBack();
		p1PromoteText.pushToBack();
		p2PromoteText.pushToBack();
	}
	
	// Show whose turn it is
	void showTurn(int player){
		
		// If player 1's turn show p1 turn
		if(player == 1){
			p2TurnText.pushToBack();
			p1TurnText.show();
			p1TurnText.pullToFront();
		}
		
		// If player 2's turn show p2 turn
		if(player == 2){
			p1TurnText.pushToBack();
			p2TurnText.show();
			p2TurnText.pullToFront();
		}
		
	}
	
	// Show capture text
	void showCapture(int player){
		
		// If player 1 captured show text for p1
		if(player == 1){
			pushBackText();
			p1CaptureText.show();
			p1CaptureText.pullToFront();
		}
		
		// If player 2 captured show text for p2
		if(player == 2){
			pushBackText();
			p2CaptureText.show();
			p2CaptureText.pullToFront();
		}
	}
	
	// Show promotion text
	void showPromote(int player){
		
		// If player 1 promoted show text for p1
		if(player == 1){
			pushBackText();
			p1PromoteText.show();
			p1PromoteText.pullToFront();
		}
		
		// If player 1 promoted show text for p1
		if(player == 2){
			pushBackText();
			p2PromoteText.show();
			p2PromoteText.pullToFront();
		}
	}
	
}
