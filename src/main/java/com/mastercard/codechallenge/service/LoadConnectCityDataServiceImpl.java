package com.mastercard.codechallenge.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class LoadConnectCityDataServiceImpl implements LoadConnectCityDataService {

	@Autowired
	ResourceLoader resourceLoader;

	Map<String, Set<String>> connectivityMap;

	private static final String COMMA_DELIMITER = ",";

	@Override
	public Map<String, Set<String>> loadData(String fileName) {

		if (!CollectionUtils.isEmpty(connectivityMap))
			return connectivityMap;

		return buildConnectivityMap(fileName);
	}

	private Map<String, Set<String>> buildConnectivityMap(String fileName) {

		connectivityMap = new HashMap<String, Set<String>>();

		Resource resource = resourceLoader.getResource(fileName);
		InputStream inputSteram = null;
		File file = null;

		try {
			inputSteram = resource.getInputStream();
			file = resource.getFile();

			FileReader freader = new FileReader(file);
			BufferedReader breader = new BufferedReader(freader);
			String line;
			while ((line = breader.readLine()) != null) {
				addRouteToMap(connectivityMap, line);
			}
		} catch (IOException exception) {
			exception.printStackTrace();
		}

		return connectivityMap;
	}

	private void addRouteToMap(Map<String, Set<String>> connectivityMap, String cityPair) {

		String[] cities = cityPair.split(COMMA_DELIMITER);
		String source = cities[0].trim();
		String destination = cities[1].trim();
		populateConnectivityMap(connectivityMap, source, destination);
		populateConnectivityMap(connectivityMap, destination, source);

	}

	private void populateConnectivityMap(Map<String, Set<String>> connectivityMap, String source, String destination) {

		if (connectivityMap.containsKey(source)) {
			connectivityMap.get(source).add(destination);
		} else {
			Set<String> destinationList = new HashSet<String>();
			destinationList.add(destination);
			connectivityMap.put(source, destinationList);
		}
	}

}
