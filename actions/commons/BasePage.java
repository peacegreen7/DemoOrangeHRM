package commons;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import pageUIs.commons.BasePageUI;

public class BasePage {
	public static BasePage getBasePage() {
		return new BasePage();
	}

	/** commons functions for page */

	/**
	 * Load a new web page in the current browser window.
	 * 
	 * @param driver  current driver
	 * @param pageUrl URL of page need to load
	 */
	public void openPageUrl(WebDriver driver, String pageUrl) {
		driver.get(pageUrl);
	}

	/**
	 * The title of the current page.
	 * 
	 * @param driver
	 * @return The title of the current page, with leading and trailing whitespace
	 *         stripped, or null if one is not already set
	 */
	public String getPageTitle(WebDriver driver) {
		return driver.getTitle();
	}

	/**
	 * Get a string representing the current URL that the browser is looking at.
	 * 
	 * @param driver
	 * @return The URL of the page currently loaded in the browser
	 */
	public String getPageUrl(WebDriver driver) {
		return driver.getCurrentUrl();
	}

	/**
	 * Get the source of the last loaded page. If the page has been modified after
	 * loading (for example, by Javascript) there is no guarantee that the returned
	 * text is that of the modified page. Please consult the documentation of the
	 * particular driver being used to determine whether the returned text reflects
	 * the current state of the page or the text last sent by the web server. The
	 * page source returned is a representation of the underlying DOM: do not expect
	 * it to be formatted or escaped in the same way as the response sent from the
	 * web server. Think of it as an artist's impression.
	 * 
	 * @param driver
	 * @return The source of the current page
	 */
	public String getPageSource(WebDriver driver) {
		return driver.getPageSource();
	}

	/**
	 * Wait until the alert popup presence
	 * 
	 * @param driver
	 * @return The alert popup is present in page
	 */
	public Alert waitForAlertPresence(WebDriver driver) {
		explicitWait = new WebDriverWait(driver, shortTimeout);
		return explicitWait.until(ExpectedConditions.alertIsPresent());
	}

	/**
	 * Accept alert popup
	 * 
	 * @param driver
	 */
	public void acceptAlert(WebDriver driver) {
		alert = waitForAlertPresence(driver);
		alert.accept();
	}

	/**
	 * Decline alert popup
	 * 
	 * @param driver
	 */
	public void cancelAlert(WebDriver driver) {
		alert = waitForAlertPresence(driver);
		alert.dismiss();
	}

	/**
	 * Send text to prompt alert popup
	 * 
	 * @param driver
	 * @param key
	 */
	public void sendkeyToAlert(WebDriver driver, String key) {
		alert = waitForAlertPresence(driver);
		alert.sendKeys(key);
	}

	/**
	 * Get display text of alert
	 * 
	 * @param driver
	 * @return
	 */
	public String getAlertText(WebDriver driver) {
		alert = waitForAlertPresence(driver);
		return alert.getText();
	}

	/**
	 * Switch the focus of future commands for this driver to the window with the
	 * given ID handle.
	 * 
	 * @param driver
	 * @param parentID The ID of current window
	 */
	public void switchToWindowByID(WebDriver driver, String parentID) {
		Set<String> allWindows = driver.getWindowHandles();
		for (String runWindow : allWindows) {
			if (!runWindow.equals(parentID)) {
				driver.switchTo().window(runWindow);
				break;
			}
		}
	}

	/**
	 * 
	 * Switch the focus of future commands for this driver to the window with the
	 * given ID handle.
	 * 
	 * @param driver
	 * @param title  The title of current window
	 */
	public void switchToWindowByTitle(WebDriver driver, String title) {
		Set<String> allWindows = driver.getWindowHandles();
		for (String runWindows : allWindows) {
			driver.switchTo().window(runWindows);
			String currentWin = driver.getTitle();
			if (currentWin.equals(title)) {
				break;
			}
		}
	}

	/**
	 * 
	 * @param driver
	 * @param parentID
	 */
	public void closeAllWindowsWithoutParent(WebDriver driver, String parentID) {
		Set<String> allWindows = driver.getWindowHandles();
		for (String runWindows : allWindows) {
			if (!runWindows.equals(parentID)) {
				driver.switchTo().window(runWindows);
				driver.close();
			}
		}
		driver.switchTo().window(parentID);
	}

	/**
	 * Move back a single "item" in the browser's history.
	 * 
	 * @param driver
	 */
	public void backToPage(WebDriver driver) {
		driver.navigate().back();
	}

