package org.cggh.chassis.manta.loadtest;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.Iterator;
import java.util.Vector;

public class LoadTest {

	private String fileName;
	private DiscreteDistribution urlsToHit = null;
	static int requestsPerRate = 30;
	static int maxHitsPerSec = 400;
	int running = 0;
	private static final byte[] bitBucket = new byte[4096];

	public LoadTest(String url) {
		this.fileName = filenameFrom(url);
		urlsToHit = new DiscreteDistribution();
		for (int histPS = 1; histPS < maxHitsPerSec; histPS++) {
			urlsToHit.add(new InstrumentedUrlRequestSet(url, histPS));
		}
	}

	class HitStats {
		public long totalTimeToOpen, totalTimeToFinish;
		public int successes;
		public int failures;

		public void notifySuccess(long started, long opened, long finished) {
			totalTimeToOpen += opened - started;
			totalTimeToFinish += finished - started;
			--failures; // Undo default
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
		long hitsPerSecond;

		InstrumentedUrlRequestSet(String url, long hitsPerSecond) {
			this.url = url;
			this.hitsPerSecond = hitsPerSecond;
		}

		void go() throws Exception {
			for (int i = 0; i < requestsPerRate; i++) {
				//System.err.println("sleeping " + waitMillis());
				Thread.sleep(waitMillis());
				new BitBucketClient(url, stats).start();
			}
		}

		public String toString() {
			return url + "(" + hitsPerSecond + ")";
		}

		public long waitMillis() {
			return 1000L / hitsPerSecond;
		}

		public String url() {
			return url;
		}

		public long hitsPerSecond() {
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
			running++;
			//System.err.print(url + " >>/dev/null ");
			InputStream inputStream = null;
			hitStats.notifyFailure(); // Our main failure is timeout
			try {
				long started = System.currentTimeMillis();
				inputStream = new URL(url).openStream();
				long opened = System.currentTimeMillis();
				while (inputStream.read(bitBucket) != -1)
					;
				long finished = System.currentTimeMillis();

				//System.err.println(" :" + started + " " + opened + " " + finished);
				hitStats.notifySuccess(started, opened, finished);

				inputStream.close();
			} catch (Exception e) {
				System.err.println(e);
			}
			running--;
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
		System.err.println(" " + filename + " " + filename.lastIndexOf('/') + "==" +  (filename.length() -1)) ;
		if (filename.length() > 0  && filename.lastIndexOf('/') == filename.length() -1)
			filename = filename.substring(0,filename.length() -1);
        if (filename.lastIndexOf('/') != -1)
        	filename = filename.substring(filename.lastIndexOf('/') +1);
        return filename;
	}

	public void run() throws Exception {
		FileOutputStream output = new FileOutputStream(fileName + ".csv");
		PrintStream out = new PrintStream(output);

		for (InstrumentedUrlRequestSet iurs : urlsToHit) {
			System.err.println(iurs);
			iurs.go();
			while (running > 0)
				Thread.sleep(100);
			Thread.sleep(500);
		}

		System.err.println("stopping");
		Thread.sleep(1500);

		out.println("url, count, success, rate, start, complete");
		for (InstrumentedUrlRequestSet iurs : urlsToHit) {
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
	}

	public static void main(String[] args) throws Exception {
		new LoadTest("http://cloud1.cggh.org/manta/questionnaire/").run();
		
		//System.err.println(filenameFrom("http://localhost:8080/manta/questionnaire/"));
		//System.err.println(filenameFrom("http://cloud1.cggh.org/manta/questionnaire/"));
		//System.err.println(filenameFrom("http://paneris.org/"));
		//System.err.println(filenameFrom("http://paneris.org/index.html"));
		
		System.exit(1);
	}
}
