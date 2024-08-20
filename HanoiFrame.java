import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class HanoiFrame extends JFrame {

    private static int num = 1;// the number of disks;
    private static String text_process = "";//text for all procedure content;
    private static ArrayList<String> steps = new ArrayList<String>(); //ArrayList for all steps info;

    //create these elements for the animation;
    Disks[] column = new Disks[3];
    Disks[] disk = new Disks[6];
    HanoiPanel panel;
    JLabel solved_label = new JLabel("Solved !!!");//set the finish label;

    // set the values to play the the calculation of animation;
    private static int column_A, column_B, column_C, column_remain;// the number of disks in every column;
    private static int step_length, disk_num, middle_line;
    private static String action_from, action_to;
    private static boolean right_direction, left_direction;
    private static Thread aThread = new Thread();

    HanoiFrame() {

        this.setTitle("Hannoi games");
        this.setSize(865, 600);

        //set a text-area for print the result
        JTextArea ta = new JTextArea(" Steps shows here !");
        ta.setBackground(null); // clear the background;
        ta.setBorder(BorderFactory.createEmptyBorder());//set border empty;
        JScrollPane jsp = new JScrollPane(ta);
        jsp.setBounds(600, 0, 250, 450);
        jsp.setBorder(BorderFactory.createEmptyBorder());//set border empty
        this.getContentPane().add(jsp);


        //set combobox to set the num;
        String[] choice = {"1", "2", "3", "4", "5", "6"};
        JComboBox cb = new JComboBox(choice);
        cb.setBounds(50, 500, 70, 30);
        this.getContentPane().add(cb);

        MyLabel l_1 = new MyLabel("Pole A");
        MyLabel l_2 = new MyLabel("Pole B");
        MyLabel l_3 = new MyLabel("Pole C");
        MyLabel l_4 = new MyLabel("Choose the number of disks");
        MyLabel l_5 = new MyLabel("Play the Animation");
        MyLabel l_6 = new MyLabel("Show all Steps");
        l_1.setBounds(80,450,50,30);
        l_2.setBounds(280,450,50,30);
        l_3.setBounds(480,450,50,30);
        l_4.setBounds(50,470,200,30);
        l_5.setBounds(350,470,200,30);
        l_6.setBounds(625,470,150,30);
        this.getContentPane().add(l_1);
        this.getContentPane().add(l_2);
        this.getContentPane().add(l_3);
        this.getContentPane().add(l_4);
        this.getContentPane().add(l_5);
        this.getContentPane().add(l_6);
        // show the problem solved label;

        solved_label.setBounds(0,0,0,0);
        solved_label.setFont(new Font("Elephant", Font.BOLD + Font.ITALIC, 35));
        solved_label.setForeground(Color.MAGENTA);// RGB value consisting of the red component in bits 16-23;
        this.getContentPane().add(solved_label);

        //set three button to act the action;
        MyButton b_1 = new MyButton("sure");
        MyButton b_2 = new MyButton("play");
        MyButton b_3 = new MyButton("show");
        b_1.setBounds(120,500,150,30);
        b_2.setBounds(325,500,150,30);
        b_3.setBounds(600,500,150,30);

//        b_1.setBorderPainted(false);
//        b_1.setFocusPainted(false);
//        b_1.setBackground(Color.LIGHT_GRAY);
////        b_1.setContentAreaFilled(false);// avoid the base class rewrite the aera; get rid of the FocusPainted;
//        b_1.setFont(new Font("Elephant", Font.BOLD + Font.ITALIC, 25));

        this.getContentPane().add(b_1);
        this.getContentPane().add(b_2);
        this.getContentPane().add(b_3);

        //draw three sticks A B C;
        for (int i = 0; i < 3; i++) {
            column[i] = new Disks();
            column[i].setX(95+200*i);//stick in defferent postion
            column[i].setY(250);
            column[i].setWidth(10);
            column[i].setHeight(200);
        }
        // draw disks (max =6)  in the panel;
        for (int i=0; i<6; i++){
            disk[i] = new Disks();
        }
        //add all elements to the panel;
        panel = new HanoiPanel(column[0],column[1],column[2],disk[0],disk[1],disk[2],disk[3],disk[4],disk[5]);
        panel.setBounds(0,0,600,450);
        this.getContentPane().add(panel);//add to the default contentPane;
        //this.add(panel);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setLocation(250, 50);
        this.setResizable(true);
        this.setVisible(true);

        // action : choose the number of disks;
        b_1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String number = (String)cb.getSelectedItem();
                num = Integer.parseInt(number);
                //re-set all disk component each loop;
                initializeDisks();
            }
        });

         // action to show the full process test;
        b_3.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                steps.clear();
                text_process = ""; //initilize text content;
                steps = towerOfHannoi(num, "A", "B", "C");
                int length = steps.size();
                for (int i = 0; i<length; i++){
                    text_process =
                            text_process + "  Move the disk " + steps.get(i).substring(0,1) + " form pole " + steps.get(i).
                            substring(1,2) +
                                    " to " +
                                    "pole " + steps.get(i).substring(2,3) + "\n";
                }
                ta.setText(text_process);
            }
        });
        // action to show all animation;
        b_2.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                initializeDisks();
                steps.clear();
                steps = towerOfHannoi(num, "A","B", "C");
                playAnimation();
            }
        });

    }

    //method to initialize all the column and disks;
    public void initializeDisks(){
        for (int i=0; i<6; i++){
            disk[i].setX(0);
            disk[i].setY(0);
            disk[i].setWidth(0);
            disk[i].setHeight(0);
        }
        for (int i=0; i<num; i++){
            disk[i].setX(85 - 15*i);
            disk[i].setY(450 - 20*num + 20 *i);
            disk[i].setWidth(30 + 30*i);
            disk[i].setHeight(20);
        }
        panel.repaint(0,0,600,450);
    }

    // method to paly the animation and
    public void playAnimation(){

        step_length = steps.size();
        disk_num = 0;
        column_A = num;
        column_B = 0;
        column_C = 0;
        middle_line =0;// set the original value for move middle line;
        column_remain = 0;// set the remian disks height on each column;

        new Thread(new Runnable(){
            public void run() {

            for (int i= 0; i < step_length; i++){

                disk_num = Integer.parseInt(steps.get(i).substring(0,1))-1;
                action_from = steps.get(i).substring(1,2);
                action_to = steps.get(i).substring(2,3);

                right_direction = false;
                left_direction = false;

                //identify the direction and the middle line;
                if(action_from.equals("A")){
                    if (action_to.equals("C")){  // from A to C;
                        middle_line = 300-15*disk_num-15;
                        right_direction = true;
                        column_A--;
                        column_C++;
                        column_remain = column_C*20;
                    } else {   // from A to B;
                        middle_line = 200-15*disk_num-15;
                        right_direction = true;
                        column_A--;
                        column_B++;
                        column_remain = column_B*20;
                    }
                }else if(action_from.equals("B")){
                    if (action_to.equals("C")){  // from B to C;
                        middle_line = 400-15*disk_num-15;
                        right_direction = true;
                        column_B--;
                        column_C++;
                        column_remain = column_C*20;
                    } else {    // from B to A;
                        middle_line = 200-15*disk_num-15;
                        left_direction = true;
                        column_B--;
                        column_A++;
                        column_remain = column_A*20;
                    }
                }else{
                    if (action_to.equals("B")){  // from C to B;
                        middle_line = 400-15*disk_num-15;
                        left_direction = true;
                        column_C--;
                        column_B++;
                        column_remain = column_B*20;
                    } else {  // from C to A;
                        middle_line = 300-15*disk_num-15;
                        left_direction = true;
                        column_C--;
                        column_A++;
                        column_remain = column_A*20;
                    }
                }

                while (right_direction) {
                    if (disk[disk_num].getY() > 230 && disk[disk_num].getX() < middle_line) {
                        disk[disk_num].setY(disk[disk_num].getY() - 10);
                    } else if (disk[disk_num].getY() <= 230 && disk[disk_num].getX() < middle_line) {
                        disk[disk_num].setX(disk[disk_num].getX() + 10);
                        disk[disk_num].setY(disk[disk_num].getY() - 10);
                    } else if (disk[disk_num].getY() < 230 && disk[disk_num].getX() >= middle_line) {
                        disk[disk_num].setX(disk[disk_num].getX() + 10);
                        disk[disk_num].setY(disk[disk_num].getY() + 10);
                    } else if (disk[disk_num].getY() >= 230 && disk[disk_num].getX() >= middle_line) {
                        disk[disk_num].setY(disk[disk_num].getY() + 10);
                    }
                    // termination condition;
                    if (disk[disk_num].getY() >= (450 - column_remain) && disk[disk_num].getX() >= middle_line) {
                        break;
                    }
                    // set delay time;
                    try {
                        Thread.sleep(35);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    panel.repaint(0, 0, 600, 450);

                }
                while (left_direction) {
                    if (disk[disk_num].getY() > 230 && disk[disk_num].getX() > middle_line) {
                        disk[disk_num].setY(disk[disk_num].getY() - 10);
                    } else if (disk[disk_num].getY() <= 230 && disk[disk_num].getX() > middle_line) {
                        disk[disk_num].setX(disk[disk_num].getX() - 10);
                        disk[disk_num].setY(disk[disk_num].getY() - 10);
                    } else if (disk[disk_num].getY() < 230 && disk[disk_num].getX() <= middle_line) {
                        disk[disk_num].setX(disk[disk_num].getX() - 10);
                        disk[disk_num].setY(disk[disk_num].getY() + 10);
                    } else if (disk[disk_num].getY() >= 230 && disk[disk_num].getX() <= middle_line) {
                        disk[disk_num].setY(disk[disk_num].getY() + 10);
                    }
                    // termination condition;
                    if (disk[disk_num].getY() >= (450 - column_remain) && disk[disk_num].getX() <= middle_line) {
                        break;
                    }
                    // set delay time;
                    try {
                        Thread.sleep(35);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    panel.repaint(0, 0, 600, 450);
                }
            }
            solved_label.setBounds(225,150,200,60);
            }
        }).start();
    }

    // method for all steps-ArrayList;
    public static ArrayList<String> towerOfHannoi(int n, String from_pole, String to_pole, String aux_pole ){
        if (n == 1){
                steps.add(n + from_pole + to_pole);
                return steps;
        }
        towerOfHannoi(n-1,from_pole,aux_pole,to_pole);
        steps.add(n + from_pole + to_pole);
        towerOfHannoi(n-1,aux_pole,to_pole,from_pole);
        return steps;
    }

    public static void main(String[] args) {
        new HanoiFrame();
    }
}