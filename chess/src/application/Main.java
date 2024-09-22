package application;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
//i dont no what i am doing day 1 of the project dated 1 may labour day for programmers
//its day 2 may 4 pata nahi logics kaisa banaga
//7 tarqi hogai images add karna agayi
//12 acha board image kamnahi kare gi usme pixel se image mathch or pices sahi set nahi horaha
// Ya Allah bacha la
// grid pane jor liya 64
// acha pice bhi add kardiya
// pices ki aleda class bana gi jahan har ki position pata honi chahia
// piece move nahi horaha
// aj bhi nahi horaha
// raat ko bhi nahi horaha
//google bhi fail youtube bhi stack overflow
//love you chatgpt eventhandles sahi bata diya
//bas ab movement ka logic raha gaya
//aur marna ka
// pawn ko queen bhi tu banana ha

public class Main extends Application {
    private static final int BOARD_SIZE = 8;
    private static final int SQUARE_SIZE = 120;
    private static final int PIECE_SIZE = 125;
    private static final String WHITE_SQUARE_COLOR = "#f0d9b5";
    private static final String BLACK_SQUARE_COLOR = "#b58863";

    private Piece[][] pieces;
    private Piece selectedPiece;
    private double mouseX, mouseY;
    private double pieceX, pieceY;

    public static void main(String[] args) {
        launch(args);
    }
    Group root;
    @Override
    public void start(Stage primaryStage) {
    
    	root = new Group();
         
        
        Scene scene = new Scene(root, BOARD_SIZE * SQUARE_SIZE, BOARD_SIZE * SQUARE_SIZE);

        initializeBoard(root);
        initializePieces(root);

        primaryStage.setTitle("Chess Game by Dawood Technologies");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void initializeBoard(Group root) {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                Rectangle square = new Rectangle(SQUARE_SIZE, SQUARE_SIZE);
                square.setX(col * SQUARE_SIZE);
                square.setY(row * SQUARE_SIZE);
                square.setFill((row + col) % 2 == 0 ? Color.web(WHITE_SQUARE_COLOR) : Color.web(BLACK_SQUARE_COLOR));

                root.getChildren().add(square);
            }
        }
    }

    private void initializePieces(Group root) {
        pieces = new Piece[BOARD_SIZE][BOARD_SIZE];

       
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
            	
            if(i==0&&(j==0||j==7)) //racist topain
        pieces[i][j] = new Piece(i, j, true, "rooks", "file:///C:/Users/Dawood/eclipse-workspace/chess/src/application/chess-rook-white.png");
        //pieces[0][7] = new Piece(i, j, true, "rooks", "file:///C:/Users/Dawood/eclipse-workspace/chess/src/application/chess-rook-white.png");
            else if (i == 7 && (j == 0 || j == 7)) //kali topain 
        pieces[i][j] = new Piece(i, j, false, "rooks", "file:///C:/Users/Dawood/eclipse-workspace/chess/src/application/chess-rook-black.png");
        //pieces[i][j] = new Piece(i, j, false, "rooks", "file:///C:/Users/Dawood/eclipse-workspace/chess/src/application/chess-rook-black.png");
           
            else if(i==1)//racist pawns
 pieces[i][j]=new Piece(i, j, true, "pawns", "file:///C:/Users/Dawood/eclipse-workspace/chess/src/application/chess-pawn-white.png");
            else if(i==6) //hamari foji
  pieces[i][j]=new Piece(i, j, false, "pawns", "file:///C:/Users/Dawood/eclipse-workspace/chess/src/application/chess-pawn-black.png");
          
            else if(i==0&&(j==1||j==6))//chita ghor sawar
            pieces[i][j]=new Piece(i, j, true, "knights", "file:///C:/Users/Dawood/eclipse-workspace/chess/src/application/chess-knight-white.png");	
            	else if(i==0&&(j==2||j==5))//chita hati 
            		pieces[i][j]=new Piece(i, j, true, "bishops", "file:///C:/Users/Dawood/eclipse-workspace/chess/src/application/chess-bishop-white.png");	
            		
           	else if(i==7&&(j==1||j==6))//ara brown ha ghora
            			pieces[i][j]=new Piece(i, j, false, "knights", "file:///C:/Users/Dawood/eclipse-workspace/chess/src/application/chess-knight-black.png");	

         	else if(i==7&&(j==2||j==5))//bishop bhi 
           				pieces[i][j]=new Piece(i, j, false, "bishops", "file:///C:/Users/Dawood/eclipse-workspace/chess/src/application/chess-bishop-black.png");    

            }  				
        
            if(i==1)
	i=5;
            }
        
        //malikaain
        pieces[0][4]=new Piece(0, 4, true, "queen", "file:///C:/Users/Dawood/eclipse-workspace/chess/src/application/chess-queen-white.png"); 
        pieces[7][4]=new Piece(7, 4, false, "queen", "file:///C:/Users/Dawood/eclipse-workspace/chess/src/application/chess-queen-black.png"); 
        //Masson kings
        pieces[7][3]=new Piece(7, 3, false, "king", "file:///C:/Users/Dawood/eclipse-workspace/chess/src/application/chess-king-black.png"); 
        pieces[0][3]=new Piece(0, 3, true, "king", "file:///C:/Users/Dawood/eclipse-workspace/chess/src/application/chess-king-white.png"); 
        
        // Add the pieces' images to the board
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                Piece piece = pieces[row][col];
                if (piece != null) {
                    ImageView image = piece.getSquare();
                    image.setFitWidth(PIECE_SIZE);  // this func to the width of the ImageView
                    image.setFitHeight(PIECE_SIZE); // Set the height of the ImageView
                    image.setX(col * SQUARE_SIZE + (SQUARE_SIZE - PIECE_SIZE) / 2);
                    image.setY(row * SQUARE_SIZE + (SQUARE_SIZE - PIECE_SIZE) / 2);

            
                    image.setOnMousePressed(this::handleMousePressed);
                    image.setOnMouseDragged(this::handleMouseDragged);
                    image.setOnMouseReleased(this::handleMouseReleased);

                    root.getChildren().add(image);
                }
            }
        }
    }

    private void handleMousePressed(MouseEvent event) {
        ImageView image = (ImageView) event.getSource();

        // Store the initial position of the piece and the mouse
        selectedPiece = findPiece(image);
        pieceX = image.getX();
        pieceY = image.getY();
        mouseX = event.getSceneX();
        mouseY = event.getSceneY();

        // Bring the selected piece to the front
        image.toFront();
    }

    private void handleMouseDragged(MouseEvent event) {
        if (selectedPiece != null) {
           
            double deltaX = event.getSceneX() - mouseX;
            double deltaY = event.getSceneY() - mouseY;
            double newPieceX = pieceX + deltaX;
            double newPieceY = pieceY + deltaY;

      
            ImageView image = selectedPiece.getSquare();
            image.setX(newPieceX);
            image.setY(newPieceY);
        }
    }

    private void handleMouseReleased(MouseEvent event) {
        if (selectedPiece != null) {
          
            int newRow = (int) (event.getY() / SQUARE_SIZE);
            int newCol = (int) (event.getX() / SQUARE_SIZE);

            movePiece(selectedPiece, newRow, newCol);

           
            selectedPiece = null;
            pieceX = 0;
            pieceY = 0;
            mouseX = 0;
            mouseY = 0;
        }
    }
 // ...
