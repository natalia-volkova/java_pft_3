package ru.stqa.pft.addressbook.tests;

        import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

        import java.util.Arrays;
        import java.util.stream.Collectors;

        import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactPhoneTests extends TestBase {

    @Test
    public void testContactPhones() {
        app.goTo().mainPage();
        ContactData contact = app.contact().all().iterator().next();
        ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);

        assertThat(contact.getAllPhones(), equalTo(app.contact().mergePhones(contactInfoFromEditForm)));
        assertThat(contact.getAllEmails(), equalTo(app.contact().mergeEmails(contactInfoFromEditForm)));
        assertThat(contact.getAddress(), equalTo(contactInfoFromEditForm.getAddress()));

    }

    public static String cleaned(String phone) {
        return phone.replaceAll("\\s", "").replaceAll("[-()]", "");
    }

    public String mergePhones(ContactData contact) {
        return Arrays.asList(contact.getHomePhone(), contact.getMobile(), contact.getWorkPhone(), contact.getPhone2())
                .stream().filter((s) -> !s.equals(""))
                .map(ContactPhoneTests::cleaned)
                .collect(Collectors.joining("\n"));

    }

    public String mergeEmails(ContactData contact) {
        return Arrays.asList(contact.geteMail(), contact.geteMail2(), contact.geteMail3())
                .stream().filter((s) -> !s.equals(""))
                .collect(Collectors.joining("\n"));


    }
}
