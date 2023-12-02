package com.atguigu.flink0710.day03;

import com.atguigu.flink0710.beans.WaterSensor;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**\
 * 转换算子--map
 * 需求：提取流中WaterSensor中的id字段的功能
 */
public class Flink07_transform_map {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStreamSource<WaterSensor> wsDS = env.fromElements(
                new WaterSensor("sensor_1", 1L, 1),
                new WaterSensor("sensor_2", 2L, 2)
        );

        //匿名内部类方式 创建MapFunction的实现
        /*wsDS.map(
                new MapFunction<WaterSensor, Object>() {
                    @Override
                    public Object map(WaterSensor ws) throws Exception {

                        return ws.id;
                    }
                }
        ).print();*/

        //wsDS.map(ws -> ws.id).print();

        //方法引用
        //wsDS.map(WaterSensor::getId).print();

        //抽取专门的类
        wsDS.map(new MyMapFunction());



        env.execute();
    }



      public static class  MyMapFunction  implements MapFunction<WaterSensor,String>{

        @Override
        public String map(WaterSensor ws) throws Exception {
            return ws.id;
        }
    }
}






