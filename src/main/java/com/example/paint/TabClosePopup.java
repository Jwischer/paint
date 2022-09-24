package com.example.paint;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import static com.example.paint.FileMenuFunctions.*;

public class TabClosePopup{
    Label question;
    Button yes;
    Button yesAndSave;
    Button no;
    Stage stage;
    Scene scene;
    HBox hBox = new HBox();
    TabClosePopup(int nextTab, TabPane tabPane, TabArrays tabArrays){
        this.yes = new Button("Close Without Saving");
        this.yesAndSave = new Button("Save and Close");
        this.no = new Button("Go Back");
        this.question = new Label("Are you sure you want to close this tab?");
        GridPane gridPane = new GridPane();
        this.scene = new Scene(gridPane);
        this.stage = new Stage();
        hBox.getChildren().add(yes);
        hBox.getChildren().add(yesAndSave);
        hBox.getChildren().add(no);
        hBox.setSpacing(60);
        stage.setScene(scene);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.add(question, 1, 1);
        gridPane.add(hBox, 1, 3);
        GridPane.setHalignment(hBox, HPos.CENTER);
        GridPane.setHalignment(question, HPos.CENTER);
        stage.show();
    }

    public static int YesFunction(Stage stage, TabPane tabPane, TabArrays tabArrays, int nextTab, int closeIndex){
        int nextTabNew;
        tabPane.getTabs().remove(closeIndex);
        nextTabNew = OnTabClose(tabPane,tabArrays.maxTabs,tabArrays,nextTab,closeIndex);
        stage.close();
        return nextTabNew;
    }

    public static int YesAndSaveFunction(Stage stage, TabPane tabPane, TabArrays tabArrays, int nextTab, int closeIndex){
        int nextTabNew;
        WritableImage image = canvasToWritableImage(tabArrays.stackCanvas[closeIndex].canvas);
        saveToFile(image, tabArrays.path[closeIndex]);
        tabPane.getTabs().remove(closeIndex);
        nextTabNew = OnTabClose(tabPane,tabArrays.maxTabs,tabArrays,nextTab,closeIndex);
        stage.close();
        return nextTabNew;
    }

    public static void NoFunction(Stage stage){
        System.out.println("N");
        stage.close();
    }
}
