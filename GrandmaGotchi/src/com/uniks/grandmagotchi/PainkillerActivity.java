package com.uniks.grandmagotchi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.uniks.grandmagotchi.data.ClotheAdapter;
import com.uniks.grandmagotchi.data.MedicAdapter;
import com.uniks.grandmagotchi.util.Message;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class PainkillerActivity extends Activity {

	// XML node keys
	static final String KEY_MEDDATA = "painkiller"; // parent node
	static final String KEY_ID = "id";
	public static final String KEY_MED = "med";
	public static final String KEY_TIME = "time";
	public static final String KEY_ICON = "icon";
	public static final String KEY_COUNT = "count";

	ListView list;
	ClotheAdapter adapter = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_meal_popup);

		try {

			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(getAssets().open("mealdata.xml"));

			final ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();

			doc.getDocumentElement().normalize(); // getting DOM element

			NodeList mList = doc.getElementsByTagName(KEY_MEDDATA);

			for (int i = 0; i < mList.getLength(); i++) {
				
				// creating new HashMap
				HashMap<String, String> map = new HashMap<String, String>();
				// Element e = (Element) mList.item(i);
				Node firstMList = mList.item(i);
				if (firstMList.getNodeType() == Node.ELEMENT_NODE) {

					Element firstMealElement = (Element) firstMList;
					
					NodeList idList = firstMealElement.getElementsByTagName(KEY_ID);
					Element firstIdElement = (Element) idList.item(0);
					NodeList textIdList = firstIdElement.getChildNodes();
					// --id
					map.put(KEY_ID, ((Node) textIdList.item(0)).getNodeValue().trim());

					NodeList mealList = firstMealElement.getElementsByTagName(KEY_MED);
					Element firstMElement = (Element) mealList.item(0);
					NodeList textMealList = firstMElement.getChildNodes();
					
					// --Meal
					map.put(KEY_MED, ((Node) textMealList.item(0)).getNodeValue().trim());

					NodeList infoList = firstMealElement.getElementsByTagName(KEY_TIME);
					Element firstInfoElement = (Element) infoList.item(0);
					NodeList textInfoList = firstInfoElement.getChildNodes();
					
					// --Meal Information
					map.put(KEY_TIME, ((Node) textInfoList.item(0)).getNodeValue().trim());

					NodeList iconList = firstMealElement.getElementsByTagName(KEY_ICON);
					Element firstIconElement = (Element) iconList.item(0);
					NodeList textIconList = firstIconElement.getChildNodes();
					// --Meal icon
					map.put(KEY_ICON, ((Node) textIconList.item(0)).getNodeValue().trim());

					// adding HashList to ArrayList
					data.add(map);
				}
			}

			// Getting adapter by passing xml data ArrayList
			final MedicAdapter adapter = new MedicAdapter(this, data);
			list = (ListView) findViewById(R.id.list);

			Log.i("BEFORE", "<<------------- Before SetAdapter-------------->>");

			list.setAdapter(adapter);
			
			
			

			Log.i("AFTER", "<<------------- After SetAdapter-------------->>");

			// Click event for single list row
			list.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					
//					switch (position) {
//	                case 0:
//                	break;}
					//TODO Hier Reaktionen auf klicken der items ( Oma nicht mehr hungrieg etc.)
					Message.message(getBaseContext(), "Granny tooks her medicine!");
					
					PainkillerActivity.this.finish();
										
				}

			});

		} catch (IOException ex) {
			Log.e("Error", ex.getMessage());
		} catch (Exception ex) {
			Log.e("Error", "Loading exception");
		}
	}
}
