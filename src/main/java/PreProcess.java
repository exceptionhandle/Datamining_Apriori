import java.io.*;
import java.util.*;

/**
 * Created by abhaysastry on 10/11/16.
 */
public class PreProcess {

    private String givenFile = "/Users/abhaysastry/Downloads/Datamining_Apriori/src/main/resources/gene_expression.txt";
    private String preProcessedFile = "/Users/abhaysastry/Downloads/Datamining_Apriori/src/main/resources/processed_gene_expression.txt";

    public void appendHeadersToGene() throws IOException {

        String line = "";
        BufferedReader bufferedReader = new BufferedReader(new FileReader(givenFile));
        PrintWriter printWriter = new PrintWriter(new FileWriter(preProcessedFile));

        while ((line = bufferedReader.readLine()) != null) {

            String[] splitString = line.split("\t");

            for (int i = 1; i < splitString.length - 1; i++) {
                splitString[i] = "G" + i + "_" + splitString[i];

            }
            printWriter.println(String.join("\t", splitString));
        }

        bufferedReader.close();
        printWriter.close();
    }

    public List<Set<String>> geneExpr() throws IOException {
        appendHeadersToGene();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(preProcessedFile));
        String line = null;
        List<Set<String>> geneExprList = new ArrayList<Set<String>>();
        while ((line = bufferedReader.readLine()) != null) {

            String[] lineArray = line.split("\t");
            geneExprList.add(new HashSet<String>(Arrays.asList(Arrays.copyOfRange(lineArray, 1, lineArray.length))));

        }
        return geneExprList;
    }

}
