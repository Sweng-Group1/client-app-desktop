package sweng.group.one.client_app_desktop.data;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 *  Service class for handling all map related server
 *  tasks, i.e. uploading, retrieving, deleting, etc.
 *  @author Paul Pickering
 */
public class MapService {
	// TODO: Do we want to change these URLs to constant specified elsewhere
	// perhaps?
	private final static String MAP_URL = "http://localhost:8080/api/v1/map/";
	private static final String MAP_FOLDER = "./assets/map/";

	/**
	 * Gets a map file from the server. No authorisation required. 
	 * 
	 * @param name The name of the map file, i.e. "York".
	 * @return Returns the path to the map file.
	 * @throws AuthenticationException
	 * @throws IOException
	 */
	public static Path retrieveMap(String name) throws IOException {
		int statusCode = 0;

		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder().url(MAP_URL + name).get().build();

		try {
			Response response = client.newCall(request).execute();

			// Handling the response.
			statusCode = response.code();

			if (statusCode == 200) {
				// Success! Save the map locally.
				Path mapPath = Paths.get(MAP_FOLDER + name + ".map");
				Files.createFile(mapPath);
				ResponseBody body = response.body();
				Files.write(mapPath, body.bytes());
				return mapPath;
			}  else if (statusCode == 500) {
				throw new RuntimeException("500 server response - server error. Check the server code / constraints.");
			} else if (statusCode == 400) {
				throw new RuntimeException("400 server response, bad request - check the request is valid");
			} else {
				throw new RuntimeException(statusCode + "server response, unknown error - check code and debug.");
			}
		} catch (FileAlreadyExistsException e) {
			Path mapPath = Paths.get(MAP_FOLDER + name + ".map");
			return mapPath;
		}
	}

	/**
	 * Uploads a map file to the server. Admin or Verified authorisation required.
	 * 
	 * @param map         The map file.
	 * @param accessToken the authorisation token.
	 * @return Returns the status code (e.g. 200 success, 403 forbidden).
	 * @throws IOException             - can fail reading the map file.
	 * @throws AuthenticationException
	 */
	public static int uploadMap(Map map, String accessToken) throws IOException, AuthenticationException {
		String filepath = map.getFile().getPath();
		byte[] fileAsBytes;
		try {
			fileAsBytes = Files.readAllBytes(map.getFile().toPath());
		} catch (IOException e) {
			throw new IOException("Failed to read file " + filepath, e);
		}

		OkHttpClient client = new OkHttpClient();
		String nameKey = "name";
		String nameValue = map.getName();

		// Ensures non-alphanumeric characters are handled properly.
		String encodedNameValue = URLEncoder.encode(nameValue, StandardCharsets.UTF_8);

		// Here we build the body of the request, specifying the MultiPartFile "file"
		// and String "name" parameters.
		RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
				.addFormDataPart("file", filepath,
						RequestBody.create(fileAsBytes, MediaType.parse("application/octet-stream")))
				.addFormDataPart(nameKey, encodedNameValue).build();

		Request request = new Request.Builder().url(MAP_URL).header("Content-Type", "application/x-www-form-urlencoded")
				.header("Authorization", "Bearer " + accessToken).post(requestBody).build();

		Response response = client.newCall(request).execute();

		// Handling the response.
		int statusCode = response.code();

		if (statusCode == 200) {
			// Success! Save the map locally.
			return statusCode;
		} else if (statusCode == 403) {
			throw new AuthenticationException("Server returned 403 code - auth token not valid. Try refreshing first.");
		}	else if (statusCode == 404) {
			throw new FileNotFoundException("No maps on the server!");
		} else if (statusCode == 500) {
			throw new RuntimeException("500 server response - server error. Check the server code / constraints. ");
		} else if (statusCode == 400) {
			throw new RuntimeException("400 server response, bad request - check the request is valid");
		} else {
			throw new RuntimeException(statusCode + "server response, unknown error - check code and debug.");
		}
	}
}
