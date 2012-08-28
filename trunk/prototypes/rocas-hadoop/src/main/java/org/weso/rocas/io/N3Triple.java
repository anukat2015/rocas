package org.weso.rocas.io;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;

public class N3Triple implements WritableComparable<N3Triple>{

	Text subject;
	Text predicate;
	Text object;
	
	public N3Triple(){
		
	}
	
	public N3Triple(Text subject, Text predicate, Text object) {
		super();
		this.subject = subject;
		this.predicate = predicate;
		this.object = object;
	}

	public Text getSubject() {
		return subject;
	}

	public void setSubject(Text subject) {
		this.subject = subject;
	}

	public Text getPredicate() {
		return predicate;
	}

	public void setPredicate(Text predicate) {
		this.predicate = predicate;
	}

	public Text getObject() {
		return object;
	}

	public void setObject(Text object) {
		this.object = object;
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		this.subject.readFields(in);
		this.predicate.readFields(in);
		this.object.readFields(in);	
	}
	@Override
	public void write(DataOutput out) throws IOException {
		this.subject.write(out);
		this.predicate.write(out);
		this.object.write(out);
	}
	@Override
	public int compareTo(N3Triple triple) {
		int cmp = this.subject.compareTo(triple.subject);
		int cmp2 = this.predicate.compareTo(triple.predicate);
		if(cmp != 0 || cmp2 != 0){
			return cmp;
		}
		return  this.object.compareTo(triple.object);		
	}

	@Override
	public String toString() {
		return this.subject + "\t" + this.predicate + "\t" + this.object+".";

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((object == null) ? 0 : object.hashCode());
		result = prime * result
				+ ((predicate == null) ? 0 : predicate.hashCode());
		result = prime * result + ((subject == null) ? 0 : subject.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		N3Triple other = (N3Triple) obj;
		if (object == null) {
			if (other.object != null)
				return false;
		} else if (!object.equals(other.object))
			return false;
		if (predicate == null) {
			if (other.predicate != null)
				return false;
		} else if (!predicate.equals(other.predicate))
			return false;
		if (subject == null) {
			if (other.subject != null)
				return false;
		} else if (!subject.equals(other.subject))
			return false;
		return true;
	}
	

}
