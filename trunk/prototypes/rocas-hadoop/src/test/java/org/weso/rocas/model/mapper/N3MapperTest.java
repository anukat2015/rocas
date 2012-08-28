package org.weso.rocas.model.mapper;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.*;

import java.io.IOException;


import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.OutputCollector;
import org.junit.Test;
import org.weso.rocas.io.N3Triple;

public class N3MapperTest {

	@Test
	public void testMap() throws IOException {
		N3Mapper mapper = new N3Mapper();
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
