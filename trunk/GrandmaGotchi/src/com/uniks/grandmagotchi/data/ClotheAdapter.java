package com.uniks.grandmagotchi.data;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.uniks.grandmagotchi.R;
import com.uniks.grandmagotchi.R.color;
import com.uniks.grandmagotchi.WarderobeActivity;
import com.uniks.grandmagotchi.util.Root;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("ResourceAsColor")
public class ClotheAdapter extends BaseAdapter {

	List<HashMap<String, String>> data;
	ViewHolder holder;
	LayoutInflater inflater;

	public ClotheAdapter() {
		// TODO Auto-generated constructor stub
	}

	public ClotheAdapter(Activity a, List<HashMap<String, String>> d) {
		this.data = d;
		inflater = (LayoutInflater) a
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	static class ViewHolder {
		TextView meal;
		TextView information;
		ImageView ic_image;
		TextView tvFoodCount;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;

		if (convertView == null) {
			vi = inflater.inflate(R.layout.meal_list_row, null);
			holder = new ViewHolder();

			holder.meal = (TextView) vi.findViewById(R.id.tvMeal); // clothe name
			holder.information = (TextView) vi.findViewById(R.id.tvInformation); // description
			holder.ic_image = (ImageView) vi.findViewById(R.id.list_image); // clothe image
			holder.tvFoodCount = (TextView) vi.findViewById(R.id.tvFoodCount);

			vi.setTag(holder);

		} else {
			
			holder = (ViewHolder) vi.getTag();
		}

		// // Setting all values in listview
		holder.meal.setText(data.get(position).get(WarderobeActivity.KEY_CLOTHE));
		holder.information.setText(data.get(position).get(WarderobeActivity.KEY_TIME));

		// Setting an image
		String uri = "drawable/" + data.get(position).get(WarderobeActivity.KEY_ICON);
		int imageResource = vi
				.getContext()
				.getApplicationContext()
				.getResources()
				.getIdentifier(
						uri,
						null,
						vi.getContext().getApplicationContext()
								.getPackageName());
		Drawable image = vi.getContext().getResources()
				.getDrawable(imageResource);
		holder.ic_image.setImageDrawable(image);
		
		String counter = data.get(position).get(WarderobeActivity.KEY_COUNT);
		Iterator it = data.get(position).entrySet().iterator();
		while (it.hasNext()) 
		{
		        Map.Entry pairs = (Map.Entry)it.next();
//		        System.out.println(pairs.getKey() + " = " + pairs.getValue());
		        for(ClotheAttributes clotheItem : Root.getClotheList())
		        {
		        	if(pairs.getValue().equals(clotheItem.getName()))
		        	{
		        		if(clotheItem.isDirty())
		        		{
		        			holder.tvFoodCount.setText(R.string.dirty);
		        			holder.tvFoodCount.setTextColor(R.color.currentClothe);
		        		}
		        		else
		        		{
		        			holder.tvFoodCount.setText(R.string.clean);
		        			holder.tvFoodCount.setTextColor(R.color.currentClothe);
		        		}
		        		if(clotheItem.isCurrentDress())
		        		{
		        			holder.tvFoodCount.setText(R.string.current);
		        			holder.tvFoodCount.setTextColor(0xFF00FF00);
		        		}
		        	}
		        }
		 }

		return vi;

	}
}
