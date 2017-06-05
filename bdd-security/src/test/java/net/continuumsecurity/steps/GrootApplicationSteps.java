package net.continuumsecurity.steps;

import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.continuumsecurity.Config;
import net.continuumsecurity.UserPassCredentials;
import net.continuumsecurity.behaviour.ILogin;
import net.continuumsecurity.behaviour.ILogout;
import net.continuumsecurity.web.Application;
import net.continuumsecurity.web.WebApplication;
import org.apache.log4j.Logger;


import java.lang.reflect.InvocationTargetException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.fail;

public class GrootApplicationSteps {
    Logger log = Logger.getLogger(GrootApplicationSteps.class);
    String actionName;
    Application app;

    public GrootApplicationSteps() {

    }

    @After
    public void logoutFromApp(){
        ((ILogout) app).logout();
    }

    @Given("^a new browser or client instance for grootApp$")
    public void createGrootAppForAnyClient() {
        createGrootApp();
    }

    public void createGrootApp() {
        app = Config.getInstance().createApp();
        app.enableDefaultClient();
        World.getInstance().setCredentials(new UserPassCredentials("", ""));
    }

    @Given("the login page for grootApp$")
    public void openGrootLoginPage() {
        ((ILogin) app).openLoginPage();
    }

    @Given("the role (.*)$")
    public void setUsernameAndPasswordAccordingToRole(String role) {
        World.getInstance().getUserPassCredentials().setUsername(role);
        World.getInstance().getUserPassCredentials().setPassword("password");
    }

    @When("the user with the role logs in")
    public void loginWithCredentialsFromRole() {
        assert World.getInstance().getCredentials() != null;
        ((ILogin) app).login(World.getInstance().getCredentials());
    }

    @When("they execute: (.*)")
    public void setActionName(String action) {
        actionName = action;
    }

    @Then("the url: (.*) should be visible to user")
    public void verifyThatUrlIsAccessible(String allowed_url) throws NoSuchMethodException {
        UserPassCredentials credentials = World.getInstance().getUserPassCredentials();
        try{
            app.getClass().getMethod(actionName, String.class).invoke(app, allowed_url);
        }catch(NoSuchMethodException nsm){
            throw nsm;
        }catch (InvocationTargetException e) {
            fail("Exception e is" + e.getCause().toString() + "User with credentials: " + credentials.getUsername() + " "
                    + credentials.getPassword()
                    + " could not access the method: " + actionName + "()");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        String currentUrl = ((WebApplication) app).getBrowser().getWebDriver().getCurrentUrl();
        assertThat("User with the role could access allowed url " + allowed_url + " and whole url is" + currentUrl, currentUrl, containsString(allowed_url));
    }

    @Then("the url: (.*) should not be visible to user")
    public void verifyThatUrlIsNotAccessible(String not_allowed_url) throws NoSuchMethodException {
        UserPassCredentials credentials = World.getInstance().getUserPassCredentials();
        try{
            app.getClass().getMethod(actionName, String.class).invoke(app, not_allowed_url);
        }catch(NoSuchMethodException nsm){
            throw nsm;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        String currentUrl = ((WebApplication) app).getBrowser().getWebDriver().getCurrentUrl();
        assertThat("User with the role could not access url " + not_allowed_url, currentUrl, not(containsString(not_allowed_url)));
    }


}
