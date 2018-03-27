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

import geb.Browser
import geb.Configuration
import java.util.concurrent.TimeUnit

System.setProperty("webdriver.chrome.driver","/opt/software/chromedriver_2.28")
System.setProperty("webdriver.chrome.logfile", "/tmp/chromedriver.log");
//System.setProperty("DBUS_SESSION_BUS_ADDRESS","/dev/null");

for (int i=0;i<4;i++) {
	RunnableTest rt = new RunnableTest();
	Thread t = new Thread (rt)
	t.start()
}

class RunnableTest implements Runnable {

	public void run () {
	try {
	Browser.drive {
	println "Start"
	//driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS)
    	//config.rawConfig.timeout = 5
    	//config.rawConfig.retryInterval = 0.5
    	//config.rawConfig.includeCauseInMessage = true
    	//config.rawConfig.reportOnTestFailureOnly = true
    	config.rawConfig.reportsDir = new File("/logs/geb-test-reports")
    	println config.rawConfig
    	cleanReportGroupDir()

	go "http://localhost:8080/test/goto"
	report "test goto 1"
	//assert waitFor(10){$("a" , id:"test").displayed}
	//report "test link appear 2"
	//$("a", id:"test").click();
	//assert waitFor(10) {$("h1").displayed}
	//report "test link clicked 3"
 	Thread.sleep(90000);	
	
	}.quit();
	} catch (Throwable t) {}
	}
}
