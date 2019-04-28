package com.igeek.greedy_snake;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**  
* @typename ClientUI  
* @author NFUE  
* @Description: TODO(������һ�仰��������������)
* @date 2019��4��17�� ����7:13:59    
* @Company https://github.com/cptbaker233
*    
*/

class Snake {
    int x;
    int y;
    
    //���췽��
    public Snake(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

class GreedySnake implements Runnable {
    public static String direction = "��";
    public static String lastDirection = "��";
    public static ArrayList<Snake> al = new ArrayList<Snake>();
    
    public static Snake food = null;
    public static String[][] field;
    public static Snake tail;
    public static int speed = 200;
    static {
        field = new String[28][34];
        Random r = new Random();
        int a = r.nextInt(field.length -2) + 1;
        int b = r.nextInt(field[0].length - 2) + 1;
        al.add(new Snake(a, b));                            //�ڳ�����������ɵ�һ���ߵ�����
        respawn();
        ClientUI.food.setBounds(10 + food.y * 16, 18 + food.x * 18, 16, 18);
        update();
    }
    
    //��ӡ��ǰս���ķ���
    public static synchronized void update() {
        for (int i = 0; i < field.length; i ++) {                       //�Ȱ�������������Ϊ��ʼ����,ȫ��Ϊ��
            for (int j = 0; j < field[0].length; j ++) {
                field[i][j] = "ؼ ";
            }
        }
        //�ѱ߿������޸�
        for (int i = 0; i < field[0].length; i ++) {
            field[0][i] = "�D ";
        }
        for (int i = 0; i < field[field.length - 1].length; i ++) {
            field[field.length - 1][i] = "�D ";
        }
        for (int i = 0; i < field.length; i ++) {
            field[i][0] = "&nbsp&nbsp�D ";
            field[i][field[0].length - 1] = "�D ";
        }
        
        field[food.x][food.y] = "&nbsp&nbsp&nbsp&nbsp&nbsp";                                    //ʳ�������Ӧ�ĳ���Ϊ"��"
        for (int i = 0; i < al.size() - 1; i ++) {                      //�������Ӧ�ĳ���Ϊ"��"
            field[al.get(i).x][al.get(i).y] = "&nbsp&nbsp&nbsp&nbsp&nbsp";
        }
        field[al.get(al.size() - 1).x][al.get(al.size() - 1).y] = "&nbsp&nbsp&nbsp&nbsp&nbsp";  //��������,����ͷ����β������
        field[al.get(0).x][al.get(0).y] = "&nbsp&nbsp&nbsp&nbsp&nbsp";
        for (int i = 0; i < ClientUI.body.size(); i ++) {
            ClientUI.body.get(i).setBounds(10 + al.get(i).y * 16, 18 + al.get(i).x * 18, 16, 18);
        }
        
        String res = "<html>";
        for (String[] temp : field) {
            for (String str: temp) {
                res += str;
            }
            res += "<br />";
        }
        res += "</html>";
        ClientUI.yard.setText(res);
    }
    
    //ʳ����ײ���
    public static boolean hit() {
        if (food != null) {
            for (int i = 0; i < al.size(); i ++) {
                if (food.x == al.get(i).x && food.y == al.get(i).y) {         //������ʳ��֮�����ʳ�����������غ�,�ж���ײ
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }
    //����ʳ��ķ���
    public static void respawn() {
        Random r = new Random();
        food = new Snake(r.nextInt(field.length - 2) + 1, r.nextInt(field[0].length - 2) + 1);   //�������ʳ��
        while (hit()) {                                                                 //һֱ������ʳ��ֱ�����ɲ�������ײ��ʳ��
            if (al.size() == (field.length - 1) * (field[0].length - 1)) {                        //������Ѿ��ѳ��������,���ж�ͨ��
                ClientUI.opened = false;
                ClientUI.yard.setText("<html><p style=\"font-size:100px; align:center; color:red\">YOU WIN!!!</p></html>");
                Thread.currentThread().stop();
            }
            food = new Snake(r.nextInt(field.length - 2) + 1, r.nextInt(field[0].length - 2) + 1);
        }
    }
    //�жϳԵ�������
    public static synchronized boolean eat() {
        if (al.get(0).x == food.x && al.get(0).y == food.y) {   //�ƶ�֮��,�����ͷ������ʳ����ͬ,�ж�true
            ClientUI.food.setBounds(10 + tail.y * 16, 18 + tail.x * 18, 16, 18);
            ClientUI.body.add(ClientUI.food);
            al.add(tail);                   //����һ���ƶ�����β���������β
            respawn();
            ClientUI.respawn();
            update(); 
            return true;
        }
        return false;
    }
    
    //�õ��෴����ķ���
    public static String oppositeDir() {
        switch (lastDirection) {
        case "w":
            return "s";
        case "s" :
            return "w";
        case "a" : 
            return "d";
        case "d" : 
            return "a";
        default:
            return lastDirection;
        }
    }
    //�ƶ��ķ���
    public static Snake move() {
        switch (direction) {
        case "d":
            tail = new Snake(al.get(al.size() - 1).x, al.get(al.size() - 1).y);        
            if (al.size() == 1) {               //�߳���ֻ��1ʱ,ֱ���ƶ�
                al.get(0).y ++;
                lastDirection = direction;
            } else {            
                Snake head = new Snake(al.get(0).x, al.get(0).y + 1);
                al.remove(al.get(al.size() - 1));
                al.add(0, head);
                lastDirection = direction;
            }
            return tail;
            
        case "a":
            tail = new Snake(al.get(al.size() - 1).x, al.get(al.size() - 1).y);      
            if (al.size() == 1) {
                al.get(0).y --;
                lastDirection = direction;
            } else {
                Snake head = new Snake(al.get(0).x, al.get(0).y - 1);
                al.remove(al.get(al.size() - 1));
                al.add(0, head);
                lastDirection = direction;
            }
            return tail;
            
        case "w":
            tail = new Snake(al.get(al.size() - 1).x, al.get(al.size() - 1).y);       
            if (al.size() == 1) {
                al.get(0).x --;
                lastDirection = direction;
            } else {
                Snake head = new Snake(al.get(0).x - 1, al.get(0).y);
                al.remove(al.get(al.size() - 1));
                al.add(0, head);
                lastDirection = direction;
            }
            return tail;
            
        case "s":
            tail = new Snake(al.get(al.size() - 1).x, al.get(al.size() - 1).y);     
            if (al.size() == 1) {
                al.get(0).x ++;
                lastDirection = direction;
            } else {
                Snake head = new Snake(al.get(0).x + 1, al.get(0).y);
                al.remove(al.get(al.size() - 1));
                al.add(0, head);
                lastDirection = direction;
            }
            return tail;
            
        default:
            return tail;
        }
    }
  //��������ײ���ͱ߽���ײ���
    public static boolean suicide() {
        if (al.get(0).x == 0 || al.get(0).x == field.length - 1 || al.get(0).y == field[0].length - 1 || al.get(0).y == 0 ) {    //�����ͷ����������߽߱��غ�,�ж�true
            return true;
        }
        for (int i = 1; i < al.size() - 1; i ++) {
            if (al.get(i).x == al.get(0).x && al.get(i).y == al.get(0).y) {
                return true;
            }
        }
        return false;
    }

    //��дrun����ʵ�ֶ��߳����Զ�ǰ��
    @Override 
    public void run() {
        if(!direction.equals("��") && !direction.equals("����")) {
            while (true) {
                tail = move();  
                if (suicide()) {
                    ClientUI.opened = false;
                    ClientUI.yard.setText(" ");
                    ClientUI.contentPane.add(ClientUI.failure);
                    Thread.currentThread().stop();
                }
                //ClientUI.contentPane.add(ClientUI.food);
                eat();
                update();
                try {
                    Thread.sleep(speed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else if (direction.equals("��")) {
            try {
                Thread.sleep(100);       //��Ϸ��ʼ��ʱ��û�г�ʼ����,����ʱ��ѭ���ȴ��û����뷽��
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            update();
            run();
        } else {
            try {
                Thread.sleep(100);       //��Ϸ��ʼ��ʱ��û�г�ʼ����,����ʱ��ѭ���ȴ��û����뷽��
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            run();
        }
    }
}

class MyPanel extends JPanel implements KeyListener {

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (ClientUI.opened == true) {
            if (GreedySnake.direction == null) {
                GreedySnake.direction = "" + e.getKeyChar();
                ClientUI.currentDir.setText("��ǰ����:  " + GreedySnake.direction + "          W:��    A:��    S:��   D:��");
            }
            if (e.getKeyChar() == 'w' && !("" + e.getKeyChar()).equals(GreedySnake.oppositeDir())) {
                GreedySnake.direction = "w";
                ClientUI.currentDir.setText("��ǰ����:  " + GreedySnake.direction + "          W:��    A:��    S:��   D:��");
                return;
            }
            if (e.getKeyChar() == 'a' && !("" + e.getKeyChar()).equals(GreedySnake.oppositeDir())) {
                GreedySnake.direction = "a";
                ClientUI.currentDir.setText("��ǰ����:  " + GreedySnake.direction + "          W:��    A:��    S:��   D:��");
                return;
            }
            if (e.getKeyChar() == 's' && !("" + e.getKeyChar()).equals(GreedySnake.oppositeDir())) {
                GreedySnake.direction = "s";
                ClientUI.currentDir.setText("��ǰ����:  " + GreedySnake.direction + "          W:��    A:��    S:��   D:��");
                return;
            }
            if (e.getKeyChar() == 'd' && !("" + e.getKeyChar()).equals(GreedySnake.oppositeDir())) {
                GreedySnake.direction = "d";
                ClientUI.currentDir.setText("��ǰ����:  " + GreedySnake.direction + "          W:��    A:��    S:��   D:��");
                return;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }

}


public class ClientUI extends JFrame {
    /**  
    * @Fields serialVersionUID : TODO(��һ�仰�������������ʾʲô)  
    */  
    private static final long serialVersionUID = 2689655315523047948L;
    public static MyPanel contentPane = new MyPanel();
    public static JLabel currentDir;
    public static JLabel yard = new JLabel();
    public static Icon headIC = new ImageIcon("src/com/igeek/greedy_snake/��.png");
    public static Icon failureIC = new ImageIcon("src/com/igeek/greedy_snake/������.png");
    public static Icon basketball = new ImageIcon("src/com/igeek/greedy_snake/����.png");
    public static ArrayList<JLabel> body = new ArrayList<JLabel>();
    public static JLabel head = new JLabel(headIC, JLabel.CENTER);
    public static JLabel food = new JLabel(basketball, JLabel.CENTER);
    public static JLabel failure = new JLabel(failureIC, JLabel.CENTER);
    public static boolean opened = true;
    static {
        contentPane.setBounds(50, 50, 600, 600);
        contentPane.setLayout(null);
        contentPane.setBackground(Color.WHITE);
        
        currentDir = new JLabel("��ǰ����:  " + GreedySnake.direction + "          W:��    A:��    S:��   D:��");
        currentDir.setBounds(5, 5, 400, 15);
        contentPane.add(currentDir);
        
        yard.setBounds(5, 20, 550, 500);
        contentPane.add(yard);
        
        failure.setBounds(0,20, 580, 500);
        
        contentPane.add(head);
        contentPane.add(food);
        
        body.add(head);
    }

    public void init() {
        this.setTitle("̰������--������  v1.0                  ����:����һ��δע�Ṥ��ʦ");
        this.setSize(575, 565);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(contentPane);
        this.addKeyListener(contentPane);
        this.setVisible(true);
    }
    
    public ClientUI() {
        init();
    }

    public static void respawn() {
        food = new JLabel(basketball, JLabel.CENTER);
        food.setBounds(10 + GreedySnake.food.y * 16, 18 + GreedySnake.food.x * 18, 16, 18);
        contentPane.add(food);
    }
    public static void main(String[] args) {
        new ClientUI();
        Thread t1 = new Thread(new GreedySnake());
        t1.start();
    }
}
