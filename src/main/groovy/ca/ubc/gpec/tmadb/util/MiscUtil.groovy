package ca.ubc.gpec.tmadb.util

import java.text.SimpleDateFormat

import ca.ubc.gpec.tmadb.DisplayConstant;
import ca.ubc.gpec.tmadb.Users
import javax.servlet.http.HttpSession


/**
 * some misc util functions ...
 */
class MiscUtil {		
    public static final String HIDDEN_KEY_PREFIX = "_HIDDEN_CURRENT_"
    public static final String HIDDEN_FLAG_PERMISSION_DENIED = "_HIDDEN_FLAG_PERMISSION_DENIED";	
    
    // TODO - get default date format from i18n/messages.properties
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(DisplayConstant.DATE_FORMAT)
    
    /**
     * calculate log base 2
     **/
    public static double log2(double x) {
        return Math.log(x)/Math.log(2d);
    }
    
    /**
     * calculate square
     **/
    public static double square(double x) {
        return x*x;
    }
    
    /**
     * check to see if pageBody need fixed position ...
     * e.g. fixed position needed for any page with data grid scrollable table
     */
    public static boolean needFixedPositionForPageBody(String controllerName, String actionName) {
        return (controllerName=="scoreTma" && actionName in ["auditScores","list"]) || 
        (controllerName=="tma_projects" && actionName=="list") || 
        (controllerName=="tma_projects" && actionName=="show") || 
        (controllerName=="tma_arrays" && actionName=="show") || 
        (controllerName=="tma_slices" && actionName=="list") || 
        (controllerName=="whole_section_slices" && actionName=="list") || 
        (controllerName=="biomarkers" && actionName=="show") || 
        (controllerName=="scoring_sessions" && actionName=="report");
    }
        
    /**
     * need to use javascript to move footer to bottom of browser window
     */
    public static boolean needRepositionFooter(String controllerName, String actionName) {
        return (controllerName in [null,"tma_projects","tma_arrays"]);
    }
    /**
     * format date, returns null if inputDate is null
     * @param inputDate
     * @return
     */
    public static String formatDate(Date inputDate) {
        if (inputDate == null) {return null}
        return SIMPLE_DATE_FORMAT.format(inputDate)
    }
	
    /**
     * hide current params by modifying keys by added _HIDDEN_CURRENT_ in front
     * e.g. action => _HIDDEN_CURRENT_action
     * @param inputParams
     * @return
     */
    public static Map hideCurrentParams(Map inputParams) {
        HashMap currentParams = new HashMap();
        inputParams.each {
            currentParams.put(HIDDEN_KEY_PREFIX+it.key, it.value)
        }
        return currentParams
    }
	
    /**
     * unhide current params by modifying keys by removing _HIDDEN_CURRENT_ in front
     * e.g. _HIDDEN_CURRENT_action => action
     * @param inputParams
     * @return
     * @throws UnhideCurrentParamException if something goes wrong
     */
    public static Map unhideCurrentParams(Map inputParams) {
        HashMap currentParams = new HashMap();
        int beginIndex = HIDDEN_KEY_PREFIX.length();
        inputParams.each {
            if (it.key.indexOf(HIDDEN_KEY_PREFIX) == 0) {
                // skip ALL params with no HIDDEN_KEY_PREFIX ... these are params  
                // we want to get rid of (e.g. params from login commands)
                currentParams.put(it.key.substring(beginIndex), it.value)
            }
        }
        return currentParams
    }
	
    /**
     * returns hidden params only ... the returned param remained hidden
     * i.e. does not take out the HIDDEN_KEY_PREFIX from the key
     * @param inputParams
     * @return
     */
    public static Map getHiddenParams(Map inputParams) {
        HashMap hiddenParams = new HashMap();
        inputParams.each {
            if (it.key.indexOf(HIDDEN_KEY_PREFIX) == 0) {
                // skip ALL params with no HIDDEN_KEY_PREFIX ... these are params
                // we want to get rid of (e.g. params from login commands)
                hiddenParams.put(it.key, it.value)
            }
        }
        return hiddenParams
    }
	
    /**
     * see if there are any hidden params
     * @param inputParams
     * @return
     */
    public static boolean hiddenParamsExist(Map inputParams) {
        boolean result = false
        inputParams.each {
            if (it.key.indexOf(HIDDEN_KEY_PREFIX) == 0) {
                result = true // hidden param found
            }
        }
        return result
    }

    /**
     * - hide current params by modifying keys by added _HIDDEN_CURRENT_ in front
     * - add a flag to indicate this is a result from accessing a resource with 
     *   insufficient privilege
     * e.g. action => _HIDDEN_CURRENT_action
     * @param inputParams
     * @return
     */
    public static Map hideCurrentParamsFromPermissionDenied(Map inputParams) {
        if (hiddenParamsExist(inputParams)) {
            return inputParams; // do not hide params more than once
        }
        HashMap currentParams = new HashMap();
        inputParams.each {
            currentParams.put(HIDDEN_KEY_PREFIX+it.key, it.value)
        }
        currentParams.put(HIDDEN_FLAG_PERMISSION_DENIED, "");
        return currentParams
    }
    
    /**
     * replace single/double quote with HTML code so that it will display properly 
     * in html page
     * NOTE: Also replace newline with \n ... not sure if this is working though :(
     */
    public static String generateHtmlSafeQuoteUseEscape(String input) {
        if (input==null) {
            return null;
        } else {
            return input.replaceAll("'","\\\'").replaceAll('"',"\\\"").replaceAll("\r","\\\n").replaceAll("\n","\\\n");
        }
    }
    
    /**
     * replace new line character (\n and \r) with <br>
     * return null if input is null
     */
    public static String replaceNewLineWithBr(String input) {
        if (input == null) {
            return null;
        }
        return input.replaceAll("\r","<br>").replaceAll("\n","<br>");
    }
    
    
    /**
     * check to see if user is signed in
     */
    public static boolean showIsLoggedIn(HttpSession inputSession) {
        Users user = null;
        if (inputSession.user) { // try to see if we can get from http session
            user = Users.findByLogin(inputSession.user.login);
            if (user) {
                return true;
            }
        } 
        // not signed in
        return false;
    }
    
    /**
     * get preferred home for user
     * @param user
     * @param defaultHomeUrl (grailsApplication.config.grails.secureServerURL) in case for user without any specific preferred home
     */
    public static String showUserPreferredHomeUrl(Users user, String defaultHomeUrl) {
        String preferredHome = user.preferredHomeControllerName() != null ? createLink(controller:user.preferredHomeControllerName()) : defaultHomeUrl;
        return preferredHome;
    }
    
    
    /**
     * for process tests ...
     * @param args
     */
    public static void main(String[] args) {
		
        System.out.println("_HIDDEN_CURRENT_action".indexOf(HIDDEN_KEY_PREFIX));
		
    }
}