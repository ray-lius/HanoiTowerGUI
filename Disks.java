import java.util.Random;
import java.awt.Color;

public class Disks{
    private int x, y , width, height;
    private Color color;

    Random random = new Random();
    float a,b,c;

    Disks(){
        this.width = 0;
        this.height = 0;
        this.x = 0;
        this.y = 0;

        a = random.nextFloat();
        b = random.nextFloat();
        c = random.nextFloat();
        this.color = new Color(a, b, c);
    }
    //setter
    void setX(int x){
        this.x = x;
    }
    void setY(int y){
        this.y = y;
    }
    void setWidth(int width){
        this.width = width;
    }
    void setHeight(int height){
        this.height = height;
    }
    void setColor(Color color) {this.color = color;}

    //getter
    int getX(){
        return this.x;
    }
    int getY(){
        return this.y;
    }
    int getWidth(){
        return this.width;
    }
    int getHeight(){
        return this.height;
    }
    Color getColor(){
        return this.color;
    }
}