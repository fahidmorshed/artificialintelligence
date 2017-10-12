/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dt_id3;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author dipon
 */
public class Node {
    int attrVal;
    String attrName;
    ArrayList <Node> children ;
    int classValue; 
    
    double gain;
    
    
    public Node ()
    {
        children = new ArrayList <Node>();
    }
    
    public Node(int attrVal,String attrName,int classValue, double gain)
    {
        this.attrName = attrName;
        this.attrVal = attrVal;
        this.classValue = classValue;
        this.gain = gain;
        
        children = new ArrayList <Node> ();
    }
    
    //getter & setter
    public int getAttrValue()
    {
        return this.attrVal;
    }
    
    public void setAttrValue(int attrVal)
    {
        this.attrVal = attrVal;
    }
    
    public String getAttrName()
    {
        return this.attrName;
    }
    
    public void setAttrName(String attrName)
    {
        this.attrName = attrName;
    }
    
    public double getGain()
    {
	return gain;
    }

    public void setGain(double gain) 
    {
	this.gain = gain;
    }
    
    public int getClassValue()
    {
        return this.classValue;
    }
    
    public void setClassValue(int classValue)
    {
        this.classValue = classValue;
    }
    
    public ArrayList<Node> getChildren()
    {
        return this.children;
    }
    
    public void setChildren(ArrayList<Node> children)
    {
        this.children = children;
    }
    
    public void printTree(Node T, int level) 
    {
		if (T.classValue== -1) {// not a leaf node
			for (Node temp : T.children) {// For all branches of this node
				int i = 0;
				while (i < level) {// According to level value we stdout |
					i++;
					System.out.print("| ");
				}
				System.out.print(T.attrName);
				System.out.print("=");
				if (temp.classValue == -1)
					System.out.print(temp.attrVal + " :\n");
				else
					System.out.print(temp.attrVal + " : ");
				printTree(temp, level + 1);// recursively call same function for
											// child node, with level+1
			}

		} else {// it is a leaf node , hence terminate
			System.out.println(T.classValue);
		}

    }

    public int findClass(HashMap <String, Integer> testCase, Node start)
    {
        int resultClass =-1;
        
        Node root = start;
        
        if (root == null) return resultClass;
        
        while(root!=null)
        {
            if(root.classValue!=-1)
            {
                resultClass = root.classValue;
                root = null;
                break;
            }
            else
            {
                String key = root.getAttrName();
                int value;
                if(testCase.containsKey(key))
                {
                    value = testCase.get(key);
                    if (root.children.size()==0)break;
                    int found =0;
                    for(Node child :root.children)
                    {
                        if(child.attrVal == value)
                        {
                            found=1;
                            if(child.classValue !=-1)
                            {
                                resultClass = child.classValue;
                                root = null;
                                break;
                            }
                            
                            else
                            {
                                root = child;
                                continue;
                            }
                        }
                    }
                    if (found==0)break;
                }
            }
        }
        
        
        
        return resultClass;
    }
    
}
