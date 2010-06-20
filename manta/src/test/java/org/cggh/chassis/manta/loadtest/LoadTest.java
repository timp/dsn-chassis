package org.cggh.chassis.manta.loadtest;

import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Random;
import java.util.Vector;


class HitStats {
  public long totalToOpen, totalToFinish;
  public int successes;
  public int failures;
  // public int errors;

  public void notifyTransaction(long started, long opened, long finished
                                // , boolean error
                                ) {
    totalToOpen += opened - started;
    totalToFinish += finished - started;
    // if (error) ++errors else
    ++successes;
  }

  public void notifyFailure() {
    ++failures;
  }

  public long meanToOpen() {
    return successes == 0 ? 0 : totalToOpen / successes;
  }

  public long meanToFinish() {
    return successes == 0 ? 0 : totalToFinish / successes;
  }
}

class DiscreteDistribution  {

  private Vector<Object> elements = new Vector<Object>();
  private Vector<Object> cumulative = new Vector<Object>();

  public DiscreteDistribution() {}

  public void add(Object o, int n) {
    for (int i = 0; i < n; ++i)
      cumulative.addElement(o);
    elements.addElement(o);
  }
 
  public void add(Object o) {
    add(o, 1);
  }

  public Object sample() {
    return cumulative.elementAt(LoadTest.random.nextInt(cumulative.size()));
  }

  public Enumeration<Object> elements() {
    return elements.elements();
  }
}

class ExponentialDistribution {
  private double minusMean;

  public ExponentialDistribution(double mean) {
    minusMean = -mean;
  }

  public double sample() {
    for (;;) {
      float x = LoadTest.random.nextFloat();
      if (x != 0.)
        return minusMean * Math.log(x);
    }
  }
}

class BitBucket extends Thread {
  private String url;
  private HitStats stats;

  public BitBucket(String url, HitStats stats) {
    this.url = url;
    this.stats = stats;
  }

  private static final byte[] b = new byte[4096];

  public void run() {
    //System.err.println(url);
    try {
      long started = System.currentTimeMillis();
      InputStream i = new URL(url).openStream();
      long opened = System.currentTimeMillis();
      while (i.read(b) != -1);
      long finished = System.currentTimeMillis();

      stats.notifyTransaction(started, opened, finished);
    }
    catch (Exception e) {
      System.err.println(e);
      stats.notifyFailure();
    }
  }
}
class RequestType {
  HitStats stats = new HitStats();
  String url;
  
  RequestType(String url) {
    this.url = url;
  }

  void go(String url) {
    new BitBucket(LoadTest.urlPrefix + url, stats).
        start();
  }

  public String toString() {
    return url;
  }
}

public class LoadTest {

  static Random random = new Random();
  static String urlPrefix;

  private long runtimeMillis;
  private boolean keepGoing;
  private ExponentialDistribution wait;
  private DiscreteDistribution types = new DiscreteDistribution();

  public LoadTest(long runtimeMillis, int hitsPerSec) {
    this.runtimeMillis = runtimeMillis;
    wait = new ExponentialDistribution(1000 / hitsPerSec);

    types = new DiscreteDistribution();

    
    types.add(new RequestType("questionnaire/"), 6);
  }
  private class Stopper extends Thread {
    public void run() {
      try {
        Thread.sleep(runtimeMillis);
      }
      catch (Exception e) {
        throw new RuntimeException(e);
      }

      keepGoing = false;
    }
  }

  public static String pad(String it, int w) {
    StringBuffer s = new StringBuffer(w);
    while (s.length() + it.length() < w)
      s.append(' ');
    s.append(it);
    return s.toString();
  }

  public static String pad(long l, int w) {
    return pad("" + l, w);
  }

  public void run() throws Exception {
    keepGoing = true;
    new Stopper().start();

    while (keepGoing) {
      Thread.sleep((long)wait.sample());
      ((RequestType)types.sample()).go("questionnaire");
    }

    System.err.println("stopping");
    Thread.sleep(5);

    System.out.println();
    System.out.println("        win open end fail");
    for (Enumeration h = types.elements(); h.hasMoreElements();) {
      RequestType hit = (RequestType)h.nextElement();
      System.out.print(hit.toString());
      System.out.println(
          pad(hit.stats.successes, 9) + " " +
          pad(hit.stats.meanToOpen(), 5) + " " +
          pad(hit.stats.meanToFinish(), 5) + " " +
          pad(hit.stats.failures, 5) + " ");
    }
  }


  public static void main(String[] args) throws Exception {
    urlPrefix = "http://localhost:8080/manta/";
    new LoadTest(new Integer(10) * 1000L,
        new Integer(500)).run();
  }
}
