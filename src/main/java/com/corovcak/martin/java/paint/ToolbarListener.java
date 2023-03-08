package com.corovcak.martin.java.paint;

import com.corovcak.martin.java.paint.utils.Tools;

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
        IOHandler ioHandler = guiFrame.getIoHandler();

        if (source == guiFrame.getPenButton()) {
            canvas.setTool(Tools.Pen);
        } else if (source == guiFrame.getLineButton()) {
            canvas.setTool(Tools.Line);
        } else if (source == guiFrame.getRectangleButton()) {
            canvas.setTool(Tools.Rectangle);
        } else if (source == guiFrame.getCircleButton()) {
            canvas.setTool(Tools.Circle);
        } else if (source == guiFrame.getEllipseButton()) {
            canvas.setTool(Tools.Ellipse);
        } else if (source == guiFrame.getPolygonButton()) {
            canvas.setTool(Tools.Polygon);
        } else if (source == guiFrame.getTextButton()) {
            canvas.setTool(Tools.Text);
        } else if (source == guiFrame.getEraserButton()) {
            canvas.setTool(Tools.Eraser);
        } else if (source == guiFrame.getPickColorButton()) {
            canvas.pickColor();
        } else if (source == guiFrame.getPickFontButton()) {
            canvas.pickFont();
        } else if (source == guiFrame.getUndoButton()) {
            canvas.undo();
        } else if (source == guiFrame.getRedoButton()) {
            canvas.redo();
        } else if (source == guiFrame.getClearButton()) {
            canvas.clear();
        } else if (source == guiFrame.getNewFileButton()) {
            ioHandler.newFile();
        } else if (source == guiFrame.getOpenButton()) {
            ioHandler.openFile();
        } else if (source == guiFrame.getSaveButton()) {
            ioHandler.saveFile();
        } else {
            System.err.println("Not implemented listener.");
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        Object source = e.getSource();
        Canvas canvas = guiFrame.getCanvas();

        if (source == guiFrame.getLineWidthSpinner()) {
            JSpinner spinner = (JSpinner)e.getSource();
            canvas.setLineWidth((Integer)spinner.getValue());
        } else {
            System.err.println("Not implemented listener.");
        }
    }
}
