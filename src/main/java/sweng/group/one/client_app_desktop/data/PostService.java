package sweng.group.one.client_app_desktop.data;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xml.sax.SAXException;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import sweng.group.one.client_app_desktop.mapping.EventMarker;
import sweng.group.one.client_app_desktop.presentation.Presentation;

/**
 * Service class for handling all post related server tasks, i.e., uploading, retrieving, deleting, etc.
 * Communicates with a server at the specified URL to perform these tasks.
 * @author Paul Pickering & Fraser Todd
 */
public class PostService {
	private final static String postURL = "http://localhost:8080/api/v1/post";

	/**
	 * Retrieves posts from the server and returns them as an array of presentations. 
	 * Access Token provided will allow logged in users to retrieve posts not made by an Admin or Verified. 
	 * @param accessToken the authorisation token of the user making the request.
	 * @return ArrayList<Presentation> consisting of the posts (presentations).
	 * @throws IOException
	 * @throws AuthenticationException
	 * @throws SAXEException
	 * @throws ParserConfigurationException
	 */
	public static ArrayList<Presentation> retrievePostsPresentations(String accessToken)
			throws SAXException, ParserConfigurationException, IOException, AuthenticationException {

		int statusCode = 0;
		OkHttpClient client = new OkHttpClient();

		// Builds the request - simple get request, ID of is sent in URL.
		Request request = new Request.Builder().url(postURL).get().header("Authorization", "Bearer " + accessToken)
				.build();
		
		 // Get the system temp directory
	    String tempDirectoryPath = System.getProperty("java.io.tmpdir") + "/WhatsOn/posts/";

	    // Ensure the directory exists
	    File tempDirectory = new File(tempDirectoryPath);
	    if (!tempDirectory.exists()) {
	        tempDirectory.mkdirs();
	    }

		// Sends the request.
		Response response = client.newCall(request).execute();

		// Handling the response and generating the presentations (posts).
		statusCode = response.code();

		if (statusCode == 200) {
			// Success - now parses the response.
			JSONArray jsonArray = new JSONArray(response.body().string());
			ArrayList<Presentation> posts = new ArrayList<Presentation>();

			// Run through each post, add it to the post (presentation) list.
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject postJSON = jsonArray.getJSONObject(i);
				String xmlString = postJSON.getString("xmlContent");
				byte[] postXML = xmlString.getBytes();
				Path xmlPath = Files.createTempFile(tempDirectory.toPath(), "post", null);
				Files.write(xmlPath, postXML);
				
				Presentation postPres = new Presentation(xmlPath.toFile());
				posts.add(postPres);
			}
			return posts;
		} else if (statusCode == 403) {
			throw new AuthenticationException("Server returned 403 code - auth token not valid.");
		} else if (statusCode == 500) {
			throw new RuntimeException("500 server response - server error. Check the server code / constraints. ");
		} else if (statusCode == 400) {
			throw new RuntimeException("400 server response, bad request - check the request is valid");
		} else {
			throw new RuntimeException(statusCode + "server response, unknown error - check code and debug.");
		}
	}

	public static ArrayList<Presentation> retrievePostsPresentations()
			throws SAXException, ParserConfigurationException, IOException, AuthenticationException {

		int statusCode = 0;
		OkHttpClient client = new OkHttpClient();

		// Builds the request - simple get request, ID of is sent in URL.
		Request request = new Request.Builder().url(postURL).get().build();

	    // Get the system temp directory
	    String tempDirectoryPath = System.getProperty("java.io.tmpdir") + "/WhatsOn/posts/";

	    // Ensure the directory exists
	    File tempDirectory = new File(tempDirectoryPath);
	    if (!tempDirectory.exists()) {
	        tempDirectory.mkdirs();
	    }
		// Sends the request.
		Response response = client.newCall(request).execute();

		// Handling the response and generating the presentations (posts).
		statusCode = response.code();

		if (statusCode == 200) {
			// Success - now parses the response.
			JSONArray jsonArray = new JSONArray(response.body().string());
			ArrayList<Presentation> posts = new ArrayList<Presentation>();

			// Run through each post, add it to the post (presentation) list.
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject postJSON = jsonArray.getJSONObject(i);
				String xmlString = postJSON.getString("xmlContent");
				byte[] postXML = xmlString.getBytes();
				Path xmlPath = Files.createTempFile(tempDirectory.toPath(), "post", null);
				Files.write(xmlPath, postXML);

				Presentation postPres = new Presentation(xmlPath.toFile());
				posts.add(postPres);
			}
			return posts;

		} else if (statusCode == 403) {
			throw new AuthenticationException("Server returned 403 code - auth token not valid.");
		} else if (statusCode == 500) {
			throw new RuntimeException("500 server response - server error. Check the server code / constraints. ");
		} else if (statusCode == 400) {
			throw new RuntimeException("400 server response, bad request - check the request is valid");
		} else {
			throw new RuntimeException(statusCode + "server response, unknown error - check code and debug.");
		}
	}

	/**
	 * Retrieves posts from the server and returns them as an array of presentations. 
	 * Only includes posts from the hashtag specified. 
	 * Access Token provided will allow logged in users to retrieve posts not made by an Admin or Verified.
	 * @param hashtag The hashtag attached to the desired posts.  
	 * @param accessToken the authorisation token of the user making the request.
	 * @return ArrayList<Presentation> consisting of the posts (presentations).
	 * @throws IOException File system error. Error saving XMLs to disk.  
	 * @throws AuthenticationException Invalid authorisation. Try refreshing access token or method for logged out users. 
	 * @throws SAXEException issue loading the XMLs into presentations. Check XML content. 
	 * @throws ParserConfigurationException issue loading the XMLs into presentations. Check XML content. 
	 */
	public static ArrayList<Presentation> retrievePostsByHashtagAsPresentations(String hashtag, String accessToken)
			throws SAXException, ParserConfigurationException, IOException, AuthenticationException {
		
		int statusCode = 0;
		OkHttpClient client = new OkHttpClient();

		// Builds the request - simple get request, ID of is sent in URL.
		Request request = new Request.Builder().url(postURL).get().header("Authorization", "Bearer " + accessToken)
				.build();
		
		// Get the system temp directory
	    String tempDirectoryPath = System.getProperty("java.io.tmpdir") + "/WhatsOn/posts/";

	    // Ensure the directory exists
	    File tempDirectory = new File(tempDirectoryPath);
	    if (!tempDirectory.exists()) {
	        tempDirectory.mkdirs();
	    }

		// Sends the request.
		Response response = client.newCall(request).execute();
		// Handling the response and generating the presentations (posts).
		statusCode = response.code();

		if (statusCode == 200) {
			// Success - now parses the response.
			JSONArray jsonArray = new JSONArray(response.body().string());
			ArrayList<Presentation> posts = new ArrayList<Presentation>();

			// Run through each post, add it to the post (presentation) list.
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject postJSON = jsonArray.getJSONObject(i);
				String xmlString = postJSON.getString("xmlContent");

				if (xmlString.contains(hashtag)) {
					byte[] postXML = xmlString.getBytes();
					Path xmlPath = Files.createTempFile(tempDirectory.toPath(), "post", null);
					Files.write(xmlPath, postXML);

					Presentation postPres = new Presentation(xmlPath.toFile());
					posts.add(postPres);
				}
			}
			return posts;

		} else if (statusCode == 403) {
			throw new AuthenticationException("Server returned 403 code - auth token not valid.");
		} else if (statusCode == 500) {
			throw new RuntimeException("500 server response - server error. Check the server code / constraints. ");
		} else if (statusCode == 400) {
			throw new RuntimeException("400 server response, bad request - check the request is valid");
		} else {
			throw new RuntimeException(statusCode + "server response, unknown error - check code and debug.");
		}
	}
	
	/**
	 * Retrieves posts from the server and returns them as an array of presentations. 
	 * Only includes posts from the hashtag specified, from verified or admin users. 
	 * @param hashtag The hashtag attached to the desired posts.  
	 * @return ArrayList<Presentation> consisting of the posts (presentations).
	 * @throws IOException File system error. Error saving XMLs to disk.  
	 * @throws SAXEException issue loading the XMLs into presentations. Check XML content. 
	 * @throws ParserConfigurationException issue loading the XMLs into presentations. Check XML content. 
	 */
	public static ArrayList<Presentation> retrievePostsByHashtagAsPresentations(String hashtag)
			throws SAXException, ParserConfigurationException, IOException, AuthenticationException {
		
		int statusCode = 0;
		OkHttpClient client = new OkHttpClient();

		// Builds the request - simple get request, ID of is sent in URL.
		Request request = new Request.Builder().url(postURL).get()
				.build();
		
		// Get the system temp directory
	    String tempDirectoryPath = System.getProperty("java.io.tmpdir") + "/WhatsOn/posts/";

	    // Ensure the directory exists
	    File tempDirectory = new File(tempDirectoryPath);
	    if (!tempDirectory.exists()) {
	        tempDirectory.mkdirs();
	    }

		// Sends the request.
		Response response = client.newCall(request).execute();
		// Handling the response and generating the presentations (posts).
		statusCode = response.code();

		if (statusCode == 200) {
			// Success - now parses the response.
			JSONArray jsonArray = new JSONArray(response.body().string());
			ArrayList<Presentation> posts = new ArrayList<Presentation>();

			// Run through each post, add it to the post (presentation) list.
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject postJSON = jsonArray.getJSONObject(i);
				String xmlString = postJSON.getString("xmlContent");

				if (xmlString.contains(hashtag)) {
					byte[] postXML = xmlString.getBytes();
					Path xmlPath = Files.createTempFile(tempDirectory.toPath(), "post", null);
					Files.write(xmlPath, postXML);

					Presentation postPres = new Presentation(xmlPath.toFile());
					posts.add(postPres);
				}
			}
			return posts;

		} else if (statusCode == 403) {
			throw new AuthenticationException("Server returned 403 code - auth token not valid.");
		} else if (statusCode == 500) {
			throw new RuntimeException("500 server response - server error. Check the server code / constraints. ");
		} else if (statusCode == 400) {
			throw new RuntimeException("400 server response, bad request - check the request is valid");
		} else {
			throw new RuntimeException(statusCode + "server response, unknown error - check code and debug.");
		}
	}
	
	/**
	 * Retrieves all admin or verified posts from the server and returns them as an array of paths to XMLs. 
	 * @param hashtag The hashtag attached to the desired posts.  
	 * @return ArrayList<Path> consisting of the posts as XMLs. 
	 * @throws AuthenticationException Invalid authorisation. Try refreshing access token or method for logged out users. 
	 * @throws IOException File system error. Error saving XMLs to disk.  
	 * @throws SAXEException issue loading the XMLs into presentations. Check XML content. 
	 * @throws ParserConfigurationException issue loading the XMLs into presentations. Check XML content. 
	 */
	public static ArrayList<Path> retrievePostsXMLs(String accessToken)
	        throws SAXException, ParserConfigurationException, AuthenticationException, IOException {

	    int statusCode = 0;
	    OkHttpClient client = new OkHttpClient();

	    // Get the system temp directory
	    String tempDirectoryPath = System.getProperty("java.io.tmpdir") + "/WhatsOn/posts/";

	    // Ensure the directory exists
	    File tempDirectory = new File(tempDirectoryPath);
	    if (!tempDirectory.exists()) {
	        tempDirectory.mkdirs();
	    }

	    // Builds the request - simple get request.
	    Request request = new Request.Builder().url(postURL).get().header("Authorization", "Bearer " + accessToken)
	            .build();

	    // Sends the request.
	    Response response = client.newCall(request).execute();
	    // Handling the response and generating the presentations (posts).
	    statusCode = response.code();

	    if (statusCode == 200) {
	        JSONArray jsonArray = new JSONArray(response.body().string());
	        ArrayList<Path> posts = new ArrayList<Path>();

	        // Run through each post, add it to the post (presentation) list.
	        for (int i = 0; i < jsonArray.length(); i++) {
	            JSONObject postJSON = jsonArray.getJSONObject(i);
	            byte[] postXML = postJSON.toString().getBytes();
	            Path xmlPath = Files.createTempFile(tempDirectory.toPath(), "post", null);
	            Files.write(xmlPath, postXML);
	            posts.add(xmlPath);
	        }
	        return posts;

	    } else if (statusCode == 403) {
	        throw new AuthenticationException("Server returned 403 code - auth token not valid.");
	    } else if (statusCode == 500) {
	        throw new RuntimeException("500 server response - server error. Check the server code / constraints. ");
	    } else if (statusCode == 400) {
	        throw new RuntimeException("400 server response, bad request - check the request is valid");
	    } else {
	        throw new RuntimeException(statusCode + "server response, unknown error - check code and debug.");
	    }
	}

	
	
	/**
	 * Retrieves all verified or admin posts from the server and returns them as an array of paths to XMLs. 
	 * @return ArrayList<Path> consisting of the posts as XMLs. 
	 * @throws AuthenticationException Invalid authorisation. Try refreshing access token or method for logged out users. 
	 * @throws IOException File system error. Error saving XMLs to disk.  
	 * @throws SAXEException issue loading the XMLs into presentations. Check XML content. 
	 * @throws ParserConfigurationException issue loading the XMLs into presentations. Check XML content. 
	 */
	public static ArrayList<Path> retrievePostsXMLs()
	        throws SAXException, ParserConfigurationException, AuthenticationException, IOException {

	    int statusCode = 0;
	    OkHttpClient client = new OkHttpClient();

	    // Get the system temp directory
	    String tempDirectoryPath = System.getProperty("java.io.tmpdir") + "/WhatsOn/posts/";

	    // Ensure the directory exists
	    File tempDirectory = new File(tempDirectoryPath);
	    if (!tempDirectory.exists()) {
	        tempDirectory.mkdirs();
	    }

	    // Builds the request - simple get request.
	    Request request = new Request.Builder().url(postURL).get().build();

	    // Sends the request.
	    Response response = client.newCall(request).execute();
	    // Handling the response and generating the presentations (posts).
	    statusCode = response.code();

	    if (statusCode == 200) {
	        JSONArray jsonArray = new JSONArray(response.body().string());
	        ArrayList<Path> posts = new ArrayList<Path>();

	        // Run through each post, add it to the post (presentation) list.
	        for (int i = 0; i < jsonArray.length(); i++) {
	            JSONObject postJSON = jsonArray.getJSONObject(i);
	            byte[] postXML = postJSON.toString().getBytes();
	            Path xmlPath = Files.createTempFile(tempDirectory.toPath(), "post", null);
	            Files.write(xmlPath, postXML);
	            posts.add(xmlPath);
	        }
	        return posts;

	    } else if (statusCode == 403) {
	        throw new AuthenticationException("Server returned 403 code - auth token not valid.");
	    } else if (statusCode == 500) {
	        throw new RuntimeException("500 server response - server error. Check the server code / constraints. ");
	    } else if (statusCode == 400) {
	        throw new RuntimeException("400 server response, bad request - check the request is valid");
	    } else {
	        throw new RuntimeException(statusCode + "server response, unknown error - check code and debug.");
	    }
	}


	/**
	 * Retrieves all posts from the server matching the hashtag, and returns them as an array of paths to XMLs. 
	 * @return ArrayList<Path> consisting of the posts as XMLs. 
	 * @throws IOException File system error. Error saving XMLs to disk.  
	 * @throws SAXEException issue loading the XMLs into presentations. Check XML content. 
	 * @throws ParserConfigurationException issue loading the XMLs into presentations. Check XML content. 
	 */
	public static ArrayList<Path> retrievePostsWithHastagXMLs(String hashtag, String accessToken)
	        throws SAXException, ParserConfigurationException, AuthenticationException, IOException {

	    int statusCode = 0;
	    OkHttpClient client = new OkHttpClient();

	    // Get the system temp directory
	    String tempDirectoryPath = System.getProperty("java.io.tmpdir") + "/WhatsOn/posts/";

	    // Ensure the directory exists
	    File tempDirectory = new File(tempDirectoryPath);
	    if (!tempDirectory.exists()) {
	        tempDirectory.mkdirs();
	    }

	    // Builds the request - simple get request.
	    Request request = new Request.Builder().url(postURL).get().header("Authorization", "Bearer " + accessToken)
	            .build();

	    // Sends the request.
	    Response response = client.newCall(request).execute();
	    // Handling the response and generating the presentations (posts).
	    statusCode = response.code();

	    if (statusCode == 200) {
	        // Success! Now handles the response.
	        JSONArray jsonArray = new JSONArray(response.body().string());
	        ArrayList<Path> posts = new ArrayList<Path>();

	        // Run through each post, add it to the post (presentation) list.
	        for (int i = 0; i < jsonArray.length(); i++) {
	            JSONObject postJSON = jsonArray.getJSONObject(i);
	            System.out.println(postJSON.toString());
	            String postHashtag = postJSON.getString("xmlContent");
	            if (postHashtag.contains(hashtag)) {
	                byte[] postXML = postJSON.toString().getBytes();
	                Path xmlPath = Files.createTempFile(tempDirectory.toPath(), "post", null);
	                Files.write(xmlPath, postXML);
	                posts.add(xmlPath);
	            }
	        }
	        return posts;
	    } else if (statusCode == 403) {
	        throw new AuthenticationException("Server returned 403 code - auth token not valid.");
	    } else if (statusCode == 500) {
	        throw new RuntimeException("500 server response - server error. Check the server code / constraints. ");
	    } else if (statusCode == 400) {
	        throw new RuntimeException("400 server response, bad request - check the request is valid");
	    } else {
	        throw new RuntimeException(statusCode + "server response, unknown error - check code and debug.");
	    }
	}

	
	/**
	 * Retrieves all verified or admin posts from the server and returns them as an array of paths to XMLs. 
	 * @return ArrayList<Path> consisting of the posts as XMLs. 
	 * @throws IOException File system error. Error saving XMLs to disk.  
	 * @throws SAXEException issue loading the XMLs into presentations. Check XML content. 
	 * @throws ParserConfigurationException issue loading the XMLs into presentations. Check XML content. 
	 */
	public static ArrayList<Path> retrievePostsWithHastagXMLs(String hashtag)
	        throws SAXException, ParserConfigurationException, AuthenticationException, IOException {

	    int statusCode = 0;
	    OkHttpClient client = new OkHttpClient();

	    // Get the system temp directory
	    String tempDirectoryPath = System.getProperty("java.io.tmpdir") + "/WhatsOn/posts/";

	    // Ensure the directory exists
	    File tempDirectory = new File(tempDirectoryPath);
	    if (!tempDirectory.exists()) {
	        tempDirectory.mkdirs();
	    }

	    // Builds the request - simple get request.
	    Request request = new Request.Builder().url(postURL).get()
	            .build();

	    // Sends the request.
	    Response response = client.newCall(request).execute();
	    // Handling the response and generating the presentations (posts).
	    statusCode = response.code();

	    if (statusCode == 200) {
	        JSONArray jsonArray = new JSONArray(response.body().string());
	        ArrayList<Path> posts = new ArrayList<Path>();

	        // Run through each post, add it to the post (presentation) list.
	        for (int i = 0; i < jsonArray.length(); i++) {
	            JSONObject postJSON = jsonArray.getJSONObject(i);
	            String postHashtag = postJSON.getString("xmlContent");
	            if (postHashtag.contains("<title>" + hashtag)) {
	                byte[] postXML = postJSON.toString().getBytes();
	                Path xmlPath = Files.createTempFile(tempDirectory.toPath(), "post", null);
	                Files.write(xmlPath, postXML);
	                posts.add(xmlPath);
	            }
	        }
	        return posts;
	    } else if (statusCode == 403) {
	        throw new AuthenticationException("Server returned 403 code - auth token not valid.");
	    } else if (statusCode == 500) {
	        throw new RuntimeException("500 server response - server error. Check the server code / constraints. ");
	    } else if (statusCode == 400) {
	        throw new RuntimeException("400 server response, bad request - check the request is valid");
	    } else {
	        throw new RuntimeException(statusCode + "server response, unknown error - check code and debug.");
	    }
	}


	/**
	 * Deletes a specific post.
	 * 
	 * @param id          the ID of the post to be deleted.
	 * @param accessToken the authorisation token of the user making the request.
	 * @return 200 the status code if successful.
	 * @throws AuthenticationException - Try refreshing access token.
	 * @throws IOException
	 */
	// TODO: TEST
	public static int deletePost(int id, String accessToken) throws AuthenticationException, IOException {

		int statusCode = 0;
		OkHttpClient client = new OkHttpClient();

		// Builds the request - simple get request, ID of is sent in URL.
		Request request = new Request.Builder().url(postURL + "/" + id).get()
				.header("Authorization", "Bearer " + accessToken).build();

		// Sends the request.
		Response response;
		response = client.newCall(request).execute();
		statusCode = response.code();

		if (statusCode == 200) {
			return statusCode;
		} else if (statusCode == 403) {
			throw new AuthenticationException("Server returned 403 code - auth token not expired.");
		} else if (statusCode == 500) {
			throw new RuntimeException("500 server response - server error. Check the server code / constraints. ");
		} else if (statusCode == 400) {
			throw new RuntimeException("400 server response, bad request - check the request is valid");
		} else {
			throw new RuntimeException(statusCode + "server response, unknown error - check code and debug.");
		}
	}

	/**
	 * Uploads a post.
	 * 
	 * @param xml         Path to the xml of the post.
	 * @param accessToken the user making the request.
	 * @return The status code of the request (200 success, 403 forbidden, etc).
	 *         Returns 0 if an exception occurs.
	 * @throws IOException             This means the XML cannot be read.
	 * @throws AuthenticationException Invalid accessToken - try refreshing.
	 */
	public static int uploadPost(Path xml, int validityHours, EventMarker hashtag, String accessToken)
			throws IOException, AuthenticationException {
		int statusCode = 0;

		OkHttpClient client = new OkHttpClient();

		String latitude = Double.toString(hashtag.getLatLong().getLatitude());
		String longitude = Double.toString(hashtag.getLatLong().getLongitude());

		RequestBody body = null;
		body = new MultipartBody.Builder().addFormDataPart("xmlContent", Files.readString(xml))
				.addFormDataPart("validityHours", Integer.toString(validityHours)).addFormDataPart("latitude", latitude)
				.addFormDataPart("longitude", longitude).addFormDataPart("hashtagName", hashtag.getName()).build();

		Request request = new Request.Builder().url(postURL).post(body).header("Authorization", "Bearer " + accessToken)
				.build();

		// Sends the request.
		Response response;

		response = client.newCall(request).execute();

		statusCode = response.code();
		if (statusCode == 200) {
			return statusCode;
		} else if (statusCode == 403) {
			throw new AuthenticationException("Server returned 403 code - auth token not expired.");
		} else if (statusCode == 500) {
			throw new RuntimeException("500 server response - server error. Check the server code / constraints. ");
		} else if (statusCode == 400) {
			throw new RuntimeException("400 server response, bad request - check the request is valid");
		} else {
			throw new RuntimeException(statusCode + "server response, unknown error - check code and debug.");
		}
	}
}
