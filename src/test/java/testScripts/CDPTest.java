package testScripts;

import java.nio.channels.NetworkChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.openqa.selenium.devtools.v115.log.model.LogEntry;
import org.openqa.selenium.devtools.v115.network.model.Headers;
import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v115.log.Log;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.google.common.base.Optional;
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
		devTools.addListener(Log.entryAdded(), new Consumer<LogEntry>() {
			public void accept(LogEntry log) {
				System.out.println("log :" + log.getText());
				System.out.println("level :" + log.getLevel());
			}
		});

		driver.get("https://www.google.com/");
	}

//	@Test
//	public void basicAuthTest() {
//		devTools.send(Network.enable(Optional.empty(),Optional.empty(),Optional.empty()));
//		Map<String, Object> headers = new HashMap();
//		String strUser = "admin";
//		String strPwd = "admin";
//
//		String basicAuth = "Basic "
//				+ new String(new Base64().encode(String.format("%s:%s", strUser, strPwd).getBytes()));
//		System.out.println("Auth : " + basicAuth);
//
//		//Set Header - Auth token
//		headers.put("Authorization", basicAuth);
//
//		devTools.send(Network.setExtraHTTPHeaders(new Headers(headers)));
//
//		driver.get("https://the-internet.herokuapp.com/basic_auth");
//
//		String success = driver.findElement(By.cssSelector("div.example p")).getText();
//		
//		Assert.assertEquals(success, "Congratulations! You must have the proper credentials.");
//
//	}
}
