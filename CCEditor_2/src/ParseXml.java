import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

/**
 * Created by huangca on 2015/4/23.
 */

class XmlNode{
        String select;
        String name;
        String id;
        String color;
        //id,color
        }

public class ParseXml {

     String role1 ;
     String role2 ;

     ArrayList<String> rolev;
    public ParseXml() {
        System.out.println("ParseXml is construct!");
    }

    public static Color hex2Rgb(String colorStr) {
        return new Color(
                Integer.valueOf( colorStr.substring( 1, 3 ), 16 ),
                Integer.valueOf( colorStr.substring( 3, 5 ), 16 ),
                Integer.valueOf( colorStr.substring( 5, 7 ), 16 ) );
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

    public Vector<TePanel> readToXML(String xml){
        Vector<TePanel> panelx=new Vector();
        try {

            File fXmlFile = new File(xml);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();

            //create a vector fill all data in it and return


            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getElementsByTagName("Dim");

            System.out.println("----------------------------");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

                System.out.println("\nCurrent Element :" + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    System.out.println("id : " + eElement.getElementsByTagName("id").item(0).getTextContent());
                    System.out.println("Select : " + eElement.getElementsByTagName("select").item(0).getTextContent());
                    System.out.println("Name : " + eElement.getElementsByTagName("name").item(0).getTextContent());
                    System.out.println("Color : " + eElement.getElementsByTagName("color").item(0).getTextContent());

                    //create  Vector<TePanel> and return;
                   TePanel panel_x=new TePanel( eElement.getElementsByTagName("name").item(0).getTextContent(),Integer.parseInt(eElement.getElementsByTagName("id").item(0).getTextContent()));
                    switch(eElement.getElementsByTagName("select").item(0).getTextContent()){
                        case "Unselected":
                            panel_x.getUnselect().setSelected(true);
                            break;
                        case "Left":
                            panel_x.getLeft().setSelected(true);
                            break;
                        case "Right":
                            panel_x.getRight().setSelected(true);
                            break;

                    }
                    Color color=Color.decode(eElement.getElementsByTagName("color").item(0).getTextContent());
                    panel_x.getAp().setBackground(color);
                    panel_x.getUnselect().setBackground(color);
                    panel_x.getRight().setBackground(color);
                    panel_x.getLeft().setBackground(color);
                    panelx.add(panel_x);


                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return panelx;

    }


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
public void alterToXml(String xml, XmlNode n){

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
            Element name4=document.createElement("color");
            name4.appendChild(document.createTextNode(node.color));
            newServer.appendChild(name3);
            newServer.appendChild(name2);
            newServer.appendChild(name);
            newServer.appendChild(name4);




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


