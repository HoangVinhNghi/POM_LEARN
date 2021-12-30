package commons;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;
import java.util.concurrent.TimeUnit;


public class AbstractPage {
	private WebDriver driver;
	protected final Log log;
	private String projectFolder = System.getProperty("user.dir");
	private String osName = System.getProperty("os.name");
	
	public AbstractPage() {
		log = LogFactory.getLog(getClass());
	}
	
	public static AbstractPage getBasePage() {
		return new AbstractPage();
	}

	public void openPageUrl(WebDriver driver, String pageUrl) {
		driver.get(pageUrl);
	}

	public String getPageTitle(WebDriver driver) {
		return driver.getTitle();
	}

	public String getPageUrl(WebDriver driver) {
		return driver.getCurrentUrl();
	}

	public String getPageSource(WebDriver driver) {
		return driver.getPageSource();
	}

	public Alert waitForAlertPresence(WebDriver driver) {
		explicitWait = new WebDriverWait(driver, longTimeout);
		return explicitWait.until(ExpectedConditions.alertIsPresent());
	}

	public void acceptAlert(WebDriver driver) {
		alert = waitForAlertPresence(driver);
		alert.accept();
		sleepInSecond(2);
	}

	public void cancelAlert(WebDriver driver) {
		alert = waitForAlertPresence(driver);
		alert.dismiss();
	}

	public void sendKeyToAlert(WebDriver driver, String value) {
		alert = waitForAlertPresence(driver);
		alert.sendKeys(value);
	}

	public String getAlertText(WebDriver driver) {
		alert = waitForAlertPresence(driver);
		return alert.getText();
	}

	public void switchToWindowByID(WebDriver driver, String windowID) {
		Set<String> allIDs = driver.getWindowHandles();
		for (String id : allIDs) {
			if (!id.equals(windowID)) {
				driver.switchTo().window(id);
				sleepInSecond(1);
				break;
			}
		}

	}

	public void switchToWindowByTitle(WebDriver driver, String expectedwindowTitle) {
		Set<String> allIDs = driver.getWindowHandles();
		for (String id : allIDs) {
			driver.switchTo().window(id);
			String actualWindowTitle = driver.getTitle();
			if (actualWindowTitle.equals(expectedwindowTitle)) {
				break;
			}

		}
	}

	public void closeAllWindowExceptParent(WebDriver driver, String windowID) {
		Set<String> allIDs = driver.getWindowHandles();

		for (String id : allIDs) {
			if (!id.equals(windowID)) {
				driver.switchTo().window(id);
				driver.close();
				sleepInSecond(1);
			}

			if (driver.getWindowHandles().size() == 1) {
				driver.switchTo().window(windowID);
				break;
			}
		}
	}
	