	/**
	 * Refresh the current page
	 * 
	 * @param driver
	 */
	public void refreshCurrentPage(WebDriver driver) {
		driver.navigate().refresh();
	}

	/**
	 * Move a single "item" forward in the browser's history. Does nothing if we are
	 * on the latest page viewed.
	 * 
	 * @param driver
	 */
	public void forwardToPage(WebDriver driver) {
		driver.navigate().forward();
	}

	/**
	 * 
	 * @param locator The XPath to use.
	 * @return A By which locates elements via XPath.
	 */
	public By getByXpath(String locator) {
		return By.xpath(locator);
	}

	/**
	 * Find the first WebElement using the given method.
	 * 
	 * @param driver
	 * @param locator The locating mechanism
	 * @return The first matching element on the current page
	 */
	public WebElement getElement(WebDriver driver, String locator) {
		return driver.findElement(getByXpath(locator));
	}

	/**
	 * Find the first WebElement using the given method.
	 * 
	 * @param driver
	 * @param locator
	 * @param params
	 * @return The first matching element on the current page
	 */
	public WebElement getElement(WebDriver driver, String locator, String... params) {
		return driver.findElement(getByXpath(getDynamicLocator(locator, params)));
	}

	/**
	 * 
	 * @param driver
	 * @param locator
	 * @return
	 */
	public List<WebElement> getElements(WebDriver driver, String locator) {
		return driver.findElements(getByXpath(locator));
	}

	/**
	 * 
	 * @param driver
	 * @param locator
	 */
	public void clickToElement(WebDriver driver, String locator) {
		getElement(driver, locator).click();
	}

	/**
	 * 
	 * @param driver
	 * @param locator
	 * @param params
	 */
	public void clickToElement(WebDriver driver, String locator, String... params) {
		getElement(driver, getDynamicLocator(locator, params)).click();
	}

	/**
	 * 
	 * @param driver
	 * @param locator
	 * @param itemText
	 */
	public void sendkeyToElement(WebDriver driver, String locator, String itemText) {
		getElement(driver, locator).clear();
		getElement(driver, locator).sendKeys(itemText);
	}

	/**
	 * 
	 * @param driver
	 * @param locator
	 * @param itemText
	 * @param params
	 */
	public void sendkeyToElement(WebDriver driver, String locator, String itemText, String... params) {
		getElement(driver, getDynamicLocator(locator, params)).sendKeys(itemText);
	}

	/**
	 * 
	 * @param driver
	 * @param locator
	 * @return
	 */
	public int getElementSize(WebDriver driver, String locator) {
		return getElements(driver, locator).size();
	}

	/**
	 * 
	 * @param driver
	 * @param locator
	 * @param params
	 * @return
	 */
	public int getElementSize(WebDriver driver, String locator, String... params) {
		return getElements(driver, getDynamicLocator(locator, params)).size();
	}

	/**
	 * 
	 * @param driver
	 * @param locator
	 * @param itemText
	 */
	public void selectDropdownByText(WebDriver driver, String locator, String itemText) {
		select = new Select(getElement(driver, locator));
		select.selectByVisibleText(itemText);
	}

	/**
	 * 
	 * @param driver
	 * @param locator
	 * @param itemText
	 * @param params
	 */
	public void selectDropdownByText(WebDriver driver, String locator, String itemText, String... params) {
		select = new Select(getElement(driver, getDynamicLocator(locator, params)));
		select.selectByVisibleText(itemText);
	}

	/**
	 * 
	 * @param driver
	 * @param locator
	 * @return
	 */
	public String getSelectedItemDropdown(WebDriver driver, String locator) {
		select = new Select(getElement(driver, locator));
		return select.getFirstSelectedOption().getText();
	}

	/**
	 * 
	 * @param driver
	 * @param locator
	 * @return
	 */
	public boolean isDropdownMultiple(WebDriver driver, String locator) {
		select = new Select(getElement(driver, locator));
		return select.isMultiple();
	}

	/**
	 * 
	 * @param driver
	 * @param parentLocator
	 * @param childItemLocator
	 * @param expectedItem
	 */
	// select item in custom dropdown
	public void selectItemInCustomDropdown(WebDriver driver, String parentLocator, String childItemLocator,
			String expectedItem) {
		getElement(driver, parentLocator).click();
		sleepInSecond(1);

		explicitWait = new WebDriverWait(driver, shortTimeout);
		List<WebElement> allItems = explicitWait
				.until(ExpectedConditions.presenceOfAllElementsLocatedBy(getByXpath(childItemLocator)));

		for (WebElement item : allItems) {
			if (item.getText().trim().equals(expectedItem)) {
				jsExecutor = (JavascriptExecutor) driver;
				jsExecutor.executeScript("arguments[0].scrollIntoView(true);", item);
				sleepInSecond(1);

				item.click();
				sleepInSecond(1);
				break;
			}
		}
	}

