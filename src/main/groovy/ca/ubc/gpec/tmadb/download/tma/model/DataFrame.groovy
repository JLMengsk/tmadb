package ca.ubc.gpec.tmadb.download.tma.model

import java.util.ArrayList
import java.util.Hashtable

/**
 * internal data structure containing the data to export
 * 
 * dataFrame is like a matrix with 
 * - column number = variable index
 * - row number = case index
 * 
 * @author samuelc
 *
 */
class DataFrame {

	// instance variables
	// - the key to the hashtables is the variable name
	private Hashtable<String, String> variableDescriptions;
	private Hashtable<String, ArrayList<CaseValues>> dataMatrix;

	// special case for id variable
	private String idName;
	private ArrayList<String> idValues; 

	/**
	 * constructor
	 */
	public DataFrame() {
		variableDescriptions = new Hashtable<String, String>();
		dataMatrix = new Hashtable<String, ArrayList<CaseValues>>();
		idValues = new ArrayList<String>();
	}

	//////////////////////////////////////////////////
	// input methods
	//////////////////////////////////////////////////
	
	/**
	 * set idName
	 * @param idName
	 */
	public void setIdName(String idName) {
		this.idName = idName;
	}
	
	/**
	 * add variable
	 * - no value/scores yet!!!
	 * @param variableDescription
	 * @param variableName
	 * @throws DataFrameWriteException
	 */
	public void addVariable(String variableName, String variableDescription) throws DataFrameWriteException {

		// check to see if variableName exist
		if (dataMatrix.containsKey(variableName)) {
			throw new DataFrameWriteException("trying to add "+variableName+" ... variable already exist in data frame object !!!")
		}

		// add description
		variableDescriptions.put(variableName, variableDescription);

		// add variable name and initialize the ArrayList
		ArrayList<CaseValues> variable = new ArrayList<CaseValues>();
		for (int i=0; i<getNumOfCases(); i++) {
			variable.add(new CaseValues());
		}
		dataMatrix.put(variableName, variable);
	}

	/**
	 * add one case to this data frame
	 * WARNING ... one needs to add ALL cases FIRST before doing any addVariable()!!!
	 * @param variableName
	 * @param numCases
	 * @throws DataFrameWriteException
	 */
	public void addCase(String idValue) throws DataFrameWriteException {
		idValues.add(idValue);
	}
			
	/**
	* add a case value in the variable specified by variableName at a particular index
	* @param variableName
	* @param index
	* @param values
	*/
   public void addValueAtIndex(String variableName, int index, String value) throws DataFrameWriteException {
	   // check to see if variableName exist
	   if (!dataMatrix.containsKey(variableName)) {
		   throw new DataFrameWriteException("trying to add values to non-existing variable: "+variableName+" !!!")
	   }

	   // check to see if index exist
	   ArrayList<CaseValues> variable = dataMatrix.get(variableName);

	   if (variable.size() <= index) {
		   throw new DataFrameWriteException("trying to add values to variable at out-of-bound index: "+variableName+"@"+index+" !!!")
	   }

	   CaseValues caseValues = variable.get(index);

	   caseValues.addValue(value);
   }
	
	//////////////////////////////////////////////////
	// read methods
	//////////////////////////////////////////////////

   /**
    * get idName
    * @return
    */
   public String getIdName() {return idName;}
   
	/**
	 * return the number of variables represented by this data frame
	 * - all variables must have a description (a description can be "")
	 * @return
	 */
	public int getNumVariables() {
		return variableDescriptions.size();
	}

	/**
	 * return variable names
	 * @return
	 */
	public ArrayList<String> getVariableNames() {
		Enumeration<String> variableNamesEnum = dataMatrix.keys();

		ArrayList<String> variableNames = new ArrayList<String>();
		while(variableNamesEnum.hasMoreElements()) {
			variableNames.add(variableNamesEnum.nextElement());
		}

		return variableNames;
	}

	/**
	 * get number of cases 
	 * @param idName
	 * @return
	 */
	public int getNumOfCases() {
		return idValues.size()
	}
	
	
	/**
	 * get index of id
	 * @param idValue
	 * @return
	 */
	public int getIndex(String idValue) throws DataFrameReadException {
		return idValues.indexOf(idValue);
	}

