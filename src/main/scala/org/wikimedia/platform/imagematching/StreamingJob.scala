package org.wikimedia.platform.imagematching
import org.apache.flink.streaming.api.scala._
import scala.util.Properties

val properties = new Properties();
properties.setProperty("bootstrap.servers", "localhost:9092");
properties.setProperty("group.id", "test");

inputStream = env.addSource (
  new FlinkKafkaConsumer08[String](
    "topic", new SimpleStringSchema(), pro perties))