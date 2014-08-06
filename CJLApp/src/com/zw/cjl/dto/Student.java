package com.zw.cjl.dto;

public class Student {
	public long id = 0;
	public long jxid = 0;
	public String xykh = null;
	public String xbmc = null;
	public String sfzmhm = null;
	public String sqcxmc = null;
	public String sjhm = null;
	public String xm = null;
	public String sqcx = null;
	public String division = null;
	public String cityDivision = null;
	public String jxmc = null;
	
	public Student (long id,
					long jxid,
					String xykh,
					String xbmc,
					String sfzmhm,
					String sqcxmc,
					String sjhm,
					String xm,
					String sqcx,
					String division,
					String cityDivision,
					String jxmc)
	{
		this.id = id;
		this.xykh = xykh;
		this.xbmc = xbmc;
		this.sfzmhm = sfzmhm;
		this.sqcxmc = sqcxmc;
		this.sjhm = sjhm;
		this.xm = xm;
		this.sqcx = sqcx;
		this.division = division;
		this.cityDivision = cityDivision;
		this.jxid = jxid;
		this.jxmc = jxmc;
	}
}
