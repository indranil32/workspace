@Grapes([
        @Grab(group='org.codehaus.gpars', module='gpars', version='1.2.1'),
        @Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.7.1'),
        @Grab(group='bouncycastle', module='bcprov-jdk16', version='140'),
        @Grab(group='com.google.apis', module='google-api-services-sheets', version='v4-rev108-1.22.0'),
        @Grab(group='com.google.oauth-client', module='google-oauth-client-jetty', version='1.22.0'),
        @Grab(group='com.google.api-client', module='google-api-client', version='1.22.0'),
        @Grab("org.gebish:geb-core:1.1.1"),
        //@Grab("org.gebish:geb-core:0.13.1"),
        @Grab("org.seleniumhq.selenium:selenium-chrome-driver:2.52.0"),
        @Grab("org.seleniumhq.selenium:selenium-chrome-driver:2.53.1"),
        @Grab("org.seleniumhq.selenium:selenium-support:2.53.1"),
        @Grab("org.apache.httpcomponents:httpclient:4.5.1"),
        @Grab("org.apache.httpcomponents:httpcore:4.4.3"),
        @Grab("commons-io:commons-io:2.5")
])
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriverService
import org.openqa.selenium.chrome.ChromeOptions

import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.HttpCommandExecutor
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.remote.internal.ApacheHttpClient
import org.openqa.selenium.remote.internal.HttpClientFactory

def builder = new ChromeDriverService.Builder().usingAnyFreePort().usingDriverExecutable(new File("/opt/software/chromedriver_2.28"))
def chromeDriverService = builder.build();
chromeDriverService.start();

// first
ChromeOptions chromeOptions = new ChromeOptions()
def capabilities = DesiredCapabilities.chrome();
capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions)
def clientFactory = new ApacheHttpClient.Factory(new HttpClientFactory(30000, 60000));
def commandExecutor = new HttpCommandExecutor([:], chromeDriverService.getUrl(), clientFactory)
def driver1 = new RemoteWebDriver(commandExecutor, capabilities);
driver1.navigate().to("http://google.com")

// second
chromeOptions = new ChromeOptions()
chromeOptions.addArguments("--start-maximized");
capabilities = DesiredCapabilities.chrome();
def driver2 = new RemoteWebDriver(commandExecutor, capabilities);
driver2.navigate().to("http://gmail.com")
