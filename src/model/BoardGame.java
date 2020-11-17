package model;

import java.util.ArrayList;

public class BoardGame {

	private final int ROW = 10;
	private final int COLUMN = 10;

	private ArrayList<PieceSet> pieces;
	private Point[][] points;

	private String colorJ1;
	private String colorJ2;

	private MoveValidator moveValidator;
	
	public Boolean shingShangSeq = false;
	private Piece shingShangPiece = null;

	

	public BoardGame(String colorJ1, String colorJ2) {
		// super();

		this.colorJ1 = colorJ1;
		this.colorJ2 = colorJ2;

		pieces = new ArrayList<PieceSet>();
		points = new Point[10][10];

		pieces.add(new PieceSet(colorJ1));
		pieces.add(new PieceSet(colorJ2));

		moveValidator = new MoveValidator();

		// MODIFIEER POUR APPEL DANS GAME
		// this.init();

	}

	public void init() {

		System.out.println("INIT...");

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {

				if (j % 10 == 0) {
					System.out.println("\n");
				}

				if ((i == 1 && (j == 4 || j == 5) || (i == 8 && (j == 4 || j == 5)))) {
					this.getPoints()[i][j] = new Point(i, j, "Portal");
				}

				else if (j == 0 || j == 9) {
					this.getPoints()[i][j] = new Point(i, j, "Nothing");
				} else {
					this.getPoints()[i][j] = new Point(i, j, "Standard");

				}

			}
		}

		// ADDING POINT O,4 - 0,5 - 4,9 -- 5,9
		// FOUR SIDE POINT ON THE BOARD
		this.getPoints()[4][0] = new Point(4, 0, "Standard");
		this.getPoints()[5][0] = new Point(5, 0, "Standard");

		this.getPoints()[4][9] = new Point(4, 9, "Standard");
		this.getPoints()[5][9] = new Point(5, 9, "Standard");

	//	this.addDragon1(0, 1);
	//	this.addDragon1(0, 8);
		this.addLion1(0, 2);
		this.addLion1(1, 1);
		this.addLion1(1, 8);
		this.addLion1(0, 7);
		this.addMonkey1(0, 3);
		this.addMonkey1(0, 6);
		this.addMonkey1(1, 2);
		this.addMonkey1(1, 7);
		this.addMonkey1(2, 1);
		this.addMonkey1(2, 8);

	//	this.addDragon2(9, 1);
	//	this.addDragon2(9, 8);
		this.addLion2(8, 1);
		this.addLion2(9, 2);
		this.addLion2(9, 7);
		this.addLion2(8, 8);
		this.addMonkey2(7, 1);
		this.addMonkey2(8, 2);
		this.addMonkey2(9, 3);
		this.addMonkey2(9, 6);
		this.addMonkey2(8, 7);
		this.addMonkey2(7, 8);
		
		//TESTING PIECES
		this.addDragon1(4, 3);
	//	this.addMonkey1(7, 4);
		
		this.addDragon2(4, 4);
	//	this.addMonkey2(4, 6);
		

	}

////////////////////////////////////////////////////////////////////////////
	public void addLion1(int x, int y) {
		this.getPoints()[x][y].setPiece(new Lion(this.getPoints()[x][y], colorJ1));
		this.getPieces().get(0).addPiece(this.getPoints()[x][y].getPiece());
	}

	public void addMonkey1(int x, int y) {
		this.getPoints()[x][y].setPiece(new Monkey(this.getPoints()[x][y], colorJ1));
		this.getPieces().get(0).addPiece(this.getPoints()[x][y].getPiece());
	}

	public void addDragon1(int x, int y) {
		this.getPoints()[x][y].setPiece(new Dragon(this.getPoints()[x][y], colorJ1));
		this.getPieces().get(0).addPiece(this.getPoints()[x][y].getPiece());
	}

////////////////////////////////////////////////////////////////////////////
	public void addLion2(int x, int y) {
		this.getPoints()[x][y].setPiece(new Lion(this.getPoints()[x][y], colorJ2));
		this.getPieces().get(1).addPiece(this.getPoints()[x][y].getPiece());
	}

	public void addMonkey2(int x, int y) {
		this.getPoints()[x][y].setPiece(new Monkey(this.getPoints()[x][y], colorJ2));
		this.getPieces().get(1).addPiece(this.getPoints()[x][y].getPiece());
	}

	public void addDragon2(int x, int y) {
		this.getPoints()[x][y].setPiece(new Dragon(this.getPoints()[x][y], colorJ2));
		this.getPieces().get(1).addPiece(this.getPoints()[x][y].getPiece());
	}

