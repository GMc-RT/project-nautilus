from requests.models import MissingSchema
from requests.sessions import InvalidSchema
from selenium import webdriver
from webdriver_manager.chrome import ChromeDriverManager
from webdriver_manager.firefox import GeckoDriverManager
import requests
import logging

#A quick and dirty implimentation in python

def clearLogs(aDriver):
    script = "console.clear();"
    aDriver.execute_script(script)

logging.basicConfig(
    format="%(asctime)s %(levelname)-8s %(message)s",
    level=logging.INFO,
    datefmt="%Y-%m-%d %H:%M:%S")

logging.info('Test Run Starting')

chromeDriver = webdriver.Chrome(ChromeDriverManager().install())
#firefoxDriver = webdriver.Firefox(executable_path=GeckoDriverManager().install())
#drivers = [chromeDriver, firefoxDriver]
drivers = [chromeDriver]
pagesToBrowse = ["https://www.w3.org/standards/badpage",
                "https://www.w3.org/standards/webofdevices/multimodal", 
                "https://www.w3.org/standards/webdesign/htmlcss"]

for driver in drivers:
    logging.info(f"Running test set for {driver.capabilities['browserName']}, version: {driver.capabilities['browserVersion']}")
    for page in pagesToBrowse:
        bugCount = 0
        clearLogs(driver) #clearing the logs between pages
        driver.get(page)   
        logging.info(f"Testing page : {page}")

        logging.info(f"TEST: The response code from the page (200, 302, 404, etc.)")
        try:
            request = requests.head(page) #Head request is much faster than a full get. If the request fails, it skips all other tests (Which would all have failed anyway) to save time.
            logging.info(f"The page returns : {request.status_code}")

            logging.info(f"TEST: There are no console errors on page loads (chrome minimum) ")    
            for log in driver.get_log('browser'): 
                print(log)            
                bugCount += 1
                logging.error(f"Found a console error on page load: {log['message']}")
                logging.error(f"Browser: {driver.capabilities['browserName']}, version: {driver.capabilities['browserVersion']}, Page: {page}, level: {log['level']}, source: {log['source']}, timestamp: {log['timestamp']}, message: {log['message']}")
            else:
                logging.info(f"No errors found in Console Log")

            logging.info(f"TEST3: All links on the page go to another live (non 4xx) page (no need to actually parse the linked page/image).") 
            allLinks = driver.find_elements_by_xpath("//a")
            logging.info(f"There are {len(allLinks)} links on this page") 
            for link in allLinks:
                href = link.get_attribute('href')
                try:
                    r = requests.head(href)
                    statusCode = r.status_code
                    if (int(statusCode) > 399):
                        logging.error(f"Error: The link '{link.text}' returns a status code of {statusCode}")
                        bugCount += 1
                except requests.ConnectionError:
                    logging.error(f"Error: The link '{link.text}' failed to connect.")
                    bugCount += 1
                except MissingSchema:
                    logging.error(f"Error: The link '{link.text}' Does not have an associated href")
                    bugCount += 1
                except InvalidSchema:
                    if "mailto:" in  link.text:
                        pass #its a mail link, not a bug. You could check for malformed email here
                    else:
                        logging.error(f"Error: The link '{link.text}' has an invalid href")
                        bugCount += 1

            if bugCount == 0:
                logging.info(f"Success! No bugs found this run, have a cookie.")
            else:
                logging.error(f"There were {bugCount} bugs found on this page. No cookie for you.")

        except requests.ConnectionError:
            logging.critical(f"Error: Failed to connect to page, all tests skipped")

    driver.quit()









    






