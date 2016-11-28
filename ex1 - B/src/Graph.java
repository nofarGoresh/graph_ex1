/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package ex1.b;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hen and Nofar
 */
public class Graph {

    String fileName;
    String fileTestName;
    Graph_algo d;
    Vertex[] nodes;
    double radius = 0;
    int numNodes = 0;
    int numEdges = 0;
    public static double infinity = Double.POSITIVE_INFINITY;
    long startTime = 0;

    public Graph(String fileName,String fileTestName) {
    	startTime = System.currentTimeMillis();
        this.fileName = fileName;
        this.fileTestName = fileTestName;
        nodes = createGraph();
        createTestFileAnswer(); 
    }

    public void setFileName(String otherFileName) {
        this.fileName = otherFileName;
    }

    public void setFileTestName(String otherTestFileName) {
        this.fileTestName = otherTestFileName;
    }

    public void createTestFileAnswer() {
        nodes = createGraph();
        d = new Graph_algo(nodes);
        try {
            FileInputStream fis = new FileInputStream(fileTestName);
            BufferedWriter out = null;
            FileWriter fstream = new FileWriter("C:\\Users\\Nofar\\git\\graph_ex1\\ex1 - B\\src\\out.txt",true);
            out = new BufferedWriter(fstream);
            Scanner scanner = new Scanner(fis);
            int numTests = scanner.nextInt();
            int times = 1;
            while (times < numTests) {
                int kod1 = scanner.nextInt();
                int kod2 = scanner.nextInt();
                out.write(String.valueOf(kod1));
                out.write(" ");
                out.write(String.valueOf(kod2));
                out.write(" ");
                int arraySize = scanner.nextInt();
                out.write(String.valueOf(arraySize));
                out.write(" ");
                int[] array = new int[arraySize];
                for (int i = 0; i < array.length; i++) {
                    array[i] = scanner.nextInt();
                    out.write(String.valueOf(array[i]));
                    out.write(" ");
                }
              d.computePathsBlackList(kod1, array);
              out.write(String.valueOf(d.getSortestWeight(kod1, kod2)));
              out.write(" ");
                  for (int i = 0; i < nodes.length; i++) {
                      nodes[i].visited = false;
                      nodes[i].previous = -1;
                      nodes[i].dist = infinity;
                  }
               out.write("\n");
               times++;
            }
            out.write("Graph:|v|=" + String.valueOf(numNodes) + " |E|="+ String.valueOf(numEdges));
            out.write(" ");
            if(d.TriangleInequality())
            	out.write("TIE");
            else
            	out.write("!TIE");
            out.write(" Radius: ");
            out.write(String.valueOf(d.radius()));
            out.write(" Diameter: ");
            out.write(String.valueOf(d.diameter()));
            out.write(" ");
            long endTime   = System.currentTimeMillis();
            long totalTime = endTime - startTime;
            out.write("runTime:"+String.valueOf(totalTime) + " ms");
            out.close();
            scanner.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Graph.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Graph.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

//    boolean flag = true;
    public Vertex[] createGraph() { //returns arr of vertex - a graph
    int numNodes = 0;
        int numEdges = 0;
        Vertex[] nodes = null;
        Edge edges[];
        try {
           
        	FileInputStream fis = new FileInputStream(this.fileName);
        	Scanner scanner = new Scanner(fis);
            numNodes = scanner.nextInt(); //read the first number = number of nudes
            numEdges = scanner.nextInt(); //read the second number = number of edges
            this.numNodes = numNodes;
            this.numEdges = numEdges;
            nodes = new Vertex[numNodes]; //array of vertex 
            edges = new Edge[numEdges]; //array of edges *2
            int e = 0;
            double infinity = Double.POSITIVE_INFINITY;
            for (int i = 0; i < nodes.length; i++) {
                nodes[i] = new Vertex(i, infinity);
            }
            while (scanner.hasNextInt()) { //read the fill untill we read null
                int sv = scanner.nextInt(); //read the first in the raw int from the text file
                int v = scanner.nextInt(); //read the second in the raw int from the text file
                double w = scanner.nextDouble(); //read the third in the raw int from the text file
//                if(flag){
//                    radius = w;
//                    flag = false;
//                }
//                if(w<radius)
//                    radius = w;
                nodes[v].edges.add(new Edge(sv,w));
                nodes[sv].edges.add(new Edge(v,w));
            }
            scanner.close();
        } catch (IOException ex) {
            System.out.print("Error reading file\n" + ex);
            System.exit(2);
        }
        return nodes;
    }

    public static void main(String[] args) {
        Graph g = new Graph("C:\\Users\\Nofar\\Desktop\\מטלה1\\test_Ex1.txt","C:\\Users\\Nofar\\Desktop\\מטלה1\\test1_Ex1_run.txt");
    }

}