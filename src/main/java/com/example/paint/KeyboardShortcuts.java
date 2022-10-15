package com.example.paint;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.Mnemonic;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Controls the keyboard shortcuts
 */
public class KeyboardShortcuts {
    KeyboardShortcuts(Scene scene, ToggleButton pencil, ToggleButton line, ToggleButton dashedLine, ToggleButton square, ToggleButton rectangle, ToggleButton circle, ToggleButton ellipse, ToggleButton triangle, MenuItem undo, MenuItem redo, ToggleButton eraser, ToggleButton polygon, MenuItem copy, MenuItem paste, MenuItem select, MenuItem cut){
        KeyCombination pencilShortcut = new KeyCodeCombination(KeyCode.P, KeyCombination.CONTROL_DOWN);
        scene.getAccelerators().put(pencilShortcut, () -> pencil.fire());
        KeyCombination lineShortcut = new KeyCodeCombination(KeyCode.L, KeyCombination.CONTROL_DOWN);
        scene.getAccelerators().put(lineShortcut, () -> line.fire());
        KeyCombination dashedShortcut = new KeyCodeCombination(KeyCode.D, KeyCombination.CONTROL_DOWN);
        scene.getAccelerators().put(dashedShortcut, () -> dashedLine.fire());
        KeyCombination squareShortcut = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN);
        scene.getAccelerators().put(squareShortcut, () -> square.fire());
        KeyCombination rectShortcut = new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN);
        scene.getAccelerators().put(rectShortcut, () -> rectangle.fire());
        KeyCombination circleShortcut = new KeyCodeCombination(KeyCode.I, KeyCombination.CONTROL_DOWN);
        scene.getAccelerators().put(circleShortcut, () -> circle.fire());
        KeyCombination ellipseShortcut = new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN);
        scene.getAccelerators().put(ellipseShortcut, () -> ellipse.fire());
        KeyCombination undoShortcut = new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN);
        undo.setAccelerator(undoShortcut);
        KeyCombination redoShortcut = new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN);
        redo.setAccelerator(redoShortcut);
        KeyCombination eraserShortcut = new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN);
        scene.getAccelerators().put(eraserShortcut, () -> eraser.fire());
        KeyCombination polygonShortcut = new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN);
        scene.getAccelerators().put(polygonShortcut, () -> polygon.fire());
        KeyCombination copyShortcut = new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN);
        copy.setAccelerator(copyShortcut);
        KeyCombination pasteShortcut = new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN);
        paste.setAccelerator(pasteShortcut);
        KeyCombination triangleShortcut = new KeyCodeCombination(KeyCode.T, KeyCombination.CONTROL_DOWN);
        scene.getAccelerators().put(triangleShortcut, () -> triangle.fire());
        KeyCombination selectShortcut = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN);
        select.setAccelerator(selectShortcut);
        KeyCombination cutShortcut = new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN);
        cut.setAccelerator(cutShortcut);
    }
}
