package co.cask.wrangler.service.kafka;


import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

/**
 * Created by nitin on 3/31/17.
 */
public final class SimpleConsumer implements Closeable {
  private KafkaConsumer<String, String> consumer = null;

  public SimpleConsumer(String broker) {
    Properties properties = new Properties();
    properties.setProperty("bootstrap.servers", broker);
    properties.setProperty("fetch.min.bytes", "50000");
    properties.setProperty("receive.buffer.bytes", "262144");
    properties.setProperty("max.partition.fetch.bytes", "2097152");
    consumer = new KafkaConsumer<>(setProperties(properties));
  }

  public SimpleConsumer(String broker, Properties properties) {
    properties.setProperty("bootstrap.servers", broker);
    if (!properties.contains("fetch.min.bytes")) {
      properties.setProperty("fetch.min.bytes", "50000");
    }
    if(!properties.contains("receive.buffer.bytes")) {
      properties.setProperty("receive.buffer.bytes", "262144");
    }
    if(!properties.contains("max.partition.fetch.bytes")) {
      properties.setProperty("max.partition.fetch.bytes", "2097152");
    }
    consumer = new KafkaConsumer<>(setProperties(properties));
  }

  private Properties setProperties(Properties properties) {
    properties.put("enable.auto.commit", "false");
    properties.put("auto.commit.interval.ms", "1000");
    properties.put("session.timeout.ms", "10000");
    properties.put("key.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer");
    properties.put("value.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer");
    properties.setProperty("group.id", "group-" + new Random().nextInt(100000));
    return properties;
  }

  /**
   * @return Set of Kafka topics that requesting user has access to.
   */
  public Set<String> getTopics() {
    Map<String, List<PartitionInfo>> topics = consumer.listTopics();
    return topics.keySet();
  }

  /**
   * Closes this stream and releases any system resources associated
   * with it. If the stream is already closed then invoking this
   * method has no effect.
   *
   * @throws IOException if an I/O error occurs
   */
  @Override
  public void close() throws IOException {
    if (consumer != null) {
      consumer.close();
    }
  }
}
