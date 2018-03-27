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



Browser.drive {
	println "Start"
    	println config.rawConfig

	go "https://host.com"
	println driver.pageSource
	def frames = $("frame") + $("iframe")
	def executeOnFramesInternal = {
		pframes, closure ->
        	if (pframes.size() > 1) {
            		withFrame(pframes[0]) {
                		executeOnFramesInternal(pframes.drop(1), closure)
            		}
        	} else {
            		withFrame(pframes[0], closure)
        	}
	}
	def htmls = frames.collect{
                executeOnFramesInternal([it]){
                    "<html>" + $("html", 0).attr('innerHTML') + "</html>"
                }
            }
        htmls.eachWithIndex{ html, index  ->
		println index
		//println html
	}
	
}.quit();
