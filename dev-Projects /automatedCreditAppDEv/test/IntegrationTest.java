import org.junit.*;

import play.test.*;
import play.libs.F.*;

import static play.test.Helpers.*;
import static org.junit.Assert.*;

public class IntegrationTest {

    /**
     * add your integration test here
     * in this example we just check if the welcome page is being shown
     */
    @Test
    public void test() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
            @Override
			public void invoke(TestBrowser browser) {
                browser.goTo("http://localhost:3333");
                assertTrue(browser.pageSource().contains("Your new application is ready."));
            }
        });
    }

}
