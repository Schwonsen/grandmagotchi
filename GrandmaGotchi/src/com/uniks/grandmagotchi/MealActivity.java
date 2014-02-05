package com.uniks.grandmagotchi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.uniks.grandmagotchi.data.FoodAttributes;
import com.uniks.grandmagotchi.data.MealAdapter;
import com.uniks.grandmagotchi.util.Message;
import com.uniks.grandmagotchi.util.Root;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class MealActivity extends Activity {

	// XML node keys
	private HashMap<String, Boolean> setNodeOneTime = new HashMap<String, Boolean>();
	static final String KEY_MEALDATA = "mealdata"; // parent node
	static final String KEY_ID = "id";
	public static final String KEY_MEAL = "meal";
	public static final String KEY_TIME = "time";
	public static final String KEY_ICON = "icon";
	public static final String KEY_COUNT = "count";

	ListView list;
	MealAdapter adapter = null;
	TextView tvFoodCount;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_meal_popup);
		
		int countItems = 0;
        
        for(FoodAttributes foodItem : Root.getFoodList())
        {
        	if(foodItem.getCount() == 0)
        	{
        		countItems++;
        	}
        }
        
        if(countItems == 9)
        {
        	Message.message(this, "No Items");
        	MealActivity.this.finish();
        }

		try {

			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(getAssets().open("mealdata.xml"));

			final ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();

			doc.getDocumentElement().normalize(); // getting DOM element

			NodeList mList = doc.getElementsByTagName(KEY_MEALDATA);

			for (int i = 0; i < mList.getLength(); i++) {
				
				boolean isOverNull = false;
				
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

					NodeList mealList = firstMealElement.getElementsByTagName(KEY_MEAL);
					Element firstMElement = (Element) mealList.item(0);
					NodeList textMealList = firstMElement.getChildNodes();
					
					for(FoodAttributes foodAttribute : Root.getFoodList())
					{
						if(foodAttribute.getName().equals(firstMElement.getTextContent()))
						{
							
							NodeList mealCounter = firstMealElement.getElementsByTagName(KEY_COUNT);
							Element firstCElement = (Element) mealCounter.item(0);
							NodeList textCountList = firstCElement.getChildNodes();

							map.put(KEY_COUNT, ((Node) textCountList.item(0)).getNodeValue().trim());
							
							
						}
					}
					
					// --Meal
					map.put(KEY_MEAL, ((Node) textMealList.item(0)).getNodeValue().trim());

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

					data.add(map);
					

				}
			}
			
			

			// Getting adapter by passing xml data ArrayList
			final MealAdapter adapter = new MealAdapter(this, data);
			list = (ListView) findViewById(R.id.list);

			Log.i("BEFORE", "<<------------- Before SetAdapter-------------->>");

			list.setAdapter(adapter);
			
			
			

			Log.i("AFTER", "<<------------- After SetAdapter-------------->>");

			// Click event for single list row
			list.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					
					//TODO Hier Reaktionen auf klicken der items ( Oma nicht mehr hungrieg etc.)

					
					Iterator it = data.get(position).entrySet().iterator();
					 while (it.hasNext()) 
					 {
					        Map.Entry pairs = (Map.Entry)it.next();
//					        System.out.println(pairs.getKey() + " = " + pairs.getValue());
					        for(FoodAttributes foodItem : Root.getFoodList())
					        {
					        	if(pairs.getValue().equals(foodItem.getName()))
					        	{
					        		if(foodItem.getCount() == 0)
					        		{
									//	data.remove(position);
					        			Message.message(getBaseContext(), "You don't have any " + foodItem.getName() + " left");
							            adapter.notifyDataSetChanged(); 
					        		}
					        		else if(foodItem.getCount() > 0)
					        		{
					        			if(!foodItem.getName().equals("Water"))
					        			{
					        				Message.message(getBaseContext(), "The granny is no longer hungry.");
					        			}
					        			else
					        			{
					        				Message.message(getBaseContext(), "The granny is no longer thirsty.");
					        			}
					        			foodItem.setCount(foodItem.getCount() - 1);
					        			adapter.notifyDataSetChanged(); 
					        			MealActivity.this.finish();
					        			
//					        			if(foodItem.getCount() == 0)
//						        		{
//											data.remove(position);
//								            adapter.notifyDataSetChanged(); 
//						        		}
					        		}
					        	}
					        }
					        
					        int i = 0;
					        
					        for(FoodAttributes foodItem : Root.getFoodList())
					        {
					        	if(foodItem.getCount() == 0)
					        	{
					        		i++;
					        	}
					        }
					        
					        if(i == 9)
					        {
					        	MealActivity.this.finish();
					        }
					         
					 }

				}

			});

		} catch (IOException ex) {
			Log.e("Error", ex.getMessage());
		} catch (Exception ex) {
			Log.e("Error", "Loading exception");
		}
	}
}
