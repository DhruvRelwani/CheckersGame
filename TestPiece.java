import static org.junit.Assert.*;
import org.junit.Test;

public class TestPiece{

	/** Tests the constructor of Piece */
    @Test
    public void testConstructor(){
    	Piece p = new Piece(true, null, 0, 2, "pawn");
    	assertEquals(true, p.isFire());
    	assertEquals(0, p.side());
    	assertEquals(false, p.isBomb());
    	assertEquals(false, p.isShield());
    	assertEquals(false, p.isKing());
    	assertEquals(false, p.hasCaptured());
    }

    @Test
    public void testPieceIsFire(){
    	//Testing Fire Pawn
    	Piece p1 = new Piece(true, null, 0, 4, "pawn");
    	assertEquals(true, p1.isFire());
    	assertEquals(0, p1.side());

    	//Testing Fire Shield
    	Piece p2 = new Piece(true, null, 1, 1, "shield");
    	assertEquals(true, p2.isFire());
    	assertEquals(0, p2.side());

    	//Testing Fire Bomb
    	Piece p3 = new Piece(true, null, 2, 6, "bomb");
    	assertEquals(true, p3.isFire());
    	assertEquals(0, p3.side());
    }

	@Test
    public void testPieceIsWater(){
		//Testing Water Pawn
    	Piece p1 = new Piece(false, null, 7, 1, "pawn");
    	assertEquals(false, p1.isFire());
    	assertEquals(1, p1.side());

    	//Testing Water Shield
    	Piece p2 = new Piece(false, null, 6, 2, "shield");
    	assertEquals(false, p2.isFire());
    	assertEquals(1, p2.side());

    	//Testing Water Bomb
    	Piece p3 = new Piece(false, null, 5, 3, "bomb");
    	assertEquals(false, p3.isFire());
    	assertEquals(1, p3.side());
    }

    @Test
    public void testPieceIsShield(){
    	//Testing Fire Shield true
    	Piece p1 = new Piece(true, null, 1, 3, "shield");
    	assertEquals(true, p1.isShield());

    	//Testing Fire Shield false
    	Piece p2 = new Piece(true, null, 0, 4, "bomb");
    	assertEquals(false, p2.isShield());

    	//Testing Water Shield true
    	Piece p3 = new Piece(false, null, 6, 4, "shield");
    	assertEquals(true, p3.isShield());

    	//Testing Water Shield false
    	Piece p4 = new Piece(false, null, 7, 5, "pawn");
    	assertEquals(false, p4.isShield());
    }

    @Test
    public void testPieceIsBomb(){
    	//Testing Fire Bomb true
    	Piece p1 = new Piece(true, null, 0, 4, "bomb");
    	assertEquals(true, p1.isBomb());

    	//Testing Fire Bomb false
    	Piece p2 = new Piece(true, null, 1, 3, "shield");
    	assertEquals(false, p2.isBomb());

    	//Testing Water Bomb true
    	Piece p3 = new Piece(false, null, 5, 1, "bomb");
    	assertEquals(true, p3.isBomb());

    	//Testing Water Bomb false
    	Piece p4 = new Piece(false, null, 7, 5, "pawn");
    	assertEquals(false, p4.isBomb());
    }

    @Test
    public void testCaptureMethods(){
    	//Testing Fire Bomb true
    	Piece p1 = new Piece(true, null, 0, 4, "bomb");
    	assertEquals(false, p1.hasCaptured());
    }

	/* Run the unit tests in this file. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestPiece.class);
    }   
}