package ca.ubc.gpec.tmadb

import ca.ubc.gpec.tmadb.DisplayConstant;
import ca.ubc.gpec.tmadb.ImageViewerMethods;
import ca.ubc.gpec.tmadb.util.MiscUtil;
import ca.ubc.gpec.tmadb.util.Scoring_sessionsConstants;
import ca.ubc.gpec.tmadb.util.ViewConstants;

class Tma_scoringsTagLib {

    /**
     * display a javascript array of nuclei selection notification nuclei count
     * e.g. [250,500]
     * 
     * @attr id of the tma_scoring object 
     */
    def tma_scoring_nuclei_selection_notification_nuclei_counts = { attr, body ->
        Tma_scorings tma_scoring = Tma_scorings.get(attr['id']);
        String nuclei_countsString = "";
        tma_scoring.showScoring_nuclei_selection_notifications().each { scoring_nuclei_selection_notification -> 
            nuclei_countsString = nuclei_countsString + scoring_nuclei_selection_notification.getNuclei_selection_notification().getNuclei_count() +","
        }
        nuclei_countsString = nuclei_countsString.length() == 0 ? nuclei_countsString : nuclei_countsString.substring(0,nuclei_countsString.length()-1); // get rid of last comma
        out << "["+nuclei_countsString+"]";
    }
    
    /**
     * display a javascript array of nuclei selection notification messages
     * e.g. [250,500]
     * 
     * @attr id of the tma_scoring object 
     */
    def tma_scoring_nuclei_selection_notification_messages = { attr, body ->
        Tma_scorings tma_scoring = Tma_scorings.get(attr['id']);
        String messagesString = "";
        tma_scoring.showScoring_nuclei_selection_notifications().each { scoring_nuclei_selection_notification -> 
            messagesString = messagesString + "\"" + MiscUtil.generateHtmlSafeQuoteUseEscape(scoring_nuclei_selection_notification.getNuclei_selection_notification().getMessage()) + "\","
        }
        messagesString = messagesString.length() == 0 ? messagesString : messagesString.substring(0,messagesString.length()-1); // get rid of last comma
        out << "["+messagesString+"]";
    }
    
