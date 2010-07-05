package org.cggh.chassis.manta.loadtest;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.Iterator;
import java.util.Vector;

public class LoadTest {

	private String fileName;
	private DiscreteDistribution urlsToHit = null;
	static int requestsPerRate = 50;
	static int rateIncrement = 1;
	static int maxHitsPerSec = 20;
	static long maxWaitTime = 100000L;
	int runningThreadCount = 0;
	private static final byte[] bitBucket = new byte[4096];

	public LoadTest(String url) {
		this.fileName = filenameFrom(url);
		urlsToHit = new DiscreteDistribution();
		for (int histPS = 1; histPS < maxHitsPerSec; histPS += rateIncrement) {
			urlsToHit.add(new InstrumentedUrlRequestSet(url, histPS));
		}
	}

	class HitStats {
		public long totalTimeToOpen, totalTimeToFinish;
		public int successes;
		public int failures;

		public void notifySuccess(long started, long opened, long finished) {
			long toOpen = opened - started;
			totalTimeToOpen += toOpen;
			long toFinish = finished - started;
			totalTimeToFinish += toFinish;
			System.err.println(started + ":" + opened + ":" + finished + "=" + toFinish);
			++successes;
		}

		public void notifyFailure() {
			++failures;
		}

		public long meanTimeToOpen() {
			return successes == 0 ? 0 : totalTimeToOpen / successes;
		}

		public long meanTimeToFinish() {
			return successes == 0 ? 0 : totalTimeToFinish / successes;
		}
	}

	class DiscreteDistribution implements Iterable<InstrumentedUrlRequestSet> {

		private Vector<InstrumentedUrlRequestSet> elements = new Vector<InstrumentedUrlRequestSet>();
		private Vector<InstrumentedUrlRequestSet> cumulative = new Vector<InstrumentedUrlRequestSet>();

		public DiscreteDistribution() {
		}

		public void add(InstrumentedUrlRequestSet o) {
			elements.addElement(o);
			for (int i = 0; i < requestsPerRate; ++i)
				cumulative.addElement(o);
		}

		public Iterator<InstrumentedUrlRequestSet> iterator() {
			return elements.iterator();
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
				new BitBucketClient(url, stats).start();
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

		public BitBucketClient(String url, HitStats stats) {
			this.url = url;
			this.hitStats = stats;
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
				while (inputStream.read(bitBucket) != -1)
					;
				System.err.println(new String(bitBucket));
				
				long finished = System.currentTimeMillis();

				hitStats.notifySuccess(started, opened, finished);

			} catch (Exception e) {
				// Error response codes are translated into exceptions. 
				
				hitStats.notifyFailure();
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
			Thread.sleep(1000);
			waited += 1000;
			System.err.println("waiting because there are still " + runningThreadCount + " threads");			
		}
		if (waited > maxWaitTime) 
			System.err.println("Maximum wait exceeded.");

		System.err.println("stopping after " + waited);

		out.println("url, count, success, rate, open, read");
		int count = 0; 
		for (InstrumentedUrlRequestSet iurs : urlsToHit) {
			count++;
			int total = iurs.stats.successes + iurs.stats.failures;
			double successRate = 0.0;
			if (total != 0)
				successRate = (iurs.stats.successes * 100.0) / (total);
			
			out.println(iurs.url() + ", " 
					+ pad(total, 5) + ", "
					+ pad(new Double(successRate).toString(), 5) + ", "
					+ pad(iurs.hitsPerSecond(), 5) + ", "
					+ pad(iurs.stats.meanTimeToOpen(), 5) + ", "
					+ pad(iurs.stats.meanTimeToFinish(), 5) + ", " + " ");
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
		//String url = "http://localhost:8080/manta/atombeat/admin/install.xql";
		//String url = "http://129.67.44.221:8080/explorer_dev/app/";
		
		System.out.println("Outputting to " + filenameFrom(url));
			
		new LoadTest(url).run();
		
		System.exit(1);
	}
}
