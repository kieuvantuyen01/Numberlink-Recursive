import javax.naming.TimeLimitExceededException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

public class Main {
    public static final int TIMEOUT = 1500;
    static String inputPath = "./input";
    static String outputPath = "./out/out.txt";
    public static File inputFolder = new File(inputPath);
    public static File outputFolder = new File(outputPath);
    public static Numberlink numberlink = null;
    public static int maxNum = 0;

//    public static void outputToTxt(String res, File outFile) {
//        try {
//            FileWriter writer = new FileWriter(outFile,true);
//            writer.write(res + "\n");
//            writer.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
    private static int getMaxNum(int[][] matrix) {
        int maxNum = 0;
        for (int i = 1; i < matrix.length; i++) {
            for (int j = 1; j < matrix[0].length; j++) {
                if (maxNum < matrix[i][j]) {
                    maxNum = matrix[i][j];
                }
            }
        }
        return maxNum;
    }

    public static void readFile(File file) throws FileNotFoundException {
        Scanner sc = new Scanner(file);
        List<List<String>> matrix = new ArrayList<>();
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            List<String> arr = Arrays.asList(line.split(" "));
            matrix.add(arr);
        }
        sc.close();
        int rows = matrix.size();
        int cols = matrix.get(0).size();

        int[][] input = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                input[i][j] = Integer.parseInt(matrix.get(i).get(j));
            }
        }
        maxNum = getMaxNum(input);
        System.out.println("rows: " + rows + " cols: " + cols + " maxNum: " + maxNum);
        numberlink = new Numberlink(rows, cols, maxNum, input);
    }

    public static void process(final File folder) throws InterruptedException, IOException, TimeoutException  {
        if (folder.listFiles() == null) return;
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                process(fileEntry);
            } else {
                if (fileEntry.isFile()) {
//                    String fileInfo = "";
                    String fileName = fileEntry.getName();
                    if ((fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase()).equals("in")) {
//                        final String[] time = {""};
                        ExecutorService executor = Executors.newSingleThreadExecutor();
                        Future<?> future = executor.submit(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    readFile(fileEntry);
                                    new Image2dViewer(numberlink.CreateNumberlink());
                                    int[] flow = new int[maxNum];
                                    for (int i = 0; i < maxNum; i++) {
                                        flow[i] = 0;
                                    }
                                    long t0 = System.currentTimeMillis();
//                                    status = String.valueOf(numberlink.NumberlinkSolver(numberlink.map, numberlink.LabelEndPosition(), numberlink.LabelFirstPosition(),0, flow));
                                    System.out.println((numberlink.NumberlinkSolver(numberlink.map, numberlink.LabelEndPosition(), numberlink.LabelFirstPosition(),0, flow)));
                                    long tf = System.currentTimeMillis();
//                                    time[0] = String.valueOf(tf - t0);
                                    System.out.println("Time: " + (tf - t0) + " ms");
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        });
                        try {
                            future.get(TIMEOUT, TimeUnit.SECONDS);  //     wait Time (seconds) to finish
                        } catch (InterruptedException e) {    //     possible error cases
                            System.out.println("job was interrupted");
                        } catch (ExecutionException e) {
                            System.out.println("caught exception: " + e.getCause());
                        } catch (java.util.concurrent.TimeoutException e) {
                            future.cancel(true);              //     interrupt the job
//                            time[0] = "timeout";
                            System.out.println("timeout");
//                            status = "UNSAT";
                            System.out.println("UNSAT");
                        } finally {
                            executor.shutdownNow();           //     always reclaim resources
                        }
//                        fileInfo = fileName + "\t" + numberlink.height + "x" + numberlink.width + "\t" + maxNum + "\t" + time[0];
                    }
//                    outputToTxt(fileInfo, outputFolder);
                }
            }
        }
    }
    public static void main(String[] args) throws IOException, InterruptedException, TimeoutException {
        process(inputFolder);
    }

    public static void mainBak(String[] args) {

        int[][] test = {{4, 0, 0, 0, 0, 0, 7},
                {0, 0, 6, 0, 3, 0, 1},
                {0, 0, 4, 6, 7, 0, 0},
                {0, 0, 0, 1, 0, 5, 0},
                {0, 3, 0, 2, 0, 0, 0},
                {0, 0, 0, 0, 0, 5, 0},
                {2, 0, 0, 0, 0, 0, 0}};
        int[][] test2 = {{0, 0, 0, 0, 0},
                {2, 0, 0, 1, 0},
                {0, 0, 0, 0, 0},
                {1, 0, 0, 2, 0},
                {4, 0, 4, 3, 3}};
        int[][] test21 = {{0, 0, 0, 3, 0},
                {0, 4, 0, 1, 0},
                {0, 0, 2, 0, 0},
                {0, 4, 0, 3, 2},
                {0, 1, 0, 0, 0}};
        int[][] test3 = {{4, 2, 3, 0, 1, 5},
                {0, 0, 0, 0, 6, 0},
                {0, 0, 3, 0, 0, 0},
                {0, 0, 1, 0, 0, 0},
                {4, 0, 6, 0, 0, 0},
                {2, 0, 5, 0, 0, 0}};
        int[][] test4 = {{0, 0, 0, 0, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 2, 0, 0},
                {0, 0, 3, 0, 4, 0, 3, 0, 0},
                {0, 0, 0, 0, 5, 6, 2, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 7, 4, 5, 0, 0, 1, 0, 0},
                {0, 0, 0, 0, 0, 6, 0, 0, 0},
                {8, 0, 0, 0, 0, 8, 7, 0, 0}};
        int[][] test5 = {{0, 3, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 2, 1},
                {0, 16, 0, 15, 0, 0, 0, 0, 14, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 7, 0, 0, 13, 0, 12, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 6, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 12, 0, 5, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0},
                {0, 0, 0, 0, 0, 15, 0, 0, 0, 0, 3, 0, 0, 0, 0},
                {0, 0, 0, 6, 0, 0, 0, 0, 0, 0, 17, 0, 0, 11, 0},
                {0, 0, 0, 14, 0, 0, 0, 0, 0, 0, 0, 16, 0, 0, 0},
                {0, 0, 0, 10, 0, 0, 0, 0, 0, 0, 0, 11, 17, 0, 0},
                {0, 0, 0, 0, 0, 9, 8, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 9, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 8, 10, 0, 13, 0, 0, 0, 2, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};
        Numberlink test0 = new Numberlink(7, 7, 7, test);
        Numberlink test02 = new Numberlink(5, 5, 4, test2);
        Numberlink test021 = new Numberlink(5, 5, 4, test21);
        Numberlink test03 = new Numberlink(6, 6, 6, test3);
        Numberlink test04 = new Numberlink(9, 9, 8, test4);
        Numberlink test05 = new Numberlink(15,15,17,test5);
        //new Image2dViewer(test0.CreateNumberlink());
        //new Image2dViewer(test02.CreateNumberlink());
        //new Image2dViewer(test021.CreateNumberlink());
        //new Image2dViewer(test03.CreateNumberlink());
        //new Image2dViewer(test04.CreateNumberlink());
        new Image2dViewer(test05.CreateNumberlink());
        int[] flow = {0, 0, 0, 0, 0, 0, 0};
        int[] flow2 = {0, 0, 0, 0};
        int[] flow3 = {0, 0, 0, 0, 0, 0};
        int[] flow4 = {0, 0, 0, 0, 0, 0, 0, 0};
        int[] flow5 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        // System.out.println((test0.NumberlinkSolver(test0.map,test0.LabelEndPosition(),test0.LabelFirstPosition(),0,flow)));
        //System.out.println((test02.NumberlinkSolver(test02.map,test02.LabelEndPosition(),test02.LabelFirstPosition(),0,flow2)));
        //System.out.println((test021.NumberlinkSolver(test021.map,test021.LabelEndPosition(),test021.LabelFirstPosition(),0,flow2)));
        //System.out.println((test03.NumberlinkSolver(test03.map, test03.LabelEndPosition(), test03.LabelFirstPosition(), 0, flow3)));
        long t0 = System.currentTimeMillis();
        System.out.println((test04.NumberlinkSolver(test04.map,test04.LabelEndPosition(),test04.LabelFirstPosition(),0,flow4)));
        //System.out.println((test05.NumberlinkSolver(test05.map, test05.LabelEndPosition(), test05.LabelFirstPosition(), 0, flow5)));

        long tf = System.currentTimeMillis();
        System.out.println("Computation time real: " + (tf - t0) + " ms");


    }
}
