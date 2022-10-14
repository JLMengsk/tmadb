package ca.ubc.gpec.tmadb

//import java.util.concurrent.ConcurrentHashMap

class Tma_slicesTagLib {
    /**
     * display the sector map for a tma slice in a html table
     * @param id of Tma_slice
     * @param mode = tma_slice_view, tma_core_image_nav
     * @param tma_scorer1
     * @param tma_scorer2
     * @param tma_scorer3
     * @param scoring_date
     * @param scoreType
     * @param tma_result_rep // a representative tma_result record
     * @param selectedTma_core_image
     */
    def displayTma_sliceSectorMapInHtmlTable = { attr, body ->
        Tma_slices tma_slicesInstance = Tma_slices.get(attr['id']);
        Tma_blocks tma_blocksInstance = tma_slicesInstance.getTma_block();
        int col_gap = tma_blocksInstance.showCol_gap(); // -1 for no col_gap specified
        int row_gap = tma_blocksInstance.showRow_gap(); // -1 for no row_gap specified
        int maxCol = tma_blocksInstance.showMaxCol();
        int maxRow = tma_blocksInstance.showMaxRow();
        boolean row_gapSpecified = row_gap != Integer.MIN_VALUE;
        boolean col_gapSpecified = col_gap != Integer.MIN_VALUE;
        String mode = attr['mode'];
        Tma_scorers tma_scorer1 = null; 
        Tma_scorers tma_scorer2 = null; 
        Tma_scorers tma_scorer3 = null; 
        Date scoring_date = null; 
        def scoreType = null;
        Tma_results tma_result_rep = attr['tma_result_rep']; // may be null!!!
        Tma_result_names tma_result_name=null;
        Tma_core_images selectedTma_core_image = null;
        String cssTableClass = "sector_map";
        if (mode=="tma_slice_view") {
            tma_scorer1 = attr['tma_scorer1']; 
            tma_scorer2 = attr['tma_scorer2']; 
            tma_scorer3 = attr['tma_scorer3']; 
            scoring_date = attr['scoring_date']; 
            scoreType = attr['scoreType'];
            if (tma_result_rep != null) {
                tma_result_name = tma_result_rep.tma_result_name;
            }
        } else if (mode=="tma_core_image_nav") {
            selectedTma_core_image = attr['selectedTma_core_image'];
            cssTableClass = "sector_map_nav";
        }
        out << "<table>";
        out << "<tr>";
        out << "<th></th>"
        for (int i=1; i<=maxCol; i++) {
            out << "<th class='"+cssTableClass+"'><b>"+i+"</b></th>";
            if (col_gapSpecified && i > 0 && (i % Math.abs(col_gap) == 0) && !(col_gap < 0 && i > Math.abs(col_gap))) {
                // extra col for col gap ... since this is a col gap DO NOT increment prevCol!!!
                out << "<th class='"+cssTableClass+"'>&nbsp;</th>";
            }
        }
        out << "</tr>";
        int prevRow = 0;
        int prevCol = 0;
        
        boolean noScore = (tma_scorer1 == null & tma_scorer2 == null & tma_scorer3 == null)
        def tma_core_images = tma_slicesInstance.getTma_core_images()
        
        HashMap<Integer,HashMap> defaultScoreNameLookups = null
        HashMap<Integer, String> defaultScoreNameLookup = null
        boolean cached=true
        if (mode=="tma_slice_view" & !noScore) {
            defaultScoreNameLookups = session["defaultScoreNameLookups"]
            if (defaultScoreNameLookups == null) { 
                defaultScoreNameLookups = new HashMap<Integer,HashMap>()
                session["defaultScoreNameLookups"] = defaultScoreNameLookups
            }
            defaultScoreNameLookup = defaultScoreNameLookups.get(tma_result_name.getId())
            if (defaultScoreNameLookup == null || tma_result_name.showIsDirty()) {
                cached=false
                defaultScoreNameLookup = new HashMap<Integer, String>()
                defaultScoreNameLookups.put(tma_result_name.getId(), defaultScoreNameLookup)
                // batch up the queries to get default score names... hope will make things faster
                (Tma_result_default_score_name_view.withCriteria {
                        eq("tma_result_name",tma_result_name)
                    }).each {
                    defaultScoreNameLookup.put(it.tma_core_image.getId(), it.default_score_name);
                }
            }
        }
        
        for (Tma_core_images t:tma_core_images) {
            int currRow = t.getTma_core().getRow();
            int currCol = t.getTma_core().getCol();
            if (currRow > prevRow) {
                while ((currRow - prevRow) > 1) {
                    prevRow++;
                    out << "<tr>";
                    out << "<th class='"+cssTableClass+"'><b>"+prevRow+"</b>";
                    out << "</th>";
                    out << "<td class='"+cssTableClass+"'></td>";
                    out << "</tr>";
                }
                if (row_gapSpecified && prevRow > 0 && (prevRow % Math.abs(row_gap) == 0) && prevRow!=maxRow) {
                    // extra row for row gap ... since this is a row gap DO NOT increment prevRow!!!
                    if (row_gap < 0) {
                        // -ve row_gap means do it once only
                        row_gapSpecified=false;
                    }
                    out << "<tr>";
                    out << "<th class='"+cssTableClass+"'>&nbsp;</th>";
                    out << "<td class='"+cssTableClass+"' colspan='"+maxCol+"'>&nbsp;</td>";
                    out << "</tr>";
                }
                out << "<tr>";
                out << "<th class='"+cssTableClass+"'><b>"+currRow+"</b></th>";
                prevRow = currRow;
                prevCol = 0;
            }
            while ((currCol - prevCol) > 1) {
                if (col_gapSpecified && prevCol > 0 && (prevCol % Math.abs(col_gap) == 0) && !(col_gap < 0 && prevCol > Math.abs(col_gap))) {
                    // extra col for col gap ... since this is a col gap DO NOT increment prevCol!!!
                    out << "<td class='"+cssTableClass+"'>&nbsp;</td>";
                }
                out << "<td class='"+cssTableClass+"'></td>";
                prevCol++;
            }
            if (col_gapSpecified && prevCol > 0 && (prevCol % Math.abs(col_gap) == 0) && !(col_gap < 0 && prevCol > Math.abs(col_gap))) {
                // extra col for col gap ... since this is a col gap DO NOT increment prevCol!!!
                out << "<td class='"+cssTableClass+"'>&nbsp;</td>";
            }
            prevCol = currCol;
            out << "<td class='"+cssTableClass+"'>";
            if (mode=="tma_slice_view") {
                Tma_cores tma_core = t.getTma_core();
                // see if there are any keyword to display 
                Keywords keyword = tma_core.showFirstKeyword();
                if (noScore) {
                    // core id only
                    if (keyword != null) {
                        out<<"<a href='"+createLink(controller:"tma_core_images",action:"show",id:t.getId())+"' title='row "+currRow+", col "+currCol+(keyword.description==null?"":("\n"+keyword.description))+"'>"+tma_core.getCore_id()+"<br>"+keyword+"</a>";
                    } else {
                        out<<"<a href='"+createLink(controller:"tma_core_images",action:"show",id:t.getId())+"' title='row "+currRow+", col "+currCol+"'>"+tma_core.getCore_id()+"</a>";
                    }
                } else {
                    // score name
                    out << "<a href='"+createLink(controller: "tma_core_images", action:"show", params:[id:t.getId(), tma_results_id:tma_result_rep.id])+"' title='row "+currRow+", col "+currCol+", core ID "+t.getTma_core().getCore_id()+(keyword==null?"":(keyword.description==null?"":("\n"+keyword.description)))+"'>";
                    out << defaultScoreNameLookup.get(t.getId())
                    if (keyword != null) {
                        out << "<br>"+keyword
                    }
                    out << "</a>";
                }
            } else {
                if (t.getId() == selectedTma_core_image.getId()) {
                    out << "<img src='"+resource(dir:'images',file:'tma_core_selected.png')+"'/>";
                } else {
                    if (tma_result_rep==null) {
                        out << "<a href='"+createLink(controller: "tma_core_images", action:"show", id:t.getId())+"' title='row "+currRow+", col "+currCol+"'><img src='"+resource(dir:'images',file:'tma_core_not_selected.png')+"'/></a>";
                    } else {
                        out << "<a href='"+createLink(controller: "tma_core_images", action:"show", params:[id:t.getId(), tma_results_id:tma_result_rep.getId()])+"' title='row "+currRow+", col "+currCol+"'><img src='"+resource(dir:'images',file:'tma_core_not_selected.png')+"'/></a>";
                    }
                }
            }
            out << "</td>";
        }
        out << "</table>";
    }
    
    
    /**
     * display a list of other available tma 
     * @param id of Tma_slice
     * @param tma_result_rep // a representative tma_result record
     */
    def displayTma_sliceOtherAvailable = { attr, body ->
        Tma_slices tma_slicesInstance = Tma_slices.get(attr['id']);
        Tma_result_names tma_result_name = attr['tma_result_name']; // may be null!!!
        
        if (session.user == null) {
            out << "" // ALL tma_slice need permission!!!
        } else {
            def otherTma_slices = tma_slicesInstance.getStaining_detail().getAvailableTma_slices(session.user)
            for (Tma_slices otherTma_slice:otherTma_slices) {
                if (tma_slicesInstance?.compareTo(otherTma_slice)!=0) {
                    if (tma_result_name==null) {
                        out << "<a href='"+createLink(controller: "tma_slices", action:"show", id:otherTma_slice.id, title:otherTma_slice.encodeAsHTML())+"'/>"+otherTma_slice.encodeAsHTML()+"</a>";
                    } else {
                        out << "<a href='"+createLink(controller: "tma_slices", action:"show", params:[id:otherTma_slice.id, tma_result_names_id:tma_result_name.getId()], title:otherTma_slice.encodeAsHTML())+"'/>"+otherTma_slice.encodeAsHTML()+"</a>";
                    }
                } else {
                    out << "<strong><i>"+otherTma_slice.encodeAsHTML()+"</i></strong>"
                }
                out << "<br>"
            }
        }
    }
}
