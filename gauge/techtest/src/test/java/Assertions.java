import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.datastore.DataStore;
import com.thoughtworks.gauge.datastore.DataStoreFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;

public class Assertions {


    @Step("The response code should be <response_code>")
    public void responseCodeShouldEqual(Integer response_code) {
        DataStore dataStore = DataStoreFactory.getScenarioDataStore();
        Integer httpResponseCode = (Integer) dataStore.get("httpResponseCode");
        Assert.assertEquals(response_code, httpResponseCode);
    }

    @Step("The response body for <requestName> should contain <mediaTypeUsed> and <bodyReceived>")
    public void responseBodyShouldContain(String requestName, String expectedMediaTypeValue, Integer expectedBodyReceivedValue) {
        DataStore dataStore = DataStoreFactory.getScenarioDataStore();
        String response = (String) dataStore.get("httpResponseBody");
        Assert.assertEquals(expectedMediaTypeValue, responseMediaValueAssertParser(requestName, response));
        Assert.assertEquals(expectedBodyReceivedValue, bodyReceivedValueAssertParser(requestName, response));
    }

    private String responseMediaValueAssertParser(String requestName, String response) {
        JSONObject jsonObject = new JSONObject(response);
        JSONArray jsonArray = jsonObject.getJSONArray(requestName);
        JSONObject responseMediaBody = jsonArray.getJSONObject(1);
        String responseMediaValue = (String) responseMediaBody.get("mediaTypeUsed");
        return responseMediaValue;
    }

    private Integer bodyReceivedValueAssertParser(String requestName, String response) {
        JSONObject jsonObject = new JSONObject(response);
        JSONArray jsonArray = jsonObject.getJSONArray(requestName);
        JSONObject responseMediaBody = jsonArray.getJSONObject(1);
        JSONObject bodyReceived = responseMediaBody.getJSONObject("bodyReceived");
        Integer actualBodyReceivedValue = (Integer) bodyReceived.get("test");
        return actualBodyReceivedValue;
    }

}
