package com.corovcak.martin.java.paint;

import com.corovcak.martin.java.paint.utils.LineSegment;
import com.corovcak.martin.java.paint.utils.Tools;
import org.drjekyll.fontchooser.FontDialog;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Stack;

public class Canvas extends JPanel {
    private final PaintAppGUI guiFrame;

    private Point point1, point2;
    private Graphics2D graphics;
    private Color color = Color.BLACK;
    private Tools selectedTool = Tools.Pen;
    private Image image, bg;
    private final Stack<Shape> previewStack = new Stack<>();
    private final Stack<Image> undoStack = new Stack<>();
    private final Stack<Image> redoStack = new Stack<>();

    @Override
    protected void paintComponent(Graphics g) {
        if (image == null) {
            image = createImage(getSize().width, getSize().height);
            graphics = (Graphics2D)image.getGraphics();
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            setLineWidth(1);
            clear();
        }
        g.drawImage(image, 0, 0, null);

        Graphics2D graphics2D = (Graphics2D)g;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (!previewStack.isEmpty()) {
            Shape s = previewStack.pop();
            graphics2D.setColor(color);
            graphics2D.setStroke(graphics.getStroke());
            if (selectedTool == Tools.Line)
                graphics2D.draw(s);
            else
                graphics2D.fill(s);
        }
    }

    public Canvas(PaintAppGUI guiFrame) {
        this.guiFrame = guiFrame;
        setBackground(Color.WHITE);
        setLayout(null);
        setDoubleBuffered(true);
        MouseInputListener mouseListener = new CustomMouseListener(this);
        addMouseListener(mouseListener);
        addMouseMotionListener(mouseListener);
    }

    public Tools getSelectedTool() {
        return selectedTool;
    }

    public Image getCurrentImage() {
        return image;
    }

    public void setPoint1(Point point1) {
        this.point1 = point1;
    }

    public void setPoint2(Point point2) {
        this.point2 = point2;
    }

    public void setTool(Tools tool) {
        selectedTool = tool;
        if (tool == Tools.Eraser)
            setColor(Color.WHITE);
    }

    public void setColor(Color color) {
        this.color = color;
        graphics.setPaint(color);
        guiFrame.getColorPanel().setBackground(color);
    }

    public void pickColor() {
        color = JColorChooser.showDialog(guiFrame, "Choose painting color.", color);
        if (color == null)
            color = Color.BLACK;
        if (selectedTool == Tools.Eraser)
            guiFrame.getPenButton().doClick();
        graphics.setPaint(color);
        guiFrame.getColorPanel().setBackground(color);
    }

    public void setLineWidth(int lineWidth) {
        graphics.setStroke(new BasicStroke(lineWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
    }

    public void pickFont() {
        FontDialog dialog = new FontDialog(guiFrame,"Choose text font.",true);
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        dialog.setVisible(true);
        if(!dialog.isCancelSelected()){
            graphics.setFont(dialog.getSelectedFont());
        }
    }

    public void penDraw() {
        graphics.drawLine(point1.x, point1.y, point2.x, point2.y);
        repaint();
        point1 = new Point(point2.x, point2.y);
    }

    public void previewLine() {
        previewStack.push(new LineSegment(point1, point2));
        repaint();
    }

    public void drawLine() {
        graphics.draw(new LineSegment(point1, point2));
        repaint();
        point2 = new Point(point1.x, point1.y);
    }

    public void previewRectangle() {
        Rectangle2D minBoundingRect = (new LineSegment(point1, point2)).getBounds2D(); // MBR
        previewStack.push(minBoundingRect);
        repaint();
    }

    public void drawRectangle() {
        Rectangle2D minBoundingRect = (new LineSegment(point1, point2)).getBounds2D(); // MBR
        graphics.fill(minBoundingRect);
        repaint();
        point2 = new Point(point1.x, point1.y);
    }

    public void previewEllipse(boolean isCircle) {
        Rectangle2D MBR = (new LineSegment(point1, point2)).getBounds2D();
        if (!isCircle)
            previewStack.push(new Ellipse2D.Double(MBR.getMinX(), MBR.getMinY(), MBR.getWidth(), MBR.getHeight()));
        else
            previewStack.push(new Ellipse2D.Double(MBR.getMinX(), MBR.getMinY(), MBR.getWidth(), MBR.getWidth()));
        repaint();
    }

    public void drawEllipse(boolean isCircle) {
        Rectangle2D MBR = (new LineSegment(point1, point2)).getBounds2D();
        if (!isCircle)
            graphics.fill(new Ellipse2D.Double(MBR.getMinX(), MBR.getMinY(), MBR.getWidth(), MBR.getHeight()));
        else
            graphics.fill(new Ellipse2D.Double(MBR.getMinX(), MBR.getMinY(), MBR.getWidth(), MBR.getWidth()));
        repaint();
        point2 = new Point(point1.x, point1.y);
    }

    public void createText() {
        JTextArea txtArea = new JTextArea();
        txtArea.setLineWrap(true);
        txtArea.setWrapStyleWord(true);
        String prompt = "Please add text to display.";
        JOptionPane.showConfirmDialog(guiFrame, txtArea, prompt, JOptionPane.OK_CANCEL_OPTION);
        int y = point1.y;
        for (String line : txtArea.getText().split("\n"))
            graphics.drawString(line, point1.x, y += graphics.getFontMetrics().getHeight());
        repaint();
    }

    public void undo() {
        if (!undoStack.isEmpty()) {
            Image temp = undoStack.pop();
            redoStack.push(image);
            setNewImage(temp);
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            Image temp = redoStack.pop();
            undoStack.push(image);
            setNewImage(temp);
        }
    }

    public void clear() {
        if (bg != null) {
            setNewImage(copyImage(bg));
        } else {
            graphics.setPaint(Color.white);
            graphics.fillRect(0, 0, getSize().width, getSize().height);
            graphics.setPaint(Color.black);
            repaint();
        }
        guiFrame.getColorPanel().setBackground(Color.black);
        guiFrame.getPenButton().doClick();
        color = Color.BLACK;
        previewStack.clear();
    }

    public void prepareNewFile() {
        bg = null;
        clear();
        undoStack.clear();
        redoStack.clear();
    }

    public void setNewImage(Image img) {
        image = img;
        graphics = (Graphics2D)image.getGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setPaint(Color.black);
        repaint();
    }

    public void setNewBackgroundImage(Image img) {
        bg = copyImage(img);
        setNewImage(copyImage(img));
    }

    private BufferedImage copyImage(Image img) {
        BufferedImage imageCopy = new BufferedImage(getSize().width, getSize().height, BufferedImage.TYPE_INT_RGB);
        Graphics g = imageCopy.createGraphics();
        g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
        return imageCopy;
    }

    public void saveCurrentImageToStack() {
        undoStack.push(copyImage(image));
    }
}
