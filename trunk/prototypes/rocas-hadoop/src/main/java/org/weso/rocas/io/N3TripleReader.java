package org.weso.rocas.io;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileSplit;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.LineRecordReader;
import org.apache.hadoop.mapred.RecordReader;

public class N3TripleReader implements RecordReader<Text, N3Triple> {

	private LineRecordReader lineReader;
	private LongWritable lineKey;
	private Text lineValue;


	public N3TripleReader(JobConf job, FileSplit input) throws IOException {
		lineReader = new LineRecordReader(job, input);
		lineKey = lineReader.createKey();
		lineValue = lineReader.createValue();
	}

	@Override
	public void close() throws IOException {
		this.lineReader.close();
	}

	@Override
	public Text createKey() {
		return new Text();
	}

	@Override
	public N3Triple createValue() {
		return new N3Triple();
	}

	@Override
	public long getPos() throws IOException {
		 return lineReader.getPos();
	}

	@Override
	public float getProgress() throws IOException {
		 return lineReader.getProgress();
	}

	@Override
	public boolean next(Text key, N3Triple value) throws IOException {
		// get the next line
		if (!lineReader.next(lineKey, lineValue)) {
			return false;
		}

		// parse the lineValue which is in the format:
		String [] pieces = lineValue.toString().split("\t");
		if (pieces.length != 4) {
			throw new IOException("Invalid record received");
		}

		// try to parse floating point components of value
		String s, p, o;
		try {
			s = pieces[1].trim();
			p = pieces[2].trim();
			o = pieces[3].trim();
			o = o.substring(0, o.length()-2);//Removing last .
		} catch (NumberFormatException nfe) {
			throw new IOException("Error parsing N3 triple point value in record");
		}

		key.set(pieces[0].trim());
		value.subject = new Text(s);
		value.predicate = new Text(p);
		value.object =  new Text(o);
		return true;
	}

}
