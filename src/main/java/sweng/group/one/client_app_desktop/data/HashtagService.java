package sweng.group.one.client_app_desktop.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.mapsforge.core.graphics.Bitmap;
import org.mapsforge.core.model.LatLong;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import sweng.group.one.client_app_desktop.mapping.EventMarker;
import sweng.group.one.client_app_desktop.sceneControl.MapScene;


// Rough and ready test - need to run post tests first to add a Hashtag to the server. 
public class HashtagService {

	private static String hashtagURL = "http://localhost:8080/api/v1/hashtag";

	/**
     * Retrieves hashtags from the server and returns them in JSON.
     * @return JSONArray of the hashtags. 
     */
	public static JSONArray retrieveHashtagsAsJSON() throws IOException, AuthenticationException {

		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder().url(hashtagURL).get().build();
		Response response = client.newCall(request).execute();
		int statusCode = response.code();

		if (statusCode == 200) {
			return new JSONArray(response.body().string());
		} else if (statusCode == 403) {
			throw new AuthenticationException("403 Forbidden server response - check your access token is valid. ");
		} else if (statusCode == 500) {
			throw new RuntimeException("500 server response - server error. Check the server code / constraints. ");
		} else if (statusCode == 400) {
			throw new RuntimeException("400 server response, bad request - check the request is valid");
		} else {
			throw new RuntimeException(statusCode + "server response, unknown error - check code and debug.");
		}
	}
	

	/**
     * Retrieves hashtags from the server and returns them as an array of EventMarkers. 
     * @param mapScene the MapScene for constructing the EventMarkers. 
     * @param bitmap the Bitmap the EventMarkers will use for the pin. 
     * @return ArrayList<EventMarker> ArrayList of the EventMarkers.
     */
	public static ArrayList<EventMarker> retrieveHashtagsAsEventMarkers(MapScene mapScene, Bitmap bitmap) throws IOException, AuthenticationException {

		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder().url(hashtagURL).get().build();
		Response response = client.newCall(request).execute();

		int statusCode = response.code();

		if (statusCode == 200) {
			JSONArray hashtagsJSON = new JSONArray(response.body().string());

			
			  ArrayList<EventMarker> hashtags = new ArrayList<EventMarker>();
			  
			  for (int i = 0; i < hashtagsJSON.length(); i++) { 
				  JSONObject hashtagJSON = hashtagsJSON.getJSONObject(i);
				  Double latitude = hashtagJSON.getDouble("latitude"); 
				  Double longitude = hashtagJSON.getDouble("longitude");
				  LatLong location = new LatLong(latitude, longitude); 
				  EventMarker hashtag = new EventMarker(mapScene, location, bitmap);
				  hashtag.setName(hashtagJSON.getString("name"));
				  hashtags.add(hashtag);
				  }
			  return hashtags;
			 
		} else if (statusCode == 403) {
			throw new AuthenticationException("403 Forbidden server response - check your access token is valid. ");
		} else if (statusCode == 500) {
			throw new RuntimeException("500 server response - server error. Check the server code / constraints. ");
		} else if (statusCode == 400) {
			throw new RuntimeException("400 server response, bad request - check the request is valid");
		} else 
		{
			throw new RuntimeException(statusCode + "server response, unknown error - check code and debug.");
		}
	}
	
	
	/**
     * Updates the name of a hashtag on the server.  
     * @param hashtag The EventMarker to be changed. 
     * @param newName the new name of the EventMarker (String).
     * @param authToken the authorisation token for the user. Must be admin or verified or 403 error will occur.
     * @return ArrayList<EventMarker> ArrayList of the EventMarkers.
     */
	public static EventMarker updateHashtagName(EventMarker hashtag, String newName, String authToken) throws IOException, AuthenticationException {
		
		OkHttpClient client = new OkHttpClient();
		
		RequestBody body = new MultipartBody.Builder()
				.addFormDataPart("name", hashtag.getName())
				.addFormDataPart("newName", newName)
				.build();
				
		Request request = new Request.Builder()
				.url(hashtagURL)
				.addHeader("Authorization", "Bearer " + authToken)
				.post(body)
				.build();
		
		Response response = client.newCall(request).execute();
		
		int statusCode = response.code();
		
		if (statusCode == 200) {
			hashtag.setName(newName);
			return hashtag;
			 
		} else if (statusCode == 403) {
			throw new AuthenticationException("403 Forbidden server response - check your access token is valid. ");
		} else if (statusCode == 500) {
			throw new RuntimeException("500 server response - server error. Check the server code / constraints. ");
		} else if (statusCode == 400) {
			throw new RuntimeException("400 server response, bad request - check the request is valid");
		} else {
			throw new RuntimeException(statusCode + "server response, unknown error - check code and debug.");
		}
		
	}
}
