package com.example.paint;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class AboutMenu extends paint {
    AboutMenu(){
        //Create a new window for the about menu
        Stage aboutStage = new Stage();
        aboutStage.setTitle("About Menu");
        GridPane gridPane = new GridPane();
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setAlignment(Pos.TOP_CENTER);
        Scene scene = new Scene(gridPane, 300, 300);
        aboutStage.setScene(scene);
        //Title
        Label title = new Label("About");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        gridPane.add(title, 1, 0);
        GridPane.setHalignment(title, HPos.CENTER);
        //SubTitle
        Label subTitle = new Label("JPaint v1.0");
        subTitle.setFont(Font.font("Arial", FontWeight.MEDIUM, 18));
        gridPane.add(subTitle,1,1);
        GridPane.setHalignment(subTitle, HPos.CENTER);
        //Features Label
        Label features = new Label("Features:");
        features.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
        gridPane.add(features,1,3);
        GridPane.setHalignment(features, HPos.CENTER);
        //Body
        Label body = new Label("1.Open and Save Images\n2. Draw on Image\n3. Add Border to Image");
        body.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
        gridPane.add(body,1,4);
        GridPane.setHalignment(body, HPos.CENTER);
        //Creator
        Label creator = new Label("Created by:");
        creator.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
        gridPane.add(creator,1,6);
        GridPane.setHalignment(creator, HPos.CENTER);
        //Name
        Label name = new Label("Jake Wischer");
        name.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
        gridPane.add(name,1,7);
        GridPane.setHalignment(name, HPos.CENTER);
        //Github
        Label github = new Label("Github Link:");
        github.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
        gridPane.add(github,1,9);
        GridPane.setHalignment(github, HPos.CENTER);
        //GHub Link
        Hyperlink gLink = new Hyperlink("github.com/Jwischer/paint");
        gLink.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
        gridPane.add(gLink,1,10);
        GridPane.setHalignment(gLink, HPos.CENTER);
        aboutStage.show();

        gLink.setOnAction(actionEvent  -> {
            System.out.println("Link Pressed");
            browser("https://github.com/Jwischer/paint");
        });
    }

    public static void openWebpage(String url) {
            //Desktop.getDesktop().browse(new URL(url).toURI());
    }

    public void browser(String url) {
        getHostServices().showDocument(url);
    }
}
