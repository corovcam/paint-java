package com.corovcak.martin.java.paint.utils;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class LineSegment extends Line2D {
    Point2D P1;
    Point2D P2;

    public LineSegment(Point2D P1, Point2D P2) {
        this.P1 = P1;
        this.P2 = P2;
    }

    @Override
    public double getX1() {
        return P1.getX();
    }

    @Override
    public double getY1() {
        return P1.getY();
    }

    @Override
    public Point2D getP1() {
        return P1;
    }

    @Override
    public double getX2() {
        return P2.getX();
    }

    @Override
    public double getY2() {
        return P2.getY();
    }

    @Override
    public Point2D getP2() {
        return P2;
    }

    @Override
    public void setLine(double x1, double y1, double x2, double y2) {
        P1 = new Point2D.Double(x1, y1);
        P2 = new Point2D.Double(x2, y2);
    }

    @Override
    public Rectangle2D getBounds2D() {
        double x = Math.min(getX1(), getX2());
        double y = Math.min(getY1(), getY2());
        double width = Math.abs(getX1() - getX2());
        double height = Math.abs(getY1() - getY2());

        return new Rectangle2D.Double(x, y, width, height);
    }
}
