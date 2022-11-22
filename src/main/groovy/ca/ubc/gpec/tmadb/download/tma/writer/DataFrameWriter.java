package ca.ubc.gpec.tmadb.download.tma.writer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

import ca.ubc.gpec.tmadb.download.tma.model.DataFrame;
import ca.ubc.gpec.tmadb.download.tma.model.DataFrameReadException;

/**
 * responsible for writing the content of a DataFrame object to output stream
 *
 * @author samuelc
 *
 */
public class DataFrameWriter {

    BufferedWriter bw;
    DataFrame dataFrame;
    ArrayList<String> desiredVariableNames;

    public DataFrameWriter(BufferedWriter bw, DataFrame dataFrame) {
        this.bw = bw;
        this.dataFrame = dataFrame;
        desiredVariableNames = null;
    }

    /**
     * close the BufferedWriter
     */
    public void close() throws IOException {
        bw.close();
    }

    /**
     * set the desired variable names - this is useful if one wants to output
     * variables in his/her desired order
     *
     * @param desiredVariableNames
     */
    public void setDesiredVariableNames(ArrayList<String> desiredVariableNames) {
        this.desiredVariableNames = desiredVariableNames;
    }

    /**
     * write content of dataFrame as delimited plain text
     *
     * @param delimited
     */
    public void writeDelimitedText(String delimiter) throws IOException, DataFrameReadException {

        ArrayList<String> variableNames = null;
        if (desiredVariableNames == null) {
            variableNames = dataFrame.getVariableNames();
        } else {
            variableNames = desiredVariableNames;
        }
        int[] multiCoreNums = dataFrame.getMultiCoreNums(variableNames);

        // writer header line ...
        bw.write(dataFrame.getIdName());
        int variableCount = 0;
        for (String variableName : variableNames) {
            for (int i = 0; i < multiCoreNums[variableCount]; i++) {
                bw.write(delimiter + variableName);
                if (multiCoreNums[variableCount] > 1) {
                    bw.write(".c" + (i + 1));
                }
            }
            variableCount++;
        }
        bw.write("\n");


        for (int i = 0; i < dataFrame.getNumOfCases(); i++) {
            // write each line ...

            // write case/core id
            String idValue = dataFrame.getIdValue(i);
            bw.write(idValue);

            bw.write(delimiter);

            // get values from dataFrame
            variableCount = 0;
            int numVariables = variableNames.size();
            for (String variableName : variableNames) {
                ArrayList<String> values = dataFrame.getValues(idValue, variableName);
                int valueCount = 0;
                for (String value : values) {
                    bw.write(value);
                    valueCount++;
                    if (valueCount < multiCoreNums[variableCount]) {
                        bw.write(delimiter);
                    }
                }
                while (valueCount < multiCoreNums[variableCount]) {
                    valueCount++;
                    if (valueCount < multiCoreNums[variableCount]) {
                        bw.write(delimiter); // add some column in case some cases do not have same multiCoreNum
                    }
                }
                variableCount++;
                if (variableCount < numVariables) {
                    bw.write(delimiter); 
                }
            }
            bw.write("\n"); // newline character
        }
    }
}
