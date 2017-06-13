package co.cask.wrangler.sampling;

import co.cask.wrangler.BoundedLineInputStream;
import com.google.common.base.Charsets;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;

/**
 * Class description here.
 */
public class SamplerTest {

  @Test
  public void testPoissonSampler() throws Exception {
    int lines = 10;
    double fraction = 0.55;
    try (InputStream is = new FileInputStream("/Users/nitin/Work/Demo/data/titanic.csv")) {
      BoundedLineInputStream blis = BoundedLineInputStream.iterator(
        is,
        Charsets.UTF_8,
        (int)(lines + lines*(1-fraction))
      );
      Iterator<String> it = new Poisson<String>(fraction).sample(blis);
      while(it.hasNext()) {
        System.out.println(it.next());
      }
    }
  }

}