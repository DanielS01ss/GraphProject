package de.jpp.model;

public class XYNode {
    private String label;
    private double x;
    private double y;

    public XYNode() {
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

    public XYNode(String label, double x, double y) throws Exception {
        if(label == null)
            throw new Exception("The label shouldn't be null!");
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

}
