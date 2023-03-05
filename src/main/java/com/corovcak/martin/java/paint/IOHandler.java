package com.corovcak.martin.java.paint;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class IOHandler {
    private final JFrame guiFrame;
    private final JFileChooser fileChooser;

    public IOHandler(JFrame guiFrame) {
        this.guiFrame = guiFrame;
        fileChooser = new JFileChooser(new File("."));
        fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "png"));
    }

    public void newFile() {

    }

    public void openFile() {
        if (fileChooser.showOpenDialog(guiFrame) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
        }
    }

    public void saveFile() {
        if (fileChooser.showSaveDialog(guiFrame) == JFileChooser.APPROVE_OPTION) {
            File file = new File(fileChooser.getSelectedFile() + ".png");
        }
    }
}
