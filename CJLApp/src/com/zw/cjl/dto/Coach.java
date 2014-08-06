package com.zw.cjl.dto;

public class Coach {
	public long id = 0;
	public long jxid = 0;
	public long xysl = 0;
	public String xbry = null;
	public String xjcx = null;
	public String xm = null;
	public String sjhm = null;
	public String sfzmhm = null;
	public String jxmc = null;
	public String division = null;
	public String cityDivision = null;
	public String cphm = null;
	
	public Coach (long id,
				long jxid,
				long xysl,
				String xbry,
				String xjcx,
				String xm,
				String sjhm,
				String sfzmhm,
				String jxmc,
				String division,
				String cityDivision,
				String cphm)
	{
		this.id = id;
		this.jxid = jxid;
		this.xysl = xysl;
		this.xbry = xbry;
		this.xjcx = xjcx;
		this.xm = xm;
		this.sjhm = sjhm;
		this.sfzmhm = sfzmhm;
		this.jxmc = jxmc;
		this.division = division;
		this.cityDivision = cityDivision;
		this.cphm = cphm;
	}
}
