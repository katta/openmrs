package org.openmrs.steps;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.openqa.selenium.lift.Finders.button;
import static org.openqa.selenium.lift.Finders.div;
import static org.openqa.selenium.lift.Finders.radioButton;
import static org.openqa.selenium.lift.Finders.textbox;
import static org.openqa.selenium.lift.Matchers.attribute;
import static org.openqa.selenium.lift.Matchers.text;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.openmrs.Steps;
import org.openqa.selenium.WebDriver;

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
		type(url,
				into(textbox().with(
						attribute("name", equalTo("database_connection")))));
	}

	@When("I type $database as new database name")
	public void enterDbName(String database) {
		type(database,
				into(textbox().with(
						attribute("name",
								equalTo("openmrs_current_database_name")))));
	}

    @When("I type $username as database username")
    public void enterDatabaseUserName(String username) {
		type(username,
				into(textbox().with(
						attribute("name",
								equalTo("create_database_username")))));
    }

	@When("I type $password as database password")
	public void enterDatabasePassword(String password) {
		type(password,
				into(passwordtextbox().with(
						attribute("name",
								equalTo("create_database_password")))));

	}

    @When("I click on $submit button on step 1")
    public void clickOnStep1ContinueButton(String submit) {
    	clickOnContinueButton(submit);
    }

    @Then("take me to step 2 of the installation page with the heading $step")
    public void verifyPage2(String step) {
    	verifyStep(step);
    }

    @Given("I am on step 2 of the installation page with the heading $step")
    public void onInstallationStep2(String step) {
    	verifyStep(step);
    }

    @When("I type $username as database username in step 2")
    public void enterUserName(String username) {
		type(username,
				into(textbox().with(
						attribute("name",
								equalTo("current_database_username")))));
    }

    @When("I type $password as database password in step 2")
    public void enterPassword(String password) {
		type(password,
				into(passwordtextbox().with(
						attribute("name",
								equalTo("current_database_password")))));
    }

    @When("I click on $submit button on step 2")
    public void clickOnStep2ContinueButton(String submit) {
    	clickOnContinueButton(submit);
    }

	@Then("take me to step 3 of the installation page with the heading $step")
	public void verifyPage3(String step) {
		verifyStep(step);
	}

    @Given("I am on step 3 of the installation page with the heading $step")
    public void onInstallationStep3(String step) {
    	verifyStep(step);
    }

    @When("I click on $submit button on step 3")
    public void clickOnStep3ContinueButton(String submit) {
    	clickOnContinueButton(submit);
    }

    @Then("take me to step 4 of the installation page with the heading $step")
    public void verifyPage4(String step) {
    	verifyStep(step);
    }

    @Given("I am on step 4 of the installation page with the heading $step")
	public void onInstallationStep4(String step) {
		verifyStep(step);
	}

	@When("I type $password as openmrs password in step 4")
	public void enterOpenmrsPassword(String password) {
		type(password,
				into(passwordtextbox().with(
						attribute("name", equalTo("new_admin_password")))));
	}

    @When("I type $password as confirm openmrs password in step 4")
    public void confirmOpenmrsPassword(String password) {
		type(password,
				into(passwordtextbox().with(
						attribute("name", equalTo("new_admin_password_confirm")))));
    }

    @When("I click on $submit button on step 4")
    public void clickOnStep4ContinueButton(String submit) {
    	clickOnContinueButton(submit);
    }

    @Then("take me to step 5 of the installation page with the heading $step")
    public void verifyPage5(String step) {
    	verifyStep(step);
    }

	@Given("I am on step 5 of the installation page with the heading $step")
	public void onInstallationStep5(String step) {
		verifyStep(step);
	}

    @When("I click on $submit button on step 5")
    public void clickOnStep5ContinueButton(String submit) {
    	clickOnContinueButton(submit);
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

    @When("I click on $submite button on review page")
    public void clickOnFinishButton(String submit) {
    	clickOnContinueButton(submit);
        waitFor(button().with(attribute("value", equalTo("Log In"))),1800000);
    }

    @Then("take me to login Page")
    public void verifyLoginPage(String title) {
        assertPresenceOf(div().with(
                text(containsString("OpenMRS Installation Wizard"))));
    }
}
