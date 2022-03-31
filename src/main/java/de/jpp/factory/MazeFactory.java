package de.jpp.factory;

import de.jpp.io.interfaces.GraphReader;
import de.jpp.io.interfaces.ParseException;
import de.jpp.maze.Maze;
import de.jpp.model.TwoDimGraph;
import de.jpp.model.XYNode;

import java.awt.image.BufferedImage;
import java.util.Random;

public class MazeFactory implements Maze {

    private boolean[][] maze;
    private int width,height;

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
        maze = new boolean[width][height];
        Maze m = new Maze() {
            @Override
            public void setHWall(int x, int y, boolean wallActive) {

            }

            @Override
            public void setVWall(int x, int y, boolean wallActive) {

            }

            @Override
            public void setAllWalls(boolean wallActive) {

            }

            @Override
            public int getWidth() {
                return 0;
            }

            @Override
            public int getHeight() {
                return 0;
            }

            @Override
            public boolean isHWallActive(int x, int y) {
                return false;
            }

            @Override
            public boolean isVWallActive(int x, int y) {
                return false;
            }
        };
        return m;
    }
    /**
     * Returns a pixel representation of the specified maze
     *
     * @param maze the maze
     * @return a pixel representation of the specified maze
     */
    public BufferedImage getMazeAsImage(Maze maze) {
        //returneaza imagine
        ///si ce e true e alb si ce e negru e false
        //latimea width*2+1 si height*2+1
        BufferedImage bf = new BufferedImage(maze.getWidth()*2+1,maze.getHeight()*2+1,BufferedImage.TYPE_INT_RGB);

        for(int i=0;i<maze.getWidth()*2+1;i++)
        {
            for(int j=0;j<maze.getHeight()*2+1;j++)
            {
                if(maze.isHWallActive(i,j))
                {
                    bf.setRGB(i,j,255);
                }
                else{
                    bf.setRGB(i,j,0);
                }
            }
        }
        return bf;
    }

    private static void shuffle(int[] dx, int[] dy) {
        for (int i = 0; i < dx.length - 1; i++) {
            int offset = (int) (Math.random() * (dx.length - i));
            int temp = dx[i];
            dx[i] = dx[i + offset];
            dx[i + offset] = temp;
            temp = dy[i];
            dy[i] = dy[i + offset];
            dy[i + offset] = temp;
        }
    }


    private static void generate(boolean[][] maze, int x, int y) {
        int[] dx = {0, 1, 0, -1};
        int[] dy = {1, 0, -1, 0};
        shuffle(dx, dy);
        for (int i = 0; i < 4; i++) {
            if (x + dx[i] * 2 >= 0 && x + dx[i] * 2 < maze.length && y + dy[i] * 2 >= 0
                    && y + dy[i] * 2 < maze[0].length && maze[x + dx[i] * 2][y + dy[i] * 2] == true) {
                maze[x + dx[i] * 2][y + dy[i] * 2] = false;
                maze[x + dx[i]][y + dy[i]] = false;
                generate(maze, x + dx[i] * 2, y + dy[i] * 2);
            }
        }
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


        Maze m = new Maze() {
            @Override
            public void setHWall(int x, int y, boolean wallActive) {

            }

            @Override
            public void setVWall(int x, int y, boolean wallActive) {

            }

            @Override
            public void setAllWalls(boolean wallActive) {

            }

            @Override
            public int getWidth() {
                return 0;
            }

            @Override
            public int getHeight() {
                return 0;
            }

            @Override
            public boolean isHWallActive(int x, int y) {
                return false;
            }

            @Override
            public boolean isVWallActive(int x, int y) {
                return false;
            }
        };

        boolean maze[][] = new boolean[width][height];
        for(int i=0;i<width;i++)
        {
            for(int j=0;j<height;j++)
            {
                maze[i][j] = true;
            }
        }
        generate(maze,0,0);
        return m;
        ///creati un labirint de true si false cu vectorul de boolean
    }

    /**
     * Returns a GraphReader which parses a TwoDimGraph from a Maze-Object
     *
     * @return a GraphReader which parses a TwoDimGraph from a Maze-Object
     */
    public GraphReader<XYNode, Double, TwoDimGraph, Maze> getMazeReader() {
        ///creeaza TwoDimGraph dintr-un obiect Maze
        GraphReader<XYNode,Double,TwoDimGraph,Maze> gr = new GraphReader<XYNode, Double, TwoDimGraph, Maze>() {
            @Override
            public TwoDimGraph read(Maze input) throws ParseException {
                TwoDimGraph gr = new TwoDimGraph();
                ///face parsing pe boolean
                ///si dupa facem TwoDimGraph
                int nodeId = 1;
                boolean nodeCreated = false;
                XYNode [] [] nodesMatr = new XYNode[input.getWidth()][input.getHeight()];
                for(int i=1;i<input.getWidth();i++)
                {
                    for(int j=1;j<input.getHeight()-1;j++)
                    {
                        if(maze[i][j] == true)
                        {
                            XYNode firstN = null;
                            if( maze[i][j-1] == false)
                            {
                                firstN = new XYNode(String.valueOf(nodeId++),i,j);
                                nodesMatr[i][j] = firstN;
                                gr.addNode(firstN);

                            } else {
                                firstN = nodesMatr[i][j-1];
                            }

                            if(maze[i][j+1] == true)
                            {
                                XYNode secondN = new XYNode(String.valueOf(nodeId++),i,j+1);
                                nodesMatr[i][j+1] = secondN;
                                gr.addNode(secondN);
                                gr.addEdge(firstN,secondN);
                            }

                            if(maze[i-1][j] == true)
                            {
                                XYNode secondN = new XYNode(String.valueOf(nodeId++),i-1,j);
                                nodesMatr[i-1][j] = secondN;
                                firstN = nodesMatr[i-1][j];
                                gr.addNode(secondN);
                                gr.addEdge(firstN,secondN);
                            }
                        }
                    }
                }

                return gr;
            }
        };

        return gr;
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