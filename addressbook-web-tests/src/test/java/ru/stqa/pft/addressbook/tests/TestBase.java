package ru.stqa.pft.addressbook.tests;

import org.openqa.selenium.remote.BrowserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import ru.stqa.pft.addressbook.appmanager.ApplicationManager;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.lang.reflect.Method;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


public class TestBase {
Logger logger = LoggerFactory.getLogger(TestBase.class);

  protected static final ApplicationManager app =
          new ApplicationManager(System.getProperty("browser", BrowserType.CHROME ));

  public TestBase() {
    }

  @BeforeSuite
  public void setUp() throws Exception {
    app.init();
  }

  @AfterSuite
  public void tearDown() {
    app.stop();
  }

  @BeforeMethod
  public void logTestStart(Method m){
    logger.info("Start test: " + m.getName());
  }


 @AfterMethod
  public void logTestStop(Method m){
    logger.info("Stop test: " + m.getName());
  }

  public void verifyGroupListUI(){
    if (Boolean.getBoolean("verifyUI")){
      Groups dbGroups = app.db().groups();
      Groups uiGroups = app.group().all();
      assertThat(uiGroups, equalTo(dbGroups.stream().map((g)->
              new GroupData().withId(g.getId()).withName(g.getName()))
              .collect(Collectors.toSet())));
    }

  }

  public void verifyContactListUI(){
    if (Boolean.getBoolean("verifyUI")){
      Contacts dbContacts = app.db().contacts();
      Contacts uiContacts = app.contact().all();
      assertThat(uiContacts, equalTo(dbContacts.stream().map((c)->
              new ContactData().withId(c.getId()).withFirstName(c.getFirstName()).withLastName(c.getLastName())
                      .withAddress(c.getAddress()).withAllEmails(app.contact().mergeEmails(c)).withAllPhones(app.contact().mergePhones(c)))
                      .collect(Collectors.toSet())));
    }

  }

}