	public void sleepInSecond(long second) {
		try {
			Thread.sleep(second * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void sleepInMiliSecond(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void backToPage(WebDriver driver) {
		driver.navigate().back();
	}

	public void refreshCurrentPage(WebDriver driver) {
		driver.navigate().refresh();
	}

	public void forwardToPage(WebDriver driver) {
		driver.navigate().forward();
	}

	public WebElement getElement(WebDriver driver, String locator) {
		return driver.findElement(getByXpath(locator));
	}

	public WebElement getElement(WebDriver driver, String locator, String... params) {
		return driver.findElement(getByXpath(getDynamicLocator(locator, params)));
	}
	
	public List<WebElement> getElements(WebDriver driver, String locator) {
		return driver.findElements(getByXpath(locator));
	}

	public List<WebElement> getElements(WebDriver driver, String locator, String... params) {
		return driver.findElements(getByXpath(getDynamicLocator(locator, params)));
	}

	public By getByXpath(String locator) {
		return By.xpath(locator);
	}

	public String getDynamicLocator(String locator, String... params) {
		return String.format(locator, (Object[]) params);
	}

	public void clickToElement(WebDriver driver, String locator) {
		element = getElement(driver, locator);
		highlightElement(driver, locator);
		if (driver.toString().toLowerCase().contains("internet explorer")) {
			clickToElementByJS(driver, locator);
			sleepInSecond(3);
		} else {
			element.click();
		}
	}

	public void clickToElement(WebDriver driver, String locator, String... params) {
		highlightElement(driver, getDynamicLocator(locator, params));
		element = getElement(driver, getDynamicLocator(locator, params));
		if (driver.toString().toLowerCase().contains("internet explorer")) {
			clickToElementByJS(driver, locator, params);
			sleepInSecond(3);
		} else {
			element.click();
		}
	}

	public void sendKeyToElement(WebDriver driver, String locator, String value) {
		highlightElement(driver, locator);
		getElement(driver, locator).clear();
		sleepInSecond(1);
		if (driver.toString().toLowerCase().contains("edge")) {
			sleepInSecond(1);
		}
		getElement(driver, locator).sendKeys(value);
	}

	public void sendKeyToElement(WebDriver driver, String locator, String value, String... params) {
		highlightElement(driver, getDynamicLocator(locator, params));
		locator = getDynamicLocator(locator, params);
		getElement(driver, locator).clear();
		sleepInSecond(1);
		getElement(driver, locator).sendKeys(value);
	}

	public int getElementSize(WebDriver driver, String locator) {
		return getElements(driver, locator).size();
	}

	public int getElementSize(WebDriver driver, String locator, String... params) {
		locator = getDynamicLocator(locator, params);
		return getElements(driver, locator).size();
	}

	public void selectDropdownByText(WebDriver driver, String locator, String itemText) {
		highlightElement(driver, locator);
		select = new Select(getElement(driver, locator));
		select.selectByVisibleText(itemText);
	}

	public void selectDropdownByText(WebDriver driver, String locator, String itemText, String... params) {
		getDynamicLocator(locator, getDynamicLocator(locator, params));
		locator = getDynamicLocator(locator, params);
		select = new Select(getElement(driver, locator));
		select.selectByVisibleText(itemText);
	}

	public String getSlectedItemDropdown(WebDriver driver, String locator) {
		highlightElement(driver, locator);
		select = new Select(getElement(driver, locator));
		return select.getFirstSelectedOption().getText();
	}

	public boolean isDropdownMultiple(WebDriver driver, String locator) {
		select = new Select(getElement(driver, locator));
		return select.isMultiple();

	}

	public void selectItemInCustomDropdown(WebDriver driver, String parentLocator, String childItemLocator, String expectedItem) {
		getElement(driver, parentLocator).click();
		sleepInSecond(2);

		explicitWait = new WebDriverWait(driver, longTimeout);
		List<WebElement> allItems = explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(getByXpath(childItemLocator)));

		for (WebElement item : allItems) {
			if (item.getText().trim().equals(expectedItem)) {
				jsExecutor = (JavascriptExecutor) driver;
				jsExecutor.executeScript("arguments[0].scrollIntoView(true);", item);
				sleepInSecond(2);

				item.click();
				sleepInSecond(2);
				break;
			}
		}
	}

	public String getElementAttribute(WebDriver driver, String locator, String attributeName) {
		getDynamicLocator(locator, locator);
		return getElement(driver, locator).getAttribute(attributeName);
	}

	public String getElementAttribute(WebDriver driver, String locator, String attributeName, String... params) {
		return getElement(driver, getDynamicLocator(locator, params)).getAttribute(attributeName);
	}

	public String getElementText(WebDriver driver, String locator) {
		highlightElement(driver, locator);
		return getElement(driver, locator).getText().trim();
	}

	public String getElementText(WebDriver driver, String locator, String... params) {
		highlightElement(driver, getDynamicLocator(locator, params));
		return getElement(driver, getDynamicLocator(locator, params)).getText().trim();
	}

	public ArrayList getElementsText(WebDriver driver, String locator) {
		// Khai báo 1 Array List
		ArrayList<String> arrayList = new ArrayList<>();

		// Tìm tất cả element matching vs điều kiện(Name/ Price/...)
		List<WebElement> elementList = driver.findElements(By.xpath(locator));

		// Lấy text của từng element add vào Array List
		for (WebElement element : elementList) {
			arrayList.add(element.getText());
		}

		System.out.println("------------All the Items------------");
		for (String name : arrayList) {
			System.out.println(name);
		}

		// Verify 2 array bằng nhau - nếu dữ liệu sort trên UI không chính xác thì kết quả trả về sai
		return arrayList;
	}


	public void checkToCheckboxOrRadio(WebDriver driver, String locator) {
		highlightElement(driver, locator);
		if (!isElementSelected(driver, locator)) {
			getElement(driver, locator).click();
		}
	}

	public void checkToCheckboxOrRadio(WebDriver driver, String locator, String... params) {
		highlightElement(driver, getDynamicLocator(locator, params));
		locator = getDynamicLocator(locator, params);
		if (!isElementSelected(driver, locator)) {
			getElement(driver, locator).click();
		}
	}

	public void uncheckToCheckbox(WebDriver driver, String locator) {
		if (isElementSelected(driver, locator)) {
			getElement(driver, locator).click();
		}
	}

	public boolean isElementDisplayed(WebDriver driver, String locator) {
		try {
			// Displayed: Visible on UI + In DOM
			// Undisplayed: Invisible on UI + In DOM
			return getElement(driver, locator).isDisplayed();
		} catch (Exception e) {
			// Undisplayed: Invisible on UI + Not In DOM
			return false;
		}
	}
	
	public boolean isElementUndisplayed(WebDriver driver, String locator) {
		System.out.println("Start time = " + new Date().toString());
		overrideGlobalTimeout(driver, shortTimeout);
		List<WebElement> elements = getElements(driver, locator);
		overrideGlobalTimeout(driver, longTimeout);
		
		if (elements.size() == 0) {
			System.out.println("Element not in DOM and not visible on UI");
			System.out.println("End time = " + new Date().toString());
			return true;
		} else if (elements.size() > 0 && !elements.get(0).isDisplayed()) {
			System.out.println("Element in DOM but not visible on UI");
			System.out.println("End time = " + new Date().toString());
			return true;
		} else {
			System.out.println("Element in DOM and visible on UI");
			return false;
		}
	}

	public boolean isElementUndisplayed(WebDriver driver, String locator, String... params) {
		System.out.println("Start time = " + new Date().toString());
		overrideGlobalTimeout(driver, shortTimeout);
		locator = getDynamicLocator(locator, params);
		List<WebElement> elements = getElements(driver, locator);
		overrideGlobalTimeout(driver, longTimeout);

		if (elements.size() == 0) {
			System.out.println("Element not in DOM and not visible on UI");
			System.out.println("End time = " + new Date().toString());
			return true;
		} else if (elements.size() > 0 && !elements.get(0).isDisplayed()) {
			System.out.println("Element in DOM but not visible on UI");
			System.out.println("End time = " + new Date().toString());
			return true;
		} else {
			System.out.println("Element in DOM and visible on UI");
			return false;
		}
	}

	public void overrideGlobalTimeout(WebDriver driver, long timeout) {
		driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
	}

	public boolean isElementDisplayed(WebDriver driver, String locator, String... params) {
		return getElement(driver, getDynamicLocator(locator, params)).isDisplayed();
	}

	public boolean isElementEnabled(WebDriver driver, String locator) {
		return getElement(driver, locator).isEnabled();
	}

	public boolean isElementSelected(WebDriver driver, String locator) {
		return getElement(driver, locator).isSelected();
	}

	public boolean isElementSelected(WebDriver driver, String locator, String... params) {
		return getElement(driver, getDynamicLocator(locator, params)).isSelected();
	}

	public WebDriver switchToIframeByElement(WebDriver driver, String locator) {
		return driver.switchTo().frame(getElement(driver, locator));
	}

	public WebDriver switchToDefaultContent(WebDriver driver) {
		return driver.switchTo().defaultContent();
	}

	public void hoverToElement(WebDriver driver, String locator) {
		highlightElement(driver, locator);
		action = new Actions(driver);
		action.moveToElement(getElement(driver, locator)).perform();
	}
	
	public void doubleClickToElement(WebDriver driver, String locator) {
		highlightElement(driver, locator);
		action = new Actions(driver);
		action.doubleClick(getElement(driver, locator)).perform();
	}
	public void doubleClickToElement(WebDriver driver, String locator, String... params) {
		locator = getDynamicLocator(locator, params);
		highlightElement(driver, getDynamicLocator(locator, params));
		action = new Actions(driver);
		action.doubleClick(getElement(driver, locator)).perform();
	}
	
	public void rightClickToElement(WebDriver driver, String locator) {
		highlightElement(driver, locator);
		action = new Actions(driver);
		action.contextClick(getElement(driver, locator)).perform();
	}

	public void leftClickToElement(WebDriver driver, String locator) {
		highlightElement(driver, locator);
		action = new Actions(driver);
		action.click(getElement(driver, locator)).perform();
	}

	public void dragAndDropElement(WebDriver driver, String sourceLocator, String targetLocator) {
		action = new Actions(driver);
		action.dragAndDrop(getElement(driver, sourceLocator), getElement(driver, targetLocator)).perform();
	}

	public void pressKeyToElement(WebDriver driver, String locator, Keys key) {
		action = new Actions(driver);
		action.sendKeys(getElement(driver, locator), key).perform();
	}

	public void pressKeyToElement(WebDriver driver, String locator, Keys key, String... params) {
		action = new Actions(driver);
		locator = getDynamicLocator(locator, params);
		action.sendKeys(getElement(driver, locator), key).perform();
	}

	public Object executeForBrowser(WebDriver driver, String javaScript) {
		jsExecutor = (JavascriptExecutor) driver;
		return jsExecutor.executeScript(javaScript);
	}

	public String getInnerText(WebDriver driver) {
		jsExecutor = (JavascriptExecutor) driver;
		return (String) jsExecutor.executeScript("return document.documentElement.innerText;");
	}

	public boolean areExpectedTextInInnerText(WebDriver driver, String textExpected) {
		jsExecutor = (JavascriptExecutor) driver;
		String textActual = (String) jsExecutor.executeScript("return document.documentElement.innerText.match('" + textExpected + "')[0]");
		return textActual.equals(textExpected);
	}

	public void scrollToBottomPage(WebDriver driver) {
		jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("window.scrollBy(0,document.body.scrollHeight)");
	}

	public void navigateToUrlByJS(WebDriver driver, String url) {
		jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("window.location = '" + url + "'");
	}

	public void highlightElement(WebDriver driver, String locator) {
		jsExecutor = (JavascriptExecutor) driver;
		WebElement element = getElement(driver, locator);
		String originalStyle = element.getAttribute("style");
		jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style", "border: 2px solid red; border-style: dashed;");
		sleepInMiliSecond(500);
		jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style", originalStyle);
	}

	public void highlightElementToCapture(WebDriver driver, String locator) {
		jsExecutor = (JavascriptExecutor) driver;
		WebElement element = getElement(driver, locator);
		String originalStyle = element.getAttribute("style");
		jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style", "border: 2px solid red; padding: 3px; border-style: solid;");
		sleepInMiliSecond(1000);
	}

	public void clickToElementByJS(WebDriver driver, String locator) {
		jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].click();", getElement(driver, locator));
	}

	public void clickToElementByJS(WebDriver driver, String locator, String... params) {
		jsExecutor = (JavascriptExecutor) driver;
		element = getElement(driver, getDynamicLocator(locator, params));
		jsExecutor.executeScript("arguments[0].click();", element);
	}

	public void scrollToElement(WebDriver driver, String locator) {
		jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].scrollIntoView(true);", getElement(driver, locator));
	}

	public void sendkeyToElementByJS(WebDriver driver, String locator, String value) {
		jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].setAttribute('value', '" + value + "')", getElement(driver, locator));
	}

