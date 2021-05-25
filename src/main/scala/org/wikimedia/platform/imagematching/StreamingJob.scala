package org.wikimedia.platform.imagematching

import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.scala._
import java.util.Properties
import io.circe.generic.auto._
import io.circe.parser._
import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer
import org.apache.flink.api.common.typeinfo.TypeInformation
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows
import org.slf4j.LoggerFactory


case class RevisionCounter(database: String, count: Int)

class StreamingJob

object StreamingJob {
  private val Log = LoggerFactory.getLogger(classOf[StreamingJob])
  private val kafkaTopic = "eqiad.mediawiki.revision-create"
  private val kafkaBootstrapServers = "kafka-jumbo1001.eqiad.wmnet:9092"
  private val kafkaGroupId = "ima-revision-create-test"

  def main(args: Array[String]): Unit = {
    import org.apache.flink.streaming.api.scala._

    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val properties = new Properties();
    properties.setProperty("bootstrap.servers", kafkaBootstrapServers);
    properties.setProperty("group.id", kafkaGroupId);

    val schema = new SimpleStringSchema()
    val consumer: FlinkKafkaConsumer[String] = new FlinkKafkaConsumer[String](kafkaTopic, schema, properties)

    val revisionStream: DataStream[String] = env.addSource(consumer)

    revisionStream.flatMap({message: String => {
      decode[RevisionCreate](message).fold(
          error => {
            Log.error { error.toString }
            Log.error { message }
            None
          },
          revision => Option(revision)
        )}
      }).map((revision: RevisionCreate) => RevisionCounter(database=revision.database, count=1))
      .keyBy(_.database) // database
      .window(TumblingProcessingTimeWindows.of(Time.seconds(15)))
      .sum("count")
      .keyBy(_.database) // database
      .asQueryableState("revision-count-state")

    env.execute("IMA echo revision")
  }
}