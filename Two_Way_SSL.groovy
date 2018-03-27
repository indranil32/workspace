
import javax.net.ssl.*;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URL;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;


public class Scanning {
    private static final SSLSocketFactory SOCKET_FACTORY = getSSLSocketFactory();
    public static SSLSocketFactory getSSLSocketFactory() {

        FileInputStream io = null;

        try {
            KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
            io = new FileInputStream("localhost.jks");
            keystore.load(io, "password".toCharArray());
            io.close();

            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            kmf.init(keystore, "password".toCharArray());


            // Create a trust manager that does not validate certificate chains
            TrustManager[] trustAllCerts = new TrustManager[1];
            trustAllCerts[0] = new X509TrustManager() {
                                    public X509Certificate[] getAcceptedIssuers() {
                                        return null;
                                    }
            
                                    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) {
                                        //To change body of implemented methods use File | Settings | File Templates.
                                    }
            
                                    public void checkServerTrusted(X509Certificate[] x509Certificates, String s) {
                                        //To change body of implemented methods use File | Settings | File Templates.
                                    }
                            }
                    
            SSLContext sc = SSLContext.getInstance("TLSv1.2");
            sc.init(kmf.getKeyManagers(), trustAllCerts, new SecureRandom());
            return sc.getSocketFactory();


        } catch (Throwable t) {
            //
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        HttpsURLConnection.setDefaultSSLSocketFactory(SOCKET_FACTORY);
        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier () {
                               @Override
                               public boolean verify(
                                        final String host,
                                         SSLSession session) {
                                    return true;
                               }
                               @Override
                               public String toString() {
                                    return "ALLOW_ALL";
                               }
                   });
        URL url = new URL("https://localhost:8080/request");
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        connection.setFollowRedirects(true);
        connection.setConnectTimeout(60000);
        connection.setReadTimeout(60000);
        String str =  """{
                    }"""
        byte[] outputInBytes = str.getBytes("UTF-8");
        OutputStream os = connection.getOutputStream();
        os.write( outputInBytes );
        os.flush();
        os.close();
        
        int responseCode = connection.getResponseCode();
        System.out.println("==========RESPONSE BEGIN===========");
        System.out.println("RESPONSE MESSAGE " + connection.getResponseMessage());
        System.out.println("RESPONSE CODE " + responseCode);
        // get ready to read the response from the cgi script 
        InputStream input = connection.getInputStream(); 
        
        // read in each character until end-of-stream is detected 
        try{
            for( int c = input.read(); c != -1; c = input.read() )
                System.out.print( (char)c );              
        } catch(Exception e)  { 
            System.out.println( "Something bad just happened." ); 
            System.out.println( e ); 
            e.printStackTrace(); 
        }
         
        input.close();

        
    }
}
