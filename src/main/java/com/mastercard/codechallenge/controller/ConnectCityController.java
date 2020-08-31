package com.mastercard.codechallenge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mastercard.codechallenge.service.ConnectCityService;

@RestController
public class ConnectCityController {

	@Autowired
	private ConnectCityService connectCityService;

	@Autowired
	ResourceLoader resourceLoader;

	@GetMapping("connected")
	public String isConnected(@RequestParam(name = "origin") String origin,
			@RequestParam(name = "destination") String destination) {

		return connectCityService.checkConnectivity(origin, destination);
	}

}
