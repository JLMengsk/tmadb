/*
 * Scoring (general) ... can be from TMA, whole section, core biopsy ...
 */
package ca.ubc.gpec.tmadb

import java.text.DecimalFormat
/**
 * DOES NOT IMPLMENT Compararble since this will always link 1-to-1 
 * to a higher level scoring object e.g. tma_scoring which implements
 * comparable using a more applicable logic
 * 
 * THIS OBJECT SHOULD ALWAYS BE ACCESSED VIA HIGHER LEVEL SCORING OBJECT
 * 
 * @author samuelc
 * - no need to implement Comparable since this table will always map 1-to-1
 *   with other scoring table e.g. tma_scoring, whole_section_scoring ...
 */
class Scorings implements SecuredMethods {

    Date scoring_date; // indicate when the scoring is DONE
    String score;
    Integer visit_order;
    SortedSet<Nuclei_selections> nuclei_selections;
    SortedSet<Scoring_nuclei_selection_notifications> scoring_nuclei_selection_notifications;
    SortedSet<Scoring_logs> scoring_logs;
    String type; // the type of scoring, null => free text
    String comment; // any commments regarding this image the score may want to record
    Tma_scorings tma_scoring;
    Whole_section_scorings whole_section_scoring;
    Whole_section_region_scorings whole_section_region_scoring;
    
    private Integer maxSelect_order=null;
    
    static constraints = {
        scoring_date (nullable: true)
        score (nullable: true)
        visit_order (nullable: true)
        type (nullable: true)
        comment (nullable: true)
        tma_scoring (nullable: true)
        whole_section_scoring (nullable: true)
        whole_section_region_scoring (nullable: true)
    }
	
    static mapping = {
        nuclei_selections cascade:'all-delete-orphan'
        scoring_nuclei_selection_notifications cascade:'all-delete-orphan'
    }
    
    static hasOne = [ tma_scoring:Tma_scorings, whole_section_scoring:Whole_section_scorings, whole_section_region_scoring:Whole_section_region_scorings ]
    
    static hasMany = [nuclei_selections:Nuclei_selections, scoring_nuclei_selection_notifications:Scoring_nuclei_selection_notifications, scoring_logs:Scoring_logs]
       
    /**
     * check to see if this is available to the user
     *
     * @param user
     * @return
     */
    public boolean isAvailable(Users user) {
        // if scoring_session is available, tma_scoring is available
        return scoring_session.isAvailable(user);
    }
        
    /**
     * reset score
     * return false if something went wrong :(
     **/
    public boolean resetScore() {
        scoring_date = null;
        score = null;
        visit_order = null;
        boolean status = true;
        if (nuclei_selections != null) {
            Nuclei_selections.findAllByTma_scoring(this).each {
                this.removeFromNuclei_selections(it);
            }
        }
        return status && this.save(flush: true);
    }
    
    /**
     * remove last selected nuclei 
     * return true/false if remove successful/fail
     **/
    public boolean removeLastNucleiSelection() {
        if (nuclei_selections.size()==0) {
            // no need to do anything
            return true;
        }
        Nuclei_selections lastNuclei_selection = nuclei_selections.last();
        nuclei_selections.remove(lastNuclei_selection);
        return lastNuclei_selection.delete(flush: true);
    }
    
    /**
     * remove the last n nuclei selection
     **/
    public boolean removeNucleiSelection(int n) {
        boolean status = true;
        for (int i=0; i<n; i++) {
            Nuclei_selections lastNuclei_selection = nuclei_selections.last();
            this.removeFromNuclei_selections(lastNuclei_selection);
            nuclei_selections.remove(lastNuclei_selection);
        }
        return status && this.save(flush: true);
    }
    
