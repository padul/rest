package com.mongo.example.mongodemo.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import com.mongo.example.mongodemo.exception.InvalidTemperatureException;
import com.mongo.example.mongodemo.models.RestCar;
import com.mongo.example.mongodemo.repo.CarRepository;
import com.mongodb.client.result.UpdateResult;

@Service
public class CarHandler implements CarInterface {
	
	@Autowired
    MongoTemplate mongoTemplate;
	
	@Autowired
    CarRepository carRepo;

	private RestCar restCar;
	
	public String handlingDoorLock( int id) {
		Query query=new Query(Criteria.where("_id").is(id));

		List<RestCar> restCar=mongoTemplate.find(query,RestCar.class);

	    if(restCar!=null){
	        Update update=new Update().set("door","LOCK");
	        UpdateResult result = mongoTemplate.updateFirst(query,update,RestCar.class);	
	        System.out.println("modified count "+result.getModifiedCount());
	    }  
	return  " door successfully locked";
	}
	 
	public String handlingDoorUnlock( int id) {

		Query query=new Query(Criteria.where("_id").is(id));
		System.out.println(query);   // Query: { "_id" : 2}, Fields: {}, Sort: {}
	
	    List<RestCar> restCar=mongoTemplate.find(query,RestCar.class);
	    if(restCar!=null){
	        Update update=new Update().set("door","UNLOCK");
	        UpdateResult result = mongoTemplate.updateFirst(query,update,RestCar.class);	
	        System.out.println("modified count = "+result.getModifiedCount());
	    }
	return  " door successfully unlocked";
	}


	@Override
	public RestCar AcTempPlus(RestCar employee) {

		if(employee.getMinTemp() < 16  ) {
			throw new InvalidTemperatureException("601","min temp <16 send proper valu");
		}
		else if(employee.getMaxTemp() > 30  ) {
			throw new InvalidTemperatureException("602","max temp >30 send proper valu");
		}
		try {
			RestCar save = this.carRepo.save(restCar);
			return save;
		}catch (IllegalArgumentException e) {
			throw new InvalidTemperatureException("603","given temp is invalid" + e.getMessage());
		}

	}
}
