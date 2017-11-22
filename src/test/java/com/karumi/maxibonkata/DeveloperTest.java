package com.karumi.maxibonkata;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(JUnitQuickcheck.class)
public class DeveloperTest {

  private static final String ANY_NAME = "any_name";
  private static final int ANY_NUMBER_OF_MAXIBONS = 1;

  @Property
  public void shouldNotGrabNegativeMaxibons(int numberOfMaxibons) throws Exception {
    System.out.println("numberOfMaxibons = [" + numberOfMaxibons + "]");
    Developer developer = new Developer(ANY_NAME, numberOfMaxibons);
    assertTrue(developer.getNumberOfMaxibonsToGrab() >= 0);
  }

  @Property
  public void shouldReturnSameName(String name) {
    System.out.println("name = [" + name + "]");
    Developer developer = new Developer(name, ANY_NUMBER_OF_MAXIBONS);
    assertEquals(name, developer.getName());
  }

}