    /**
     * remove all nuclei selection
     * - for synchronizing btw applet and server
     * return false if something went wrong :(
     **/
    public boolean removeAllNucleiSelection() {
        boolean status = true;
        if (nuclei_selections != null) {
            Nuclei_selections.findAllByTma_scoring(this).each {
                this.removeFromNuclei_selections(it);
            }
        }
        return status && this.save(flush: true);
    }
    
    
    /**
     * return number of positive nuclei selected
     **/
    private int countPositiveNucleiSelected() {
        if (nuclei_selections == null) {return 0;}
        int count=0;
        nuclei_selections.each {
            if (it.showIsPositive()) {
                count++;
            }
        }
        return count;
    }
    
    /**
     * return total number of nuclei selected
     **/
    public int countTotalNucleiSelected() {
        if (nuclei_selections == null) {return 0;}
        return nuclei_selections.size();
    }
        
    /**
     * parameter string for telling the applet which nuclei are selected
     * 
     * format:
     * [x-coordinate]x[y-coordinate]y[optional time in milliseconds][n/p]
     * separator = "_"
     * e.g. 12x45yp_98x65yp
     *      12x45y1003000p_98x65y1004000p
     *      12x45y1003000p_98x65y-91000p   (using compressed time format)
     * 
     * NOTE: time format
     *       - time captured in seconds only since MySQL database stores time 
     *         only down to seconds (instead of milliseconds)
     *       - time of first nuclei ALWAYS absolute time i.e. # of seconds from epic (1970-01-01)
     *       - time of all nuclei after the first one would be # of seconds from previous nuclei
     * 
     * there are ONLY THREE types of nuclei selection
     * 
     * 1. coordinates without time
     * e.g. 12x34yp
     * 
     * 2. coordinates with time
     * e.g. 12x34y56p
     * 
     * 3. time only (i.e. no coordinates)
     * e.g. 123p
     * 
     * 
     * more than one set of nuclei selection ...
     * COMPARE_[x-coordinate]x[y-coordinate]y[optional time in milliseconds][n/p] ... _BEGIN_[x-coordinate]x[y-coordinate]y[optional time in milliseconds][n/p] ...
     * e.g. COMPARE_12x45yp_98x65yp_BEGIN_13x42yp_102x69yp
     *      COMPARE_12x45y1003000p_98x65y1004000p_BEGIN_13x42y1003000p_102x69y1004000p
     * 
     * 
     */
    public String showNucleiSelectionParamString() {
        String result = "";
        boolean coordinatesAvailable = false;
        boolean coordinatesChecked = false;
        Long timeOfPreviousNuclei = null;
        
        nuclei_selections.each {
            if (!coordinatesChecked) {
                // check if coordinates available
                coordinatesAvailable = (it.getX() != null);
                coordinatesChecked = true;
            }
            // calculate the time
            Long time = it.showTimeInMilliseconds();
            if (timeOfPreviousNuclei == null) {
                // this is either the first nuclei or all nuclei do not have time set
                timeOfPreviousNuclei = (time == null) ? null : time / 1000; // time stored in seconds
                time = time / 1000;
            } else {
                time = time / 1000; // Assume that time is either available for ALL or none of the nuclei
                long timeDiff = time - timeOfPreviousNuclei;
                timeOfPreviousNuclei = time;
                time = timeDiff;  
            }
            result = result+((coordinatesAvailable) ? it.getX()+Nuclei_selections.X_LABEL+it.getY()+Nuclei_selections.Y_LABEL : "")+((time==null)?"":time)+it.state+Nuclei_selections.DELIMITER;
        }
        if (result.length()>0) {
            // need to get rid of the last delimiter
            result = result.substring(0,result.length()-Nuclei_selections.DELIMITER.length());
        }
        return result;
    }
    
    /**
     * check to see if this is linked to a tma_scoring record
     */
    public boolean showIsTma_scoring(){
        return tma_scoring != null;
    }
    
    /**
     * check to see if this is linked to a whole_section_scoring record
     */
    public boolean showIsWhole_section_scoring() {
        return whole_section_scoring != null;
    }
    
