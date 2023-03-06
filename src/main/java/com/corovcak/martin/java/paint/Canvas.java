package com.corovcak.martin.java.paint;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;

public class Canvas extends JPanel {
    private final PaintAppGUI guiFrame;

    private Point point1, point2;
    private Graphics2D graphics;
    private Color color = Color.BLACK;
    private Tools selectedTool = Tools.Pen;
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
        setLayout(null);
        setDoubleBuffered(true);
        MouseInputListener mouseListener = new CustomMouseListener(this);
        addMouseListener(mouseListener);
        addMouseMotionListener(mouseListener);
    }

    public Tools getSelectedTool() {
        return selectedTool;
    }

    public void setPoint1(Point point1) {
        this.point1 = point1;
    }

    public void setPoint2(Point point2) {
        this.point2 = point2;
    }

    public void setTool(Tools tool) {
        selectedTool = tool;
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

    public void penDraw() {
        if (graphics != null) {
            graphics.drawLine(point1.x, point1.y, point2.x, point2.y);
            repaint();
            point1 = new Point(point2.x, point2.y);
        }
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
