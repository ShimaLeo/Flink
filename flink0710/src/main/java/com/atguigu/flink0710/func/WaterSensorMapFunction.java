package com.atguigu.flink0710.func;

import com.atguigu.flink0710.beans.WaterSensor;
import org.apache.flink.api.common.functions.MapFunction;

/**
 * 将读取到的一行数据  转换为WaterSensor对象
 */
public class WaterSensorMapFunction implements MapFunction<String, WaterSensor> {
    @Override
    public WaterSensor map(String linestr) throws Exception {
        String[] filedArr = linestr.split(",");
        return new WaterSensor(filedArr[0],Long.valueOf(filedArr[1]),Integer.valueOf(filedArr[2]));
    }
}
