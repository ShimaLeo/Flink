package com.atguigu.flink0710.day03;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.kafka.common.protocol.types.Field;

/**
 *  自定义数据源
 *  一般实现的是SourceFunction接口，重写其中的方法
 *  env.addSource添加数据
 */
public class Flink06_source_self {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.addSource(
                new MyselfSource()
        ).print();//1.12之前
        //env.fromSource();//1.12之后
        env.execute();
    }
}

class MyselfSource implements SourceFunction<String> {

    @Override
    public void run(SourceContext<String> sourceContext) throws Exception {
        for (int i = 0; i < 10; i++) {
            sourceContext.collect("数据" + i);
        }
    }

    @Override
    public void cancel() {
        //当应用被取消后，执行cancel
        System.out.println("结束");
    }
}