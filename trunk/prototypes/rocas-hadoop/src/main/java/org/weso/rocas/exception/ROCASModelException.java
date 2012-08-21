package org.weso.rocas.exception;

import java.io.FileNotFoundException;

public class ROCASModelException extends RuntimeException {

	public ROCASModelException(String sms, Throwable e) {
		super(sms,e);
	}

}
