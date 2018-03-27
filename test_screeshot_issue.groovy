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

System.setProperty("webdriver.chrome.driver","/opt/software/chromedriver")
System.setProperty("webdriver.chrome.logfile", "/tmp/chromedriver.log");



Browser.drive {
	println "Start"
    	driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS)
        config.rawConfig.timeout = 10
        config.rawConfig.retryInterval = 0.5
        config.rawConfig.includeCauseInMessage = true
        //config.rawConfig.reportOnTestFailureOnly = true
	config.rawConfig.reportsDir = new File("/logs/geb-test-reports")
        println config.rawConfig
        //cleanReportGroupDir()

	try {
		go "file:///home/indranilm/github/indranil32/workspace/test4.html"
	} catch (Throwable t) {
		t.printStackTrace();
	}
	report("123")
	//go "file:///home/indranilm/github/indranil32/workspace/test4.html"
	
}.quit();
