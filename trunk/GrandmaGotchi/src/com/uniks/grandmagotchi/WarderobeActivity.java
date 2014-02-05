package com.uniks.grandmagotchi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.uniks.grandmagotchi.data.ClotheAdapter;
import com.uniks.grandmagotchi.data.ClotheAttributes;
import com.uniks.grandmagotchi.data.MealAdapter;
import com.uniks.grandmagotchi.util.Message;
import com.uniks.grandmagotchi.util.Root;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

public class WarderobeActivity extends Activity {

	// XML node keys
	static final String KEY_CLOTHEDATA = "clothedata"; // parent node
	static final String KEY_ID = "id";
	public static final String KEY_CLOTHE = "clothes";
	public static final String KEY_TIME = "time";
	public static final String KEY_ICON = "icon";
	public static final String KEY_COUNT = "count";

	ListView list;
	MealAdapter adapter = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_meal_popup);
		
		int countItems = 0;
        
        for(ClotheAttributes clotheItem : Root.getClotheList())
        {
        	if(clotheItem.isDirty())
        	{
        		countItems++;
        	}
        }
        
        if(countItems == 4)
        {
        	Message.message(this, "No Items, wash your clothes");
        	WarderobeActivity.this.finish();
        }

		try {

			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(getAssets().open("mealdata.xml"));

			final ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();

			doc.getDocumentElement().normalize(); // getting DOM element

			NodeList mList = doc.getElementsByTagName(KEY_CLOTHEDATA);

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

					NodeList mealList = firstMealElement.getElementsByTagName(KEY_CLOTHE);
					Element firstMElement = (Element) mealList.item(0);
					NodeList textMealList = firstMElement.getChildNodes();
					
					for(ClotheAttributes clotheAttribute : Root.getClotheList())
					{
						if(clotheAttribute.getName().equals(firstMElement.getTextContent()))
						{
							
							NodeList mealCounter = firstMealElement.getElementsByTagName(KEY_COUNT);
							Element firstCElement = (Element) mealCounter.item(0);
							NodeList textCountList = firstCElement.getChildNodes();
							
							map.put(KEY_COUNT, ((Node) textCountList.item(0)).getNodeValue().trim());
							
							
						}
					}
					
					// --Meal
					map.put(KEY_CLOTHE, ((Node) textMealList.item(0)).getNodeValue().trim());

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
			final ClotheAdapter adapter = new ClotheAdapter(this, data);
			list = (ListView) findViewById(R.id.list);

			Log.i("BEFORE", "<<------------- Before SetAdapter-------------->>");

			list.setAdapter(adapter);
			
			
			

			Log.i("AFTER", "<<------------- After SetAdapter-------------->>");

			// Click event for single list row
			list.setOnItemClickListener(new OnItemClickListener() {

				private ImageView grannyClothes;

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					
					//TODO Hier Reaktionen auf klicken der items ( Oma nicht mehr hungrieg etc.)
					
					

					
					Iterator it = data.get(position).entrySet().iterator();
					 while (it.hasNext()) 
					 {
					        Map.Entry pairs = (Map.Entry)it.next();
					        System.out.println(pairs.getKey() + " = " + pairs.getValue());
					        for(ClotheAttributes clotheItem : Root.getClotheList())
					        {
					        	if(pairs.getValue().equals(clotheItem.getName()))
					        	{
					        		if(clotheItem.isDirty())
					        		{
					        			Message.message(getBaseContext(), "This Dress is dirty!");
//										data.remove(position);
							            adapter.notifyDataSetChanged(); 
					        		}
					        		else if(!clotheItem.isDirty())
					        		{
//					        			if(clotheItem.getName().equals("Pinky"))
//					        			{
//					        				grannyClothes.setImageResource(R.drawable.image_grandma_confused);
//					        			} 
//					        			else if(clotheItem.getName().equals("Red skull"))
//					        			{
//					        				grannyClothes.setImageResource(R.drawable.image_grandma_confused_red);
//					        			}
//					        			else if(clotheItem.getName().equals("Black Dress"))
//					        			{
//					        				grannyClothes.setImageResource(R.drawable.image_grandma_confused_black);
//					        			}
//					        			else if(clotheItem.getName().equals("Camo"))
//					        			{
//					        				grannyClothes.setImageResource(R.drawable.image_grandma_confused_green);
//					        			}
//					        			else if(clotheItem.getName().equals("Sunny"))
//					        			{
//					        				grannyClothes.setImageResource(R.drawable.image_grandma_confused_blue);
//					        			}
					        			Message.message(getBaseContext(), "Changed clothes!");
					        			for(ClotheAttributes cA : Root.getClotheList())
					        			{
					        				if(cA.isCurrentDress())
					        				{
					        					cA.setCurrentDress(false);
					        					cA.setDirty(true);
					        					break;
					        				}
					        			}
					        			clotheItem.setCurrentDress(true);
					        			adapter.notifyDataSetChanged(); 
//					        			
//					        			if(clotheItem.getCount() == 0)
//						        		{
//											data.remove(position);
//								            adapter.notifyDataSetChanged(); 
//						        		}
					        			
					        		}
					        	}
					        }
					        
					        int i = 0;
					        
					        for(ClotheAttributes clotheItem : Root.getClotheList())
					        {
					        	if(clotheItem.isDirty())
					        	{
					        		i++;
					        	}
					        }
					        
					        if(i == 5)
					        {
					        	WarderobeActivity.this.finish();
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





//<Button
//android:id="@+id/one"
//android:layout_width="100dp"
//android:layout_height="100dp"
//android:layout_alignParentRight="true"
//android:layout_marginLeft="5dp"
//android:text="@string/empty_Field"
//android:textSize="70sp" />
//
//<Button
//android:id="@+id/two"
//android:layout_width="100dp"
//android:layout_height="100dp"
//android:layout_alignTop="@id/one"
//android:layout_toLeftOf="@id/one"
//android:text="@string/empty_Field"
//android:textSize="70sp" />
//
//<Button
//android:id="@+id/three"
//android:layout_width="100dp"
//android:layout_height="100dp"
//android:layout_alignRight="@+id/six"
//android:layout_alignTop="@id/two"
//android:layout_marginLeft="5dp"
//android:padding="0dp"
//android:text="@string/empty_Field"
//android:textSize="70sp" />
//
//<Button
//android:id="@+id/four"
//android:layout_width="100dp"
//android:layout_height="100dp"
//android:layout_alignParentRight="true"
//android:layout_below="@id/one"
//android:layout_marginLeft="5dp"
//android:text="@string/empty_Field"
//android:textSize="70sp" />
//
//<Button
//android:id="@+id/five"
//android:layout_width="100dp"
//android:layout_height="100dp"
//android:layout_alignTop="@id/four"
//android:layout_marginLeft="5dp"
//android:layout_toLeftOf="@id/four"
//android:text="@string/empty_Field"
//android:textSize="70sp" />
//
//<Button
//android:id="@+id/six"
//android:layout_width="100dp"
//android:layout_height="100dp"
//android:layout_alignTop="@id/five"
//android:layout_marginLeft="5dp"
//android:layout_toLeftOf="@id/five"
//android:text="@string/empty_Field"
//android:textSize="70sp" />
//
//<Button
//android:id="@+id/seven"
//android:layout_width="100dp"
//android:layout_height="100dp"
//android:layout_alignParentRight="true"
//android:layout_below="@id/four"
//android:layout_marginLeft="5dp"
//android:text="@string/empty_Field"
//android:textSize="70sp" />
//
//<Button
//android:id="@+id/eight"
//android:layout_width="100dp"
//android:layout_height="100dp"
//android:layout_alignTop="@id/seven"
//android:layout_marginLeft="5dp"
//android:layout_toLeftOf="@id/seven"
//android:text="@string/empty_Field"
//android:textSize="70sp" />
//
//<Button
//android:id="@+id/nine"
//android:layout_width="100dp"
//android:layout_height="100dp"
//android:layout_alignTop="@id/eight"
//android:layout_marginLeft="5dp"
//android:layout_toLeftOf="@id/eight"
//android:text="@string/empty_Field"
//android:textSize="70sp" />
//
//<Button
//android:id="@+id/new_game"
//android:layout_width="wrap_content"
//android:layout_height="wrap_content"
//android:layout_alignParentBottom="true"
//android:layout_centerHorizontal="true"
//android:layout_marginBottom="29dp"
//android:onClick="btnOnClickGame"
//android:text="@string/new_game" />
