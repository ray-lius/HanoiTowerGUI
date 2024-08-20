import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;

public class MyButton extends JButton{
    MyButton(String str){
        super(str);
        this.setBorderPainted(false);
        this.setFocusPainted(false);
        this.setContentAreaFilled(false);// avoid the base class rewrite the aera; get rid of the FocusPainted;
        this.setFont(new Font("Elephant", Font.BOLD + Font.ITALIC, 25));

        this.addMouseListener( new MouseAdapter(){
            @Override
            public void mouseEntered(MouseEvent e){
                setBackground(Color.LIGHT_GRAY);
                setContentAreaFilled(true);
                repaint();
            }
            @Override
            public void mouseExited(MouseEvent e){
                setBackground(null);
                setContentAreaFilled(false);
                repaint();
            }
        });
    }
}