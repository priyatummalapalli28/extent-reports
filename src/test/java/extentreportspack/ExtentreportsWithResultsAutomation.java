package extentreportspack;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ExtentreportsWithResultsAutomation
{
	public static void main(String[] args) throws Exception
	{
		//Create ExtentReports class object
		ExtentReports er=new ExtentReports("amazontitletestres.html",false);
		ExtentTest et=er.startTest("Amazon Title test Results");
		//Open browser
		WebDriverManager.chromedriver().setup();
		ChromeDriver driver=new ChromeDriver();
		//launch site
		driver.get("https://www.amazon.in");
		//Maximize
		driver.manage().window().maximize();
		//Get title
		String title=driver.getTitle();
		if(title.contains("Amazon"))
		{
			et.log(LogStatus.PASS,"Amazon Title test passed");
		}
		else
		{
			//Screenshot
			SimpleDateFormat sf=new SimpleDateFormat("dd-MMM-yyyy-hh-mm-ss");
			Date dt=new Date();
			String fname=sf.format(dt)+".png";
			File src=driver.getScreenshotAs(OutputType.FILE);
			File dest=new File(fname);
			FileHandler.copy(src, dest);
			et.log(LogStatus.FAIL,et.addScreenCapture(fname),"Amazon Title test failed");
		}
		//Close site
		driver.close();
		
		//save extentreports file
		er.endTest(et);
		er.flush();
		er.close();
		
		//Automating results file
		ChromeDriver driver1=new ChromeDriver();
		driver1.get(System.getProperty("user.dir")+"\\amazontitletestres.html");
		driver1.manage().window().maximize();
		List<WebElement> l=driver1.findElements(By.xpath("//*[@id='test-collection']/li"));
		Thread.sleep(3000);
		int size=l.size();
		l.get(size-1).click();
		Thread.sleep(2000);
		String x=driver1.findElement(By.xpath("//*[@id='test-collection']/li["+size+"]/div/span[2]")).getText();
		x=x.toLowerCase();
		if(x.contains("pass"))
		{
			driver1.findElement(By.xpath("//*[@id='slide-out']/li[2]/a/i")).click();
		}
		else
		{
			try
			{
				driver1.findElement(By.xpath("(//*[@class='report-img'])[2]")).click();
			}
			catch(Exception ex)
			{
			}
		}
	}
}
