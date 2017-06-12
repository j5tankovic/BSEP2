package net.continuumsecurity;


import net.continuumsecurity.behaviour.ILogin;
import net.continuumsecurity.behaviour.ILogout;
import net.continuumsecurity.behaviour.INavigable;
import net.continuumsecurity.web.WebApplication;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class GrootApplicationService extends WebApplication implements ILogin, ILogout, INavigable {

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

    public void viewCourses(String url){
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

    public void viewPeople(String url) {

        driver.get(getUrl(url));
        driver.findElement(By.id("people-list"));
    }

    public void editProfile(String url) {
        System.out.println("Url is:" + url);
        driver.get(getUrl(url));

        driver.findElement(By.id("profile-btn-edit")).click();

        driver.findElement(By.id("profile-input-name")).clear();
        driver.findElement(By.id("profile-input-name")).sendKeys("Edited Name");

        driver.findElement(By.id("profile-input-surname")).clear();
        driver.findElement(By.id("profile-input-surname")).sendKeys("Edited surname");

        driver.findElement(By.id("profile-btn-editSave")).click();
    }

    public void addAnnouncement(String url) {
        driver.get(getUrl(url));
        findAndWaitForElement(By.id("announcements-btn-newAnnouncement"));

        driver.findElement(By.id("announcements-btn-newAnnouncement")).click();

        driver.findElement(By.id("announcements-input-title")).clear();
        driver.findElement(By.id("announcements-input-title")).sendKeys("New Announcement");

        driver.findElement(By.id("announcements-textarea-content")).clear();
        driver.findElement(By.id("announcements-textarea-content")).sendKeys("Content for new announcement");

        driver.findElement(By.id("announcements-btn-save")).click();
    }

    public void editAnnouncement(String url) {
        driver.get(getUrl(url));
        driver.findElement(By.id("announcement-btn-edit")).click();

        driver.findElement(By.id("announcement-input-title")).clear();
        driver.findElement(By.id("announcement-input-title")).sendKeys("Announcement edited");

        driver.findElement(By.id("announcement-textarea-content")).clear();
        driver.findElement(By.id("announcement-textarea-content")).sendKeys("Content for announcement edited");

        driver.findElement(By.id("announcement-btn-save")).click();
    }

    public void deleteAnnouncement(String url) {
        driver.get(getUrl(url));

        driver.findElement(By.id("announcement-btn-delete")).click();
    }

    public void addUser(String url) {
        driver.get(getUrl(url));
        driver.findElement(By.id("admin-btn-addUser")).click();

        driver.findElement(By.id("admin-input-user-name")).clear();
        driver.findElement(By.id("admin-input-user-name")).sendKeys("User Name");

        driver.findElement(By.id("admin-input-user-surname")).clear();
        driver.findElement(By.id("admin-input-user-surname")).sendKeys("User Surname");

        driver.findElement(By.id("admin-input-user-username")).clear();
        driver.findElement(By.id("admin-input-user-username")).sendKeys("User username");

        driver.findElement(By.id("admin-input-user-password")).clear();
        driver.findElement(By.id("admin-input-user-password")).sendKeys("User password");

        Select select = new Select(driver.findElement(By.id("admin-select-user-role")));
        select.selectByVisibleText("STUDENT");


        driver.findElement(By.id("admin-btn-saveUser")).click();

    }

    public void deleteUser(String url) {
        driver.get(getUrl(url));
        List<WebElement> buttons = driver.findElements(By.id("admin-btn-deleteUser"));
        buttons.get(buttons.size() - 1).click();

    }


    public void addCourse(String url) {
        driver.get(getUrl(url));
        driver.findElement(By.id("admin-btn-addCourse")).click();

        driver.findElement(By.id("admin-input-course-name")).clear();
        driver.findElement(By.id("admin-input-course-name")).sendKeys("UPP");

        driver.findElement(By.id("admin-btn-saveCourse")).click();
    }

    public void deleteCourse(String url) {
        driver.get(getUrl(url));
        List<WebElement> buttons = driver.findElements(By.id("admin-btn-deleteCourse"));
        buttons.get(buttons.size() - 1).click();

    }

    public void viewAdminPanel(String url) {
        driver.get(getUrl(url));
    }

    @Override
    public void logout() {
        driver.findElement(By.id("main-btn-logout")).click();
    }


    private String getUrl(String urlAddition) {
        return Config.getInstance().getBaseUrl(TestingOption.SERVICE) + HASH_URL_PART + urlAddition;
    }
}
