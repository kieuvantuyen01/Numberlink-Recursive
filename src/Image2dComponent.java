//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Iterator;
import javax.swing.JComponent;

class Image2dComponent extends JComponent {
    private static final long serialVersionUID = -7710437354239150390L;
    private Image2d img;

    public Image2dComponent(Image2d img) {
        this.img = img;
        this.setPreferredSize(new Dimension(img.getWidth(), img.getHeight()));
    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        Dimension d = this.getSize();
        g2.setBackground(Color.white);
        g2.clearRect(0, 0, d.width, d.height);
        Iterator var6;
        synchronized(this.img.getColoredPolygons()) {
            var6 = this.img.getColoredPolygons().iterator();

            while(true) {
                if (!var6.hasNext()) {
                    break;
                }

                ColoredPolygon coloredPolygon = (ColoredPolygon)var6.next();
                g2.setColor(coloredPolygon.insideColor);
                g2.fillPolygon(coloredPolygon.polygon);
                g2.setColor(coloredPolygon.boundaryColor);
                g2.drawPolygon(coloredPolygon.polygon);
            }
        }

        synchronized(this.img.getColoredSegments()) {
            var6 = this.img.getColoredSegments().iterator();

            while(var6.hasNext()) {
                ColoredSegment coloredSegment = (ColoredSegment)var6.next();
                g2.setColor(coloredSegment.color);
                g2.setStroke(new BasicStroke((float)coloredSegment.width));
                g2.drawLine(coloredSegment.x1, coloredSegment.y1, coloredSegment.x2, coloredSegment.y2);
            }

        }
    }
}
