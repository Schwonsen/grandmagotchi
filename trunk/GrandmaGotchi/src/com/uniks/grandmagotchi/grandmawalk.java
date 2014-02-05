package com.uniks.grandmagotchi;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.uniks.grandmagotchi.util.Needs;
import com.uniks.grandmagotchi.util.Root;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

	public class grandmawalk extends FragmentActivity implements
    ConnectionCallbacks,
    OnConnectionFailedListener,
    OnMyLocationButtonClickListener, LocationListener, android.location.LocationListener, LocationSource {
private static final int RQS_GooglePlayServices = 0;
public GoogleMap mMap;
private Toast toast;
private boolean close = false;
private double oldPositionlatitude=0;
private double oldPositionlongitude=0;
private double newPositionlatitude=0;
private double newPositionlongitude=0;
private int gesamtdistanz=0;


		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			try {setContentView(R.layout.grandmawalking);
			GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());			
				    
				    } catch (IllegalStateException e) {
				    	  Toast.makeText(this, "Sie müssen GPS aktiviert haben um diese Funktion nutzen zu können", Toast.LENGTH_SHORT).show();
				    }
			
			
			  int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
			  
			  if (resultCode == ConnectionResult.SUCCESS){
			   //Toast.makeText(getApplicationContext(), 
			     //"isGooglePlayServicesAvailable SUCCESS", 
			     //Toast.LENGTH_LONG).show();
			  }else{
			   GooglePlayServicesUtil.getErrorDialog(resultCode, this, RQS_GooglePlayServices);
			  }
			    LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
			    String provider = lm.getBestProvider(new Criteria(), true);
			    lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100, 0, this);
			  listener();
	}

		public void onStop(){
			super.onStop();
			close=true;
		}
		

		
		public void listener(){
			if (close==false){
			Handler handler = new Handler(); 
		    handler.postDelayed(new Runnable() { 
		         public void run() { 
		        	 
		        	  getpositiongrandma();
		              listener();
		        	 }
		         
		    }, 6800);
			} 
		}
		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.main, menu);
			MenuInflater inflater = getMenuInflater();
	        inflater.inflate(R.menu.grandmawalkingmenu, menu);
	        setgetpositionmap();
			return true;
		}
		
		public void setgetpositionmap() {
		       // Do a null check to confirm that we have not already instantiated the map.
	        if (mMap == null) {

	            // Try to obtain the map from the SupportMapFragment.
	        	 mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map2))
		                    .getMap();
	            // Check if we were successful in obtaining the map.
	            if (mMap != null) {
	                mMap.setMyLocationEnabled(true);
	            	mMap.getUiSettings().setZoomControlsEnabled(false);
	                mMap.setOnMyLocationButtonClickListener(this);
	                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
	    			//mMap.getUiSettings().setMyLocationButtonEnabled(true);
					/*mMap.addMarker(new MarkerOptions()
			        .position(new LatLng(0, 0)));*/

	                
	            }
	        }
		}
		
		public void getpositiongrandma(MenuItem item) {
			try{
				Location myLocation = mMap.getMyLocation();
				double longitude = myLocation.getLongitude();
				double latitude = myLocation.getLatitude();
				mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 18));
			    }catch(Exception e){
			    	if(toast!=null){toast.cancel();}
					toast =Toast.makeText(getApplicationContext(), 
						     "Standort konnte noch nicht ermittelt werden. Dies kann einige Sekunden dauern. Bitte stellen sie außerdem sicher, dass die Standortdienste-Einstellungen ihres Gerätes eine Standortabfrage erlauben und GPS eingeschaltet ist.", 
						     Toast.LENGTH_LONG);
					toast.show();
				}
				}
		public void getpositiongrandma() {
			try{				
				oldPositionlongitude= newPositionlongitude;
				oldPositionlatitude= newPositionlatitude;
				if(toast!=null){toast.cancel();}
				Location myLocation = mMap.getMyLocation();
				double longitude = myLocation.getLongitude();
				double latitude = myLocation.getLatitude();
				newPositionlongitude=longitude;
				newPositionlatitude=latitude;
				if(oldPositionlongitude==0){ //position updaten + marker setzen
					oldPositionlongitude=newPositionlongitude;
					oldPositionlatitude=newPositionlatitude;
	                mMap.addMarker(new MarkerOptions()
			        .position(new LatLng(latitude, longitude))
			        .title("p"))
			        .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.grandmakopfklein));
				}
				//Kameraposition updaten mit Zoom
				mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 18));
				mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 18));
				
				
				//neue locations aus ermittelten Werten erstellen
				Location locationA = new Location("point A");

				locationA.setLatitude(oldPositionlongitude);
				locationA.setLongitude(oldPositionlatitude);

				Location locationB = new Location("point B");

				locationB.setLatitude(newPositionlongitude);
				locationB.setLongitude(newPositionlatitude);
                //distanz berechen + auswerten (erst ab 3 meterndistanz wird wegen gps ungenauigkeiten geupdatet
				//da man sonst sozusagen "im sitzen laufen könnte"
				float distance = locationA.distanceTo(locationB);
				if(distance>=3){
				mMap.addMarker(new MarkerOptions()
			        .position(new LatLng(latitude, longitude))
			        .title("p"))
			        .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.grandmakopfklein));
				gesamtdistanz = gesamtdistanz + (int) distance;
				//Abbruch/Endbedingung und erfüllung des Needs
				if (gesamtdistanz>=30){
	            	Root.getUniqueRootInstance().removeNeed(Needs.WALK);
	            	closegrandmawalking();
	            	Toast.makeText(getApplicationContext(), 
						     "Spaziergang erfolgreich beendet" , 
						     Toast.LENGTH_LONG).show();
	            }else{//Distanzausgabe aktualisieren
				TextView gesamtdistanztext = (TextView)findViewById(R.id.GesamtDistanzWert);
	            gesamtdistanztext.setText(gesamtdistanz +" Meter ");
				Toast.makeText(getApplicationContext(), 
					     "..erfolgreich.."+" + " +(int)distance+ " Meter", 
					     Toast.LENGTH_SHORT).show();}
				}else{//ausgabe falls zurückgelegte Distanz in vorgegebenen Zeitfenster zu niedrig
					Toast.makeText(getApplicationContext(), 
						     "..erfolgreich.."+ "Sie sind zu langsam", 
						     Toast.LENGTH_SHORT).show();}
				TextView gesamtdistanztext = (TextView)findViewById(R.id.GesamtDistanzWert);
	            gesamtdistanztext.setText(gesamtdistanz +" Meter ");
				
	            
				
				
			    }catch(Exception e){
			    	if(toast!=null){toast.cancel();}
					toast = Toast.makeText(getApplicationContext(), 
						     "..Signal schwach..", 
						     Toast.LENGTH_SHORT);
					toast.show();
				}
			}
		//schließen durch menubutton aufruf
		public void closegrandmawalking(MenuItem item) {		
				if(toast!=null){toast.cancel();}
			    close=true;
			    finish();
			    
			
		}
		//schließen durch ausruf durch getpostiongrandma methode beim "fertigem" spaziergang
		public void closegrandmawalking() {
			    if(toast!=null){toast.cancel();}
			    close=true;
			    finish();
			
		}

		
		@Override
		public boolean onMyLocationButtonClick() {
			return false;
		}



		@Override
		public void onConnectionFailed(ConnectionResult arg0) {
			
		}

		@Override
		public void onConnected(Bundle arg0) {
			
			
		}

		@Override
		public void onDisconnected() {
			
			
		}

		@Override
		public void onProviderDisabled(String arg0) {
			
			
		}

		@Override
		public void onProviderEnabled(String arg0) {
			
			
		}

		@Override
		public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
			
			
		}
		

		@Override
		public void activate(OnLocationChangedListener arg0) {
			
		}

		@Override
		public void deactivate() {
			
		}


		@Override
		public void onLocationChanged(Location arg0) {
			
		}



}
