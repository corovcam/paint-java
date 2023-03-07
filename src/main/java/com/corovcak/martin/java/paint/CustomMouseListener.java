package com.corovcak.martin.java.paint;

import javax.swing.event.MouseInputListener;
import java.awt.event.MouseEvent;

public class CustomMouseListener implements MouseInputListener {
    private final Canvas canvas;

    public CustomMouseListener(Canvas canvas) {
        this.canvas = canvas;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println(e.getPoint());
        canvas.saveCurrentImageToStack();
        canvas.setPoint1(e.getPoint());
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        System.out.println(e.getPoint());
        canvas.setPoint2(e.getPoint());
        switch (canvas.getSelectedTool()) {
            case Pen, Eraser -> canvas.penDraw();
            case Line -> canvas.previewLine();
            case Rectangle -> canvas.previewRectangle();
            case Ellipse -> canvas.previewEllipse(false);
            case Circle -> canvas.previewEllipse(true);
            default -> {}
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (canvas.getSelectedTool()) {
            case Line -> canvas.drawLine();
            case Rectangle -> canvas.drawRectangle();
            case Ellipse -> canvas.drawEllipse(false);
            case Circle -> canvas.drawEllipse(true);
            default -> {}
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
