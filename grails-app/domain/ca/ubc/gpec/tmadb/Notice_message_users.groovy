package ca.ubc.gpec.tmadb

/**
 * link between notice_message to users
 */
class Notice_message_users {

    Users user;
    Notice_messages notice_message;
    
    static constraints = {
    }
    
    static mapping = {
        user column:'user_id'
        notice_message column:'notice_message_id'
    }
}
