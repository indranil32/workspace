import groovyx.net.http.HTTPBuilder
import groovyx.net.http.ContentType
import groovyx.net.http.Method
import groovyx.net.http.RESTClient
import groovy.json.JsonOutput

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.nio.charset.StandardCharsets
import java.security.KeyFactory
import java.security.NoSuchAlgorithmException
import java.security.NoSuchProviderException
import java.security.PrivateKey
import java.security.PublicKey
import java.security.Security
import java.security.interfaces.RSAPrivateCrtKey
import java.security.spec.InvalidKeySpecException
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.RSAPublicKeySpec

import javax.crypto.Cipher

import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.apache.commons.lang.RandomStringUtils;
import java.text.SimpleDateFormat


@Grapes([
        @Grab(group='org.codehaus.gpars', module='gpars', version='1.2.1'),
        @Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.7.1'),
        @Grab(group='bouncycastle', module='bcprov-jdk16', version='140'),
        @Grab(group='com.google.apis', module='google-api-services-sheets', version='v4-rev108-1.22.0'),
        @Grab(group='com.google.oauth-client', module='google-oauth-client-jetty', version='1.22.0'),
        @Grab(group='com.google.api-client', module='google-api-client', version='1.22.0')
])

def ToExec = 'ALL'
if (args != null || args.length > 0 ) {
	ToExec = args[0]
}

// local
def url = "http://localhost:8080/Service/"
def ping = 'ping'
def submit = 'request'

Security.addProvider(new BouncyCastleProvider());

def privateKey=''

def DATA_STORE_DIR = new java.io.File(System.getProperty("user.home"), ".credentials/sheets.googleapis.com-java-quickstart");
def JSON_FACTORY = JacksonFactory.getDefaultInstance();   
def SCOPES = Arrays.asList(SheetsScopes.SPREADSHEETS_READONLY);
def HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
def DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);

def requestId = Long.parseLong(RandomStringUtils.randomNumeric(3));
println requestId
def applicationId = Long.parseLong(RandomStringUtils.randomNumeric(10));
println applicationId

def setRequest = {
    def request = [:];
    request.requestId = requestId
    request.applicationId = applicationId
    
    request.Type = 'SAMPLE_TYPE'
    
    return request
 }

def setTestParams = {
    request ->
        request.configMap=[:]
       
    return request
}

def generatePrivateKey = { //=  throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException
        privateKeyParam ->
        byte[] decodedData = Base64.getDecoder().decode(privateKeyParam);
        PKCS8EncodedKeySpec privspec = new PKCS8EncodedKeySpec(decodedData);
        KeyFactory factory = KeyFactory.getInstance("RSA", "BC");
        return factory.generatePrivate(privspec);
    }

def PublicKey generatePublicKey(RSAPrivateCrtKey pvtkey) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
    RSAPublicKeySpec publicKeySpec =  new  RSAPublicKeySpec(pvtkey.getModulus(), pvtkey.getPublicExponent());
    KeyFactory factory = KeyFactory.getInstance("RSA", "BC");
    return factory.generatePublic(publicKeySpec);
}

def encrypt =  {
        password ->
        def cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
        RSAPrivateCrtKey tmp = (RSAPrivateCrtKey)generatePrivateKey(privateKey);
        PublicKey pk = generatePublicKey(tmp);
        cipher.init(Cipher.ENCRYPT_MODE, pk);
        //String enc = new String(cipher.doFinal(password.getBytes()), StandardCharsets.UTF_8)
        return Base64.getEncoder().encodeToString(cipher.doFinal(password.getBytes()));
        //println enc;
        //return enc
}

def decrypt =  {
    base64EncryptedData ->
        def cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
        cipher.init(Cipher.DECRYPT_MODE, generatePrivateKey(privateKey));
        return new String(cipher.doFinal(base64EncryptedData.decodeBase64()), StandardCharsets.UTF_8)
}

