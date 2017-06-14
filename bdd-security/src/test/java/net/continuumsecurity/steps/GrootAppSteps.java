package net.continuumsecurity.steps;


import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import edu.umass.cs.benchlab.har.HarEntry;
import net.continuumsecurity.Config;
import net.continuumsecurity.TestingOption;
import net.continuumsecurity.UserPassCredentials;
import net.continuumsecurity.behaviour.ILogin;
import net.continuumsecurity.behaviour.ILogout;
import net.continuumsecurity.proxy.LoggingProxy;
import net.continuumsecurity.proxy.ZAProxyScanner;
import net.continuumsecurity.web.Application;
import org.apache.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


public class GrootAppSteps {
    Logger log = Logger.getLogger(GrootAppSteps.class);
    String actionName;
    String allowedUrl;
    String notAllowedUrl;
    Application app;
    LoggingProxy proxy;

    public GrootAppSteps() {
    }

    @After
    public void logoutFromGrootNewApp(){
        ((ILogout) app).logout();
    }

    @Given("^a new intercepting proxy browser$")
    public void initializeLoggingProxyBrowserForApp() {
        app = Config.getInstance().createApp(TestingOption.SERVICE);
        app.enableHttpLoggingClient();
        World.getInstance().setCredentials(new UserPassCredentials("", ""));
    }

    @Given("^a new browser$")
    public void initiBrowserForApp() {
        app = Config.getInstance().createApp(TestingOption.CLIENT);
        app.enableDefaultClient();
        World.getInstance().setCredentials(new UserPassCredentials("", ""));
    }


    @Given("^cleared proxy logs$")
    public void clearProxyLogs() {
        getProxy().clear();
    }


    private LoggingProxy getProxy() {
        if (proxy == null)
            proxy = new ZAProxyScanner(Config.getInstance().getProxyHost(), Config.getInstance().getProxyPort(), Config.getInstance().getProxyApi());
        return proxy;
    }

    @Given("^the login page$")
    public void openGrootAppLoginPage() {
        ((ILogin) app).openLoginPage();
    }

    @Given("^the user with the role (.*)$")
    public void setUsernameAndPasswordAccordingToRole(String role) {
        String username = "";
        switch (role) {
            case "TEACHER":
                username = "teacher";
                break;
            case "STUDENT":
                username = "student";
                break;
            case "ADMIN":
                username = "admin";
                break;
        }

        World.getInstance().getUserPassCredentials().setUsername(username);
        World.getInstance().getUserPassCredentials().setPassword("password");
    }

    @When("^user logs in$")
    public void loginWithCredentialsFromRoleForGrootApp() {
        assert World.getInstance().getCredentials() != null;
        ((ILogin) app).login(World.getInstance().getCredentials());
    }

    @When("user navigates to the allowed url (.*)$")
    public void setAllowedUrl(String allowedUrl) {
        this.allowedUrl = allowedUrl;
    }

    @When("user navigates to the not allowed url (.*)$")
    public void setNotAllowedUrl(String notAllowedUrl) {
        this.notAllowedUrl = notAllowedUrl;
    }

    @When("^he performs action (.*)$")
    public void setActionName(String action) {
        this.actionName = action;
    }


    @Then("the status code in response should be 200")
    public void verifyOkStatusCode() throws NoSuchMethodException, InterruptedException {
        try{
            app.getClass().getMethod(actionName, String.class).invoke(app, allowedUrl);
        }catch(NoSuchMethodException nsm){
            throw nsm;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Thread.sleep(2000);
        World.getInstance().getMethodProxyMap().put(actionName, getProxy().getHistory());
        List<HarEntry> harEntries =  World.getInstance().getMethodProxyMap().get(actionName);
        boolean everyResponseIsOk = true;
        List<HarEntry> filteredEntries = excludeEntriesForBdicFiles(harEntries);
        for (HarEntry he: filteredEntries) {
            if (he.getResponse().getStatus() != 200) {
                everyResponseIsOk = false;
                break;
            }
        }

        assertThat(everyResponseIsOk, is(true));
    }

    @Then("the status code in response should be 403")
    public void verifyUnauthorizedStatusCode() throws NoSuchMethodException, InterruptedException {
        try{
            app.getClass().getMethod(actionName, String.class).invoke(app, notAllowedUrl);
        }catch(NoSuchMethodException nsm){
            throw nsm;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Thread.sleep(2000);
        World.getInstance().getMethodProxyMap().put(actionName, getProxy().getHistory());
        List<HarEntry> harEntries =  World.getInstance().getMethodProxyMap().get(actionName);
        boolean responseWith403Exists = false;
        List<HarEntry> filteredEntries = excludeEntriesForBdicFiles(harEntries);
        for (HarEntry he: filteredEntries) {
            if (he.getResponse().getStatus() == 403) {
                responseWith403Exists = true;
                break;
            }
        }

        assertThat(responseWith403Exists, is(true));
    }

    @Then("the unauthorized view should appear")
    public void verifyUnauthorizedViewIsPresent() throws NoSuchMethodException{
        try{
            app.getClass().getMethod(actionName, String.class).invoke(app, notAllowedUrl);
        }catch(NoSuchMethodException nsm){
            throw nsm;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private List<HarEntry> excludeEntriesForBdicFiles(List<HarEntry> harEntries) {
        List<HarEntry> filteredEntries = new ArrayList<>();
        boolean isRedirectorUrl;
        boolean is302ResponseStatus;
        for (HarEntry he: harEntries) {
            isRedirectorUrl = he.getRequest().getUrl().contains("https://redirector.gvt1.com");
            is302ResponseStatus = he.getResponse().getStatus() == 302;
            if (!isRedirectorUrl && !is302ResponseStatus) {
                filteredEntries.add(he);
            }
        }
        return filteredEntries;
    }
}
