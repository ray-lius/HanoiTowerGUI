import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Image;

public class HanoiPanel extends JPanel{

    private Image offScreenImage;  //set buffer image;
    private Disks[] disks;
    HanoiPanel(Disks... disks){
        this.disks = disks;
    }

    // draw all the component on the panel;

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        for (Disks disk :disks){
            g.setColor(disk.getColor());
            g.fillRect(disk.getX(), disk.getY(), disk.getWidth(), disk.getHeight());
        }
    }
    @Override
    public void update(Graphics g) {
        if(offScreenImage == null)
            offScreenImage = this.createImage(600,600);     //create a new buffer image,image size 800*600
        Graphics gImage = offScreenImage.getGraphics();  //give the Graphics to the gImage
        paintComponent(gImage);//put the panit info to the canvas
        gImage.dispose();
        g.drawImage(offScreenImage, 0, 0, null);   //show all things;
    }
}