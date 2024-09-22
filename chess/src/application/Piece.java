package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Piece {
    private int row;
    private int col;
    private boolean isWhite;
    private String type;
    private ImageView square;

    ///
    
    
    
     public static int  topostivity(int a) {
    	 if(a<0) {
    		 a*=-1;
    		 return a;}
    	 return a;
     }
    //

     
     
    public Piece(int row, int col, boolean isWhite, String type, String imagePath) {
        this.row = row;
        this.col = col;
        this.isWhite = isWhite;
        this.type = type;
        this.square = new ImageView(new Image(imagePath));
    }
//
    
    
    
    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public String getType() {
        return type;
    }

    public ImageView getSquare() {
        return square;
    }

    public void move(int newRow, int newCol) {
        this.row = newRow;
        this.col = newCol;
    }

//    public boolean isValidMove(int newRow, int newCol) {
//
//        if (row ==newRow  && col == newCol) {
//            return false; 
//        }
//
//        switch (type) {
//            case "rooks":
//                return isValidRookMove(newRow, newCol);
//            case "knights":
//                return isValidKnightMove(newRow, newCol);
//            case "bishops":
//                return isValidBishopMove(newRow, newCol);
//            case "queen":
//                return isValidQueenMove(newRow, newCol);
//            case "king":
//                return isValidKingMove(newRow, newCol);
//            case "pawns":
//                return isValidPawnMove(newRow, newCol);
//            default:
//                return false;
//        }
//    }
    public boolean isValidMove(int newRow, int newCol,Piece targetPiece) {
        if (row == newRow && col == newCol) {
            return false;
        }

        
        if (targetPiece != null && targetPiece.isWhite() == isWhite) {
            return false;
        }

        switch (type) {
            case "rooks":
                return isValidRookMove(newRow, newCol);
            case "knights":
                return isValidKnightMove(newRow, newCol);
            case "bishops":
                return isValidBishopMove(newRow, newCol);
            case "queen":
                return isValidQueenMove(newRow, newCol);
            case "king":
                return isValidKingMove(newRow, newCol);
            case "pawns":
                return isValidPawnMove(newRow, newCol,targetPiece);
            default:
                return false;
        }
    }
    
    
    
    
///
    //
    private boolean isValidRookMove(int newRow, int newCol) {
      
    	
    	
    	return newRow == row || newCol == col;
    }
//
    private boolean isValidKnightMove(int newRow, int newCol) {
        int rowDiff = topostivity(newRow - row);
        int colDiff = topostivity(newCol - col);
        return (rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2);
    }

    private boolean isValidBishopMove(int newRow, int newCol) {
        int rowDiff = topostivity(newRow - row);
        int colDiff = topostivity(newCol - col);
        return rowDiff == colDiff;
    }

    private boolean isValidQueenMove(int newRow, int newCol) {
        return isValidRookMove(newRow, newCol) || isValidBishopMove(newRow, newCol);
    }

    private boolean isValidKingMove(int newRow, int newCol) {
        int rowDiff = Math.abs(newRow - row);
        int colDiff = Math.abs(newCol - col);
        
     
      
      
        
        
        return rowDiff <= 1 || colDiff <= 1;
    }



//    private boolean isValidPawnMove(int newRow, int newCol) {
//       //masla araha ha 3 se 7 row ma jana ka
//    	int rowDiff = newRow - row;
//    	//topostivity- khatam kardeta baic ha ma bhi bana sakta tha ;) 
//        int colDiff = topostivity(newCol - col);
////uffffffff
//        if (isWhite) 
//            return rowDiff == 1 && colDiff == 0;
//        else 
//            return rowDiff == -1 && colDiff == 0;
//        }
//    }
//
//   private boolean isValidPawnMove(int newRow, int newCol, Piece targetPiece) {
//        int rowDiff = newRow - row;
//        int colDiff = newCol - col;
//
//
//        // koi ha 
//        if (targetPiece != null && targetPiece.isWhite() != isWhite) {
//            // daignal move 
//            if (topostivity(rowDiff) == 1 && topostivity(colDiff) == 1) {
//                return true;
//            }
//        }
//
//        // piada ko move 1 qadam
//     
//        if (colDiff == 0 && topostivity(rowDiff) == 1) {
//            
//            if (targetPiece == null) {
////                return true;
//            	   if (isWhite) 
//            		  //column diff is 0 so can,t return
//                     return rowDiff == 1 && colDiff == 0;
//                 else 
//                     return rowDiff == -1 && colDiff == 0;
//                 }
//            }
//        
//
//        return false;
//   }}
//
//    
    private boolean isValidPawnMove(int newRow, int newCol, Piece targetPiece) {
        int rowDiff = (newRow - row);
        int colDiff =(newCol - col);

        // Check if the target piece is an opponent's piece
        boolean isCaptureMove = (targetPiece != null && targetPiece.isWhite() != isWhite);

        if (isCaptureMove) {
           if(isWhite==true) {
            if (rowDiff == 1 && Math.abs(colDiff) == 1) {
               
            	
            	return true;
            		
            		
            		
            		//perfected
            		
            	}
            }else if(rowDiff == -1 && Math.abs(colDiff) == 1) {
        		
        		return true;
        	}
        } else {
            
            if (colDiff == 0) {
                if (isWhite) {
                 
                    if (rowDiff == 1 && targetPiece == null) {
                        return true;
                    } else if (row == 1 && rowDiff == 2 && targetPiece == null) {
                       
                        return true;
                    }
                } else {
                   
                    if (rowDiff == -1 && targetPiece == null) {
                    	  System.out.println("dsafdsaf");
                    	return true;
                      
                    } else if (row == 6 && rowDiff == -2 && targetPiece == null) {
                        
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
//
//    
    




