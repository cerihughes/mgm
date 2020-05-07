package uk.co.cerihughes.mgm.data.input;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class GoogleSheetsDataLoaderImpl implements GoogleSheetsDataLoader {
    private static final String url = "https://spreadsheets.google.com/feeds/list/1AeTjG2coPkJ_3XSsSUWmu1Pb5Tpm9YdHP3M3iBtgUew/od6/public/values?alt=json";

    public String loadJsonData() throws IOException {
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(url);

        ResponseHandler<String> responseHandler = response -> {
            int status = response.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
                HttpEntity entity = response.getEntity();
                return entity != null ? EntityUtils.toString(entity) : null;
            } else {
                throw new ClientProtocolException("Unexpected response status: " + status);
            }
        };
        return httpclient.execute(httpget, responseHandler);
    }
}