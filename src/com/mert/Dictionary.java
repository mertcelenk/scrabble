package com.mert;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "dictionary")
public class Dictionary {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int dictionaryId;
	private String wordName;
	
	public int getDictionaryId() {
		return dictionaryId;
	}
	public void setDictionaryId(int dictionaryId) {
		this.dictionaryId = dictionaryId;
	}
	public String getWordName() {
		return wordName;
	}
	public void setWordName(String wordName) {
		this.wordName = wordName;
	}
	
}
