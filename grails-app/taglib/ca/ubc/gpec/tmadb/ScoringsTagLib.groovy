package ca.ubc.gpec.tmadb

import ca.ubc.gpec.tmadb.MessageConstant;
import ca.ubc.gpec.tmadb.util.Scoring_sessionsConstants;
import ca.ubc.gpec.tmadb.util.ViewConstants;

class ScoringsTagLib {

    /**
     * show image diaplay options
     * 
     * @attr scoring object 
     * @attr showReference indicate whether currently showing reference image
     * @attr showPageBodyOnly indicate whether to show page body only
     * @attr showHE indicates whether to show H&E
     * @attr showMM indicates whether to show myoepithelial marker
     */
    def display_scoring_image_option = { attr, body ->
        Scorings scoring = attr['scoring'];
        out << "<span style='font-size:"+ViewConstants.FONT_SIZE_MEDIUM+"px'";
        if(scoring.showIsTma_scoring()) {
            out << g.display_tma_scoring_image_option(          tma_scoring:scoring.getTma_scoring(),                     showReference:attr['showReference'], showPageBodyOnly:attr['showPageBodyOnly'], showHE:attr['showHE'], showMM:attr['showMM']);
        } else if (scoring.showIsWhole_section_scoring()) {
            out << g.display_whole_section_scoring_image_option(whole_section_scoring:scoring.getWhole_section_scoring(), showReference:attr['showReference'], showPageBodyOnly:attr['showPageBodyOnly'], showHE:attr['showHE'], showMM:attr['showMM']);
        }
        out << "</span>";
    }
    
    /**
     * show the scoring objects id e.g. tma_scoring.id so to keep track of which object is being scored
     * 
     * this taglib is useful because scorig objects may be null
     * 
     * @attr scoring_session must not be null
     * @attr tma_scoring
     * @attr whole_sectionscoring
     */
    def display_scoring_objects_ids = { attr, body ->
        Scoring_sessions scoring_session = attr['scoring_session'];
        out << "<input type='hidden' name='id' value='"+scoring_session.getId()+"'/>";
        if (attr['tma_scoring'] != null) {
            Tma_scorings tma_scoring = attr['tma_scoring'];
            out << "<input type='hidden' name='"+ViewConstants.HTML_PARAM_NAME_SCORING_SESSION_TMA_SCORING_ID+"' value='"+tma_scoring.getId()+"'/>";
        }
        if (attr['whole_section_scoring'] != null) {
            Whole_section_scorings whole_section_scoring = attr['whole_section_scoring'];
            out << "<input type='hidden' name='"+ViewConstants.HTML_PARAM_NAME_SCORING_SESSION_WHOLE_SECTION_SCORING_ID+"' value='"+whole_section_scoring.getId()+"'/>";
        }
    }
        
