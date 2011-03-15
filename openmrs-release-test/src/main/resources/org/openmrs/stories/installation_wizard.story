Given I am on the OpenMRS Installation Wizard at http://localhost:8080/openmrs-release-test/initialsetup
When I select the Advanced option
And I click on Continue button
Then take me to step 1 of the installation page with the heading Step 1 of 5

Given I am on step 1 of the installation page with the heading Step 1 of 5
When I type jdbc:mysql:mxj://localhost:3316/openmrs_memory?autoReconnect=true&sessionVariables=storage_engine=InnoDB&useUnicode=true&characterEncoding=UTF-8&server.initialize-user=true&createDatabaseIfNotExist=true&server.basedir=database&server.datadir=database/data&server.collation-server=utf8_general_ci&server.character-set-server=utf8 as Database connection url
And I type openmrs_memory as new database name
And I type openmrs as database username
And I type test as database password
And I click on Continue button on step 1
Then take me to step 2 of the installation page with the heading Step 2 of 5

Given I am on step 2 of the installation page with the heading Step 2 of 5
When I type openmrs as database username in step 2
And I type test as database password in step 2
And I click on Continue button on step 2
Then take me to step 3 of the installation page with the heading Step 3 of 5


Given I am on step 3 of the installation page with the heading Step 3 of 5
When I click on Continue button on step 3
Then take me to installation step 4 with heading Step 4 of 5

Given I am on step 4 of the installation page with the heading Step 4 of 5
When I type Admin123 as openmrs password in step 4
And I type Admin123 as confirm openmrs password in step 4
And I click on Continue button on step 4
Then take me to step 5 of the installation page with the heading Step 5 of 5

Given I am on step 5 of the installation page with the heading Step 5 of 5
When I click on Continue button on step 5
Then take me to Review Page

Given I am on Review page
When I click on Finish button on review page
Then take me to login Page