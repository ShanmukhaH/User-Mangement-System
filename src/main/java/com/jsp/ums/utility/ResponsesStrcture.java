package com.jsp.ums.utility;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Component
public class ResponsesStrcture<T> {

	private int statuscode;
	private String message;
	private T data;
}
