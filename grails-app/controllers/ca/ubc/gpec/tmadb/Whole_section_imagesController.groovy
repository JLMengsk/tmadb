package ca.ubc.gpec.tmadb

import ca.ubc.gpec.tmadb.util.OddEvenRowFlag;
import ca.ubc.gpec.tmadb.util.ViewConstants;

class Whole_section_imagesController {
    
    def show = {
        def whole_section_imagesInstance = Whole_section_images.get(params.id)
        if (!whole_section_imagesInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'whole_section_images.label', default: 'Whole_section_images'), params.id])}"
            redirect(url: grailsApplication.config.grails.serverSecureURL); // go back to home
        }
        else {
            [
                whole_section_imagesInstance: whole_section_imagesInstance,
                whole_section_slicesInstance: whole_section_imagesInstance.getWhole_section_slice(),
                oddEvenRowFlag: new OddEvenRowFlag(ViewConstants.CSS_CLASS_NAME_ROW_ODD,ViewConstants.CSS_CLASS_NAME_ROW_EVEN)
            ]
        }
    }

    
}
