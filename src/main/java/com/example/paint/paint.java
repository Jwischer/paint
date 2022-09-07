package com.example.paint;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javax.imageio.*;
import javafx.embed.swing.SwingFXUtils;
import java.nio.Buffer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class paint extends Application {
    public void start(Stage stage) throws IOException {
        //Create new image view
        ImageView myImageView = new ImageView();
        //Create canvas
        Canvas canvas = new Canvas(320,240);
        //Set up graphics context
        GraphicsContext gc = canvas.getGraphicsContext2D();
        //Create new file chooser
        FileChooser fileChooser = new FileChooser();
        final String[] path = new String[1];
        MenuBar menuBar = new MenuBar();
        //Menu Bar
        Menu filemenu = new Menu("File");
        Menu toolsmenu = new Menu("Tools");

        //Create and add menu items to menu
        MenuItem openfile = new MenuItem("Open File...");
        filemenu.getItems().add(openfile);
        filemenu.getItems().add(new SeparatorMenuItem());
        MenuItem save = new MenuItem("Save");
        filemenu.getItems().add(save);
        MenuItem saveas = new MenuItem("Save As...");
        filemenu.getItems().add(saveas);
        MenuItem exit = new MenuItem("Exit");
        filemenu.getItems().add(exit);

        MenuItem background = new MenuItem("Add Background");
        toolsmenu.getItems().add(background);

        //add menus to menu bar
        menuBar.getMenus().addAll(filemenu);
        menuBar.getMenus().addAll(toolsmenu);

        //set up border pane
        BorderPane border = new BorderPane();
        HBox taskbar = new HBox();
        StackPane center = new StackPane();
        border.setTop(menuBar);
        border.setCenter(center);

        //Create a new window
        Scene scene = new Scene(border, 320, 240);
        stage.setTitle("Pain(t)");

        //Set up file chooser to only import images
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );

        //add objects to scene
        center.getChildren().add(myImageView);
        center.getChildren().add(canvas);

        //Load Window
        stage.setScene(scene);
        stage.show();

        //Menu Functions
        openfile.setOnAction(e -> {
            fileChooser.setTitle("Open Image File");
            File file = fileChooser.showOpenDialog(null);
            path[0] = file.getPath();
            if (file != null) {
                Image image = new Image(file.toURI().toString());
                canvas.setHeight(image.getHeight());
                canvas.setWidth(image.getWidth());
                gc.drawImage(image, 0, 0);
                myImageView.setImage(image);
            }
        });

        save.setOnAction(e -> {
            System.out.println(path[0]);
            WritableImage writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
            writableImage = canvas.snapshot(null,writableImage);
            saveToFileCanvas(writableImage, path[0]);
        });

        saveas.setOnAction((ActionEvent event) -> {

            File file = fileChooser.showSaveDialog(null);
            String saveLocation = file.toURI().toString();
            String[] saveloc = saveLocation.split(":", 2);
            WritableImage writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
            writableImage = canvas.snapshot(null,writableImage);
            //Image image = myImageView.getImage();
            //saveToFile(image, saveloc[1]);
            saveToFileCanvas(writableImage, saveloc[1]);
        });

        background.setOnAction((ActionEvent event) -> {
            gc.setStroke(Color.DARKRED);
            gc.setFill(Color.BLUE);
            gc.setLineWidth(10);
            gc.fillRect(0,0,canvas.getWidth(), canvas.getHeight());
            gc.strokeRect(0, 0, canvas.getWidth(), canvas.getHeight());
            gc.drawImage(myImageView.getImage(),0,0);
        });


    }


    public static void main(String[] args) {
        launch();
    }

    public static void saveToFileCanvas (WritableImage image, String location) {
        File outputFile = new File(location);
        RenderedImage renderedImage = SwingFXUtils.fromFXImage(image, null);
        try {
            ImageIO.write(renderedImage, "png", outputFile);
        } catch (IOException ex) {
            //Logger.getLogger(JavaFX_DrawOnCanvas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void saveToFile(Image image, String location) {
        File outputFile = new File(location);
        BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
        try {
            ImageIO.write(bImage, "png", outputFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
