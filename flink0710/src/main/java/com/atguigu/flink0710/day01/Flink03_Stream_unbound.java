package com.atguigu.flink0710.day01;


import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;



public class Flink03_Stream_unbound {
    public static void main(String[] args) throws Exception {
        //TODO 1. 指定批处理环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //TODO 2. 从指定的网络端口读取数据
        //DataStreamSource<String> socketDS = env.socketTextStream("hadoop102", 8888);
        //TODO 3. 将读取的数据进行转换  封装为Tuple二元组
        /*SingleOutputStreamOperator<Tuple2<String, Long>> tupleDS = socketDS.flatMap(new FlatMapFunction<String, Tuple2<String, Long>>() {
            @Override
            public void flatMap(String linestr, Collector<Tuple2<String, Long>> collector) throws Exception {
                String[] wordArr = linestr.split(" ");
                for (String word : wordArr) {
                    collector.collect(Tuple2.of(word, 1L));
                }
            }
        });*/

        /**
         * 简化
         */

        env.socketTextStream("hadoop102",8888)
                .flatMap(
                        (String linestr,Collector<Tuple2<String,Long>> collector) -> {
                            String[] wordArr = linestr.split(" ");
                            for (String word : wordArr) {
                                collector.collect(Tuple2.of(word, 1L));
                            }
                        }
                )
                .returns(Types.TUPLE(Types.STRING,Types.LONG))
                .keyBy(wordTuple->wordTuple.f0)
                .sum(1)
                .print();

        //TODO 4。按单词进行分组
        //KeyedStream<Tuple2<String, Long>, Tuple> keyedDS = tupleDS.keyBy(0);
        //TODO 5. 聚合
        //SingleOutputStreamOperator<Tuple2<String, Long>> sumDS = keyedDS.sum(1);
        //TODO 6. 打印
        //sumDS.print();
        //TODO 7. 提交
        env.execute();


    }
}
