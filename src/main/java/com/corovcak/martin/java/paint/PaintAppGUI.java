package com.corovcak.martin.java.paint;

import javax.swing.*;

public class PaintAppGUI extends JFrame {
    private JButton clearButton;
    private JButton penButton;
    private JButton lineButton;
    private JButton circleButton;
    private JButton ellipseButton;
    private JButton polygonButton;
    private JButton undoButton;
    private JButton redoButton;
    private JPanel mainPanel;
    private JPanel drawPanel;
    private JButton eraserButton;
    private JButton saveButton;
    private JButton pickColorButton;
    private JPanel colorPanel;
    private JButton newButton;
    private JButton openButton;
    private JButton rectangleButton;
    private JButton textButton;

    public PaintAppGUI() {
        penButton.addActionListener(e -> JOptionPane.showMessageDialog(null, "Btn clicked"));
    }

    public final JPanel getMainPanel() {
        return mainPanel;
    }
}
