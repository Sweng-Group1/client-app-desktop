package sweng.group.one.client_app_desktop.data;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXException;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import sweng.group.one.client_app_desktop.presentation.Presentation;

public class PostServerUnitTests {
	private MockWebServer server;
	
	@Before
	public void Setup() throws IOException {
		server = new MockWebServer();
		server.start(8080);	
	}
	
	@After
	public void Closedown() throws IOException {
		server.close();
		
	}

	@Test
	public void RetrievePostsAsXMLsReturnsValidXML() throws SAXException, ParserConfigurationException, AuthenticationException, IOException {
        // Create the inner "hashtag" JSONObject.
        JSONObject hashtag = new JSONObject();
        hashtag.put("id", 1);
        hashtag.put("name", "#MovieSociety");
        hashtag.put("latitude", 50.0);
        hashtag.put("longitude", 50.0);

        // Create the outer JSONObject and add the inner "hashtag" JSONObject to it.
        JSONObject serverResponse = new JSONObject();
        serverResponse.put("id", 1);
        serverResponse.put("xmlContent", "This is a test XML");
        serverResponse.put("created", "2023-06-01T16:39:40.666239");
        serverResponse.put("updated", JSONObject.NULL);
        serverResponse.put("expiry", "2023-06-02T02:39:40.631999");
        serverResponse.put("hashtag", hashtag);

        // Now, if your response is a JSONArray, you can wrap it as:
        JSONArray responseArray = new JSONArray();
        responseArray.put(serverResponse);

        // Convert the object to a JSON string.
        String jsonString = responseArray.toString();

        // Set up the server to return the JSON string.
        server.enqueue(new MockResponse()
        	.setResponseCode(200)
            .setBody(jsonString)
            .addHeader("Content-Type", "application/json"));
        
        ArrayList<Path> paths = PostService.retrievePostsXMLs("OpenSesame");
        
        String xml = Files.readString(paths.get(0));
 
        assertThat(xml).contains("This is a test XML");
        assertThat(paths.get(0).toFile().exists()).isTrue();
	}
	
	@Test
	public void RetrievePostsAsPresentationsReturnsValidPresentations() throws SAXException, ParserConfigurationException, AuthenticationException, IOException {	
		File testXML = new File("testAssets/evaluation.xml");	
		Presentation expectedPresentation = new Presentation(testXML);
		
        JSONObject hashtag = new JSONObject();
        hashtag.put("id", 1);
        hashtag.put("name", "#MovieSociety");
        hashtag.put("latitude", 50.0);
        hashtag.put("longitude", 50.0);

        // Create the outer JSONObject and add the inner "hashtag" JSONObject to it.
        JSONObject serverResponse = new JSONObject();
        serverResponse.put("id", 1);
        serverResponse.put("xmlContent", Files.readString(testXML.toPath()));
        serverResponse.put("created", "2023-06-01T16:39:40.666239");
        serverResponse.put("updated", JSONObject.NULL);
        serverResponse.put("expiry", "2023-06-02T02:39:40.631999");
        serverResponse.put("hashtag", hashtag);


        JSONArray responseArray = new JSONArray();
        responseArray.put(serverResponse);

        // Convert the object to a JSON string.
        String jsonString = responseArray.toString();

        // Set up the server to return the JSON string.
        server.enqueue(new MockResponse()
        	.setResponseCode(200)
            .setBody(jsonString)
            .addHeader("Content-Type", "application/json"));
        
        ArrayList<Presentation> presentations = PostService.retrievePostsPresentations("OpenSesame");
        
        Presentation retrievedPresentation = presentations.get(0);
        // Double check this toString is valid. 
        assertThat(retrievedPresentation.toString()).isEqualTo(expectedPresentation.toString());
	}
}
