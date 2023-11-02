package com.andersenlab.hotel.repository.jpa;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class JpaUnitNameFromPersistentXml {
    public static String nameOfJPAUnitFromPersistenceFile() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.parse(new File("src/main/resources/META-INF/persistence.xml"));
        //Document document = db.parse(new File("./domain/src/main/resources/META-INF/persistence.xml"));
        NodeList nodeList = document.getElementsByTagName("persistence-unit");
        return nodeList.item(0).getAttributes().getNamedItem("name").getNodeValue();
    }
}
