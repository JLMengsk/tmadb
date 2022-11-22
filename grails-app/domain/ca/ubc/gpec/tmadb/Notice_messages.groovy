package ca.ubc.gpec.tmadb

/**
 * notice messages for user or everybody
 */
class Notice_messages {
    
    Date start_time;
    Date end_time;
    String message;
    
    static constraints = {
    }
    
    static mapping = {
        notice_message_user column:'notice_message_user_id'
    }
    
    /**
     * override default to string
     * - display message if curr time within start/end_time OR if if end_time before start_time
     * - return null if no message to display
     * 
     * NOTE: end_time before start_time indicates always display message
     */
    public String showMessage() {
        if (Calendar.getInstance().getTime().after(start_time) && Calendar.getInstance().getTime().before(end_time)) {
            return message;
        } else if (end_time.before(start_time)){
            return message;
        } else {
            return null;
        }
    }
}
