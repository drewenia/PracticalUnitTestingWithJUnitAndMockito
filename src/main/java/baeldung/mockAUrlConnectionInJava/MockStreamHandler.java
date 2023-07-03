package baeldung.mockAUrlConnectionInJava;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

public class MockStreamHandler extends URLStreamHandler {

    private final MockHttpURLConnection mockHttpURLConnection;

    public MockStreamHandler(MockHttpURLConnection mockHttpURLConnection) {
        this.mockHttpURLConnection = mockHttpURLConnection;
    }

    @Override
    protected URLConnection openConnection(URL url) throws IOException {
        return this.mockHttpURLConnection;
    }
}
