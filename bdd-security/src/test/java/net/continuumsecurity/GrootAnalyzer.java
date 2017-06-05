package net.continuumsecurity;


import net.continuumsecurity.behaviour.ILogin;
import net.continuumsecurity.behaviour.ILogout;
import net.continuumsecurity.behaviour.INavigable;
import net.continuumsecurity.web.WebApplication;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

public class GrootAnalyzer extends WebApplication implements ILogin, ILogout, INavigable {

    //since GrootEdu app is using angular ui-router, this part is needed
    private final String HASH_URL_PART = "#!";

    @Override
    public void login(Credentials credentials) {
        System.out.println(credentials.get("username"));
        System.out.println(credentials.get("password"));
        UserPassCredentials creds = new UserPassCredentials(credentials);
        driver.findElement(By.id("login-input-username")).clear();
        driver.findElement(By.id("login-input-username")).sendKeys(creds.getUsername());

        driver.findElement(By.id("login-input-password")).clear();
        driver.findElement(By.id("login-input-password")).sendKeys(creds.getPassword());

        driver.findElement(By.id("login-btn-login")).click();
    }

    @Override
    public void openLoginPage() {
        driver.get(Config.getInstance().getBaseUrl() + HASH_URL_PART + "/login");
        findAndWaitForElement(By.id("login-input-username"));
    }

    @Override
    public boolean isLoggedIn() {
//        driver.get(Config.getInstance().getBaseUrl() + "#!/home");
//        try{
//            WebElement element = driver.findElement(By.id("main-btn-logout"));
//            return true;
//        } catch(NoSuchElementException e){
//            return false;
//        }
        driver.get(Config.getInstance().getBaseUrl() + "#!/home");
        return driver.getPageSource().contains("GrootEdu");
    }

    public void viewCourses(String url){
        driver.get(Config.getInstance().getBaseUrl() + HASH_URL_PART + url);
        driver.findElement(By.id("home-list-courses"));
    }

    public void viewCourse(String url) {
        driver.get(Config.getInstance().getBaseUrl() + HASH_URL_PART + url);
    }

    public void viewAnnouncements(String url) {
        driver.get(Config.getInstance().getBaseUrl() + HASH_URL_PART + url);
    }

    public void viewAnnouncement(String url) {
        driver.get(Config.getInstance().getBaseUrl() + HASH_URL_PART + url);
    }

    public void viewPeople(String url) {

        driver.get(Config.getInstance().getBaseUrl() + HASH_URL_PART + url);
        driver.findElement(By.id("people-list"));
    }

    public void editProfile(String url) {
        driver.get(Config.getInstance().getBaseUrl() + HASH_URL_PART + url);
        driver.findElement(By.id("profile-btn-edit"));
    }

    public void addAnnouncement(String url) {
        driver.get(Config.getInstance().getBaseUrl() + HASH_URL_PART+ url);
        driver.findElement(By.id("announcements-btn-newAnnouncement"));
    }

    public void editAnnouncement(String url) {
        driver.get(Config.getInstance().getBaseUrl() + HASH_URL_PART + url);
        driver.findElement(By.id("announcement-btn-edit"));
    }

    public void deleteAnnouncement(String url) {
        driver.get(Config.getInstance().getBaseUrl() + HASH_URL_PART + url);
        driver.findElement(By.id("announcement-btn-delete"));
    }

    public void addUser(String url) {
        driver.get(Config.getInstance().getBaseUrl() + HASH_URL_PART + url);
        driver.findElement(By.id("admin-btn-addUser"));
    }


    public void addCourse(String url) {
        driver.get(Config.getInstance().getBaseUrl() + HASH_URL_PART + url);
        driver.findElement(By.id("admin-btn-addCourse"));
    }

    public void viewAdminPanel(String url) {
        driver.get(Config.getInstance().getBaseUrl() + HASH_URL_PART + url);
    }

    @Override
    public void logout() {
        driver.findElement(By.id("main-btn-logout")).click();
    }
}
