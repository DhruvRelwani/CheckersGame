public class Board{

    /* Two Dimensional Array to hold the pieces */
    private Piece[][] boardarr = new Piece[8][8];
    private int player_turn = 0, selectedx = 0, selectedy = 0; 
    private boolean has_selected = false, has_moved = false, has_captured = false;


    /** Draws an N x N board. Adapted from:
        http://introcs.cs.princeton.edu/java/15inout/CheckerBoard.java.html
     */
    public Board(boolean shouldBeEmpty){
    	if (shouldBeEmpty){
    		//initializes and empty 8 x 8 board
    		for (int i = 0; i < 8; i++) {
            	for (int j = 0; j < 8; j++) {
            		boardarr[i][j] = null;
            	}
            }
    	}
    	else{
    		initialize_default_board(8, this);
    	}
    }

    private void drawBoard(int N) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if ((i + j) % 2 == 0) StdDrawPlus.setPenColor(StdDrawPlus.GRAY);
                else                  StdDrawPlus.setPenColor(StdDrawPlus.RED);
                StdDrawPlus.filledSquare(i + .5, j + .5, .5);
            }
        }
        initialize_pieceGUI(N);
    }

    private void initialize_pieceGUI(int N){
    	for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
            	String crown_check = "", type_check = "";
            	if (pieceAt(i, j) != null){
            		if(pieceAt(i, j).isKing() == true){
            			crown_check = "-crowned";
            		}
            		if(pieceAt(i, j).isFire() == true){
            			type_check = "-fire";
            		}
            		else if(pieceAt(i, j).isFire() != true){
            			type_check = "-water";
            		}
            		if(pieceAt(i, j).isBomb() == true){
            			StdDrawPlus.picture(i + .5, j + .5, "img/bomb"+ type_check + crown_check + ".png", 1, 1);
            		}
            		else if(pieceAt(i, j).isShield() == true){
            			StdDrawPlus.picture(i + .5, j + .5, "img/shield"+ type_check + crown_check + ".png", 1, 1);
            		}
            		else{
            			StdDrawPlus.picture(i + .5, j + .5, "img/pawn"+ type_check + crown_check + ".png", 1, 1);
            		}         		
            	}
        	}
        }
    }

    private void initialize_default_board(int N, Board b){
    	for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
            	if (j == 0 && i % 2 ==0){
            		boardarr[i][j] = new Piece(true, b, i, j, "pawn");
            	}
            	else if (j == 1 && i % 2 !=0){
            		boardarr[i][j] = new Piece(true, b, i, j, "shield");
            	}
            	else if (j == 2 && i % 2 ==0){
            		boardarr[i][j] = new Piece(true, b, i, j, "bomb");
            	}
            	else if (j == 5 && i % 2 !=0){
            		boardarr[i][j] = new Piece(false, b, i, j, "bomb");
            	}
            	else if (j == 6 && i % 2 ==0){
            		boardarr[i][j] = new Piece(false, b, i, j, "shield");
            	}
            	else if (j == 7 && i % 2 !=0){
            		boardarr[i][j] = new Piece(false, b, i, j, "pawn");
            	}
            	else{
            		boardarr[i][j] = null;
            	}
        	}
        }
    }


    public void place(Piece p, int x, int y){
    	if (x<8 && y<8 && (x > -1) && (y > -1) && (p != null)){
    		// If a piece already exists
    		if(pieceAt(x, y) != null){
    			remove(x, y);
    		}
    		boardarr[x][y] = p; // Change the Board's configuration
    	}
    }

    public Piece remove(int x, int y){
    	if(x>7 || y>7 || x<0 || y<0 ){
    		System.out.println("Out of bounds");
    		return null;
    	}
    	else if(pieceAt(x, y) == null){
    		System.out.println("No Piece to remove");
    		return null;
    	}
    	else{
    		Piece temp = boardarr[x][y];
    		boardarr[x][y] = null;
    		return temp;
    	}
    }

    public Piece pieceAt(int x, int y){
    	if(x<8 && (x > -1) && (y > -1) && y<8){
    		return boardarr[x][y];
    	}
    	return null;
    }

    /* Checks if the move is valid for the previously selected piece:
       1) Normal piece or King piece
       2) Fire or Water
       3) Geometrically possible
    */
    private boolean validMove(int xi, int yi, int xf, int yf){
    	int y_change = yf - yi, x_change = xf - xi;
    	// y_change for normal water piece is negative and positive for normal fire piece

    	//Normal Piece
    	if(!boardarr[xi][yi].isKing()){

    		// Normal Water Piece, Normal Movement
    		if( (y_change == -1) && ((x_change == -1) || (x_change == 1)) && (pieceAt(xf, yf)==null) && (boardarr[xi][yi].hasCaptured() == false) ){
    			return true;
    		}
    		// Normal Water Piece, Capture Movement
    		else if( (y_change == -2) && ((x_change == -2) || (x_change == 2)) && (pieceAt(xf, yf)==null) && (pieceAt((xi + (x_change/2)), (yi + (y_change/2))) != null) ) {
    			if(boardarr[(xi + (x_change/2))][(yi + (y_change/2))].isFire()){
    				return true;
    			}
    		}

    		// Normal Fire Piece, Normal Movement
    		else if( (y_change == 1) && ((x_change == -1) || (x_change == 1)) && (pieceAt(xf, yf)==null) && (boardarr[xi][yi].hasCaptured() == false) ){
    			return true;
    		}
    		// Normal Fire Piece, Capture Movement
    		else if( (y_change == 2) && ((x_change == -2) || (x_change == 2)) && (pieceAt(xf, yf)==null) && (pieceAt((xi + (x_change/2)), (yi + (y_change/2))) != null) ){
    			if(!boardarr[(xi + (x_change/2))][(yi + (y_change/2))].isFire()){
    				return true;
    			}
    		}
    	}

    	// KingPiece
    	if(boardarr[xi][yi].isKing()){
    		// KingPiece, Normal Movement
    		if( ((y_change == 1) || (y_change == -1) ) && ((x_change == -1) || (x_change == 1)) && (pieceAt(xf, yf)==null) && (boardarr[xi][yi].hasCaptured() == false) ){
    			return true;
    		}
    		//KingPiece Capture Movement
    		else if ( ((y_change == 2) || (y_change == -2))  && ((x_change == -2) || (x_change == 2)) && (pieceAt(xf, yf)==null) && (pieceAt((xi + (x_change/2)), (yi + (y_change/2))) != null) ){
    			// The piece to be captured should be of a different type than the capturing piece 
    			if(boardarr[(xi + (x_change/2))][(yi + (y_change/2))].isFire() != boardarr[xi][yi].isFire()){
    				return true;
    			}
    		}
    	}

    	return false;	
    }

 
    public boolean canSelect(int x, int y){
    	/* Selecting a square with a piece */
    	if (pieceAt(x, y) != null && has_captured == false){
    			// Corresponding player's turn
	    		if (player_turn == boardarr[x][y].side()){
	    			// Has selected a piece or Has selected but hasn't moved the piece.
	    			if(has_selected == false || (has_selected == true && has_moved == false)){
	    				return true;
	    			}
	    		}
	    		else if(player_turn != boardarr[x][y].side()){
	    			return false;
	    		}
    		
    	}

    	/* Selecting an empty square */
    	else if (pieceAt(x, y) == null){
    		// Selected previously for first move
    		if(has_selected == true && has_moved == false && validMove(selectedx, selectedy, x, y) == true){
    			return true;
    		}
    		// Selected previously for multiple captures
    		else if(has_selected == true && boardarr[selectedx][selectedy].hasCaptured() == true && validMove(selectedx, selectedy, x, y) == true){
    			return true;
    		}
    	}

    	return false;
    }

    public void select(int x, int y){

    	//Selecting a Piece
    	if(pieceAt(x, y) != null){
    		selectedx = x;
    		selectedy = y;
    		has_selected = true;
    		has_moved = false;
    		
    	}	

    	//Selecting an empty square
    	else if(pieceAt(x, y) == null){
    		//Selecting an empty square means that a piece has been previously selected
    		
    		boardarr[selectedx][selectedy].move(x, y); 
    		has_moved = true;

    		if((y - selectedy==2) || (y - selectedy== -2)){
    			has_captured = true;
    		}

    		selectedx = x;
    		selectedy = y;

    	}   			
    }

    public boolean canEndTurn(){
    	if(has_selected == true && (has_moved == true || boardarr[selectedx][selectedy].hasCaptured() == true)){
    		return true;
    	}
    	return false;
    }

    public void endTurn(){
    	has_selected = false;
    	if(pieceAt(selectedx, selectedy) != null){
    		boardarr[selectedx][selectedy].doneCapturing();
    	}
    	has_captured = false;
    	has_moved = false;
    	// Switch players
    	if(player_turn == 0){
    		player_turn = 1;
    	}
    	else{
    		player_turn = 0;
    	}
    }

    public String winner(){
    	int water_pieces = 0, fire_pieces = 0;

    	// Counting the pieces on board
    	for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
            	if(pieceAt(i, j) != null){
	            	if(boardarr[i][j].isFire()){
	            		fire_pieces += 1;
	            	}
	            	else{
	            		water_pieces += 1;
	            	}
            	}	
            }
        }
        if((water_pieces == 0) || (fire_pieces==0)){
        	// Game Results
	        if(water_pieces > fire_pieces){
	        	return "Water";
	        }
	        else if(water_pieces < fire_pieces) {
	        	return "Fire";
	        }
	        else{
	        	return "No one";
	        }
        }
        return null;
    }

    public static void main(String[] args) {
    	Board b = new Board(false);

        int N = 8;
        StdDrawPlus.setXscale(0, N);
        StdDrawPlus.setYscale(0, N);

        /** Monitors for mouse presses. Wherever the mouse is pressed,
            a new piece appears. */
        while(b.winner() == null) {
            b.drawBoard(N);
            if (StdDrawPlus.mousePressed()) {
                double x = StdDrawPlus.mouseX();
                double y = StdDrawPlus.mouseY();
                if (b.canSelect((int) x, (int) y)) {
                	b.select((int) x, (int) y);
                	StdDrawPlus.setPenColor(StdDrawPlus.WHITE);
    				StdDrawPlus.filledSquare(x + .5, y + .5, .5);
                }
            } 
            if (StdDrawPlus.isSpacePressed()){
            	if(b.canEndTurn()){
            		b.endTurn();
            	}
            }        
            StdDrawPlus.show(100);
        }

        
    }

}

















