package com.programming.springblog.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {

	String resourceName;
	String fieldName;
	Object fieldValue;

	public ResourceNotFoundException(String resourceName, String fieldName, Object string) {
		super(String.format("%s not found with %s : %s", resourceName, fieldName, string));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = string;
	}

}