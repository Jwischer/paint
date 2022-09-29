package com.example.paint;

import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

public class KeyboardShortcuts {
    KeyboardShortcuts(MenuItem pencil, MenuItem line,MenuItem dashedLine,MenuItem square,MenuItem rectangle,MenuItem circle,MenuItem ellipse, MenuItem triangle, MenuItem undo, MenuItem redo, MenuItem eraser, MenuItem polygon, MenuItem copy, MenuItem paste, MenuItem select){
        KeyCombination pencilShortcut = new KeyCodeCombination(KeyCode.P, KeyCombination.CONTROL_DOWN);
        pencil.setAccelerator(pencilShortcut);
        KeyCombination lineShortcut = new KeyCodeCombination(KeyCode.L, KeyCombination.CONTROL_DOWN);
        line.setAccelerator(lineShortcut);
        KeyCombination dashedShortcut = new KeyCodeCombination(KeyCode.D, KeyCombination.CONTROL_DOWN);
        dashedLine.setAccelerator(dashedShortcut);
        KeyCombination squareShortcut = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN);
        square.setAccelerator(squareShortcut);
        KeyCombination rectShortcut = new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN);
        rectangle.setAccelerator(rectShortcut);
        KeyCombination circleShortcut = new KeyCodeCombination(KeyCode.I, KeyCombination.CONTROL_DOWN);
        circle.setAccelerator(circleShortcut);
        KeyCombination ellipseShortcut = new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN);
        ellipse.setAccelerator(ellipseShortcut);
        KeyCombination undoShortcut = new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN);
        undo.setAccelerator(undoShortcut);
        KeyCombination redoShortcut = new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN);
        redo.setAccelerator(redoShortcut);
        KeyCombination eraserShortcut = new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN);
        eraser.setAccelerator(eraserShortcut);
        KeyCombination polygonShortcut = new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN);
        polygon.setAccelerator(polygonShortcut);
        KeyCombination copyShortcut = new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN);
        copy.setAccelerator(copyShortcut);
        KeyCombination pasteShortcut = new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN);
        paste.setAccelerator(pasteShortcut);
        KeyCombination triangleShortcut = new KeyCodeCombination(KeyCode.T, KeyCombination.CONTROL_DOWN);
        triangle.setAccelerator(triangleShortcut);
        KeyCombination selectShortcut = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN);
        select.setAccelerator(selectShortcut);
    }
}
