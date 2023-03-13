package com.corovcak.martin.java.paint;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

/**
 * Class that handles the IO operations of the application, namely New File (image), Open File and Save File.
 */
public class IOHandler {
    private final PaintAppGUI guiFrame;
    private final JFileChooser fileChooser;

    public IOHandler(PaintAppGUI guiFrame) {
        this.guiFrame = guiFrame;
        // Instantiate File Chooser in the current directory
        fileChooser = new JFileChooser(new File("."));
        // Can select only valid Image Files: .jpg and .png
        fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "png"));
    }

    /**
     * Prepare the canvas for a new file - image.
     */
    public void newFile() {
        Canvas canvas = guiFrame.getCanvas();
        canvas.prepareNewFile();
    }

    /**
     * If the user selects a file, then set the background image of the canvas to the image in the file.
     */
    public void openFile() {
        // Open file only if approved (clicked "Open" button) in the Dialog window
        if (fileChooser.showOpenDialog(guiFrame) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                guiFrame.getCanvas().setNewBackgroundImage(ImageIO.read(file));
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    /**
     * If the user selects a file to save, create a new file with the selected file name and the .png extension,
     * get the current image from the canvas, and write the image to the file.
     */
    public void saveFile() {
        if (fileChooser.showSaveDialog(guiFrame) == JFileChooser.APPROVE_OPTION) {
            File file = new File(fileChooser.getSelectedFile() + ".png");
            Image imgToWrite = guiFrame.getCanvas().getCurrentImage();
            try {
                ImageIO.write((RenderedImage) imgToWrite, "PNG", file);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
