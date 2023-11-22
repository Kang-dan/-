package com.ssafy.enjoytrip.board.model;

import lombok.Builder;
import lombok.Data;

@Data
public class FileUpload {
	private int no;
	private int boardNo;
	private String originalName;
	private String saveName;
	private long size;
	
	@Builder
	public FileUpload(String originalName, String saveName, long size) {
		this.originalName = originalName;
		this.saveName = saveName;
		this.size = size;
	}
	
	
}
