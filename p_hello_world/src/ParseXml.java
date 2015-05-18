import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.xml.sax.*;
import org.w3c.dom.*;

import java.io.IOException;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by huangca on 2015/4/23.
 */

class XmlNode{
        String select;
        String name;
        String id;
        //id,color
        }

public class ParseXml {

     String role1 ;
     String role2 ;

     ArrayList<String> rolev;
    public ParseXml() {
        System.out.println("ParseXml is construct!");
    }
   /*
    public boolean readXML(String xml) {
        rolev = new ArrayList<String>();
        Document dom;
        // Make an  instance of the DocumentBuilderFactory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            // use the factory to take an instance of the document builder
            DocumentBuilder db = dbf.newDocumentBuilder();
            // parse using the builder to get the DOM mapping of the
            // XML file
            dom = db.parse(xml);

            Element doc = dom.getDocumentElement();

            role1 = getTextValue(role1, doc, "DimA");
            if (role1 != null) {
                if (!role1.isEmpty())
                    rolev.add(role1);
            }
            role2 = getTextValue(role2, doc, "DimB");
            if (role2 != null) {
                if (!role2.isEmpty())
                    rolev.add(role2);
            }

            return true;

        } catch (ParserConfigurationException pce) {
            System.out.println(pce.getMessage());
        } catch (SAXException se) {
            System.out.println(se.getMessage());
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }

        return false;
    }
*/
    public void saveToXML(String xml) {
        Document dom;
        Element e = null;

        // instance of a DocumentBuilderFactory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            // use factory to get an instance of document builder
            DocumentBuilder db = dbf.newDocumentBuilder();
            // create instance of DOM
            dom = db.newDocument();

            // create the root element
            Element rootEle = dom.createElement("Dims");
            /*
            // create data elements and place them under root
            e = dom.createElement("DimA");
            e.appendChild(dom.createTextNode(role1));
            rootEle.appendChild(e);

            e = dom.createElement("DimB");
            e.appendChild(dom.createTextNode(role2));
            rootEle.appendChild(e);

            */
            dom.appendChild(rootEle);

            try {
                Transformer tr = TransformerFactory.newInstance().newTransformer();
                tr.setOutputProperty(OutputKeys.INDENT, "yes");
                tr.setOutputProperty(OutputKeys.METHOD, "xml");
                tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                //tr.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "dim.dtd");
                //tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

                // send DOM to file
                tr.transform(new DOMSource(dom),
                        new StreamResult(new FileOutputStream(xml)));

            } catch (TransformerException te) {
                System.out.println(te.getMessage());
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }
        } catch (ParserConfigurationException pce) {
            System.out.println("UsersXML: Error trying to instantiate DocumentBuilder " + pce);
        }
    }
public void alterToXml(String xml,XmlNode n){

    DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
    try {
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(xml);
        Element root = document.getDocumentElement();

        Collection<XmlNode> xmln = new ArrayList<XmlNode>();
        xmln.add(n);

        for (XmlNode node : xmln) {
            // server elements
            Element newServer = document.createElement("Dim");
            root.appendChild(newServer);

            Element name = document.createElement("select");
            name.appendChild(document.createTextNode(node.select));
            Element name2=document.createElement("name");
            name2.appendChild(document.createTextNode(node.name));
            Element name3=document.createElement("id");
            name3.appendChild(document.createTextNode(node.id));
            newServer.appendChild(name3);
            newServer.appendChild(name2);
            newServer.appendChild(name);




            root.appendChild(newServer);
        }

        DOMSource source = new DOMSource(document);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        StreamResult result = new StreamResult(xml);
        transformer.transform(source, result);
    }catch (TransformerException te) {
        System.out.println(te.getMessage());
    } catch (IOException ioe) {
        System.out.println(ioe.getMessage());
    }
 catch (ParserConfigurationException pce) {
        System.out.println("UsersXML: Error trying to instantiate DocumentBuilder " + pce);
    }catch (SAXException se) {
        System.out.println(se.getMessage());
    }

}



    private String getTextValue(String def, Element doc, String tag) {
        String value = def;
        NodeList nl;
        nl = doc.getElementsByTagName(tag);
        if (nl.getLength() > 0 && nl.item(0).hasChildNodes()) {
            value = nl.item(0).getFirstChild().getNodeValue();
        }
        return value;
    }


}


