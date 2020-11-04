package OAuthServices;

import com.github.scribejava.apis.VkontakteApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;

public class VK {

    private static final String NETWORK_NAME = "Vkontakte.ru";
    private static final String PROTECTED_RESOURCE_URL = "https://api.vk.com/method/users.get?&fields=photo_200&v=" + VkontakteApi.VERSION;
    public static VK here;

    static {
        here = new VK();
    }

    final String clientId = "7651029";
    final String clientSecret = "LYFr6OMdVY6M5s7pIzU7";
    final String scope = "email";
    final OAuth20Service service;

    private VK() {
        service = new ServiceBuilder(clientId)
                .apiSecret(clientSecret)
                .defaultScope(scope)
                .callback("http://localhost:8080/oauth")
                .build(VkontakteApi.instance());
    }

    public String getURL() {
        final String authorizationUrl = service.createAuthorizationUrlBuilder()
                .scope(scope)
                .build();
        return authorizationUrl;
    }

    public OAuthUser getUser(String code) throws InterruptedException, ExecutionException, IOException {
        Gson g = new Gson();

        String url = "https://oauth.vk.com/access_token?client_id=" + clientId + "&client_secret=" + clientSecret + "&redirect_uri=http://localhost:8080/oauth&code=" + code;
        String AcTokJSON = getData(url);
        AccessToken accessToken = g.fromJson(AcTokJSON, AccessToken.class);

        final OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
        service.signRequest(accessToken.access_token, request);

        String JSON = service.execute(request).getBody();
        System.out.println(JSON);

        JSON = JSON.substring(13, JSON.length() - 2);
        OAuthUser oAuthUser = g.fromJson(JSON, OAuthUser.class);
        System.out.println(accessToken.email);
        oAuthUser.email = accessToken.email;
        return oAuthUser;
    }

    private String getData(String url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");
        InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        StringBuilder sb = new StringBuilder();
        int charByte;
        while ((charByte = bufferedReader.read()) != -1) {
            sb.append((char) charByte);
        }
        return sb.toString();
    }
}