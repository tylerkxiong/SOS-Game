package src.sprint_1;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Objects;
import java.util.Vector;

import static java.lang.Integer.parseInt;


public class GUI extends Application {
    StackPane top = new StackPane();
    TextField numrandc = new TextField();
    StackPane bottom = new StackPane();
    Pane grid = new Pane();
    VBox redPplayer = new VBox();
    VBox bluePlayer = new VBox();
    ToggleGroup toggle = new ToggleGroup();
    RadioButton Scheck = new RadioButton("S");
    RadioButton Ocheck = new RadioButton("O");
    RadioButton S2check = new RadioButton("S");
    RadioButton O2check = new RadioButton("O");
    ToggleGroup toggle2 = new ToggleGroup();
    Vector<Cell> Cells = new Vector<Cell>();
    ToggleButton start = new ToggleButton("Start Game");
    Text bottomPrompt = new Text();
    Text endstatusPrompt = new Text();
    RadioButton simpleGame = new RadioButton("Simple Game");
    RadioButton generalGame = new RadioButton("General Game");
    ToggleGroup toggleGroup = new ToggleGroup();
    RadioButton Human = new RadioButton("Human");
    RadioButton Computer = new RadioButton("Computer");
    ToggleGroup aiorhuman = new ToggleGroup();
    RadioButton Human2 = new RadioButton("Human");
    RadioButton Computer2 = new RadioButton("Computer");
    ToggleGroup aiorhuman2 = new ToggleGroup();

    int redlines = 0;
    int bluelines = 0;


    public enum GameState {
        PLAYING, DRAW, RED_WIN, BLUE_WIN
    }

    private GameState currentGameState;



    public class Cell {
        private int x, y;
        boolean filled = false;
        private char SorO = 'S';
        public Cell(int x, int y, char sorO) {
            this.x = x;
            this.y = y;
            this.SorO = sorO;
        }
        private String getIndex(){
            return String.format("%d, %d", x, y);
        }

        private Character getSorO(){
            return SorO;
        }

        public void setIsFilled() {
            filled = true;
        }
    }

