package com.example.demo;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class SendObject {

	private String kinoId;
	private String nyuryokuId;
	private String soshinNichiji;
	
	public SendObject(String kinoId, String nyuryokuId) {
		this.kinoId = kinoId;
		this.nyuryokuId = nyuryokuId;
		this.soshinNichiji = LocalDateTime.now().toString();
	}
	
	public SendObject() {
		
	}
}
