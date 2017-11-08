package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDeletionTests extends TestBase{
  @BeforeMethod
  public void ensurePreconditions() {

    if (app.db().contacts().size()==0) {
      app.contact().create(new ContactData().withLastName("Last4").withHomePhone("111"), true);
      app.goTo().mainPage();
    }
  }

    
    @Test
    public void testContactDelete() {

      Contacts before = app.db().contacts();
      app.goTo().mainPage();
      ContactData deletedContact = before.iterator().next();
      app.contact().delete(deletedContact);
      assertThat(app.contact().count(), equalTo(before.size()-1));
      Contacts after = app.db().contacts();
      assertThat(after, equalTo(before.without(deletedContact)));
      verifyContactListUI();
        }




}
