package com.atguigu.flink0710.day03;

import com.atguigu.flink0710.beans.WaterSensor;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

public class Flink09_tranform_flatmap {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStreamSource<WaterSensor> wsDS = env.fromElements(
                new WaterSensor("sensor_1", 1L, 1),
                new WaterSensor("sensor_1", 2L, 2),
                new WaterSensor("sensor_2", 2L, 2),
                new WaterSensor("sensor_3", 3L, 3)
        );

        wsDS.flatMap(
                new FlatMapFunction<WaterSensor, String>() {

                    @Override
                    public void flatMap(WaterSensor ws, Collector<String> collector) throws Exception {
                        if("sensor_1".equals(ws.id)){
                            collector.collect(ws.id + ":" + ws.vc);

                        }else if ("sensor_2".equals(ws.id)){
                            collector.collect(ws.id + ":" + ws.vc);
                            collector.collect(ws.id + ":" + ws.ts);
                        }
                    }
                }
        ).print();

        env.execute();


    }
}
