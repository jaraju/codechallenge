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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

@SpringBootTest
public class LoadConnectCityDataServiceImplTest {

	@InjectMocks
	LoadConnectCityDataServiceImpl loadConnectCityDataServiceImpl;

	@Mock
	ResourceLoader resourceLoader;
	
	 @Value("classpath:city.txt")
	 private Resource cityConnectivityDataResource;

	private static String DATA_FILE = "classpath:city.txt";


	@Test
	public void when_connectivityMapIsAlreadyBuilt_expect_doNotCreateAnotherconnectivityMap() {
		
		Map<String, Set<String>> connectivityMap  = createConnectivityMap();
		loadConnectCityDataServiceImpl.connectivityMap = connectivityMap;
		Map<String, Set<String>> connectivityMapReturned = loadConnectCityDataServiceImpl.loadData(DATA_FILE);
		Assertions.assertEquals(connectivityMap, connectivityMapReturned);
	}

	@Test
	public void when_connectivityMapIsRequestedFirstTime_expect_createConnectivityMap() {
		when(resourceLoader.getResource(DATA_FILE)).thenReturn(cityConnectivityDataResource);

		Map<String, Set<String>> connectivityMapReturned = loadConnectCityDataServiceImpl.loadData(DATA_FILE);
		Assertions.assertEquals(connectivityMapReturned, connectivityMapReturned);
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
