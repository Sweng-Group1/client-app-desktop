package sweng.group.one.client_app_desktop.data;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static org.assertj.core.api.Assertions.assertThat;
import java.io.IOException;

public class UserServiceUnitTests {
    private MockWebServer server;
    private UserService serviceTest;
    
    @Before
    public void setup() throws IOException {
        server = new MockWebServer();
        server.start(9000);
        serviceTest = new UserService("server-urls-test.properties");
    }

    @Test
    public void testLoginUsingDefaultAdminCredentialsReturns200OkCode() {
        server.enqueue(new MockResponse().setResponseCode(200)
        		.setBody(("{\"access_token\":\"TestAccessToken\",\"refresh_token\":\"TestRefreshToken\"}")));
        
        String defaultAdminUsername = "sid";
        String defaultAdminPass = "password123";
        User userTest = new User(defaultAdminUsername);
        
        System.out.println("Running login test for correct credentials: ");
        int statusCode = serviceTest.login(userTest, defaultAdminPass);
        System.out.println("Status Code: " + statusCode);
        assertThat(statusCode).isEqualTo(200);
    }
    
    @After
    public void tearDown() throws IOException {
        server.shutdown();
    }
}
