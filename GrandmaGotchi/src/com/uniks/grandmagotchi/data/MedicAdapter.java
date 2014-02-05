package com.uniks.grandmagotchi.data;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.uniks.grandmagotchi.PainkillerActivity;
import com.uniks.grandmagotchi.R;
import com.uniks.grandmagotchi.WarderobeActivity;
import com.uniks.grandmagotchi.util.Root;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MedicAdapter extends BaseAdapter {

	List<HashMap<String, String>> data;
	ViewHolder holder;
	LayoutInflater inflater;

	public MedicAdapter() {
		// TODO Auto-generated constructor stub
	}

	public MedicAdapter(Activity a, List<HashMap<String, String>> d) {
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

			holder.meal = (TextView) vi.findViewById(R.id.tvMeal); // medic name
			holder.information = (TextView) vi.findViewById(R.id.tvInformation); // description
			holder.ic_image = (ImageView) vi.findViewById(R.id.list_image); // medic image
			holder.tvFoodCount = (TextView) vi.findViewById(R.id.tvFoodCount);

			vi.setTag(holder);

		} else {
			
			holder = (ViewHolder) vi.getTag();
		}

		// // Setting all values in listview
		holder.meal.setText(data.get(position).get(PainkillerActivity.KEY_MED));
		holder.information.setText(data.get(position).get(PainkillerActivity.KEY_TIME));

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

		return vi;

	}
}
