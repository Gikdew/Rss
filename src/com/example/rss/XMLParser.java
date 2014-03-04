package com.example.rss;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.content.Context;
import android.util.Log;

public class XMLParser {
	private URL url;
	Context contextor;

	public XMLParser(String url, Context contexto) {
		contextor = contexto;
		try {
			this.url = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public ArrayList < Video > parse() {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		ArrayList < Video > Videos = new ArrayList < Video > ();
		Video Video;

		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document dom = builder.parse(this.url.openConnection().getInputStream());
			Element root = dom.getDocumentElement();
			NodeList items = root.getElementsByTagName("item");
			for (int i = 0; i < items.getLength(); i++) {
				Video = new Video();
				Node item = items.item(i);
				NodeList properties = item.getChildNodes();
				//Log.i("NodeList", properties.toString());
				for (int j = 0; j < properties.getLength(); j++) {
					Node property = properties.item(j);
					String name = property.getNodeName();
					//Log.i("name", name);
					if (name.equalsIgnoreCase("title")) {
						Video.setTitle(property.getFirstChild().getNodeValue());
					} else if (name.equalsIgnoreCase("media:group")) {
						NodeList medList = property.getChildNodes();
						for (int w = 0; w < medList.getLength(); w++) {
							Node property1 = medList.item(w);
							String name1 = property1.getNodeName();
							if (name1.equalsIgnoreCase("media:thumbnail")) {
								Element e = (Element) property1;
								if (Integer.parseInt(e.getAttribute("height")) == 360) {
									String thumbUrl = e.getAttribute("url");
									Video.setImage(thumbUrl);
								}
								//Log.i("MediaThumbnail", thumbUrl);																	
							}
							//Log.i("name1", name1);
						}
					} else if (name.equalsIgnoreCase("description")) {

					} else if (name.equalsIgnoreCase("link")) {
						Video.setLink(property.getFirstChild().getNodeValue());
					} else if (name.equalsIgnoreCase("pubDate")) {

					}
				}
				Videos.add(Video);
				Log.i("Parsher", "Video Image: " + Video.getImage());
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return Videos;
	}
}