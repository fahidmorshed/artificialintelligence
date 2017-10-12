/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package textclassifier;

/**
 *
 * @author Black_Knight
 */
class IndexDistancePair implements Comparable<IndexDistancePair> {
    String index;
    double distance;

    public IndexDistancePair(String i,double d) {
        index = i;
        distance = d;
        
    }
    
    public void test()
    {
        index = "Hello";
        distance = 10.0;
        
        IndexDistancePair idp = new IndexDistancePair("Hi", 10.0);
        System.out.println(this.compareTo(idp));
    }
    
    public IndexDistancePair()
    {
        
    }

    @Override
    public int compareTo(IndexDistancePair o) {
        return Double.compare(this.distance, o.distance);
    }
    
    
    
}
