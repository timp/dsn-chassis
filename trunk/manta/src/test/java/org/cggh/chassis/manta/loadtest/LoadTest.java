package org.cggh.chassis.manta.loadtest;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Vector;

/**
 *  A Utility to stress a single URL from the command line or inside an IDE.
 *  
 * 
 * @author timp
 * @since 2010/06/20
 */
public class LoadTest {

	private String fileName;
	private Vector<InstrumentedUrlRequestSet> urlsToHit = null;
	private ArrayList<BitBucketClient> threads = new ArrayList<BitBucketClient>();
	static int requestsPerRate = 5;
	static int rateIncrement = 1;
	static int maxHitsPerSec = 501;
	static long maxWaitTime = 240000L;
	volatile static int runningThreadCount = 0;
	private static final byte[] bitBucket = new byte[4096];

	public LoadTest(String url) {
		this.fileName = filenameFrom(url);
		urlsToHit = new Vector<InstrumentedUrlRequestSet>();
		for (int hitsPerSec = 1; hitsPerSec < maxHitsPerSec; hitsPerSec += rateIncrement) {
			urlsToHit.add(new InstrumentedUrlRequestSet(url, hitsPerSec));
		}
	}

	class HitStats {
		public long totalTimeToOpen, totalTimeToFinish;
		public int successes, failures;
		public int totalRunningThreadsCount;

		public void notifySuccess(long started, long opened, long finished, int runningThreadsCount) {
			long toOpen = opened - started;
			totalTimeToOpen += toOpen;
			long toFinish = finished - started;
			totalTimeToFinish += toFinish;
			totalRunningThreadsCount = totalRunningThreadsCount + runningThreadsCount;
			System.err.println(started + ":" + opened + ":" + finished + "=" + toFinish + "  (" + runningThreadsCount + ")");
			++successes;
		}

		public void notifyFailure(int runningThreadsCount) {
			++failures;
			totalRunningThreadsCount = totalRunningThreadsCount + runningThreadsCount;
		}

		public long meanTimeToOpen() {
			return successes == 0 ? 0 : totalTimeToOpen / successes;
		}

		public long meanTimeToFinish() {
			return successes == 0 ? 0 : totalTimeToFinish / successes;
		}
		
		public int totalCompleted() { 
			return successes + failures;
		}
		public long meanRunningThreadsCount() {
			return totalCompleted() == 0 ? 0 : totalRunningThreadsCount / totalCompleted();
		}
		
		
	}

	class InstrumentedUrlRequestSet {
		HitStats stats = new HitStats();
		String url;
		int hitsPerSecond;
		long waitMillis;

		InstrumentedUrlRequestSet(String url, int hitsPerSecond) {
			this.url = url;
			this.hitsPerSecond = hitsPerSecond;
			this.waitMillis = 1000L / hitsPerSecond;
		}

		void go() throws InterruptedException {
			for (int i = 0; i < requestsPerRate; i++) {
				BitBucketClient b = new BitBucketClient(url, stats); 
				threads.add(b);
				b.start();
				Thread.sleep(waitMillis);
			}
		}

		public String toString() {
			return url + "(" + hitsPerSecond + ")";
		}

		public String url() {
			return url;
		}

		public int hitsPerSecond() {
			return hitsPerSecond;
		}
	}

	class BitBucketClient extends Thread {
		private String url;
		private HitStats hitStats;
		private volatile boolean stop = false;
		
