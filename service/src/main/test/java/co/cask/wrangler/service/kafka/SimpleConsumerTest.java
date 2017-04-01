package co.cask.wrangler.service.kafka;

import org.junit.Assert;
import org.junit.Test;
import parquet.Closeables;

import java.util.Set;

/**
 * Created by nitin on 3/31/17.
 */
public class SimpleConsumerTest {
  @Test
  public void testKafkaConnection() throws Exception {
    SimpleConsumer client = new SimpleConsumer("localhost:9098");
    Set<String> topics = client.getTopics();
    Closeables.closeAndSwallowIOExceptions(client);
    Assert.assertTrue(true);
  }

}