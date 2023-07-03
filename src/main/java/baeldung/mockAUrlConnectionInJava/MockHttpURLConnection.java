package baeldung.mockAUrlConnectionInJava;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class MockHttpURLConnection extends HttpURLConnection {
    protected MockHttpURLConnection(URL url) {
        super(url);
    }

    @Override
    public int getResponseCode() throws IOException {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    @Override
    public void disconnect() {
    }

    @Override
    public boolean usingProxy() {
        return false;
    }

    @Override
    public void connect() throws IOException {
    }
}
