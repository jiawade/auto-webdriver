# chromewebdriver

automatically download  chromedriver according to current platform 
and download the chromedriver compatible with Google Chrome

## It is easy to use
###for example:
1.download and configure compatible with current google browser version
ChromeDriverConfigurator.configureAppropriateDriver();

2.download and configure last version of chromedriver
ChromeDriverConfigurator.configureLatestDriver();

3.download and configure specific version of chrome driver, note: the version must be greater than 70.0
ChromeDriverConfigurator.configureSpecificDriver(75.0f);
