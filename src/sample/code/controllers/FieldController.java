package sample.code.controllers;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import sample.Main;
import sample.code.customClasses.ReusableLabel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

public class FieldController {

    @FXML
    Pane motherPane;

    @FXML
    Label prototypeBlock;


    @FXML
    Label score;

    @FXML
    Label time;

    @FXML
    Button startButton;


    Thread timer;
    ArrayList<Thread> blockMovers = new ArrayList<>();

    //explosion images
    @FXML
    ImageView explosion1;
    @FXML
    ImageView explosion2;
    @FXML
    ImageView explosion3;
    @FXML
    ImageView explosion4;
    @FXML
    ImageView explosion5;
    @FXML
    ImageView explosion6;
    @FXML
    ImageView explosion7;
    ImageView[] explosionSeries;
    int imageTransitionTimeMillis = 60;


    public void initialize(){
        time.setText(Integer.toString(Main.dModel.getTimeSec()));
        ReusableLabel protoReusableBlock = ReusableLabel.clone(prototypeBlock);
        Main.gModel.addBlock(protoReusableBlock.clone());
        Main.gModel.addChildrenList(motherPane.getChildren(), "field");
        explosionSeries = new ImageView[]{explosion1, explosion2, explosion3, explosion4, explosion5, explosion6, explosion7};

    }




    @FXML
    public void start(){

        time.setText(Integer.toString(Main.dModel.getTimeSec()));


        timer = new Thread(timeController);
        timer.start();
        new Thread(setOffBlocks).start();


        for (Label block : Main.gModel.getBlocks()){
            block.setVisible(true);
            block.setDisable(false);
        }


        toggleStartButton();
    }



    public Runnable moveABlockAroundAndReleaseItFromList(){
        return new Runnable() {
            @Override
            public void run() {

                Label nextBlock = Main.gModel.giveAwayBlock();

                while(true){

                    try {
                        Thread.sleep(Main.dModel.getSpeedInMillis());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            moveToRandomLocation(nextBlock,1280 - prototypeBlock.getWidth(), 800 - prototypeBlock.getHeight());
                        }
                    });
                }
            }
        };
    }




    Runnable setOffBlocks = new Runnable() {
        @Override
        public void run() {

            ArrayList<Label> blocks = Main.gModel.getBlocks();
            Random random = new Random();
            while (!blocks.isEmpty()){

                Label currentBlock = Main.gModel.getBlock(0);
                currentBlock.setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        Main.dModel.incScore();
                        score.setText(Integer.toString(Main.dModel.getScore()));


                        new Thread(explode(leaveDeadBody(currentBlock))).start();

                    }
                });
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        motherPane.getChildren().add(currentBlock);
                    }
                });


                Thread blockMover = new Thread(moveABlockAroundAndReleaseItFromList());
                blockMovers.add(blockMover);
                blockMover.start();

                try {
                    Thread.sleep((int)(random.nextDouble() * Main.dModel.getSpeedInMillis()));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        }
    };



    Runnable timeController = new Runnable() {
        @Override
        public void run() {

            while(true){

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        Main.dModel.decTime();
                        time.setText(Integer.toString(Main.dModel.getTimeSec()));

                        if (Main.dModel.getTimeSec() == 0){

                            switchToCongratsScreen();

                        }
                    }

                });

            }

        }
    };


    private Runnable explode(Label triggeredBlockBody){
        return new Runnable() {
            @Override
            public void run() {

                for (int i = 1; i <= 7; i++){

                    //create image
                    FileInputStream input = null;
                    try {
                        input = new FileInputStream("src/sample/graphics/view/images/explosion"+i+".png");
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    Image image = new Image(input);
                    ImageView explosionFrame = new ImageView(image);


                    //calibrate image
                    explosionFrame.setLayoutX(triggeredBlockBody.getLayoutX() - 30);
                    explosionFrame.setLayoutY(triggeredBlockBody.getLayoutY() - 30);

                    explosionFrame.setFitHeight(120);
                    explosionFrame.setFitWidth(120);

                    //show image
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            motherPane.getChildren().add(explosionFrame);
                        }

                    });


                    //wait
                    try {
                        Thread.sleep(imageTransitionTimeMillis);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    //hide image
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            motherPane.getChildren().remove(explosionFrame);
                        }

                    });
                }


            }
        };
    }



    private void flashNextExplosionScene(ImageView scene){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

            }
        });

    }


    private void switchToCongratsScreen(){

        score.setText("0");

        Scene congrats = Main.gModel.getScene("congrats");
        Main.gModel.getStage().setScene(congrats);

        for (Thread mover : blockMovers){
            mover.stop();
        }
        blockMovers.clear();
        timer.stop();

        Main.gModel.clearAllBlocks();
        toggleStartButton();


    }

    private void toggleStartButton(){
        startButton.setDisable(!startButton.disabledProperty().getValue());
        startButton.setVisible(!startButton.isVisible());
    }


    private void moveToRandomLocation(Label block, double maxX, double maxY){
        Random random = new Random();
        double x = random.nextDouble() * (maxX - block.getWidth());
        double y = random.nextDouble() * (maxY - block.getWidth());

        block.setLayoutX(x);
        block.setLayoutY(y);
    }

    @FXML
    private Label leaveDeadBody(Label currentBlock){

        ReusableLabel deadBlock = new ReusableLabel(" : (");
        deadBlock.setPrefWidth(50);
        deadBlock.setPrefHeight(50);
        deadBlock.setLayoutX(currentBlock.getLayoutX());
        deadBlock.setLayoutY(currentBlock.getLayoutY());
        deadBlock.setFont(new Font(50));
        deadBlock.setStyle("-fx-background-color: grey;");
        deadBlock.setOpacity(0.3);
        Main.gModel.addDeadBody(deadBlock);

        return deadBlock;

    }

//    @FXML
//    public void mouseOn(){
//        Main.dModel.incScore();
//        score.setText(Integer.toString(Main.dModel.getScore()));
//        new Thread(explode).start();
//        leaveDeadBody();
//    }



}
