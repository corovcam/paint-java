package com.corovcak.martin.java.paint;

import javax.swing.*;

public class PaintAppGUI extends JFrame {
    private JPanel mainPanel;
    private JPanel drawPanel;
    private JPanel colorPanel;
    private JButton newFileButton;
    private JButton saveButton;
    private JButton openButton;
    private JButton undoButton;
    private JButton redoButton;
    private JButton clearButton;
    private JButton penButton;
    private JButton lineButton;
    private JButton rectangleButton;
    private JButton circleButton;
    private JButton ellipseButton;
    private JButton polygonButton;
    private JButton textButton;
    private JButton eraserButton;
    private JButton pickColorButton;
    private JSpinner lineWidthSpinner;

    private final Canvas canvas;

    public PaintAppGUI() {
        canvas = new Canvas(drawPanel);
        ToolbarListener toolbarListener = new ToolbarListener(this);
        JButton[] buttons = new JButton[] {
                newFileButton, saveButton, openButton, undoButton, redoButton,
                clearButton, penButton, lineButton, rectangleButton, circleButton,
                ellipseButton, polygonButton, textButton, eraserButton, pickColorButton
        };
        for (var btn : buttons)
            btn.addActionListener(toolbarListener);
        lineWidthSpinner.addChangeListener(toolbarListener);
    }

    public final JPanel getMainPanel() {
        return mainPanel;
    }

    public JButton getClearButton() {
        return clearButton;
    }

    public JButton getPenButton() {
        return penButton;
    }

    public JButton getLineButton() {
        return lineButton;
    }

    public JButton getCircleButton() {
        return circleButton;
    }

    public JButton getEllipseButton() {
        return ellipseButton;
    }

    public JButton getPolygonButton() {
        return polygonButton;
    }

    public JButton getUndoButton() {
        return undoButton;
    }

    public JButton getRedoButton() {
        return redoButton;
    }

    public JPanel getDrawPanel() {
        return drawPanel;
    }

    public JButton getEraserButton() {
        return eraserButton;
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public JButton getPickColorButton() {
        return pickColorButton;
    }

    public JPanel getColorPanel() {
        return colorPanel;
    }

    public JButton getNewFileButton() {
        return newFileButton;
    }

    public JButton getOpenButton() {
        return openButton;
    }

    public JButton getRectangleButton() {
        return rectangleButton;
    }

    public JButton getTextButton() {
        return textButton;
    }

    public JSpinner getLineWidthSpinner() {
        return lineWidthSpinner;
    }

    public Canvas getCanvas() {
        return canvas;
    }
}