    /**
     * check to see if this is linked to a whole_section_region_scoring record
     */
    public boolean showIsWhole_section_region_scoring() {
        return whole_section_region_scoring != null;
    }
    
    /**
     * check to see if scoring type is free text
     * type==null => free text
     **/
    public boolean showIsScoringTypeFreeText() {
        return type==null;
    }
    
    public boolean showIsScoringTypeNucleiCount() {
        if (type==null) {
            return false
        } else if (type.equals(GenericScorings.SCORING_TYPE_NUCLEI_COUNT)) {
            return true
        }
        return false; // other types
    }
    
    public boolean showIsScoringTypeNucleiCountNoCoord() {
        if (type==null) {
            return false;
        } else if (type.equals(GenericScorings.SCORING_TYPE_NUCLEI_COUNT_NO_COORD)) {
            return true;
        }
        return false; // other types
    }
    
    public boolean showIsScoringTypeKi67Phase3() {
        if (type==null) {
            return false;
        } else if (type.equals(GenericScorings.SCORING_TYPE_KI67_QC_PHASE3)) {
            return true;
        }
        return false; // other types        
    }
       
    /**
     * return true if there are any nuclei selection notification linked
     * to this tma_scoring
     */
    public boolean showHasNucleiSelectionNotifications() {
        return scoring_nuclei_selection_notifications.size() > 0;
    }
    
    /**
     * return the next select order
     * ALSO INCREMENT maxSelect_order
     **/
    public int showNextSelect_order() {
        if (maxSelect_order == null) {
            // need to iterate nuclei_selection and find max select_order;
            maxSelect_order = -1;
            nuclei_selections.each {
                if (it.select_order > maxSelect_order) {
                    maxSelect_order = it.select_order
                }
            }
        }
        maxSelect_order = maxSelect_order + 1;
        return maxSelect_order;        
    }
       
    /**
     * print out nice format of score type
     */ 
    public String showScoreType() {
        if (type==null) {
            return "free-text";
        } else if (type.equals(GenericScorings.SCORING_TYPE_NUCLEI_COUNT)) {
            return "nuclei count";
        } else if (type.equals(GenericScorings.SCORING_TYPE_NUCLEI_COUNT_NO_COORD)) {
            return "nuclei count from glass"
        } else if (type.equals(GenericScorings.SCORING_TYPE_KI67_QC_PHASE3)) {
            return "Ki67-QC phase 3"
        } else {
            return "unknown";
        }
    }
    
    /**
     * print out nice format of score
     **/
    public String showScore() {
        if (type==null) {
            return score;
        } else if (type.equals(GenericScorings.SCORING_TYPE_NUCLEI_COUNT) | type.equals(GenericScorings.SCORING_TYPE_NUCLEI_COUNT_NO_COORD)) {
            // nuclei count format: [# positive](+ve), [# negative](-ve), [# total](total) 
            // 12(+ve), 24(-ve), 36(total), 33(%+ve), 5.04(log2 %+ve)
            int numPos = countPositiveNucleiSelected();
            int numTotal = countTotalNucleiSelected();
            double percentPositiveDouble = 0d;
            int percentPositiveRounded = 0;
            String log2PercentPositiveRound = "NA"
            if (numTotal > 0) {
                percentPositiveDouble = ((double)numPos)/((double)numTotal)*100;
                percentPositiveRounded = Math.round(percentPositiveDouble);
                if (percentPositiveDouble > 0) {
                    log2PercentPositiveRound = (new DecimalFormat("0.00")).format(Math.log(percentPositiveDouble)/Math.log(2));
                } else {
                    log2PercentPositiveRound = "-Inf"
                }
            }
            return numPos+"(+ve), "+(numTotal-numPos)+"(-ve), "+numTotal+"(total), "+percentPositiveRounded+"(%+ve), "+log2PercentPositiveRound+"(log2 %+ve)";
        } else if (type.equals(GenericScorings.SCORING_TYPE_KI67_QC_PHASE3)) {
            // go through the 5 fields and print out scores and Ki67 level for each fields
            String result = "%H/M/L/N:"+whole_section_scoring.getPercent_high()+"/"+whole_section_scoring.getPercent_medium()+"/"+whole_section_scoring.getPercent_low()+"/"+whole_section_scoring.getPercent_negligible()+";";
            result = result + "###" + comment + "###"
            whole_section_scoring.getWhole_section_region_scorings().each { wsrs ->
                result = result + wsrs.getIhc_score_category().getName()+ "[" + wsrs.toString() + "]:" + wsrs.showScore()+"; "
            }
            return result.substring(0,result.length()-2); // get rid of last "; "
        }
    }
    
