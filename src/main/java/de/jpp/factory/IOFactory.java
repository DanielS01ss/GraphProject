package de.jpp.factory;


import de.jpp.io.interfaces.GraphReader;
import de.jpp.io.interfaces.GraphWriter;
import de.jpp.model.LabelMapGraph;
import de.jpp.model.TwoDimGraph;
import de.jpp.model.XYNode;

import java.awt.image.BufferedImage;
import java.util.Map;

public class IOFactory {


    /**
     * Returns a new GraphReader instance which parses a TwoDimGraph from a GXL-String
     *
     * @return a new GraphReader instance which parses a TwoDimGraph from a GXL-String
     */
    public GraphReader<XYNode, Double, TwoDimGraph, String> getTwoDimGxlReader() {
        throw new UnsupportedOperationException("not supported yet!");
    }

    /**
     * Returns a new GraphReader instance which parses a TwoDimGraph from a DOT-String
     *
     * @return a new GraphReader instance which parses a TwoDimGraph from a DOT-String
     */
    public GraphReader<XYNode, Double, TwoDimGraph, String> getTwoDimDotReader() {
        throw new UnsupportedOperationException("not supported yet!");
    }

    /**
     * Returns a new GraphReader instance which parses a TwoDimGraph from a BufferedImage-Maze
     *
     * @return a new GraphReader instance which parses a TwoDimGraph from a BufferedImage-Maze
     */
    public GraphReader<XYNode, Double, TwoDimGraph, BufferedImage> getTwoDimImgReader() {
        throw new UnsupportedOperationException("not supported yet!");
    }

    /**
     * Returns a new GraphReader instance which parses a LabelMapGraph from a GXL-String
     *
     * @return a new GraphReader instance which parses a LabelMapGraph from a GXL-String
     */
    public GraphReader<String, Map<String, String>, LabelMapGraph, String> getLabelMapGraphGxlReader() {
        throw new UnsupportedOperationException("not supported yet!");
    }

    /**
     * Returns a new GraphWriter instances which outputs a TwoDimGraph as a GXL-String
     *
     * @return a new GraphWriter instances which outputs a TwoDimGraph as a GXL-String
     */
    public GraphWriter<XYNode, Double, TwoDimGraph, String> getTwoDimGxlWriter() {
        throw new UnsupportedOperationException("not supported yet!");
    }

    /**
     * Returns a new GraphWriter instance which outputs a TwoDimGraph as DOT-String
     *
     * @return a new GraphWriter instance which outputs a TwoDimGraph as DOT-String
     */
    public GraphWriter<XYNode, Double, TwoDimGraph, String> getTwoDimDotWriter() {
        throw new UnsupportedOperationException("not supported yet!");
    }

    /**
     * Returns a new GraphWriter instance which outputs a LabelMapGraph as GXL-String
     *
     * @return a new GraphWriter instance which outputs a LabelMapGraph as GXL-String
     */
    public GraphWriter<String, Map<String, String>, LabelMapGraph, String> getLabelMapGraphGxlWriter() {
        throw new UnsupportedOperationException("not supported yet!");
    }


}
