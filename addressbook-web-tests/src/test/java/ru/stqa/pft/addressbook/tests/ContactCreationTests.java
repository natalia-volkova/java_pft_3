package ru.stqa.pft.addressbook.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {
@BeforeMethod
public void ensurePreconditions(){

  if (app.db().groups().size()==0){
    app.goTo().groupPage();
    app.group().create(new GroupData().withName("test_contact_add").withFooter("footer1").withHeader("header1"));
  }
  app.goTo().mainPage();
}

  @DataProvider
  public Iterator<Object []> validContactsXml () throws IOException {

  try(BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.xml")))){
    String xml = "";
    String line = reader.readLine();
    while (line!=null){
      xml+=line;
      line= reader.readLine();
    }
    XStream xstream = new XStream();
    xstream.processAnnotations(ContactData.class);
    List<ContactData> contacts = (List<ContactData>) xstream.fromXML(xml);
    return contacts.stream().map((g)-> new Object[]{g}).collect(Collectors.toList()).iterator();
  }



  }

  @DataProvider
  public Iterator<Object []> validContactsJson () throws IOException {
  try(BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.json")))){
    String json = "";
    String line = reader.readLine();
    while (line!=null){
      json+=line;
      line= reader.readLine();
    }
    Gson gson = new Gson();

    List<ContactData> contacts = (List<ContactData>) gson.fromJson(json, new TypeToken<List<ContactData>>(){}.getType());
    return contacts.stream().map((g)-> new Object[]{g}).collect(Collectors.toList()).iterator();
  }



  }

  @Test(dataProvider = "validContactsJson")
  public void testContactCreation(ContactData contact) {

    Groups groups = app.db().groups();
    Contacts before = app.db().contacts();
    GroupData groupForContact= groups.iterator().next();
    app.contact().create(contact.inGroup(groupForContact), true);
    assertThat(app.contact().count(), equalTo(before.size()+1));
    Contacts after = app.db().contacts();
    contact.withId(after.stream().mapToInt(g->g.getId()).max().getAsInt());
    ContactData contactAddedDb = after.stream().filter(data -> Objects.equals(data.getId(), contact.getId())).findFirst().get();
    assertThat(after, equalTo(before.withAdded(contact)));

    Assert.assertTrue(app.contact().isContactInGroup(contactAddedDb, groupForContact ));

    verifyContactListUI();

  }

}
