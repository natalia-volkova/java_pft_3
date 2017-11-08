package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PointTests {

  @Test
  public void oneQuarter() {


    Point p1 = new Point(3, 8);
    Point p2 = new Point(6, 4);
    Assert.assertEquals(p1.distance(p2), 5.0);
  }
  @Test
  public void differentQuarter() {

    Point p1 = new Point(-3, -2);
    Point p2 = new Point(1, 1);
    Assert.assertEquals(p1.distance(p2), 5.0);
  }
  @Test
  public void samePoints() {

    Point p1 = new Point(-4, -6);
    Point p2 = new Point(-4, -6);
    Assert.assertEquals(p1.distance(p2), 0.0);
  }


}
