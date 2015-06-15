import org.w3c.dom.*;
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

    //not use in this project keep it just in case
    public static Color hex2Rgb(String colorStr) {
        return new Color(
                Integer.valueOf( colorStr.substring( 1, 3 ), 16 ),
                Integer.valueOf( colorStr.substring( 3, 5 ), 16 ),
                Integer.valueOf( colorStr.substring( 5, 7 ), 16 ) );
    }




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



            NodeList nList = doc.getElementsByTagName("Dim");


            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);


                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;


                    //create  Vector<TePanel> and return;
                   TePanel panel_x=new TePanel( eElement.getAttribute("name"),Integer.parseInt(eElement.getAttribute("id")));
                    switch(eElement.getAttribute("select")){
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
                    Color color=Color.decode(eElement.getAttribute("color"));
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
            dom.appendChild(rootEle);

            try {
                Transformer tr = TransformerFactory.newInstance().newTransformer();
                tr.setOutputProperty(OutputKeys.INDENT, "yes");
                tr.setOutputProperty(OutputKeys.METHOD, "xml");
                tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

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

            Attr attr_id = document.createAttribute("id");
            attr_id.setValue(node.id);
            newServer.setAttributeNode(attr_id);


            Attr attr_name = document.createAttribute("name");
            attr_name.setValue(node.name);
            newServer.setAttributeNode(attr_name);

            Attr attr_select = document.createAttribute("select");
            attr_select.setValue(node.select);
            newServer.setAttributeNode(attr_select);

            Attr attr_color = document.createAttribute("color");
            attr_color.setValue(node.color);
            newServer.setAttributeNode(attr_color);





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


// not use in this project keep it just in case
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


