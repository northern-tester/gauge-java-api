import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.datastore.DataStore;
import com.thoughtworks.gauge.datastore.DataStoreFactory;
import org.junit.Assert;

public class CommonSteps {
    @Step("Then the response will be <expectedResponse>")
    public void ApiResponse(String expectedResponse) {
        DataStore dataStore = DataStoreFactory.getScenarioDataStore();
        String httpResponseStatusText = (String) dataStore.get("httpResponseStatusText");
        Assert.assertEquals(expectedResponse, httpResponseStatusText);
    }

    @Step("Then the response content-type will be <expectedResponse>")
    public void ContentType(String expectedResponse) {
        DataStore dataStore = DataStoreFactory.getScenarioDataStore();
        String httpResponseContentType = (String) dataStore.get("httpResponseContentType");
        Assert.assertEquals(expectedResponse, httpResponseContentType);
    }
}
