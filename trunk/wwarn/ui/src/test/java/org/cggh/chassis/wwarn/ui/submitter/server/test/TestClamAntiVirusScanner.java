package org.cggh.chassis.wwarn.ui.submitter.server.test;


import java.io.InputStream;
import java.net.ConnectException;
import java.net.Socket;

import org.cggh.chassis.wwarn.ui.submitter.server.ClamAntiVirusScanner;
import org.cggh.chassis.wwarn.ui.submitter.server.ScannerException;

import junit.framework.TestCase;
/**
 * You have to install a clamd server for this to work.
 * This will need to be configured to use tcp sockets. 
 * 
 * On Debian the following work 
 * 
 * @author Jean-Francois POUX, jfp@taldius.net
 * @author timp
 * @since 25-Jan-2010 
 */
public class TestClamAntiVirusScanner extends TestCase {

	String host = "127.0.0.1";
	//int port = 3310;  // the default
	int port = 9998;
	int timeout = 1; // 1 second is pretty short 
	
	public void testSuccess () throws Exception {
		try { 
			new Socket(host, port);
			
			ClamAntiVirusScanner scanner = new ClamAntiVirusScanner (host, port, timeout);
			InputStream inputStream = getClass().getResourceAsStream("/log4j.properties");
			assertNotNull(inputStream);
			assertTrue(scanner.performScan(inputStream));
			assertEquals("stream: OK", scanner.getMessage());
		} catch (ConnectException e){ 
			System.err.println("clamd not running - test not run");
		}
	}
	
	public void testVirus () throws Exception {
		try { 
			new Socket(host, port);
			
			ClamAntiVirusScanner scanner = new ClamAntiVirusScanner (host, port, timeout);
			InputStream inputStream = getClass().getResourceAsStream("/eicar.com.txt");
			assertNotNull(inputStream);
			assertFalse(scanner.performScan(inputStream));
			assertEquals("stream: Eicar-Test-Signature FOUND", scanner.getMessage());
		} catch (ConnectException e){ 
			System.err.println("clamd not running - test not run");
		}
	}
	
	
	public void testBadHostScanner () throws Exception { 
		ClamAntiVirusScanner scanner = new ClamAntiVirusScanner ("127.0.0.5", port, timeout);
		InputStream inputStream = getClass().getResourceAsStream("/log4j.properties");
		assertNotNull(inputStream);
		try { 
			assertTrue(scanner.performScan(inputStream));
			fail("Should have bombed");
		} catch (ScannerException e) { 
			assertEquals("Connection refused", e.getCause().getMessage());
		}
	}
	public void testBadPortScanner () throws Exception { 
		ClamAntiVirusScanner scanner = new ClamAntiVirusScanner (host, 999, timeout);
		InputStream inputStream = getClass().getResourceAsStream("/log4j.properties");
		assertNotNull(inputStream);
		try { 
			assertTrue(scanner.performScan(inputStream));
			fail("Should have bombed");
		} catch (ScannerException e) { 
			assertEquals("Connection refused", e.getCause().getMessage());
		}
	}
}