    /**
     * show the description and name of scorig objects
     * show navigation option e.g. previous/next
     * 
     * @attr scoring_session must not be null
     * @attr tma_scoring
     * @attr whole_section_scoring
     * @attr showPageBodyOnly
     * @attr showHE (optional)
     * @attr showMM (optional)
     * 
     * requires javascript function cleanUpBeforeChangeMainWindow()
     */
    def display_scoring_objects_description_and_navigation = { attr, body ->
        Scoring_sessions scoring_session = attr['scoring_session'];
        Tma_scorings tma_scoring = attr['tma_scoring'];
        Whole_section_scorings whole_section_scoring = attr['whole_section_scoring'];
        boolean showPageBodyOnly = attr[ViewConstants.SHOW_BODY_ONLY_PARAM_FLAG];
        boolean showHE = (attr['showHE'] != null ? attr['showHE'] : false);
        boolean showMM = (attr['showMM'] != null ? attr['showMM'] : false);
        out << "<table>";
        out << "<tbody>";
        out << "<tr class='odd'><td valign='top' class='name'>Description: "+scoring_session.getDescription()+"</td></tr>";
        out << "<tr class='even'>";
        out << "<td valign='top' class='name'>";
        if (tma_scoring != null) {
            out << "TMA core: "+tma_scoring.getTma_core_image().getTma_core().toString();
        } else if (whole_section_scoring != null) {
            if (showHE) {
                Whole_section_images he = whole_section_scoring.getWhole_section_image().showHandE();
                if (he!=null) {
                    out << "Whole section: "+he.getWhole_section_slice().toStringWithParaffin_blockName();
                } else {
                    out << "Whole section: "+whole_section_scoring.getWhole_section_image().getWhole_section_slice().getParaffin_block().toString();
                } 
            } else if (showMM) {
                Whole_section_images mm = whole_section_scoring.getWhole_section_image().showMM();
                if (mm!=null) {
                    out << "Whole section: "+mm.getWhole_section_slice().toStringWithParaffin_blockName();
                } else {
                    out << "Whole section: "+whole_section_scoring.getWhole_section_image().getWhole_section_slice().getParaffin_block().toString();
                }
            } else {
                out << "Whole section: "+whole_section_scoring.getWhole_section_image().getWhole_section_slice().toStringWithParaffin_blockName();
            }
        } else {
            out << "ERROR: unknown scoring object"
        }
        out << "</td>";
        out << "</tr>";
        // show navigation option e.g. previous/next ONLY if not showPageBodyOnly
        if (!showPageBodyOnly) {
            if (tma_scoring != null) {
                out << g.display_tma_scoring_navigation(          tma_scoring:tma_scoring                    );
            } else if (whole_section_scoring != null) {
                out << g.display_whole_section_scoring_navigation(whole_section_scoring:whole_section_scoring);
            } 
        }
        out << "</tbody>";
        out << "</table>";
    }
    
    /**
     * display corresponding H&E image of the scoring object
     * 
     * @attr scoring_session must not be null
     * @attr tma_scoring
     * @attr whole_section_scoring 
     */ 
    def display_scoring_object_he = { attr, body ->
        Scoring_sessions scoring_session = attr['scoring_session'];
        Tma_scorings tma_scoring = attr['tma_scoring'];
        Whole_section_scorings whole_section_scoring = attr['whole_section_scoring'];
        out << "<table>";
        out << "<tbody>";
        out << "<tr><td>";
        def he_image; // can be Tma_core_image or Whole_section_image
        if (tma_scoring != null) {
            he_image = tma_scoring.getTma_core_image().showHandE();
        } else if (whole_section_scoring != null) {
            he_image = whole_section_scoring.getWhole_section_image().showHandE();
        }
        if (he_image != null) {
            out << ""+he_image.showImageHtml(createLink(uri: '/').toString(),ImageViewerMethods.IMAGE_VIEWER_WIDTH,ImageViewerMethods.IMAGE_VIEWER_HEIGHT);
        } else {
            out << MessageConstant.MSG_HE_IMAGE_NOT_AVAILABLE;
        }
        out << "</td></tr>";
        out << "</tbody>";
        out << "</table>";
    }
    
    /**
     * display corresponding myoepithelial marker image of the scoring object
     * 
     * assume there is ONLY ONE !!!
     * 
     * @attr scoring_session must not be null
     * @attr tma_scoring
     * @attr whole_section_scoring 
     */ 
    def display_scoring_object_mm = { attr, body ->
        Scoring_sessions scoring_session = attr['scoring_session'];
        Tma_scorings tma_scoring = attr['tma_scoring'];
        Whole_section_scorings whole_section_scoring = attr['whole_section_scoring'];
        out << "<table>";
        out << "<tbody>";
        out << "<tr><td>";
        def he_image; // can be Tma_core_image or Whole_section_image
        if (tma_scoring != null) {
            he_image = tma_scoring.getTma_core_image().showMM();
        } else if (whole_section_scoring != null) {
            he_image = whole_section_scoring.getWhole_section_image().showMM();
        }
        if (he_image != null) {
            out << ""+he_image.showImageHtml(createLink(uri: '/').toString(),ImageViewerMethods.IMAGE_VIEWER_WIDTH,ImageViewerMethods.IMAGE_VIEWER_HEIGHT);
        } else {
            out << MessageConstant.MSG_MM_IMAGE_NOT_AVAILABLE;
        }
        out << "</td></tr>";
        out << "</tbody>";
        out << "</table>";
    }
    
