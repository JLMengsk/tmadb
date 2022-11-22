/**
 * record of a qPCR experiment
 */

package ca.ubc.gpec.tmadb

class Qpcr_experiments implements Comparable<Qpcr_experiments>, SecuredMethods {

    Surgical_blocks surgical_block;
    Date date;
    String experiment_sample_id;
    Institutions institution;
    String comment;
    SortedSet<Qpcr_results> qpcr_results
    
    static constraints = {
    }
    
    static hasMany = [ qpcr_results:Qpcr_results ]
    
    static mapping = {
        surgical_block column:'surgical_block_id'
        institution column:'institution_id'
    }
    
    
    /**
     * for Comparable interface
     * @param other
     * @return
     */
    public int compareTo(Qpcr_experiments other){
        return experiment_sample_id.compareTo(other.experiment_sample_id);
    }
    
    /**
     * override default to string
     */
    public String toString() {
        return experiment_sample_id+" ("+institution+")";
    }
    
    /**
     * check to see if this is available to the user
     * 
     * @param user
     * @return
     */
    public boolean isAvailable(Users user) {
        // admin can access everything
        if (user.login.equals(Users.ADMINISTRATOR_LOGIN)) {return true}
        
        return true; // TODO: remember to implement later!!!!
        
    }
}
