package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;
import ru.stqa.pft.addressbook.tests.ContactPhoneTests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ContactHelper extends HelperBase {

  public ContactHelper(ApplicationManager app) {
    super(app);
  }

  public void submitContactCreation() {
    click(By.xpath("//div[@id='content']/form/input[21]"));
  }

  public void fillContactForm(ContactData contactData, boolean creation) {
    type(By.name("firstname"), contactData.getFirstName());
    type(By.name("lastname"), contactData.getLastName());
    type(By.name("home"), contactData.getHomePhone());
    type(By.name("mobile"), contactData.getMobile());
    type(By.name("work"), contactData.getWorkPhone());
    type(By.name("email"), contactData.geteMail());
    type(By.name("email2"), contactData.geteMail2());
    type(By.name("email3"), contactData.geteMail3());

    if (creation) {
      if ((contactData.getGroups().size()>0)) {
        Assert.assertTrue(contactData.getGroups().size()==1);
        String groupName= contactData.getGroups().iterator().next().getName();
        new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(groupName);
      }

    } else {
      Assert.assertFalse(isElementPresent(By.name("new_group")));
    }
  }


  public void initCreation() {
    click(By.linkText("add new"));
  }

  public void select(int index) {
    wd.findElements(By.name("selected[]")).get(index).click();
  }

  public void delete() {
    click(By.xpath("//div[@id='content']/form[2]/div[2]/input"));
  }

  public void closeConfirmationDialog() {
    wd.switchTo().alert().accept();
  }


  public void clickEditIcon(int index) {
    wd.findElements(By.xpath("//img[@title='Edit']")).get(index).click();
  }

  public void submitModification() {
    click(By.xpath("//div[@id='content']/form[1]/input[22]"));
  }

  public int count() {
    return wd.findElements(By.name("selected[]")).size();
  }

  public void create(ContactData contact, boolean creation) {
    initCreation();
    fillContactForm(contact, creation);
    submitContactCreation();
    contactCache = null;
    app.goTo().mainPage();
  }

  public void modify(ContactData contact) {
    clickEditIconByID(contact.getId());

    fillContactForm(contact, false);
    submitModification();
    contactCache = null;
    app.goTo().mainPage();
  }

  public void clickEditIconByID(int id) {
    WebElement checkbox = wd.findElement(By.cssSelector("input[value='" + id + "']"));
    WebElement contactRow = checkbox.findElement(By.xpath("./../../."));
    contactRow.findElement(By.xpath(".//img[@title='Edit']")).click();
  }

  public void delete(int index) {
    select(index);
    delete();
    closeConfirmationDialog();
    contactCache = null;
    app.goTo().mainPage();
  }

  public void delete(ContactData contact) {
    selectById(contact.getId());
    delete();
    closeConfirmationDialog();
    contactCache = null;
    app.goTo().mainPage();
  }

  private void selectById(int id) {
    wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
  }

  public boolean isThereAContact() {
    return isElementPresent((By.name("selected[]")));
  }

  public ContactData infoFromEditForm(ContactData contact) {
    clickEditIconByID(contact.getId());
    String firstName = wd.findElement(By.name("firstname")).getAttribute("value");
    String lastName = wd.findElement(By.name("lastname")).getAttribute("value");
    String homePhone = wd.findElement(By.name("home")).getAttribute("value");
    String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
    String workPhone = wd.findElement(By.name("work")).getAttribute("value");
    String fax = wd.findElement(By.name("fax")).getAttribute("value");
    String phone2 = wd.findElement(By.name("phone2")).getAttribute("value");
    String email = wd.findElement(By.name("email")).getAttribute("value");
    String email2 = wd.findElement(By.name("email2")).getAttribute("value");
    String email3 = wd.findElement(By.name("email3")).getAttribute("value");
    String address = wd.findElement(By.name("address")).getAttribute("value");
    app.goTo().mainPage();
    return new ContactData().withId(contact.getId()).withFirstName(firstName).withLastName(lastName)
            .withHomePhone(homePhone).withMobile(mobile).withWorkPhone(workPhone).withFax(fax).withPhone2(phone2)
            .withEMail(email).withEMail2(email2).withEMail3(email3).withAddress(address);
  }

  public List<ContactData> list() {
    List contacts = new ArrayList<ContactData>();
    List<WebElement> elements = wd.findElements(By.xpath("//tr[@name='entry']"));

    for (WebElement element : elements) {
      List<WebElement> contactEntries = element.findElements(By.cssSelector("td"));

      String firstName = contactEntries.get(2).getText();
      String lastName = contactEntries.get(1).getText();
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
      ContactData contact = new ContactData().withId(id).withLastName(lastName).withFirstName(firstName);
      contacts.add(contact);

    }

    return contacts;
  }

  public Contacts contactCache = null;

  /*public Contacts all() {
    if (contactCache!=null){
      return new Contacts(contactCache);
    }
    contactCache = new Contacts();
    List <WebElement> elements = wd.findElements(By.xpath("//tr[@name='entry']"));

    for (WebElement element: elements){
      List <WebElement> contactEntries = element.findElements(By.cssSelector("td"));

      String firstName = contactEntries.get(2).getText();
      String lastName = contactEntries.get(1).getText();
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
      ContactData contact = new ContactData().withId(id).withLastName(lastName).withFirstName(firstName);
      contactCache.add(contact);

    }

    return new Contacts(contactCache);
  }*/

  public Contacts all() {

    if (contactCache != null) {
      return new Contacts(contactCache);
    }

    //Set <ContactData> contacts = new HashSet<ContactData>();
    contactCache = new Contacts();
    List<WebElement> elements = wd.findElements(By.xpath("//tr[@name='entry']"));

    for (WebElement element : elements) {
      List<WebElement> contactEntries = element.findElements(By.cssSelector("td"));
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
      String firstName = contactEntries.get(2).getText();
      String lastName = contactEntries.get(1).getText();
      String allPhones = contactEntries.get(5).getText();
      String allEmails = contactEntries.get(4).getText();
      String address = contactEntries.get(3).getText();
      ContactData contact = new ContactData().withId(id).withLastName(lastName).withFirstName(firstName)
              .withAllPhones(allPhones).withAllEmails(allEmails).withAddress(address);
      //contacts.add(contact);
      contactCache.add(contact);
    }
    //return contacts;
    return new Contacts(contactCache);
  }



  public static String cleaned(String phone){
    return phone.replaceAll("\\s","").replaceAll("[-()]","");
  }

  public String mergePhones(ContactData contact) {
    return Arrays.asList(contact.getHomePhone(),contact.getMobile(), contact.getWorkPhone(), contact.getPhone2())
            .stream().filter((s) -> !s.equals(""))
            .map(ContactPhoneTests::cleaned)
            .collect(Collectors.joining("\n"));

  }

  public String mergeEmails(ContactData contact) {
    return Arrays.asList(contact.geteMail(),contact.geteMail2(), contact.geteMail3())
            .stream().filter((s) -> !s.equals(""))
            .collect(Collectors.joining("\n"));

  }

  public boolean isContactInGroup(ContactData contact, GroupData group){
    //Groups contactGroups = new List<GroupData>;
    if (contact.getGroups().size()==0){
      return false;
    }
    Groups contactGroups= contact.getGroups();

    for (GroupData contactGroup:contactGroups) {
      if (contactGroup.equals(group)){
        return true;
      }

    }
    return false;

  }


  public void addContactToGroup(ContactData contact, GroupData groupAssigned) {
    selectById(contact.getId());
    String groupName= groupAssigned.getName();
    new Select(wd.findElement(By.name("to_group"))).selectByVisibleText(groupAssigned.getName());
    wd.findElement(By.name("add")).click();
  }

  public void removeContactFromGroup(ContactData contact, GroupData groupUnassigned) {
    app.goTo().mainPage();
    selectGroup(groupUnassigned.getName());
    selectById(contact.getId());
    wd.findElement(By.name("remove")).click();
    app.goTo().mainPage();
  }

  private void selectGroup(String groupName) {
    new Select(wd.findElement(By.name("group"))).selectByVisibleText(groupName);
  }

  public ContactData setContactGroups(ContactData modifiedContact, ContactData contact) {
    Groups contactsGroups = modifiedContact.getGroups();
    if (contactsGroups.size() > 0) {
      for (GroupData group : contactsGroups) {
        contact.inGroup(group);
      }
      return contact;
    }
    return contact;
  }
}
