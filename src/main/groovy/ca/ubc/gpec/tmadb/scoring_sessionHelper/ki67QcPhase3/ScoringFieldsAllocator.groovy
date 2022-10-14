/*
 * specific to Ki67-QC phase 3 study ...
 * 
 * Allocate scoring fields based on user's estimate of percentage of Ki67 scores
 * across the whole slide
 */

package ca.ubc.gpec.tmadb.scoring_sessionHelper.ki67QcPhase3

/**
 * allocate scoring regions according to Mitch's algorithm (2014-02-13)
 *
 * For each category of staining for which there is non-zero representation on 
 * the full slide, allocate one field for counting. If any of the categories of 
 * staining is zero, less than 4 fields will have been allocated and the 
 * following should be adhered to complete the selection of fields: 
 * - one field left:
 *    - if there are ties between the three non-zero percentages 
 *      (e.g. 20% low, 40% medium, 40% high) do not allocate i.e. only pick and 
 *      score three fields.
 *    - otherwise allocate this field to the category with the highest percentage.
 * - two fields left
 *    - if the two non-zero percentages are similar i.e. absolute difference 
 *      between the two non-zero percentages is less then some threshold, TH 
 *      (e.g. 50%) allocate the remaining two fields to the two categories 
 *      e.g. 74% low, 26% medium -> 2 fields low, 2 fields medium
 *    - otherwise allocate the remaining two fields to the category with the 
 *      highest percentage e.g. 75% low, 25% medium -> 3 fields low, 1 field medium 
 * - three fields left. 
 *    - allocate all three fields to the single non-zero percentage category.

 *
 * @author samuelc
 */
class ScoringFieldsAllocator {
    public static final float THRESHOLD = 25;
    
    private float percent_negligible; // 0-100
    private float percent_low; // 0-100
    private float percent_medium; // 0-100
    private float percent_high; // 0-100
    
    private int numNegligible; // number of fields allocated to represent negligible ki67 score
    private int numLow; // number of fields allocated to represent low ki67 score
    private int numMedium; // number of fields allocated to represent medium ki67 score
    private int numHigh; // number of fields allocated to represent high ki67 score
    
    /**
     * return TRUE if they are all different
     * false if at least two of them are equal
     */
    private boolean allDifferent(float a, float b, float c) {
        return !(a==b || a==c || b==c);
    }
    
