package de.jpp.model;

import de.jpp.io.interfaces.GraphReader;
import de.jpp.model.interfaces.Edge;
import de.jpp.model.interfaces.Graph;

import javax.swing.text.Element;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

/**
 * A LabelMapGraph. <br>
 * The abstract-tag is only set because the tests will not compile otherwise. You should remove it!
 */

public class LabelMapGraph extends GraphImpl<String, Map<String, String>> {

    public GraphReader<String, Map<String, String>, LabelMapGraph, Element> getLabelMapGraphGxlReader()
    {
        return null;
    }    
}
