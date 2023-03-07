package com.corovcak.martin.java.paint;

import javax.swing.*;
import java.awt.*;

public class PaintApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(PaintApp::showGUI);
    }

    private static void showGUI() {
        PaintAppGUI frame = new PaintAppGUI();
        frame.setContentPane(frame.getMainPanel());
        frame.setTitle("Paint App");
        frame.setMinimumSize(new Dimension(700, 600));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}