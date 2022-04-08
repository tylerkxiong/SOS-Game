package src.sprint_2.product;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
    Vector<Cell> Cells = new Vector<Cell>();

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

        public boolean isFilled(){
            if(!filled) {
                return false;
            }
            else{
                return true;
            }
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
        RadioButton simpleGame = new RadioButton("Simple Game");
        RadioButton generalGame = new RadioButton("General Game");
        ToggleGroup toggleGroup = new ToggleGroup();
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
        ToggleButton start = new ToggleButton("Start Game");
        start.setTranslateX(400);
        start.setTranslateY(-20);
        bottom.getChildren().addAll(start);

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
        RadioButton S2check = new RadioButton("S");
        RadioButton O2check = new RadioButton("O");
        ToggleGroup toggle2 = new ToggleGroup();
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
    }

    @Override
    public void start(Stage primaryStage) {
        topMenu();
        bottomMenu();
        drawGrid();
        leftrightplayerchoices();
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
//                    System.out.println(String.format("%d, %d", gridx, gridy));
                    if (gridy < boardSize) {
                        Cell cell = new Cell(gridx, gridy, 'S');
                        String currentCell = String.format("%d, %d", gridx, gridy);


                        if(Cells.isEmpty()){
                            grid.getChildren().add(s);
                            Cells.add(cell);
                        }

                        if(!Cells.isEmpty()) {
                            Cells.add(cell);
                            for (int i = 0; i < Cells.size() - 1; ++i) {
                                System.out.println(String.format("%d '%s' difference '%s'", i, currentCell, Cells.get(i).getIndex()));
                                if (Cells.get(i).x == gridx && Cells.get(i).y == gridy) {
                                    ++in;
                                    if (in > 0) {
                                        Cells.remove(Cells.size() - 1);
                                        System.out.println(in);
                                        break;
                                    }
                                }else {
                                    grid.getChildren().add(s);
                                    in = 0;
                                    break;
                                }
                            }
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

                        if(Cells.size() <= 1){
                            grid.getChildren().add(o);
                        }

                        if(Cells.size() > 0) {
                            for (int i = 0; i < Cells.size()-1; ++i) {
                                System.out.println(String.format("%d '%s' difference '%s'", i, currentCell, Cells.get(i).getIndex()));
                                if (Cells.get(i).x == gridx && Cells.get(i).y == gridy) {
                                    ++in;
                                    if (in > 0) {
                                        Cells.remove(Cells.size() - 1);
                                        cell.setIsFilled();
                                        System.out.println(in);
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


//                for(int i = 0; i < Cells.size(); ++i) {
//                    System.out.println(Cells.get(i).getIndex());
//                }
                System.out.println("/n");
            }
        });
        setupScene(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}