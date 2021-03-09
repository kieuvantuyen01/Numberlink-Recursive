//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import javax.swing.JFrame;

class Image2dViewer extends JFrame {
    private static final long serialVersionUID = -7498525833438154949L;
    static int xLocation = 0;
    public Image2d img;

    public Image2dViewer(Image2d img) {
        this.img = img;
        this.setLocation(xLocation, 0);
        this.setDefaultCloseOperation(2);
        this.add(new Image2dComponent(img));
        this.pack();
        this.setVisible(true);
        xLocation += img.getWidth();
    }
}
