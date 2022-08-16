package com.festivals.festivals.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.festivals.festivals.entities.Festival;

@Service
public class FestivalServiceImpl implements FestivalService {

	List<Festival> list;
	
	public FestivalServiceImpl() {
		
		list = new ArrayList<>();
		list.add(new Festival(1,"Ganesh Utsav","August"));
		list.add(new Festival(2,"Diwali","October"));
	}
	
	@Override
	public List<Festival> getFestivals() {
		return list;
	}

	@Override
	public Festival getFestival(long festivalId) {
		
		Festival f = null;
		try {
			for(Festival festival:list) {
				if(festival.getId() == festivalId) {
					f = festival;
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return f;
	}

	@Override
	public Festival addFestival(Festival festival) {
		
		Festival f = null;
		
		for(Festival fEach:list) {
			if(fEach.getId() == festival.getId()) {
				return f;
			}
		}
		list.add(festival);		
		return festival;
		
	}

	@Override
	public Festival updateFestival(Festival festival) {
		
		Festival f = null;
		
		for(Festival fEach:list) {
			if(fEach.getId() == festival.getId()) {
				fEach.setName(festival.getName());
				fEach.setMonth(festival.getMonth());
				return festival;
				}
		}
		
		return f;		
	}

	@Override
	public void deleteFestival(long parseLong) {
		list = this.list.stream().filter(e->e.getId()!=parseLong).collect(Collectors.toList());		
	}

}
