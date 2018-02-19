package com.challenge.starwarsapi.common.model;

import org.springframework.data.annotation.Id;

public class Sequence {

	@Id
	private String id;
	private long sequence;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getSequence() {
		return sequence;
	}

	public void setSeq(long sequence) {
		this.sequence = sequence;
	}

	@Override
	public String toString() {
		return "CustomSequence [id=" + id + ", sequence=" + sequence + "]";
	}

}