package org.weso.rocas.reasoner.jobs;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.*;

import java.io.IOException;


import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.OutputCollector;
import org.junit.Test;
import org.weso.rocas.io.N3Triple;


public class NaiveReasonerMapperTest {

	@Test
	public void test() throws IOException {
		NaiveN3ReasonerMapper mapper = new NaiveN3ReasonerMapper();
		Text object = new Text("o");
		Text predicate = new Text("p");
		Text subject = new Text("s");
		N3Triple value = new N3Triple(subject, predicate, object);
		OutputCollector<LongWritable, N3Triple> output = mock(OutputCollector.class);
		mapper.map(null, value, output, null);				
		verify(output).collect(null, value);
		mapper.close();
	}

}
