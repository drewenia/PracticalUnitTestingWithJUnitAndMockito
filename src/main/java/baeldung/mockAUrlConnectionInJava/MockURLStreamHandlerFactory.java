package baeldung.mockAUrlConnectionInJava;

import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;

public class MockURLStreamHandlerFactory implements URLStreamHandlerFactory {
    private MockHttpURLConnection mockHttpURLConnection;

    public MockURLStreamHandlerFactory(MockHttpURLConnection mockHttpURLConnection) {
        this.mockHttpURLConnection = mockHttpURLConnection;
    }

    @Override
    public URLStreamHandler createURLStreamHandler(String protocol) {
        return new MockStreamHandler(this.mockHttpURLConnection);
    }
}
