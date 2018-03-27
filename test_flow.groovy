@Grapes([
        //@Grab("org.gebish:geb-core:1.1.1"),
        @Grab("org.gebish:geb-core:0.13.1"),
        @Grab("org.seleniumhq.selenium:selenium-chrome-driver:2.52.0"),
        @Grab("org.seleniumhq.selenium:selenium-chrome-driver:2.53.1"),
        @Grab("org.seleniumhq.selenium:selenium-support:2.53.1"),
        @Grab("org.apache.httpcomponents:httpclient:4.5.1"),
        @Grab("org.apache.httpcomponents:httpcore:4.4.3")
        
])
import geb.Browser
import geb.Configuration
import geb.report.PageSourceReporter
import geb.report.ReportState
import geb.report.ScreenshotReporter
import java.awt.image.BufferedImage
import javax.imageio.IIOImage
import javax.imageio.ImageIO
import javax.imageio.ImageWriteParam
import javax.imageio.ImageWriter
import javax.imageio.stream.ImageOutputStream
import javax.imageio.plugins.jpeg.JPEGImageWriteParam
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriverService
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.HttpCommandExecutor
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.remote.internal.ApacheHttpClient
import org.openqa.selenium.remote.internal.HttpClientFactory

//System.setProperty("webdriver.chrome.driver","/opt/software/chromedriver_2.28")
//System.setProperty("webdriver.chrome.logfile", "/tmp/chromedriver.log");

class TestCompositeReporter extends ScreenshotReporter {

    private PageSourceReporter html = new PageSourceReporter();
    
    private ScreenshotReporter screenShot;
	
    @Override
    void writeReport(ReportState reportState) {
        html.writeReport(reportState)
        super.writeReport(reportState)
    println 'memory used ' +Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()
    }
	
        @Override
	protected File saveScreenshotPngBytes(File outputDir, String label, byte[] bytes) {
		def file = getFile(outputDir, label, 'JPG')
		file.withOutputStream { it << pngBytesToJpgBytes(bytes) }
		file
	}
	
	
       private byte[] pngBytesToJpgBytes(byte[] pngBytes) throws IOException {
		//create InputStream for ImageIO using png byte[]
		ByteArrayInputStream bais = new ByteArrayInputStream(pngBytes);
    println 'memory used ' +Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()
		//read png bytes as an image
		BufferedImage bufferedImagePNG = ImageIO.read(bais);
    println 'memory used ' +Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()
		//create OutputStream to write prepared jpg bytes
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
    println 'memory used ' +Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()
		//write image as jpg bytes
		ImageIO.write(bufferedImagePNG, 'jpg' /*FORMAT*/, baos);
    println 'memory used ' +Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()
		BufferedImage bufferedImageJPG = ImageIO.read(new ByteArrayInputStream(baos.toByteArray()));
    println 'memory used ' +Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()
		// compress and convert OutputStream to a byte[]
		return compressImages(bufferedImageJPG)
		/*byte[] jpegBytes = baos.toByteArray();
		bais.close();
		bais.close();
		return jpegBytes*/
	}
	
	private byte[] compressImages(BufferedImage image) {
		ImageWriter writer = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageOutputStream ios = ImageIO.createImageOutputStream(baos);
    println 'memory used ' +Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()
		Iterator<ImageWriter> iwi = ImageIO.getImageWritersByFormatName('jpg')//FORMAT);
		if (!iwi.hasNext())
			return;
		writer = (ImageWriter) iwi.next();
		ImageWriteParam iwp = new JPEGImageWriteParam(Locale.getDefault());//writer.getDefaultWriteParam();
		iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT) ;
		iwp.setCompressionQuality(0.5)//COMPRESSION_QUALITY);
		writer.setOutput(ios);
		writer.write(null, new IIOImage(image, null, null), iwp);
    println 'memory used ' +Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()
		ios.flush();
		writer.dispose();
		ios.close();
    println 'memory used ' +Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()
		return baos.toByteArray();
	}
}

DesiredCapabilities getConfiguration(Map params) {
        ChromeOptions chromeOptions = new ChromeOptions()
        chromeOptions.addArguments("ignore-certificate-errors", "disable-popup-blocking", "disable-translate")
        HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
        chromePrefs.put("download.default_directory", "/logs/geb-test-reports")//params.downloadPath);
        chromePrefs.put("download.prompt_for_download", false);
        chromePrefs.put("plugins.plugins_list", [["enabled": false,"name":"Chrome PDF Viewer"]])

        chromeOptions.setExperimentalOption("prefs", chromePrefs)

        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions)
        capabilities
}

def capabilities = getConfiguration(null)
ChromeDriverService.Builder builder = new ChromeDriverService.Builder()
                .usingAnyFreePort()
                .usingDriverExecutable(new File("/opt/software/chromedriver_2.28"))
// for headless dispplay
// builder.withEnvironment(ImmutableMap.of("DISPLAY", manager.port))
def chromeDriverService = builder.build();
chromeDriverService.start(); 
def clientFactory = new ApacheHttpClient.Factory(new HttpClientFactory(30000, 60000));
def commandExecutor = new HttpCommandExecutor([:], chromeDriverService.getUrl(), clientFactory)
def driver = new RemoteWebDriver(commandExecutor, capabilities)
def configuration = new Configuration(atCheckWaiting: true, reportsDir: "/logs/geb-test-reports")// , reporter: new TestCompositeReporter())
configuration.setReporter(new TestCompositeReporter())
def browser = new Browser(configuration , driver: driver)
browser.cleanReportGroupDir()
browser.go "https://online.com"
browser.report "go"
browser.quit()

//println args[0]
/*
Browser.drive {
    println "Start"
    config.rawConfig.timeout = 10
    config.rawConfig.retryInterval = 0.5
    config.rawConfig.includeCauseInMessage = true
    config.rawConfig.reportOnTestFailureOnly = true
    config.rawConfig.reportsDir = new File("/logs/geb-test-reports")
    config.rawConfig.reporter = new TestCompositeReporter() 
    
    println config.rawConfig
    cleanReportGroupDir()
    go "https://online.com"
    println 'memory used ' +Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()
    report "go"
    println 'memory used ' +Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()
    assert waitFor(10) {$("input", id:"AuthenticationFG.CUSTOM_USER_PRINCIPAL").displayed}
    $("input", id:"AuthenticationFG.CUSTOM_USER_PRINCIPAL").value();
    //$("input", id : "STU_VALIDATE_CREDENTIALS").click();
    //waitFor(30) {$("input", id : "AuthenticationFG.TARGET_CHECKBOX").value('Y')}
    //$("input", id : "AuthenticationFG.ACCESS_CODE").value('')
    //$("input", id : "VALIDATE_STU_CREDENTIALS_UX").click()
    Thread.sleep(2000)
    println "End"
}.quit();
*/
