package org.weso.rocas.reasoner.jobs;
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.ToolRunner;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import examples.Node;




public class FilterJob extends ROCASBaseJob {

	public static class FilterJobMapper extends FilterMapper {
		public void map(Object key, Text value, Context context)
				throws IOException, InterruptedException {
			Model m = ModelFactory.createDefaultModel();
			m.read(value.toString(), null);
			super.map(key, value, context);

		}
	}
	public static class FilterJobReducer extends FilterReducer{

	}

	// method to set the configuration for the job and the mapper and the reducer classes
	private Job getJobConf(String[] args) throws Exception {

		JobInfo jobInfo = new JobInfo() {
			@Override
			public Class<? extends Reducer> getCombinerClass() {
				return null;
			}

			@Override
			public Class<?> getJarByClass() {
				return FilterJob.class;
			}

			@Override
			public Class<? extends Mapper> getMapperClass() {
				return FilterMapper.class;
			}

			@Override
			public Class<?> getOutputKeyClass() {
				return Text.class;
			}

			@Override
			public Class<?> getOutputValueClass() {
				return Text.class;
			}

			@Override
			public Class<? extends Reducer> getReducerClass() {
				return FilterReducer.class;
			}
		};

		return setupJob("filtertriples", jobInfo);


	}

	// the driver to execute the job and invoke the map/reduce functions

	public int run(String[] args) throws Exception {
		Job job;
    	job = getJobConf(args); // get the job configuration
		FileInputFormat.setInputPaths(job, new Path(args[0])); // setting the input files for the job
		FileOutputFormat.setOutputPath(job, new Path(args[1])); // setting the output files for the job
		job.waitForCompletion(true); // wait for the job to complete
		return 0;
	}

	public static void main(String[] args) throws Exception {
		int res = ToolRunner.run(new Configuration(), new FilterJob(), args);
		if(args.length != 2){
			System.err.println("Usage: <in> <output name> ");
		}
		System.exit(res);
	}

}
