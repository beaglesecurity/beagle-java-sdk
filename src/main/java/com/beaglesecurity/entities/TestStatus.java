package com.beaglesecurity.entities;

public class TestStatus {
	private String status; //"running", "completed", "stopped", "failed"
	private Integer progress;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getProgress() {
		return progress;
	}
	public void setProgress(Integer progress) {
		this.progress = progress;
	}
}
