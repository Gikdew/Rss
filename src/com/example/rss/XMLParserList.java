package com.example.rss;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.content.Context;
import android.util.Log;

public class XMLParserList {
	private URL url;
	Context contextor;
	ConfigClass c = new ConfigClass();

	public XMLParserList(String url, Context contexto) {
		contextor = contexto;
		try {
			this.url = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	public ArrayList < Video > parse() {
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		ArrayList < Video > Videos = new ArrayList < Video > ();
		Video Video;
		DateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy kk:mm:ss Z", Locale.ENGLISH);

		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document dom = builder.parse(this.url.openConnection().getInputStream());
			//Log.i("Dom", dom.toString());
			Element root = dom.getDocumentElement();
			NodeList items = root.getElementsByTagName("entry");
			for (int i = 0; i < items.getLength(); i++) {
				Video = new Video();
				Node item = items.item(i);
				NodeList properties = item.getChildNodes();
				//Log.i("NodeList", String.valueOf(items.getLength()));
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
									Video.setImageUrl(thumbUrl);
								}
								//Log.i("MediaThumbnail", thumbUrl);																	
							}else if(name1.equalsIgnoreCase("media:content")){
								Element e = (Element) property1;
								int duration = Integer.parseInt(e.getAttribute("duration"));
								Video.setDuration(duration);
							}
							
						}
					} else if (name.equalsIgnoreCase("description")) {
					} else if (name.equalsIgnoreCase("link")) {
						Element e = (Element) property;	
						//Log.i("Attribute Rel", e.getAttribute("rel"));
						if(e.getAttribute("rel").equalsIgnoreCase("alternate")){
							String link = e.getAttribute("href");
							Video.setLink(link);
						}
												
						
					} else if (name.equalsIgnoreCase("publishedd")) {
						Video.setDate(formatter.parse(""+property.getFirstChild().getNodeValue()));				
					}
				}
				Videos.add(Video);
				//Log.i("Parsher", "Video Image: " + Video.getLink());
			}
		} catch (Exception e) {
			//Log.i("XmlParserProblem", e.toString());
			throw new RuntimeException(e);
		}
		return Videos;
	}
}