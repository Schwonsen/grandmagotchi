package com.uniks.grandmagotchi.rooms;

import com.uniks.grandmagotchi.R;
import com.uniks.grandmagotchi.util.Message;
import com.uniks.grandmagotchi.util.Root;

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
import android.widget.ImageButton;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class FragmentBedroom extends Fragment implements SensorEventListener
{	
	
	final static private int VISIBLE = 0;
	
	private SensorManager sensorManager;
	private Sensor proxSensor;
	
	private Context context;

	private View view;
	
	private ImageButton btnWakeUp;

	public FragmentBedroom()
	{
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		
		context = getActivity();
		view = inflater.inflate(R.layout.fragment_bedroom, container, false);
		
		sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		proxSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
		sensorManager.registerListener(this, proxSensor, SensorManager.SENSOR_DELAY_NORMAL);

		btnWakeUp = (ImageButton) view.findViewById(R.id.btn_bedroom_wake_up);
		// Inflate the layout for this fragment
		return view;
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event)
	{
		if(event.values[0] == 0.0f && !Root.getAttributes().isSleeping())
		{
			Message.message(context, "You turned the light off, Grandma sleeps now");
			btnWakeUp.setVisibility(VISIBLE);
			Root.getAttributes().setSleeping(true);

		}
	}

}
