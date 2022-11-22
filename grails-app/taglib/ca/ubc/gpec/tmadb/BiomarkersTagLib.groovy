package ca.ubc.gpec.tmadb

class BiomarkersTagLib {
    /**
     * display the biomarker related actions available to the logged in user 
     * ONLY SHOW ONE OPTION!!!
     * 
     * @attr id of the disorder object 
     * 
     */
    def displayAvailableBiomarkerActions = { attr, body ->
        if (!attr.containsKey("id")) {
            // show options available when not looking at a specific biomarker
            if (Biomarkers.showCanCreate(session)) {
                out <<"<button "
                out <<"dojoType='dijit.form.Button' "
                out <<"type='submit' " 
                out <<"name='submitButton' " 
                out <<"title='click me to create biomarker record' "
                out <<"onclick=window.location='"+createLink(controller:'biomarkers', action:'create')+"' "
                out <<">+</button>"
            }
        } else {
            long id = attr['id'];
            Biomarkers biomarker = Biomarkers.get(id);
            // NOTE: the order of the if / if else is VERY important !!!!
            //       want to show the most relevant available actions!!!
            if (biomarker?.showCanEdit(session)) {
                if (actionName=="edit") {
                    if (biomarker?.showCanDelete(session)) {
                        out <<"<button "
                        out <<"dojoType='dijit.form.Button' "
                        out <<"type='delete' " 
                        out <<"name='submitButton' " 
                        out <<"title='click me to delete biomarker' "
                        out <<"onclick=\"confirm('Delete biomarker: "+biomarker.getName()+"?') ? window.location='"+createLink(controller:'biomarkers', action:'delete', id:id)+"' : false\""
                        out <<">-</button>"
                    }
                } else {
                    out <<"<button "
                    out <<"dojoType='dijit.form.Button' "
                    out <<"type='submit' " 
                    out <<"name='submitButton' " 
                    out <<"title='click me to edit (or delete) biomarker name/description' "
                    out <<"onclick=window.location='"+createLink(controller:'biomarkers', action:'edit', id:id)+"' "
                    out <<">edit</button>"
                }
            }
        }
    }
    
}
