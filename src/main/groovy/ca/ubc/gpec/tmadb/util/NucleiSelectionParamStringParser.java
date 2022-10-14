package ca.ubc.gpec.tmadb.util;

import java.util.ArrayList;
import java.util.Date;

/*
 * the initial Nuclei selection as a parameter string passed to the applet
 *
 * [# of nuclei to remove]_[x-coordinate]x[y-coordinate]y[n/p] separator = "_"
 * e.g. 56_12x45yp_98x65yp OR with time parameter ... [# of nuclei to
 * remove]_[x-coordinate]x[y-coordinate]y[time in milliseconds][n/p] separator =
 * "_" e.g. 56_12x45y1004000p_98x65y1005000p e.g.
 * 56_12x45y1004000p_98x65y-91000p
 *
 *  * NOTE: compressed time format: '-9xxx' = xxx milliseconds from first time
 * e.g. '-9123' = 123 millisecons from time of first nuclei update therefore,
 * the time of the first nuclei update must NOT be compressed
 *
 * more than one set of nuclei selection ...
 * COMPARE_[x-coordinate]x[y-coordinate]y[optional time in milliseconds][n/p]
 * ... _BEGIN_[x-coordinate]x[y-coordinate]y[optional time in milliseconds][n/p]
 * ... e.g. COMPARE_12x45yp_98x65yp_BEGIN_13x42yp_102x69yp
 * COMPARE_12x45y1003000p_98x65y1004000p_BEGIN_13x42y1003000p_102x69y1004000p
 *
 */
public class NucleiSelectionParamStringParser {

    static final String X_LABEL = "x";
    static final String Y_LABEL = "y";
    static final String DELIMITER = "_";
    static final String POSITIVE_LABEL = "p";
    static final String NEGATIVE_LABEL = "n";
    private String nucleiSelectionParamString;
    private ArrayList<Integer> xArr;
    private ArrayList<Integer> yArr;
    private ArrayList<String> stateArr;
    private ArrayList<Long> timeArr;
    private int numNucleiToRemove;

    public NucleiSelectionParamStringParser(String nucleiSelectionParamString) {
        this.nucleiSelectionParamString = nucleiSelectionParamString;

        xArr = new ArrayList<Integer>();
        yArr = new ArrayList<Integer>();
        stateArr = new ArrayList<String>();
        timeArr = new ArrayList<Long>();

        parse();
    }

    /**
     * parse the String and return a NucleiSelection object
     *
     * format: [x-coordinate]x[y-coordinate]y[optional time in
     * milliseconds][n/p] separator = "_" e.g. 12x45yp_98x65yp
     * 12x45y1003000p_98x65y1004000p 12x45y1003000p_98x65y-91000p (using
     * compressed time format)
     *
     * NOTE: time format - time captured in seconds only since MySQL database
     * stores time only down to seconds (instead of milliseconds) - time of
     * first nuclei ALWAYS absolute time i.e. # of seconds from epic
     * (1970-01-01) - time of all nuclei after the first one would be # of
     * seconds from previous nuclei
     *
     * there are ONLY THREE types of nuclei selection
     *
     * 1. coordinates without time e.g. 12x34yp
     *
     * 2. coordinates with time e.g. 12x34y56p
     *
     * 3. time only (i.e. no coordinates) e.g. 123p
     *
     *
     * @return
     */
    private void parse() {

        String[] paramStringArr = nucleiSelectionParamString.split(DELIMITER);

        // find out the number of nuclei to delete
        numNucleiToRemove = Integer.parseInt(paramStringArr[0]);

        Long timeOfPreviousNuclei = null; // keep track of time of previous nuclei
        for (int i = 1; i < paramStringArr.length; i++) {

            Integer x = null;
            Integer y = null;
            Long time = null;

            String paramString = paramStringArr[i];

            if (paramString.contains(X_LABEL)) {
                // must have coordinates
                // format either:
                // 12x45yp_98x65yp OR 12x45y1003000p_98x65y10p 
                String[] arr = paramString.split(X_LABEL);
                x = Integer.parseInt(arr[0]);
                String[] arr2 = arr[1].split(Y_LABEL);
                y = Integer.parseInt(arr2[0]);
                time = (arr2[1].length() > 1) ? Long.parseLong(arr2[1].substring(0, arr2[1].length() - 1)) * 1000 : null; // since time in Nuclei is in milliseconds
            } else {
                // no coordinates - MUST have time ... currently do not support no time AND no coordinates
                // format: 123p
                time = Long.parseLong(paramString.substring(0, paramString.length() - 1)) * 1000; // since time in Nuclei is in milliseconds
            }

            // need to figure out if time is absolute time or time from previous nuclei
            if (timeOfPreviousNuclei == null) {
                // must be either:
                // - first nuclei OR
                // - time not set for ALL nuclei
                timeOfPreviousNuclei = time;
            } else {
                // must have seen the first nuclei, the current nuclei is the
                // time difference from previous nuclei
                time = timeOfPreviousNuclei + time;
                timeOfPreviousNuclei = time;
            }

            xArr.add(x); // add x
            yArr.add(y); // add y
            timeArr.add(time); // add time

            if (paramString.endsWith(POSITIVE_LABEL)) {
                stateArr.add(POSITIVE_LABEL);
            } else {
                // if not positive, assume negative
                stateArr.add(NEGATIVE_LABEL);
            }
        }

    }

    public int showSize() {
        return xArr.size();
    }

    public Integer showX(int index) {
        return xArr.get(index);
    }

    public Integer showY(int index) {
        return yArr.get(index);
    }

    public String showState(int index) {
        return stateArr.get(index);
    }

    public Long showTimeInMilliseconds(int index) {
        return timeArr.get(index);
    }

    /**
     * show time as a Date object, returns null if time is not set
     *
     * @param index
     * @return
     */
    public Date showTimeInDate(int index) {
        Long timeInMilliseconds = showTimeInMilliseconds(index);
        return (timeInMilliseconds == null) ? null : new Date(timeInMilliseconds);
    }

    public int showNumNucleiToRemove() {
        return numNucleiToRemove;
    }

    /**
     * process test ...
     *
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("testing NucleiSelectionParamStringParser ...");


    }
}