		public BitBucketClient(String url, HitStats stats) {
			this.url = url;
			this.hitStats = stats;
		}
		public void stop_soon() { 
			stop = true;
		}
		public void run() {
			runningThreadCount++;
			//System.err.print(url + " >>/dev/null ");
			InputStream inputStream = null;
			try {
				long started = System.currentTimeMillis();
				URL it = new URL(url);
				inputStream = it.openStream();
				long opened = System.currentTimeMillis();
				while (!stop && inputStream.read(bitBucket) != -1)
					;
				System.err.println(new String(bitBucket));
				
				long finished = System.currentTimeMillis();

				hitStats.notifySuccess(started, opened, finished,runningThreadCount);

			} catch (Exception e) {
				// Error response codes are translated into exceptions. 
				
				hitStats.notifyFailure(runningThreadCount);
				System.err.println(e);
			} finally { 
				try {
					if (inputStream != null)
					  inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}				
			}
			runningThreadCount--;
		}
		
		public HitStats getHitStats() { 
			return hitStats;
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
	
	public static String filenameFrom(String url) {
		String filename = url.toLowerCase();
		filename = filename.replace("http://","");
		filename = filename.replace(".html","");
		if (filename.length() > 0  && filename.lastIndexOf('/') == filename.length() -1)
			filename = filename.substring(0,filename.length() -1);
        if (filename.lastIndexOf('/') != -1)
        	filename = filename.substring(filename.lastIndexOf('/') +1);
        return filename + ".csv";
	}

	public void run() throws Exception {
		FileOutputStream output = new FileOutputStream(fileName);
		PrintStream out = new PrintStream(output);

		for (InstrumentedUrlRequestSet iurs : urlsToHit) {
			System.err.println(iurs);
			iurs.go();
		}
		long waited = 0;
		while (runningThreadCount > 0 && waited < maxWaitTime) {
			Thread.sleep(2000);
			waited += 2000;
			System.err.println("waiting because there are still " + runningThreadCount + " threads");			
		}
		if (waited > maxWaitTime) 
			System.err.println("Maximum wait exceeded.");
        for (BitBucketClient t : threads) {
        	synchronized(t) { 
              if (t.isAlive()) {
                t.getHitStats().notifyFailure(runningThreadCount);
        	    t.stop_soon();
              }
        	}
        }
		
		System.err.println("stopping after " + waited);

		out.println("url, count, success, fail, %, rate, open, read, threads");
		int count = 0; 
		for (InstrumentedUrlRequestSet iurs : urlsToHit) {
			count++;
			double successRate = 0.0;
			if (iurs.stats.totalCompleted() != 0)
				successRate = (iurs.stats.successes * 100.0) / (iurs.stats.totalCompleted());
			
			out.println(iurs.url() + ", " 
					+ pad(iurs.stats.totalCompleted(), 5) + ", "
					+ pad(iurs.stats.successes, 5) + ", "
                    + pad(iurs.stats.failures, 5) + ", "
					+ pad(new Double(successRate).toString(), 5) + ", "
					+ pad(iurs.hitsPerSecond(), 5) + ", "
					+ pad(iurs.stats.meanTimeToOpen(), 5) + ", "
					+ pad(iurs.stats.meanTimeToFinish(), 5) + ", " + " "
                    + pad(iurs.stats.meanRunningThreadsCount(), 5) + ", "
                    );
		}
		System.err.println("Wrote " + count + " records");
	}

	public static void main(String[] args) throws Exception {
		String url = "http://localhost:8080/manta/questionnaire/";
		//String url = "http://localhost:8080/manta/index.html";
		//String url = "http://localhost:8080/manta/home/";
		//String url = "http://localhost:8080/manta/exist/rest/db/atom/content/studies/KCDZQ.atom";
		//String url = "http://localhost:8080/manta/exist/rest/db/atom/content/studies/";
		//String url = "http://localhost:8080/manta/home/";
		//String url = "http://colin%40example.org:bar@localhost:8080/manta/contributor/";
		//String url = "http://localhost/";
		//String url = "http://localhost:8080/manta/atombeat/admin/install.xql";
		//String url = "http://129.67.44.221:8080/explorer_dev/app/";
		
		System.out.println("Outputting to " + filenameFrom(url));
			
		new LoadTest(url).run();
		
		System.exit(1);
	}
}
