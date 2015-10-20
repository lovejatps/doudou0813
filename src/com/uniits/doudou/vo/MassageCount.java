package com.uniits.doudou.vo;

public class MassageCount {
	
	private String doInputFile = "doInputFile";

	private Long successCount;
	
	private Long errorCount;

	public Long getSuccessCount() {
		return successCount;
	}

	public void setSuccessCount(Long successCount) {
		this.successCount = successCount;
	}

	public Long getErrorCount() {
		return errorCount;
	}

	public void setErrorCount(Long errorCount) {
		this.errorCount = errorCount;
	}

	public String getDoInputFile() {
		return doInputFile;
	}

	public void setDoInputFile(String doInputFile) {
		this.doInputFile = doInputFile;
	}
	
}
