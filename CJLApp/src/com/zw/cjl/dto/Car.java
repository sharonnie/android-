package com.zw.cjl.dto;

public class Car {
	public long _id = 0;
	public long _orgId = 0;
	public long _schoolId = 0;
	public String _ownerName = null;
	public String _schoolName = null;
	public String _carKind = null;
	public String _status = null;
	public String _cityDivision = null;
	public String _simNo = null;
	public String _schoolCode = null;
	public String _carType = null;
	public String _carNo = null;
	public String _division = null;
	public String _deviceNo = null;
	public String _ownerPhone = null;
	
	public Car (long id,
				long orgId,
				long schoolId,
				String ownerName,
				String schoolName,
				String carKind,
				String status,
				String cityDivision,
				String simNo,
				String schoolCode,
				String carType,
				String carNo,
				String division,
				String deviceNo,
				String ownerPhone) 
	{
		_id = id;
		_orgId = orgId;
		_schoolId = schoolId;
		_ownerName = ownerName;
		_schoolName = schoolName;
		_carKind = carKind;
		_status = status;
		_cityDivision = cityDivision;
		_simNo = simNo;
		_schoolCode = schoolCode;
		_carType = carType;
		_carNo = carNo;
		_division = division;
		_deviceNo = deviceNo;
		_ownerPhone = ownerPhone;
	}
}
