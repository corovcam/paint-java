package com.corovcak.martin.java.paint;

import javax.swing.event.MouseInputListener;
import java.awt.event.MouseEvent;

public class CustomMouseListener implements MouseInputListener {
    private final Canvas canvas;

    public CustomMouseListener(Canvas canvas) {
        this.canvas = canvas;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        System.out.println(e.getPoint());
        canvas.setPoint2(e.getPoint());
        switch (canvas.getSelectedTool()) {
            case Pen -> {
                canvas.penDraw();
            }
            default -> {

            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println(e.getPoint());
        canvas.setPoint1(e.getPoint());
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
