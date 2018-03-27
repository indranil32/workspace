@Grapes([
        @Grab(group='org.codehaus.gpars', module='gpars', version='1.2.1'),
        @Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.7.1'),
        @Grab(group='bouncycastle', module='bcprov-jdk16', version='140'),
        @Grab(group='com.google.apis', module='google-api-services-sheets', version='v4-rev108-1.22.0'),
        @Grab(group='com.google.oauth-client', module='google-oauth-client-jetty', version='1.22.0'),
        @Grab(group='com.google.api-client', module='google-api-client', version='1.22.0'),
        @Grab("org.gebish:geb-core:1.1.1"),
        //@Grab("org.gebish:geb-core:0.13.1"),
        //@Grab("org.seleniumhq.selenium:selenium-chrome-driver:2.52.0"),
        //@Grab("org.seleniumhq.selenium:selenium-chrome-driver:2.53.1"),
        //@Grab("org.openqa.selenium.ie.InternetExplorerDriver"),
        @Grab("org.seleniumhq.selenium:selenium-ie-driver:2.53.1"),
        @Grab("org.seleniumhq.selenium:selenium-support:2.53.1"),
        @Grab("org.apache.httpcomponents:httpclient:4.5.1"),
        @Grab("org.apache.httpcomponents:httpcore:4.4.3"),
        @Grab("commons-io:commons-io:2.5")
])

import geb.Browser
import geb.Configuration
import geb.module.Checkbox


System.setProperty("webdriver.ie.driver","ie.sh")
System.setProperty("webdriver.ie.logfile", "/tmp/ie.log");

Browser.drive {
	println "Start"
    	config.rawConfig.timeout = 10
    	config.rawConfig.retryInterval = 0.5
    	config.rawConfig.includeCauseInMessage = true
    	config.rawConfig.reportOnTestFailureOnly = true
    	config.rawConfig.reportsDir = new File("/logs/geb-test-reports")

    	println config.rawConfig
    	cleanReportGroupDir()

	go "https://personalloan.host.com/personal-loan/"
	report "PL 1"
	assert waitFor(10){$("button" , id:"submit_first_step").displayed}
	$("input", id:"loan_amount").value(100000)
	$("select", id:"employment_type_id").value(1)
	$("input", id:"monthly_income").value(100000)
	$("select", id:"city").value(420)//"Chennai")
	$("input", id:"email").value("test@gmail.com")
	$("input", id:"mobile_number").value("9786157411")
	def checkbox = $("input", id: "terms").module(Checkbox)
	checkbox.check()
	$("button", id:"submit_first_step").click()
	report "PL 2"
 	Thread.sleep(20000);	
	
}.quit();

