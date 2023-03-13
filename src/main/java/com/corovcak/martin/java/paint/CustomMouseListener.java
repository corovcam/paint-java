package com.corovcak.martin.java.paint;

import com.corovcak.martin.java.paint.utils.Tools;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.event.MouseEvent;

public class CustomMouseListener implements MouseInputListener {
    private final Canvas canvas;

    public CustomMouseListener(Canvas canvas) {
        this.canvas = canvas;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        canvas.saveCurrentImageToStack();
        canvas.setPoint1(e.getPoint());
        if (SwingUtilities.isLeftMouseButton(e)) {
            if (canvas.getSelectedTool() == Tools.Text) {
                canvas.createText();
            } else if (canvas.getSelectedTool() == Tools.Polygon) {
                canvas.createPolygonPoint();
            }
        } else if (SwingUtilities.isMiddleMouseButton(e)) {
            if (canvas.getSelectedTool() == Tools.Polygon) {
                canvas.resetPolygonPoints();
            }
        } else if (SwingUtilities.isRightMouseButton(e)) {
            if (canvas.getSelectedTool() == Tools.Polygon) {
                canvas.drawPolygon();
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
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
