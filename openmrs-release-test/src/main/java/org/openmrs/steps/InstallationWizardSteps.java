package org.openmrs.steps;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.openmrs.Steps;
import org.openqa.selenium.WebDriver;
import static org.hamcrest.Matchers.equalTo;

import static org.hamcrest.Matchers.containsString;
import static org.openqa.selenium.lift.Finders.*;
import static org.openqa.selenium.lift.Matchers.attribute;
import static org.openqa.selenium.lift.Matchers.text;

public class InstallationWizardSteps extends Steps {
    public InstallationWizardSteps(WebDriver driver) {
        super(driver);
    }

    @Given("I am on the $title at $url")
    public void beginInstallation(String title, String url) {
        goTo(url);
        assertPresenceOf(div().with(text(containsString(title))));
    }

	@When("I select the $option option")
	public void selectAdvancedOption(String option) {
		clickOn(radioButton().with(attribute("value", equalTo(option.toLowerCase()))));
	}

    @When("I click on $submit button")
    public void clickOnContinueButton(String submit) {
        clickOn(button().with(attribute("value", equalTo(submit))));
    }

    @Then("take me to step 1 of the installation page with the heading $step")
    public void verifyStep(String step) {
        assertPresenceOf(div().with(
                text(containsString(step))));
    }

    @Given("I am on step 1 of the installation page with the heading $step")
    public void onPage1(String step) {
    	verifyStep(step);
    }
    
    @When("I type $url as Database connection url")
    public void enterConnectionUrl(String url) {
        String xpath = "/html/body/form/div/table/tbody/tr[3]/td/input";
        clear(xpath);
        type(url, finderByXpath("/html/body/form/div/table/tbody/tr[3]/td/input"));

    }

    @When("I type $dbName as new database name")
    public void enterDbName(String dbName) {
        String xpath = "/html/body/form/div/table/tbody/tr[9]/td/input";
        clear(xpath);
        type(dbName, finderByXpath(xpath));

    }

    @When("I type $userName as database username")
    public void enterDatabaseUserName(String userName) {
        String xpath = "/html/body/form/div/table/tbody/tr[11]/td/input";
        clear(xpath);
        type(userName, finderByXpath(xpath));

    }

    @When("I type $password as database password")
    public void enterDatabasePassword(String password) {
        type(password, finderByXpath("/html/body/form/div/table/tbody/tr[12]/td/input"));

    }

    @When("I click on Continue button on step 1")
    public void clickOnStep1ContinueButton() {
        clickOn(finderByXpath("/html/body/form/div/table/tbody/tr[13]/td/input[3]"));
    }

    @Then("take me to step 2 of the installation page with the heading $step")
    public void verifyPage2(String step) {
    	verifyStep(step);
    }

    @Given("I am on step 2 of the installation page with the heading $step")
    public void onInstallationStep2(String step) {
    	verifyStep(step);
    }

    @When("I type $userName as database username in step 2")
    public void enterUserName(String userName) {
        type(userName, finderByXpath("/html/body/form/div/table/tbody/tr[8]/td/input"));
    }

    @When("I type $password as database password in step 2")
    public void enterPassword(String password) {
        type(password, finderByXpath("/html/body/form/div/table/tbody/tr[9]/td/input"));
    }

    @When("I click on Continue button on step 2")
    public void clickOnStep2ContinueButton() {
        clickOn(finderByXpath("/html/body/form/div/table/tbody/tr[13]/td/input[3]"));
    }

	@Then("take me to step 3 of the installation page with the heading $step")
	public void verifyPage3(String step) {
		verifyStep(step);
	}

    @Given("I am on step 3 of the installation page with the heading $step")
    public void onInstallationStep3(String step) {
    	verifyStep(step);
    }

    @When("I click on Continue button on step 3")
    public void clickOnStep3ContinueButton() {
        clickOn(finderByXpath("/html/body/form/div/table/tbody/tr[6]/td/input[3]"));
    }

    @Then("take me to installation step 4 with heading Step $current of $total")
    public void verifyPage4(int current, int total) {
        assertPresenceOf(div().with(
                text(containsString("Step " + current + " of " + total))));
    }

    @Given("I am on step 4 of the installation page with the heading $step")
	public void onInstallationStep4(String step) {
		verifyStep(step);
	}

    @When("I type $password as openmrs password in step 4")
    public void enterOpenmrsPassword(String password) {
        type(password, finderByXpath("/html/body/form/div/table/tbody/tr[4]/td[2]/input"));
    }

    @When("I type $password as confirm openmrs password in step 4")
    public void confirmOpenmrsPassword(String password) {
        type(password, finderByXpath("/html/body/form/div/table/tbody/tr[5]/td[2]/input"));
    }

    @When("I click on Continue button on step 4")
    public void clickOnStep4ContinueButton() {
        clickOn(finderByXpath("/html/body/form/div/table/tbody/tr[6]/td/input[3]"));
    }

    @Then("take me to step 5 of the installation page with the heading $step")
    public void verifyPage5(String step) {
    	verifyStep(step);
    }

	@Given("I am on step 5 of the installation page with the heading $step")
	public void onInstallationStep5(String step) {
		verifyStep(step);
	}

    @When("I click on Continue button on step 5")
    public void clickOnStep5ContinueButton() {
        clickOn(finderByXpath("/html/body/form/div/table/tbody/tr[7]/td/input[3]"));
    }

    @Then("take me to $title Page")
    public void verifyReviewPage(String title) {
        assertPresenceOf(div().with(
                text(containsString(title))));
    }

    @Given("I am on $title page")
    public void onReviewPage(String title) {
        assertPresenceOf(div().with(
                text(containsString(title))));
    }

    @When("I click on Finish button on review page")
    public void clickOnFinishButton() {
        clickOn(finderByXpath("/html/body/form/div/table/tbody/tr[19]/td/input[@id=\'start\']"));
        waitFor(button().with(attribute("value", equalTo("Log In"))),1800000);
    }

    @Then("take me to login Page")
    public void verifyLoginPage(String title) {
        assertPresenceOf(div().with(
                text(containsString("OpenMRS Installation Wizard"))));
    }
}
