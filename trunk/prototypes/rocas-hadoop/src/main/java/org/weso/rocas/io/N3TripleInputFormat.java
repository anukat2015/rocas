package org.weso.rocas.io;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileSplit;
import org.apache.hadoop.mapred.InputSplit;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.RecordReader;
import org.apache.hadoop.mapred.Reporter;

public class N3TripleInputFormat extends  FileInputFormat<Text, N3Triple> {




	public RecordReader<Text, N3Triple> getRecordReader(
			InputSplit input, JobConf job, Reporter reporter)
					throws IOException {

		reporter.setStatus(input.toString());
		return new N3TripleReader(job, (FileSplit)input);
	}

}
