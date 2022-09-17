package com.example.paint;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import static java.lang.Math.round;

public class SettingsMenuFunctions {
    Slider strokeSlider;
    SettingsMenuFunctions(MenuItem strokeWidth){
        //Text box for user width input
        TextField sliderTextField = new TextField("10");
        HBox sliderBox = new HBox();
        //Create a slider for stroke width
        this.strokeSlider = new Slider(0,50,10);
        //Change Slider Appearance
        strokeSlider.setShowTickMarks(true);
        strokeSlider.setShowTickLabels(true);
        strokeSlider.setSnapToTicks(true);
        strokeSlider.setMinorTickCount(10);
        strokeSlider.setMajorTickUnit(10);
        //Add slider and text box to slider menu
        sliderBox.getChildren().add(strokeSlider);
        sliderBox.getChildren().add(sliderTextField);
        strokeWidth.setGraphic(sliderBox);
        final double[] drawWidth = {0};

        sliderTextField.textProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                //Set slider equal to text field value
                strokeSlider.setValue(Integer.parseInt(sliderTextField.getCharacters().toString()));
            }
        });

        //When slider changes
        strokeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                //set drawWidth to slider value
                drawWidth[0] = strokeSlider.getValue();
                //Set text field equal to slider value
                sliderTextField.setText(String.valueOf(round(strokeSlider.getValue())));
            }
        });
    }
}
