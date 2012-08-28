package org.weso.rocas.io;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;
import org.apache.hadoop.io.WritableUtils;

public class N3TripleComparator extends WritableComparator {
	private static final Text.Comparator TEXT_COMPARATOR = new Text.Comparator();


	public int compare(byte[] b1, int s1, int l1, byte[] b2, int s2, int l2) {
		try {
			int firstL1 = WritableUtils.decodeVIntSize(b1[s1]) + readVInt(b1, s1);
			int firstL2 = WritableUtils.decodeVIntSize(b2[s2]) + readVInt(b2, s2);
			return TEXT_COMPARATOR.compare(b1, s1, firstL1, b2, s2, firstL2);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}

	@Override
	public int compare(WritableComparable a, WritableComparable b) {
		if (a instanceof N3Triple && b instanceof N3Triple) {
			return ((N3Triple) a).compareTo(((N3Triple) b));
		}
		return super.compare(a, b);
	}

	public N3TripleComparator() {
		super(N3Triple.class);
	}


	protected N3TripleComparator(Class<? extends WritableComparable> keyClass) {
		super(keyClass);
	}


}
