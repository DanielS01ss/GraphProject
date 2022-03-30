package com.example.graphen;

import de.jpp.factory.IOFactory;
import de.jpp.io.interfaces.GraphReader;
import de.jpp.io.interfaces.GraphWriter;
import de.jpp.io.interfaces.ParseException;
import de.jpp.model.TwoDimGraph;
import de.jpp.model.TwoDimGraphGXLWriter;
import de.jpp.model.XYNode;
import de.jpp.model.interfaces.Edge;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
//        launch();
        XYNode start=null,end=null;
        try{
            start = new XYNode("id1",10,20);
            end = new XYNode("id2",20,30);
        } catch (ParseException e)
        {
            System.out.println(e);
        }
        Optional<Double> op = Optional.of(20.0);
        Edge<XYNode,Double> edg = new Edge<XYNode,Double>(start,end,op);
        System.out.println(TwoDimGraphGXLWriter.writeEdge1(edg));
    }
}
