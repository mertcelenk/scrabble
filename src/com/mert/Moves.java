package com.mert;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "moves")
public class Moves {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int moveId;
	private int boardId;
	private int sequence;
	private String word;
	private int startXCoordinate;
	private int startYCoordinate;
	private int direction;
	private int score;
	
	public int getMoveId() {
		return moveId;
	}
	public void setMoveId(int moveId) {
		this.moveId = moveId;
	}
	public int getBoardId() {
		return boardId;
	}
	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}
	public int getSequence() {
		return sequence;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public int getStartXCoordinate() {
		return startXCoordinate;
	}
	public void setStartXCoordinate(int startXCoordinate) {
		this.startXCoordinate = startXCoordinate;
	}
	public int getStartYCoordinate() {
		return startYCoordinate;
	}
	public void setStartYCoordinate(int startYCoordinate) {
		this.startYCoordinate = startYCoordinate;
	}
	public int getDirection() {
		return direction;
	}
	public void setDirection(int direction) {
		this.direction = direction;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	} 
	
	
}