    /**
     * show image diaplay options
     * 
     * @attr tma_scoring object 
     * @attr showReference indicate whether currently showing reference image
     * @attr showPageBodyOnly indicate whether to show page body only
     * @attr showHE indicate whether to show H&E
     * @attr showMM indicates whether to show myoepithelial marker
     */
    def display_tma_scoring_image_option = { attr, body ->
        Tma_scorings tma_scoring = attr['tma_scoring'];
        long id = tma_scoring.getId();
        long scoring_session_id = tma_scoring.getScoring_session().getId()
        boolean showReference = attr['showReference']
        boolean showPageBodyOnly = attr[ViewConstants.SHOW_BODY_ONLY_PARAM_FLAG]
        boolean showHE = attr['showHE']
        boolean showMM = attr['showMM']
        
        if (!showReference && !showPageBodyOnly) {
            if (showHE) {
                out << "showing <strong>H&E</strong> image " // need space at end 
                out << "["
                if(tma_scoring.showIsScoringTypeNucleiCountNoCoord()) {
                    out << "<a href='"+createLink(controller:"scoring_sessions",action:"score", id:scoring_session_id, params:["tma_scoring_id":id])+"' title=\"click me to show cell counter\">cell counter</a>"
                } else {
                    out << "<a href='"+createLink(controller:"scoring_sessions",action:"score", id:scoring_session_id, params:["tma_scoring_id":id])+"' title=\"click me to show marker image\">"+tma_scoring.getTma_core_image().getTma_slice().getStaining_detail().getBiomarker().getName()+" image</a>"
                }
                out << "] " // remember space after ']'
            } else if (showMM) {
                out << "showing <strong>myoepithelial marker</strong> image " // need space at end 
                out << "["
                if(tma_scoring.showIsScoringTypeNucleiCountNoCoord()) {
                    out << "<a href='"+createLink(controller:"scoring_sessions",action:"score", id:scoring_session_id, params:["tma_scoring_id":id])+"' title=\"click me to show cell counter\">cell counter</a>"
                } else {
                    out << "<a href='"+createLink(controller:"scoring_sessions",action:"score", id:scoring_session_id, params:["tma_scoring_id":id])+"' title=\"click me to show marker image\">"+tma_scoring.getTma_core_image().getTma_slice().getStaining_detail().getBiomarker().getName()+" image</a>"
                }
                out << "] " // remember space after ']'      
            } else {
                out << "showing <strong>"
                if (tma_scoring.showIsScoringTypeNucleiCountNoCoord()) {
                    out << "cell counter</strong> " // need space at end
                } else {
                    out << tma_scoring.getTma_core_image().getTma_slice().getStaining_detail().getBiomarker().getName()+"</strong> image "
                }
                out <<"["
                if (tma_scoring.showIsAllowedToUpdateScore()) {
                    out << "<a href=\"\" onClick=\""
                    //out << ((tma_scoring.showIsScoringTypeNucleiCountNoCoord() | tma_scoring.showIsScoringTypeNucleiCount()) ? "nucleiCounter.uploadNucleiSelection(); " : "")
                    out << "openHeWindow();return false;\" title=\"click me to show H&E image in separate window\"><span style='background-color:"+ViewConstants.COLOR_CODE_PINK+"; color:"+ViewConstants.COLOR_CODE_WHITE+";'>H&E image</span></a>"
                } else {
                    out << "<a href='' onClick=\"openHeWindow();return false;\" title=\"click me to show H&E image in separate window\"><span style='background-color:"+ViewConstants.COLOR_CODE_PINK+"; color:"+ViewConstants.COLOR_CODE_WHITE+";'>H&E image</span></a>";
                    // NOTE currently only Ki67-QC phase 3 need myoepithelial marker image ... not sure if this will be need for general scoring
                    //out << " | ";
                    //out << "<a href='' onClick=\"openMmWindow();return false;\" title=\"click me to show myoepithelial marker image in separate window\">myoepithelial marker image</a>"
                }
                out << "] " // remember space after ']'
            }
        }
        
        // only do the following if scores submitted already
        // do not show if ki67 calibrator test!!!
        if (!tma_scoring.showIsAllowedToUpdateScore() && !showPageBodyOnly && (!tma_scoring.getScoring_session().showIsKi67QcCalibratorTest())) {
            if (tma_scoring.showTma_scoring_referencesAvailable()) {
                int counter = 0;
                tma_scoring.showTma_scoring_references().each {
                    out << "["
                    out << "<a href='' onclick=\"refWindowHandle=window.open('"+createLink(controller:"scoring_sessions", action:"score", id:scoring_session_id, params:["tma_scoring_id":id, "showRef":counter, (ViewConstants.SHOW_BODY_ONLY_PARAM_FLAG):true])+"', 'reference image',JAVASCRIPT_OPEN_POPUP_WINDOW_SPECS); return false;\" title='click me to show reference image #"+(counter+1)+" (in new window)'>reference "+(counter+1)+"</a>"
                    out << "] " // remember space after ']'
                    counter++;
                }
                out << "<strong>reference score: "+tma_scoring.showAveragePercentPositiveTma_scoring_referencesText()+" (%+ve)</strong>"
            }
        }
    }
    
    /**
     * display link to corresponding H&E image
     *
     * NOTE: use https since user must have logged in.
     *
     * @attr id of the tma_scoring object 
     */
    def display_tma_scoring_he_link = { attr, body ->
        long id = attr['id']
        Tma_scorings tma_scoring = Tma_scorings.get(id);
        out << createLink(base:grailsApplication.config.grails.serverSecureURL, controller:"scoring_sessions", action:"score", id:tma_scoring.getScoring_session().getId(), params:["tma_scoring_id":id,"showHE":"",(ViewConstants.SHOW_BODY_ONLY_PARAM_FLAG):true])
    }
    
