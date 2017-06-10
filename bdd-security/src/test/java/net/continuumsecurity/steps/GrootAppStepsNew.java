package net.continuumsecurity.steps;


import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import edu.umass.cs.benchlab.har.HarEntry;
import net.continuumsecurity.Config;
import net.continuumsecurity.UserPassCredentials;
import net.continuumsecurity.behaviour.ILogin;
import net.continuumsecurity.behaviour.ILogout;
import net.continuumsecurity.proxy.LoggingProxy;
import net.continuumsecurity.proxy.ZAProxyScanner;
import net.continuumsecurity.web.Application;
import org.apache.log4j.Logger;
import org.hamcrest.beans.HasProperty;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.is;


public class GrootAppStepsNew {
    Logger log = Logger.getLogger(GrootAppStepsNew.class);
    String actionName;
    String allowedUrl;
    String notAllowedUrl;
    Application app;
    LoggingProxy proxy;

    public GrootAppStepsNew() {
    }

    @After
    public void logoutFromGrootNewApp(){
        ((ILogout) app).logout();
    }

    @Given("^a new browser or client instance for grootAppNew$")
    public void createGrootAppForAnyClient() {
        createGrootApp();
    }

    public void createGrootApp() {
        app = Config.getInstance().createApp();
        app.enableDefaultClient();
        //simulates logout (erases content)
        //app.getAuthTokenManager().deleteAuthTokens();
        World.getInstance().setCredentials(new UserPassCredentials("", ""));
    }

    @Given("^configured instance for logging proxy$")
    public void loggingClient() {
        app.enableHttpLoggingClient();
    }

    @When("^clear prx logs$")
    public void clearProxyForGrootApp() {
        getProxyGrootApp().clear();
    }


    public LoggingProxy getProxyGrootApp() {
        if (proxy == null)
            proxy = new ZAProxyScanner(Config.getInstance().getProxyHost(), Config.getInstance().getProxyPort(), Config.getInstance().getProxyApi());
        return proxy;
    }

    @Given("^the login page for grootAppNew$")
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

    @When("^mister login$")
    public void loginWithCredentialsFromRoleForGrootApp() {
        assert World.getInstance().getCredentials() != null;
        ((ILogin) app).login(World.getInstance().getCredentials());
    }

    @When("he goes to the allowed url (.*)$")
    public void goToAllowedUrl(String allowedUrl) {
        this.allowedUrl = allowedUrl;
    }

    @When("he goes to the not allowed url (.*)$")
    public void goToNotAllowedUrl(String notAllowedUrl) {
        this.notAllowedUrl = notAllowedUrl;
    }

    @When("he executes (.*)$")
    public void executeAction(String action) {
        this.actionName = action;
    }

//    @When("HTTP (.*) request is sent to api_url (.*)$")
//    public void findRequestWitUrlAndMethod(String method, String api_url) throws NoSuchMethodException  {
//        System.out.println("Method: " + method);
//        System.out.println("Api url: " + api_url);
//        UserPassCredentials credentials = World.getInstance().getUserPassCredentials();
//        try{
//            @     app.getClass().getMethod(actionName, String.class).invoke(app, allowedUrl);
//        }catch(NoSuchMethodException nsm){
//            throw nsm;
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//        if (World.getInstance().getMethodProxyMap().get(actionName) != null) {
//            log.info("The method: "
//                    + actionName
//                    + " has already been added to the map, using the existing HTTP logs");
//            return;
//        }
//        World.getInstance().getMethodProxyMap().put(actionName, getProxyGrootApp().getHistory());
//        List<HarEntry> harEntries =  World.getInstance().getMethodProxyMap().get(actionName);
//        System.out.println("Har entries for action " + actionName + " " + harEntries.size());
//        List<HarEntry> apiUrls = getProxyGrootApp().findInRequestHistory(api_url);
//        for (HarEntry he: apiUrls) {
//            System.out.println("Har entry and method:" + he.getRequest().getMethod());
//            if (method.equals(he.getRequest().getMethod())){
//                World.getInstance().setCurrentHar(he);
//                break;
//            }
//        }
//    }

    @Then("the status code in response should be 200")
    public void verifyOkStatusCode() throws NoSuchMethodException {
        UserPassCredentials credentials = World.getInstance().getUserPassCredentials();
        try{
            app.getClass().getMethod(actionName, String.class).invoke(app, allowedUrl);
        }catch(NoSuchMethodException nsm){
            throw nsm;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (World.getInstance().getMethodProxyMap().get(actionName) != null) {
            log.info("The method: "
                    + actionName
                    + " has already been added to the map, using the existing HTTP logs");
            return;
        }
        World.getInstance().getMethodProxyMap().put(actionName, getProxyGrootApp().getHistory());
        List<HarEntry> harEntries =  World.getInstance().getMethodProxyMap().get(actionName);
        boolean everyResponseIsOk = true;
        HarEntry notOkEntry = null;
        for (HarEntry he: harEntries) {
            System.out.println(he.getRequest().getMethod() + " " + he.getRequest().getUrl());
            System.out.println(he.getResponse().getStatus());
            System.out.println("=========");
            if (he.getResponse().getStatus() != 200) {
                everyResponseIsOk = false;
                notOkEntry = he;
                break;
            }
        }

        if (!everyResponseIsOk) {
            System.out.println("HarEntry is: " + notOkEntry);
        }

        assertThat(everyResponseIsOk, is(true));


    }

    @Then("the status code response should be 403")
    public void verifyUnauthorizedStatusCode() throws NoSuchMethodException {
        UserPassCredentials credentials = World.getInstance().getUserPassCredentials();
        try{
            app.getClass().getMethod(actionName, String.class).invoke(app, notAllowedUrl);
        }catch(NoSuchMethodException nsm){
            throw nsm;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        assertThat(World.getInstance().getCurrentHar().getResponse().getStatus(), is(equalTo(403)));
    }
}