    /**
     * constructor
     * 
     * NOTE: assume sum of the percentages is 100
     * 
     * @param percent_negligible
     * @param percent_low
     * @param percent_medium
     * @param percent_high
     */
    public ScoringFieldsAllocator(float percent_negligible, float percent_low, float percent_medium, float percent_high) {
        this.percent_negligible = percent_negligible;
        this.percent_low = percent_low;
        this.percent_medium = percent_medium;
        this.percent_high = percent_high;
        
        // For each category of staining for which there is non-zero 
        // representation on the full slide, allocate one field for counting.
        numNegligible = percent_negligible == 0 ? 0 : 1;
        numLow        = percent_low        == 0 ? 0 : 1;
        numMedium     = percent_medium     == 0 ? 0 : 1;
        numHigh       = percent_high       == 0 ? 0 : 1;
        
        // NOTE: INITIAL required number of fields is 4.  The algorithm needs to be changed if the number is not 4.  
        int requiredNumFields = 4; // final required fields may be THREE.
        
        int numFieldsRemaining = requiredNumFields - numNegligible - numLow - numMedium - numHigh;
       
        // If any of the categories of staining is zero, less than 4 fields will 
        // have been allocated and the following should be adhered to complete 
        // the selection of fields: 
        if (numFieldsRemaining == 0) {
            return; // done! 
        } else if (numFieldsRemaining == 1) {
            // if one left ... 
            // if there are ties between the three non-zero percentages 
            //    (e.g. 20% low, 40% medium, 40% high) do not allocate 
            //    i.e. only pick and score three fields.
            // otherwise allocate this field to the category with the highest percentage.
            if (percent_high > [percent_medium, percent_low, percent_negligible].max() && allDifferent(percent_medium, percent_low, percent_negligible)) {
                numHigh++;
            } else if (percent_medium > [percent_high, percent_low, percent_negligible].max() && allDifferent(percent_high, percent_low, percent_negligible)) {
                numMedium++;
            } else if (percent_low > [percent_high, percent_medium, percent_negligible].max() && allDifferent(percent_high, percent_medium, percent_negligible)) {
                numLow++;
            } else if (percent_negligible > [percent_high, percent_medium, percent_low].max() && allDifferent(percent_high, percent_medium, percent_low)) {
                numNegligible++;
            } else {
                // there must be a tide ... only pick and score three fields. 
            }
        } else if (numFieldsRemaining == 2) {
            // if two left ...  
            // - if the two non-zero percentages are similar i.e. absolute 
            //   difference between the two non-zero percentages is less then 
            //   some threshold, TH (e.g. 50%) allocate the remaining two fields 
            //   to the two categories 
            //   e.g. 74% low, 26% medium -> 2 fields low, 2 fields medium
            numNegligible = numNegligible + (numNegligible > 0 ? 1 : 0);
            numLow        = numLow        + (numLow        > 0 ? 1 : 0);
            numMedium     = numMedium     + (numMedium     > 0 ? 1 : 0);
            numHigh       = numHigh       + (numHigh       > 0 ? 1 : 0);
            if ([
                    percent_high       == 0 ? 0 : Math.abs(percent_high       - [percent_medium, percent_low,    percent_negligible].max()),
                    percent_medium     == 0 ? 0 : Math.abs(percent_medium     - [percent_high,   percent_low,    percent_negligible].max()),
                    percent_low        == 0 ? 0 : Math.abs(percent_low        - [percent_high,   percent_medium, percent_negligible].max()),
                    percent_negligible == 0 ? 0 : Math.abs(percent_negligible - [percent_high,   percent_medium, percent_low       ].max())
                ].max() >= THRESHOLD) {
                // get rid of the one with the min percent ... there must be only one left hence "else if" statements
                if (percent_high !=0 && percent_high < [percent_medium, percent_low, percent_negligible].max()) {
                    numHigh--;
                    numMedium     = numMedium     + (numMedium     == 2 ? 1 : 0);
                    numLow        = numLow        + (numLow        == 2 ? 1 : 0);
                    numNegligible = numNegligible + (numNegligible == 2 ? 1 : 0);
                } else if (percent_medium != 0 && percent_medium < [percent_high, percent_low, percent_negligible].max()) {
                    numMedium--;
                    numHigh       = numHigh       + (numHigh       == 2 ? 1 : 0);
                    numLow        = numLow        + (numLow        == 2 ? 1 : 0);
                    numNegligible = numNegligible + (numNegligible == 2 ? 1 : 0);
                } else if (percent_low != 0 && percent_low < [percent_high, percent_medium, percent_negligible].max()) {
                    numLow--;
                    numHigh       = numHigh       + (numHigh       == 2 ? 1 : 0);
                    numMedium     = numMedium     + (numMedium     == 2 ? 1 : 0);
                    numNegligible = numNegligible + (numNegligible == 2 ? 1 : 0);
                } else {
                    numNegligible--;
                    numHigh   = numHigh   + (numHigh   == 2 ? 1 : 0);
                    numMedium = numMedium + (numMedium == 2 ? 1 : 0);
                    numLow    = numLow    + (numLow    == 2 ? 1 : 0);
                }
            }
        } else {
            // if three left, assign all three to same group!
            if (numNegligible > 0) {
                numNegligible = numNegligible + 3;
            } else if (numLow > 0) {
                numLow = numLow + 3;
            } else if (numMedium > 0) {
                numMedium = numMedium + 3;
            } else {
                numHigh = numHigh + 3;
            }
        }
    }
    
    /**
     * return number of negligible fields allocated
     */
    public int getNumNegligible() {
        return numNegligible;
    }
    
    /**
     * return number of low fields allocated
     */
    public int getNumLow() {
        return numLow;
    }
    
    /**
     * return number of medium fields allocated
     */
    public int getNumMedium() {
        return numMedium;
    }
    
    /**
     * return number of high fields allocated
     */
    public int getNumHigh() {
        return numHigh;
    }
    
    public int getNumNonHotspot() {
        return numHigh + numMedium + numLow + numNegligible;
    }
}