	/**
	 * 
	 * @param driver
	 * @param locator
	 * @param attributeName
	 * @return
	 */
	public String getElementAttribute(WebDriver driver, String locator, String attributeName) {
		return getElement(driver, locator).getAttribute(attributeName);
	}

	/**
	 * 
	 * @param driver
	 * @param locator
	 * @param attributeName
	 * @param params
	 * @return
	 */
	public String getElementAttribute(WebDriver driver, String locator, String attributeName, String... params) {
		return getElement(driver, getDynamicLocator(locator, params)).getAttribute(attributeName);
	}

	/**
	 * 
	 * @param driver
	 * @param locator
	 * @return
	 */
	public String getElementText(WebDriver driver, String locator) {
		return getElement(driver, locator).getText();
	}

	/**
	 * 
	 * @param driver
	 * @param locator
	 */
	public void checkToCheckboxOrRadio(WebDriver driver, String locator) {
		// verify checkbox or radio selected/not-selected
		if (!getElement(driver, locator).isSelected()) {
			getElement(driver, locator).click();
		}
	}

	/**
	 * 
	 * @param driver
	 * @param locator
	 */
	public void uncheckToCheckbox(WebDriver driver, String locator) {
		if (getElement(driver, locator).isSelected()) {
			getElement(driver, locator).click();
		}
	}

