import javax.swing.JLabel;
import java.awt.Font;

public class MyLabel extends JLabel{
    MyLabel(String str){
        super(str);
        this.setFont(new Font("Times New Roman", Font.PLAIN, 15));
    }

}