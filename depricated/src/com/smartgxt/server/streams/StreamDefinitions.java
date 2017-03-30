/**
 * 
 */
package com.smartgxt.server.streams;

import java.io.Flushable;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletInputStream;

import com.smartgxt.server.base.configs.Configuration;

/**
 * @author Anton Alexeyev
 * 
 */

// TODO ContentLength!!!

public class StreamDefinitions implements Flushable {

	private static String dispositionAttr = "Content-Disposition:";
	private static String typeAttr = "Content-Type:";

	private static Map<String, StreamDefinition> streams;
	private Map<String, StreamDefinition> userStreams;

	static {
		streams = new HashMap<String, StreamDefinition>();
	}

	public StreamDefinition getStreamDefinition(String id) {
		return userStreams.get(id);
	}

	public StreamDefinitions() {
		userStreams = new HashMap<String, StreamDefinition>();

	}

	public String addStreamDefinition(StreamDefinition def) {
		String id = randString(36);
		userStreams.put(id, def);
		def.setId(id);
		streams.put(id, def);
		return id;
	}

	public StreamDefinition addStreamDefinition(String fileName) {
		StreamDefinition sd = new StreamDefinition();
		sd.setFileName(fileName);
		addStreamDefinition(sd);
		return sd;
	}

	public StreamDefinition addStreamDefinition(String contentType,
			ServletInputStream servletInputStream) throws IOException {
		StreamDefinition sd = new StreamDefinition();
		addStreamDefinition(sd);
		int lastIndex = contentType.lastIndexOf("=");
		String boundary = contentType.substring(lastIndex + 1,
				contentType.length());

		String str = null;
		int buffSize = Configuration.getBuffersSize();
		int result;
		boolean skipLine = false;
		byte[] line = new byte[buffSize];

		String fileName = null;
		String fileType = null;

		OutputStream os = sd.getOutputStream();
		while ((result = servletInputStream.readLine(line, 0, buffSize)) != -1) {
			str = new String(line, 0, result, "UTF-8");
			if (!skipLine) {
				if (str.indexOf(dispositionAttr) != -1) {
					int tmp = str.indexOf("filename=") + "filename=".length()
							+ 1;
					fileName = str.substring(tmp, str.length() - 3);
				} else if (str.indexOf(typeAttr) != -1) {
					int tmp = str.indexOf(typeAttr) + typeAttr.length() + 1;
					fileType = str.substring(tmp, str.length() - 2);
					skipLine = true;
				} else if (str.indexOf(boundary) == -1) {
					os.write(line, 0, result);
					os.flush();
				}
			} else {
				skipLine = false;
			}
		}
		sd.setFileName(fileName);
		sd.setContentType(fileType);
		os.close();

		return sd;
	}

	public String addStreamDefinition(String contentType, String fileName) {
		StreamDefinition sd = new StreamDefinition();
		sd.setFileName(fileName);
		sd.setContentType(contentType);
		return addStreamDefinition(sd);
	}

	public String randString(int numChars) {
		int numBits = (int) (Math.floor(numChars * Math.log(36) / Math.log(2)));
		String str = new java.math.BigInteger(numBits, new java.util.Random())
				.toString(36);
		if (streams.get(str) != null)
			str = randString(numChars);
		return str.toUpperCase();
	}

	public Map<String, StreamDefinition> getUserStreams() {
		return userStreams;
	}

	@Override
	public void flush() {
		for (String s : userStreams.keySet()) {
			// FileSytem.deleteFile(catalogPath + "/" + s);
			System.out.println("deleted file: "
					+ streams.get(s).getFile().getAbsolutePath());
			streams.get(s).getFile().delete();
			streams.remove(s);
		}
		userStreams.clear();
		// streams.clear();
	}

}
