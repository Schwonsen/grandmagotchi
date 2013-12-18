package com.uniks.grandmagotchi.rooms;

import com.uniks.grandmagotchi.R;
import com.uniks.grandmagotchi.util.Message;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class FragmentBedroom extends Fragment implements SensorEventListener
{	
	
	private SensorManager sensorManager;
	private Sensor proxSensor;
	
	private Context context;

	public FragmentBedroom()
	{
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		
		context = getActivity();
		
		sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		proxSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
		sensorManager.registerListener(this, proxSensor, SensorManager.SENSOR_DELAY_NORMAL);
		
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_bedroom, container,
				false);
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event)
	{
		Message.message(context, "Light has changed to " + event.values[0]);
	}

}