    /**
     * display navigation i.e. next/previous tma_scoring
     * 
     * @attr tma_scoring
     * 
     * requires a function cleanUp() ... this function is responsible for closing any possible popup windows
     */
    def display_tma_scoring_navigation = { attr, body ->
        Tma_scorings tma_scoring = attr['tma_scoring'];
        Tma_scorings prevTma_scoring = tma_scoring.getScoring_session().showPrevTma_scoring(tma_scoring);
        Tma_scorings nextTma_scoring = tma_scoring.getScoring_session().showNextTma_scoring(tma_scoring);
        if (prevTma_scoring != null | nextTma_scoring != null) {
            out << "<tr class='prop'><td valign='top' class='name' colspan='2'>Show " // need space after!
            if (prevTma_scoring != null) {
                out << "<a href='"+createLink(controller:"scoring_sessions", action:"score", id:tma_scoring.getScoring_session().getId(), params:["tma_scoring_id":prevTma_scoring.getId()])+"' "
                out << "title='click me to show the previous scored image' onClick='cleanUp();' "
                out << ">previous</a>"
            }
            if (prevTma_scoring != null & nextTma_scoring != null) {
                out << " | "
            }
            if (nextTma_scoring != null) {
                out << "<a href='"+createLink(controller:"scoring_sessions", action:"score", id:tma_scoring.getScoring_session().getId(), params:["tma_scoring_id":nextTma_scoring.getId()])+"' "
                out << "title='click me to show the next scored image' onClick='cleanUp();' "
                out << ">next</a>" 
            }            
            out << "</td></tr>"
        }
    } 
    
    /**
     * display image zoom/pan command
     * 
     * require the applet nucleiCounter
     * - use the method: 
     *      nucleiCounter.zoomOut()
     *      nucleiCounter.zoomIn()
     *      nucleiCounter.zoomFit()
     *      nucleiCounter.moveX()
     *      nucleiCounter.moveY()
     */
    def display_tma_scoring_image_zoom_pan_command = { attr, body ->
        out << "<td valign=\"top\" class=\"name\">Navigation commands</td>";
        out << "<td valign=\"top\" class=\"value\">";
        out << "<i>zoom-in/out:</i>"; 
        out << "<a href=\"\" onClick=\"nucleiCounter.zoomOut();return false;\" title=\"click here to zoom out (or mouse scroll down)\">[-]</a>";
        out << " | " // need space before/after
        out << "<a href=\"\" onClick=\"nucleiCounter.zoomIn();return false;\" title=\"click here to zoom in (or mouse scroll up)\">[+]</a>";
        out << " | " // need space before/after
        out << "<a href=\"\" onClick=\"nucleiCounter.zoomOne();return false;\" title=\"click here to zoom in to 1:1\">[100%]</a>";
        out << " | " // need space before/after
        out << "<a href=\"\" onClick=\"nucleiCounter.zoomFit();return false;\" title=\"click here to zoom out so that whole core image fit the window\">[fit]</a>";
        out << "&nbsp;&nbsp;&nbsp;<i>move:</i>"; 
        out << "<a href=\"\" onClick=\"nucleiCounter.moveX(50);return false;\" title=\"click here to move left\">[&larr;]</a>";
        out << " | " // need space before/after
        out << "<a href=\"\" onClick=\"nucleiCounter.moveX(-50);return false;\" title=\"click here to move right\">[&rarr;]</a>";
        out << " | " // need space before/after
        out << "<a href=\"\" onClick=\"nucleiCounter.moveY(50);return false;\" title=\"click here to move up\">[&uarr;]</a>";
        out << " | " // need space before/after
        out << "<a href=\"\" onClick=\"nucleiCounter.moveY(-50);return false;\" title=\"click here to move down\">[&darr;]</a>";
        out << "&nbsp;&nbsp;&nbsp;<i>hide markings</i>";
        out << "<a href=\"\" onClick=\"return false;\" onmouseover=\"nucleiCounter.setNucleiSelectionVisible(false);\" onmouseout=\"nucleiCounter.setNucleiSelectionVisible(true);\">[mouse over]</a>";
    }
    
    /**
     * display nuclei selector (counter) applet
     * 
     * @attr tma_scoring object
     */
    def display_nuclei_selector_applet = { attr, body ->
        Tma_scorings tma_scoring = attr['tma_scoring'];
        out << "<canvas id='"+ImageViewerMethods.NUCLEI_COUNTER_CANVAS_ID+"' width='"+ImageViewerMethods.IMAGE_VIEWER_WIDTH+"' height='"+ImageViewerMethods.IMAGE_VIEWER_HEIGHT+"'>Failed to load image.</canvas>";
        out << "<script type='text/javascript'>"; 
        out << "var nucleiCounter;"
        out << "require(['dojo/ready'], function(ready){ready(function(){";
        out << "nucleiCounter = new NucleiCounter(";
        out << "'"+tma_scoring.getTma_core_image().showImageURL()+"','"+ImageViewerMethods.NUCLEI_COUNTER_CANVAS_ID+"',";
        out << "false,"; // drawThumbnailWindow
        out << "'',"; // counterImageURL i.e. nuclei counter
        out << "'','','',MEDIA_SOUND_FILE_DINGLING,"; // neg/pos/undo/notification sound url; MEDIA_SOUND_FILE_DINGLING defined in main.gsp
        out << "'"+tma_scoring.showNucleiSelectionParamString()+"',";
        out << (tma_scoring.showIsAllowedToUpdateScore()?"false":"true");
        out << ");";
        out << "nucleiCounter.updateNucleiCountOnBrowser();"; // this was called within Applet initializer ... therefore, need to call explicitly!
        out << "});});";
        out << "</script>";
    }
    
