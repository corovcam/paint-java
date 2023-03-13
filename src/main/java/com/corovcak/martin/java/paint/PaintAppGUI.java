package com.corovcak.martin.java.paint;

import javax.swing.*;

/**
 * GUI JFrame that contains all the buttons, panels and interactive components
 */
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
    private JToggleButton penButton;
    private JToggleButton lineButton;
    private JToggleButton rectangleButton;
    private JToggleButton circleButton;
    private JToggleButton ellipseButton;
    private JToggleButton polygonButton;
    private JToggleButton textButton;
    private JToggleButton eraserButton;
    private JButton pickColorButton;
    private JButton pickFontButton;
    private JSpinner lineWidthSpinner;
    private ButtonGroup buttonGroup;

    private final IOHandler ioHandler;

    public PaintAppGUI() {
        ioHandler = new IOHandler(this);
        ToolbarListener toolbarListener = new ToolbarListener(this);

        // Buttons with just clickable function
        JButton[] buttons = new JButton[] {
                newFileButton, saveButton, openButton, undoButton,
                redoButton, clearButton, pickColorButton, pickFontButton
        };
        // Toggle buttons that stay looking "pressed" after click
        JToggleButton[] toggleButtons = new JToggleButton[] {
                penButton, lineButton, rectangleButton, circleButton,
                ellipseButton, polygonButton, textButton, eraserButton
        };
        // Pen is always clicked from the start
        penButton.doClick();
        // Add custom button listener
        for (var btn : buttons)
            btn.addActionListener(toolbarListener);
        for (var btn : toggleButtons) {
            btn.addActionListener(toolbarListener);
            buttonGroup.add(btn); // Add to Toggle ButtonGroup
        }
        lineWidthSpinner.addChangeListener(toolbarListener);
    }

    /**
     * Custom components definition (not supported by GUI Designer).
     * The IDE will call this function when it needs to create the UI components.
     */
    private void createUIComponents() {
        drawPanel = new Canvas(this);
        lineWidthSpinner = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
        buttonGroup = new ButtonGroup();
    }

    public final JPanel getMainPanel() {
        return mainPanel;
    }

    // Component Getters (we don't need to expose Component setters to other Classes)
    public AbstractButton getPenButton() {
        return penButton;
    }

    public AbstractButton getLineButton() {
        return lineButton;
    }

    public AbstractButton getCircleButton() {
        return circleButton;
    }

    public AbstractButton getEllipseButton() {
        return ellipseButton;
    }

    public AbstractButton getPolygonButton() {
        return polygonButton;
    }

    public AbstractButton getRectangleButton() {
        return rectangleButton;
    }

    public AbstractButton getTextButton() {
        return textButton;
    }

    public AbstractButton getEraserButton() {
        return eraserButton;
    }

    public JButton getClearButton() {
        return clearButton;
    }

    public JButton getUndoButton() {
        return undoButton;
    }

    public JButton getRedoButton() {
        return redoButton;
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public JButton getPickColorButton() {
        return pickColorButton;
    }

    public JButton getPickFontButton() {
        return pickFontButton;
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

    public JSpinner getLineWidthSpinner() {
        return lineWidthSpinner;
    }

    /**
     * This function returns a Canvas object that is the same as the drawPanel object.
     *
     * @return The Canvas object.
     */
    public Canvas getCanvas() {
        return (Canvas)drawPanel;
    }

    public IOHandler getIoHandler() {
        return ioHandler;
    }
}
