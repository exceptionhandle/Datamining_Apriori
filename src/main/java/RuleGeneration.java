import java.util.*;

/**
 * Created by abhaysastry on 10/12/16.
 */
public class RuleGeneration {

    private String sample_rule = "RULE HAS (1) OF (gene_59_UP,gene_96_Down)";
    private Map<Integer, List<Set<String>>> frequentItemsetMap;

    public RuleGeneration() {
        AprioriImpl apriori = new AprioriImpl(70);
        frequentItemsetMap = apriori.generateFrequentItemSets();
    }

    private void parseTemplate(String template) {

        Set<String> orSet = new HashSet<String>(Arrays.asList(template.split(" OR ")));
        Set<String> finalResultSet = new HashSet<String>();
        for (String orStatement : orSet) {
            Set<String> andResult = new HashSet<String>();
            Set<String> andSet = new HashSet<String>(Arrays.asList(orStatement.split(" AND ")));
            boolean initialFlag = true;
            for (String andStatement : andSet) {
                Set<String> queryResult = processQuery(andStatement);
                if (andResult.isEmpty() && initialFlag == true) {
                    initialFlag = false;
                    andResult.addAll(queryResult);
                } else {
                    andResult.retainAll(queryResult);
                }

            }
            finalResultSet.addAll(andResult);
        }
    }

    private Set<String> processQuery(String query) {

        String[] parsedString = query.split(" ");

        if (parsedString.length == 5) {
            return processTemplate1(parsedString[0], parsedString[4], parsedString[4]);
        } else {
            return processTemplate2(parsedString[0], parsedString[2]);
        }
    }

    private Set<String> processTemplate1(String rule, String amount, String items) {
        return null;

    }

    private Set<String> processTemplate2(String rule, String value) {
        return null;
    }


}
