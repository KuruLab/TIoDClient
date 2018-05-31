/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import evoGraph.Config;
import evoGraph.EvoJSONFileWriter;
import evoGraph.GraphImageBuilder;
import evoGraph.GraphIndividual;
import evoPuzzle.PuzzleConfig;
import evoPuzzle.PuzzleDecoder;
import evoPuzzle.PuzzleIndividual;
import graphstream.GraphStreamUtil;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import org.graphstream.algorithm.generator.BarabasiAlbertGenerator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.geom.Point3;
import static org.graphstream.ui.graphicGraph.GraphPosLengthUtils.nodePointPosition;
import twoStageEvo.TwoStageGA;

/**
 *
 * @author Kurumin
 */
public class DungeonGenerator implements Runnable {
    
    private String dungeonName;
    
    private JProgressBar progressbar;
    private JLabel status;
    private TwoStageGA ga;
    private String baseStatus;
    protected boolean finished;

    public DungeonGenerator(String dgName, JProgressBar progressbar, JLabel statusLabel) {
        this.dungeonName = dgName;
        this.progressbar = progressbar;
        this.status = statusLabel;
    }

    public String getDungeonName() {
        return dungeonName;
    }

    public void setDungeonName(String dungeonName) {
        this.dungeonName = dungeonName;
    }

    public JProgressBar getProgressbar() {
        return progressbar;
    }

    public void setProgressbar(JProgressBar progressbar) {
        this.progressbar = progressbar;
    }

    public JLabel getStatus() {
        return status;
    }

    public void setStatus(JLabel status) {
        this.status = status;
    }

    public TwoStageGA getGa() {
        return ga;
    }

    public void setGa(TwoStageGA ga) {
        this.ga = ga;
    }

    public String getBaseStatus() {
        return baseStatus;
    }

    public void setBaseStatus(String baseStatus) {
        this.baseStatus = baseStatus;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
        this.ga.setFinished(finished);
    }

    @Override
    public void run() {
        baseStatus = "Generating";
        finished = false;
        
        graphConfigSetup();
        puzzleConfigSetup();

        ga = new TwoStageGA();
        
        viewBar();
        viewStatus();
        
        ga.run();
        if(!finished){
            baseStatus = "Decoding";

            GraphIndividual individual = ga.getBestIndividual();
            PuzzleIndividual puzzle = ga.getBestPuzzleIndividual();

            PuzzleDecoder decoder = new PuzzleDecoder(individual);
            Graph g = decoder.decode(puzzle, false);

            GraphStreamUtil gUtil = new GraphStreamUtil();
            g.addAttribute("ui.stylesheet", "url('data" + File.separator + "media" + File.separator + "stylesheet.css')");
            gUtil.normalizeNodesSizes(g, Config.minNodeSize, Config.maxNodeSize);
            gUtil.setupStyle(g);

            baseStatus = "Exporting Dungeon";
            exportGraph(g);
            baseStatus = "Dungeon Generated.";
        }
        else{
            baseStatus = "Cancelling";
        }
        finished = true;
    }

    private void puzzleConfigSetup() {
        PuzzleConfig.popSize = 100;
        PuzzleConfig.maxGen = 50;
        PuzzleConfig.crossoverProb = 0.9;
        PuzzleConfig.mutationProb = 0.1;
    }

    private void graphConfigSetup() {
        Config.borderSize = 15;
        Config.edgeSize = 6;

        Config.minNodeCount = 25;
        Config.maxNodeCount = 100;

        Config.minEdgeDistance = 5;

        Config.areaIntersectionPenalty = 100;
        Config.edgeIntersectionPenalty = 1000;
        Config.nodeCountPenalty = 1000;
        Config.edgeDistancePentalty = 1000;

        Config.minNodeSize = 15.0;
        Config.maxNodeSize = 30.0;
        Config.scaleFactor = 50;

        Config.barabasiFactor = 5; // delta
        Config.maxLinksPerStep = 2; // m

        Config.crossoverProb = 0.90;
        Config.mutationProb = 0.10;
        Config.refinementProb = 0.01;

        Config.nodeXLeap = 1.1;
        Config.nodeYLeap = 1.1;
        Config.nodeZLeap = 0;

        Config.popSize = 100;
        Config.maxGen = 100;

        Config.desiredAngles = new int[3];
        Config.desiredAngles[0] = 0;
        Config.desiredAngles[1] = 90;
        Config.desiredAngles[2] = 180;

        Config.idealNonLinearity = 3;

        Config.useDesiredAngles = true;
        Config.useAverageShortestPath = true;
        Config.useMaximizeNodeCount = true;
        Config.useIdealNonLinearity = true;
        Config.useRefinement = true;

        Config.numberOfProcess = Runtime.getRuntime().availableProcessors() * 4;

        Config.folder = "." + File.separator + "data" + File.separator + "dungeons" + File.separator + "";
    }

    private void viewBar() {
        progressbar.setStringPainted(true);
        progressbar.setValue(0);

        int timerDelay = 10;
        new javax.swing.Timer(timerDelay, new ActionListener() {
            int previousGen = 0;
            public void actionPerformed(ActionEvent e) {
                int progress = Math.round(((float) ga.getCurrentGeneration() / (float) Config.maxGen) * 100f);
                progressbar.setValue(progress);
                
                if (previousGen < ga.getCurrentGeneration() && previousGen > 0) {
                    // ?
                }
                if (progress >= 100 || finished) {
                    ((javax.swing.Timer) e.getSource()).stop(); // stop the timer
                }
                previousGen = ga.getCurrentGeneration();
            }
        }).start();

        progressbar.setValue(progressbar.getMinimum());
    }
    
    private void viewStatus(){
        status.setText(baseStatus);
        int timerDelay = 1000;
        new javax.swing.Timer(timerDelay, new ActionListener() {
            int count = 0;
            public void actionPerformed(ActionEvent e) {
                count++;
                String dots = "";
                for(int i = 0; i < count%4; i++){
                    dots+=".";
                }
                status.setText(baseStatus+dots);
                
                if(finished){
                    ((javax.swing.Timer) e.getSource()).stop(); // stop the timer
                    status.setText(baseStatus);
                }
            }
            
        }).start();
    }
    
    public void exportGraph(Graph g){
        
        if(dungeonName.isEmpty()){
            dungeonName = ""+g.hashCode();
        }
        
        File dir = new File(Config.folder, dungeonName);
        if(!dir.exists()){
            dir.mkdir();
        }
        else{
            System.err.println("Attention: Dir "+dir+" already exists!");
        }
        int maxX = 0;
        int maxY = 0;
        for(Node node : g.getEachNode()){
            Point3 p = nodePointPosition(node);
            maxX = (int) Math.max(maxX, p.x + ((double) node.getAttribute("width")/2));
            maxY = (int) Math.max(maxY, p.y + ((double) node.getAttribute("height")/2));
        }
        EvoJSONFileWriter fw = new EvoJSONFileWriter(g);
        System.out.println("Exporting Map\n"+
                File.separator+"data_"+dungeonName+".json\n"+
                File.separator+"map_"+dungeonName+".json");
        fw.exportDataJSON(dir+File.separator+"data_"+dungeonName+".json",
                "GraphStream", maxX, maxY, true);
        fw.exportMapJSON(dir+File.separator+"map_"+dungeonName+".json",
                "Evolutionary"+BarabasiAlbertGenerator.class.getSimpleName(), true);
        
        // export PNG
        GraphImageBuilder gib = new GraphImageBuilder(dir+File.separator+"img_"+dungeonName+".png");
        gib.buildImage(g);
    }
}
