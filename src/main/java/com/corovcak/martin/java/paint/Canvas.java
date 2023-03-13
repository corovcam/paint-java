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
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Main Canvas class that uses JPanel as a GUI component, that allows to paint on itself using {@link java.awt} library.
 * Each Shape drawing includes invoking draw methods on {@link #graphics} property and calling {@link #repaint()} to
 * redraw current Canvas. This object stores collections of Shapes, Points and Images to handle preview, Undo, Redo
 * and Polygon tool functionalities. {@link ToolbarListener} sets current {@link #selectedTool} and
 * {@link CustomMouseListener} invokes drawing methods on this instance. {@link #point1} is first set on
 * {@link CustomMouseListener#mousePressed} event and {@link #point2} is set every
 * {@link CustomMouseListener#mouseDragged} event.
 */
public class Canvas extends JPanel {
    private final PaintAppGUI guiFrame;

    private Point point1, point2;
    private Graphics2D graphics;
    private Color color = Color.BLACK;
    private Tools selectedTool = Tools.Pen;
    private Image image, bg;
    private final List<Point> polygonPoints = new ArrayList<>();
    private final Stack<Point> polygonPointsStack = new Stack<>();
    private final Stack<Shape> previewStack = new Stack<>();
    private final Stack<Image> undoStack = new Stack<>();
    private final Stack<Image> redoStack = new Stack<>();

    /**
     * The central part in the Canvas class that overrides base JComponent implementation to redraw current
     * {@link #image} onto {@link #graphics} with default size. Also handles drawing "preview" versions of Shapes onto
     * Canvas as well as Polygon "preview" points.
     * <p>
     * {@inheritDoc}
     *
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        // If image is already set (image is not set only at the start of the App)
        if (image == null) {
            image = createImage(getSize().width, getSize().height);
            graphics = (Graphics2D)image.getGraphics();
            // Antialiasing is set to render High-Res graphics and smooth strokes and edges
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            setLineWidth(1);
            clear(); // Clear to set White Canvas background
        }
        // Draws the current image onto whole Canvas
        g.drawImage(image, 0, 0, null);

        // Create new Graphics2D component for next repaint
        Graphics2D graphics2D = (Graphics2D)g;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // If there is some Shape to preview (Rectangle, Ellipse, Line)
        if (!previewStack.isEmpty()) {
            Shape s = previewStack.pop();
            graphics2D.setColor(color);
            graphics2D.setStroke(graphics.getStroke());
            if (selectedTool == Tools.Line)
                graphics2D.draw(s); // Line cannot be called with "fill"
            else
                graphics2D.fill(s);
        }
        // Repaint all Polygon "preview" points each repaint
        if (!polygonPoints.isEmpty()) {
            graphics2D.setColor(color);
            graphics2D.setStroke(graphics.getStroke());
            for (var point : polygonPoints) {
                graphics2D.fillOval(point.x, point.y, 5, 5);
            }
        }
    }

    public Canvas(PaintAppGUI guiFrame) {
        this.guiFrame = guiFrame;
        //
        // Default settings
        //
        setBackground(Color.WHITE);
        // We don't GUI component positioning layout because we don't use Components on Canvas, only Graphics
        setLayout(null);
        setDoubleBuffered(true);
        // Canvas uses custom listener to listen to mouse events
        MouseInputListener mouseListener = new CustomMouseListener(this);
        addMouseListener(mouseListener);
        addMouseMotionListener(mouseListener);
    }

    /**
     * This function returns the selected tool.
     *
     * @return The selected tool.
     */
    public Tools getSelectedTool() {
        return selectedTool;
    }

    /**
     * This function returns the current image.
     *
     * @return The image variable is being returned.
     */
    public Image getCurrentImage() {
        return image;
    }

    /**
     * This function sets the value of the point1 variable to the value of the point1 parameter.
     *
     * @param point1 The first point of the line.
     */
    public void setPoint1(Point point1) {
        this.point1 = point1;
    }

    /**
     * This function sets the value of the point2 variable to the value of the point2 parameter.
     *
     * @param point2 The second point of the line.
     */
    public void setPoint2(Point point2) {
        this.point2 = point2;
    }

    /**
     * The function sets the selected tool to the tool passed in as a parameter, and then displays an informative
     * message to the user depending on the tool selected.
     *
     * @param tool The tool that is selected.
     */
    public void setTool(Tools tool) {
        selectedTool = tool;
        switch (tool) {
            case Polygon -> JOptionPane.showMessageDialog(
                    guiFrame,
                    """
                            Left click on canvas to create a Polygon point.
                            Right click to create Polygon.
                            Middle click to cancel Polygon creation.""",
                    "Polygon Instructions", JOptionPane.INFORMATION_MESSAGE
            );
            case Text -> JOptionPane.showMessageDialog(
                    guiFrame,
                    """
                            Left click on canvas where you want to create Text.
                            Write Text in text box and hit OK.""",
                    "Text Instructions", JOptionPane.INFORMATION_MESSAGE
            );
            case Eraser -> setColor(Color.WHITE);
        }
        // Reset Polygon points if user clicks on different Tool button during Polygon creation
        resetPolygonPoints();
    }

    /**
     * This function sets the color of the paintbrush to the color passed in as a parameter, and then sets the color
     * of the "preview" color panel to the same color.
     *
     * @param color The color of the brush
     */
    public void setColor(Color color) {
        this.color = color;
        graphics.setPaint(color);
        guiFrame.getColorPanel().setBackground(color);
    }

    /**
     * Opens a color chooser dialog, sets the color to the selected color, and sets the "preview" color panel to
     * the selected color.
     */
    public void pickColor() {
        color = JColorChooser.showDialog(guiFrame, "Choose painting color.", color);
        // Default brush color is Black
        if (color == null)
            color = Color.BLACK;
        // If user chooses color during "Eraser" mode, then it will automatically switch to "Pen" mode
        if (selectedTool == Tools.Eraser)
            guiFrame.getPenButton().doClick();
        graphics.setPaint(color);
        guiFrame.getColorPanel().setBackground(color);
    }

    /**
     * Set the line width to the given value. Stroke has "round" stroke type.
     *
     * @param lineWidth The width of the line in pixels.
     */
    public void setLineWidth(int lineWidth) {
        graphics.setStroke(new BasicStroke(lineWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
    }

    /**
     * Opens FontDialog for user to pick desired font and text size. If the user doesn't cancel the font dialog, set
     * the font of the graphics object to the font the user selected.
     */
    public void pickFont() {
        FontDialog dialog = new FontDialog(guiFrame,"Choose text font.",true);
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        dialog.setVisible(true);
        if(!dialog.isCancelSelected()){
            graphics.setFont(dialog.getSelectedFont());
        }
    }

    /**
     * Pen Tool draws small lines from point1 to point2, then sets point1 to point2 every mouse drag event.
     */
    public void penDraw() {
        graphics.drawLine(point1.x, point1.y, point2.x, point2.y);
        repaint();
        point1 = new Point(point2.x, point2.y);
    }

    /**
     * When the user drags the mouse, the program previews a line from the previous point to the current point.
     */
    public void previewLine() {
        previewStack.push(new LineSegment(point1, point2));
        repaint();
    }

    /**
     * Draw a line from point1 to point2, then set point2 to be the same as point1.
     */
    public void drawLine() {
        graphics.draw(new LineSegment(point1, point2));
        repaint();
        point2 = new Point(point1.x, point1.y);
    }

    /**
     * Create a rectangle that is the Minimum Bounding Rectangle of the line segment between point1 and point2,
     * and push it onto the preview stack.
     */
    public void previewRectangle() {
        Rectangle2D minBoundingRect = (new LineSegment(point1, point2)).getBounds2D(); // MBR
        previewStack.push(minBoundingRect);
        repaint();
    }

    /**
     * Draw a filled rectangle from point1 to point2 (using MBR), and then reset point2 to point1.
     */
    public void drawRectangle() {
        Rectangle2D minBoundingRect = (new LineSegment(point1, point2)).getBounds2D(); // MBR
        graphics.fill(minBoundingRect);
        repaint();
        point2 = new Point(point1.x, point1.y);
    }

    /**
     * Draw an ellipse preview with the same width and height as the bounding box of the line segment between point1
     * and point2.
     *
     * @param isCircle true if the ellipse is a circle, false if it is an ellipse.
     */
    public void previewEllipse(boolean isCircle) {
        Rectangle2D MBR = (new LineSegment(point1, point2)).getBounds2D();
        if (!isCircle)
            previewStack.push(new Ellipse2D.Double(MBR.getMinX(), MBR.getMinY(), MBR.getWidth(), MBR.getHeight()));
        else
            previewStack.push(new Ellipse2D.Double(MBR.getMinX(), MBR.getMinY(), MBR.getWidth(), MBR.getWidth()));
        repaint();
    }

    /**
     * Draw a permanent ellipse with the same width and height as the bounding box of the line segment between point1
     * and point2, and then reset point2 to point1.
     *
     * @param isCircle true if the ellipse is a circle, false if it is an ellipse.
     */
    public void drawEllipse(boolean isCircle) {
        Rectangle2D MBR = (new LineSegment(point1, point2)).getBounds2D();
        if (!isCircle)
            graphics.fill(new Ellipse2D.Double(MBR.getMinX(), MBR.getMinY(), MBR.getWidth(), MBR.getHeight()));
        else
            graphics.fill(new Ellipse2D.Double(MBR.getMinX(), MBR.getMinY(), MBR.getWidth(), MBR.getWidth()));
        repaint();
        point2 = new Point(point1.x, point1.y);
    }

    /**
     * The function adds a Polygon "preview" Point to the polygonPoints arraylist and then repaints the canvas
     */
    public void createPolygonPoint() {
        polygonPoints.add(point1);
        repaint();
    }

    /**
     * Takes a list of points, converts them to arrays of x and y coordinates, and then draws a filled polygon using
     * those coordinates. Clears the polygonPoints list afterwards.
     */
    public void drawPolygon() {
        int[] xPoints = polygonPoints.stream().mapToInt(point -> point.x).toArray();
        int[] yPoints = polygonPoints.stream().mapToInt(point -> point.y).toArray();
        graphics.fillPolygon(xPoints, yPoints, polygonPoints.size());
        polygonPoints.clear();
        repaint();
    }

    /**
     * This function clears the polygonPoints and polygonPointsStack collections to reset Polygon creation.
     */
    public void resetPolygonPoints() {
        if (!polygonPoints.isEmpty()) {
            polygonPoints.clear();
            polygonPointsStack.clear();
            repaint();
        }
    }

    /**
     * The function creates a text area, prompts the user to enter text, and then displays the text in the graphics
     * window.
     */
    public void createText() {
        // We render a small automatically resizable JTextArea inside ConfirmDialog for small multi-line text input
        JTextArea txtArea = new JTextArea();
        txtArea.setLineWrap(true);
        txtArea.setWrapStyleWord(true);
        String prompt = "Please add text to display.";
        JOptionPane.showConfirmDialog(guiFrame, txtArea, prompt, JOptionPane.OK_CANCEL_OPTION);
        // We can't render the multi-line text directly, so we iterate by each line using FontMetrics's
        // font text height to add the height onto Canvas's "y" coordinate
        int y = point1.y;
        for (String line : txtArea.getText().split("\n"))
            graphics.drawString(line, point1.x, y += graphics.getFontMetrics().getHeight());
        repaint();
    }

    /**
     * If the undo stack is not empty, pop the top image off the stack and set it as the current image. If the
     * polygonPoints array is not empty, remove the last from the array and it to stack (used for redo).
     */
    public void undo() {
        if (!undoStack.isEmpty()) {
            Image temp = undoStack.pop();
            redoStack.push(image);
            setNewImage(temp);
        }
        if (!polygonPoints.isEmpty()) {
            Point p = polygonPoints.remove(polygonPoints.size() - 1);
            polygonPointsStack.push(p);
            repaint();
        }
    }

    /**
     * If the redo stack is not empty, pop the top image off the redo stack and push it onto the undo stack, then set
     * the new image to the popped image. If the polygonPointsStack is not empty, pop the last and add it back to
     * previews.
     */
    public void redo() {
        if (!redoStack.isEmpty()) {
            Image temp = redoStack.pop();
            undoStack.push(image);
            setNewImage(temp);
        }
        if (!polygonPointsStack.isEmpty()) {
            Point p = polygonPointsStack.pop();
            polygonPoints.add(p);
            repaint();
        }
    }

    /**
     * Used for clearing the drawing currently on the Canvas by repainting it with new image
     * If there is a background image, then set the new image to be a copy of the background image. Otherwise, set the
     * paint to white and fill the entire canvas with white. Repaint the canvas.
     */
    public void clear() {
        if (bg != null) {
            setNewImage(copyImage(bg));
        } else {
            graphics.setPaint(Color.white);
            graphics.fillRect(0, 0, getSize().width, getSize().height);
            graphics.setPaint(Color.black);
            repaint();
        }
        // Line/Text color is reverted back to default Black color
        guiFrame.getColorPanel().setBackground(Color.black);
        // Tool is reverted back to default Pen tool
        guiFrame.getPenButton().doClick();
        color = Color.BLACK;
        // Remove all "previews" to prevent Undo/Redo bugs
        previewStack.clear();
        resetPolygonPoints();
    }

    /**
     * Prepare a new file by clearing the undo and redo stacks, clearing the canvas, and setting the background to null.
     */
    public void prepareNewFile() {
        bg = null;
        clear();
        undoStack.clear();
        redoStack.clear();
    }

    /**
     * Set the {@link #image} to a new image, and set the graphics to the graphics of the new image.
     *
     * @param img The image to be drawn on the canvas.
     */
    public void setNewImage(Image img) {
        image = img;
        graphics = (Graphics2D)image.getGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setPaint(Color.black);
        repaint();
    }

    /**
     * This function sets the background image to the image passed in as a parameter.
     *
     * @param img The image to be used as new background.
     */
    public void setNewBackgroundImage(Image img) {
        bg = copyImage(img);
        setNewImage(copyImage(img));
    }

    /**
     * Copies passed image and draws it onto current graphics (whole Canvas area).
     * Create a new BufferedImage object, draw the original image onto it, and return the new BufferedImage object.
     *
     * @param img The image to be copied.
     * @return A copy of the image.
     */
    private BufferedImage copyImage(Image img) {
        BufferedImage imageCopy = new BufferedImage(getSize().width, getSize().height, BufferedImage.TYPE_INT_RGB);
        Graphics g = imageCopy.createGraphics();
        g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
        return imageCopy;
    }

    /**
     * Save the current image to the undo stack (saves a copy of the {@link #image} object).
     */
    public void saveCurrentImageToStack() {
        undoStack.push(copyImage(image));
    }
}
