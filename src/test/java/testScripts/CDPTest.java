package testScripts;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.openqa.selenium.devtools.v115.log.model.LogEntry;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v115.log.Log;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.google.common.graph.Network;

public class CDPTest {

	ChromeDriver driver;
	DevTools devTools;

	@BeforeMethod
	public void setup() {
		driver = new ChromeDriver();
		devTools = driver.getDevTools();
		devTools.createSession();
	}

//	@Test
	public void deviceModeTest() {
		Map deveiceMetrics = new HashMap() {
			{
				put("width", 400);
				put("height", 500);
				put("deviceScaleFactor", 50);
				put("mobile", true);
			}
		};

		driver.executeCdpCommand("Emulation.setDeviceMetricsOverride", deveiceMetrics);
		driver.get("https://www.selenium.dev/");

	}

	@Test
	public void geoLocationTest() {
		Map deveiceMetrics = new HashMap() {
			{
				put("latitude", 33.748997);
				put("longitude", -84.387985);
				put("accuracy", 100);
			}
		};

		driver.executeCdpCommand("Emulation.setGeolocationOverride", deveiceMetrics);
		driver.get("https://oldnavy.gap.com/stores");

	}

	@Test
	public void captureConsoleLogTest() {
		devTools.send(Log.enable());
		devTools.addListener(Log.entryAdded(),
				new Consumer<LogEntry>() {
			public void accept(LogEntry log) {
				System.out.println("log :" + log.getText());
				System.out.println("level :" + log.getLevel() );
			}
				});
		
		driver.get("https://www.google.com/");
	}
}
