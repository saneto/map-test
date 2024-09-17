import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

public class DisableSSLValidationExample {
    public static void main(String[] args) throws Exception {
        // Create a custom TrustManager that trusts all certificates
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

        // Initialize an SSLContext with the trust manager
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

        // Create an HttpClient with the custom SSLContext
        HttpClient client = HttpClient.newBuilder()
                .sslContext(sslContext)  // Use the insecure SSLContext
                .build();

        // Prepare a GET request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://example.com"))  // Example URL (replace with your own)
                .GET()
                .build();

        // Send the request and print the response
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
    }
}