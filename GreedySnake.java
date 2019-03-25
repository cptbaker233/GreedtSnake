package com.igeek.greedy_snake;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

class Snake {
    int x;
    int y;
    
    //���췽��
    public Snake(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

public class GreedySnake {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random r = new Random();
        ArrayList<Snake> al = new ArrayList<Snake>();
        System.out.println("�����ó��س�:");
        int l = sc.nextInt();
        System.out.println("�����ó��ظ߶�:");
        int h = sc.nextInt();
        String[][] field = new String[h][l + 2];
        Snake tail;
        int a = r.nextInt(field.length);
        int b = r.nextInt(field[0].length - 2) + 1;
        al.add(new Snake(a, b));
        Snake food = respawn(al, field);
        update(field, al, food);
        update(field, al, food);
        update(field, al, food);
        update(field, al, food);
        update(field, al, food);
        update(field, al, food);
        update(field, al, food);
        update(field, al, food);
        update(field, al, food);
        String option = sc.next();
        while(true) {
            switch (option) {
            case "w":
                tail = moveUp(al);
                if (suicide(al, field)) {
                    System.out.println("������!��Ϸʧ��!");
                    System.out.println("������!��Ϸʧ��!");
                    System.out.println("������!��Ϸʧ��!");
                    System.out.println("������!��Ϸʧ��!");
                    System.out.println("������!��Ϸʧ��!");
                    System.out.println("������!��Ϸʧ��!");
                    System.exit(0);
                }
                if (eat(al, food, tail)) {
                    food = respawn(al, field);
                    update(field, al, food);
                    option = sc.next();
                }
                break;
            case "s":
                tail = moveDown(al);
                if (suicide(al, field)) {
                    System.out.println("������!��Ϸʧ��!");
                    System.out.println("������!��Ϸʧ��!");
                    System.out.println("������!��Ϸʧ��!");
                    System.out.println("������!��Ϸʧ��!");
                    System.out.println("������!��Ϸʧ��!");
                    System.out.println("������!��Ϸʧ��!");
                    System.exit(0);
                }
                if (eat(al, food, tail)) {
                    food = respawn(al, field);
                    update(field, al, food);
                    option = sc.next();
                } 
                break;
            case "a":
                tail = moveLeft(al);
                if (suicide(al, field)) {
                    System.out.println("������!��Ϸʧ��!");
                    System.out.println("������!��Ϸʧ��!");
                    System.out.println("������!��Ϸʧ��!");
                    System.out.println("������!��Ϸʧ��!");
                    System.out.println("������!��Ϸʧ��!");
                    System.out.println("������!��Ϸʧ��!");
                    System.exit(0);
                }
                if (eat(al, food, tail)) {
                    food = respawn(al, field);
                    update(field, al, food);
                    option = sc.next();
                }
                break;
            case "d":
                tail = moveRight(al);
                if (suicide(al, field)) {
                    System.out.println("������!��Ϸʧ��!");
                    System.out.println("������!��Ϸʧ��!");
                    System.out.println("������!��Ϸʧ��!");
                    System.out.println("������!��Ϸʧ��!");
                    System.out.println("������!��Ϸʧ��!");
                    System.out.println("������!��Ϸʧ��!");
                    System.exit(0);
                }
                if (eat(al, food, tail)) {
                    food = respawn(al, field);
                    update(field, al, food);
                    option = sc.next();
                }
                break;
            case "quit":
                System.out.println("�����˳�!");
                System.out.println("�����˳�!");
                System.out.println("�����˳�!");
                System.out.println("�����˳�!");
                System.out.println("�����˳�!");
                System.out.println("���˳�!");
                System.exit(0);
            }
            update(field, al, food);
            option = sc.next();
        }
        
        
    }
    
    //��ӡ��ǰս���ķ���
    public static void update(String[][] field, ArrayList<Snake> al, Snake food) {
        for (int i = 0; i < field.length; i ++) {
            field[i][0] = "�D";
            field[i][field[0].length - 1] = "�D";
            for (int j = 1; j < field[0].length - 1; j ++) {
                field[i][j] = "��";
            }
        }
        field[food.x][food.y] = "��";
        for (int i = 1; i < al.size() - 1; i ++) {
            field[al.get(i).x][al.get(i).y] = "��";
        }
        field[al.get(al.size() - 1).x][al.get(al.size() - 1).y] = "��";
        field[al.get(0).x][al.get(0).y] = "�K";
        System.out.println("�ֶ�̰����v1.2");
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
    public static boolean hit(Snake s, ArrayList<Snake> al) {
        for (int i = 0; i < al.size(); i ++) {
            if (s.x == al.get(i).x && s.y == al.get(i).y) {
                return true;
            }
        }
        return false;
    }
    //����ʳ��ķ���
    public static Snake respawn(ArrayList<Snake> al, String[][] field) {
        Random r = new Random();
        Snake s = new Snake(r.nextInt(field.length), r.nextInt(field[0].length - 2) + 1);
        while (hit(s,al)) {
            if (al.size() == field.length * (field[0].length - 2)) {
                System.out.println("��ϲ��ͨ��!");
                System.out.println("��ϲ��ͨ��!");
                System.out.println("��ϲ��ͨ��!");
                System.out.println("��ϲ��ͨ��!");
                System.out.println("��ϲ��ͨ��!");
                System.out.println("��ϲ��ͨ��!");
                System.exit(0);
            }
            s = new Snake(r.nextInt(field.length), r.nextInt(field[0].length - 2) + 1);
        }
        return s;
    }
    //�жϳԵ�������
    public static boolean eat(ArrayList<Snake> al, Snake food, Snake tail) {
        int x = al.get(0).x;
        int y = al.get(0).y;        
        if (x == food.x && y == food.y) {
            al.add(tail);
            return true;
        }
        return false;
    }
    //����
    public static Snake moveRight(ArrayList<Snake> al) {
        Snake tail = new Snake(al.get(al.size() - 1).x, al.get(al.size() - 1).y);        
        if (al.size() == 1) {
            al.get(0).y ++;
        } else if (al.get(0).y + 1 != al.get(1).y) {
            Snake head = new Snake(al.get(0).x, al.get(0).y + 1);
            al.remove(al.get(al.size() - 1));
            al.add(0, head);
        }
        return tail;
    }
  //����
    public static Snake moveLeft(ArrayList<Snake> al) {
        Snake tail = new Snake(al.get(al.size() - 1).x, al.get(al.size() - 1).y);      
        if (al.size() == 1) {
            al.get(0).y --;
        } else if (al.get(0).y - 1 != al.get(1).y) {
            Snake head = new Snake(al.get(0).x, al.get(0).y - 1);
            al.remove(al.get(al.size() - 1));
            al.add(0, head);
        }
        return tail;
    }
  //����
    public static Snake moveUp(ArrayList<Snake> al) {
        Snake tail = new Snake(al.get(al.size() - 1).x, al.get(al.size() - 1).y);       
        if (al.size() == 1) {
            al.get(0).x --;
        } else if (al.get(0).x - 1!= al.get(1).x) {
            Snake head = new Snake(al.get(0).x - 1, al.get(0).y);
            al.remove(al.get(al.size() - 1));
            al.add(0, head);
        }
        return tail;
    }
  //����
    public static Snake moveDown(ArrayList<Snake> al) {
        Snake tail = new Snake(al.get(al.size() - 1).x, al.get(al.size() - 1).y);     
        if (al.size() == 1) {
            al.get(0).x ++;
        } else if (al.get(0).x + 1 != al.get(1).x) {
            Snake head = new Snake(al.get(0).x + 1, al.get(0).y);
            al.remove(al.get(al.size() - 1));
            al.add(0, head);
        }
        return tail;
    }
  //��������ײ���ͱ߽���ײ���
    public static boolean suicide(ArrayList<Snake> al, String[][] field) {
        if (al.get(0).x == -1 || al.get(0).x == field.length || al.get(0).y == field[0].length - 1 || al.get(0).y == 0 ) {
            return true;
        }
        for (int i = 1; i < al.size() - 1; i ++) {
            if (al.get(i).x == al.get(0).x && al.get(i).y == al.get(0).y) {
                return true;
            }
        }
        return false;
    }
}
