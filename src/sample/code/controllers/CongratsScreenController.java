package sample.code.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import sample.Main;
import sample.code.customClasses.ReusableLabel;

public class CongratsScreenController {

    @FXML
    Pane motherPane;

    @FXML
    Label finalScore;

    @FXML
    Label prototypeBlock;


    @FXML
    TextField newNumOfBlocks;
    @FXML
    TextField newTime;
    @FXML
    TextField newSpeed;

    public void initialize(){
        new Thread(updateScore).start();
        Main.gModel.addChildrenList(motherPane.getChildren(), "congrats");

    }




    @FXML
    public void playAgain(){
        Main.dModel.setScore(0);
        Main.dModel.setTimeSec(Integer.parseInt(newTime.getText()));
        Main.dModel.setSpeed(Double.parseDouble(newSpeed.getText()));

        Main.gModel.getStage().setScene(Main.gModel.getScene("field"));
        Main.gModel.clearDeadBodies();

        int numOfBlocks = Integer.parseInt(newNumOfBlocks.getText());
        for (int i = 0; i < numOfBlocks; i++){
            ReusableLabel block = ReusableLabel.clone(prototypeBlock);
            block.setOpacity(1);
            Main.gModel.addBlock(block);
        }

    }


    Runnable updateScore = new Runnable() {
        @Override
        public void run() {

            while(true){

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        finalScore.setText(Integer.toString(Main.dModel.getScore()));
                    }
                });
            }
        }
    };
}
