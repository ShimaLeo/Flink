package com.atguigu.flink0710.day03;

import com.atguigu.flink0710.beans.WaterSensor;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * keyby操作以及聚合
 * keyby的作用：是对数据进行分组
 * keyby不算是一个转换算子，只是一个分组操作
 * flink提供了一些聚合算子
 */
public class Flink11_keyby {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStreamSource<WaterSensor> wsDS = env.fromElements(
                new WaterSensor("sensor_1", 1L, 10),
                new WaterSensor("sensor_2", 2L, 20),
                new WaterSensor("sensor_3", 3L, 30),
                new WaterSensor("sensor_4", 4L, 40)
        );

        KeyedStream<WaterSensor, String> keyedDS
                = wsDS.keyBy(WaterSensor::getId);
        //keyedDS.print();

        //keyedDS.max("vc").print();
        keyedDS.maxBy("vc").print();

        env.execute();
    }
}
