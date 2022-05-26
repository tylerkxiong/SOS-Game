package src.sprint_5.product;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Objects;
import java.util.Random;
import java.util.Vector;

import static java.lang.Integer.parseInt;

public class GUI extends Application {
    StackPane top = new StackPane();
    TextField boardSizeBox = new TextField();
    StackPane bottom = new StackPane();
    Pane grid = new Pane();
    VBox redPlayer = new VBox();
    VBox bluePlayer = new VBox();
    ToggleGroup toggle = new ToggleGroup();
    RadioButton sCheck = new RadioButton("S");
    RadioButton oCheck = new RadioButton("O");
    RadioButton S2check = new RadioButton("S");
    RadioButton O2check = new RadioButton("O");
    ToggleGroup toggle2 = new ToggleGroup();
    Vector<Cell> Cells = new Vector<>();
    ToggleButton start = new ToggleButton("Start Game");
    Text bottomPrompt = new Text();
    Text endStatusPrompt = new Text();
    RadioButton simpleGame = new RadioButton("Simple Game");
    RadioButton generalGame = new RadioButton("General Game");
    ToggleGroup toggleGroup = new ToggleGroup();
    RadioButton Human = new RadioButton("Human");
    RadioButton Computer = new RadioButton("Computer");
    ToggleGroup aiOrHuman = new ToggleGroup();
    RadioButton Human2 = new RadioButton("Human");
    RadioButton Computer2 = new RadioButton("Computer");
    ToggleGroup aiOrHuman2 = new ToggleGroup();
    Random indexX = new Random();
    Random indexY = new Random();
    Random sor = new Random();
    CheckBox record = new CheckBox("Record Game");

    int in = 1;

    int redLines = 0;
    int blueLines = 0;

    public enum GameState {
        PLAYING, DRAW, RED_WIN, BLUE_WIN
    }

    private GameState currentGameState;

    public static class Cell {
        public final int x;
        public final int y;
        public boolean filled = false;
        public final char SorO;
        public Cell(int x, int y, char sorO) {
            this.x = x;
            this.y = y;
            this.SorO = sorO;
        }
        public String getIndex() {
            return String.format("%d, %d", x, y);
        }
        public Character getSorO(){
            return SorO;
        }

        public void setIsFilled() {
            filled = true;
        }
    }