	/**
	 * 
	 * @param driver
	 * @param locator
	 * @return
	 */
	public boolean isElementDisplayed(WebDriver driver, String locator) {
		try {
			return getElement(driver, locator).isDisplayed();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 
	 * @param driver
	 * @param locator
	 * @param params
	 * @return
	 */
	public boolean isElementDisplayed(WebDriver driver, String locator, String... params) {
		return getElement(driver, getDynamicLocator(locator, params)).isDisplayed();
	}

	/**
	 * 
	 * @param driver
	 * @param locator
	 * @return
	 */
	public boolean isElementUndisplayed(WebDriver driver, String locator) {
		overrideGlobalTimeout(driver, shortTimeout);
		List<WebElement> elements = getElements(driver, locator);
		overrideGlobalTimeout(driver, longTimeout);

		System.out.println("Start time: " + new Date().toString());
		if (elements.size() == 0) {
			System.out.println("Element is not exists in DOM");
			System.out.println("End time: " + new Date().toString());
			return true;
		} else if (elements.size() > 0 && !elements.get(0).isDisplayed()) {
			System.out.println("Element is in DOM but not visible/displayed");
			System.out.println("End time: " + new Date().toString());
			return true;
		} else {
			System.out.println("Element is is visible");
			return false;
		}
	}

	/**
	 * 
	 * @param driver
	 * @param timeOut
	 */
	private void overrideGlobalTimeout(WebDriver driver, int timeOut) {
		driver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.SECONDS);
	}

	/**
	 * 
	 * @param driver
	 * @param locator
	 * @return
	 */
	public boolean isElementEnabled(WebDriver driver, String locator) {
		return getElement(driver, locator).isEnabled();
	}

	/**
	 * 
	 * @param driver
	 * @param locator
	 * @return
	 */
	public boolean isElementSelected(WebDriver driver, String locator) {
		return getElement(driver, locator).isSelected();
	}

	/**
	 * 
	 * @param driver
	 * @param locator
	 * @return
	 */
	public WebDriver switchToIframe(WebDriver driver, String locator) {
		return driver.switchTo().frame(getElement(driver, locator));
	}

	/**
	 * 
	 * @param driver
	 * @return
	 */
	public WebDriver switchToDefaultContent(WebDriver driver) {
		return driver.switchTo().defaultContent();
	}

	/**
	 * 
	 * @param driver
	 * @param locator
	 */
	public void doubleClickToElement(WebDriver driver, String locator) {
		actions = new Actions(driver);
		actions.doubleClick(getElement(driver, locator)).perform();
	}

	/**
	 * 
	 * @param driver
	 * @param locator
	 */
	public void moveToElement(WebDriver driver, String locator) {
		actions = new Actions(driver);
		actions.moveToElement(getElement(driver, locator)).perform();
	}

	/***
	 * 
	 * @param driver
	 * @param locator
	 */
	public void rightClickToElement(WebDriver driver, String locator) {
		actions = new Actions(driver);
		actions.contextClick(getElement(driver, locator)).perform();
	}

	/**
	 * 
	 * @param driver
	 * @param sourceLocator
	 * @param targetLocator
	 */
	public void dragAndDrop(WebDriver driver, String sourceLocator, String targetLocator) {
		actions = new Actions(driver);
		actions.dragAndDrop(getElement(driver, sourceLocator), getElement(driver, targetLocator)).perform();
	}

	/**
	 * 
	 * @param driver
	 * @param locator
	 * @param key
	 */
	public void pressKeyToElement(WebDriver driver, String locator, Keys key) {
		actions = new Actions(driver);
		actions.sendKeys(getElement(driver, locator), key).perform();
	}

	/**
	 * 
	 * @param driver
	 * @param javaScript
	 * @return
	 */
	public Object executeForBrowser(WebDriver driver, String javaScript) {
		jsExecutor = (JavascriptExecutor) driver;
		return jsExecutor.executeScript(javaScript);
	}

	/**
	 * 
	 * @param driver
	 * @return
	 */
	public String getInnerText(WebDriver driver) {
		jsExecutor = (JavascriptExecutor) driver;
		return (String) jsExecutor.executeScript("return document.documentElement.innerText;");
	}

	/**
	 * 
	 * @param driver
	 * @param textExpected
	 * @return
	 */
	public boolean areExpectedTextInInnerText(WebDriver driver, String textExpected) {
		jsExecutor = (JavascriptExecutor) driver;
		String textActual = (String) jsExecutor
				.executeScript("return document.documentElement.innerText.match('" + textExpected + "')[0]");
		return textActual.equals(textExpected);
	}

	/**
	 * 
	 * @param driver
	 */
	public void scrollToBottomPage(WebDriver driver) {
		jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("window.scrollBy(0,document.body.scrollHeight)");
	}

	/**
	 * 
	 * @param driver
	 */
	public void scrollToTopPage(WebDriver driver) {
		jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("window.scrollTo(0,0)");
	}

	/**
	 * 
	 * @param driver
	 * @param url
	 */
	public void navigateToUrlByJS(WebDriver driver, String url) {
		jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("window.location = '" + url + "'");
	}

	/**
	 * 
	 * @param driver
	 * @param locator
	 */
	public void highlightElement(WebDriver driver, String locator) {
		jsExecutor = (JavascriptExecutor) driver;
		WebElement element = getElement(driver, locator);
		String originalStyle = element.getAttribute("style");
		jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style",
				"border: 2px solid red; border-style: dashed;");
		sleepInSecond(1);
		jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style",
				originalStyle);
	}

	/**
	 * 
	 * @param driver
	 * @param locator
	 */
	public void clickToElementByJS(WebDriver driver, String locator) {
		jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].click();", getElement(driver, locator));
	}

	/**
	 * 
	 * @param driver
	 * @param locator
	 */
	public void scrollToElement(WebDriver driver, String locator) {
		jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].scrollIntoView(true);", getElement(driver, locator));
	}

	/**
	 * 
	 * @param driver
	 * @param locator
	 * @param value
	 */
	public void sendkeyToElementByJS(WebDriver driver, String locator, String value) {
		jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].setAttribute('value', '" + value + "')", getElement(driver, locator));
	}

	/**
	 * 
	 * @param driver
	 * @param locator
	 * @param attributeRemove
	 */
	public void removeAttributeInDOM(WebDriver driver, String locator, String attributeRemove) {
		jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].removeAttribute('" + attributeRemove + "');",
				getElement(driver, locator));
	}

	/**
	 * 
	 * @param driver
	 * @return
	 */
	public boolean areJQueryAndJSLoadedSuccess(WebDriver driver) {
		explicitWait = new WebDriverWait(driver, shortTimeout);
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

	/**
	 * 
	 * @param driver
	 * @param locator
	 * @return
	 */
	public String getElementValidationMessage(WebDriver driver, String locator) {
		jsExecutor = (JavascriptExecutor) driver;
		return (String) jsExecutor.executeScript("return arguments[0].validationMessage;", getElement(driver, locator));
	}

	/**
	 * 
	 * @param driver
	 * @param locator
	 * @return
	 */
	public boolean isImageLoaded(WebDriver driver, String locator) {
		jsExecutor = (JavascriptExecutor) driver;
		boolean status = (boolean) jsExecutor.executeScript(
				"return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0",
				getElement(driver, locator));
		if (status) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @param driver
	 * @param locator
	 */
	public void waitForElementVisible(WebDriver driver, String locator) {
		explicitWait = new WebDriverWait(driver, shortTimeout);
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(getByXpath(locator)));

	}

	/**
	 * 
	 * @param driver
	 * @param locator
	 * @param params
	 */
	public void waitForElementVisible(WebDriver driver, String locator, String... params) {
		explicitWait = new WebDriverWait(driver, shortTimeout);
		explicitWait
				.until(ExpectedConditions.visibilityOfElementLocated(getByXpath(getDynamicLocator(locator, params))));

	}

	/**
	 * 
	 * @param driver
	 * @param locator
	 */
	public void waitForAllElementsVisible(WebDriver driver, String locator) {
		explicitWait = new WebDriverWait(driver, shortTimeout);
		explicitWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(getByXpath(locator)));

	}

	/**
	 * 
	 * @param driver
	 * @param locator
	 */
	public void waitForElementClickable(WebDriver driver, String locator) {
		explicitWait = new WebDriverWait(driver, shortTimeout);
		explicitWait.until(ExpectedConditions.elementToBeClickable(getByXpath(locator)));
	}

	/**
	 * 
	 * @param driver
	 * @param locator
	 * @param params
	 */
	public void waitForElementClickable(WebDriver driver, String locator, String... params) {
		explicitWait = new WebDriverWait(driver, shortTimeout);
		explicitWait.until(ExpectedConditions.elementToBeClickable(getByXpath(getDynamicLocator(locator, params))));
	}

	/**
	 * 
	 * @param driver
	 * @param locator
	 */
	public void waitForElementInvisible(WebDriver driver, String locator) {
		explicitWait = new WebDriverWait(driver, shortTimeout);
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(getByXpath(locator)));
	}

	/**
	 * 
	 * @param driver
	 * @param locator
	 * @param params
	 */
	public void waitForElementInvisible(WebDriver driver, String locator, String... params) {
		explicitWait = new WebDriverWait(driver, shortTimeout);
		explicitWait
				.until(ExpectedConditions.invisibilityOfElementLocated(getByXpath(getDynamicLocator(locator, params))));
	}

	/**
	 * 
	 * @param locator
	 * @param params
	 * @return
	 */
	public String getDynamicLocator(String locator, String... params) {
		return String.format(locator, (Object) params);
	}

	/**
	 * 
	 * @param second
	 */
	public void sleepInSecond(int second) {
		try {
			Thread.sleep(second * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param driver
	 * @return
	 */
	public Set<Cookie> getAllCookies(WebDriver driver) {
		return driver.manage().getCookies();
	}

	public void setAllCookies(WebDriver driver, Set<Cookie> Allcookies) {
		for (Cookie cookie : Allcookies) {
			driver.manage().addCookie(cookie);
		}
	}

	/**
	 * 
	 * @param driver
	 * @param textboxID
	 * @param value
	 */
	// Pattern Object
	public void enterToTextboxByID(WebDriver driver, String textboxID, String value) {
		waitForElementVisible(driver, BasePageUI.DYNAMIC_TEXTBOX_BY_ID, textboxID);
		sendkeyToElement(driver, BasePageUI.DYNAMIC_TEXTBOX_BY_ID, value, textboxID);
	}

	/**
	 * 
	 * @param driver
	 * @param buttonText
	 */
	public void clickToButtonByText(WebDriver driver, String buttonText) {
		waitForElementClickable(driver, BasePageUI.DYNAMIC_BUTTON_BY_TEXT, buttonText);
		clickToElement(driver, BasePageUI.DYNAMIC_BUTTON_BY_TEXT, buttonText);
	}

	/**
	 * 
	 * @param driver
	 * @param radioID
	 */
	public void clickToRadioButtonByID(WebDriver driver, String radioID) {
		waitForElementClickable(driver, BasePageUI.DYNAMIC_RADIO_BY_ID, radioID);
		clickToElement(driver, BasePageUI.DYNAMIC_RADIO_BY_ID, radioID);
	}

	/**
	 * 
	 * @param driver
	 * @param dropdownName
	 * @param itemText
	 */
	public void selectDropdownByName(WebDriver driver, String dropdownName, String itemText) {
		selectDropdownByText(driver, BasePageUI.DYNAMIC_DROPDOWN_BY_NAME, itemText, dropdownName);
	}

	private int longTimeout = GlobalConstants.LONG_TIMEOUT;
	private int shortTimeout = GlobalConstants.SHORT_TIMEOUT;
	private Alert alert;
	private Select select;
	private Actions actions;
	private WebDriverWait explicitWait;
	private JavascriptExecutor jsExecutor;
}