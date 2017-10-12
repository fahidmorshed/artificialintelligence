/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package textclassifier;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Black_Knight
 */
public class TopicReader {
    String fileName;
    int length;
    int start_index;
    ArrayList<String> strings = new ArrayList<String>();

    public TopicReader(String name,int length) {
        fileName = name;
        this.length = length;
        start_index = 0;
    }
    
    public TopicReader(String name) {
        fileName = name;
        length = -1;
        start_index = 0;
    }
    
    public TopicReader(String name,int length,int begin)
    {
        fileName = name;
        this.length = length;
        start_index = begin;
    }
    
    
    
    public ArrayList<String> getStrings()
    {
        ArrayList<String> topicStrings = new ArrayList<String>();
        
        try {

            File fXmlFile = new File(fileName);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("row");

            for (int temp = start_index; temp < nList.getLength(); temp++)
            {
                
                if(length!=-1 && (temp-start_index)>=length) break;

                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element eElement = (Element) nNode;
                            
                    String s = eElement.getAttribute("Body");
                    topicStrings.add(s);        
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return topicStrings;
    }
    
    
}
