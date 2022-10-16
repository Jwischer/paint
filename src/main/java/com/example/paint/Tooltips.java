package com.example.paint;

import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;

public class Tooltips {
    Tooltips(ToggleButton pencil,ToggleButton line,ToggleButton dashedLine,ToggleButton rectangle,ToggleButton square,ToggleButton ellipse,ToggleButton circle,ToggleButton eraser,ToggleButton triangle,ToggleButton polygon){
        Tooltip pencilTool = new Tooltip("Pencil tool");
        Tooltip.install(pencil, pencilTool);
        Tooltip lineTool = new Tooltip("Line tool");
        Tooltip.install(line, lineTool);
        Tooltip dashedLineTool = new Tooltip("Dashed line tool");
        Tooltip.install(dashedLine, dashedLineTool);
        Tooltip rectangleTool = new Tooltip("Rectangle tool");
        Tooltip.install(rectangle, rectangleTool);
        Tooltip squareTool = new Tooltip("Square tool");
        Tooltip.install(square, squareTool);
        Tooltip ellipseTool = new Tooltip("Ellipse tool");
        Tooltip.install(ellipse, ellipseTool);
        Tooltip circleTool = new Tooltip("Circle tool");
        Tooltip.install(circle, circleTool);
        Tooltip eraserTool = new Tooltip("Eraser tool");
        Tooltip.install(eraser, eraserTool);
        Tooltip triangleTool = new Tooltip("Triangle tool");
        Tooltip.install(triangle, triangleTool);
        Tooltip polygonTool = new Tooltip("Polygon tool");
        Tooltip.install(polygon, polygonTool);
    }
}
