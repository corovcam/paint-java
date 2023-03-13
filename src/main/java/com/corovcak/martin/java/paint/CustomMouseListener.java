package com.corovcak.martin.java.paint;

import com.corovcak.martin.java.paint.utils.Tools;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.event.MouseEvent;

/**
 * Listens for mouse events and calls the appropriate methods in the Canvas class.
 */
public class CustomMouseListener implements MouseInputListener {
    private final Canvas canvas;

    public CustomMouseListener(Canvas canvas) {
        this.canvas = canvas;
    }

    /**
     * A method that is called when the mouse is pressed on a component.
     * Saves current Canvas to back stack and performs Tool action based on Mouse Button clicked.
     * @param e the event to be processed
     */
    @Override
    public void mousePressed(MouseEvent e) {
        canvas.saveCurrentImageToStack();
        canvas.setPoint1(e.getPoint());
        // Using SwingUtilities static checkers, we perform action for the specified Tool
        // For Tools that use different action for Middle/Right button than the default (drawing)
        // we define custom action (only Polygon tool).
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

    /**
     * Performs either Pen free drawing function or previews Line, Rectangle, Ellipse or Circle Shape before
     * performing permanent drawing.
     * <p>
     * {@inheritDoc}
     *
     * @param e the event to be processed
     */
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

    /**
     * {@inheritDoc}
     * <p>
     * Permanently displays/draws the previewed Shape on the Canvas.
     * @param e the event to be processed
     */
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

    /**
     * Method not implemented
     */
    @Override
    public void mouseMoved(MouseEvent e) {

    }

    /**
     * Method not implemented
     */
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    /**
     * Method not implemented
     */
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    /**
     * Method not implemented
     */
    @Override
    public void mouseExited(MouseEvent e) {

    }
}
