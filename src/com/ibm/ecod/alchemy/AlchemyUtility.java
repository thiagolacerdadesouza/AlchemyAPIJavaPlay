package com.ibm.ecod.alchemy;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class AlchemyUtility {


	//XMLToStringConverter
	public static String returnResultFromXML(String convertDocToString, String tag ) throws SAXException, IOException, ParserConfigurationException {
			String alchemyResult = null;
			//XML Conversion 
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			// Load the input XML document, parse it and return an instance of the
			// Document class.
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(convertDocToString));
			Document document = builder.parse(is);
			NodeList nodes = document.getElementsByTagName("results");
			for (int i = 0; i < nodes.getLength(); i++) {
				Element element = (Element) nodes.item(i);
				NodeList authorElement = element.getElementsByTagName(tag);
				Element line = (Element) authorElement.item(i);
				//System.out.println("author is" + authorElement.toString());
				Node child = line.getFirstChild();
				if (child instanceof CharacterData) {
					CharacterData cd = (CharacterData) child;
					alchemyResult = cd.getData();
				}
			}

			return alchemyResult;
		}


	// utility method
    public static String getStringFromDocument(Document doc) {
        try {
            DOMSource domSource = new DOMSource(doc);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.transform(domSource, result);

            return writer.toString();
        } catch (TransformerException ex) {
            ex.printStackTrace();
            return null;
        }
    }	
    
}
