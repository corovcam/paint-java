package com.corovcak.martin.java.paint;

import javax.swing.*;
import java.awt.*;

/**
 * <h1>Java Paint App</h1>
 * <b>Java Paint App</b> is a drawing application, similar to programs such as Microsoft Paint or GIMP, that allows you
 * to draw lines, rectangles, ellipses, circles, polygons, create text using different colors, line widths and fonts.
 * The app also includes image imports, exports and undo/redo functionality.
 *
 * @author Martin Čorovčák
 * @version 1.0
 * @since 2023-03-13
 */
public class PaintApp {
    /**
     * When the Swing thread is ready, call the showGUI function.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(PaintApp::showGUI);
    }

    /**
     * This function creates a new PaintAppGUI object, sets the content pane to the main GUI panel and sets title, size,
     * pane location and the default close operation.
     */
    private static void showGUI() {
        PaintAppGUI frame = new PaintAppGUI();
        frame.setContentPane(frame.getMainPanel());
        frame.setTitle("Paint App");
        frame.setMinimumSize(new Dimension(700, 600)); // Edit size freely
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}