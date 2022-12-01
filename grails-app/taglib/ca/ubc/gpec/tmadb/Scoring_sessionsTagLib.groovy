package ca.ubc.gpec.tmadb
import ca.ubc.gpec.tmadb.util.ViewConstants;

class Scoring_sessionsTagLib {

    /**
     * display the overall scoring progress (i.e. number of images left) for a scoring session.
     * When the scoring session is completed, display link to download the scores/data.
     * 
     * @attr id of the tma_scoring object 
     * 
     * requires a function cleanUp() ... this function is responsible for closing any possible popup windows
     */
    def display_scoring_session_progress = { attr, body ->
        long id = attr['id'];
        Scoring_sessions scoring_session = Scoring_sessions.get(id);
        out << "<div>";
        if (scoring_session.showCompleted()) {        
            out << "<a href='"+createLink(controller: 'scoring_sessions', action: 'report', id: id)+"' title='Click me to show scoring session report' onclick='cleanUp();'>[show report]</a>"
            out << " "
            out << "<a href='"+createLink(controller: 'downloadData', action: 'downloadScoringSessionScores', id: id)+"' title='Click me to download scoring session scores in tab-delimited text format'>[download scores]</a>"
            out << " "
            if(scoring_session.showHasNucleiSelection()) {
                out << "<a href='"+createLink(controller: 'downloadData', action: 'downloadScoringSessionNucleiSelections', id: id)+"' title='Click me to download scoring session nuclei selections in tab-delimited text format'>[download nuclei selections]</a>"
                out << " "
            }
            if (!scoring_session.showSubmitted()) {
                out << "<a href='"+createLink(controller: 'scoring_sessions', action: 'submitScores', id: id)+"' title='WARNING: You will not be able to modify scores once submitted.' "
                out << "onclick=\"cleanUp(); return confirm('You will not be able to modify scores once submitted.  Continue to submit?')\" "
                out << ">[<span style=\"background-color: yellow\">submit scores</span>]</a>"
            }
        } else {
            out << "number of remaining images ... <i>"+scoring_session.showRemainingImageCount()+"</i>"
        }
        out << "<div style=\"display: inline-block; float: right\">"
        if (scoring_session.showIsKi67QcCalibratorTraining() || scoring_session.showIsKi67QcCalibratorTest()) {
            out << "<a href='"+createLink(controller:'calibrator')+"' ";
            if (!scoring_session.showSubmitted()) {
                out << " onClick=\"if(confirm('If you have not saved your nuclei selection, please click cancel and save nuclei selection first.')){cleanUp(); return true;}else{return false;}\">";
            } else {
                out << " onClick=\"cleanUp(); return true;\">";
            }
            out << "calibrator intro page <img src='"+resource(dir:'images/skin',file:'calibration.png')+"'/></a>&nbsp;|&nbsp;";
            out << "<a href='http://www.gpec.ubc.ca/calibrator_doc/nuclei_count_method.pdf' target='ki67_qc_calibrator_help'>scoring procedure <img src='"+resource(dir:'images/skin',file:'pdf_icon.jpg')+"'/></a>&nbsp;|&nbsp;";
            out << "<button dojoType='dijit.form.Button' onclick='window.open(\"http://www.gpec.ubc.ca/calibrator_doc/faqs.html\");'>troubleshooting</button>";
        } else {
            out << "<button dojoType='dijit.form.Button' onclick='window.open(\"http://www.gpec.ubc.ca/calibrator_doc/faqs.html\");'>troubleshooting</button>";
        }
        out << "</div>";
        out << "</div>";
    }
}
