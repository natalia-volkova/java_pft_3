package ru.stqa.pft.addressbook.model;

import com.google.gson.annotations.Expose;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

@XStreamAlias("contact")
@Entity
@Table(name = "addressbook")

public class ContactData {
  @XStreamOmitField
  @Id
  @Column(name = "id")
  private int id= Integer.MAX_VALUE;
  @Expose
  @Column(name = "lastname")
  private  String lastName = "";
  @Expose
  @Column(name = "firstname")
  private  String firstName= "";
  @Expose
  @Column(name = "home")
  @Type(type="text")
  private  String homePhone= "";
  @Expose
  @Column(name = "mobile")
  @Type(type="text")
  private  String mobile= "";
  @Expose
  @Column(name = "work")
  @Type(type="text")
  private  String workPhone= "";
  @XStreamOmitField
  @Column(name = "fax")
  @Type(type="text")
  private  String fax= "";
  @XStreamOmitField
  @Column(name = "phone2 ")
  @Type(type="text")
  private  String phone2= "";
  @Expose
  @Type(type="text")
  @Column(name = "email")
  private  String eMail= "";
  @Expose
  @Type(type="text")
  @Column(name = "email2")
  private  String eMail2= "";
  @Expose
  @Type(type="text")

  @Column(name = "email3")
  private  String eMail3= "";
  @XStreamOmitField
  @Column(name = "address")
  @Type(type="text")
  private  String address= "";

   @XStreamOmitField
  @Transient
  private String allPhones;
  @XStreamOmitField
  @Transient
  private String allEmails;
  @Column(name = "photo")
  @Type(type="text")
  public String photo= "";

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name="address_in_groups",
          joinColumns = @JoinColumn (name = "id"),inverseJoinColumns = @JoinColumn (name = "group_id"))
  @Type(type="text")
  private Set<GroupData> groups = new HashSet<GroupData>();

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ContactData that = (ContactData) o;

    if (id != that.id) return false;
    if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
    if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
    if (homePhone != null ? !homePhone.equals(that.homePhone) : that.homePhone != null) return false;
    if (mobile != null ? !mobile.equals(that.mobile) : that.mobile != null) return false;
    if (workPhone != null ? !workPhone.equals(that.workPhone) : that.workPhone != null) return false;
    if (fax != null ? !fax.equals(that.fax) : that.fax != null) return false;
    if (phone2 != null ? !phone2.equals(that.phone2) : that.phone2 != null) return false;
    if (eMail != null ? !eMail.equals(that.eMail) : that.eMail != null) return false;
    if (eMail2 != null ? !eMail2.equals(that.eMail2) : that.eMail2 != null) return false;
    if (eMail3 != null ? !eMail3.equals(that.eMail3) : that.eMail3 != null) return false;
    if (address != null ? !address.equals(that.address) : that.address != null) return false;

    if (photo != null ? !photo.equals(that.photo) : that.photo != null) return false;
    return groups != null ? groups.equals(that.groups) : that.groups == null;
  }

  @Override
  public int hashCode() {
    int result = id;
    result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
    result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
    result = 31 * result + (homePhone != null ? homePhone.hashCode() : 0);
    result = 31 * result + (mobile != null ? mobile.hashCode() : 0);
    result = 31 * result + (workPhone != null ? workPhone.hashCode() : 0);
    result = 31 * result + (fax != null ? fax.hashCode() : 0);
    result = 31 * result + (phone2 != null ? phone2.hashCode() : 0);
    result = 31 * result + (eMail != null ? eMail.hashCode() : 0);
    result = 31 * result + (eMail2 != null ? eMail2.hashCode() : 0);
    result = 31 * result + (eMail3 != null ? eMail3.hashCode() : 0);
    result = 31 * result + (address != null ? address.hashCode() : 0);

    result = 31 * result + (photo != null ? photo.hashCode() : 0);
    result = 31 * result + (groups != null ? groups.hashCode() : 0);
    return result;
  }

  public File getPhoto() {return new File(photo);}

public ContactData withPhoto(File photo){
  this.photo=photo.getPath();
  return this;
}

  public ContactData withId(int id) {
    this.id = id;
    return this;
  }

  public ContactData withLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public ContactData withFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public ContactData withHomePhone(String homePhone) {
    this.homePhone = homePhone;
    return this;
  }

  public ContactData withMobile(String mobile) {
    this.mobile = mobile;
    return this;
  }

  public ContactData withWorkPhone(String workPhone) {
    this.workPhone = workPhone;
    return this;
  }

  public ContactData withFax(String fax) {
    this.fax = fax;
    return this;
  }

  public ContactData withPhone2(String phone2) {
    this.phone2 = phone2;
    return this;
  }


  public ContactData withEMail(String eMail) {
    this.eMail = eMail;
    return this;
  }

  public ContactData withEMail2(String eMail2) {
    this.eMail2 = eMail2;
    return this;
  }

  public ContactData withEMail3(String eMail3) {
    this.eMail3 = eMail3;
    return this;
  }

  public ContactData withAddress(String address) {
    this.address = address;
    return this;
  }

  public ContactData withAllPhones(String allPhones) {
    this.allPhones = allPhones;
    return this;
  }

  public ContactData withAllEmails(String allEmails) {
    this.allEmails = allEmails;
    return this;
  }

  public ContactData inGroup(GroupData group) {
    groups.add(group);
    return this;
  }

  public String getLastName() {
    return lastName;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getHomePhone() {
    return homePhone;
  }

  public String getMobile() {
    return mobile;
  }

  public String getWorkPhone() { return workPhone; }

  public String getFax() {
    return fax;
  }

  public String getPhone2() {
    return phone2;
  }



  public String getAllPhones() {
    return allPhones;
  }

  public String geteMail() {
    return eMail;
  }

  public String geteMail2() {
    return eMail2;
  }

  public String geteMail3() {
    return eMail3;
  }

  public String getAllEmails() {
    return allEmails;
  }

  public String getAddress() {
    return address;
  }

  public int getId() {    return id;  }

  public Groups getGroups() {
    return new Groups(groups);
  }



 /* @Override
  public String toString() {
    return "ContactData{" +
            "lastName='" + lastName + '\'' +
            ", firstName='" + firstName + '\'' +
            '}';
  }*/

  @Override
  public String toString() {
    return "ContactData{" +
            "id=" + id +
            ", lastName='" + lastName + '\'' +
            ", firstName='" + firstName + '\'' +
            ", homePhone='" + homePhone + '\'' +
            ", mobile='" + mobile + '\'' +
            ", workPhone='" + workPhone + '\'' +
            ", fax='" + fax + '\'' +
            ", phone2='" + phone2 + '\'' +
            ", eMail='" + eMail + '\'' +
            ", eMail2='" + eMail2 + '\'' +
            ", eMail3='" + eMail3 + '\'' +
            ", address='" + address + '\'' +
            ", photo='" + photo + '\'' +

            '}';
  }
}
