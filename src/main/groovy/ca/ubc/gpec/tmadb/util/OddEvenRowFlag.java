/*
 * keeps track of odd/even row and return a flag that indicates
 * whether current row is odd/even
 */
package ca.ubc.gpec.tmadb.util;

/**
 *
 * @author samuelc
 */
public class OddEvenRowFlag {
    
    private String oddFlag;
    private String evenFlag;
    private int rowCounter;
    
    public OddEvenRowFlag(String oddFlag, String evenFlag) {
        this.oddFlag = oddFlag;
        this.evenFlag = evenFlag;
        rowCounter = 0;
    }
    
    public String showFlag() {
        String currFlag = (rowCounter % 2) == 0 ? evenFlag : oddFlag;
        rowCounter++;
        return currFlag;
    }
    
    
}