    /**
     * display nuclei count status i.e. number of +/-/total
     * NOTE: status is display in TWO td element
     * 
     * @attr numNeg_id - dom id
     * @attr numPos_id - dom id
     * @attr numTotal_id - dom id
     */
    def display_nuclei_count_status = {attr, body ->
        boolean hidden = false;
        if (attr['hidden'] != null) {
            hidden=attr['hidden'];
        }
        out << (hidden?"":"<td valign='top' class='name'>Current nuclei count:</td><td valign='top' class='value'>");
        out << "<div style='display:"+(hidden?"none":"inline")+";' id='"+attr['numNeg_id']  +"'>0</div>"+(hidden?"":"(-) | ");
        out << "<div style='display:"+(hidden?"none":"inline")+";' id='"+attr['numPos_id']  +"'>0</div>"+(hidden?"":"(+) | ");
        out << "<div style='display:"+(hidden?"none":"inline")+";' id='"+attr['numTotal_id']+"'>0</div>"+(hidden?"":"(total)</td>");    
    }
    
    /**
     * display two buttons: 1) undo nuclei selection, 2) save nuclei selection
     * @attr tma_scoring
     */
    def display_nuclei_counter_undo_save_button = { attr, body ->
        Tma_scorings tma_scoring = attr['tma_scoring'];
        if (tma_scoring?.showIsScoringTypeNucleiCount()) {
            if (tma_scoring.showIsAllowedToUpdateScore()) {
                out << "<span class='button'>";
                out << "<button dojoType='dijit.form.Button' type='submit' value='save' title='click me to undo last nuclei selection' "; 
                out << " onclick='nucleiCounter.removeLastNucleiSelection();return false;'>UNDO</button>";
                out << "<button dojoType='dijit.form.Button' type='submit' value='save' title='click here to save nuclei selection' "; 
                out << " onclick=\"nucleiCounter.uploadNucleiSelection(); return false;\">SAVE nuclei selection</button>";
                out << "</span>"                
            } else {
                out << "NA";
            }
        } 
    }
    
