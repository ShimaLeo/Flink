package com.atguigu.flink0710.day01;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

public class Flink02_Stream_bound {

    public static void main(String[] args) throws Exception {

        //TODO 1. 指定批处理环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //TODO 2. 从指定的文件中读取数据
        DataStreamSource<String> textDS = env.readTextFile("D:\\Atguigu\\BigData\\上课资料\\实时数仓\\code\\bigdata0710-parent\\flink0710\\input\\wordcount.txt");
        //TODO 3. 将读取的数据进行转换  封装为二元组
        SingleOutputStreamOperator<Tuple2<String, Long>> tupleDS = textDS.flatMap(new FlatMapFunction<String, Tuple2<String, Long>>() {
            @Override
            public void flatMap(String linestr, Collector<Tuple2<String, Long>> collector) throws Exception {
                String[] wordArr = linestr.split(" ");
                for (String word : wordArr) {
                    collector.collect(Tuple2.of(word, 1L));
                }
            }
        });
        //TODO 4。分组
        KeyedStream<Tuple2<String, Long>, Tuple> keyedDS = tupleDS.keyBy(0);
        //TODO 5. 聚合
        SingleOutputStreamOperator<Tuple2<String, Long>> sumDS = keyedDS.sum(1);
        sumDS.print();
        //TODO 6. 提交
        env.execute();
    }
}
