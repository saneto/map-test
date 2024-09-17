import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

public class DisableSSLHandshakeExample {
    public static void main(String[] args) throws Exception {
        // Create a TrustManager that trusts all certificates
        TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }
        };

        // Initialize the SSLContext with the TrustManager that disables certificate validation
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

        // Create an HttpClient that uses the insecure SSLContext
        HttpClient client = HttpClient.newBuilder()
                .sslContext(sslContext)
                .sslParameters(new SSLParameters() {{
                    setEndpointIdentificationAlgorithm("");  // Disable hostname verification
                }})
                .build();

        // Build the HTTP request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://example.com"))  // Replace with the target URL
                .GET()
                .build();

        // Send the HTTP request and print the response
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
    }
}