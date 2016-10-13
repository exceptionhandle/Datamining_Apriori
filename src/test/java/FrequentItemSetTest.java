import org.junit.Test;

/**
 * Created by abhaysastry on 10/12/16.
 */
public class FrequentItemSetTest {

    @Test
    public void generateOneFrequentItemSetTest() {
        AprioriImpl apriori = new AprioriImpl(40);
        apriori.generateFrequentItemSets();
    }
}
