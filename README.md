How to run the tests and get test results:
* Clone repo
* Install Gradle `https://gradle.org/install/` 
* Navigate to downloaded folder and Run tests: `./gradlew clean test`
* Generate report and open it:
    * `./gradlew allureReport` - generate Allure report
    * `./gradlew allureServe` - will open report in default browser OR it could be opened from `build/reports/allure-report/allureReport/index.html`