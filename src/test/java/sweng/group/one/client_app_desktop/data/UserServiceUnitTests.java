package sweng.group.one.client_app_desktop.data;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

/* TEST STRATEGY
Unit tests for the service class. 
Uses MockWebServer (another library by OkHttp, the library used for the requests)
to mock the server interactions. Mockito is used for mocking User. 
*/
 
@RunWith(MockitoJUnitRunner.class)
public class UserServiceUnitTests {
    private MockWebServer server;
    private UserService serviceTest;
    
    @Mock
    private User mockedUser;
    
    @Before
    public void setup() throws IOException {
        server = new MockWebServer();
        server.start(9000);
        serviceTest = new UserService("server-urls-test.properties");
    }

    @Test
    public void SuccessfulLoginSavesAccessAndRefreshTokens() throws IOException {
        String defaultAdminUsername = "sid";
        String defaultAdminPass = "password123";
        String testAccessToken = "TestAccessToken";
        String testRefreshToken = "TestRefreshToken";

        server.enqueue(new MockResponse()
            .setResponseCode(200)
            .setBody("{\"access_token\":\"" + testAccessToken + "\",\"refresh_token\":\"" + testRefreshToken + "\"}"));

        when(mockedUser.getUsername()).thenReturn(defaultAdminUsername);
        int statusCode = serviceTest.login(mockedUser, defaultAdminPass);

        verify(mockedUser).saveAccessToken(testAccessToken);
        verify(mockedUser).saveRefreshToken(testRefreshToken);
    }

    @Test
    public void successfulRefreshSavesNewAccessToken() throws IOException {
        String defaultAdminUsername = "sid";
        String testAccessToken = "TestAccessToken";

        server.enqueue(new MockResponse()
            .setResponseCode(200)
            .setBody("{\"access_token\":\"" + testAccessToken + "\"}"));
        
        int statusCode = serviceTest.refreshAccessToken(mockedUser);
        verify(mockedUser).saveAccessToken(testAccessToken);
    }
    
    @After
    public void tearDown() throws IOException {
        server.shutdown();
    }
}