    public void topMenu() {
        //only allows characters into textbox
        numrandc.setText("3");
        numrandc.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    numrandc.setText(newValue.replaceAll("[^\\d]", ""));
                }
                if (parseInt(newValue) > 8) {
                    numrandc.setText("8");
                }
                if(parseInt(newValue) < 3) {
                    numrandc.setText("3");
                }
                bottomPrompt.setVisible(false);
                endstatusPrompt.setVisible(false);
                Cells.removeAllElements();
                start.setSelected(false);
            }

        });
        numrandc.setTranslateX(300);
        numrandc.setTranslateY(10);
        numrandc.setPrefWidth(20);
        numrandc.setMaxWidth(80);
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
        top.getChildren().addAll(simpleGame, generalGame, numrandc, boardSizeText, SOS);
    }

    public void bottomMenu(){
        bottomPrompt.setTranslateY(-20);
        endstatusPrompt.setTranslateY(-20);
        start.setTranslateX(400);
        start.setTranslateY(-20);
        bottom.getChildren().addAll(start);
        bottom.getChildren().add(bottomPrompt);
        bottom.getChildren().add(endstatusPrompt);
        endstatusPrompt.setVisible(false);
        bottomPrompt.setVisible(false);

        Text set_board_size = new Text("Set Board Size");
        set_board_size.setTranslateY(-20);
        set_board_size.setVisible(false);
        bottom.getChildren().add(set_board_size);
        start.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(numrandc.getText()==""){
                    start.setSelected(false);
                    set_board_size.setTranslateY(-20);
                    set_board_size.setVisible(true);
                    set_board_size.setText("Set Board Size");
                }
                if(numrandc.getText()!=""){
                    start.setSelected(true);
                    set_board_size.setText("");
                }
            }
        });
    }

    public void drawGrid() {
        int size = 2520;
        int boardSize = parseInt(numrandc.getText());
        int cellSize = size/boardSize;
        for(int i = 0; i < size; i+= cellSize) {
            for (int j = 0; j < size; j += cellSize){
                Rectangle r = new Rectangle(i, j, cellSize, cellSize);
                r.setStroke(Color.BLACK);
                r.setFill(Color.WHITE);
                grid.getChildren().add(r);
            }
        }
        numrandc.setOnKeyReleased(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                System.out.println(numrandc.getCharacters());
                int size = 2520;
                int boardSize = parseInt(numrandc.getText());
                int cellSize = size/boardSize;
                for(int i = 0; i < size; i+= cellSize) {
                    for (int j = 0; j < size; j += cellSize){
                        Rectangle cell = new Rectangle(i, j, cellSize, cellSize);
                        cell.setStroke(Color.BLACK);
                        cell.setFill(Color.WHITE);
                        grid.getChildren().add(cell);
                    }
                }

            }
        });

        grid.setScaleX(.25);
        grid.setScaleY(.25);
        grid.setTranslateX(-142);
        grid.setTranslateY(-280);
    }

    public void leftrightplayerchoices(){
        Text p1 = new Text("Red Player");
        Human.setTranslateX(50);
        Human.setTranslateY(300);
        Computer.setTranslateX(50);
        Computer.setTranslateY(350);
        p1.setTranslateX(30);
        p1.setTranslateY(300);
        Scheck.setTranslateX(90);
        Ocheck.setTranslateX(90);
        Scheck.setTranslateY(300);
        Ocheck.setTranslateY(300);
        Scheck.setToggleGroup(toggle);
        Ocheck.setToggleGroup(toggle);
        redPplayer.getChildren().addAll(p1, Scheck, Ocheck);

        Text p2 = new Text("Blue Player");
        p2.setTranslateX(-30);
        p2.setTranslateY(300);

        S2check.setTranslateX(-90);
        O2check.setTranslateX(-90);
        S2check.setTranslateY(300);
        O2check.setTranslateY(300);
        S2check.setToggleGroup(toggle2);
        O2check.setToggleGroup(toggle2);
        bluePlayer.getChildren().addAll(p2, S2check, O2check);
    }

    public void setupScene(Stage primaryStage){
        BorderPane root = new BorderPane();

        topMenu();
        bottomMenu();
        drawGrid();
        leftrightplayerchoices();

        root.setBottom(bottom);

        root.setCenter(grid);
        root.setRight(bluePlayer);
        root.setLeft(redPplayer);
        root.setTop(top);
        Scene scene = new Scene(root, 1400, 900, Color.ORANGE);

        primaryStage.setTitle("SOS!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void checkforSOS() {
        int size = 2520;
        int boardSize = parseInt(numrandc.getText());
        int cellSize = size / boardSize;
        Cell currentCell = Cells.get(Cells.size() - 1);
        char cellso = Cells.get(Cells.size() - 1).SorO;
        for (int i = 0; i < Cells.size(); ++i) {
            System.out.printf("%d. Cell's taken: %s, which is %s \n", i, Cells.get(i).getIndex(), Cells.get(i).getSorO());
        }

        if (cellso == 'S') {
            for (int i = 0; i < Cells.size() - 1; ++i) {
                if (currentCell.x - 1 == Cells.get(i).x && currentCell.y - 1 == Cells.get(i).y) {
                    if (Cells.get(i).getSorO() == 'O') {
                        for (int j = 0; j < Cells.size() - 1; ++j) {
                            if (currentCell.x - 2 == Cells.get(j).x && currentCell.y - 2 == Cells.get(j).y) {
                                if (Cells.get(j).getSorO() == 'S') {
                                    if (bottomPrompt.getText() == "Red Player's Turn") {
                                        redlines++;
                                        Line line = new Line();
                                        line.setStroke(Color.RED);
                                        line.setStrokeWidth(20);
                                        drawLine line1 = new drawLine((cellSize / 2 + cellSize * currentCell.x), (cellSize / 2 + cellSize * currentCell.y), (cellSize / 2 + cellSize * Cells.get(j).x), (cellSize / 2 + cellSize * Cells.get(j).y), line);
                                        line1.draw();
                                        grid.getChildren().add(line);
                                        currentGameState = GameState.RED_WIN;
                                    }
                                    if (bottomPrompt.getText() == "Blue Player's Turn") {
                                        bluelines++;
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
                                        redlines++;
                                        Line line = new Line();
                                        line.setStroke(Color.RED);
                                        line.setStrokeWidth(20);
                                        drawLine line1 = new drawLine((cellSize / 2 + cellSize * currentCell.x), (cellSize / 2 + cellSize * currentCell.y), (cellSize / 2 + cellSize * Cells.get(j).x), (cellSize / 2 + cellSize * Cells.get(j).y), line);
                                        line1.draw();
                                        grid.getChildren().add(line);
                                        currentGameState = GameState.RED_WIN;
                                    }
                                    if (bottomPrompt.getText() == "Blue Player's Turn") {
                                        bluelines++;
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
                                        redlines++;
                                        Line line = new Line();
                                        line.setStroke(Color.RED);
                                        line.setStrokeWidth(20);
                                        drawLine line1 = new drawLine((cellSize / 2 + cellSize * currentCell.x), (cellSize / 2 + cellSize * currentCell.y), (cellSize / 2 + cellSize * Cells.get(j).x), (cellSize / 2 + cellSize * Cells.get(j).y), line);
                                        line1.draw();
                                        grid.getChildren().add(line);
                                        currentGameState = GameState.RED_WIN;
                                    }
                                    if (bottomPrompt.getText() == "Blue Player's Turn") {
                                        bluelines++;
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
                                        redlines++;
                                        Line line = new Line();
                                        line.setStroke(Color.RED);
                                        line.setStrokeWidth(20);
                                        drawLine line1 = new drawLine((cellSize / 2 + cellSize * currentCell.x), (cellSize / 2 + cellSize * currentCell.y), (cellSize / 2 + cellSize * Cells.get(j).x), (cellSize / 2 + cellSize * Cells.get(j).y), line);
                                        line1.draw();
                                        grid.getChildren().add(line);
                                        currentGameState = GameState.RED_WIN;
                                    }
                                    if (bottomPrompt.getText() == "Blue Player's Turn") {
                                        bluelines++;
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
                                        redlines++;
                                        Line line = new Line();
                                        line.setStroke(Color.RED);
                                        line.setStrokeWidth(20);
                                        drawLine line1 = new drawLine((cellSize / 2 + cellSize * currentCell.x), (cellSize / 2 + cellSize * currentCell.y), (cellSize / 2 + cellSize * Cells.get(j).x), (cellSize / 2 + cellSize * Cells.get(j).y), line);
                                        line1.draw();
                                        grid.getChildren().add(line);
                                        currentGameState = GameState.RED_WIN;
                                    }
                                    if (bottomPrompt.getText() == "Blue Player's Turn") {
                                        bluelines++;
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
                                        redlines++;
                                        Line line = new Line();
                                        line.setStroke(Color.RED);
                                        line.setStrokeWidth(20);
                                        drawLine line1 = new drawLine((cellSize / 2 + cellSize * currentCell.x), (cellSize / 2 + cellSize * currentCell.y), (cellSize / 2 + cellSize * Cells.get(j).x), (cellSize / 2 + cellSize * Cells.get(j).y), line);
                                        line1.draw();
                                        grid.getChildren().add(line);
                                        currentGameState = GameState.RED_WIN;
                                    }
                                    if (bottomPrompt.getText() == "Blue Player's Turn") {
                                        bluelines++;
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
                                        redlines++;
                                        Line line = new Line();
                                        line.setStroke(Color.RED);
                                        line.setStrokeWidth(20);
                                        drawLine line1 = new drawLine((cellSize / 2 + cellSize * currentCell.x), (cellSize / 2 + cellSize * currentCell.y), (cellSize / 2 + cellSize * Cells.get(j).x), (cellSize / 2 + cellSize * Cells.get(j).y), line);
                                        line1.draw();
                                        grid.getChildren().add(line);
                                        currentGameState = GameState.RED_WIN;
                                    }
                                    if (bottomPrompt.getText() == "Blue Player's Turn") {
                                        bluelines++;
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
                                        redlines++;
                                        Line line = new Line();
                                        line.setStroke(Color.RED);
                                        line.setStrokeWidth(20);
                                        drawLine line1 = new drawLine((cellSize / 2 + cellSize * currentCell.x), (cellSize / 2 + cellSize * currentCell.y), (cellSize / 2 + cellSize * Cells.get(j).x), (cellSize / 2 + cellSize * Cells.get(j).y), line);
                                        line1.draw();
                                        grid.getChildren().add(line);
                                        currentGameState = GameState.RED_WIN;
                                    }
                                    if (bottomPrompt.getText() == "Blue Player's Turn") {
                                        bluelines++;
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

    public void redPlayerTurn() {
        grid.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                int size = 2520;
                int boardSize = parseInt(numrandc.getText());
                int cellSize = size/boardSize;
                int gridx = (int)mouseEvent.getX()/cellSize;
                int gridy = (int)mouseEvent.getY()/cellSize;
                int in = 0;

                if(toggle.getSelectedToggle() == Scheck) {
                    Text s = new Text("S");
                    s.setScaleX(cellSize / 11);
                    s.setScaleY(cellSize / 11);
                    shapeDraw sShape = new shapeDraw((cellSize / 2 + cellSize * gridx), (cellSize / 2 + cellSize * gridy), s);
                    sShape.draw();


                    if (gridy < boardSize) {
                        Cell cell = new Cell(gridx, gridy, 'S');
                        Cells.add(cell);


                        if(Cells.size() > 0) {
                            for (int i = 0; i < Cells.size()-1; ++i) {
                                if (Cells.get(i).x == gridx && Cells.get(i).y == gridy) {
                                    ++in;
                                    if (in > 0) {
                                        Cells.remove(Cells.size() - 1);
                                        cell.setIsFilled();
                                    }
                                }else {
                                    in++;
                                }
                            }
                        }
                        if(!cell.filled){
                            grid.getChildren().add(s);
                        }
                    }
                }

                if(toggle.getSelectedToggle() == Ocheck) {
                    Text o = new Text("O");
                    o.setScaleX(cellSize / 11);
                    o.setScaleY(cellSize / 11);
                    shapeDraw sShape = new shapeDraw((cellSize / 2 + cellSize * gridx), (cellSize / 2 + cellSize * gridy), o);
                    sShape.draw();
//                    System.out.println(String.format("%d, %d", gridx, gridy));
                    if (gridy < boardSize) {
                        Cell cell = new Cell(gridx, gridy, 'O');
                        String currentCell = String.format("%d, %d", gridx, gridy);
                        Cells.add(cell);

                        if(Cells.size() > 0) {
                            for (int i = 0; i < Cells.size()-1; ++i) {
//                                System.out.println(String.format("%d '%s' difference '%s'", i, currentCell, Cells.get(i).getIndex()));
                                if (Cells.get(i).x == gridx && Cells.get(i).y == gridy) {
                                    ++in;
                                    if (in > 0) {
                                        Cells.remove(Cells.size() - 1);
                                        cell.setIsFilled();
                                    }
                                }else {
                                    in++;
                                }
                            }
                        }
                        if(!cell.filled){
                            grid.getChildren().add(o);
                        }
                    }
                }

                checkforSOS();
                bottomPrompt.setText("Blue Player's Turn");
                bottomPrompt.setVisible(true);
                System.out.println("BLUE PLAYER TURN\n");

                if(toggleGroup.getSelectedToggle() == simpleGame) {
                    if (currentGameState == GameState.RED_WIN) {
                        bottomPrompt.setVisible(false);
                        endstatusPrompt.setText("Red Player Wins!");
                        endstatusPrompt.setVisible(true);
                    }
                    else {
                        bluePlayerTurn();
                    }
                }
                if(toggleGroup.getSelectedToggle() == generalGame){
                    if (currentGameState == GameState.RED_WIN) {
                        currentGameState = GameState.PLAYING;
                    }
                    if(Cells.size() == boardSize*boardSize){
                        if(redlines > bluelines){
                            currentGameState = GameState.RED_WIN;
                            bottomPrompt.setVisible(false);
                            endstatusPrompt.setText("Red Player Wins!");
                            endstatusPrompt.setVisible(true);
                            System.out.printf("Red: %d \nBlue: %d", redlines, bluelines);
                        }
                        if(bluelines == redlines){
                            currentGameState = GameState.DRAW;
                            bottomPrompt.setVisible(false);
                            endstatusPrompt.setText("Draw!");
                            endstatusPrompt.setVisible(true);
                            System.out.printf("Red: %d \nBlue: %d", redlines, bluelines);
                        }
                    }
                    else{
                        bluePlayerTurn();
                    }
                }
            }
        });
    }

    public void bluePlayerTurn() {
        grid.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                int size = 2520;
                int boardSize = parseInt(numrandc.getText());
                int cellSize = size / boardSize;
                int gridx = (int) mouseEvent.getX() / cellSize;
                int gridy = (int) mouseEvent.getY() / cellSize;
                int in = 0;

                if (toggle2.getSelectedToggle() == S2check) {
                    Text s = new Text("S");
                    s.setScaleX(cellSize / 11);
                    s.setScaleY(cellSize / 11);
                    shapeDraw sShape = new shapeDraw((cellSize / 2 + cellSize * gridx), (cellSize / 2 + cellSize * gridy), s);
                    sShape.draw();
//                    System.out.println(String.format("%d, %d", gridx, gridy));
                    if (gridy < boardSize) {
                        Cell cell = new Cell(gridx, gridy, 'S');
                        Cells.add(cell);

                        if (Cells.size() > 0) {
                            for (int i = 0; i < Cells.size() - 1; ++i) {
//                                System.out.println(String.format("%d '%s' difference '%s'", i, currentCell, Cells.get(i).getIndex()));
                                if (Cells.get(i).x == gridx && Cells.get(i).y == gridy) {
                                    ++in;
                                    if (in > 0) {
                                        Cells.remove(Cells.size() - 1);
                                        cell.setIsFilled();
                                    }
                                } else {
                                    in++;
                                }
                            }
                        }
                        if (!cell.filled) {
                            grid.getChildren().add(s);
                        }
                    }
                }

                if (toggle2.getSelectedToggle() == O2check) {
                    Text o = new Text("O");
                    o.setScaleX(cellSize / 11);
                    o.setScaleY(cellSize / 11);
                    shapeDraw sShape = new shapeDraw((cellSize / 2 + cellSize * gridx), (cellSize / 2 + cellSize * gridy), o);
                    sShape.draw();
//                    System.out.println(String.format("%d, %d", gridx, gridy));
                    if (gridy < boardSize) {
                        Cell cell = new Cell(gridx, gridy, 'O');
                        String currentCell = String.format("%d, %d", gridx, gridy);
                        Cells.add(cell);

                        if (Cells.size() > 0) {
                            for (int i = 0; i < Cells.size() - 1; ++i) {
//                                System.out.println(String.format("%d '%s' difference '%s'", i, currentCell, Cells.get(i).getIndex()));
                                if (Cells.get(i).x == gridx && Cells.get(i).y == gridy) {
                                    ++in;
                                    if (in > 0) {
                                        Cells.remove(Cells.size() - 1);
                                        cell.setIsFilled();
                                    }
                                } else {
                                    in++;
                                }
                            }
                        }
                        if (!cell.filled) {
                            grid.getChildren().add(o);
                        }
                    }
                }

                checkforSOS();
                bottomPrompt.setText("Red Player's Turn");
                bottomPrompt.setVisible(true);
                System.out.println("RED PLAYER TURN\n");

                if (toggleGroup.getSelectedToggle() == simpleGame) {
                    if (currentGameState == GameState.BLUE_WIN) {
                        bottomPrompt.setVisible(false);
                        endstatusPrompt.setText("Blue Player Wins!");
                        endstatusPrompt.setVisible(true);

                    } else {
                        redPlayerTurn();
                    }
                }
                if (toggleGroup.getSelectedToggle() == generalGame) {
                    if (currentGameState == GameState.BLUE_WIN) {

                        currentGameState = GameState.PLAYING;
                    }
                    if (Cells.size() == boardSize * boardSize) {
                        System.out.println("HFJF");
                        if (bluelines > redlines) {
                            currentGameState = GameState.BLUE_WIN;
                            bottomPrompt.setVisible(false);
                            endstatusPrompt.setText("Blue Player Wins!");
                            endstatusPrompt.setVisible(true);
                            System.out.printf("Red: %d \nBlue: %d", redlines, bluelines);
                        }
                        if(bluelines == redlines){
                            currentGameState = GameState.DRAW;
                            bottomPrompt.setVisible(false);
                            endstatusPrompt.setText("Draw!");
                            endstatusPrompt.setVisible(true);
                            System.out.printf("Red: %d \nBlue: %d", redlines, bluelines);
                        }
                    } else {
                        redPlayerTurn();
                    }
                }
            }
        });
    }

    public class shapeDraw {
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

        public void drawthat(){}

    }
    public class drawLine {
        private double x, y, x2, y2;
        private Line line;

        drawLine(double x, double y, double x2, double y2, Line line){
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

    @Override
    public void start(Stage primaryStage) {
        start.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(start.isSelected()){
                    currentGameState = GameState.PLAYING;
                    redlines = 0;
                    bluelines = 0;
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