    /**
     * print out nice format of score in short format
     **/
    public String showScoreShort() {
        if (type==null) {
            return score;
        } else if (type.equals(GenericScorings.SCORING_TYPE_NUCLEI_COUNT) | type.equals(GenericScorings.SCORING_TYPE_NUCLEI_COUNT_NO_COORD)) {
            // nuclei count format: [# positive](+ve), [# negative](-ve), [# total](total) 
            // 12(+ve), 24(-ve), 36(total), 33(%+ve), 5.04(log2 %+ve)
            int numPos = countPositiveNucleiSelected();
            int numTotal = countTotalNucleiSelected();
            double percentPositiveDouble = 0d;
            int percentPositiveRounded = 0;
            String log2PercentPositiveRound = "NA"
            if (numTotal > 0) {
                percentPositiveDouble = ((double)numPos)/((double)numTotal)*100;
                percentPositiveRounded = Math.round(percentPositiveDouble);
                if (percentPositiveDouble > 0) {
                    log2PercentPositiveRound = (new DecimalFormat("0.00")).format(Math.log(percentPositiveDouble)/Math.log(2));
                } else {
                    log2PercentPositiveRound = "-Inf"
                }
            }
            return numPos+"/"+numTotal+"("+percentPositiveRounded+"%+ve,log2="+log2PercentPositiveRound+")";
        } else if (type.equals(GenericScorings.SCORING_TYPE_KI67_QC_PHASE3)) {
            // go through the 5 fields and print out scores and Ki67 level for each fields
            String result = "";
            whole_section_scoring.getWhole_section_region_scorings().each { wsrs ->
                // no breaks will fource dojo to display whole item in single line in table
                result = result + wsrs.getIhc_score_category().getName().toUpperCase() + ":" + wsrs.showScoreShort()+"; "
            }
            return result.substring(0,result.length()-2); // get rid of last "; "
        }
    }
    
    /**
     * show percent positive (0-1)
     * return -1 if not applicable e.g. not nuclei count
     **/
    public double showPercentPositive() {
        if (type==null) {
            return -1;
        }
        if (type.equals(GenericScorings.SCORING_TYPE_NUCLEI_COUNT) | type.equals(GenericScorings.SCORING_TYPE_NUCLEI_COUNT_NO_COORD)) {
            int numPos = countPositiveNucleiSelected();
            int numTotal = countTotalNucleiSelected();
            return ((double)numPos)/((double)numTotal);
        } else {
            return -1;
        }
    }
        
    /**
     * check to see if it is still allowed to score this image
     * e.g. if submitted - do not allow
     **/
    public boolean showIsAllowedToUpdateScore() {
        if (tma_scoring != null) {
            return !tma_scoring.getScoring_session().showSubmitted();
        } else if (whole_section_scoring != null) {
            return !whole_section_scoring.getScoring_session().showSubmitted();
        } else if (whole_section_region_scoring != null) {
            return !whole_section_region_scoring.getScoring().showSubmitted();
        } else {
            return false; // no scoring_session!!!
        }
    }
}