	/**
	 * get idValue given the index
	 * @param index
	 * @return
	 * @throws DataFrameReadException
	 */
	public String getIdValue(int index) throws DataFrameReadException {
		if (index >= idValues.size()) {
			throw new DataFrameReadException("IndexOutOfBoundsException encounter when trying to find idValue at index: "+index);
		}
		return idValues.get(index);
	}
	
	/**
	 * return values from variables
	 * 
	 * NOTE: the return values in the ArrayList is as follows ...
	 * v1.c1, v1.c2, v1.c3 ... v2.c1, v2.c2, v2.c3 ...
	 * 
	 * v1.c1 = variable1, core #1
	 * 
	 * So, the order in variableName is IMPORTANT!!!!
	 * 
	 * @param idValue
	 * @param variableNames
	 * @return
	 * @throws DataFrameReadException
	 */
	public ArrayList<String> getValues(String idValue,
		ArrayList<String> variableNames) throws DataFrameReadException {

		// find index
		int index = getIndex(idValue);

		// find variable values ...
		ArrayList<String> values = new ArrayList<String>();

		for (String variableName:variableNames) {
			ArrayList<CaseValues> variable = dataMatrix.get(variableName);
			if (variable == null) {
				throw new DataFrameReadException("variable ("+variableName+") not found!!!");
			}
			ArrayList<String> valuesFromEachVariable = variable.get(index).getValues();
			for (String valueFromEachVariable : valuesFromEachVariable) {
				values.add(valueFromEachVariable);
			}	
		}
		return values;
	}
	
	/**
	 * convenience method if you only want values from one variable.
	 * 
	 * this method is not efficient if you have more than one variable as it creates
	 * an ArrayList for each variable
	 * 	
	 * @param idValue
	 * @param variableName
	 * @return
	 * @throws DataFrameReadException
	 */
	public ArrayList<String> getValues(String idValue, String variableName) throws DataFrameReadException { 	
		ArrayList<String> variableNames = new ArrayList<String>();
		variableNames.add(variableName);
		return getValues(idValue, variableNames);
	}

	/**
	 * get the MAXIMUM number of multiplicate core in the whole data frame
	 * @return
	 */
	public int getMaxMultiCoreNum() {
		
		int multiCoreNum = 1; // start with single core
		
		Enumeration<String> variableNamesEnum = dataMatrix.keys();
		while (variableNamesEnum.hasMoreElements()) {
			String variableName = variableNamesEnum.nextElement();

			for (CaseValues caseValue : dataMatrix.get(variableName)) {
				int numOfValues = caseValue.getNumberOfValues();
				if (numOfValues > multiCoreNum) {
					multiCoreNum = numOfValues;
				}
			}
		}
		
		return multiCoreNum;
	}
	
	/**
	* get the number of multiplicate core in the whole data frame
	* for each input variable
	* @return
	*/
   public int[] getMultiCoreNums(ArrayList<String> variableNames) {
	   int[] multiCoreNums = new int[variableNames.size()];	   
	   int counter = 0;
	   for (String variableName : variableNames) {
		   int multiCoreNum = 1;
		   for (CaseValues caseValue : dataMatrix.get(variableName)) {
			   int numOfValues = caseValue.getNumberOfValues();
			   if (numOfValues > multiCoreNum) {
				   multiCoreNum = numOfValues;
			   }
		   }
		   multiCoreNums[counter] = multiCoreNum;
		   counter++;
	   }
	   return multiCoreNums;
   }
	
	/**
	 * print String representation of this data frame ...
	 */
	public String toString() {
		String result = "# of variables in this data frame ..." + getNumVariables()+"\n";

		Enumeration<String> variableNamesEnum = dataMatrix.keys();
		while (variableNamesEnum.hasMoreElements()) {
			String variableName = variableNamesEnum.nextElement();

			result = result+variableName+"("+variableDescriptions.get(variableName)+") ... \n";

			for (CaseValues caseValue : dataMatrix.get(variableName)) {
				result = result + caseValue+",";
			}
			result = result+"\n";
		}

		return result;
	}
}
