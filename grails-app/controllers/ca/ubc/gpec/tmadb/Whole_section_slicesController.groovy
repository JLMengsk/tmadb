package ca.ubc.gpec.tmadb

import ca.ubc.gpec.tmadb.util.MiscUtil;
import ca.ubc.gpec.tmadb.util.OddEvenRowFlag;
import ca.ubc.gpec.tmadb.util.ViewConstants;

class Whole_section_slicesController {

    /**
     * index redirects to list
     */
    def index = {
        redirect(base: (MiscUtil.showIsLoggedIn(session)?grailsApplication.config.grails.serverSecureURL:grailsApplication.config.grails.serverURL), action: "list", params: params)
    }

    /**
     * list available tma slices to user
     */
    def list = {
        // view list.gsp ... data query by ajax
        return [user:session.user]
    }
    
    /**
     * show whole section slice
     */
    def show = {
        def whole_section_slicesInstance = Whole_section_slices.get(params.id)
        if (!whole_section_slicesInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'whole_section_slices.label', default: 'Whole_section_slices'), params.id])}"
            redirect(url: grailsApplication.config.grails.serverSecureURL); // go back to home
        }
        else {
            [
                whole_section_slicesInstance: whole_section_slicesInstance,
                oddEvenRowFlag: new OddEvenRowFlag(ViewConstants.CSS_CLASS_NAME_ROW_ODD,ViewConstants.CSS_CLASS_NAME_ROW_EVEN)
            ]
        }
    }
    
    /*
     * get all available whole_section_slices
     * - designed for datagrid in list.gsp from whole_section_slices
     * - format [name]___[url]
     */
    def ajaxGetAvailableWhole_section_slices = {
        
        TreeSet<Whole_section_slices> whole_section_slices = new TreeSet<Whole_section_slices>();
        Users user = Users.findByLogin(session.user.login);
        if (user.showIsAdministrator()) {
            Whole_section_slices.list().each {
                whole_section_slices.add(it); // all tma slices are available to administrator
            }
        } else {
            user.getUser_permits().each {
                if (it.getWhole_section_slice() != null) {
                    whole_section_slices.add(it.getWhole_section_slice());
                }
            }
        }
        int id;
        String name;
        String block;
        String bm;
        ArrayList array = new ArrayList(whole_section_slices.size());

        for (whole_section_slice in whole_section_slices) {

            Biomarkers biomarker = whole_section_slice.getStaining_detail().getBiomarker();
            String block_string = "";
            if (whole_section_slice.getParaffin_block().showIsSurgical_block()) {
                block_string = whole_section_slice.getParaffin_block().getSurgical_block().encodeAsHTML()+ViewConstants.AJAX_RESPONSE_DELIMITER+createLink(controller:"surgical_blocks", action:"show", id:whole_section_slice.getParaffin_block().getSurgical_block().getId())
            } else if (whole_section_slice.getParaffin_block().showIsCore_biopsy_block()) {
                block_string = whole_section_slice.getParaffin_block().getCore_biopsy_block().encodeAsHTML()+ViewConstants.AJAX_RESPONSE_DELIMITER+createLink(controller:"core_biopsy_blocks", action:"show", id:whole_section_slice.getParaffin_block().getCore_biopsy_block().getId());
            } else {
                block_string = whole_section_slice.getParaffin_block().encodeAsHTML()+createLink(controller:"paraffin_blocks", action:"show", id:whole_section_slice.getParaffin_block().getId());
            }

            id = whole_section_slice.getId();
            name = whole_section_slice.getName()+ViewConstants.AJAX_RESPONSE_DELIMITER+createLink(controller:"whole_section_slices", action:"show", id:whole_section_slice.getId());
            block = block_string;
            bm = biomarker.getName()+" ("+biomarker.getBiomarker_type().getName()+")"+ViewConstants.AJAX_RESPONSE_DELIMITER+createLink(controller:"biomarkers", action:"show", id:biomarker.getId());
            array.add("id":id, "name":name,"block":block,"biomarker":bm);
        }

        render(contentType: "text/json") {
            identifier("id")
            numRows(whole_section_slices.size())
            items(array)

        }
    }

}
