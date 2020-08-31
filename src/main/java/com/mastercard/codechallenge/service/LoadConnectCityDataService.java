package com.mastercard.codechallenge.service;

import java.util.Map;
import java.util.Set;

public interface LoadConnectCityDataService {

	public Map<String, Set<String>> loadData(String fileName);
}
