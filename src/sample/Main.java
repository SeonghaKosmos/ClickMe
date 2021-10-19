package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.code.model.DataModel;
import sample.code.model.GraphicsModel;

public class Main extends Application {

    public static DataModel dModel = new DataModel(20, 2);
    public static GraphicsModel gModel = new GraphicsModel();

    @Override
    public void start(Stage primaryStage) throws Exception{
        gModel.setStage(primaryStage);
        gModel.addScene("field", new Scene(FXMLLoader.load(getClass().getResource("graphics/view/field.fxml")), 1280, 800));
        gModel.addScene("congrats", new Scene(FXMLLoader.load(getClass().getResource("graphics/view/congratsScreen.fxml")), 1280, 800));
        gModel.getScene("congrats").getStylesheets().add("/Users/sabastocrator/Desktop/Program Files/clickMe/src/sample/styles/congratsScreen.css");


        primaryStage.setTitle("Hello World");
        primaryStage.setScene(gModel.getScene("congrats"));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}
