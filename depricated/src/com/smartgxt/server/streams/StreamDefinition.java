package com.smartgxt.server.streams;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.smartgxt.server.base.configs.Configuration;

/**
 * @author Anton Alexeyev
 * 
 */
public class StreamDefinition {

	private String contentType = "application/octet-stream";
	private String fileName;
	private String id;
	private File file;

	public StreamDefinition() {
		try {
			file = File.createTempFile(Configuration.getAppName(), ".tmp");
			System.out.println("created file: " + file.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		file.deleteOnExit();
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getContentType() {
		return contentType;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setInputStream(InputStream inputStream) throws IOException {
		OutputStream os;
		os = new FileOutputStream(file);

		byte[] buffer = new byte[Configuration.getBuffersSize()];
		for (int n; (n = inputStream.read(buffer)) != -1;) {
			os.write(buffer, 0, n);
			os.flush();
		}

		os.close();
	}

	public FileInputStream getFileInputStream() throws FileNotFoundException {
		FileInputStream d = new FileInputStream(file);
		return d;
	}

	public OutputStream getOutputStream() throws FileNotFoundException {
		OutputStream os;
		os = new FileOutputStream(file);
		return os;
	}

	public void dumpToOutputStream(OutputStream outputStream)
			throws IOException {
		InputStream os = new FileInputStream(file);
		byte[] buffer = new byte[Configuration.getBuffersSize()];
		for (int n; (n = os.read(buffer)) != -1;) {
			outputStream.write(buffer, 0, n);
			outputStream.flush();
		}
		os.close();
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

}
