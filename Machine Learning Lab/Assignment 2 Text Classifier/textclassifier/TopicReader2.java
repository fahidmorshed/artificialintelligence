/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package textclassifier;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Black_Knight
 */
public class TopicReader2 {
    String fileName;
    int length;
    int rowNum=0;
    int start;
    ArrayList<String> strings = new ArrayList<String>();

    public TopicReader2(String name,int length,int start) 
    {
        fileName = name;
        this.length = length;
        rowNum = 1;
        this.start = start;
    }
    
    public TopicReader2(String name , int len) {
        fileName = name;
        length = len;
    }
    
    public ArrayList<String> getStrings() throws ParserConfigurationException, SAXException, IOException
    {
        ArrayList<String> topicStrings = new ArrayList<String>();
        
        
        ArrayList<String> stopWords = new ArrayList<String>();
        
        File f3 = new File("stop_words.txt");
        
        Scanner input3 = null;
        try {
                        input3 = new Scanner(f3);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TextClassifier.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Input File Not Found");
            System.exit(-1);
        }
        while(input3.hasNextLine())
        {
            stopWords.add(input3.nextLine().trim().toLowerCase());
        }
        
        
        

            File fXmlFile = new File(fileName);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();

            //System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getElementsByTagName("row");

          //  System.out.println("Constructing data Set.....");
            
            int tmp;
            
            if(rowNum==0)
            {
                tmp = nList.getLength();
                start =0;
            }
            
            else 
            {
                tmp=this.length;
            }
            int rows =0;
            for (int temp = start; temp < (tmp+start); temp++) 
            {
                if(length!=-1 && rows>=length) break;
                rows++;
               

                    Node nNode = nList.item(temp);

                    
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) 
                    {

                            Element eElement = (Element) nNode;
                            
                            String s = eElement.getAttribute("Body");
                           
                            String target =s.replaceAll("\\<.*?>","");
                           
                         
                           StringTokenizer info = new StringTokenizer (target," \n?.,*()!\"");
                           while(info.hasMoreElements())
                           {
                               topicStrings.add((String)info.nextToken());
                           }
              
                    }

            }
            

        return topicStrings;
    }

    
}
