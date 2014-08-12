package com.zw.cjl.dto;

public class Order {
	public String id = null;
	public String name = null;
	public String identity = null;
	public String stage = null;
	public String type = null;
	public String sqcx = null;
	public String examDate = null;
	public String examLocation = null;
	public String commitDate = null;
	public String status = null;
	public String reason = null;
	
	public Order (String id,
				  String name,
				  String identity,
				  String stage,
				  String type,
				  String sqcx,
				  String examDate,
				  String examLocation,
				  String commitDate,
				  String status,
				  String reason)
	{
		this.id = id;
		this.name = name;
		this.identity = identity;
		this.stage = stage;
		this.type = type;
		this.sqcx = sqcx;
		this.examDate = examDate;
		this.examLocation = examLocation;
		this.commitDate = commitDate;
		this.status = status;
		this.reason = reason;
	}
}
