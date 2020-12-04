import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;

public class ImageShow extends Canvas{
    private BufferedImage bufferedImage;
    private ByteArrayInputStream bis;
    public ImageShow(ByteArrayInputStream bis){
        this.bis = bis;
    }

    private void getImage(){
        try {
            this.bufferedImage = ImageIO.read(this.bis);
        } catch (Exception exception) {
            System.out.println("File does not exist");
        }
    }
    public void paint(Graphics g) {
        getImage();
        g.drawImage(this.bufferedImage, 0,0,this);
    }

    public void run() {
        ImageShow m=new ImageShow(this.bis);
        JFrame f=new JFrame();
        f.add(m);
        f.setSize(400,400);
        f.setVisible(true);
    }
}
