package ca.ubc.gpec.tmadb.download.tma.model;

import java.util.ArrayList;

/**
 * implements value/score(s) of a case
 * 
 * NOTE: value set null to empty string
 * 
 * sometimes, a case may have > 1 scores (e.g. duplicate core TMA) would have
 * 2 scores per case (each case represented by 2 cores)
 * 
 * @author samuelc
 *
 */
public class CaseValues {

    ArrayList<String> values;
	
    /**
     * constructor
     */
    public CaseValues() {
        values = new ArrayList<String>();
    }
	
    /** 
     * add value
     * @param value
     */
    public void addValue(String value) {
        // NOTE: set null to empty string
        values.add(value==null?"":value);
    }
	
    /**
     * get number of values associated with this case
     */
    public int getNumberOfValues() {
        return values.size();
    }
	
    /**
     * get value at particular index
     * @param index
     * @return
     * @throws DataFrameReadException
     */
    public String getValue(int index) throws DataFrameReadException {
        if (index >= values.size()) {
            throw new DataFrameReadException("attempt to get value from unavailable index: "+index);
        }
        return values.get(index);
    }
	
    public ArrayList<String> getValues() {
        return values;
    }
	
    /**
     * override default equals
     * @param obj to test
     * @return equal
     */
    public boolean equals(Object obj) {
        if (this == obj) {return true;}
        if (!(obj instanceof CaseValues)) {return false;}
        ArrayList<String> objValues = ((CaseValues)obj).values;
        int valuesSize = this.values.size();
        if (valuesSize != objValues.size()) {
            return false;
        }
        for (int i=0; i<valuesSize; i++) {
            if (!this.values.get(i).equals(objValues.get(i))) {return false;}
        }
        return true;
    }
	
    /**
     * override default toString
     */
    public String toString() {
        String result = "{";
        for (String value : values) {
            result = result + value +", ";
        }
        if (result.length() > 1) {
            result = result.substring(0,result.length()-2);
        } 
        result = result+"}";
        return result;
    }
	
    /**
     * main method for process test ...
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("testing ...");
		
        CaseValues cv1 = new CaseValues();
        CaseValues cv2 = new CaseValues();
		
        cv1.addValue("1");
        cv2.addValue("2");
		
        System.out.println(""+cv1.equals(cv2));
    }
}
