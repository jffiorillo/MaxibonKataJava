package com.karumi.maxibonkata;

import com.pholser.junit.quickcheck.From;
import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static com.karumi.maxibonkata.KarumiHQs.PACKAGE_OF_MAXIBONS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

@RunWith(JUnitQuickcheck.class)
public class KarumiHQsTest {

  private KarumiHQs karumiHQs;
  @Mock
  private Chat chat;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    karumiHQs = new KarumiHQs(chat);
  }

  @Property
  public void shouldRemainMoreThan2MaxibonsAfterADeveloperGrabMaxibons(@From(DevelopersGenerator.class) Developer developer) throws Exception {
    System.out.println("developer = [" + developer + "]");

    karumiHQs.openFridge(developer);

    assertTrue(karumiHQs.getMaxibonsLeft() >= 2);
  }

  @Property
  public void shouldRemainMoreThan2MaxibonsAfterADeveloperLowerConsumerGrabMaxibons(@From(DevelopersLowerConsumerGenerator.class) Developer developer) throws Exception {
    System.out.println("developer = [" + developer + "]");

    karumiHQs.openFridge(developer);

    assertTrue(karumiHQs.getMaxibonsLeft() >= 2);
  }

  @Property
  public void shouldRemainThisMaxibonsAfterGrab(@From(DevelopersGenerator.class) Developer developer) throws Exception {
    System.out.println("developer = [" + developer + "]");
    int maxibonsLeft = karumiHQs.getMaxibonsLeft();

    karumiHQs.openFridge(developer);

    assertEquals(expectedRemainMaxibons(maxibonsLeft, developer.getNumberOfMaxibonsToGrab()), karumiHQs.getMaxibonsLeft());
  }

  private int expectedRemainMaxibons(int maxibonsLeft, int numberToGrab) {
    int maxibonLeft = maxibonsLeft - eatMaximumTheseMaxibons(maxibonsLeft, numberToGrab);
    return maxibonLeft <= 2 ? maxibonLeft + PACKAGE_OF_MAXIBONS : maxibonLeft;
  }

  private int eatMaximumTheseMaxibons(int maxibonsLeft, int numberToGrab) {
    return Math.min(maxibonsLeft, numberToGrab);
  }

  @Property
  public void shouldRemain2MaxibonsAfterGrab(List<@From(DevelopersGenerator.class) Developer> developers) {
    System.out.println("developers = [" + developers + "]");
    int maxibonsLeft = karumiHQs.getMaxibonsLeft();
    for (Developer developer : developers) {
      maxibonsLeft = expectedRemainMaxibons(maxibonsLeft,developer.getNumberOfMaxibonsToGrab());
    }

    karumiHQs.openFridge(developers);

    assertTrue(karumiHQs.getMaxibonsLeft() >= 2);
  }

  @Property
  public void shouldRemainThisMaxibonsAfterGrab(List<@From(DevelopersGenerator.class) Developer> developers) {
    System.out.println("developers = [" + developers + "]");
    int maxibonsLeft = karumiHQs.getMaxibonsLeft();
    for(Developer developer :developers){
      maxibonsLeft = expectedRemainMaxibons(maxibonsLeft,developer.getNumberOfMaxibonsToGrab());
    }

    karumiHQs.openFridge(developers);

    assertEquals(maxibonsLeft,karumiHQs.getMaxibonsLeft());
  }

  @Property
  public void shouldNotifyToChatWhenHasToBuyMaxibons(@From(HungryDevelopersGenerator.class) Developer developer) {
    System.out.println("developer = [" + developer + "]");

    karumiHQs.openFridge(developer);

    verify(chat).sendMessage(eq("Hi guys, I'm " + developer.getName() + ". We need more maxibons!"));
  }

}