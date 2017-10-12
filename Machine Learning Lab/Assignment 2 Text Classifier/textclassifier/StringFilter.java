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
public class StringFilter {
    String str;
    String filteredString;

    public StringFilter(String s) {
        str = s;
        filteredString = FilterString();
    }
    
    public ArrayList<String> getWords()
    {
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
        
        ArrayList<String> words = new ArrayList<String>();                            
        
        String splitString[] = filteredString.split("[ \n]");
                            
        for(int k=0;k<splitString.length;k++)
        {
            String t = splitString[k].toLowerCase();
                                
            if(t.equals("\n") || t.equals(""));
            else if(stopWords.contains((Object)t)) ; //System.out.println(t);
            else
            {
                if(t.charAt(0)=='\"' || t.charAt(0)=='(')
                {
                    if(t.length()!=1) t = t.substring(1);
                }
                                    
                                    
                 if(t.charAt(t.length()-1)=='\"' || t.charAt(t.length()-1)==')' || t.charAt(t.length()-1)=='.' || t.charAt(t.length()-1)=='?' || t.charAt(t.length()-1)==',' || t.charAt(t.length()-1)==';' || t.charAt(t.length()-1)==':') t = t.substring(0, t.length()-1);
                                    
                 if(t.length()!=0) words.add(t);
            }
                                
        }
        return words;

    }
    
    
    public String FilterString()
    {
        //ArrayList<String> topicStrings = new ArrayList<String>();
        
        String s = str;
        String delimeters[] = {"<code>","</code>","<pre>","</pre>","<h[0-9]>","</h[0-9]>","<p>","</p>","<br>","<li>","</li>","<ul>","</ul>","</strong>","<strong>","<em>","</em>"};

        String text = "";

        for(int c=0;c<delimeters.length;c++)
        {
            String lines[] = s.split(delimeters[c]);
            text = "";
            for(int j=0;j<lines.length;j++)
            {
                text+=lines[j];
            }

            s = text;
        }
        
        /**
        while(true)
        {
            int fi = s.indexOf("<a", 0);
            if(fi!=-1)
            {
                int li = s.indexOf("</a>",0);
                
                if(li==-1) break;
                
                //String t = s.substring(fi, li+4);
                s = s.substring(0,fi) + s.substring(li+4);
            }
            else
            {
                break;
            }
                                
        }
        */
        
        
        
        return s;
    }
    
}