//int count=2;
//    private void movePiece(Piece piece, int newRow, int newCol) {
//        Piece targetPiece = pieces[newRow][newCol];
//
//        if ((piece.isWhite() == false) && count % 2 == 0 || ((piece.isWhite() == true) && count % 2 == 1)) {
//
//            if (piece.isValidMove(newRow, newCol, targetPiece)) {
//
//                if (!uperSe(piece.getRow(), piece.getCol(), newRow, newCol) || piece.getType().equals("knights")) {
//
//                    if (targetPiece != null) {
//                        removePiece(targetPiece);
//                    }
//
//                    // badlo the positions and board
//                    pieces[piece.getRow()][piece.getCol()] = null;
//                    piece.move(newRow, newCol);
//                    pieces[newRow][newCol] = piece;
//
//                    // Move the piece's image to the new position
//                    toOrginal(piece);
//                    count++;
//                    System.out.println(count);
//
//                    // Check for checkmate
//                    if (isCheckmate(piece.isWhite())) {
//                        // Display checkmate message and end the game
//                        displayCheckmateMessage(piece.isWhite());
//                    }
//                } else {
//                    toOrginal(piece);
//                }
//            } else {
//                toOrginal(piece);
//            }
//        } else {
//            // Display "Not Your Move" message
//            displayNotYourMoveMessage();
//            toOrginal(piece);
//        }
//    }
//
//    private boolean isCheckmate(boolean isWhite) {
//        // Iterate over all the pieces of the current player
//        for (int row = 0; row < BOARD_SIZE; row++) {
//            for (int col = 0; col < BOARD_SIZE; col++) {
//                Piece piece = pieces[row][col];
//                if (piece != null && piece.isWhite() == isWhite) {
//                    // Check if any valid move can get the player out of check
//                    for (int i = 0; i < BOARD_SIZE; i++) {
//                        for (int j = 0; j < BOARD_SIZE; j++) {
//                            if (piece.isValidMove(i, j, pieces[i][j]) && !uperSe(piece.getRow(), piece.getCol(), i, j)) {
//                                return false; // Player is not in checkmate
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        return true; // Player is in checkmate
//    }
//
//    private void displayCheckmateMessage(boolean isWhite) {
//        String color = isWhite ? "White" : "Black";
//        Alert alert = new Alert(AlertType.INFORMATION);
//        alert.setTitle("Checkmate!");
//        alert.setHeaderText(color + " is in checkmate.");
//        alert.setContentText("Game Over!");
//
//        // Show the alert
//        alert.show();
//    }
//
//    private void displayNotYourMoveMessage() {
//        Alert alert = new Alert(AlertType.INFORMATION);
//        alert.setTitle("Invalid Move");
//        alert.setHeaderText("Not Your Move!");
//        alert.setContentText("Pass Please.");
//
//        // Show the alert
//        alert.show();
//    }

    // ...


