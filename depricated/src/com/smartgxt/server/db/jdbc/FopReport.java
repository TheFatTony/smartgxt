package com.smartgxt.server.db.jdbc;

import com.smartgxt.server.base.executers.RpcRequestExecuter;

/**
 * @author Anton Alexeyev
 * 
 */
@Deprecated
public class FopReport extends RpcRequestExecuter {

	// private FopFactory fopFactory;
	// private TransformerFactory tFactory;
	// static private DefaultConfigurationBuilder cfgBuilder;
	// static private Configuration cfg;
	// private Fop fop;

	static {
		// try {
		// cfgBuilder = new DefaultConfigurationBuilder();
		// cfg = cfgBuilder.buildFromFile(new File("fop/conf.xml"));
		// } catch (ConfigurationException e) {
		// e.printStackTrace();
		// } catch (SAXException e) {
		// e.printStackTrace();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
	}

	private String code = "Untitled2";

	public FopReport() {
		// fopFactory = FopFactory.newInstance();
		// tFactory = TransformerFactory.newInstance();
		// // private String sFormat = null;
		// // private String sExtension = null;
		// fop = null;

	}

	public void report() throws Exception {
		// fopFactory.setUserConfig(cfg);
		// StreamDefinition def = getSession().getUserStreams()
		// .addStreamDefinition(code + ".pdf");
		// fop = fopFactory.newFop(MimeConstants.MIME_PDF,
		// def.getOutputStream());
		// InputStream xsltFile = new FileInputStream(RemoteServlet.get()
		// .getConfig().getAppName()
		// + "/reports/en/" + code + ".xsl");
		// Source xsltSrc = new StreamSource(xsltFile);
		// Transformer transformer = tFactory.newTransformer(xsltSrc);
		//
		// OracleSession session = (OracleSession) getSession();
		// Connection conn = session.getConnection();
		//
		// CallableStatement p = (CallableStatement) conn.prepareCall("BEGIN "
		// + "SELECT DBMS_XMLGEN.getXMLType ( " + "'SELECT LEVEL id, "
		// + "''test '' || LEVEL name, "
		// + "DECODE (MOD (LEVEL, 2), 0, ''Auto'', ''Media'') data, "
		// + "SYSDATE date_, " + "999999999999999999999999999 big_num, "
		// + "0.99999999999999999999999999 tin_num " + "FROM DUAL "
		// + "CONNECT BY LEVEL <= 1500').getBlobVal(871) "
		// + "INTO :p_data " + "FROM DUAL; " + "END;");
		// p.registerOutParameter("p_data", OracleTypes.BLOB);
		// p.execute();
		//
		// Blob object = p.getBlob("p_data");
		//
		// InputStream is = object.getBinaryStream();
		//
		// StreamDefinition def1 = getSession().getUserStreams()
		// .addStreamDefinition("data.xml");
		// def1.setInputStream(is);
		//
		// Result res = new SAXResult(fop.getDefaultHandler());
		// Source src = new StreamSource(new InputStreamReader(
		// def1.getFileInputStream()));
		// transformer.transform(src, res);
		//
		// setResponseProperty("file", def);

	}

	@Override
	public void execute() throws Exception {
		report();
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}
