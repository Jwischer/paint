package com.example.paint;

import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

public class KeyboardShortcuts {
    KeyboardShortcuts(MenuItem pencil, MenuItem line,MenuItem dashedLine,MenuItem square,MenuItem rectangle,MenuItem circle,MenuItem ellipse, MenuItem undo){
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
        KeyCombination circleShortcut = new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN);
        circle.setAccelerator(circleShortcut);
        KeyCombination ellipseShortcut = new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN);
        ellipse.setAccelerator(ellipseShortcut);
        KeyCombination undoShortcut = new KeyCodeCombination(KeyCode.U, KeyCombination.CONTROL_DOWN);
        undo.setAccelerator(undoShortcut);
    }
}