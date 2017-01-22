package com.wipro.crawler.util;

import java.util.List;
import java.util.Set;

public interface Crawler {

	public <T> List<T> getUrlList();
	
	public Set crawl(String link);
}
