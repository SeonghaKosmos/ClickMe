package sample.code.customClasses;

import javafx.scene.control.Label;

public class ReusableLabel extends Label{

    public ReusableLabel(String text) {
        super(text);
    }

    public ReusableLabel clone(){
        ReusableLabel clone = new ReusableLabel(this.getText());
        clone.setPrefHeight(this.getPrefHeight());
        clone.setPrefWidth(this.getPrefWidth());
        clone.setLayoutX(this.getLayoutX());
        clone.setLayoutY(this.getLayoutY());
        clone.setFont(this.getFont());
        clone.setStyle(this.getStyle());
        clone.setOpacity(this.getOpacity());

        return clone;
    }

    public static ReusableLabel clone(Label label){
        ReusableLabel clone = new ReusableLabel(label.getText());
        clone.setPrefHeight(label.getPrefHeight());
        clone.setPrefWidth(label.getPrefWidth());
        clone.setLayoutX(label.getLayoutX());
        clone.setLayoutY(label.getLayoutY());
        clone.setFont(label.getFont());
        clone.setStyle(label.getStyle());
        clone.setOpacity(label.getOpacity());

        return clone;
    }
}
