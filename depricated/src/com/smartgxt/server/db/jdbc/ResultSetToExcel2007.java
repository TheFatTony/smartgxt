package com.smartgxt.server.db.jdbc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.smartgxt.server.base.configs.Configuration;
import com.smartgxt.shared.ExcelColumnsConfig;
import com.smartgxt.shared.ExcelColumnsGroups;

/**
 * @author Anton Alexeyev
 * 
 */
public class ResultSetToExcel2007 {

	protected ResultSet resultSet;
	protected ArrayList<ExcelColumnsConfig> formatTypes;
	protected ArrayList<ExcelColumnsGroups> groups;
	protected String sheetName;
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;

	private static Map<String, XSSFCellStyle> styles;

	public ResultSetToExcel2007(ResultSet resultSet,
			ArrayList<ExcelColumnsConfig> formatTypes,
			ArrayList<ExcelColumnsGroups> groups, String sheetName)
			throws NoClassDefFoundError {
		// super(resultSet, formatTypes, sheetName);
		this.resultSet = resultSet;
		this.formatTypes = formatTypes;
		this.sheetName = sheetName;
		this.groups = groups;
		workbook = new XSSFWorkbook();
		sheet = workbook.createSheet(sheetName);
		styles = createStyles(workbook);
	}

	// public Map<String, ExcelOptions> getFormatTypes() {
	// return formatTypes;
	// }
	//
	// public void setFormatTypes(Map<String, ExcelOptions> formatTypes) {
	// this.formatTypes = formatTypes;
	// }

	/**
	 * Create a library of cell styles.
	 */
	private static Map<String, XSSFCellStyle> createStyles(XSSFWorkbook wb) {
		Map<String, XSSFCellStyle> styles = new HashMap<String, XSSFCellStyle>();
		XSSFDataFormat fmt = wb.createDataFormat();

		// XSSFCellStyle styleCells1 = wb.createCellStyle();
		// styleCells1.setAlignment(XSSFCellStyle.ALIGN_RIGHT);
		// styleCells1.setDataFormat(fmt.getFormat(ColumnsFormatNumber.INTEGER
		// .getFormat()));
		// styles.put(ColumnsFormatNumber.INTEGER, styleCells1);
		//
		// XSSFCellStyle styleCells2 = wb.createCellStyle();
		// styleCells2.setAlignment(XSSFCellStyle.ALIGN_RIGHT);
		// styleCells2.setDataFormat(fmt.getFormat(ColumnsFormatNumber.FLOAT
		// .getFormat()));
		// styles.put(ColumnsFormatNumber.FLOAT, styleCells2);
		//
		// XSSFCellStyle styleCells3 = wb.createCellStyle();
		// styleCells3.setAlignment(XSSFCellStyle.ALIGN_RIGHT);
		// styleCells3.setDataFormat(fmt.getFormat(ColumnsFormatDate.DATE
		// .getFormat()));
		// styles.put(ColumnsFormatDate.DATE, styleCells3);
		//
		// XSSFCellStyle styleCells6 = wb.createCellStyle();
		// styleCells6.setAlignment(XSSFCellStyle.ALIGN_RIGHT);
		// styleCells6.setDataFormat(fmt.getFormat(ColumnsFormatDate.DATETIME
		// .getFormat()));
		// styles.put(ColumnsFormatDate.DATETIME, styleCells6);
		//
		// XSSFCellStyle styleCells7 = wb.createCellStyle();
		// styleCells7.setAlignment(XSSFCellStyle.ALIGN_RIGHT);
		// styleCells7.setDataFormat(fmt.getFormat(ColumnsFormatDate.TIME
		// .getFormat()));
		// styles.put(ColumnsFormatDate.TIME, styleCells7);

		XSSFCellStyle styleCells4 = wb.createCellStyle();
		styleCells4.setAlignment(XSSFCellStyle.ALIGN_LEFT);
		styles.put("text", styleCells4);

		// XSSFCellStyle style5 = wb.createCellStyle();
		// XSSFFont headerFont = wb.createFont();
		// headerFont.setBold(true);
		// style5.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		// //
		// style5.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		// // style5.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		// style5.setFont(headerFont);
		// styles.put(ColumnsFormatText.HEADER, style5);

		return styles;
	}

	// public ResultSetToExcel2007(ResultSet resultSet, String sheetName) {
	// this(resultSet, null, null, sheetName);
	// }

