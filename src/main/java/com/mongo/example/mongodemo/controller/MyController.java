package com.mongo.example.mongodemo.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mongo.example.mongodemo.exception.ControllerException;
import com.mongo.example.mongodemo.exception.InvalidTemperatureException;
import com.mongo.example.mongodemo.models.RestCar;
import com.mongo.example.mongodemo.repo.CarRepository;
import com.mongo.example.mongodemo.service.CarInterface;
import com.mongodb.client.MongoClient;
import com.mongodb.client.result.UpdateResult;

@RestController
@RequestMapping("/car")
public class MyController {

	@Autowired
	private CarRepository carRepo;
	
	@Autowired
	MongoTemplate mongoTemplate;
		
	@Autowired
	private CarInterface carInterface;

	MongoClient mongoClient;

	@GetMapping("/speed")
	public ResponseEntity<?> getSpeed() {
		return ResponseEntity.ok(this.carRepo.findAll());
	}
	
	@PostMapping("/addSpeed")
	public ResponseEntity<?> addSpeedtry(@RequestBody RestCar restCar) {
		if(restCar.getSpeed() >= 0 && restCar.getSpeed() <=20)
		{
			restCar.setLevel(1);
		}
		else if(restCar.getSpeed() > 20 && restCar.getSpeed() <= 40) {
			restCar.setLevel(2);
		}
		else if(restCar.getSpeed() > 40 && restCar.getSpeed() <= 60) {
			restCar.setLevel(3);
		}
		else if(restCar.getSpeed() > 60 && restCar.getSpeed() <= 80) {
			restCar.setLevel(4);
		}
		else if(restCar.getSpeed() > 80 && restCar.getSpeed() <= 100) {
			restCar.setLevel(5);
		}
		RestCar save = this.carRepo.insert(restCar);

		return ResponseEntity.ok(save);
	}
	
	@PutMapping("/doorunlock/{id}")
	public String handlingDoorUnlock( @PathVariable("id") int id) {
	return	 carInterface.handlingDoorUnlock(id);
	
	}

	@RequestMapping("/doorlock/{id}")
	public String handlingDoorLock(@PathVariable("id") int id) {
		
	    return	 carInterface.handlingDoorLock(id);
	}

	@RequestMapping("/trunkopen/{id}")
	public String handlingTrunkOpen(@PathVariable("id") int id) {
		Query query=new Query(Criteria.where("_id").is(id));
	    List<RestCar> restCar=mongoTemplate.find(query,RestCar.class);

	    if(restCar!=null){
	        Update update=new Update().set("trunk","OPEN");
	        UpdateResult result = mongoTemplate.updateFirst(query,update,RestCar.class);	
	        System.out.println("count incre"+result.getModifiedCount());
	    }else{
System.out.println("else");		   
}		    
	return  "success";
	}

	@RequestMapping("/trunkclose/{id}")
	public String handlingTrunkClose(@PathVariable("id") int id) {
		Query query=new Query(Criteria.where("_id").is(id));
	    List<RestCar> restCar=mongoTemplate.find(query,RestCar.class);

	    if(restCar!=null){
	        Update update=new Update().set("trunk","CLOSE");
	        UpdateResult result = mongoTemplate.updateFirst(query,update,RestCar.class);	
	        System.out.println("count incre"+result.getModifiedCount());
	    }else{
System.out.println("else");		   
}		    
	return  "success";
	}


	@RequestMapping("/acon/{id}")
	public String handlingACOn(@PathVariable("id") int id) {
		Query query=new Query(Criteria.where("_id").is(id));
	    List<RestCar> restCar=mongoTemplate.find(query,RestCar.class);

	    if(restCar!=null){
	        Update update=new Update().set("ac","ON");
	        UpdateResult result = mongoTemplate.updateFirst(query,update,RestCar.class);	
	        System.out.println("count incre"+result.getModifiedCount());
	    }else{
System.out.println("else");		   
}		    
	return  "success";
	}

	@RequestMapping("/acoff/{id}")
	public String handlingACOff(@PathVariable("id") int id) {
		Query query=new Query(Criteria.where("_id").is(id));
	    List<RestCar> restCar=mongoTemplate.find(query,RestCar.class);

	    if(restCar!=null){
	        Update update=new Update().set("ac","OFF");
	        UpdateResult result = mongoTemplate.updateFirst(query,update,RestCar.class);	
	        System.out.println("count incre"+result.getModifiedCount());
	    }else{
System.out.println("else");		   
}		    
	return  "success";
	}

	@PutMapping("/actempchange")
	public ResponseEntity<?> AcTempPlus(@RequestBody RestCar restCar) {
		try {
			RestCar employeeSaved = carInterface.AcTempPlus(restCar);
			return new ResponseEntity<RestCar>(employeeSaved, HttpStatus.CREATED);
		}catch (InvalidTemperatureException e) {
			ControllerException ce = new ControllerException(e.getErrorCode(),e.getErrorMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			ControllerException ce = new ControllerException("611","Something went wrong in controller");
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/decrsunroofslider/{id}")
	public String DecreSunroofSlider( @PathVariable("id") int id) {
		    Query query=new Query(Criteria.where("_id").is(id));
		    List<RestCar> restCar=mongoTemplate.find(query,RestCar.class);

		    if(restCar!=null){
		        Update update=new Update().inc("sunroofSlider",-1);
		        UpdateResult result = mongoTemplate.updateFirst(query,update,RestCar.class);	
		        System.out.println("count decre"+result.getModifiedCount());
		    }else{
System.out.println("else");		   
}		    
		return  "success";
	}

	@PutMapping("/incrsunroofslider/{id}")
	public String IncreSunroofSlider( @PathVariable("id") int id) {

		    Query query=new Query(Criteria.where("_id").is(id));
		    List<RestCar> restCar=mongoTemplate.find(query,RestCar.class);

		    if(restCar!=null){
//		    	System.out.println("if");
		        Update update=new Update().inc("sunroofSlider",1);
//		        Update up = new Update().set("sunroofSlider", 3);
		        UpdateResult result = mongoTemplate.updateFirst(query,update,RestCar.class);	
		        System.out.println("count incre"+result.getModifiedCount());
		    }else{
System.out.println("else");		   
}		    
		return  "success";
	}

}

//@PutMapping("/doorlock/{id}")
//public String handlingDoorUnlock( @PathVariable("id") int id) {
//	Query query=new Query(Criteria.where("_id").is(id));
//
//	List<RestCar> restCar=mongoTemplate.find(query,RestCar.class);
//
//    if(restCar!=null){
//        Update update=new Update().set("door","LOCK");
//        UpdateResult result = mongoTemplate.updateFirst(query,update,RestCar.class);	
//        System.out.println("count incre"+result.getModifiedCount());
//    }else{
//        throw new ResourceNotFoundException(" not found");		   
//}		    
//return  "success";
//}

//@RequestMapping("/doorunlock/{id}")
//public String handlingDoorOff(@PathVariable("id") int id) {
//	Query query=new Query(Criteria.where("_id").is(id));
//    List<RestCar> restCar=mongoTemplate.find(query,RestCar.class);
//
//    if(restCar!=null){
//    	System.out.println("if");
//        Update update=new Update().set("door","UNLOCK");
//        Update up = new Update().set("sunroofSlider", 3);
//        UpdateResult result = mongoTemplate.updateFirst(query,update,RestCar.class);	
//        System.out.println("count incre"+result.getModifiedCount());
//    }else{
//System.out.println("else");		   
//}		    
//return  "success";
//}
