package net.continuumsecurity;

import net.continuumsecurity.behaviour.ILogin;
import net.continuumsecurity.behaviour.ILogout;
import net.continuumsecurity.behaviour.INavigable;
import net.continuumsecurity.web.WebApplication;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class GrootApplicationClient extends WebApplication implements ILogin, ILogout, INavigable {

    private final String HASH_URL_PART = "#!";

    @Override
    public void login(Credentials credentials) {
        UserPassCredentials creds = new UserPassCredentials(credentials);
        driver.findElement(By.id("login-input-username")).clear();
        driver.findElement(By.id("login-input-username")).sendKeys(creds.getUsername());

        driver.findElement(By.id("login-input-password")).clear();
        driver.findElement(By.id("login-input-password")).sendKeys(creds.getPassword());

        driver.findElement(By.id("login-btn-login")).click();
    }

    @Override
    public void openLoginPage() {
        driver.get(getUrl("/login"));
        findAndWaitForElement(By.id("login-input-username"));
    }

    @Override
    public boolean isLoggedIn() {
        driver.get(getUrl("/home"));
        return driver.getPageSource().contains("GrootEdu");
    }

    public void viewCourses(String url) {
        driver.get(getUrl(url));
        driver.findElement(By.id("home-list-courses"));
    }

    public void viewCourse(String url) {
        driver.get(getUrl(url));
    }

    public void viewAnnouncements(String url) {
        driver.get(getUrl(url));
    }

    public void viewAnnouncement(String url) {
        driver.get(getUrl(url));
    }

    public WebElement viewPeople(String url) {
        driver.get(getUrl(url));
        return driver.findElement(By.id("people-list"));
    }

    public boolean editProfile(String url) {
        driver.get(getUrl(url));
        return checkForElementPresence("profile-btn-edit");
    }


    public boolean addAnnouncement(String url) {
        driver.get(getUrl(url));
        return checkForElementPresence("announcements-btn-newAnnouncement");
    }


    public boolean editAnnouncement(String url) {
        driver.get(getUrl(url));
        return checkForElementPresence("announcement-btn-edit");
    }

    public boolean deleteAnnouncement(String url) {
        driver.get(getUrl(url));
        return checkForElementPresence("announcement-btn-delete");
    }

    public boolean addUser(String url) {
        driver.get(getUrl(url));
        return checkForElementPresence("admin-btn-addUser");
    }

    public boolean deleteUser(String url) {
        driver.get(getUrl(url));
        return checkForElementPresence("admin-btn-deleteUser");
    }


    public boolean addCourse(String url) {
        driver.get(getUrl(url));
        return checkForElementPresence("admin-btn-addCourse");
    }

    public boolean deleteCourse(String url) {
        driver.get(getUrl(url));
        return checkForElementPresence("admin-btn-deleteCourse");
    }

    public void viewAdminPanel(String url) {
        driver.get(getUrl(url));
    }

    @Override
    public void logout() {
        driver.findElement(By.id("main-btn-logout")).click();
    }

    private String getUrl(String urlAddition) {
        return Config.getInstance().getBaseUrl(TestingOption.CLIENT) + HASH_URL_PART + urlAddition;
    }

    private boolean checkForElementPresence(String elementId) {
        boolean elementIsPresent;
        try {
            driver.findElement(By.id(elementId));
            elementIsPresent = true;
        } catch (NoSuchElementException e){
            elementIsPresent = false;
        }
        return elementIsPresent;
    }
}