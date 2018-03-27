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

System.setProperty("webdriver.chrome.driver","/opt/software/chromedriver_2.28")
System.setProperty("webdriver.chrome.logfile", "/tmp/chromedriver.log");



	class TestRunnable1 implements Runnable {
		private Browser browser;

		public TestRunnable1(Browser browser) {
			this.browser = browser
		}
		
		public void run() {
			this.browser.drive {
				waitFor(10) {
					$("a", id:"test").click()	
				}
			}
		}
		
	}
	class TestRunnable2 implements Runnable {
		private Browser browser;

		public TestRunnable2(Browser browser) {
			this.browser = browser
		}
		
		public void run() {
			this.browser.drive {
				assert js."document.title" == "Test"
			}
		}
		
	}


Browser browser = new Browser();
browser.go("file:///home/indranilm/github/indranil32/workspace/test3.html")

Thread t1 = new Thread ( new TestRunnable1(browser)); 
Thread t2 = new Thread ( new TestRunnable2(browser)); 

t1.start();
t2.start();

Thread.sleep(10000)

browser.quit();
