package com.example.graphen;

import de.jpp.factory.IOFactory;
import de.jpp.io.interfaces.GraphReader;
import de.jpp.io.interfaces.GraphWriter;
import de.jpp.io.interfaces.ParseException;
import de.jpp.model.TwoDimGraph;
import de.jpp.model.XYNode;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

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
        IOFactory io = new IOFactory();
        GraphReader<XYNode, Double, TwoDimGraph, String> gl = io.getTwoDimGxlReader();
        TwoDimGraph tg = null;
        try{
             tg = gl.read("1");
        } catch(ParseException e)
        {
            System.out.println(e);
        }

        if(tg!=null)
        {
            Collection<XYNode> nodes = tg.getNodes();
            for(XYNode n:nodes)
            {
//                System.out.println("X = "+n.getX());
//                System.out.println("Y = "+n.getY());
//                System.out.println("Label = "+n.getLabel());
            }
        } else {
            System.out.println("a");
        }

        GraphWriter<XYNode, Double, TwoDimGraph, String> gw = io.getTwoDimGxlWriter();
        String res = gw.write(tg);

    }
}