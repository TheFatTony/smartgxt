package com.smartgxt.server.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.smartgxt.shared.Utilities;

/**
 * @author Anton Alexeyev
 * 
 */
public class Logger {

	private File file;
	private Writer output;

	public Logger() {
		// TODO use this:
		// java.util.logging.ConsoleHandler;
	}

	public void log(Throwable error) {

		SimpleDateFormat timeFormat = new SimpleDateFormat(
				"yyyy.MM.dd HH:mm:ss");

		file = new File("file.log");
		// if (file.length() > 20000) {
		//
		// if (!file.renameTo(new File("file1" + ".log", file.getName()))) {
		// System.out.println("!!!! Не удалось переместить файл");
		// }
		// file = new File("file.log");
		//
		// }
		try {
			output = new BufferedWriter(new FileWriter(file, true));
			output.write("<error>" + "\r\n");
			output.write("<effective_date>" + timeFormat.format(new Date())
					+ "</effective_date>" + "\r\n");
			output.write("<message>" + Utilities.getMessage(error)
					+ "</message>" + "\r\n");
			output.write("<stack_trace>" + Utilities.getStackTrace(error)
					+ "</stack_trace>" + "\r\n");
			output.write("</error>" + "\r\n");
			output.flush();
			output.close();
		} catch (IOException e) {
			System.err.println(Utilities.getMessage(e) + "\n"
					+ Utilities.getStackTrace(e));
		}

		// LogReader.parse();

	}
}
