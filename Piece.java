public class Piece{
	private boolean fire, king, has_captured; 
	private Board brd; 
	private int xpos, ypos; 
	private String piece_type;

	public Piece(boolean isFire, Board b, int x, int y, String type){
		fire = isFire;
		brd = b;
		xpos = x;
		ypos = y;
		piece_type = type;
		king = false;
		has_captured = false;
	}

	public boolean isFire(){
		if(fire){
			return true;
		}
		return false;
	}

	public int side(){
		if(fire){
			return 0;
		}
		return 1;
	}

	public boolean isKing(){
		return king;
	}

	public boolean isBomb(){
		if(piece_type == "bomb"){
			return true;
		}
		return false;
	}

	public boolean isShield(){
		if(piece_type == "shield"){
			return true;
		}
		return false;
	}

	public boolean hasCaptured(){
		return has_captured;
	}

	public void doneCapturing(){
		has_captured = false;
	}

	public void move(int x, int y){
		int x_change = x - xpos , y_change = y - ypos;

			//Explosion
    		if(piece_type == "bomb" && ((y_change == 2) || (y_change == -2))){

    			int stop_x = x+1, stop_y = y+1;
    			for(int start_x = x-1; start_x<=stop_x; start_x++){
    				for(int start_y = y-1; start_y<=stop_y; start_y++){

    					//Unless the piece is a shield, remove it
    					if(brd.pieceAt(start_x, start_y) != null){
    						if(!brd.pieceAt(start_x, start_y).isShield()){
    							brd.remove(start_x, start_y);
    						}

    					}

    				}
    			}


    			// Change the Board's configuration.
	    		brd.remove(xpos, ypos); // Remove the Piece from previous position

	    		// Change the Piece's coordinates
	    		xpos = x; 
	    		ypos = y;
    		}

    		//Not an explosion
    		else{
				//Capturing
				if((y_change == 2) || (y_change == -2)){
					brd.remove((xpos + (x_change/2)), (ypos + (y_change/2))); // Remove the captured piece from the board.
					has_captured = true;
				}				
				
	    		//Cases for changing King Status
	    		if( (y == 7 && isFire() == true) || (y == 0 && isFire() == false) ){
	    			king = true;
	    		}

	    		// Change the Board's configuration.
	    		brd.place(this, x, y);  // Places the new Piece with updated coordinates at the new location on the board
	    		brd.remove(xpos, ypos); // Remove the Piece from previous position

	    		// Change the Piece's coordinates
	    		xpos = x; 
	    		ypos = y;
    		}
    }
}



















