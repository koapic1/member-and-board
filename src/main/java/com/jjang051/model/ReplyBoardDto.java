package com.jjang051.model;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Component
public class ReplyBoardDto {
	private int no;
	private String subject;
	private String name;
	private String email;
	private String password;
	private String regDate;
	private int reGroup;
	private int reLevel;
	private int reStep;
	private int hit;
	private String contents;
	private int num;
	private String id;
}
