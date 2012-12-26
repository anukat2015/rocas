package org.weso.rocas.reasoner.jobs;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.ToolRunner;




public class NaiveReasonerJob extends ROCASBaseJob {

	public static class NaiveReasonerJobMapper extends NaiveReasonerMapper {
		
	}
	public static class NaiveReasonerJobReducer extends NaiveReasonerReducer{

	}

	// method to set the configuration for the job and the mapper and the reducer classes
	private Job getJobConf(String[] args) throws Exception {

		JobInfo jobInfo = new JobInfo() {
			@Override
			public Class<? extends Reducer> getCombinerClass() {
				return NaiveReasonerJobReducer.class;
			}

			@Override
			public Class<?> getJarByClass() {
				return NaiveReasonerJob.class;
			}

			@Override
			public Class<? extends Mapper> getMapperClass() {
				return NaiveReasonerJobMapper.class;
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
				return NaiveReasonerJobReducer.class;
			}
		};

		return setupJob("naivereasoner", jobInfo);


	}

	// the driver to execute the job and invoke the map/reduce functions

	public int run(String[] args) throws Exception {
		Job job;
    	job = getJobConf(args); // get the job configuration
//    	job.setMapOutputKeyClass(IntWritable.class);
//        job.setMapOutputValueClass(Text.class);
//        job.setOutputKeyClass(Text.class);
//        job.setOutputValueClass(Text.class);
    	///home/chema/datasets/naive/input /home/chema/datasets/naive/output
        System.out.println(args[0]);
        System.out.println(args[1]);
		FileInputFormat.setInputPaths(job, new Path(args[0])); // setting the input files for the job
		FileOutputFormat.setOutputPath(job, new Path(args[1])); // setting the output files for the job
		job.waitForCompletion(true); // wait for the job to complete
		return 0;
	}

	public static void main(String[] args) throws Exception {
		int res = ToolRunner.run(new Configuration(), new NaiveReasonerJob(), args);
		if(args.length != 2){
			System.err.println("Usage: <in> <output name> ");
		}
		System.exit(res);
	}

}
