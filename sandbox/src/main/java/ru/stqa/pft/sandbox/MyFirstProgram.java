package ru.stqa.pft.sandbox;

public class MyFirstProgram {

  public static void main(String[] args) {

    double x1 = 3;
    double y1 = 8;
    double x2 = 4;
    double y2 = 9;

    Point p1 = new Point(x1, y1);
    Point p2 = new Point(x2, y2);

    System.out.println("The distance between point1 (" + p1.x + "," + p1.y + ") and point2 (" + +p2.x + "," + p2.y + ") is: " + distance(p1, p2)+" :calculated via function.");
    System.out.println("The distance between point1 (" + p1.x + "," + p1.y + ") and point2 (" + +p2.x + "," + p2.y + ") is: " + p1.distance(p2)+ " :calculated via class method.");

  }

  public static void hello(String name){
    System.out.println("Hello," + name+"!");
  }

  public static double distance(Point p1, Point p2) {
    return Math.sqrt((p2.x - p1.x) * (p2.x - p1.x) + (p2.y - p1.y) * (p2.y - p1.y));
  }
}