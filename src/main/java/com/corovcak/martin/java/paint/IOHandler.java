package com.corovcak.martin.java.paint;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

public class IOHandler {
    private final PaintAppGUI guiFrame;
    private final JFileChooser fileChooser;

    public IOHandler(PaintAppGUI guiFrame) {
        this.guiFrame = guiFrame;
        fileChooser = new JFileChooser(new File("."));
        fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "png"));
    }

    public void newFile() {
        Canvas canvas = guiFrame.getCanvas();
        canvas.prepareNewFile();
    }

    public void openFile() {
        if (fileChooser.showOpenDialog(guiFrame) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                guiFrame.getCanvas().setNewBackgroundImage(ImageIO.read(file));
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }

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
