package com.atguigu.flink0710.day03;

import com.atguigu.flink0710.beans.WaterSensor;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * 转换算子--filter
 * 将数据流中传感器id为 sensor_1的数据过滤出来
 */
public class Flink08_transform_filter {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStreamSource<WaterSensor> wsDS = env.fromElements(
                new WaterSensor("s1", 1L, 10),
                new WaterSensor("s2", 1L, 20),
                new WaterSensor("s3", 1L, 30),
                new WaterSensor("s1", 1L, 40)
        );

        wsDS.filter(
                ws -> "s1".equals(ws.id)
        ).print();

        env.execute();

    }
}
