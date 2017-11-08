package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactToGroupAdditionTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions(){
        if (app.db().groups().size()==0){
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("test_contact_add").withFooter("footer1").withHeader("header1"));
        }

        if (app.db().contacts().size()==0) {
            app.contact().create(new ContactData().withLastName("Last4").withHomePhone("111").inGroup(app.db().groups().iterator().next()), true);
            app.goTo().mainPage();
        }

        if (validGroupAndContactID().size()==0){
            app.contact().create(new ContactData().withLastName("Last4").withHomePhone("111").inGroup(app.db().groups().iterator().next()), true);
            app.goTo().mainPage();
        }



    }

    //@DataProvider
    public List<Integer> validGroupAndContactID ()  {
          Contacts contacts = app.db().contacts();
          Groups groups = app.db().groups();
        List<Integer> validGroupAndContactID= new ArrayList<Integer>();
          //Check if contact
          for (ContactData contact: contacts){
              for (GroupData group: groups){
                  if(!app.contact().isContactInGroup(contact, group)){
                      validGroupAndContactID.add(group.getId());
                      validGroupAndContactID.add(contact.getId());
                      return validGroupAndContactID;

                  }

              }
          }
        return validGroupAndContactID;
        }




    @Test
    public void testContactAddToGroup(){

        List<Integer> validID= validGroupAndContactID();
        Contacts before = app.db().contacts();
        Groups groups = app.db().groups();

        ContactData modifiedContact = before.stream().filter(data -> Objects.equals(data.getId(), validID.get(1))).findFirst().get();
        GroupData groupAssigned = groups.stream().filter(data -> Objects.equals(data.getId(), validID.get(0))).findFirst().get();

        ContactData contact = modifiedContact;
        contact = contact.inGroup(groupAssigned);
        app.goTo().mainPage();
        app.contact().addContactToGroup(contact, groupAssigned);
        Contacts after = app.db().contacts();
        assertThat(after, equalTo(before.withModified(modifiedContact, contact)));


    }
}
