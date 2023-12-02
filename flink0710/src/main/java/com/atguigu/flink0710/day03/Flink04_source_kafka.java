package com.atguigu.flink0710.day03;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * Flink提供的kafka连接器KafkaSource底层保证了消费的精准一次
 *
 * KafkaSource->
 */
public class Flink04_source_kafka {
    public static void main(String[] args) throws Exception {
        //TODO 1. 指定流处理环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //TODO 2. 从Kafka主题中读取数据
        KafkaSource<String> kafkaSource = KafkaSource.<String>builder()
                .setBootstrapServers("hadoop102:9092,hadoop103:9092,hadoop104:9092")
                .setTopics("first")
                .setGroupId("testGroup")
                .setStartingOffsets(OffsetsInitializer.latest())
                .setValueOnlyDeserializer(new SimpleStringSchema())
                .build();
        DataStreamSource<String> kafkaDS
                = env.fromSource(kafkaSource, WatermarkStrategy.noWatermarks(), "kafka_source");
        //TODO 3. 打印
        kafkaDS.print();
        //TODO 4. 提交
        env.execute();
    }

}
