package baeldung.mockAUrlConnectionInJava;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UrlFetcherTest {

    @Test
    void givenMockedUrl_whenRequestSent_thenIsUrlAvailableFalse() throws IOException {
        HttpURLConnection mockHttpUrlConnection = mock(HttpURLConnection.class);
        when(mockHttpUrlConnection.getResponseCode()).thenReturn(HttpURLConnection.HTTP_NOT_FOUND);

        URL mockUrl = mock(URL.class);
        when(mockUrl.openConnection()).thenReturn(mockHttpUrlConnection);

        UrlFetcher urlFetcher = new UrlFetcher(mockUrl);
        assertFalse(urlFetcher.isUrlAvailable());
    }
}
