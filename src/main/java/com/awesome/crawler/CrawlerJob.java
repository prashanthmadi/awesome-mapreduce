package com.awesome.crawler;

import com.awesome.AppStart;
import com.awesome.helpers.BaseJob;
import com.awesome.helpers.UtilMethods;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import java.net.URI;
import java.util.Map;

public class CrawlerJob implements BaseJob {

    private String start_url ="https://github.com/sindresorhus/awesome/blob/master/readme.md";
    private String input = "/input.txt";
    private String output ="/output";

    public void start(String jobName){
        Map<String,String> links = UtilMethods.getLinks(start_url);
        try{
            Configuration conf = new Configuration();
            conf.set("mapreduce.input.keyvaluelinerecordreader.key.value.separator", "->");

            FileSystem fs = FileSystem.get(URI.create(input),conf);
            FSDataOutputStream out = fs.create(new Path(input));
            for (Map.Entry<String, String> link : links.entrySet()) {
               out.writeBytes(link.getKey()+"->"+link.getValue()+"\n");
               System.out.println(link.getKey());
            }
            out.close();

            Job job = Job.getInstance(conf, jobName);
            job.setJarByClass(AppStart.class);
            job.setInputFormatClass(KeyValueTextInputFormat.class);
            job.setMapOutputKeyClass(Text.class);
            job.setOutputFormatClass(TextOutputFormat.class);

            job.setMapOutputValueClass(IntWritable.class);

            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(IntWritable.class);

            job.setMapperClass(MapData.class);
            job.setReducerClass(ReduceData.class);

            FileInputFormat.addInputPath(job, new Path(input));
            FileOutputFormat.setOutputPath(job, new Path(output));
            System.exit(job.waitForCompletion(true)?0:1);

        }catch (Exception e){
            System.out.println("error");
            e.printStackTrace();

        }
    }



}