	public void generate(OutputStream out) throws Exception {

		// Step 1. Create a template file. Setup sheets and workbook-level
		// objects such as
		// cell styles, number formats, etc.

		// workbook = new XSSFWorkbook();
		// sheet = workbook.createSheet("Big Grid");

		// name of the zip entry holding sheet data, e.g.
		// /xl/worksheets/sheet1.xml
		String sheetRef = sheet.getPackagePart().getPartName().getName();

		// save the template
		// String fileName = "template" + (i++) + ".xlsx";
		// FileOutputStream os = new FileOutputStream(fileName);
		File template = File.createTempFile("template", ".xlsx");
		template.deleteOnExit();
		FileOutputStream os = new FileOutputStream(template);
		workbook.write(os);
		os.close();

		// Step 2. Generate XML file.
		File tmp = File.createTempFile("sheet", ".xml");
		tmp.deleteOnExit();
		Writer fw = new FileWriter(tmp);
		createDocument(fw);
		fw.close();

		// Step 3. Substitute the template entry with the generated data
		// FileOutputStream out = new FileOutputStream("big-grid.xlsx");
		substitute(template, tmp, sheetRef.substring(1), out);
		out.close();
	}

	public void createDocument(Writer outputStream) throws Exception {
		int currentRow = 0;

		SpreadsheetWriter sw = new SpreadsheetWriter(outputStream);
		sw.beginSheet(formatTypes);

		int styleIndex = styles.get("text").getIndex();
		if (groups != null) {
			sw.insertRow(currentRow);
			String prevCell = null;

			for (ExcelColumnsConfig ft : formatTypes) {
				String s = "";
				for (ExcelColumnsGroups g : groups) {
					if (ft.getIndex() == g.getColumn()) {
						s = g.getText();
						break;
					}
				}

				prevCell = sw.createCell(ft.getIndex(), s, styleIndex);

				for (ExcelColumnsGroups g : groups) {
					if (ft.getIndex() == g.getColumn()) {
						g.setFromCell(prevCell);
					}
					if (ft.getIndex() == (g.getColumn() + g.getColumnsSpan() - 1)) {
						g.setToCell(prevCell);
					}
				}
			}
			sw.endRow();
			currentRow++;
		}

		sw.insertRow(currentRow);
		// int styleIndex = styles.get(ColumnsFormatText.HEADER).getIndex();

		for (ExcelColumnsConfig ft : formatTypes) {
			sw.createCell(ft.getIndex(), ft.getHeader(), styleIndex);
		}
		sw.endRow();

		currentRow++;
		// Write report rows
		while (resultSet.next()) {
			sw.insertRow(currentRow++);

			for (ExcelColumnsConfig ft : formatTypes) {
				Object value = resultSet.getObject(ft.getId());
				// styleIndex = styles.get(ft.getFormat()).getIndex();
				styleIndex = styles.get("text").getIndex();

				if (value instanceof Date) {
					java.sql.Timestamp d = (java.sql.Timestamp) value;
					java.util.Date dt = new java.util.Date(d.getTime());
					sw.createCell(ft.getIndex(), dt, styleIndex);
				} else if (value instanceof BigDecimal) {
					BigDecimal b = (BigDecimal) value;
					// sw.createCell(i, b.doubleValue(), styleIndex);
					sw.createCell(ft.getIndex(), b, styleIndex);
				} else
					sw.createCell(ft.getIndex(), value.toString(), styleIndex);
			}

			sw.endRow();
		}
		sw.endSheet(groups);
	}

	/**
	 * 
	 * @param zipfile
	 *            the template file
	 * @param tmpfile
	 *            the XML file with the sheet data
	 * @param entry
	 *            the name of the sheet entry to substitute, e.g.
	 *            xl/worksheets/sheet1.xml
	 * @param out
	 *            the stream to write the result to
	 */
	private static void substitute(File zipfile, File tmpfile, String entry,
			OutputStream out) throws IOException {
		ZipFile zip = new ZipFile(zipfile);

		ZipOutputStream zos = new ZipOutputStream(out);

		@SuppressWarnings("unchecked")
		Enumeration<ZipEntry> en = (Enumeration<ZipEntry>) zip.entries();
		while (en.hasMoreElements()) {
			ZipEntry ze = en.nextElement();
			if (!ze.getName().equals(entry)) {
				zos.putNextEntry(new ZipEntry(ze.getName()));
				InputStream is = zip.getInputStream(ze);
				copyStream(is, zos);
				is.close();
			}
		}
		zos.putNextEntry(new ZipEntry(entry));
		InputStream is = new FileInputStream(tmpfile);
		copyStream(is, zos);
		is.close();

		zos.close();

		zipfile.delete();
		tmpfile.delete();
	}