    /**
     * display scoring commands
     * 
     * @attr id
     * @attr tma_scoring object
     * @attr whole_section_scoring object
     * @attr whole_section_region_scoring object
     * @attr displayInOneTd
     *
     * may require the applet nucleiCounter
     * - may use the method: 
     *     nucleiCounter.removeLastNucleiSelection()
     *     nucleiCounter.uploadNucleiSelection()
     *     nucleiCounter.playAddPositiveNucleiSound()
     *     nucleiCounter.playAddNegativeNucleiSound()
     *     nucleiCounter.playUndoNucleiSound()
     */
    def display_scoring_commands = { attr, body ->
        Tma_scorings tma_scoring = attr['tma_scoring'];
        Whole_section_scorings whole_section_scoring = attr['whole_section_scoring'];
        Whole_section_region_scorings whole_section_region_scoring = attr['whole_section_region_scoring'];
        boolean displayInOneTd = attr['displayInOneTd'] == null ? false : attr['displayInOneTd']; // default false
        
        out << "<td "+(attr['id'] == null ? "" : ("id='"+attr['id']+"'"))+"valign='top' class='"+(displayInOneTd?"value":"name")+"'>"+(displayInOneTd?"<b>":"")+"<span style=\"font-size:large\">Nuclei Counting Method</span>"+(displayInOneTd?"</b><br>":"</td>");
        out << (displayInOneTd ? "" : "<td valign='top' class='value'>");
        if (tma_scoring?.showIsScoringTypeNucleiCount()) {
            out << "<ul>";
            out << "<li>select positive nuclei: shift + mouse double click</li>";
            out << "<li>select negative nuclei: control + mouse double click</li>";
            // need to synchronize between server/applet FIRST before remove last nuclei selection 
            out << "</ul>"
            out << g.display_nuclei_counter_undo_save_button(tma_scoring: tma_scoring);
            out << "</td>"
        } else if (whole_section_scoring != null || whole_section_region_scoring != null || tma_scoring?.showIsScoringTypeNucleiCountNoCoord()) {
            // get scoring object ... 
            Scorings scoring = null;
            if (tma_scoring != null) {
                scoring = tma_scoring.getScoring();
            } else if (whole_section_region_scoring != null) {
                scoring = whole_section_region_scoring.getScoring();
            } else if (whole_section_scoring != null) {
                scoring = whole_section_scoring.getScoring();
            }

            if (scoring.showIsAllowedToUpdateScore()){
                out << "<br>";
                out << "<b>Please click in the box below then use the keyboard to enter cell counter commands</b> (each keystroke command will produce a unique sound through the computer speakers)"
                out << "<ul>"
                out << "<li>Count positive nucleus: press     <span style=\"font-size:large\"><b>'"+Scoring_sessionsConstants.NUCLEI_COUNT_KEY_POSITIVE+"'</b></span> key <a href=\"\" onClick=\"nucleiCounter.playAddPositiveNucleiSound();return false;\" title=\"test/play sound when positive nuclei counted\" ><img src='"+resource(dir:'images',file:'play_sound_image_small.jpg')+"' border=\"0\" /></a></li>";
                out << "<li>Count negative nucleus: press     <span style=\"font-size:large\"><b>'"+Scoring_sessionsConstants.NUCLEI_COUNT_KEY_NEGATIVE+"'</b></span> key <a href=\"\" onClick=\"nucleiCounter.playAddNegativeNucleiSound();return false;\" title=\"test/play sound when negative nuclei counted\" ><img src='"+resource(dir:'images',file:'play_sound_image_small.jpg')+"' border=\"0\" /></a></li>";
                out << "<li>Undo previous keystroke(s): press <span style=\"font-size:large\"><b>'"+Scoring_sessionsConstants.NUCLEI_COUNT_KEY_UNDO+    "'</b></span> key <a href=\"\" onClick=\"nucleiCounter.playUndoNucleiSound();       return false;\" title=\"test/play sound when undo nuclei counted\" >    <img src='"+resource(dir:'images',file:'play_sound_image_small.jpg')+"' border=\"0\" /></a></li>";
                if (scoring.showHasNucleiSelectionNotifications()){
                    out << "<li>notification will sound and appear at the following nuclei count interval:";
                    int counter=0;
                    int scoring_nuclei_selection_notificationsSize = scoring.getScoring_nuclei_selection_notifications().size();
                    scoring.getScoring_nuclei_selection_notifications().each { item ->
                        out << item.nuclei_selection_notification.getNuclei_count();
                        out << ((counter<(scoring_nuclei_selection_notificationsSize-1)) ? "," : "")
                    }
                    out << "<a href=\"\" onClick=\"nucleiCounter.playNotificationSound();return false;\" title=\"test/play notification sound\" ><img src='"+resource(dir:'images',file:'play_sound_image_small.jpg')+"' border=\"0\" /></a>";
                    out << "</li>"
                    counter++;
                }
                if (!scoring.showIsScoringTypeKi67Phase3()) { // for Ki67-QC phase 3, display the links below as buttons ... shown near the nuclei counter
                    out << "<li><a href=\"\" onClick=\"nucleiCounter.uploadNucleiSelection(); return false;\" title=\"click here to save nuclei counts\">SAVE nuclei counts</a></li>";
                    out << "<li><a href=\"\" onClick=\"return resetCellCounter();\" title=\"click here to reset cell counter\">RESET COUNTER</a></li>";
                }
                out << "</ul>";
            } else {
                out << "NA";
            }
            out << "</td>"
        }
    }
    
}
