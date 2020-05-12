package com.bitozen.wms.report.common.converterenggine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StreamGobbler extends Thread {
	private InputStream is;
	private OutputStream os;

	private static final Logger logger = LogManager.getLogger();

	/** Constructor with no output stream. Output from the process
	 *  will be discarded.
	 */
	public StreamGobbler(InputStream is) {
		this(is, null);
	}

	/** Constructor with output stream. Output from the process will
	 *  be sent to it on a line-by-line basis.
	 */
	public StreamGobbler(InputStream is, OutputStream redirect) {
		this.is = is;
		this.os = redirect;
	}

	public void run() {
		try {
			PrintWriter pw = null;
			if (os != null)
				pw = new PrintWriter(os);

			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null) {
				if (pw != null)
					pw.println(line);
			}
			if (pw != null)
				pw.flush();
		} catch (IOException ioe) {
			logger.error("Error reading input command.", ioe);
		}
	}
}
