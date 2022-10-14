package ca.ubc.gpec.tmadb
import ca.ubc.gpec.tmadb.util.ViewConstants;
import ca.ubc.gpec.tmadb.util.MiscUtil;

class LoginTagLib {
    /**
     * show create account link
     * - link will call makeSureOkToGoToCreateAccountPage first 
     * 
     * @attr label - text to show in link
     */
    def createAccountLink = { attr, body ->
        String label = attr['label'];
        out << "<a href='' title='New user? Please click me to create an user account.' onclick=\"";
        out << " makeSureOkToGoToCreateAccountPage('"+createLink(base: grailsApplication.config.grails.serverSecureURL, controller:"users", action:"create_account_showPageBodyOnly")+"'); return false;"
        out << "\">"+label+"</a>"        
    }
    
    /**
     * show sign in link
     */
    def signInLink = {
        out << "" + createLink(base: grailsApplication.config.grails.serverSecureURL, action:"login_showPageBodyOnly", controller:"users", params:MiscUtil.hideCurrentParams(params));
    }
    
    def loginControl = {
        if(session.user){
            out << "Hello, ${session.user.name} "
        } else {
            out << "<a href='"
            out << g.signInLink();
            out << "' title='Please sign in for more available actions e.g. view more slides, participate in tests'>";
            out << "<img src=\""+resource(dir:'images',file:'sign_in.jpg')+"\" ";
            out << "onmouseover=\"this.src='"+resource(dir:'images',file:'sign_in_glow.jpg')+"'\" "; 
            out << "onmouseout=\"this.src='"+resource(dir:'images',file:'sign_in.jpg')+"'\" "; 
            out << "border=\"0\" "; 
            out << "/>";
            out << "</a>";
            out << "<br>";
            out << "New user? ";
            out << g.createAccountLink(label:"Create account.");
            out << "<br>";
        }
    }
}
