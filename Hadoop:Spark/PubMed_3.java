package org.sikdar;

import java.io.IOException;
import java.util.*;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.*;
import org.apache.hadoop.mapreduce.lib.output.*;
import org.apache.hadoop.util.*;

public class PubMed_3 extends Configured {

    public static class MyMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

        private Text text = new Text();
        private final static IntWritable iw = new IntWritable(1);
        private int yoi;

        @Override
        public void setup(Context context) {
            Configuration conf = context.getConfiguration();
            yoi = Integer.parseInt(conf.get("yoi"));
        }

        @Override
        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

            String[] output = value.toString().split("\t");

            int year = Integer.parseInt(output[6]);

            if (year != yoi) {
                return;
            }

            text.set(output[7]);
            context.write(text, iw);
        }
    }

    public static class MyReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

        @Override
        public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {

            int total = 0;

            for (IntWritable value : values) {
                total += value.get();
            }

            context.write(key, new IntWritable(total));
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 3) {
            System.err.println("Invalid Command Line Argument! Proper Format: PubMed_3 <input file> <year> <output file>");
            return;
        }

        String input_arg=args[0];
        String output_arg=args[2];
        int yoi= Integer.parseInt(args[1]);
        Configuration configuration = new Configuration();
        Job job = Job.getInstance(configuration, "task3");
        job.setJarByClass(PubMed_3.class);
        job.setMapperClass(MyMapper.class);
        job.setReducerClass(MyReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        FileInputFormat.addInputPath(job, new Path(input_arg));
        FileOutputFormat.setOutputPath(job, new Path(output_arg));
        job.getConfiguration().setInt("yoi", yoi);
        System.exit(job.waitForCompletion(true) ? 0 : 1);


    }
}

