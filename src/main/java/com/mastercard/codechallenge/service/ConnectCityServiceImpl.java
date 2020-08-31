package com.mastercard.codechallenge.service;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConnectCityServiceImpl implements ConnectCityService {

	@Autowired
	LoadConnectCityDataService loadConnectCityDataService;

	private static String CONNECTED = "yes";
	private static String UN_CONNECTED = "no";
	private static String DATA_FILE = "classpath:city.txt";

	@Override
	public String checkConnectivity(String origin, String destination) {

		if (StringUtils.isBlank(origin) || StringUtils.isBlank(destination)) {
			return UN_CONNECTED;
		}

		return isRouteExisting(origin, destination);
	}

	private String isRouteExisting(String origin, String destination) {

		Map<String, Set<String>> connectivityMap = loadConnectCityDataService.loadData(DATA_FILE);
		boolean found = origin.equals(destination);

		if (connectivityMap.containsKey(origin) && connectivityMap.containsKey(destination)) {

			Queue<String> citiesToVisit = new LinkedList<String>();
			Set<String> citiesAlreadyVisited = new HashSet<String>();
			citiesToVisit.add(origin);

			while (!citiesToVisit.isEmpty() && !found) {

				String city = citiesToVisit.poll();
				found = city.equals(destination);
				Set<String> possibleConnections = connectivityMap.get(city);

				for (String possibleCity : possibleConnections) {

					if (!citiesAlreadyVisited.contains(possibleCity)) {

						citiesToVisit.add(possibleCity);
						citiesAlreadyVisited.add(possibleCity);
					}
				}
			}
		}

		if (found)
			return CONNECTED;

		return UN_CONNECTED;

	}

}