    /**
     * display nuclei counter with sector map
     * 
     * @attr tma_scoring object
     */
    def display_nuclei_counter_with_sector_map = { attr, body ->
        Tma_scorings tma_scoring = attr['tma_scoring'];
        out << "<i>sector map on the right indicates the TMA core "+(tma_scoring.showIsAllowedToUpdateScore()?"to be ":"")+"scored</i><br>";
        out << "<script src='"+(MiscUtil.showIsLoggedIn(session)?grailsApplication.config.grails.serverSecureURL_noAppName:grailsApplication.config.grails.serverURL_noAppName)+"images/tools/deployJava.js'></script>"; // does NOT work if put in Application.resources; does NOT work when using dojo.ready
        out << "<script type='text/javascript'>"; 
        out << "var attributes = { id:'nucleiCounter', code:'ca.ubc.gpec.nucleicounter.NucleiCounterApplet.class',  width:249, height:249} ;";
        out << "var parameters = { ";
        //out << "jnlp_href: '"+(MiscUtil.showIsLoggedIn(session)?grailsApplication.config.grails.serverSecureURL_noAppName:grailsApplication.config.grails.serverURL_noAppName)+"images/tools/nucleiCounter/nucleiCounter.jnlp',";
        out << "jnlp_href: \"nucleiCounter_forEmbed.jnlp\", jnlp_embedded: \""+grailsApplication.config.ca.ubc.gpec.nucleiCounterJnlpBase64+"\",";
        out << "separate_jvm: (navigator.userAgent.indexOf('Macintosh')==-1),";
        out << "classloader_cache: false,";
        out << "nucleiSelectionParamString: '"+tma_scoring.showNucleiSelectionParamString()+"',";
        if (tma_scoring.showIsAllowedToUpdateScore()){
            out << "readOnly: 'false'";
        } else {
            out << "readOnly: 'true'";
        }
        out << "} ;";
        out << "deployJava.runApplet(attributes, parameters, '1.7');";
        out << "</script>";
        
        out << "<script type='text/javascript'>"; 
        out << "var attributes = { id:'sectorMapViewer', code:'ca.ubc.gpec.tmadb.sectormapviewer.SectorMapViewerApplet',  width:249, height:249} ;";
        out << "var parameters = { jnlp_href: '"+(MiscUtil.showIsLoggedIn(session)?grailsApplication.config.grails.serverSecureURL_noAppName:grailsApplication.config.grails.serverURL_noAppName)+"images/tools/sectorMapViewer/sectorMapViewer.jnlp',";
        out << "separate_jvm: (navigator.userAgent.indexOf('Macintosh')==-1),";
        out << "classloader_cache: false,";
        out << "sectorMapString: '"+tma_scoring.getTma_core_image().getTma_core().showSectorMapString()+"'";
        out << "} ;";
        out << "deployJava.runApplet(attributes, parameters, '1.7');";
        out << "</script>";
        if (tma_scoring.showIsAllowedToUpdateScore()) {
            out << "<br>please type the cell counter commands (i.e. '"+Scoring_sessionsConstants.NUCLEI_COUNT_KEY_POSITIVE+"','"+Scoring_sessionsConstants.NUCLEI_COUNT_KEY_NEGATIVE+"','"+Scoring_sessionsConstants.NUCLEI_COUNT_KEY_UNDO+"') in text box below:<br><input id='keyStrokeListener' type='text' dojoType='dijit.form.TextBox' onkeypress='return sendCellCounterKeyStrokeToApplet(event);'/>";
        }
    }
}