package com.example.demo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Greeting {

	private long id;
	private String content;

	public Greeting() {
		
	}
	
	public Greeting(long id, String content) {
		this.id = id;
		this.content = content;
	}

	
}