    public void topMenu() {
        boardSizeBox.setText("3");
        boardSizeBox.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                boardSizeBox.setText(newValue.replaceAll("[^\\d]", ""));
            }
            if (parseInt(newValue) > 8) {
                boardSizeBox.setText("8");
            }
            if(parseInt(newValue) < 3) {
                boardSizeBox.setText("3");
            }
            bottomPrompt.setVisible(false);
            endStatusPrompt.setVisible(false);
            Cells.removeAllElements();
            start.setSelected(false);
        });
        boardSizeBox.setTranslateX(300);
        boardSizeBox.setTranslateY(10);
        boardSizeBox.setPrefWidth(20);
        boardSizeBox.setMaxWidth(80);
        Text SOS = new Text("SOS");
        SOS.setTranslateX(-300);
        SOS.setTranslateY(10);
        Text boardSizeText = new Text("Board Size:");
        boardSizeText.setTranslateX(228);
        boardSizeText.setTranslateY(10);

        simpleGame.setToggleGroup(toggleGroup);
        generalGame.setToggleGroup(toggleGroup);
        simpleGame.setSelected(true);
        simpleGame.setTranslateX(-90);
        simpleGame.setTranslateY(10);
        generalGame.setTranslateX(10);
        generalGame.setTranslateY(10);
        top.getChildren().addAll(simpleGame, generalGame, boardSizeBox, boardSizeText, SOS);
    }

    public void bottomMenu(){
        record.setTranslateY(-20);
        record.setTranslateX(-380);
        bottomPrompt.setTranslateY(-20);
        endStatusPrompt.setTranslateY(-20);
        start.setTranslateX(400);
        start.setTranslateY(-20);
        bottom.getChildren().addAll(start, record);
        bottom.getChildren().add(bottomPrompt);
        bottom.getChildren().add(endStatusPrompt);
        endStatusPrompt.setVisible(false);
        bottomPrompt.setVisible(false);

        Text set_board_size = new Text("Set Board Size");
        set_board_size.setTranslateY(-20);
        set_board_size.setVisible(false);
        bottom.getChildren().add(set_board_size);
        start.setOnMouseClicked(mouseEvent -> {
            if(Objects.equals(boardSizeBox.getText(), "")){
                start.setSelected(false);
                set_board_size.setTranslateY(-20);
                set_board_size.setVisible(true);
                set_board_size.setText("Set Board Size");
            }
            if(!Objects.equals(boardSizeBox.getText(), "")){
                start.setSelected(true);
                set_board_size.setText("");
            }
            if(aiOrHuman2.getSelectedToggle() == null || aiOrHuman.getSelectedToggle() ==null){
                start.setSelected(false);
                set_board_size.setVisible(false);
                set_board_size.setText("Select Human or Computer");
                set_board_size.setVisible(true);
            }
        });
    }

    public void drawGrid() {
        int size = 2520;
        int boardSize = parseInt(boardSizeBox.getText());
        int cellSize = size/boardSize;
        for(int i = 0; i < size; i+= cellSize) {
            for (int j = 0; j < size; j += cellSize){
                Rectangle r = new Rectangle(i, j, cellSize, cellSize);
                r.setStroke(Color.BLACK);
                r.setFill(Color.WHITE);
                grid.getChildren().add(r);
            }
        }
        boardSizeBox.textProperty().addListener(e -> {
            System.out.printf("\n\n\n\n\n\n%s\n\n\n\n\n", boardSizeBox.getCharacters());
            int size1 = 2520;
            int boardSize1 = parseInt(boardSizeBox.getText());
            int cellSize1 = size1 / boardSize1;
            for (int i = 0; i < size1; i += cellSize1) {
                for (int j = 0; j < size1; j += cellSize1) {
                    Rectangle cell = new Rectangle(i, j, cellSize1, cellSize1);
                    cell.setStroke(Color.BLACK);
                    cell.setFill(Color.WHITE);
                    grid.getChildren().add(cell);
                }
            }

        });

        grid.setScaleX(.25);
        grid.setScaleY(.25);
        grid.setTranslateX(-142);
        grid.setTranslateY(-280);
    }

    public void playerChoices(){
        Text p1 = new Text("Red Player");
        p1.setTranslateX(30);
        p1.setTranslateY(300);
        Human.setTranslateX(50);
        Human.setTranslateY(300);
        Computer.setTranslateX(50);
        Computer.setTranslateY(350);
        sCheck.setTranslateX(90);
        oCheck.setTranslateX(90);
        sCheck.setTranslateY(300);
        oCheck.setTranslateY(300);
        Human.setToggleGroup(aiOrHuman);
        Computer.setToggleGroup(aiOrHuman);
        sCheck.setToggleGroup(toggle);
        oCheck.setToggleGroup(toggle);
        Computer.setOnMouseClicked(e -> {
            oCheck.setSelected(false);
            sCheck.setSelected(false);
            oCheck.setDisable(true);
            sCheck.setDisable(true);
        });
        Human.setOnMouseClicked(e -> {
            oCheck.setDisable(false);
            sCheck.setDisable(false);
        });
        redPlayer.getChildren().addAll(p1, Human, sCheck, oCheck, Computer);

        Text p2 = new Text("Blue Player");
        p2.setTranslateX(-30);
        p2.setTranslateY(300);
        Human2.setTranslateX(-50);
        Human2.setTranslateY(300);
        Computer2.setTranslateX(-50);
        Computer2.setTranslateY(350);
        S2check.setTranslateX(-90);
        O2check.setTranslateX(-90);
        S2check.setTranslateY(300);
        O2check.setTranslateY(300);
        Human2.setToggleGroup(aiOrHuman2);
        Computer2.setToggleGroup(aiOrHuman2);
        S2check.setToggleGroup(toggle2);
        O2check.setToggleGroup(toggle2);
        Computer2.setOnMouseClicked(e -> {
            O2check.setSelected(false);
            S2check.setSelected(false);
            O2check.setDisable(true);
            S2check.setDisable(true);
        });
        Human2.setOnMouseClicked(e -> {
            O2check.setDisable(false);
            S2check.setDisable(false);
        });
        bluePlayer.getChildren().addAll(p2, Human2, S2check, O2check, Computer2);
    }

    public void setupScene(Stage primaryStage){
        BorderPane root = new BorderPane();

        topMenu();
        bottomMenu();
        drawGrid();
        playerChoices();

        root.setBottom(bottom);
        root.setCenter(grid);
        root.setRight(bluePlayer);
        root.setLeft(redPlayer);
        root.setTop(top);
        Scene scene = new Scene(root, 1400, 900, Color.ORANGE);

        primaryStage.setTitle("SOS!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void checkForSOS() {
        int size = 2520;
        int boardSize = parseInt(boardSizeBox.getText());
        int cellSize = size / boardSize;
        Cell currentCell = Cells.get(Cells.size() - 1);
        char lastCellPlaced = Cells.get(Cells.size() - 1).SorO;
        if (lastCellPlaced == 'S') {
            for (int i = 0; i < Cells.size() - 1; ++i) {
                if (currentCell.x - 1 == Cells.get(i).x && currentCell.y - 1 == Cells.get(i).y) {
                    if (Cells.get(i).getSorO() == 'O') {
                        for (int j = 0; j < Cells.size() - 1; ++j) {
                            if (currentCell.x - 2 == Cells.get(j).x && currentCell.y - 2 == Cells.get(j).y) {
                                if (Cells.get(j).getSorO() == 'S') {
                                    if (bottomPrompt.getText() == "Red Player's Turn") {
                                        redLines++;
                                        Line line = new Line();
                                        line.setStroke(Color.RED);
                                        line.setStrokeWidth(20);
                                        drawLine line1 = new drawLine((cellSize / 2 + cellSize * currentCell.x), (cellSize / 2 + cellSize * currentCell.y), (cellSize / 2 + cellSize * Cells.get(j).x), (cellSize / 2 + cellSize * Cells.get(j).y), line);
                                        line1.draw();
                                        grid.getChildren().add(line);
                                        currentGameState = GameState.RED_WIN;
                                    }
                                    if (bottomPrompt.getText() == "Blue Player's Turn") {
                                        blueLines++;
                                        Line line = new Line();
                                        line.setStroke(Color.BLUE);
                                        line.setStrokeWidth(20);
                                        drawLine line1 = new drawLine((cellSize / 2 + cellSize * currentCell.x), (cellSize / 2 + cellSize * currentCell.y), (cellSize / 2 + cellSize * Cells.get(j).x), (cellSize / 2 + cellSize * Cells.get(j).y), line);
                                        line1.draw();
                                        grid.getChildren().add(line);
                                        currentGameState = GameState.BLUE_WIN;
                                    }
                                }
                            }
                        }
                    }
                } //\
                if (currentCell.x - 1 == Cells.get(i).x && currentCell.y == Cells.get(i).y) {
                    if (Cells.get(i).getSorO() == 'O') {
                        for (int j = 0; j < Cells.size() - 1; ++j) {
                            if (currentCell.x - 2 == Cells.get(j).x && currentCell.y == Cells.get(j).y) {
                                if (Cells.get(j).getSorO() == 'S') {
                                    if (bottomPrompt.getText() == "Red Player's Turn") {
                                        redLines++;
                                        Line line = new Line();
                                        line.setStroke(Color.RED);
                                        line.setStrokeWidth(20);
                                        drawLine line1 = new drawLine((cellSize / 2 + cellSize * currentCell.x), (cellSize / 2 + cellSize * currentCell.y), (cellSize / 2 + cellSize * Cells.get(j).x), (cellSize / 2 + cellSize * Cells.get(j).y), line);
                                        line1.draw();
                                        grid.getChildren().add(line);
                                        currentGameState = GameState.RED_WIN;
                                    }
                                    if (bottomPrompt.getText() == "Blue Player's Turn") {
                                        blueLines++;
                                        Line line = new Line();
                                        line.setStroke(Color.BLUE);
                                        line.setStrokeWidth(20);
                                        drawLine line1 = new drawLine((cellSize / 2 + cellSize * currentCell.x), (cellSize / 2 + cellSize * currentCell.y), (cellSize / 2 + cellSize * Cells.get(j).x), (cellSize / 2 + cellSize * Cells.get(j).y), line);
                                        line1.draw();
                                        grid.getChildren().add(line);
                                        currentGameState = GameState.BLUE_WIN;
                                    }
                                }
                            }
                        }
                    }
                }   //-
                if (currentCell.x - 1 == Cells.get(i).x && currentCell.y + 1 == Cells.get(i).y) {
                    if (Cells.get(i).getSorO() == 'O') {
                        for (int j = 0; j < Cells.size() - 1; ++j) {
                            if (currentCell.x - 2 == Cells.get(j).x && currentCell.y + 2 == Cells.get(j).y) {
                                if (Cells.get(j).getSorO() == 'S') {
                                    if (bottomPrompt.getText() == "Red Player's Turn") {
                                        redLines++;
                                        Line line = new Line();
                                        line.setStroke(Color.RED);
                                        line.setStrokeWidth(20);
                                        drawLine line1 = new drawLine((cellSize / 2 + cellSize * currentCell.x), (cellSize / 2 + cellSize * currentCell.y), (cellSize / 2 + cellSize * Cells.get(j).x), (cellSize / 2 + cellSize * Cells.get(j).y), line);
                                        line1.draw();
                                        grid.getChildren().add(line);
                                        currentGameState = GameState.RED_WIN;
                                    }
                                    if (bottomPrompt.getText() == "Blue Player's Turn") {
                                        blueLines++;
                                        Line line = new Line();
                                        line.setStroke(Color.BLUE);
                                        line.setStrokeWidth(20);
                                        drawLine line1 = new drawLine((cellSize / 2 + cellSize * currentCell.x), (cellSize / 2 + cellSize * currentCell.y), (cellSize / 2 + cellSize * Cells.get(j).x), (cellSize / 2 + cellSize * Cells.get(j).y), line);
                                        line1.draw();
                                        grid.getChildren().add(line);
                                        currentGameState = GameState.BLUE_WIN;
                                    }
                                }
                            }
                        }
                    }
                }  ///
                if (currentCell.x == Cells.get(i).x && currentCell.y - 1 == Cells.get(i).y) {
                    if (Cells.get(i).getSorO() == 'O') {
                        for (int j = 0; j < Cells.size() - 1; ++j) {
                            if (currentCell.x == Cells.get(j).x && currentCell.y - 2 == Cells.get(j).y) {
                                if (Cells.get(j).getSorO() == 'S') {
                                    if (bottomPrompt.getText() == "Red Player's Turn") {
                                        redLines++;
                                        Line line = new Line();
                                        line.setStroke(Color.RED);
                                        line.setStrokeWidth(20);
                                        drawLine line1 = new drawLine((cellSize / 2 + cellSize * currentCell.x), (cellSize / 2 + cellSize * currentCell.y), (cellSize / 2 + cellSize * Cells.get(j).x), (cellSize / 2 + cellSize * Cells.get(j).y), line);
                                        line1.draw();
                                        grid.getChildren().add(line);
                                        currentGameState = GameState.RED_WIN;
                                    }
                                    if (bottomPrompt.getText() == "Blue Player's Turn") {
                                        blueLines++;
                                        Line line = new Line();
                                        line.setStroke(Color.BLUE);
                                        line.setStrokeWidth(20);
                                        drawLine line1 = new drawLine((cellSize / 2 + cellSize * currentCell.x), (cellSize / 2 + cellSize * currentCell.y), (cellSize / 2 + cellSize * Cells.get(j).x), (cellSize / 2 + cellSize * Cells.get(j).y), line);
                                        line1.draw();
                                        grid.getChildren().add(line);
                                        currentGameState = GameState.BLUE_WIN;
                                    }
                                }
                            }
                        }
                    }
                }       //|
                if (currentCell.x + 1 == Cells.get(i).x && currentCell.y + 1 == Cells.get(i).y) {
                    if (Cells.get(i).getSorO() == 'O') {
                        for (int j = 0; j < Cells.size() - 1; ++j) {
                            if (currentCell.x + 2 == Cells.get(j).x && currentCell.y + 2 == Cells.get(j).y) {
                                if (Cells.get(j).getSorO() == 'S') {
                                    if (bottomPrompt.getText() == "Red Player's Turn") {
                                        redLines++;
                                        Line line = new Line();
                                        line.setStroke(Color.RED);
                                        line.setStrokeWidth(20);
                                        drawLine line1 = new drawLine((cellSize / 2 + cellSize * currentCell.x), (cellSize / 2 + cellSize * currentCell.y), (cellSize / 2 + cellSize * Cells.get(j).x), (cellSize / 2 + cellSize * Cells.get(j).y), line);
                                        line1.draw();
                                        grid.getChildren().add(line);
                                        currentGameState = GameState.RED_WIN;
                                    }
                                    if (bottomPrompt.getText() == "Blue Player's Turn") {
                                        blueLines++;
                                        Line line = new Line();
                                        line.setStroke(Color.BLUE);
                                        line.setStrokeWidth(20);
                                        drawLine line1 = new drawLine((cellSize / 2 + cellSize * currentCell.x), (cellSize / 2 + cellSize * currentCell.y), (cellSize / 2 + cellSize * Cells.get(j).x), (cellSize / 2 + cellSize * Cells.get(j).y), line);
                                        line1.draw();
                                        grid.getChildren().add(line);
                                        currentGameState = GameState.BLUE_WIN;
                                    }
                                }
                            }
                        }
                    }
                }   ///
                if (currentCell.x == Cells.get(i).x && currentCell.y + 1 == Cells.get(i).y) {
                    if (Cells.get(i).getSorO() == 'O') {
                        for (int j = 0; j < Cells.size() - 1; ++j) {
                            if (currentCell.x == Cells.get(j).x && currentCell.y + 2 == Cells.get(j).y) {
                                if (Cells.get(j).getSorO() == 'S') {
                                    if (bottomPrompt.getText() == "Red Player's Turn") {
                                        redLines++;
                                        Line line = new Line();
                                        line.setStroke(Color.RED);
                                        line.setStrokeWidth(20);
                                        drawLine line1 = new drawLine((cellSize / 2 + cellSize * currentCell.x), (cellSize / 2 + cellSize * currentCell.y), (cellSize / 2 + cellSize * Cells.get(j).x), (cellSize / 2 + cellSize * Cells.get(j).y), line);
                                        line1.draw();
                                        grid.getChildren().add(line);
                                        currentGameState = GameState.RED_WIN;
                                    }
                                    if (bottomPrompt.getText() == "Blue Player's Turn") {
                                        blueLines++;
                                        Line line = new Line();
                                        line.setStroke(Color.BLUE);
                                        line.setStrokeWidth(20);
                                        drawLine line1 = new drawLine((cellSize / 2 + cellSize * currentCell.x), (cellSize / 2 + cellSize * currentCell.y), (cellSize / 2 + cellSize * Cells.get(j).x), (cellSize / 2 + cellSize * Cells.get(j).y), line);
                                        line1.draw();
                                        grid.getChildren().add(line);
                                        currentGameState = GameState.BLUE_WIN;
                                    }
                                }
                            }
                        }
                    }
                }   //|
                if (currentCell.x + 1 == Cells.get(i).x && currentCell.y - 1 == Cells.get(i).y) {
                    if (Cells.get(i).getSorO() == 'O') {
                        for (int j = 0; j < Cells.size() - 1; ++j) {
                            if (currentCell.x + 2 == Cells.get(j).x && currentCell.y - 2 == Cells.get(j).y) {
                                if (Cells.get(j).getSorO() == 'S') {
                                    if (bottomPrompt.getText() == "Red Player's Turn") {
                                        redLines++;
                                        Line line = new Line();
                                        line.setStroke(Color.RED);
                                        line.setStrokeWidth(20);
                                        drawLine line1 = new drawLine((cellSize / 2 + cellSize * currentCell.x), (cellSize / 2 + cellSize * currentCell.y), (cellSize / 2 + cellSize * Cells.get(j).x), (cellSize / 2 + cellSize * Cells.get(j).y), line);
                                        line1.draw();
                                        grid.getChildren().add(line);
                                        currentGameState = GameState.RED_WIN;
                                    }
                                    if (bottomPrompt.getText() == "Blue Player's Turn") {
                                        blueLines++;
                                        Line line = new Line();
                                        line.setStroke(Color.BLUE);
                                        line.setStrokeWidth(20);
                                        drawLine line1 = new drawLine((cellSize / 2 + cellSize * currentCell.x), (cellSize / 2 + cellSize * currentCell.y), (cellSize / 2 + cellSize * Cells.get(j).x), (cellSize / 2 + cellSize * Cells.get(j).y), line);
                                        line1.draw();
                                        grid.getChildren().add(line);
                                        currentGameState = GameState.BLUE_WIN;
                                    }
                                }
                            }
                        }
                    }
                }   //-
                if (currentCell.x + 1 == Cells.get(i).x && currentCell.y == Cells.get(i).y) {
                    if (Cells.get(i).getSorO() == 'O') {
                        for (int j = 0; j < Cells.size() - 1; ++j) {
                            if (currentCell.x + 2 == Cells.get(j).x && currentCell.y == Cells.get(j).y) {
                                if (Cells.get(j).getSorO() == 'S') {
                                    if (bottomPrompt.getText() == "Red Player's Turn") {
                                        redLines++;
                                        Line line = new Line();
                                        line.setStroke(Color.RED);
                                        line.setStrokeWidth(20);
                                        drawLine line1 = new drawLine((cellSize / 2 + cellSize * currentCell.x), (cellSize / 2 + cellSize * currentCell.y), (cellSize / 2 + cellSize * Cells.get(j).x), (cellSize / 2 + cellSize * Cells.get(j).y), line);
                                        line1.draw();
                                        grid.getChildren().add(line);
                                        currentGameState = GameState.RED_WIN;
                                    }
                                    if (bottomPrompt.getText() == "Blue Player's Turn") {
                                        blueLines++;
                                        Line line = new Line();
                                        line.setStroke(Color.BLUE);
                                        line.setStrokeWidth(20);
                                        drawLine line1 = new drawLine((cellSize / 2 + cellSize * currentCell.x), (cellSize / 2 + cellSize * currentCell.y), (cellSize / 2 + cellSize * Cells.get(j).x), (cellSize / 2 + cellSize * Cells.get(j).y), line);
                                        line1.draw();
                                        grid.getChildren().add(line);
                                        currentGameState = GameState.BLUE_WIN;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void checkForWin(int boardSize) {

        if (Cells.size() == boardSize * boardSize && blueLines == 0 && redLines == 0) {
            currentGameState = GameState.DRAW;
            bottomPrompt.setVisible(false);
            endStatusPrompt.setText("Draw!");
            endStatusPrompt.setVisible(true);
        }
        if (toggleGroup.getSelectedToggle() == simpleGame) {
            if (currentGameState == GameState.RED_WIN) {
                bottomPrompt.setVisible(false);
                endStatusPrompt.setText("Red Player Wins!");
                endStatusPrompt.setVisible(true);
                recordGame();
            }
            if (currentGameState == GameState.BLUE_WIN) {
                bottomPrompt.setVisible(false);
                endStatusPrompt.setText("Blue Player Wins!");
                endStatusPrompt.setVisible(true);
                recordGame();
            }
            if (in == 0 && currentGameState == GameState.PLAYING) {
                if (bottomPrompt.getText() == "Red Player's Turn") {
                    bottomPrompt.setText("Blue Player's Turn");
                    bluePlayerTurn();
                } else {
                    bottomPrompt.setText("Red Player's Turn");
                    redPlayerTurn();
                }
            }
            if(in > 0 && currentGameState == GameState.PLAYING && aiOrHuman.getSelectedToggle() == Computer && bottomPrompt.getText() == "Red Player's Turn"){
                redPlayerTurn();
            }
            if(in > 0 && currentGameState == GameState.PLAYING && aiOrHuman2.getSelectedToggle() == Computer2 && bottomPrompt.getText() == "Blue Player's Turn"){
                bluePlayerTurn();
            }

        }
        if (toggleGroup.getSelectedToggle() == generalGame) {
            if (currentGameState == GameState.RED_WIN && Cells.size() != boardSize * boardSize) {
                currentGameState = GameState.PLAYING;
            }
            if (currentGameState == GameState.BLUE_WIN && Cells.size() != boardSize * boardSize) {
                currentGameState = GameState.PLAYING;
            }
            if (Cells.size() == boardSize * boardSize) {
                if (redLines > blueLines) {
                    currentGameState = GameState.RED_WIN;
                    bottomPrompt.setVisible(false);
                    endStatusPrompt.setText("Red Player Wins!");
                    endStatusPrompt.setVisible(true);
                    System.out.printf("Red: %d \nBlue: %d", redLines, blueLines);
                    recordGame();
                }
                if (blueLines > redLines) {
                    currentGameState = GameState.BLUE_WIN;
                    bottomPrompt.setVisible(false);
                    endStatusPrompt.setText("Blue Player Wins!");
                    endStatusPrompt.setVisible(true);
                    System.out.printf("Red: %d \nBlue: %d", redLines, blueLines);
                    recordGame();
                }
                if (blueLines == redLines) {
                    currentGameState = GameState.DRAW;
                    bottomPrompt.setVisible(false);
                    endStatusPrompt.setText("Draw!");
                    endStatusPrompt.setVisible(true);
                    System.out.printf("Red: %d \nBlue: %d", redLines, blueLines);
                    recordGame();
                }
            } else {
                if (in == 0) {
                    if (bottomPrompt.getText() == "Red Player's Turn") {
                        bottomPrompt.setText("Blue Player's Turn");
                        bluePlayerTurn();
                    }
                    else {
                        bottomPrompt.setText("Red Player's Turn");
                        redPlayerTurn();
                    }
                }
            }
        }
    }

    public void redPlayerTurn() {
        int size = 2520;
        int boardSize = parseInt(boardSizeBox.getText());
        int cellSize = size / boardSize;
        if (aiOrHuman.getSelectedToggle() == Human) {
            grid.setOnMouseClicked(mouseEvent -> {
                if (currentGameState == GameState.PLAYING) {
                    int gridX = (int) mouseEvent.getX() / cellSize;
                    int gridY = (int) mouseEvent.getY() / cellSize;
                    if (toggle.getSelectedToggle() == sCheck) {
                        Text s = new Text("S");
                        s.setScaleX(cellSize / 11);
                        s.setScaleY(cellSize / 11);
                        shapeDraw sShape = new shapeDraw((cellSize / 2 + cellSize * gridX), (cellSize / 2 + cellSize * gridY), s);
                        sShape.draw();


                        if (gridY < boardSize) {
                            Cell cell = new Cell(gridX, gridY, 'S');
                            Cells.add(cell);


                            for (int i = 0; i < Cells.size() - 1; ++i) {
                                if (Cells.get(i).x == gridX && Cells.get(i).y == gridY) {
                                    ++in;
                                    Cells.remove(Cells.size() - 1);
                                    cell.setIsFilled();
                                }
                            }
                            if (!cell.filled) {
                                grid.getChildren().add(s);
                                in = 0;
                                checkForSOS();
                                checkForWin(boardSize);
                            }
                        }
                    }
                    if (toggle.getSelectedToggle() == oCheck) {
                        Text o = new Text("O");
                        o.setScaleX(cellSize / 11);
                        o.setScaleY(cellSize / 11);
                        shapeDraw sShape = new shapeDraw((cellSize / 2 + cellSize * gridX), (cellSize / 2 + cellSize * gridY), o);
                        sShape.draw();
                        if (gridY < boardSize) {
                            Cell cell = new Cell(gridX, gridY, 'O');
                            Cells.add(cell);

                            for (int i = 0; i < Cells.size() - 1; ++i) {
                                if (Cells.get(i).x == gridX && Cells.get(i).y == gridY) {
                                    ++in;
                                    Cells.remove(Cells.size() - 1);
                                    cell.setIsFilled();
                                }
                            }
                            if (!cell.filled) {
                                grid.getChildren().add(o);
                                in = 0;
                                checkForSOS();
                                checkForWin(boardSize);
                            }
                        }
                    }

                }
            });
        }
        if (aiOrHuman.getSelectedToggle() == Computer) {
            if (currentGameState == GameState.PLAYING) {
                PauseTransition wait = new PauseTransition(Duration.millis(80));
                wait.setOnFinished(e -> {
                    int iy = indexY.nextInt(boardSize);
                    int ix = indexX.nextInt(boardSize);
                    int so = sor.nextInt(2);
                    if (so == 0) {
                        Text s = new Text("S");
                        s.setScaleX(cellSize / 11);
                        s.setScaleY(cellSize / 11);
                        shapeDraw sShape = new shapeDraw((cellSize / 2 + cellSize * ix), (cellSize / 2 + cellSize * iy), s);
                        sShape.draw();

                        if (iy < boardSize) {
                            Cell cell = new Cell(ix, iy, 'S');
                            Cells.add(cell);


                            for (int i = 0; i < Cells.size() - 1; ++i) {
                                if (Cells.get(i).x == ix && Cells.get(i).y == iy) {
                                    ++in;
                                    Cells.remove(Cells.size() - 1);
                                    cell.setIsFilled();
                                }
                            }
                            if (!cell.filled) {
                                grid.getChildren().add(s);
                                in = 0;
                                checkForSOS();
                                checkForWin(boardSize);
                            }
                        }
                        if(in > 0 && currentGameState == GameState.PLAYING && aiOrHuman.getSelectedToggle() == Computer && bottomPrompt.getText() == "Red Player's Turn"){
                            redPlayerTurn();
                        }
                    }
                    if (so == 1) {
                        Text s = new Text("O");
                        s.setScaleX(cellSize / 11);
                        s.setScaleY(cellSize / 11);
                        shapeDraw sShape = new shapeDraw((cellSize / 2 + cellSize * ix), (cellSize / 2 + cellSize * iy), s);
                        sShape.draw();

                        if (iy < boardSize) {
                            Cell cell = new Cell(ix, iy, 'O');
                            Cells.add(cell);


                            for (int i = 0; i < Cells.size() - 1; ++i) {
                                if (Cells.get(i).x == ix && Cells.get(i).y == iy) {
                                    ++in;
                                    Cells.remove(Cells.size() - 1);
                                    cell.setIsFilled();
                                }
                            }
                            if (!cell.filled) {
                                grid.getChildren().add(s);
                                in = 0;
                                checkForSOS();
                                checkForWin(boardSize);
                            }
                        }
                        if(in > 0 && currentGameState == GameState.PLAYING && aiOrHuman.getSelectedToggle() == Computer && bottomPrompt.getText() == "Red Player's Turn"){
                            redPlayerTurn();
                        }
                    }
                });
                wait.play();
            }
        }
    }

    public void bluePlayerTurn() {
        int size = 2520;
        int boardSize = parseInt(boardSizeBox.getText());
        int cellSize = size / boardSize;
        if (aiOrHuman2.getSelectedToggle() == Human2) {
            grid.setOnMouseClicked(mouseEvent -> {
                if (currentGameState == GameState.PLAYING) {
                    int gridX = (int) mouseEvent.getX() / cellSize;
                    int gridY = (int) mouseEvent.getY() / cellSize;
                    if (toggle2.getSelectedToggle() == S2check) {
                        Text s = new Text("S");
                        s.setScaleX(cellSize / 11);
                        s.setScaleY(cellSize / 11);
                        shapeDraw sShape = new shapeDraw((cellSize / 2 + cellSize * gridX), (cellSize / 2 + cellSize * gridY), s);
                        sShape.draw();
                        if (gridY < boardSize) {
                            Cell cell = new Cell(gridX, gridY, 'S');
                            Cells.add(cell);

                            for (int i = 0; i < Cells.size() - 1; ++i) {
                                if (Cells.get(i).x == gridX && Cells.get(i).y == gridY) {
                                    ++in;
                                    Cells.remove(Cells.size() - 1);
                                    cell.setIsFilled();
                                }
                            }
                            if (!cell.filled) {
                                grid.getChildren().add(s);
                                in = 0;
                                checkForSOS();
                                checkForWin(boardSize);
                            }
                        }
                    }
                    if (toggle2.getSelectedToggle() == O2check) {
                        Text o = new Text("O");
                        //noinspection IntegerDivisionInFloatingPointContext
                        o.setScaleX(cellSize / 11);
                        o.setScaleY(cellSize / 11);
                        shapeDraw sShape = new shapeDraw((cellSize / 2 + cellSize * gridX), (cellSize / 2 + cellSize * gridY), o);
                        sShape.draw();
                        if (gridY < boardSize) {
                            Cell cell = new Cell(gridX, gridY, 'O');
                            Cells.add(cell);

                            for (int i = 0; i < Cells.size() - 1; ++i) {
                                if (Cells.get(i).x == gridX && Cells.get(i).y == gridY) {
                                    ++in;
                                    Cells.remove(Cells.size() - 1);
                                    cell.setIsFilled();
                                }
                            }
                            if (!cell.filled) {
                                grid.getChildren().add(o);
                                in = 0;
                                checkForSOS();
                                checkForWin(boardSize);
                            }
                        }
                    }

                }
            });
        }
        if (aiOrHuman2.getSelectedToggle() == Computer2) {
            if (currentGameState == GameState.PLAYING) {
                PauseTransition wait = new PauseTransition(Duration.millis(80));
                wait.setOnFinished(e -> {
                    int iy = indexY.nextInt(boardSize);
                    int ix = indexX.nextInt(boardSize);
                    int so = sor.nextInt(2);
                    if (so == 0) {
                        Text s = new Text("S");
                        s.setScaleX(cellSize / 11);
                        s.setScaleY(cellSize / 11);
                        shapeDraw sShape = new shapeDraw((cellSize / 2 + cellSize * ix), (cellSize / 2 + cellSize * iy), s);
                        sShape.draw();
                        if (iy < boardSize) {
                            Cell cell = new Cell(ix, iy, 'S');
                            Cells.add(cell);

                            for (int i = 0; i < Cells.size() - 1; ++i) {
                                if (Cells.get(i).x == ix && Cells.get(i).y == iy) {
                                    ++in;
                                    Cells.remove(Cells.size() - 1);
                                    cell.setIsFilled();
                                }
                            }
                            if (!cell.filled) {
                                grid.getChildren().add(s);
                                in = 0;
                                checkForSOS();
                                checkForWin(boardSize);
                            }
                        }
                        if(in > 0 && currentGameState == GameState.PLAYING && aiOrHuman2.getSelectedToggle() == Computer2 && bottomPrompt.getText() == "Blue Player's Turn"){
                            bluePlayerTurn();
                        }
                    }
                    if(so == 1){
                        Text s = new Text("O");
                        s.setScaleX(cellSize / 11);
                        s.setScaleY(cellSize / 11);
                        shapeDraw sShape = new shapeDraw((cellSize / 2 + cellSize * ix), (cellSize / 2 + cellSize * iy), s);
                        sShape.draw();

                        if (iy < boardSize) {
                            Cell cell = new Cell(ix, iy, 'O');
                            Cells.add(cell);


                            for (int i = 0; i < Cells.size() - 1; ++i) {
                                if (Cells.get(i).x == ix && Cells.get(i).y == iy) {
                                    ++in;
                                    Cells.remove(Cells.size() - 1);
                                    cell.setIsFilled();
                                }
                            }
                            if (!cell.filled) {

                                grid.getChildren().add(s);
                                in = 0;
                                checkForSOS();
                                checkForWin(boardSize);
                            }
                        }
                        if(in > 0 && currentGameState == GameState.PLAYING && aiOrHuman2.getSelectedToggle() == Computer2 && bottomPrompt.getText() == "Blue Player's Turn"){
                            bluePlayerTurn();
                        }
                    }
                });
                wait.play();
            }
        }
    }

    public static class shapeDraw {
        private double x, y;
        private Text s;
        public shapeDraw(double x, double y, Text s){
            this.x = x;
            this.y =y;
            this.s = s;
        }

        public void draw(){
            s.setTranslateX(x);
            s.setTranslateY(y);
        }

    }

    public static class drawLine {
        private double x, y, x2, y2;
        private Line line;

        public drawLine(double x, double y, double x2, double y2, Line line){
            this.x = x;
            this.y = y;
            this.x2 = x2;
            this.y2 =y2;
            this.line = line;
        }

        public void draw(){
            line.setStartX(x);
            line.setStartY(y);
            line.setEndX(x2);
            line.setEndY(y2);
        }
    }

    public void recordGame() {
        if(record.isSelected()){
            try {
                PrintStream console = new PrintStream("Recorded Game.txt");
                System.setOut(console);
                for (int i = 0; i < Cells.size(); ++i) {
                    if ((i + 1) % 2 == 0) {
                        console.printf("%d. Blue Player played %s, which is %s. \n", i+1,Cells.get(i).getIndex(), Cells.get(i).getSorO());
                    }
                    else {
                        console.printf("%d. Red Player played %s, which is %s. \n", i+1, Cells.get(i).getIndex(), Cells.get(i).getSorO());
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void start(Stage primaryStage) {
        start.setOnAction(actionEvent -> {
            currentGameState = GameState.PLAYING;
            redLines = 0;
            blueLines = 0;
            if (start.isSelected()) {
                if (!(aiOrHuman2.getSelectedToggle() == null) || !(aiOrHuman.getSelectedToggle() == null)) {
                    bottomPrompt.setText("Red Player's Turn");
                    bottomPrompt.setVisible(true);
                    redPlayerTurn();
                }
            }
        });
        setupScene(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}