package com.atguigu.flink0710.day02;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.RestOptions;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.graph.StreamGraph;
import org.apache.flink.util.Collector;

/**
 * 并行度设置
 *      代码中单独指定算子并行度 > 代码中进行全局设置
 */
public class Flink01_par {
    public static void main(String[] args) throws Exception {
        //TODO 1. 指定批处理环境
        //StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        Configuration conf = new Configuration();
        conf.set(RestOptions.BIND_PORT,"8081");
        StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(conf);
        //全局设置并行度,默认：CPU线程数
        //env.setParallelism(2);
        //TODO 2. 从指定的网络端口读取数据
        DataStreamSource<String> socketDS = env.socketTextStream("hadoop102", 8888);
        //TODO 3. 将读取的数据进行转换  封装为Tuple二元组
        SingleOutputStreamOperator<String> flatMapDS = socketDS.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public void flatMap(String linestr, Collector<String> collector) throws Exception {
                String[] wordArr = linestr.split(" ");
                for (String word : wordArr) {
                    collector.collect(word);
                }
            }
        });//.setParallelism(2);

        SingleOutputStreamOperator<Tuple2<String, Long>> tupleDS = flatMapDS.map(
                new MapFunction<String, Tuple2<String, Long>>() {

                    @Override
                    public Tuple2<String, Long> map(String word) throws Exception {
                        return Tuple2.of(word, 1L);
                    }
                }
        ).setParallelism(3);


        //TODO 4。按单词进行分组
        KeyedStream<Tuple2<String, Long>, Tuple> keyedDS = tupleDS.keyBy(0);
        //TODO 5. 聚合
        SingleOutputStreamOperator<Tuple2<String, Long>> sumDS = keyedDS.sum(1);
        //TODO 6. 打印
        sumDS.print();
        //TODO 7. 提交
        env.execute();


    }
}
