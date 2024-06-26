# Admin Automation

## Overview
Selenium webdriver based Admin Portal Automation covers CRUD(create, read, update and delete) operation performed via UI with Chrome driver

## Build
1. Build jar `mvn clean install`
2. Place jar in one folder along with src/main/resources files and folder
3. Run jar with vm args- ``` -Dpath=https://admin.dev.mosip.net/ -DKeyclockURL=https://iam.dev.mosip.net -Denv.user=api-internal.dev  -Denv.endpoint=https://api-internal.dev.mosip.net jar nameofAdminJar.jar```

## Configurations
1. Update below keys from `Config.properties`
* langcode:eng -- Admin login page language selection description placed in `TestData.json`
* bulkwait:10000 -- Bulk upload wait

2. Update below keys from `TestData.json`
* setExcludedGroups:"" -- To run all the scenario mentioned below
* setExcludedGroups:"BL,CT" -- To exclude testcases execution based on below tags

3. Chrome driver place under working directory inside folder name chromedriver

## Below tags with scenarios
* blocklistedwordsCRUD: BL
* bulkUploadCRUD: BU
* centerCRUD: CTR
* centerTypeCRUD: CT
* deviceSpecCRUD: DS
* deviceCRUD: D
* deviceTypesCRUD: DT
* documentCategoriesCRUD: DOC
* documentTypesCRUD: DOCT
* dynamicFieldCRUD: DF
* holidaysCRUD: H
* machineSpecCRUD: MS
* machineCRUD: M
* machineTypesCRUD: MT
* templateCRUD: T

## Headless Mode
*Update below key from `TestData.json`
* hedless: "no" --To run in normal mode.
* hedless : "yes" --To run in headless mode

## Execution result and logs
1. Verify the failure in the logs file `\logs\AutomationLogs.log`
2. Execution results present under test-output folder file `emailable-report.html`
3. Extent results are present  `\admintest\Reports`

#Usercreation
1.In config folder `\config\kernal.property`
2.on line no 27,36,38 chnage we can change the user(keep same at all three place)
3.


## License
This project is licensed under the terms of [Mozilla Public License 2.0](../LICENSE).