def postText = {
    baseUrl, path , json -> 
        try {
            def ret = null
            def http = new HTTPBuilder(baseUrl)

            // perform a POST request, expecting TEXT response
            http.request(Method.POST, ContentType.JSON) {
                uri.path = path
                body= json                
                requestContentType = ContentType.JSON
                headers.'User-Agent' = 'Mozilla/5.0 Ubuntu/8.10 Firefox/3.0.4'

                // response handler for a success response code
                response.success = { resp, reader ->
                    
                    println "response status: ${resp.statusLine}"
                    println 'Headers: -----------'
                    resp.headers.each { h ->
                        println " ${h.name} : ${h.value}"
                    }
                
                    if (reader instanceof Reader)
                        ret = reader.getText()
                    else ret = reader    
                    println 'Response data: -----'
                    println ret
                    println '--------------------'
                }
            }
            return ret

        } catch (groovyx.net.http.HttpResponseException ex) {
            println ex.statusCode
            ex.printStackTrace()
            return null
        } catch (java.net.ConnectException ex) {
            println ex.message()
            ex.printStackTrace()
            return null
        }
}
def getText = {
    baseUrl, path -> 
        try {
            def ret = null
            def http = new HTTPBuilder(baseUrl)

            http.request(Method.GET, ContentType.JSON) {
                uri.path = path                
                headers.'User-Agent' = 'Mozilla/5.0 Ubuntu/8.10 Firefox/3.0.4'

                // response handler for a success response code
                response.success = { resp, reader ->
                    
                    println "response status: ${resp.statusLine}"
                    println 'Headers: -----------'
                    resp.headers.each { h ->
                        println " ${h.name} : ${h.value}"
                    }
                
                    if (reader instanceof Reader)
                        ret = reader.getText()
                    else ret = reader    
                    println 'Response data: -----'
                    println ret
                    println '--------------------'
                }
            }
            return ret

        } catch (groovyx.net.http.HttpResponseException ex) {
            println ex.statusCode
            ex.printStackTrace()
            return null
        } catch (java.net.ConnectException ex) {
            println ex.message()
            ex.printStackTrace()
            return null
        }   
}

def authorize = {
        // Load client secrets.
        InputStream instream = new FileInputStream("client_secret.json");
        GoogleClientSecrets clientSecrets =
            GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(instream));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(DATA_STORE_FACTORY)
                .setAccessType("offline")
                .build();
        Credential credential = new AuthorizationCodeInstalledApp(
            flow, new LocalServerReceiver()).authorize("user");
        System.out.println(
                "Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
        return credential;
    }
def getSheetsService = {
        Credential credential = authorize();
        return new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName("Google Sheets API Java Quickstart")
                .build();
    }

   
def read = {
    spreadsheetId , range , sendMap -> 
    Map<String,List<Object>> map = new HashMap<String,List<Object>>();
    // Build a new authorized API client service.
    Sheets service = getSheetsService();

    // Prints the names and majors of students in a sample spreadsheet:
    // https://docs.google.com/spreadsheets/d/1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms/edit
    //String spreadsheetId = "1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms";
    //String range = "Sheet1!A2:L39";
    ValueRange response = service.spreadsheets().values()
        .get(spreadsheetId, range)
        .execute();
    List<List<Object>> values = response.getValues();
    if (values == null || values.size() == 0) {
        System.out.println("No data found.");
    } else if (sendMap){
       for(List<Object> row : values) {
          /*for (Object obj : row) {
              print obj; print ' '
          }
          println ""
          */
          if (row.size() > 3) {
            //System.out.printf("%s, %s, %s\n", row.get(0), row.get(2), row.get(3));
            map.put(String.valueOf(row.get(0)).toUpperCase(), row);
        }
      }
    }
    return sendMap ? map : values
}

def liveMap = read('','Sheet2!A2:E60', false)

def credsMap = read('','Sheet1!B2:L46', true)

// ping
getText(url,ping)

def request

for(List<Object> row : liveMap) {  
  def isLive = row.get(3) == "1. Live"
  def Name = row.get(0)
  if (isLive && ( ToExec.equals("ALL") ? true : Name.toUpperCase().contains(ToExec))) {      
      println isLive
      println Name
      def Id = row.get(1)
      println Id
      request = setRequest()
      request.Id = 998
      Calendar cal = Calendar.getInstance();
      SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy", Locale.ENGLISH);
      def dob = "22/07/1980"
      try {
          //dob = credsMap.get(tmpbn.toUpperCase()).get(4).trim()
      } catch (Exception e) {
          println e.getMessage()
      }
      cal.setTime(sdf.parse(dob));
      println cal.getTime()
      def username = credsMap.get(tmpbn.toUpperCase()).get(2).trim()
      println username;
      request.username = username;
      def password = credsMap.get(tmpbn.toUpperCase()).get(3).trim()
      println password;
      request.password = encrypt(password)
      request = setTestParams(request)
      def json = new JsonOutput().toJson(request)
      println json      
      // submitrequest
      postText(url,submit, json)     
  }
}


//def responseURL = 'response/'+request.requestId+'/'+request.Type
//println responseURL
//Thread.sleep(1000*180)
//getText(url,responseURL)

