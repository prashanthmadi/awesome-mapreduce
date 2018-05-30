package com.awesome.crawler;

import com.awesome.helpers.UtilMethods;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class MapData extends Mapper<Text,Text,Text,IntWritable> {
    @Override
    protected void map(Text key, Text value, Context context) throws IOException, InterruptedException {

        UtilMethods.getLinks(value.toString());
        context.write(key,new IntWritable(1));
    }
}