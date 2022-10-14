package ca.ubc.gpec.tmadb

class Tma_blocksTagLib {
    /**
     * display the sector map for a tma slice in a html table
     * @param id of Tma_block
     * @param mode = tma_block_view, tma_core_nav
     * @param selectedTma_core
     */
    def displayTma_blockSectorMapInHtmlTable = { attr, body ->
        Tma_blocks tma_blocksInstance = Tma_blocks.get(attr['id']);
        int col_gap = tma_blocksInstance.showCol_gap(); // -1 for no col_gap specified
        int row_gap = tma_blocksInstance.showRow_gap(); // -1 for no row_gap specified
        int maxCol = tma_blocksInstance.showMaxCol();
        int maxRow = tma_blocksInstance.showMaxRow();
        boolean row_gapSpecified = row_gap != Integer.MIN_VALUE;
        boolean col_gapSpecified = col_gap != Integer.MIN_VALUE;
        String mode = attr['mode'];
        String cssTableClass = "sector_map";
        Tma_cores selectedTma_core = null;
        if (mode=="tma_core_nav") {
            selectedTma_core = attr['selectedTma_core'];
            cssTableClass = "sector_map_nav";
        }   
        out << "<table>";
        out << "<tr><th></th>";
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
        for (Tma_cores t:tma_blocksInstance.getTma_cores()) {
            int currRow = t.getRow();
            int currCol = t.getCol();
            if (currRow > prevRow) {
                while ((currRow - prevRow) > 1) {
                    prevRow++;
                    out << "<tr><th class='"+cssTableClass+"'><b>"+prevRow+"</b></th><td class='"+cssTableClass+"'></td><td class='"+cssTableClass+"'></td></tr>";
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
                out << "<tr><th class='"+cssTableClass+"'><b>"+currRow+"</b></th>";
                prevRow = currRow;
                prevCol = 0;
            }
            while ((currCol - prevCol) > 1){
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
            if (mode=="tma_core_nav") {
                // display image
                if (t.getId() == selectedTma_core.getId()) {
                    out << "<img src='"+resource(dir:'images',file:'tma_core_selected.png')+"'/>";
                } else {
                    out << "<a href='"+createLink(controller:"tma_cores", action:"show", id:t.getId())+"' title='row "+currRow+", col "+currCol+"'>";
                    out << "<img src='"+resource(dir:'images',file:'tma_core_not_selected.png')+"'/>";
                    out << "</a>";
                }
            } else if (mode=="tma_block_view") {
                // see if there are any keyword to display 
                Keywords keyword = t.showFirstKeyword();
                out << "<a href='"+createLink(controller:"tma_cores", action:"show", id:t.getId())+"' title='row "+currRow+", col "+currCol+(keyword==null?"":(keyword.description==null?"":("\n"+keyword.description)))+"'>";
                out << t.getCore_id();
                if (keyword != null) {
                    out << "<br>"+keyword
                }
                out << "</a>";
            }
            out << "</td>";
        }
        out << "</table>";
    }
}