	public void removeAttributeInDOM(WebDriver driver, String locator, String attributeRemove) {
		jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].removeAttribute('" + attributeRemove + "');", getElement(driver, locator));
	}

	public boolean areJQueryAndJSLoadedSuccess(WebDriver driver) {
		explicitWait = new WebDriverWait(driver, longTimeout);
		jsExecutor = (JavascriptExecutor) driver;

		ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				try {
					return ((Long) jsExecutor.executeScript("return jQuery.active") == 0);
				} catch (Exception e) {
					return true;
				}
			}
		};

		ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return jsExecutor.executeScript("return document.readyState").toString().equals("complete");
			}
		};

		return explicitWait.until(jQueryLoad) && explicitWait.until(jsLoad);
	}

	public String getElementValidationMessage(WebDriver driver, String locator) {
		jsExecutor = (JavascriptExecutor) driver;
		return (String) jsExecutor.executeScript("return arguments[0].validationMessage;", getElement(driver, locator));
	}

	public boolean isImageLoaded(WebDriver driver, String locator) {
		jsExecutor = (JavascriptExecutor) driver;
		boolean status = (boolean) jsExecutor.executeScript("return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0", getElement(driver, locator));
		if (status) {
			return true;
		} else {
			return false;
		}
	}

	public void waitForElementVisible(WebDriver driver, String locator, String... params) {
		explicitWait = new WebDriverWait(driver, longTimeout);
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(getByXpath(getDynamicLocator(locator, params))));
	}

	public void waitForElementVisible(WebDriver driver, String locator) {
		explicitWait = new WebDriverWait(driver, longTimeout);
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(getByXpath(locator)));
	}

	public void waitForAllElementVisible(WebDriver driver, String locator) {
		explicitWait = new WebDriverWait(driver, longTimeout);
		explicitWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(getByXpath(locator)));
	}

	public void waitForAllElementVisible(WebDriver driver, String locator, String... params) {
		explicitWait = new WebDriverWait(driver, longTimeout);
		explicitWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(getByXpath(getDynamicLocator(locator, params))));
	}

	public void waitForElementClickable(WebDriver driver, String locator) {
		explicitWait = new WebDriverWait(driver, longTimeout);
		explicitWait.until(ExpectedConditions.elementToBeClickable(getByXpath(locator)));
	}

	public void waitForElementClickable(WebDriver driver, String locator, String... params) {
		explicitWait = new WebDriverWait(driver, longTimeout);
		explicitWait.until(ExpectedConditions.elementToBeClickable(getByXpath(getDynamicLocator(locator, params))));
	}

	public void waitForElementInvisible(WebDriver driver, String locator) {
		explicitWait = new WebDriverWait(driver, shortTimeout);
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(getByXpath(locator)));
	}

	public void waitForElementInvisible(WebDriver driver, String locator, String... params) {
		locator = getDynamicLocator(locator, params);
		explicitWait = new WebDriverWait(driver, shortTimeout);
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(getByXpath(locator)));
	}

	public boolean isDataStringSortedAscending(WebDriver driver, String locator) {
		// Khai báo 1 Array List
		ArrayList<String> arrayList = new ArrayList<String>();

		// Tìm tất cả element matching vs điều kiện(Name, Price/...)
		List<WebElement> elementList = driver.findElements(By.xpath(locator));

		// Lấy text của từng element add vào Array List
		for (WebElement element : elementList) {
			arrayList.add(element.getText());
		}

		System.out.println("------------The Ascending Order in UI:------------");

		for (String name : arrayList) {
			System.out.println(name);
		}

		// Copy qua 1 array list mới để SORT trong Code
		ArrayList<String> sortedList = new ArrayList<>();
		for (String child : arrayList) {
			sortedList.add(child);
		}

		// Thực hiện SORT ASC
		Collections.sort(sortedList);

		System.out.println("------------The Order by Ascending Sort Code:------------");
		for (String name : sortedList) {
			System.out.println(name);
		}

		// Verify 2 array bằng nhau - nếu dữ liệu sort trên UI không chính xác thì kết quả trả về sai
		return sortedList.equals(arrayList);
	}

	public boolean isDataStringSortedDescending(WebDriver driver, String locator) {
		// Khai báo 1 Array List
		ArrayList<String> arrayList = new ArrayList<>();

		// Tìm tất cả element matching vs điều kiện(Name/ Price/...)
		List<WebElement> elementList = driver.findElements(By.xpath(locator));

		// Lấy text của từng element add vào Array List
		for (WebElement element : elementList) {
			arrayList.add(element.getText());
		}

		System.out.println("------------The Descending Order in UI:------------");
		for (String name : arrayList) {
			System.out.println(name);
		}

		// Copy to a new array list to SORT by JAVA Code
		ArrayList<String> sortedList = new ArrayList<>();
		for (String child : arrayList) {
			sortedList.add(child);
		}

		// Thực hiện SORT ASC
		Collections.sort(sortedList);

		System.out.println("------------The Order by Ascending Sort Code:------------");
		for (String name : sortedList) {
			System.out.println(name);
		}

		// Reverse data để sort DESC (
		Collections.reverse(sortedList);

		System.out.println("------------The Order by Descending Sort Code:------------");
		for (String name : sortedList) {
			System.out.println(name);
		}

		// Verify 2 array bằng nhau - nếu dữ liệu sort trên UI không chính xác thì kết quả trả về sai
		return sortedList.equals(arrayList);
	}

	public boolean isDataFloatSortedAscending(WebDriver driver, String locator) {
		// Khai báo 1 Array List
		ArrayList<Float> arrayList = new ArrayList<Float>();

		// Tìm tất cả element matching vs điều kiện(Name/ Price/...)
		List<WebElement> elementList = driver.findElements(By.xpath(locator));

		// Lấy text của từng element add vào Array List
		for (WebElement element : elementList) {
			arrayList.add(Float.parseFloat(element.getText().replace("$", "").trim()));
		}

		System.out.println("------------Dữ liệu trên UI:------------");
		for (Float name : arrayList) {
			System.out.println(name);
		}

		// Copy qua 1 arry list mới để SORT trong Code
		ArrayList<Float> sortedList = new ArrayList<Float>();
		for (Float child : arrayList) {
			sortedList.add(child);
		}

		// Thực hiện SORT ASC
		Collections.sort(sortedList);

		System.out.println("------------Dữ liệu đã SORT ASC trong code:------------");
		for (Float name : sortedList) {
			System.out.println(name);
		}

		// Verify 2 array bằng nhau - nếu dữ liệu sort trên UI không chính xác thì kết quả trả về sai
		return sortedList.equals(arrayList);
	}

	public boolean isDataFloatSortedDescending(WebDriver driver, String locator) {
		// Khai báo 1 Array List
		ArrayList<Float> arrayList = new ArrayList<Float>();

		// Tìm tất cả element matching vs điều kiện(Name/ Price/...)
		List<WebElement> elementList = driver.findElements(By.xpath(locator));

		// Lấy text của từng element add vào Array List
		for (WebElement element : elementList) {
			arrayList.add(Float.parseFloat(element.getText().replace("$", "").trim()));
		}

		System.out.println("------------Dữ liệu trên UI:------------");
		for (Float name : arrayList) {
			System.out.println(name);
		}

		// Copy qua 1 arry list mới để SORT trong Code
		ArrayList<Float> sortedList = new ArrayList<Float>();
		for (Float child : arrayList) {
			sortedList.add(child);
		}

		// Thực hiện SORT ASC
		Collections.sort(sortedList);

		System.out.println("------------Dữ liệu đã SORT ASC trong code:------------");
		for (Float name : sortedList) {
			System.out.println(name);
		}

		// Reverse data để sort DESC (
		Collections.reverse(sortedList);

		System.out.println("------------Dữ liệu đã SORT DESC trong code:------------");
		for (Float name : sortedList) {
			System.out.println(name);
		}
		// Verify 2 array bằng nhau - nếu dữ liệu sort trên UI không chính xác thì kết quả trả về sai
		return sortedList.equals(arrayList);
	}

	public boolean isDataDateSortedAscending(WebDriver driver, String locator) {
		// Khai báo 1 Array List
		ArrayList<Date> arrayList = new ArrayList<Date>();

		// Tìm tất cả element matching vs điều kiện(Date)
		List<WebElement> elementList = driver.findElements(By.xpath(locator));

		// Lấy text của từng element add vào Array List
		for (WebElement element : elementList) {
			arrayList.add(convertStringToDate(element.getText()));
		}

		System.out.println("------------Dữ liệu trên UI:------------");
		for (Date name : arrayList) {
			System.out.println(name);
		}

		// Copy qua 1 arry list mới để SORT trong Code
		ArrayList<Date> sortedList = new ArrayList<Date>();
		for (Date child : arrayList) {
			sortedList.add(child);
		}

		// Thực hiện SORT ASC
		Collections.sort(sortedList);

		System.out.println("------------Dữ liệu đã SORT ASC trong code:------------");
		for (Date name : sortedList) {
			System.out.println(name);
		}

		// Verify 2 array bằng nhau - nếu dữ liệu sort trên UI không chính xác thì kết quả trả về sai
		return sortedList.equals(arrayList);
	}

	public boolean isDataDateSortedDescending(WebDriver driver, String locator) {
		// Khai báo 1 Array List
		ArrayList<Date> arrayList = new ArrayList<Date>();

		// Tìm tất cả element matching vs điều kiện(Date)
		List<WebElement> elementList = driver.findElements(By.xpath(locator));

		// Lấy text của từng element add vào Array List
		for (WebElement element : elementList) {
			arrayList.add(convertStringToDate(element.getText()));
		}

		System.out.println("------------Dữ liệu trên UI:------------");
		for (Date name : arrayList) {
			System.out.println(name);
		}

		// Copy qua 1 arry list mới để SORT trong Code
		ArrayList<Date> sortedList = new ArrayList<Date>();
		for (Date child : arrayList) {
			sortedList.add(child);
		}

		// Thực hiện SORT ASC
		Collections.sort(sortedList);

		System.out.println("------------Dữ liệu đã SORT ASC trong code:------------");
		for (Date name : sortedList) {
			System.out.println(name);
		}

		// Reverse data để sort DESC (
		Collections.reverse(sortedList);

		System.out.println("------------Dữ liệu đã SORT DESC trong code:------------");
		for (Date name : sortedList) {
			System.out.println(name);
		}

		// Verify 2 array bằng nhau - nếu dữ liệu sort trên UI không chính xác thì kết quả trả về sai
		return sortedList.equals(arrayList);
	}

	public Date convertStringToDate(String dateInString) {
		dateInString = dateInString.replace(",", "");
		SimpleDateFormat formatter = new SimpleDateFormat("MMM dd yyyy");
		Date date = null;
		try {
			date = formatter.parse(dateInString);
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public void deleteTextboxByRobot(WebDriver webdriver,String locator) {
		try {
			clickToElement(webdriver, locator);
			robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_A);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_A);
			robot.keyPress(KeyEvent.VK_BACK_SPACE);
			robot.keyRelease(KeyEvent.VK_BACK_SPACE);
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	
	public void pasteToTextboxByRobot(WebDriver webdriver) {
		try {
			robot = new Robot();
	        robot.keyPress(KeyEvent.VK_CONTROL);
	        robot.keyPress(KeyEvent.VK_V);
	        robot.keyRelease(KeyEvent.VK_CONTROL);
	        robot.keyRelease(KeyEvent.VK_V);
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	
	public static void takeSnapShot(WebDriver webdriver,String fileWithPath) {
		try {
			//Convert web driver object to TakeScreenshot
			TakesScreenshot scrShot =((TakesScreenshot)webdriver);

			//Call getScreenshotAs method to create image file
			File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
			//Move image file to new destination
			File DestFile=new File(fileWithPath);

			//Copy file at destination
			FileUtils.copyFile(SrcFile, DestFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getRandomNumber() {
		Random rand = new Random();
		return rand.nextInt(99999);
	}
	
	public void copyToClipboard(String copyTo) {
	    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	    StringSelection str = new StringSelection(copyTo);
	    clipboard.setContents(str, null );
	}

	public String dayOfWeekShortFormat() {
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		return new SimpleDateFormat("EE", Locale.ENGLISH).format(date.getTime());
	}

	public String dayOfWeekLongFormat() {
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		return new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime());
	}


	private enum BROWSER {
		CHROME, FIREFOX, IE, SAFARI, EDGE_LEGAXY, EDGE_CHROMIUM, CHROME_HEADLESS, FIREFOX_HEADLESS, OPERA, COCCOC;
	}

	public WebDriver getBrowserDriver(String browserName) {
		BROWSER browser = BROWSER.valueOf(browserName.toUpperCase());

		if (browser == BROWSER.FIREFOX) {
			WebDriverManager.firefoxdriver().setup();
			
			// Disable log
			System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE, "true");
			System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, projectFolder + File.separator + "browserLogs"+ File.separator + "Firefox.log");
			
			// Add extension
			FirefoxProfile profile = new FirefoxProfile();
			File file = new File(projectFolder + File.separator + "browserExtensions" + File.separator + "gg_translate-4.1.0-fx.xpi");
			profile.addExtension(file);
			
			FirefoxOptions options = new FirefoxOptions();
			options.setProfile(profile);
			options.addPreference("intl.accept_languages", "vi-vn, vi, en-us, en");
			
			// Auto Save/ Download
			options.addPreference("browser.download.folderList", "2");
			options.addPreference("browser.download.dir", projectFolder + File.separator + "downloadFiles");
			options.addPreference("browser.download.useDownloadDir", true);
			options.addPreference("browser.helperApps.neverAsk.saveToDisk", "multipart/x-zip,application/zip,"
					+ "application/x-zip-compressed,application/x-compressed,application/msword,"
					+ "application/csv,text/csv,image/jpeg,application/pdf,text/html,text/plain,"
					+ " application/excel,application/vnd.ms-excel, application/x-excel, application/x-msexcel,"
					+ " application/octet-stream");
			options.addPreference("pdfjs.disabled", true);
			
			// Private mode
			options.addArguments("-private");
			driver = new FirefoxDriver(options);
			
		} else if (browser == BROWSER.CHROME) {
			WebDriverManager.chromedriver().setup();
			
			// Disable log
			System.setProperty("webdriver.chrome.args", "--disable-logging");
			System.setProperty("webdriver.chrome.silentOutput", "true");
			
			// Add Extension
			File file = new File(projectFolder + File.separator + "browserExtensions" + File.separator + "gg_translate_2_0_9_0.crx");
			ChromeOptions options = new ChromeOptions();
			options.addExtensions(file);
			
			// Set language
			options.addArguments("--lang=vi");
			
			//Infor Bar/ Notification/ Location
			options.addArguments("--disable-infobars");
			options.addArguments("--disable-notifications");
			options.addArguments("--disable-geolocation");
			options.setExperimentalOption("useAutomationExtension", false);
			options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
			
			// Auto Save/ Dowload
			HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
			chromePrefs.put("profile.default_content_settings.popups", 0);
			chromePrefs.put("download.default_directory", projectFolder + File.separator + "downloadFiles");
			options.setExperimentalOption("prefs", chromePrefs);
			
			// Incognito
			options.addArguments("--incognito");
			driver = new ChromeDriver(options);
			
		} else if (browser == BROWSER.EDGE_CHROMIUM) {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
			
		} else if (browser == BROWSER.EDGE_LEGAXY) {
			driver = new EdgeDriver();
			
		} else if (browser == BROWSER.IE) {
			WebDriverManager.iedriver().arch32().setup();
			driver = new InternetExplorerDriver();
			
		} else if (browser == BROWSER.COCCOC) {
			WebDriverManager.chromedriver().driverVersion("88.0.4324.27").setup();
			ChromeOptions options = new ChromeOptions();
			options.setBinary("C:\\Program Files (x86)\\CocCoc\\Browser\\Application\\browser.exe");
			driver = new ChromeDriver(options);
			
		} else if (browser == BROWSER.OPERA) {
			WebDriverManager.operadriver().setup();
			driver = new OperaDriver();
		}
			else if (browser == BROWSER.CHROME_HEADLESS) {
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
			options.addArguments("headless");
			options.addArguments("window-size=1920x1080");
			driver = new ChromeDriver(options);
			
		} else if (browser == BROWSER.FIREFOX_HEADLESS) {
			WebDriverManager.firefoxdriver().setup();
			FirefoxOptions options = new FirefoxOptions();
			options.addArguments("-headless");
			options.addArguments("window-size=1920x1080");
			driver = new FirefoxDriver(options);
		} else {
			throw new RuntimeException("please enter correct browser name!");
		}

		driver.manage().window().maximize();
		return driver;
	}

	public WebDriver getBrowserDriver(String browserName, String appUrl) {
		BROWSER browser = BROWSER.valueOf(browserName.toUpperCase());
		System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE, "true");
		System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, projectFolder + File.separator + "browserLogs"+ File.separator + "Firefox.log");
		System.setProperty("webdriver.chrome.args", "--disable-logging");
		System.setProperty("webdriver.chrome.silentOutput", "true");
		
		switch (browser) {
		case FIREFOX:
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			break;
		case CHROME:
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			break;
		case EDGE_CHROMIUM:
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
			break;
		case OPERA:
			WebDriverManager.operadriver().setup();
			driver = new OperaDriver();
			break;
		case FIREFOX_HEADLESS:
			WebDriverManager.chromedriver().setup();
			FirefoxOptions options = new FirefoxOptions();
			options.addArguments("-headless");
			options.addArguments("window-size=1920x1080");
			driver = new FirefoxDriver(options);
			break;
		case CHROME_HEADLESS:
			WebDriverManager.chromedriver().setup();
			ChromeOptions options1 = new ChromeOptions();
			options1.addArguments("headless");
			options1.addArguments("window-size=1920x1080");
			driver = new ChromeDriver(options1);
			break;
		default:
			throw new RuntimeException("please enter correct browser name!");

		}

		driver.manage().window().maximize();
		driver.get(appUrl);
		return driver;
	}

	public String getRandomEmail() {
		Random rand = new Random();
		return "automationfc.vn" + rand.nextInt(9999) + "@gmail.com";
	}

	public int getRandomInt() {
		Random rand = new Random();
		return rand.nextInt(9999);
	}

	private boolean checkTrue(boolean condition) {
		boolean pass = true;
		try {
			if (condition == true) {
				log.info(" -------------------------- PASSED -------------------------- ");
			} else {
				log.info(" -------------------------- FAILED -------------------------- ");
			}
			Assert.assertTrue(condition);
		} catch (Throwable e) {
			pass = false;

			// Add lỗi vào ReportNG

		}
		return pass;
	}

	public boolean verifyTrue(boolean condition) {
		return checkTrue(condition);
	}

	private boolean checkFailed(boolean condition) {
		boolean pass = true;
		try {
			if (condition == false) {
				log.info(" -------------------------- PASSED -------------------------- ");
			} else {
				log.info(" -------------------------- FAILED -------------------------- ");
			}
			Assert.assertFalse(condition);
		} catch (Throwable e) {
			pass = false;

		}
		return pass;
	}

	public boolean verifyFalse(boolean condition) {
		return checkFailed(condition);
	}

	private boolean checkEquals(Object actual, Object expected) {
		boolean pass = true;
		try {
			Assert.assertEquals(actual, expected);
			log.info(" -------------------------- PASSED -------------------------- ");
		} catch (Throwable e) {
			pass = false;
			log.info(" -------------------------- FAILED -------------------------- ");

		}
		return pass;
	}

	public boolean verifyEquals(Object actual, Object expected) {
		return checkEquals(actual, expected);
	}

	public WebDriver getDriver() {
		return driver;
	}

	public void closeBrowserAndDriver(WebDriver driver) {
		try {
			// Get ra tên của OS và convert qua chữ thường
			String osName = System.getProperty("os.name").toLowerCase();
			log.info("OS name = " + osName);

			// Khai báo 1 biến command line để thực thi
			String cmd = "";

			// Kiểm tra xem browser đã đóng chưa - nếu chưa -> quit
			if (driver != null) {
				driver.manage().deleteAllCookies();
				driver.quit();
			}

			// Quit driver executable file in Task Manager
			if (driver.toString().toLowerCase().contains("chrome")) {
				if (osName.toLowerCase().contains("mac")) {
					cmd = "pkill chromedriver";
				} else if (osName.toLowerCase().contains("windows")) {
					cmd = "taskkill /F /FI \"IMAGENAME eq chromedriver*\"";
				}
			} else if (driver.toString().toLowerCase().contains("internetexplorer")) {
				if (osName.toLowerCase().contains("window")) {
					cmd = "taskkill /F /FI \"IMAGENAME eq IEDriverServer*\"";
				}
			} else if (driver.toString().toLowerCase().contains("firefox")) {
				if (osName.toLowerCase().contains("mac")) {
					cmd = "pkill geckodriver";
				} else if (osName.toLowerCase().contains("windows")) {
					cmd = "taskkill /F /FI \"IMAGENAME eq geckodriver*\"";
				}
			} else if (driver.toString().toLowerCase().contains("edge")) {
				if (osName.toLowerCase().contains("mac")) {
					cmd = "pkill msedgedriver";
				} else if (osName.toLowerCase().contains("windows")) {
					cmd = "taskkill /F /FI \"IMAGENAME eq msedgedriver*\"";
				}
			}

			Process process = Runtime.getRuntime().exec(cmd);
			process.waitFor();

			log.info("---------- QUIT BROWSER SUCCESS ----------");
		} catch (Exception e) {
			log.info(e.getMessage());
		}
	}
	
	public void showBrowserConsoleLogs(WebDriver driver) {
		if (driver.toString().contains("chrome")) {
			LogEntries logs = driver.manage().logs().get("browser");
			List<LogEntry> logList = logs.getAll();
			for (LogEntry logging: logList) {
				System.out.println("---------------------" + logging.getLevel().toString() + "---------------------\n" + logging.getMessage());
			}
		}
	}
	
	private Alert alert;
	private Select select;
	private Actions action;
	private long shortTimeout = 30;
	private long longTimeout = 60;
	private WebDriverWait explicitWait;
	private JavascriptExecutor jsExecutor;
	private WebElement element;
	private Robot robot;
}
