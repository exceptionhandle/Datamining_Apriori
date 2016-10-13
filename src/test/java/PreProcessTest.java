import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by abhaysastry on 10/11/16.
 */

public class PreProcessTest {

    @Test
    public void testFileRead() throws IOException {
        PreProcess preProcess = new PreProcess();
        preProcess.appendHeadersToGene();
    }

    @Test
    public void testList() throws IOException {
        PreProcess preProcess = new PreProcess();
        Assert.assertEquals(preProcess.geneExpr().size(),100);
    }
}
