package online.omnia.autostop;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class HttpMethodUtils {
    private static String baseUrl;
    private static CloseableHttpClient httpClient;

    static {
        baseUrl = "https://api.ori.cmcm.com/";
        httpClient = HttpClients.createDefault();
    }
    public HttpMethodUtils() {

    }
    public static String sendJsonRequest(String token, int id, Date startDate) throws IOException {
        HttpURLConnection httpcon = (HttpURLConnection) ((new URL("https://api.ori.cmcm.com/report/advertiser").openConnection()));
        httpcon.setDoOutput(true);
        httpcon.setRequestProperty("Content-Type", "application/json");
        httpcon.setRequestProperty("Accept", "application/json,application/x.orion.v1+json");
        httpcon.setRequestProperty("Authorization", "Bearer " + token);
        httpcon.setRequestMethod("POST");
        httpcon.connect();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = new Date();
        System.out.println("Creating json request");
        String str = "{\"column\":[\"impression\",\"click\",\"conversion\",\"revenue\",\"cpc\",\"cpi\",\"cpm\",\"ctr\",\"cvr\",\"cpv\"],"
                + "\"groupby\":[\"datetime\",\"adset\"],"
                + "\"filter\":{\"adset\":{\"op\":\"eq\",\"value\":\"" + id + "\"}},"
                + "\"start\":\"" + dateFormat.format(startDate) + "\","
                + "\"end\":\"" + dateFormat.format(new Date(currentDate.getTime())) + "\"}";
        System.out.println(str);
        byte[] outputBytes = str.getBytes("UTF-8");
        OutputStream os = httpcon.getOutputStream();
        os.write(outputBytes);
        os.close();
        System.out.println(httpcon.getResponseMessage());

        BufferedReader reader = new BufferedReader(new InputStreamReader(httpcon.getInputStream()));
        String line;
        StringBuilder lineBuilder = new StringBuilder();
        System.out.println("Getting answer");
        while ((line = reader.readLine()) != null) {
            lineBuilder.append(line);
        }
        return lineBuilder.toString();
    }
    public static String getToken(String clientId, String clientCredentials){
        try {
            HttpPost httpPost = new HttpPost(baseUrl + "oauth/access_token");
            List<NameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("grant_type", "client_credentials"));
            nameValuePairs.add(new BasicNameValuePair("client_id", clientId));
            nameValuePairs.add(new BasicNameValuePair("client_secret", clientCredentials));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            return getResponse(httpPost);
        } catch (IOException e) {
            System.out.println("Input output exception during getting access token:");
            System.out.println(e.getMessage());
        }
        return "";
    }
    public static String getMethod(String urlPath, String token){
        try {
            if (urlPath == null) return null;
            HttpGet httpGet = new HttpGet(baseUrl + urlPath);
            httpGet.addHeader("Authorization", "Bearer " + token);
            return getResponse(httpGet);
        } catch (IOException e) {
            System.out.println("Input output exception during executing get request:");
            System.out.println(e.getMessage());
        }
        return "";
    }

    public static String postMethod(String urlPath, List<NameValuePair> params, String token){
        try {
            if (urlPath == null || params == null) return null;
            HttpPost httpPost = new HttpPost(baseUrl + urlPath);
            httpPost.addHeader("Authorization", "Bearer " + token);
            httpPost.setEntity(new UrlEncodedFormEntity(params));

            return getResponse(httpPost);
        } catch (IOException e) {
            System.out.println("Input output exception during executing post request:");
            System.out.println(e.getMessage());
        }
        return "";
    }

    private static String getResponse(HttpUriRequest httpRequest) throws IOException {
        CloseableHttpResponse response = httpClient.execute(httpRequest);
        StringBuilder serverAnswer = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
        String answer;
        while ((answer = reader.readLine()) != null) {
            serverAnswer.append(answer);
        }
        reader.close();
        response.close();
        return serverAnswer.toString();
    }


}