int count=2;
    private void movePiece(Piece piece, int newRow, int newCol) {
        Piece targetPiece = pieces[newRow][newCol];
  
        if((piece.isWhite()==false)&&count%2==0||((piece.isWhite()==true)&&count%2==1)) {
        	
        if (piece.isValidMove(newRow, newCol, targetPiece)) {
            
            if (!uperSe(piece.getRow(), piece.getCol(), newRow, newCol)) {
            
                if (targetPiece != null) {
                    removePiece(targetPiece);
                }

                // badlo the positions and board
                pieces[piece.getRow()][piece.getCol()] = null;
                piece.move(newRow, newCol);
                pieces[newRow][newCol] = piece;

                // Move the piece's image to the new position
                toOrginal( piece);
                count++;
                System.out.println(count);
            }else{      
           	  toOrginal( piece);
        }
            }else {
                //wapis ajaoo
        	  toOrginal( piece);
         }
        }else { 
        	
        	
        	  Alert alert = new Alert(Alert.AlertType.INFORMATION);
              alert.setTitle("Image Alert");
             

              Image image = new Image("file:///C:/Users/Dawood/eclipse-workspace/chess/src/application/New_Project-removebg-preview.png");

              ImageView imageView = new ImageView(image);
              imageView.setFitWidth(200); // Set the desired width of the image
              imageView.setPreserveRatio(true); // Maintain the image's aspect ratio

           
              DialogPane dialogPane = alert.getDialogPane();
              dialogPane.setContent(new GridPane());

              
              GridPane gridPane = (GridPane) dialogPane.getContent();
              gridPane.add(imageView, 0, 0);

              
              alert.show();
              Duration duration = Duration.seconds(1); // Change the duration as per your requirements
              //
                             // Create a timeline to close the alert
                             Timeline timeline = new Timeline(new KeyFrame(duration, e -> alert.close()));
                             timeline.play();

            toOrginal( piece);}

    }
    
    void toOrginal(Piece piece) {
  	  ImageView image = piece.getSquare();
      image.setX(piece.getCol() * SQUARE_SIZE + (SQUARE_SIZE - PIECE_SIZE) / 2);
      image.setY(piece.getRow() * SQUARE_SIZE + (SQUARE_SIZE - PIECE_SIZE) / 2);
    	
    }
    private boolean uperSe(int currentRow, int currentCol, int newRow, int newCol) {
       
        if (pieces[currentRow][currentCol].getType().equals("knights")) {
            return false; 
        }

        int rowDiff = newRow - currentRow;
        int colDiff = newCol - currentCol;

        int rowStep = rowDiff > 0 ? 1 : (rowDiff < 0 ? -1 : 0);
        int colStep = colDiff > 0 ? 1 : (colDiff < 0 ? -1 : 0);

        int row = currentRow + rowStep;
        int col = currentCol + colStep;


        while (row != newRow || col != newCol) {
            if (pieces[row][col] != null) {
                return true; 
            }
            row += rowStep;
            col += colStep;
        }

        return false;}




    private void removePiece(Piece piece) {
        pieces[piece.getRow()][piece.getCol()] = null;
        ImageView image = piece.getSquare();
        root.getChildren().remove(image);
    }


    private Piece findPiece(ImageView image) {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                Piece piece = pieces[row][col];
                if (piece != null && piece.getSquare() == image) {
                    return piece;
                }
            }
        }
        return null;
    }
}
