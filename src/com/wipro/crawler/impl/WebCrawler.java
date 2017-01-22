package com.wipro.crawler.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.wipro.crawler.util.Constants;
import com.wipro.crawler.util.Crawler;
import com.wipro.crawler.util.CrawlerUtil;

/**
 * Launch Class - crawls the given URL
 * 
 * @author sachinchitre
 *
 */

public class WebCrawler implements Crawler {

	static HashSet<String> masterQueue = new HashSet<String>();

	/**
	 * Crawl URLs
	 */
	@SuppressWarnings("unchecked")
	public Set crawl(String link) {
		HashSet<String> urlsVisited = new HashSet<String>();

		URL url;

		try {
			url = new URL(link);
			
			

			System.out.println("-------------------------------------");
			System.out.println("BEGIN crawl for URL " + url.toString());

			Set<String> allLinks = CrawlerUtil.extractLinksWithText(url).keySet();

			System.out.println("Found total " + allLinks.size() + " links");

			int linkCount = 0;
			for (String nextLink : allLinks) {

				linkCount++;

				URL nextURL;

				try {
					nextURL = new URL(nextLink);
				} catch (MalformedURLException e) {
					System.out.println("BAD URL Encountered");
					continue;

				}
				String targetDomain = nextURL.getHost();
		
				//If the found URL is from wiprodigital.com domain, store it in a HashSet & display
				if (targetDomain.contains(Constants.DOMAIN)) {
					urlsVisited.add(nextLink);
					System.out.println(linkCount + ": " + nextLink+" "+urlsVisited.size());
				

				}

			}

		} catch (MalformedURLException e) {
			System.out.println("BAD URL Encountered");
			

		} catch (IOException e) {
			
		}
		
		System.out.println("Returning "+urlsVisited.size());
		
		return urlsVisited;
	}

	
	
	
	@Override
	public <T> List<T> getUrlList() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

	public static void main(String a[]) {

		WebCrawler crawler=new WebCrawler();
		HashSet<String> urls;
		int totalLinksProcessed=0;
		
		masterQueue.add(Constants.DOMAIN_URL);
		
		
		//Until sufficient number of links are gathered, keep crawling
		while(true){
		
			//Sub que to add to when a link is processed- this will then be merged into masterQueue
			HashSet<String> subQueue =new HashSet<String>();
			for(String url:masterQueue){
			
			urls=(HashSet<String>) crawler.crawl(url);
			System.out.println(urls.size()+" new URLs crawled");
			
			//WebCrawler.masterQueue.addAll(urls);
			subQueue.addAll(urls);
			
			totalLinksProcessed=masterQueue.size();
			masterQueue.addAll(subQueue);
			System.out.println("Master Queue Size "+totalLinksProcessed);
			
			if(totalLinksProcessed>Constants.MAX_LINKS)
				
				return;
			}
			
		}
		
		
		
	}
}
