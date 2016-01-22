package crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;

public class Client {

	private String proxyIP;
	private int port;

	public Client(String proxyIP, int port) {
		this.proxyIP = proxyIP;
		this.port = port;
	}

	public String getMovie(String url) {
		try {

			// String url = "http://www.omdbapi.com/?t="
			// + URLEncoder.encode("Life of pi", "UTF-8");
			// HttpHost proxy = new HttpHost("46.101.87.21", 8080);
			HttpHost proxy = new HttpHost(this.proxyIP, this.port);
			StringBuilder sb = new StringBuilder();
			DefaultHttpClient httpClient = new DefaultHttpClient();
			httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
					proxy);
			HttpPost httpPost = new HttpPost(url);

			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			InputStream is = httpEntity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "UTF-8"), 8);

			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();

			String json = sb.toString();
			return json;

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}
}
