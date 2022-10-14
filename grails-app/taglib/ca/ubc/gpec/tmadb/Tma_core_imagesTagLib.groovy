package ca.ubc.gpec.tmadb

import ca.ubc.gpec.tmadb.ImageViewerMethods;
import ca.ubc.gpec.tmadb.util.OddEvenRowFlag;
import ca.ubc.gpec.tmadb.util.MiscUtil;
import ca.ubc.gpec.tmadb.util.ViewConstants;

class Tma_core_imagesTagLib {

    /**
     * display all tma_results of a tma core image
     * @attr tma_core_image
     * @attr tma_result_rep - representative tma_result record, optional
     */
    def displayTma_core_imageTma_results = { attr, body ->
        Tma_core_images tma_core_image = attr['tma_core_image'];
        def tma_results = tma_core_image.getTma_results();
        Tma_results tma_result_rep = null; // representative tma_result record ... to indicate which score the user is most interested at this moment
        if (attr['tma_result_rep']!=null) {
            tma_result_rep = attr['tma_result_rep']
        }
        if (tma_results != null ? !tma_results.isEmpty() : false) {
            out << "<table><tbody>";
            OddEvenRowFlag tma_resultsOddEvenRowFlag = new OddEvenRowFlag(ViewConstants.CSS_CLASS_NAME_ROW_ODD,ViewConstants.CSS_CLASS_NAME_ROW_EVEN);
            tma_results.each { t ->
                if (tma_result_rep?.getTma_result_name()?.compareTo(t.getTma_result_name())==0) {
                    out << "<tr class='"+tma_resultsOddEvenRowFlag.showFlag()+"' style='background-color: "+ViewConstants.HIGHLIGHT_COLOR+"'><td>";
                } else {
                    out << "<tr class='"+tma_resultsOddEvenRowFlag.showFlag()+"'><td>";
                }
                out << "<ul><li><a href='"+createLink(controller:"tma_results", action:"show", id:t.getId())+"' title='"+t.showDefaultScoreDescription()+"'>"+t.showDefaultScoreNameWithScorersAndComment()+"</a></li></ul>";
                out << "</td></tr>"
            }
            out << "</tbody></table>";
        }
    }

    /**
     * display tma core image
     * @attr id of tma_core_image
     * @attr tma_result_rep - representative tma_result record, optional
     */
    def displayTma_core_image = { attr, body ->
        Tma_core_images tma_core_image = Tma_core_images.get(attr['id']);
        Tma_results tma_result_rep = null; // representative tma_result record
        if (attr['tma_result_rep']!=null) {
            tma_result_rep = attr['tma_result_rep']
        }
        
        // see if there are any keyword to display 
        Keywords keyword = tma_core_image.tma_core.showFirstKeyword();
        if (keyword != null) {
            out << "<p>Keyword: ";
            out << "<a title='"+(keyword.description==null?"":keyword.description)+"'>"+keyword+"</a>";
            out << "</p>"
        }
        
        out << "<table><tbody><tr>";
        out << "<td>"
        out << "<div dojoType='dijit.layout.TabContainer'  tabPosition='top' style='width: "+(ImageViewerMethods.IMAGE_VIEWER_WIDTH+50)+"px; height: "+(ImageViewerMethods.IMAGE_VIEWER_HEIGHT+170)+"px; '>";
        out << "<div dojoType='dijit.layout.ContentPane'  title='TMA core image' >";
        out << tma_core_image.showImageHtml(createLink(uri: '/').toString());
        out << "<br>";
        out << g.displayTma_core_imageTma_results(tma_core_image: tma_core_image, tma_result_rep:tma_result_rep);
        out << "</div>" // dijit.layout.ContentPane

        out << "<div dojoType='dijit.layout.ContentPane'  title='Details' >";
        out << "<table id = '"+ViewConstants.TMA_CORE_IMAGE_DETAILS_TABLE_ID+tma_core_image.getId()+"'>"
        out << "<tbody>"      
        OddEvenRowFlag oddEvenRowFlag = new OddEvenRowFlag(ViewConstants.CSS_CLASS_NAME_ROW_ODD,ViewConstants.CSS_CLASS_NAME_ROW_EVEN);       
        if (tma_core_image.getImage_server() != null) {
            out << "<tr class='"+oddEvenRowFlag.showFlag()+"'>";
            out << "<td valign='top' class='name'>Image server:</td>";
            out << "<td valign='top' class='value'><a href='"+createLink(controller:"image_servers", action:"show", id:tma_core_image.getImage_server().getId())+"'>"+tma_core_image.getImage_server().encodeAsHTML()+"</g:link></td>";
            out << "</tr>"
        }      
        out << "<tr class='"+oddEvenRowFlag.showFlag()+"'>";
        out << "<td valign='top' class='name'>Resource name:</td>";
        out << "<td valign='top' class='value'>"+tma_core_image.getResource_name()+"</td>";
        out << "</tr>"

        out << "<tr class='"+oddEvenRowFlag.showFlag()+"'>";
        out << "<td valign='top' class='name'>Scanning date:</td>";
        out << "<td valign='top' class='value'>"+MiscUtil.formatDate(tma_core_image.getScanning_date())+"</td>";
        out << "</tr>";

        out << "<tr class='"+oddEvenRowFlag.showFlag()+"'>";
        out << "<td valign='top' class='name'>Scanner info:</td>";
        out << "<td valign='top' class='value'><a href='"+createLink(controller:"scanner_infos", action:"show", id:tma_core_image.getScanner_info().getId())+"'>"+tma_core_image.getScanner_info().encodeAsHTML()+"</td>";
        out << "</tr>";

        out << "<tr class='"+oddEvenRowFlag.showFlag()+"'>";
        out << "<td valign='top' class='name'>Server path:</td>";
        out << "<td valign='top' class='value'>"+tma_core_image.getServer_path()+"</td>";
        out << "</tr>";
        
        String comment = tma_core_image.getComment();
        if (comment != null) {
            out << "<tr class='"+oddEvenRowFlag.showFlag()+"'>";
            out << "<td valign='top' class='name'>Comment:</td>";
            out << "<td valign='top' class='value'>"+tma_core_image.getComment()+"</td>";
            out << "</tr>";
        }

        out << "</tbody>";
        out << "</table>";
        out << "</div>"; // dijit.layout.ContentPane
        
        out << "</div>"; // dijit.layout.TabContainer
        out << "</td>";
        out << "<td>"
        Tma_slices tma_slice = tma_core_image.getTma_slice();
        if (tma_result_rep!=null) {
            out << "<a href='"+createLink(controller:"tma_slices", action:"show", params:[id:tma_slice.getId(), tma_results_id:tma_result_rep.getId()])+"' title='"+tma_slice.getDescription()+"'>TMA slice "+tma_slice.getName()+"</a>";
            out << "<br>"
            out << displayTma_sliceSectorMapInHtmlTable(id:tma_slice.getId(), mode:"tma_core_image_nav", selectedTma_core_image:tma_core_image, tma_result_rep:tma_result_rep);
        } else {
            out << "<a href='"+createLink(controller:"tma_slices", action:"show", id:tma_slice.getId())+"' title='"+tma_slice.getDescription()+"'>TMA slice "+tma_slice.getName()+"</a>";
            out << "<br>"
            out << displayTma_sliceSectorMapInHtmlTable(id:tma_slice.getId(), mode:"tma_core_image_nav", selectedTma_core_image:tma_core_image);
        }
        out << "</td>"
        out << "</tr></tbody></table>"
    }
}
