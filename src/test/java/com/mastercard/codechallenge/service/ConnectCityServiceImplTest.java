package com.mastercard.codechallenge.service;

import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ConnectCityServiceImplTest {

	@InjectMocks
	ConnectCityServiceImpl connectCityServiceImpl;

	@Mock
	LoadConnectCityDataService loadConnectCityDataService;

	private static String CONNECTED = "yes";
	private static String UN_CONNECTED = "no";

	@Test
	public void when_givenOriginIsEmptyOrNull_expect_responseAsNo() {

		String isConnected = connectCityServiceImpl.checkConnectivity("", "Newark");
		Assertions.assertEquals(UN_CONNECTED, isConnected);

		isConnected = connectCityServiceImpl.checkConnectivity(null, "Newark");
		Assertions.assertEquals(UN_CONNECTED, isConnected);
	}

	@Test
	public void when_givenDestinationIsEmptyOrNull_expect_responseAsNo() {

		String isConnected = connectCityServiceImpl.checkConnectivity("Newark", "");
		Assertions.assertEquals(UN_CONNECTED, isConnected);

		isConnected = connectCityServiceImpl.checkConnectivity("Newark", null);
		Assertions.assertEquals(UN_CONNECTED, isConnected);
	}

	@Test
	public void when_ThereIsDirectConnectivityBetwenGivenSourceAndDestination_expect_responseAsYes() {

		when(loadConnectCityDataService.loadData(Mockito.anyString())).thenReturn(createConnectivityMap());

		String isConnected = connectCityServiceImpl.checkConnectivity("Newark", "Boston");
		Assertions.assertEquals(CONNECTED, isConnected);

	}

	@Test
	public void when_ThereIsIndirectConnectivityBetwenGivenSourceAndDestination_expect_responseAsYes() {

		when(loadConnectCityDataService.loadData(Mockito.anyString())).thenReturn(createConnectivityMap());

		String isConnected = connectCityServiceImpl.checkConnectivity("Boston", "Philadelphia");
		Assertions.assertEquals(CONNECTED, isConnected);

	}

	@Test
	public void when_ThereIsNoConnectivityBetwenGivenSourceAndDestination_expect_responseAsYes() {

		when(loadConnectCityDataService.loadData(Mockito.anyString())).thenReturn(createConnectivityMap());

		String isConnected = connectCityServiceImpl.checkConnectivity("Newark", "Albany");
		Assertions.assertEquals(UN_CONNECTED, isConnected);

	}

	public Map<String, Set<String>> createConnectivityMap() {

		Map<String, Set<String>> connectivityMap = new HashMap();

		Set<String> boston = new HashSet<String>();
		boston.add("New York");
		boston.add("Newark");
		connectivityMap.put("Boston", boston);

		Set<String> newYork = new HashSet<String>();
		newYork.add("Boston");
		connectivityMap.put("New York", newYork);

		Set<String> philadelphia = new HashSet<String>();
		philadelphia.add("Newark");
		connectivityMap.put("Philadelphia", philadelphia);

		Set<String> newark = new HashSet<String>();
		newark.add("Philadelphia");
		newark.add("Boston");
		connectivityMap.put("Newark", newark);

		Set<String> trenton = new HashSet<String>();
		trenton.add("Albany");
		connectivityMap.put("Trenton", trenton);

		Set<String> albany = new HashSet<String>();
		albany.add("Trenton");
		connectivityMap.put("Albany", albany);

		return connectivityMap;
	}

}
