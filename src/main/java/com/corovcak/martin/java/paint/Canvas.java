package com.corovcak.martin.java.paint;

import javax.swing.*;
import java.awt.*;

public class Canvas {
    private final JPanel drawPanel;

    private Graphics2D graphics;
    private Color color = Color.BLACK;

    public Canvas(JPanel drawPanel) {
        this.drawPanel = drawPanel;
    }

    public void setTool(Tools tool) {

    }

    public void pickColor() {

    }

    public void setLineWidth(int lineWidth) {

    }

    public void undo() {

    }

    public void redo() {

    }

    public void clear() {

    }
}