////////////////////////////////////////////////////////////////////////////
	public String getColorJ1() {
		return colorJ1;
	}

	public void setColorJ1(String colorJ1) {
		this.colorJ1 = colorJ1;
	}

	public String getColorJ2() {
		return colorJ2;
	}

	public void setColorJ2(String colorJ2) {
		this.colorJ2 = colorJ2;
	}

	public ArrayList<PieceSet> getPieces() {
		return pieces;
	}

	public void setPieces(ArrayList<PieceSet> pieces) {
		this.pieces = pieces;
	}

	public Point[][] getPoints() {
		return points;
	}

	public void setPoints(Point[][] points) {
		this.points = points;
	}

	public void movePiece(Piece piece, Point to_point) {
		removePiece(piece.getPosition());
		piece.setPosition(to_point);
		this.getPoints()[to_point.getN_row()][to_point.getN_column()].setPiece(piece);
	}
	
	public void removePiece(Point point) {
		point.setPiece(null);
	}

	public void eatPiece(Piece piece, Point point) {
		
		System.out.println(piece.getType() +" du joueur " + piece.getColor() + " saute par dessus : " + point.getPiece().getType() 
				+ " du joueur " + point.getPiece().getColor() + " et l'attrape.");
	
			point.getPiece().killPiece();
			removePiece(point);
		
	}

	public Boolean placePiece(Piece piece, Point point) {
		
		Boolean ret = false;
		
		if(piece.equals(shingShangPiece)) {
			System.out.println("Choose another Piece, you already did a ShingShang sequence with this piece.");
		}
		else {
		
		
		Tuple<Boolean, String> moveData = moveValidator.moveValid(piece, point, getPoints());

		// CHECK IF MOVE OK -- getFirst return the Boolean of moveValidator ( move is valid or not ) 
		if (moveData.getFirst()) {
			// Reset the current ShingShangPiece
			setShingShangPiece(null);
			
						if (moveData.getSecond() == "EnnemyJump") {
				System.out.println("SHING-SHANG");
				this.shingShangSeq = true;
				this.shingShangPiece = piece;
				//System.out.println(getNeighbourPoint(piece,point).getPiece().getType());
				eatPiece(piece,getNeighbourPoint(piece,point));
				movePiece(piece, point);
			
			} 
			else if(moveData.getSecond() == "AllyJump") {
				this.shingShangSeq = true;
				movePiece(piece, point);
			}
			
			else{
				this.shingShangSeq = false;
				movePiece(piece, point);
			}

			ret = true;
		} else {
			ret = false;
			System.out.println("The Move is not allowed, try again");
		}

		
		}
		return ret;
	}

	

	public Piece getShingShangPiece() {
		return shingShangPiece;
	}

	public void setShingShangPiece(Piece shingShangPiece) {
		this.shingShangPiece = shingShangPiece;
	}

	public Point getNeighbourPoint(Piece piece, Point point_to ) {
		
		Point[][] all_points = getPoints();
		Point ret = null;
		
		int x = piece.getPosition().getN_row();
		int y = piece.getPosition().getN_column();
		
		//CROSS

		try {
			// CHECK IF DESTINATION IS IN BIG SQUARE
			// UP 
			if (all_points[x - 2][y].equals(point_to)) {

				// Check if the piece has a neighbour between itself and the destination
				if (all_points[x - 1][y].isUsed()) {
					ret = all_points[x - 1][y];
				}
			}
		} catch (java.lang.ArrayIndexOutOfBoundsException Exception) {
		}

		try {
			// CHECK IF DESTINATION IS IN BIG SQUARE
			// DOWN
			if (all_points[x + 2][y].equals(point_to)) {

				// Check if the piece has a neighbour between itself and the destination
				if (all_points[x + 1][y].isUsed()) {
					//System.out.println("POSITION POINT A MANGER" + x+1 + "," + y);
					ret = all_points[x + 1][y];
				}
			}
		} catch (java.lang.ArrayIndexOutOfBoundsException Exception) {
		}

		try {
			// CHECK IF DESTINATION IS IN BIG SQUARE
			// LEFT
			if (all_points[x][y - 2].equals(point_to)) {

				// Check if the piece has a neighbour between itself and the destination
				if (all_points[x][y - 1].isUsed()) {
					ret = all_points[x][y - 1];
				}
			}
		} catch (java.lang.ArrayIndexOutOfBoundsException Exception) {
		}

		try {
			// CHECK IF DESTINATION IS IN BIG SQUARE
			// RIGHT
			if (all_points[x][y + 2].equals(point_to)) {

				// Check if the piece has a neighbour between itself and the destination
				if (all_points[x][y + 1].isUsed()) {
					ret = all_points[x][y + 2];
				}
			}
		} catch (java.lang.ArrayIndexOutOfBoundsException Exception) {
		}

// DIAGONALS
		try {
			// CHECK IF DESTINATION IS IN BIG SQUARE
			// TOP LEFT CORNER
			if (all_points[x - 2][y - 2].equals(point_to)) {

				// Check if the piece has a neighbour between itself and the destination
				if (all_points[x - 1][y - 1].isUsed()) {
					ret = all_points[x - 1][y - 1];
				}
			}
		} catch (java.lang.ArrayIndexOutOfBoundsException Exception) {
		}

		try {
			// BOTTOM LEFT CORNER
			if (all_points[x + 2][y - 2].equals(point_to)) {

				// Check if the piece has a neighbour between itself and the destination
				if (all_points[x + 1][y - 1].isUsed()) {
					ret = all_points[x + 1][y - 1];
				}
			}
		} catch (java.lang.ArrayIndexOutOfBoundsException Exception) {
		}

		try {
			// TOP RIGHT CORNER
			if (all_points[x - 2][y + 2].equals(point_to)) {

				// Check if the piece has a neighbour between itself and the destination
				if (all_points[x - 1][y + 1].isUsed()) {
					ret = all_points[x - 1][y + 1];
				}
			}
		} catch (java.lang.ArrayIndexOutOfBoundsException Exception) {
		}

		try {
			// BOTTOM RIGHT CORNER
			if (all_points[x + 2][y + 2].equals(point_to)) {
				System.out.print("ok");
				// Check if the piece has a neighbour between itself and the destination
				if (all_points[x + 1][y + 1].isUsed()) {

					ret = all_points[x + 1][y + 1];

				}
			}
		} catch (java.lang.ArrayIndexOutOfBoundsException Exception) {
		}

		return ret;

	}
	
	public boolean getShingShangSeq() {
		return shingShangSeq;
	}
	
	public void setShingShangSeq(Boolean bool) {
		this.shingShangSeq = bool;
	}

	public Tuple<Boolean,String> onPortalCheck() {
		
		Tuple<Boolean,String> ret = new Tuple<Boolean,String>(false,null);
		//Portals of Player 1
		Point portalJ11 = this.getPoints()[1][4];
		Point portalJ12 = this.getPoints()[1][5];
		
		// Portals of Player2
		Point portalJ21 = this.getPoints()[8][4];
		Point portalJ22 = this.getPoints()[8][5];
		
		
		try {
		if(((portalJ11.getPiece().getType() == "Dragon") && (portalJ11.getPiece().getColor() == colorJ2 )) ||
			((portalJ12.getPiece().getType() == "Dragon") && (portalJ12.getPiece().getColor() == colorJ2 ))){
			
			ret.setFirst(true);
			ret.setSecond("2");
		}
		if(((portalJ21.getPiece().getType() == "Dragon") && (portalJ21.getPiece().getColor() == colorJ1 )) ||
			((portalJ22.getPiece().getType() == "Dragon") && (portalJ22.getPiece().getColor() == colorJ1 ))) {
			ret.setFirst(true);	
			ret.setSecond("1");
	}
		}
		catch(java.lang.NullPointerException Exception) {}

		return ret;
	}

	public Tuple<Boolean, String> noDragonCheck() {
		
		// Check if there is no Dragon left on one side to end the game
		Tuple<Boolean,String> ret = new Tuple<Boolean,String>(false,null);
		Integer player1Dragons = 0;
		Integer player2Dragons = 0;
		Integer sizeArr = 0;
		
		
		 
		 if( pieces.get(0).getPieces().size() == pieces.get(1).getPieces().size()) {
			 sizeArr = pieces.get(0).getPieces().size();
		 }
		 else {System.out.println("Error size of pieces arrays");}
		 
		
		
		for(int i = 0;i<sizeArr;i++) {
			if((pieces.get(0).getPieces().get(i).getType() == "Dragon" )
					&& (pieces.get(0).getPieces().get(i).getPieceState() == PieceState.ALIVE)) {
				
				player1Dragons++;
			}
			if((pieces.get(1).getPieces().get(i).getType() == "Dragon" )
					&& (pieces.get(1).getPieces().get(i).getPieceState() == PieceState.ALIVE)) {
				
				player2Dragons++;
			
			}
		}
		
		if(player1Dragons == 0) {
			ret.setFirst(true);
			ret.setSecond("2");
		}
		else if (player2Dragons == 0) {
			ret.setFirst(true);
			ret.setSecond("1");
		}
		else {}
		
		return ret;
	}

}
