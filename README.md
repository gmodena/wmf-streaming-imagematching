# wmf-streaming-imagematching
A streaming job to experiment with the Kafka `revision` topic.

# Build

Run
```
./gradlew build
```
to create a thin jar, or
```
./graldew shadowJar
```
to create a fat jar with all dependencies.


# Deploy

The `shadowJar` target can be manually copied to a stat machine with:
```bash
scp build/libs/wmf-streaming-imagematching-0.1-SNAPSHOT.jar stat1008.eqiad.wmnet:~/flink-1.13.0/
```

Start a Flink cluster on YARN with
```
export HADOOP_CLASSPATH=`hadoop classpath`
./bin/yarn-session.sh --detached
```

Finally lunch the job with
```bash
./bin/flink run wmf-streaming-imagematching-0.1-SNAPSHOT.jar
```

# Flink on YARN Quickstart

A standalone cluster can be setup locally (on a stat machine atop YARN) with
```
wget https://www.apache.org/dyn/closer.lua/flink/flink-1.13.0/flink-1.13.0-bin-scala_2.12.tgz
tar xvzf flink-1.13.0-bin-scala_2.12.tgz
cd flink-1.13.0 
export HADOOP_CLASSPATH=`hadoop classpath`
./bin/yarn-session.sh --detached
```

For more details see the [project doc](https://ci.apache.org/projects/flink/flink-docs-release-1.13/docs/deployment/resource-providers/standalone/overview/).
The [Flink Web Interface]() will be available at yarn.wikimedia.org under
https://yarn.wikimedia.org/proxy/<applicationId>.

Kerberos authentication is required to access WMF Analytics resources.
The relevant config settings are found in `conf/flink-conf.yaml`:
```properties
security.kerberos.login.use-ticket-cache: true
# security.kerberos.login.keytab:
security.kerberos.login.principal: krbtgt/WIKIMEDIA@WIKIMEDIA
# The configuration below defines which JAAS login contexts
security.kerberos.login.contexts: Client,KafkaClient
```
