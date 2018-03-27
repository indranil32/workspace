@Grapes([
        @Grab("org.gebish:geb-core:1.1.1"),
        @Grab("org.seleniumhq.selenium:selenium-chrome-driver:2.52.0"),
        @Grab("org.seleniumhq.selenium:selenium-support:2.52.0"),
        @Grab("org.apache.httpcomponents:httpclient:4.5.1"),
        @Grab("org.apache.httpcomponents:httpcore:4.4.3")

])
import geb.Browser
import geb.Page
//import org.openqa.selenium.chrome.ChromeDriver
//import org.openqa.selenium.WebDriver

System.setProperty("webdriver.chrome.driver","/opt/software/chromedriver")
System.setProperty("geb.build.baseUrl","file:///home/indranilm/github/workspace/")


class TestPage extends Page {
    static url = "test2.html"

    static at = {
        $("h1").text() == "Test 2 page"
    }
}

class BlankPage extends Page {
    static at = { $("html").size() == 0 }
}

class EmptyBodyPage extends Page {
    static at = { $("body").children().size() == 0 }
}

class NoFramesPage extends Page {
    static at = { $("frames").size() == 0 }
}

// Internal Server error
//
Browser.drive {
  config.rawConfig.unexpectedPages=[BlankPage,EmptyBodyPage,NoFramesPage]
  go "test1.html"
  assert title == "test html"
  //assert $("div").size() == 0 
  Thread.sleep(5000)
  to TestPage
  Thread.sleep(5000)
}.quit();

