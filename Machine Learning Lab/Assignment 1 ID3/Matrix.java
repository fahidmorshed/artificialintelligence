/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dt_id3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.StringTokenizer;

/**
 *
 * @author dipon
 */
public class Matrix {
    
    int columns;
    ArrayList <String> attributes;
    ArrayList <int []>rows;
    ArrayList <int []>testRows;
    int numRows;
    private static Random random = new Random(System.currentTimeMillis());
    public Matrix()
    {
        attributes = new ArrayList <String>(); 
        rows = new ArrayList <int[]>();
        testRows = new ArrayList <int[]>();
    }
    
    public ArrayList<String> getAttributes() {
	return attributes;
    }

    public void setAttributes(ArrayList<String> Attributes) {
	this.attributes= Attributes;
    }

    public ArrayList<int[]> getRows() {
	return rows;
    }

    public void setRows(ArrayList<int[]> rows) {
	this.rows = rows;
    }
    
    public void arrayFill(int[]array, int index)           
    {
        int tempIndex =0;
        for(int[]temRow:rows)
        {
            array[tempIndex++]=temRow[index];
        }
    }
    
    public void createMat(String file) throws FileNotFoundException, IOException
    {
        BufferedReader br = null;
        
        br = new BufferedReader(new FileReader(file));
        //reading attribute name
        String read = br.readLine();
        StringTokenizer col= new StringTokenizer (read,",");
        while(col.hasMoreElements())
        {
            attributes.add((String)col.nextElement());
        }
                
        /* System.out.println("Lets Check Headers"); 
         for(String temp:attributes){
            System.out.println("\t "+temp);
         }*/
        columns = attributes.size();
        
        String line= br.readLine();
        while(line!= null)
        {
            StringTokenizer info = new StringTokenizer(line,",");
            int indexTemp =0;
            int []temp = new int[columns];
            while(info.hasMoreElements())
            {
                temp[indexTemp++]=Integer.parseInt((String)info.nextElement());
            }
            rows.add(temp);
            line= br.readLine();
        }
        
        int rowsToTest = rows.size()*20/100;
        int rowsToTake = rows.size()-200;
        int testIndex = random.nextInt(rowsToTake);
        System.out.println(testIndex);
        
        for(int i=testIndex;i<(testIndex+100);i++)
        {
            int []temp = rows.get(i);
            testRows.add(temp);
            rows.remove(i);
        }
        //System.out.println(rows.size());
        //System.out.println(testRows.size());
    }
    
    
    public void printMatrix() {
	for (String tempMatrix : attributes) {
            System.out.print("\t" + tempMatrix);
	}
	for (int[] tempRows : rows) {
            System.out.println("");
            for (int i = 0; i < columns; i++) {
		System.out.print("\t" + tempRows[i]);
            }
	}
	System.out.println("");
    }
    
    
    public Matrix splitMat(String attrMax, int attrvalue)
    {
        Matrix result = new Matrix();
        ArrayList <String> attr = new ArrayList<String>();
        ArrayList <int[]> resultRows = new ArrayList<int[]>();
        
        for(String temp:this.attributes)
        {
            if(!temp.equals(attrMax))
                attr.add(temp);
        }
        result.setAttributes(attr);
        result.columns = attr.size();
        int attrIndex;
        for(attrIndex=0;attrIndex<attributes.size();attrIndex++)
        {
            if(attributes.get(attrIndex).equals(attrMax))
                break;
        }
        
        // creating rows
        for(int[]temp:this.rows)
        {
            if(temp[attrIndex]==attrvalue)
            {
                int[]temprows = new int[result.columns];
                int index =0;
                for(int i=0;i<this.columns;i++ )
                {
                    if (i!=attrIndex)
                    {
                        temprows[index++]=temp[i];
                    }
                }
                resultRows.add(temprows);
            }
        }
        result.setRows(resultRows);
        result.numRows = resultRows.size();
        return result;
    }  
    
    public Matrix splitMat2(String attrMax, int attrvalue)
    {
        Matrix result = new Matrix();
        ArrayList <String> attr = new ArrayList<String>();
        ArrayList <int[]> resultRows = new ArrayList<int[]>();
        
        for(String temp:this.attributes)
        {
            if(!temp.equals(attrMax))
                attr.add(temp);
        }
        result.setAttributes(attr);
        result.columns = attr.size();
        int attrIndex;
        for(attrIndex=0;attrIndex<attributes.size();attrIndex++)
        {
            if(attributes.get(attrIndex).equals(attrMax))
                break;
        }
        
        // creating rows
        for(int[]temp:this.rows)
        {
            if(temp[attrIndex]==attrvalue)
            {
                int[]temprows = new int[result.columns];
                int index =0;
                for(int i=0;i<this.columns;i++ )
                {
                    if (i!=attrIndex)
                    {
                        temprows[index++]=temp[i];
                    }
                }
                resultRows.add(temprows);
            }
        }
        result.setRows(resultRows);
        result.numRows = resultRows.size();
        return result;
    }  
    
    
    public Result getTest(Node root)
    {
        int tp=0;
        int fp=0;
        int tn=0;
        int fn=0;
        for(int []test:testRows)
        {
            HashMap<String, Integer> testCase = new HashMap<String,Integer>();
            int finalClass =-1;
            for(int i=0;i<attributes.size();i++)
            {
                if(i==attributes.size()-1)
                    finalClass = test[i];
                else
                    testCase.put((String)attributes.get(i), test[i]);
            }
            int decidedClass = root.findClass(testCase, root);
            if(finalClass == 0 && decidedClass==0)
                tn++;
            else  if(finalClass == 0 && decidedClass==1)
                fp++;
            else  if(finalClass == 1 && decidedClass==0)
                fn++;
            else  if(finalClass == 1 && decidedClass==1)
                tp++;
        }
        int recall = 100*tp/(tp+fn);
        int precission = 100*tp/(tp+fp);
        int accuracy = 100*(tp+tn)/(tp+tn+fp+fn);
        
        Result result = new Result();
        result.accuracy = accuracy;
        result.precission = precission;
        result.recall = recall;
        
        
        //System.out.println("TP: "+ tp);
        //System.out.println("recall: "+ recall+" precission: "+precission+" accuracy: "+accuracy);
        return result;
    }
    
}
