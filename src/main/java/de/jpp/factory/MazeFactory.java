package de.jpp.factory;

import de.jpp.io.interfaces.GraphReader;
import de.jpp.maze.Maze;
import de.jpp.model.TwoDimGraph;
import de.jpp.model.XYNode;

import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class MazeFactory implements Maze {

    private boolean[][] maze;

    public MazeFactory() {
    }

    public MazeFactory(boolean[][] maze) {
        this.maze = maze;
    }

    /**
     * Creates a new empty maze with the specified width and height
     *
     * @param width  the width
     * @param height the height
     * @return a new empty maze with the specified width and height
     */
    public Maze getEmptyMaze(int width, int height) {
        throw new UnsupportedOperationException("not supported yet!");
    }

    /**
     * Returns a pixel representation of the specified maze
     *
     * @param maze the maze
     * @return a pixel representation of the specified maze
     */
    public BufferedImage getMazeAsImage(Maze maze) {
        throw new UnsupportedOperationException("not supported yet!");
    }

    /**
     * Returns a random maze with specified width, height created by the specified algorithm specified from the instruction <br>
     * Random numbers are only taken from the specified random number generator (RNG) and only as specified in the instruction
     *
     * @param ran    the random number generator (RNG)
     * @param width  the width
     * @param height the height
     * @return a random maze with specified width and height
     */
    public static Maze getRandomMaze(Random ran, int width, int height) {
        throw new UnsupportedOperationException("not supported yet!");
    }

    /**
     * Returns a GraphReader which parses a TwoDimGraph from a Maze-Object
     *
     * @return a GraphReader which parses a TwoDimGraph from a Maze-Object
     */
    public GraphReader<XYNode, Double, TwoDimGraph, Maze> getMazeReader() {
        throw new UnsupportedOperationException("not supported yet!");
    }

    @Override
    public void setHWall(int x, int y, boolean wallActive) {
        maze[x][y] = wallActive;
    }

    @Override
    public void setVWall(int x, int y, boolean wallActive) {
        maze[x][y] = wallActive;
    }

    @Override
    public void setAllWalls(boolean wallActive) {

    }

    @Override
    public int getWidth() {
        return maze[0].length;
    }

    @Override
    public int getHeight() {
        return maze.length;
    }

    @Override
    public boolean isHWallActive(int x, int y) {
        return maze[x][y];
    }

    @Override
    public boolean isVWallActive(int x, int y) {
        return maze[x][y];
    }
}
