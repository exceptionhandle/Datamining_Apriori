import java.io.IOException;
import java.util.*;

/**
 * Created by abhaysastry on 10/11/16.
 */
public class AprioriImpl {

    private double supportCount;
    private PreProcess preProcess;
    private List<Set<String>> data;
    private Map<Integer, List<Set<String>>> frequentSetMap;


    public AprioriImpl(int minSupport) {
        preProcess = new PreProcess();
        try {
            data = preProcess.geneExpr();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Something went wrong while reading/writing the file. The file might nor exist!");
        }
        supportCount = data.size() * minSupport / 100;
        frequentSetMap = new HashMap<Integer, List<Set<String>>>();
    }

    public Map<Integer, List<Set<String>>> generateFrequentItemSets() {

        int currentLevel = 1;
        List<Set<String>> frequentOneItemSet = findOneItemFrequentSets(data);
        frequentSetMap.put(1, frequentOneItemSet);


        while (!frequentSetMap.get(currentLevel).isEmpty()) {

            List<Set<String>> possibleCandidates = generatePossibleCandidateSets(frequentSetMap.get(currentLevel));
            List<Set<String>> frequentKItemSet = findKFrequentSets(possibleCandidates);
            frequentSetMap.put(++currentLevel, frequentKItemSet);
        }

        for (Map.Entry<Integer, List<Set<String>>> itemSet : frequentSetMap.entrySet()) {

            System.out.println(itemSet.getKey() + "---" + itemSet.getValue());
        }
        return frequentSetMap;

    }

    private List<Set<String>> findKFrequentSets(List<Set<String>> possibleCandidates) {

        List<Set<String>> frequentItemSets = new ArrayList<Set<String>>();
        Map<Set<String>, Integer> supportMap = new HashMap<Set<String>, Integer>();

        for (Set<String> transaction : data) {
            for (Set<String> itemset : possibleCandidates) {
                if (transaction.containsAll(itemset)) {
                    if (supportMap.containsKey(itemset)) {
                        supportMap.put(itemset, supportMap.get(itemset) + 1);
                    } else {
                        supportMap.put(itemset, 1);
                    }
                }
            }
        }

        for (Map.Entry<Set<String>, Integer> itemEntry : supportMap.entrySet()) {
            if (itemEntry.getValue() >= supportCount) {
                frequentItemSets.add(itemEntry.getKey());
            }
        }

        return frequentItemSets;
    }

    private List<Set<String>> generatePossibleCandidateSets(List<Set<String>> itemSet) {

        List<List<String>> setToListItemSet = new ArrayList<List<String>>();
        List<Set<String>> candidateSets = new ArrayList<Set<String>>();

        for (Set<String> setOfItems : itemSet) {
            List<String> tempList = new ArrayList<String>(setOfItems);
            Collections.sort(tempList);
            setToListItemSet.add(tempList);
        }

        for (int i = 0; i < setToListItemSet.size(); i++) {
            for (int j = i + 1; j < setToListItemSet.size(); j++) {

                List<String> set1 = setToListItemSet.get(i);
                List<String> set2 = setToListItemSet.get(j);
                Set<String> candidateSet = combineBothSets(set1, set2);
                if (candidateSet != null && candidateSetContainedInTransaction(candidateSet)) {
                    candidateSets.add(candidateSet);
                } else
                    continue;

            }
        }

        return candidateSets;
    }

    private boolean candidateSetContainedInTransaction(Set<String> candidateSet) {
        for (Set<String> transaction : data) {
            if (transaction.containsAll(candidateSet)) {
                return true;
            }
        }
        return false;
    }

    private Set<String> combineBothSets(List<String> set1, List<String> set2) {

        if (set1.size() != set2.size()) {
            return null;
        }

        for (int i = 0; i < set1.size() - 1; i++) {
            if (!set1.get(i).equals(set2.get(i))) {
                return null;
            }
        }

        if (set1.get(set1.size() - 1).equals(set2.get(set1.size() - 1))) {
            return null;
        }

        Set<String> candidateSet = new HashSet<String>();

        for (int i = 0; i < set1.size() - 1; i++) {
            candidateSet.add(set1.get(i));
        }
        candidateSet.add(set1.get(set1.size() - 1));
        candidateSet.add(set2.get(set1.size() - 1));


        return candidateSet;
    }

    private List<Set<String>> findOneItemFrequentSets(List<Set<String>> data) {

        Map<String, Integer> supportCountMap = new HashMap<String, Integer>();
        List<Set<String>> frequentOneItemSet = new ArrayList<Set<String>>();

        for (Set<String> itemSet : data) {
            for (String item : itemSet) {
                if (supportCountMap.containsKey(item)) {
                    supportCountMap.put(item, supportCountMap.get(item) + 1);
                } else {
                    supportCountMap.put(item, 1);
                }

            }
        }

        for (Map.Entry<String, Integer> entry : supportCountMap.entrySet()) {
            if (entry.getValue() >= supportCount) {
                Set<String> frequentItem = new HashSet<String>();
                frequentItem.add(entry.getKey());
                frequentOneItemSet.add(frequentItem);
            }
        }

        return frequentOneItemSet;
    }

}
