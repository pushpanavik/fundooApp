package com.bridgeit.fundooNote.jsoup;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.bridgeit.fundooNote.noteservice.model.UrlData;

public class WebScrapper {
	
	/**
	 * @param String url
	 * @return UrlData
	 * @throws IOException
	 */
	public UrlData getUrlMetaData(String url) throws IOException {
		
		StringBuilder sb = new StringBuilder();

		  Connection con = Jsoup.connect(url);
		  System.out.println("25" +url);

		    /* this browseragant thing is important to trick servers into sending us the LARGEST versions of the images */
//		    con.userAgent(Constants.BROWSER_USER_AGENT);
		    Document document = con.get();
		String title = null;
		String imageUrl = null;
		String domain = null;

		try {
			URI uri = new URI(url);
			domain = uri.getHost();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		Elements metaOgTitle = document.select("meta[property=og:title]");

		if (metaOgTitle != null) {
			title = metaOgTitle.attr("content");

			if (title == null) {
				title = document.title();
			}
		}

		Elements metaOgImage = document.select("meta[property=og:image]");
		if (metaOgImage != null) {
			imageUrl = metaOgImage.attr("content");
		}
		return new UrlData(title, imageUrl, domain);
	}


}
