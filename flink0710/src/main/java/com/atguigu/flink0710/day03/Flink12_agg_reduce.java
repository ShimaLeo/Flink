package com.atguigu.flink0710.day03;

import com.atguigu.flink0710.beans.WaterSensor;
import com.atguigu.flink0710.func.WaterSensorMapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * 使用reduce进行max
 *
 * 总结：如果流中只有一条元素，那么reduce方法不会执行，直接将数据向下游传递
 *      reduce（value1，value2）
 *          value1：归约的结果
 *          value2：流中归来的元素
 */
public class Flink12_agg_reduce {
    public static void main(String[] args) throws Exception {
        //准备环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //从指定的网络端口读取数据

        env
            .socketTextStream("hadoop102",8888)
            .map(new WaterSensorMapFunction())
                .keyBy(WaterSensor::getId)
                .reduce(
                        new ReduceFunction<WaterSensor>() {
                            @Override
                            public WaterSensor reduce(WaterSensor value1, WaterSensor value2) throws Exception {
                                System.out.println("value1" + value1);
                                System.out.println("value2" + value2);
                                if (value2.vc > value1.vc){
                                    value1.vc = value2.vc;

                                }
                                return value1;
                            }
                        }
                ).print();

        env.execute();

    }
}
