package com.igeek.greedy_snake;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
/**
 * 
* @typename Snake  
* @author NFUE  
* @Description: ������̵߳İ��Զ�̰����С��Ϸ
* @date 2019��4��13�� ����3:40:48    
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

public class GreedySnake implements Runnable {
    private static String direction;
    private static ArrayList<Snake> al = new ArrayList<Snake>();
    private static Snake food;
    private static String[][] field;
    private static Snake tail;
    private static int speed = 600;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random r = new Random();
        System.out.println("�����ó��س�:");
        int l = sc.nextInt();
        System.out.println("�����ó��ظ߶�:");
        int h = sc.nextInt();
        System.out.println("�������ƶ��ٶ�:                 (Ĭ��600,����ԽСԽ��)");
        speed = sc.nextInt();
        field = new String[h][l + 2];            //�������õĳ��س�������ά���鳡��
        int a = r.nextInt(field.length);
        int b = r.nextInt(field[0].length - 2) + 1;
        al.add(new Snake(a, b));                            //�ڳ�����������ɵ�һ���ߵ�����
        respawn();                    //�ڳ������������һ��ʳ������
        update();                            //ȷ���ߺ�ʳ�������֮��,ˢ�³���
        update();                            //������ĳ�����ʾΪ��,�հ׳�����ʾΪ�ո�,ʳ��������ʾΪ��
        update();
        update();
        update();
        update();
        update();
        update();
        update();
        new Thread(new GreedySnake()).start();
        while(true) {
            String newDir = sc.next();
            if (newDir.equals("quit")) {
                System.out.println("�˳���Ϸ!!!!!!!!!!!!");
                System.out.println("�˳���Ϸ!!!!!!!!!!!!");
                System.out.println("�˳���Ϸ!!!!!!!!!!!!");
                System.out.println("�˳���Ϸ!!!!!!!!!!!!");
                System.exit(0);
            } else if (direction == null) {
                direction = newDir;
                tail = move();
                if (suicide()) {
                    System.out.println("������!��Ϸʧ��!");
                    System.out.println("������!��Ϸʧ��!");
                    System.out.println("������!��Ϸʧ��!");
                    System.out.println("������!��Ϸʧ��!");
                    System.out.println("������!��Ϸʧ��!");
                    System.out.println("������!��Ϸʧ��!");
                    System.exit(0);
                }
                update();
                eat();
            } else if (!newDir.equals(oppositeDir())) {
                direction = newDir;
            }
        }
    }
    
    //��ӡ��ǰս���ķ���
    public static void update() {
        for (int i = 0; i < field.length; i ++) {                       //�Ȱ�������������Ϊ��ʼ����
            field[i][0] = "�D";
            field[i][field[0].length - 1] = "�D";
            for (int j = 1; j < field[0].length - 1; j ++) {
                field[i][j] = "��";
            }
        }
        field[food.x][food.y] = "��";                                    //ʳ�������Ӧ�ĳ���Ϊ"��"
        for (int i = 0; i < al.size() - 1; i ++) {                      //�������Ӧ�ĳ���Ϊ"��"
            field[al.get(i).x][al.get(i).y] = "��";
        }
        field[al.get(al.size() - 1).x][al.get(al.size() - 1).y] = "��";  //��������,����ͷ����β������
        field[al.get(0).x][al.get(0).y] = "�K";
        System.out.println("���Զ�̰����v1.3");
        /*
         * ���е����궼ȷ��֮��,������ˢ�³��ߺ�ʳ��
         */
        for (int i = 1; i <= field[0].length ; i ++) {
            System.out.print("�D");
        }
        System.out.println();
        for (int i = 0; i < field.length; i ++) {
            for (int j = 0; j < field[0].length; j ++) {
                System.out.print(field[i][j]);
            }
            System.out.println();
        }
        for (int i = 1; i <= field[0].length ; i ++) {
            System.out.print("�D");
        }
        System.out.println();
        System.out.println("����wasd�ƶ��ƶ�:(d:��   a:��  w:��   s:��     quit:�˳�)");
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
        food = new Snake(r.nextInt(field.length), r.nextInt(field[0].length - 2) + 1);   //�������ʳ��
        while (hit()) {                                                                 //һֱ������ʳ��ֱ�����ɲ�������ײ��ʳ��
            if (al.size() == field.length * (field[0].length - 2)) {                        //������Ѿ��ѳ��������,���ж�ͨ��
                System.out.println("��ϲ��ͨ��!");
                System.out.println("��ϲ��ͨ��!");
                System.out.println("��ϲ��ͨ��!");
                System.out.println("��ϲ��ͨ��!");
                System.out.println("��ϲ��ͨ��!");
                System.out.println("��ϲ��ͨ��!");
                System.exit(0);
            }
            food = new Snake(r.nextInt(field.length), r.nextInt(field[0].length - 2) + 1);
        }
    }
    //�жϳԵ�������
    public static boolean eat() {
        if (al.get(0).x == food.x && al.get(0).y == food.y) {   //�ƶ�֮��,�����ͷ������ʳ����ͬ,�ж�true
            al.add(tail);                   //����һ���ƶ�����β���������β
            respawn();
            update(); 
            return true;
        }
        return false;
    }
    
    //�õ��෴����ķ���
    public static String oppositeDir() {
        switch (direction) {
        case "w":
            return "s";
        case "s" :
            return "w";
        case "a" : 
            return "d";
        case "d" : 
            return "a";
        default:
            return direction;
        }
    }
    //�ƶ��ķ���
    public static Snake move() {
        switch (direction) {
        case "d":
            tail = new Snake(al.get(al.size() - 1).x, al.get(al.size() - 1).y);        
            if (al.size() == 1) {               //�߳���ֻ��1ʱ,ֱ���ƶ�
                al.get(0).y ++;
            } else {            
                Snake head = new Snake(al.get(0).x, al.get(0).y + 1);
                al.remove(al.get(al.size() - 1));
                al.add(0, head);
            }
            return tail;
            
        case "a":
            tail = new Snake(al.get(al.size() - 1).x, al.get(al.size() - 1).y);      
            if (al.size() == 1) {
                al.get(0).y --;
            } else {
                Snake head = new Snake(al.get(0).x, al.get(0).y - 1);
                al.remove(al.get(al.size() - 1));
                al.add(0, head);
            }
            return tail;
            
        case "w":
            tail = new Snake(al.get(al.size() - 1).x, al.get(al.size() - 1).y);       
            if (al.size() == 1) {
                al.get(0).x --;
            } else {
                Snake head = new Snake(al.get(0).x - 1, al.get(0).y);
                al.remove(al.get(al.size() - 1));
                al.add(0, head);
            }
            return tail;
            
        case "s":
            tail = new Snake(al.get(al.size() - 1).x, al.get(al.size() - 1).y);     
            if (al.size() == 1) {
                al.get(0).x ++;
            } else {
                Snake head = new Snake(al.get(0).x + 1, al.get(0).y);
                al.remove(al.get(al.size() - 1));
                al.add(0, head);
            }
            return tail;
            
        default:
            return tail;
        }
    }
  //��������ײ���ͱ߽���ײ���
    public static boolean suicide() {
        if (al.get(0).x == -1 || al.get(0).x == field.length || al.get(0).y == field[0].length - 1 || al.get(0).y == 0 ) {    //�����ͷ����������߽߱��غ�,�ж�true
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
        if(direction != null) {
            while (true) {
                tail = move();  
                if (suicide()) {
                    System.out.println("������!��Ϸʧ��!");
                    System.out.println("������!��Ϸʧ��!");
                    System.out.println("������!��Ϸʧ��!");
                    System.out.println("������!��Ϸʧ��!");
                    System.out.println("������!��Ϸʧ��!");
                    System.out.println("������!��Ϸʧ��!");
                    System.exit(0);
                }
                update();
                eat();
                try {
                    Thread.sleep(speed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                Thread.sleep(10);       //��Ϸ��ʼ��ʱ��û�г�ʼ����,����ʱ��ѭ���ȴ��û����뷽��
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            run();
        }
    }
}