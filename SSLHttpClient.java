import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;

public class SSLHttpClient {  
    
    public static String SSLHttpClientDoget(String url) {  
    	  
        try {  
  
            HttpClient httpclient = new DefaultHttpClient();  
                        //Secure Protocol implementation.  
            SSLContext ctx = SSLContext.getInstance("SSL");  
                        //Implementation of a trust manager for X509 certificates  
            X509TrustManager tm = new X509TrustManager() {  
  
                public void checkClientTrusted(X509Certificate[] xcs,  
                        String string) throws CertificateException {  
  
                }  
  
                public void checkServerTrusted(X509Certificate[] xcs,  
                        String string) throws CertificateException {  
                }  
  
                public X509Certificate[] getAcceptedIssuers() {  
                    return null;  
                }  
            };  
            ctx.init(null, new TrustManager[] { tm }, null);  
            SSLSocketFactory ssf = new SSLSocketFactory(ctx);  
  
            ClientConnectionManager ccm = httpclient.getConnectionManager();  
                        //register https protocol in httpclient's scheme registry  
            SchemeRegistry sr = ccm.getSchemeRegistry();  
            sr.register(new Scheme("https", 443, ssf));  
  
            HttpGet httpget = new HttpGet(url);  
            HttpParams params = httpclient.getParams();  
  
            params.setParameter("param1", "paramValue1");  
  
            httpget.setParams(params);  
            System.out.println("REQUEST:" + httpget.getURI());  
            ResponseHandler<String> responseHandler = new BasicResponseHandler();  
            String responseBody;  
  
            responseBody = httpclient.execute(httpget, responseHandler);  
  
            System.out.println(responseBody);  
  
            // Create a response handler  
            return responseBody;  
        } catch (NoSuchAlgorithmException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (ClientProtocolException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (Exception ex) {  
            ex.printStackTrace();  
  
        }
        return null;  
    }  
}  