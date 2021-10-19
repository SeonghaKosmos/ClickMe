package sample.code.model;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import sample.code.customClasses.ReusableLabel;

import java.util.ArrayList;
import java.util.HashMap;

public class GraphicsModel {

    Stage stage;
    HashMap<String, Scene> scenes;

    ArrayList<Label> deadBlocks;
    HashMap<String, ObservableList<Node>> childrenLists;
    int numOfDeadBodies;

    ArrayList<Label> blocks;

    public GraphicsModel(){
        scenes = new HashMap<>();
        deadBlocks = new ArrayList<>();
        childrenLists = new HashMap<>();
        numOfDeadBodies = 0;
        blocks = new ArrayList<>();
    }

    public void addBlock(ReusableLabel block){
        blocks.add(block);
    }
    public Label giveAwayBlock(){
       return blocks.remove(0);
    }
    public Label getBlock(int index){
        return blocks.get(index);
    }
    public void clearAllBlocks(){
        blocks.clear();
        ObservableList<Node> fieldChildren = childrenLists.get("field");
        for (int i = fieldChildren.size() - 1; i >= 0; i--){
            Node tested = fieldChildren.get(i);
            if (tested instanceof Label){
                if (((Label) tested).getText().equals(" : )")){
                    fieldChildren.remove(i);
                }
            }
        }
    }

    public ArrayList<Label> getBlocks() {
        return blocks;
    }

    public void addScene(String id, Scene scene){
        scenes.put(id, scene);
    }
    public Scene getScene(String id){
        return scenes.get(id);
    }




    public void setStage(Stage stage){
        this.stage = stage;
    }
    public Stage getStage() {
        return stage;
    }




    public void clearDeadBodies(){

        for (String id : childrenLists.keySet()){

            ObservableList<Node> childrenList = childrenLists.get(id);
            for (int i = childrenList.size() - 1; i>=0; i--){

                Node tested = childrenList.get(i);
                if (tested instanceof Label){
                    if (((Label) tested).getText().equals(" : (")){
                        childrenList.remove(i);
                    }
                }

            }
        }
    }
    public void addDeadBody(ReusableLabel body){

        for (String key : childrenLists.keySet()) {

            childrenLists.get(key).add(0, body.clone());

        }
        numOfDeadBodies++;
    }
    public void addChildrenList(ObservableList<Node> childrenList, String id){
        childrenLists.put(id, childrenList);
    }

}
