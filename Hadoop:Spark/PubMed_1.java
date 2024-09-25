package org.sikdar;


import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.*;
import org.apache.hadoop.mapreduce.lib.output.*;

public class PubMed_1 {

    // 4 types declared: Type of input key, type of input value, type of output key, type of output value
    public static class MyMapper extends Mapper<Object, Text, Text, LongWritable> {
        private final static LongWritable lw = new LongWritable(1);
        private Text text = new Text();

        // The 4 types declared here should match the types that was declared on the top
        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {

            String[] output=value.toString().split("\\t");
                text.set(output[6]);
                context.write(text, lw);
        }

    }



    // 4 types declared: Type of input key, type of input value, type of output key, type of output value
    // The input types of reduce should match the output type of map
    public static class MyReducer extends Reducer<Text, LongWritable, Text, LongWritable> {
        private LongWritable count = new LongWritable();

        // Notice the that 2nd argument: type of the input value is an Iterable collection of objects
        //  with the same type declared above/as the type of output value from map
        public void reduce(Text key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException {
            long total = 0;
            for (LongWritable value: values) {
                total += value.get();
            }
            count.set(total);
            // This write to the final output
            context.write(key, count);
        }
    }


    public static void main(String[] args)  throws Exception {
        if (args.length != 2) {
            System.err.println("Invalid Command Line Argument! Proper Format: PubMed_1 <input file> <output file>");
            return;
        }

        String input_arg=args[0];
        String output_arg=args[1];
        Configuration configuration = new Configuration();
        Job job = Job.getInstance(configuration, "task1");
        job.setJarByClass(PubMed_1.class);
        job.setMapperClass(MyMapper.class);
        job.setReducerClass(MyReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);
        FileInputFormat.addInputPath(job, new Path(input_arg));
        FileOutputFormat.setOutputPath(job,  new Path(output_arg));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}