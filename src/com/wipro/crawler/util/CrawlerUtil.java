package com.wipro.crawler.util;

import java.net.*;
import java.io.*;
import java.util.*;
 

/**
 * Utility class
 */


public class CrawlerUtil {

	public static String convert(){return null;}
	
	public static boolean isEmpty(String line){
		if(line.equals(""))
			return true;
		
		return false;
	}
	
	
	public static String getDomain(String link){
	
		//String PLACEHOLDER="";
		int DOMAIN_INDEX=7;
		
		if(link.startsWith("http")){
			
			//Find out the domain name-external or internal
			for(DomainTypes domain: DomainTypes.values())
			{
				if(link.contains("."+domain)){
			
					return link.substring((DOMAIN_INDEX),link.indexOf("."+domain));
					
				}
		
			}
			
			
			
		}
	return "";
	}










		public static void saveURL(URL url, Writer writer)
			throws IOException {
			BufferedInputStream in = new BufferedInputStream(url.openStream());
			for (int c = in.read(); c != -1; c = in.read()) {
				writer.write(c);
			}
		}


		public static void saveURL(URL url, OutputStream os)
			throws IOException {
			InputStream is = url.openStream();
			byte[] buf = new byte[1048576];
			int n = is.read(buf);
			while (n != -1) {
				os.write(buf, 0, n);
				n = is.read(buf);
			}
		}

		/**
		 * Writes the contents of the url to a string by calling saveURL with a
		 * string writer as argument
		 */
		public static String getURL(URL url)
			throws IOException {
			StringWriter sw = new StringWriter();
			saveURL(url, sw);
			return sw.toString();
		}

	
	
		public static Map extractLinksWithText(URL url)
			throws IOException {
			
			//System.out.println(getURL(url));
			
			return extractLinksWithText(getURL(url));
		}

	

		/**
		 * Extract links (key) with link text (value)
		 * only one link text is returned per
		 * URL, even if a link occurs multiple times with different texts.
		 */
		public static Map extractLinksWithText(String rawPage, String page) {
			int index = 0;
			Map links = new HashMap();
		//	  System.out.println("rawPage "+rawPage+" page "+page );
			while ((index = page.indexOf("<a ", index)) != -1)
			{
				int tagEnd = page.indexOf(">", index);
			    if ((index = page.indexOf("href", index)) == -1) break;
			    if ((index = page.indexOf("=", index)) == -1) break;
				int endTag = page.indexOf("</a", index);
			    
				String remaining = rawPage.substring(++index);
				
			//    System.out.println("Remaining: " + remaining);
				
			    StringTokenizer st 
					= new StringTokenizer(remaining, "\t\n\r\"'>#");
			    String strLink = st.nextToken();
				String strText = "";
				
				if (tagEnd != -1 && tagEnd + 1 <= endTag) {
					strText = rawPage.substring(tagEnd + 1, endTag);
				}
				strText = strText.replaceAll("\\s+", " ");
				links.put(strLink, strText);
			}
			return links;
			
		}
	    
			public static Map extractLinksWithText(String rawPage) {
			// System.out.println("started..."); 
			return extractLinksWithText(rawPage, rawPage.toLowerCase().replaceAll("\\s", " "));
		}

	
	


}
