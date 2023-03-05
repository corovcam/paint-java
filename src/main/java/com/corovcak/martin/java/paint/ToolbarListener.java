package com.corovcak.martin.java.paint;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToolbarListener implements ActionListener, ChangeListener {
    private final PaintAppGUI guiFrame;

    public ToolbarListener(PaintAppGUI guiFrame) {
        this.guiFrame = guiFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        Canvas canvas = guiFrame.getCanvas();
    }

    @Override
    public void stateChanged(ChangeEvent e) {

    }
}
