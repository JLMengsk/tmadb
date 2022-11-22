package ca.ubc.gpec.tmadb
/**
 * check and show flash message
 */
class FlashMessageTagLib {
    
    def showFlashMessage = { 
        if (flash.message != null) {
            out << "<div class='message'>";
            out << flash.message;
            out << "</div>";
        }
        // get user
        if (false) { // the following have problem ... need fo fix!!!!! ... disable for now
            if (session != null) {
                Users user = session.getAttribute("user");
                if (user != null) {
                    user.refresh();
                    Notice_message_users.findAllByUser(user).each {
                        Notice_message_users notice_message_user = Notice_message_users.get(it.id);
                        // having problem!!! TODO: need to fix!!!!
                        if (message != null) {
                            out << "<div class='message'>";
                            out << notice_message_user.notice_message.id;
                            out << "</div>";
                        }
                    }
                }
            }
        }
    }
}