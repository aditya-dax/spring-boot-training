package com.festivals.festivals.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.festivals.festivals.entities.Festival;
import com.festivals.festivals.services.FestivalService;

@RestController
public class ProjectController {
	
	@Autowired
	private FestivalService festivalService;
	
	@GetMapping("/home")
	public String home() {
		return "This is Festivals <-> month storage!";
	}
	
	@GetMapping("/festivals")
	public ResponseEntity<List<Festival>> getFestivals(){
		
		List<Festival> list = festivalService.getFestivals();
		if( list.size() <= 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		return ResponseEntity.of(Optional.of(list));
	}
	
	@GetMapping("/festivals/{festivalId}")
	public ResponseEntity<Festival> getFestival(@PathVariable String festivalId) {
		Festival festival = festivalService.getFestival(Long.parseLong(festivalId));
		
		
		if( festival == null ) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		return ResponseEntity.of(Optional.of(festival));
	}
	
	@PostMapping("/festivals")
	public ResponseEntity<Festival> addFestival(@Valid @RequestBody Festival festival) {
		
		Festival f = null;
		
		try {
			
						
			f = festivalService.addFestival(festival);
			if(f == null) {
				return ResponseEntity.status(HttpStatus.CONFLICT).build();
				//This is temporary as no database connected in this case.
			}
			return ResponseEntity.ok().build();
		
		} catch (Exception e){	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		} 
	}
	
	@PutMapping("/festivals")
	public ResponseEntity<Festival> updateFestival(@Valid @RequestBody Festival festival) {
		
		Festival f = null;
		
		try {
		
				List<Festival> list = festivalService.getFestivals();
				if( list.size() <= 0) {
					return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
				}
			
				f = this.festivalService.updateFestival(festival);
				if( f == null ) {
					return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			    }
			
			return ResponseEntity.ok().body(f);
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@DeleteMapping("/festivals/{festivalId}")
	public ResponseEntity<HttpStatus> deleteFestival( @PathVariable String festivalId){
		try {
			
			List<Festival> list = festivalService.getFestivals();
			if( list.size() <= 0) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
			
			this.festivalService.deleteFestival(Long.parseLong(festivalId));
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
