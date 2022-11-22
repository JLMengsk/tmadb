package ca.ubc.gpec.tmadb.util;

/**
 * implement some custom compare rules ...
 * @author samuelc
 *
 */

public class CustomCompare {

	/**
	 * check if a is numeric (int) type
	 * @param a
	 */
	public static boolean isInt(String a) {
		try {
			Integer.parseInt(a);
		} catch (NumberFormatException nfe) {
			return(false);
		}
		return(true);
	}
	
	/**
	 * compare string a b
	 * @param a
	 * @param b
	 * @return if (a<b){return(-1)}elseif(a>b){return(1)}else{return(0)}
	 *    - numeric comes BEFORE non-numeric
	 */
	public static int compareNumBeforeNonNum(String a, String b) {
		
		boolean aIsInt = isInt(a);
		boolean bIsInt = isInt(b);
		
		if (aIsInt & bIsInt) {
			int aInt = Integer.parseInt(a);
			int bInt = Integer.parseInt(b);
			
			if (aInt < bInt) {
				return -1;
			} else if (aInt > bInt) {
				return 1;
			} else {
				return 0;
			}
		} else if ((!aIsInt) & (!bIsInt)) {
			return a.compareTo(b);
		} else {
			if (aIsInt) {
				return -1; 
			} else {
				return 1;
			}
		}
		
	}
}
