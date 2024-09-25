package org.sikdar;


import java.io.IOException;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.*;
import org.apache.hadoop.mapreduce.lib.output.*;

public class PubMed_2 {

    public static class MyMapper extends Mapper<LongWritable, Text, Text, NullWritable> {

        private Text text = new Text();

        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String[] output = value.toString().split("\\t");
            text.set(output[7]);
            context.write(text, NullWritable.get());
        }
    }

    public static class MyReducer extends Reducer<Text, NullWritable, Text, NullWritable> {

        public void reduce(Text key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
            context.write(key, NullWritable.get());
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println("Invalid Command Line Argument! Proper Format: PubMed_2 <input file> <output file>");
            return;
        }

        String input_arg=args[0];
        String output_arg=args[1];
        Configuration configuration = new Configuration();
        Job job = Job.getInstance(configuration, "task2");
        job.setJarByClass(PubMed_2.class);
        job.setMapperClass(MyMapper.class);
        job.setReducerClass(MyReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);
        FileInputFormat.addInputPath(job, new Path(input_arg));
        FileOutputFormat.setOutputPath(job,  new Path(output_arg));
        System.exit(job.waitForCompletion(true) ? 0 : 1);

    }
}
