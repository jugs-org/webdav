package org.jugs.webdav.interop;

import java.io.CharArrayWriter;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class XslTransformer {
	public static void main(String[] args) {
		String styleSheet = "src/main/resources/xml/prefix_v1.xsl";
//		String styleSheet = "xml/prefix_v2.xsl";
		String webdavResponse = "src/main/resources/xml/webdav.xml";
		
		Source styleSource = new StreamSource(styleSheet);
		Source webdavSource = new StreamSource(webdavResponse); 
		
		try {
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory
					.newTransformer(styleSource);
			CharArrayWriter caw = new CharArrayWriter();
			StreamResult result = new StreamResult(caw);
			transformer.transform(webdavSource, result);

			System.out.println(caw.toString());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
