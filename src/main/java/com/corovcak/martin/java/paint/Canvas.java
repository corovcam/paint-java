package com.corovcak.martin.java.paint;

import javax.swing.*;
import java.awt.*;

public class Canvas extends JPanel {
    private final PaintAppGUI guiFrame;

    private Graphics2D graphics;
    private Color color = Color.BLACK;
    private Image image;

    @Override
    protected void paintComponent(Graphics g) {
        if (image == null) {
            image = createImage(getSize().width, getSize().height);
            graphics = (Graphics2D)image.getGraphics();
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            clear();
        }
        g.drawImage(image, 0, 0, null);
    }

    public Canvas(PaintAppGUI guiFrame) {
        this.guiFrame = guiFrame;
        setBackground(Color.WHITE);
    }

    public void setTool(Tools tool) {

    }

    public void pickColor() {
        color = JColorChooser.showDialog(guiFrame, "Choose painting color.", color);
        if (color == null)
            color = Color.BLACK;
        graphics.setPaint(color);
        guiFrame.getColorPanel().setBackground(color);
    }

    public void setLineWidth(int lineWidth) {
        graphics.setStroke(new BasicStroke(lineWidth));
    }

    public void undo() {

    }

    public void redo() {

    }

    public void clear() {
        graphics.setPaint(Color.white);
        graphics.fillRect(0, 0, getSize().width, getSize().height);
        graphics.setPaint(Color.black);
        guiFrame.getColorPanel().setBackground(Color.black);
        repaint();
    }
}
