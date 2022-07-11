package com.mongo.example.mongodemo.service;

import com.mongo.example.mongodemo.models.RestCar;

public interface CarInterface {
	public String handlingDoorUnlock( int id);

	public String handlingDoorLock( int id) ;

	RestCar AcTempPlus(RestCar employee);
	

}
