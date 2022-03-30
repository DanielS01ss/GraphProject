package de.jpp.model;

import de.jpp.io.interfaces.ParseException;

import java.util.Objects;

public class XYNode {
    private String label;
    private double x;
    private double y;

    public XYNode() {
        label = "";
        x = 0;
        y = 0;
    }
    public XYNode(XYNode node) {
        this.label = node.label;
        this.x = node.x;
        this.y = node.y;
    }
    public XYNode(String label, double x) {
        this.label = label;
        this.x = x;
    }
    /**
     * Creates a new XYNode with the specified label and coordinate
     *
     * @param label the label
     * @param x     the x value of the coordinate
     * @param y     the y value of the coordinate
     **/

    public XYNode(String label, double x, double y) throws ParseException {
        if(label == null)
            throw new ParseException();
        else
            this.label = label;
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the label of this node
     * @return
     */
    public String getLabel() {
        return label;
    }

    /**
     * Returns the x coordinate of this node
     * @return
     */
    public double getX() {
        return x;
    }

    /**
     * Returns the y coordinate of this node
     * @return
     */
    public double getY() {
        return y;
    }

    /**
     * Calculates the euclidian distance to the specified XYNode
     *
     * @param other the node to calculate the distance to
     * @return the euclidian distance to the specified XYNode
     */
    public double euclidianDistTo(XYNode other) {
        return Math.sqrt((other.x - this.x)*(other.x - this.x) + (other.y - this.y)*(other.y - this.y));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        XYNode xyNode = (XYNode) o;
        return Double.compare(xyNode.x, x) == 0 && Double.compare(xyNode.y, y) == 0 && label.equals(xyNode.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }


}
