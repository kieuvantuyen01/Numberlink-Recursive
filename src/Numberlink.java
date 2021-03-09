import java.awt.Color;
import java.util.LinkedList;

public class Numberlink {
    int width;
    int height;
    int labelmax;
    int[][] map;
    static int MAX = Integer.MAX_VALUE;

    Numberlink(int w, int h, int l, int[][] m) {
        this.width = w;
        this.height = h;
        this.labelmax = l;
        this.map = m;
    }

    //Create a Grid
    public static Image2d makequadrillage(int w, int h) {
        Image2d img = new Image2d(256);
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                img.addPolygon(new int[]{25 + 25 * j, 25 + 25 * j, 25 + (j + 1) * 25, 25 + (j + 1) * 25}, new int[]{25 + 25 * i, 25 + 25 * (i + 1), 25 + 25 * (i + 1), 25 + 25 * i}, Color.WHITE, Color.BLACK);
            }
        }
        return img;
    }

    //Create a square
    public static Image2d makesquareat(int xpos, int ypos, Color color) {
        Image2d img = new Image2d(256);
        img.addPolygon(new int[]{25 + 25 * xpos, 25 + 25 * xpos, 25 + 25 * (xpos + 1), 25 + 25 * (xpos + 1)}, new int[]{25 + 25 * ypos, 25 + 25 * (ypos + 1), 25 + 25 * (ypos + 1), 25 + 25 * ypos}, color, Color.BLACK);
        return img;
    }

    //Create a Numberlink
    Image2d CreateNumberlink() {
        Image2d img = new Image2d(600);
        Color[] couleurs = new Color[19];
        int w = this.width;
        int h = this.height;
        int[][] m = this.map;
        couleurs[0] = Color.WHITE;
        System.setProperty("one", "#6fbfed");
        couleurs[1] = Color.getColor("one");
        System.setProperty("two", "#edbee8");
        couleurs[2] = Color.getColor("two");
        System.setProperty("three", "#eb5763");
        couleurs[3] = Color.getColor("three");
        System.setProperty("four", "#eb9a57");
        couleurs[4] = Color.getColor("four");
        System.setProperty("five", "#57eb88");
        couleurs[5] = Color.getColor("five");
        System.setProperty("six", "#5763eb");
        couleurs[6] = Color.getColor("six");
        System.setProperty("seven", "#cd82f5");
        couleurs[7] = Color.getColor("seven");
        System.setProperty("eight", "#f5e282");
        couleurs[8] = Color.getColor("eight");
        System.setProperty("nine", "#bcf582");
        couleurs[9] = Color.getColor("nine");
        System.setProperty("ten", "#818a86");
        couleurs[10] = Color.getColor("ten");
        System.setProperty("eleven", "#0e4380");
        couleurs[11] = Color.getColor("eleven");

        System.setProperty("twelve", "#800e2b");
        couleurs[12] = Color.getColor("twelve");
        System.setProperty("thirteen", "#805e0e");
        couleurs[13] = Color.getColor("thirteen");
        System.setProperty("fourteen", "#54800e");
        couleurs[14] = Color.getColor("fourteen");
        System.setProperty("fifteen", "#0e806d");
        couleurs[15] = Color.getColor("fifteen");

        System.setProperty("sixteen", "##9fe39d");
        couleurs[16] = Color.getColor("sixteen");
        System.setProperty("seventeen", "#80094c");
        couleurs[17] = Color.getColor("seventeen");

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                img.add(makesquareat(i, j, couleurs[m[i][j]]));
            }
        }
        return img;
    }

    //Initialize the initial conditions
    //Luư điểm cuối
    public int[][] LabelEndPosition() {
        int width = this.width;
        int height = this.height;
        int labelmax = this.labelmax;
        int[][] map = this.map;
        int[][] LabelLastPosition = new int[labelmax][2];
        int[][] LabelEndPosition = new int[labelmax][2];
        int[] LabelVisited = new int[labelmax];
        for (int i = 0; i < labelmax; i++) {
            LabelVisited[i] = 2;
        }
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (map[i][j] > 0 && LabelVisited[map[i][j] - 1] > 0) {
                    //con 2 diem chua luu
                    if (LabelVisited[map[i][j] - 1] == 2) {
                        LabelLastPosition[map[i][j] - 1][0] = i;
                        LabelLastPosition[map[i][j] - 1][1] = j;
                        LabelVisited[map[i][j] - 1] -= 1;
                    }
                    //luu diem cuoi cung
                    else {
                        LabelEndPosition[map[i][j] - 1][0] = i;
                        LabelEndPosition[map[i][j] - 1][1] = j;
                    }
                }
            }
        }
        //System.out.println(LabelEndPosition[0][0]);
        //System.out.println(LabelEndPosition[0][1]);
        return LabelEndPosition;
    }

    //Initialize the final positions
    //Lưu điểm đầu
    public int[][] LabelFirstPosition() {
        int width = this.width;
        int height = this.height;
        int labelmax = this.labelmax;
        int[][] map = this.map;
        int[][] LabelLastPosition = new int[labelmax][2];
        int[][] LabelEndPosition = new int[labelmax][2];
        int[] LabelVisited = new int[labelmax];
        for (int i = 0; i < labelmax; i++) {
            LabelVisited[i] = 2;
        }
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (map[i][j] > 0 && LabelVisited[map[i][j] - 1] > 0) {
                    if (LabelVisited[map[i][j] - 1] == 2) {
                        LabelLastPosition[map[i][j] - 1][0] = i;
                        LabelLastPosition[map[i][j] - 1][1] = j;
                        LabelVisited[map[i][j] - 1] -= 1;
                    } else {
                        LabelEndPosition[map[i][j] - 1][0] = i;
                        LabelEndPosition[map[i][j] - 1][1] = j;
                    }
                }
            }
        }
        //System.out.println(LabelLastPosition);
        return LabelLastPosition;
    }

    //Solves Numberlink
    boolean NumberlinkSolver(int[][] map, int[][] LabelLastPosition, int[][] LabelEndPosition, int SolveCounter, int[] FlowDone) {
        long t0 = System.currentTimeMillis();
        //RESOLUTION
        if (SolveCounter == this.labelmax && !HaveZeros(map)) {
            Numberlink solved = new Numberlink(this.width, this.height, this.labelmax, map);
            new Image2dViewer(solved.CreateNumberlink());
            long tf = System.currentTimeMillis();
            System.out.println("Computation time : " + (tf - t0) + " ms");
            return true;
        }
        int width = this.width;
        int height = this.height;
        int labelmax = this.labelmax;

        //ô xung quanh
        int[][] neighborSquare = new int[labelmax][4];
        //số ô trống??
        int[] numOfEmptySquare
                = new int[labelmax];
        //gán giá trị cho các ô xung quanh vào mảng voisin...
        for (int i = 0; i < labelmax; i++) {
            voisinestlibre(i, LabelLastPosition[i][0], LabelLastPosition[i][1], width, height, map, neighborSquare, numOfEmptySquare
            );
            if (FlowDone[i] == 1) {
                numOfEmptySquare
                        [i] = MAX;
            }
        }
        //Index with least neighbors
        int ind = IndexOfSmallest(numOfEmptySquare
        );
        SolveCounter = 0;
        for (int x = 0; x < 4; x++) {
            if (neighborSquare[ind][x] == -1) {
                switch (x) {
                    case 0:
                        LabelLastPosition[ind][0] -= 1;
                        break;
                    case 1:
                        LabelLastPosition[ind][1] += 1;
                        break;
                    case 2:
                        LabelLastPosition[ind][0] += 1;
                        break;
                    case 3:
                        LabelLastPosition[ind][1] -= 1;
                        break;
                }
                map[LabelLastPosition[ind][0]][LabelLastPosition[ind][1]] = ind + 1;
                for (int i = 0; i < labelmax; i++) {
                    if (this.CheckSolved(i, LabelEndPosition, LabelLastPosition)) {
                        SolveCounter += 1;
                    }
                }
                if (this.CheckSolved(ind, LabelEndPosition, LabelLastPosition)) {
                    FlowDone[ind] = 1;
                }
                if (this.NumberlinkSolver(map, LabelLastPosition, LabelEndPosition, SolveCounter, FlowDone)) {
                    return true;
                } else {
                    map[LabelLastPosition[ind][0]][LabelLastPosition[ind][1]] = 0;
                    switch (x) {
                        case 0:
                            LabelLastPosition[ind][0] += 1;
                            break;
                        case 1:
                            LabelLastPosition[ind][1] -= 1;
                            break;
                        case 2:
                            LabelLastPosition[ind][0] -= 1;
                            break;
                        case 3:
                            LabelLastPosition[ind][1] += 1;
                            break;
                    }
                    FlowDone[ind] = 0;
                }
            }
            SolveCounter = 0;
        }
        return false;
    }

    //0 --> left
    //1 --> down
    //2 --> right
    //3 --> left up
    //kiểm tra xq ô label là ô trống (-1) hay value(value)
    public static void voisinestlibre(int label, int xpos, int ypos, int width, int height, int[][] map, int[][] neighborSquare, int[] nombre) {
        //đi lên
        if (xpos > 0) {
            if (map[xpos - 1][ypos] == 0) {
                neighborSquare[label][0] = -1;
                nombre[label]++;
            } else {
                neighborSquare[label][0] = map[xpos - 1][ypos];
            }
        }
        if (ypos < height - 1) {
            if (map[xpos][ypos + 1] == 0) {
                neighborSquare[label][1] = -1;
                nombre[label]++;
            } else {
                neighborSquare[label][1] = map[xpos][ypos + 1];
            }
        }
        if (xpos < width - 1) {
            if (map[xpos + 1][ypos] == 0) {
                neighborSquare[label][2] = -1;
            } else {
                neighborSquare[label][2] = map[xpos + 1][ypos];
            }
        }
        if (ypos > 0) {
            if (map[xpos][ypos - 1] == 0) {
                neighborSquare[label][3] = -1;
            } else {
                neighborSquare[label][3] = map[xpos][ypos - 1];
            }
        }
    }

    //check is the label's flow is done
    public boolean CheckSolved(int label, int[][] LabelEndPosition, int[][] LabelLastPosition) {
        int width = this.width;
        int height = this.height;
        //KIỂM TRA ĐÃ ĐI TỚI VỊ TRÍ CUỐI CHƯA??
        if (LabelLastPosition[label][0] - 1 >= 0 && LabelLastPosition[label][0] - 1 == LabelEndPosition[label][0]) {
            if (LabelLastPosition[label][1] == LabelEndPosition[label][1]) {
                return true;
            }
        }
        if (LabelLastPosition[label][0] + 1 < height && LabelLastPosition[label][0] + 1 == LabelEndPosition[label][0]) {
            if (LabelLastPosition[label][1] == LabelEndPosition[label][1]) {
                return true;
            }
        }
        if (LabelLastPosition[label][1] - 1 >= 0 && LabelLastPosition[label][1] - 1 == LabelEndPosition[label][1]) {
            if (LabelLastPosition[label][0] == LabelEndPosition[label][0]) {
                return true;
            }
        }
        if (LabelLastPosition[label][1] + 1 < width && LabelLastPosition[label][1] + 1 == LabelEndPosition[label][1]) {
            if (LabelLastPosition[label][0] == LabelEndPosition[label][0]) {
                return true;
            }
        }
        return false;
    }

    public static int IndexOfSmallest(int[] a) {
        int b = a[0];
        int c = 0;
        for (int i = 1; i < a.length; i++) {
            if (a[i] < b) {
                b = a[i];
                c = i;
            }
        }
        return c;
    }

    //Gave Zeros
    public static boolean HaveZeros(int[][] map1) {
        int rows = map1.length;
        int cols = map1[0].length;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (map1[i][j] == 0) {
                    return true;
                }
            }
        }
        return false;
    }
}
