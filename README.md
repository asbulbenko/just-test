#How to run the tests and get test results:

Pre-condition
* You must have gradle installed in your machine, `https://gradle.org/install/`

Steps
* Clone repo
* Open your Terminal/Command prompt 
* Navigate to the project folder 
* Execute following command to run Rest API tests: `./gradlew clean test --tests RestTests`
* Execute following command to run Web tests: `./gradlew clean test --tests WebTests`
* To run all Rest & Web tests execute: `./gradlew clean test`
* Generate report and open it:
    * `./gradlew allureReport` - generate Allure report
    * `./gradlew allureServe` - will open report in default browser OR it could be opened from `build/reports/allure-report/allureReport/index.html`