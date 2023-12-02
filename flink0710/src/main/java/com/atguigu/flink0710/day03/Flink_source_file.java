package com.atguigu.flink0710.day03;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.connector.file.src.FileSource;
import org.apache.flink.connector.file.src.reader.TextLineInputFormat;
import org.apache.flink.core.fs.Path;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class Flink_source_file {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //env.readTextFile();
        FileSource<String> fileSource
                = FileSource.forRecordStreamFormat(new TextLineInputFormat(),
                        new Path("D:\\Atguigu\\BigData\\上课资料\\实时数仓\\code\\bigdata0710-parent\\flink0710\\input\\wordcount.txt"))
                .build();

        DataStreamSource<String> fileDS = env.fromSource(fileSource, WatermarkStrategy.noWatermarks(), "filesource");

        fileDS.print();

        env.execute();


    }
}
