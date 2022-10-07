package com.example.paint;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import static com.example.paint.FileMenuFunctions.canvasToWritableImage;
import static com.example.paint.FileMenuFunctions.saveToFile;

/**
 * The popup that appears when you try to close JPaint using the menu function
 */
public class CloseAppPopup {
    Label question;
    Button yes;
    Button yesandsave;
    Button no;
    Stage stage;
    Scene scene;
    HBox hBox = new HBox();

    /**
     *
     * @param stageToClose
     * @param tabPane
     * @param tabArrays
     */
    CloseAppPopup(Stage stageToClose, TabPane tabPane, TabArrays tabArrays){
        this.yes = new Button("Close Without Saving");
        this.yesandsave = new Button("Save and Close");
        this.no = new Button("Go Back");
        this.question = new Label("Are you sure you want to close?");
        GridPane gridPane = new GridPane();
        this.scene = new Scene(gridPane);
        this.stage = new Stage();
        hBox.getChildren().add(yes);
        hBox.getChildren().add(yesandsave);
        hBox.getChildren().add(no);
        hBox.setSpacing(100);
        stage.setScene(scene);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.add(question, 1, 1);
        gridPane.add(hBox, 1, 3);
        GridPane.setHalignment(hBox, HPos.CENTER);
        GridPane.setHalignment(question, HPos.CENTER);
        stage.show();

        yes.setOnAction(actionEvent -> {
            System.out.println("Y");
            stage.close();
            stageToClose.close();
        });

        yes.setOnAction(actionEvent -> {
            System.out.println("Y");
            for(int i=0;i<tabArrays.maxTabs ;i++){
                if(tabArrays.path[i] != null) {
                    //Convert current state of canvas to a writable image
                    WritableImage writableImage = canvasToWritableImage(tabArrays.stackCanvas[i].canvas);
                    //Save the writable image to the same location as the file that was opened
                    //CREATE AN ARRAY OF PATHS FOR SAVE COMMANDS
                    saveToFile(writableImage, tabArrays.path[i], false);
                }
            }
            stage.close();
            stageToClose.close();
        });

        no.setOnAction(actionEvent -> {
            System.out.println("N");
            stage.close();
        });
    }
}