	private static void copyStream(InputStream in, OutputStream out)
			throws IOException {
		byte[] chunk = new byte[Configuration.getBuffersSize()];
		int count;
		while ((count = in.read(chunk)) >= 0) {
			out.write(chunk, 0, count);
		}
	}

	/**
	 * Writes spreadsheet data in a Writer. (YK: in future it may evolve in a
	 * full-featured API for streaming data in Excel)
	 */
	public static class SpreadsheetWriter {
		private final Writer _out;
		private int _rownum;

		public SpreadsheetWriter(Writer out) {
			_out = out;
		}

		public void beginSheet(ArrayList<ExcelColumnsConfig> formatTypes)
				throws IOException {
			_out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
					+ "<worksheet xmlns=\"http://schemas.openxmlformats.org/spreadsheetml/2006/main\">");
			_out.write("<cols>\n");

			for (ExcelColumnsConfig ft : formatTypes) {
				int idx = ft.getIndex() + 1;
				_out.write("<col min=\"" + idx + "\" max=\"" + idx
						+ "\" width=\"" + ft.getWidth() / 7
						+ "\" customWidth=\"1\" />\n");
			}

			_out.write("</cols>\n");
			_out.write("<sheetData>\n");

		}

		public void endSheet(ArrayList<ExcelColumnsGroups> groups)
				throws IOException {
			_out.write("</sheetData>");
			if (groups != null) {
				_out.write("<mergeCells count=\"" + groups.size() + "\">\n");
				for (ExcelColumnsGroups g : groups) {
					_out.write("<mergeCell ref=\"" + g.getFromCell() + ":"
							+ g.getToCell() + "\"/>\n");
				}
				_out.write("</mergeCells>\n");
			}
			_out.write("</worksheet>");
		}

		/**
		 * Insert a new row
		 * 
		 * @param rownum
		 *            0-based row number
		 */
		public void insertRow(int rownum) throws IOException {
			_out.write("<row r=\"" + (rownum + 1) + "\">\n");
			this._rownum = rownum;
		}

		/**
		 * Insert row end marker
		 */
		public void endRow() throws IOException {
			_out.write("</row>\n");
		}

		public String createCell(int columnIndex, String value, int styleIndex)
				throws IOException {

			String ref = new CellReference(_rownum, columnIndex)
					.formatAsString();
			_out.write("<c r=\"" + ref + "\" t=\"inlineStr\"");
			if (styleIndex != -1)
				_out.write(" s=\"" + styleIndex + "\"");
			_out.write(">");
			_out.write("<is><t>" + value + "</t></is>");
			_out.write("</c>");
			return ref;
		}

		public String createCell(int columnIndex, String value)
				throws IOException {
			return createCell(columnIndex, value, -1);
		}

		public String createCell(int columnIndex, BigDecimal value,
				int styleIndex) throws IOException {
			String ref = new CellReference(_rownum, columnIndex)
					.formatAsString();
			_out.write("<c r=\"" + ref + "\" t=\"n\"");
			if (styleIndex != -1)
				_out.write(" s=\"" + styleIndex + "\"");
			_out.write(">");
			_out.write("<v>" + value + "</v>");
			_out.write("</c>");
			return ref;
		}

		public String createCell(int columnIndex, double value, int styleIndex)
				throws IOException {
			String ref = new CellReference(_rownum, columnIndex)
					.formatAsString();
			_out.write("<c r=\"" + ref + "\" t=\"n\"");
			if (styleIndex != -1)
				_out.write(" s=\"" + styleIndex + "\"");
			_out.write(">");
			_out.write("<v>" + value + "</v>");
			_out.write("</c>");
			return ref;
		}

		public String createCell(int columnIndex, BigDecimal value)
				throws IOException {
			return createCell(columnIndex, value, -1);
		}

		public String createCell(int columnIndex, Date value, int styleIndex)
				throws IOException {
			return createCell(columnIndex, DateUtil.getExcelDate(value, false),
					styleIndex);
		}
	}

}