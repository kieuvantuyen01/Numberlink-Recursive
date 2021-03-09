//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.awt.Color;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Image2d {
    private int width;
    private int height;
    private List<ColoredPolygon> coloredPolygons;
    private List<ColoredSegment> coloredSegments;

    public Image2d(int width, int height) {
        this.width = width;
        this.height = height;
        this.coloredPolygons = Collections.synchronizedList(new LinkedList());
        this.coloredSegments = Collections.synchronizedList(new LinkedList());
    }

    public Image2d(int size) {
        this.width = size;
        this.height = size;
        this.coloredPolygons = Collections.synchronizedList(new LinkedList());
        this.coloredSegments = Collections.synchronizedList(new LinkedList());
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public List<ColoredPolygon> getColoredPolygons() {
        return this.coloredPolygons;
    }

    public List<ColoredSegment> getColoredSegments() {
        return this.coloredSegments;
    }

    public void addPolygon(int[] xcoords, int[] ycoords, Color insideColor, Color boundaryColor) {
        this.coloredPolygons.add(new ColoredPolygon(xcoords, ycoords, insideColor, boundaryColor));
    }

    public void add(Image2d img) {
        while(!img.coloredPolygons.isEmpty()) {
            this.coloredPolygons.add((ColoredPolygon)img.coloredPolygons.remove(0));
        }

        while(!img.coloredSegments.isEmpty()) {
            this.coloredSegments.add((ColoredSegment)img.coloredSegments.remove(0));
        }

    }

    public void addSegment(int x1, int y1, int x2, int y2, int width, Color color) {
        this.coloredSegments.add(new ColoredSegment(x1, y1, x2, y2, width, color));
    }

    public void clear() {
        this.coloredPolygons = Collections.synchronizedList(new LinkedList());
        this.coloredSegments = Collections.synchronizedList(new LinkedList());
    }
}
