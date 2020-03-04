package main;

//Class for Contact Info, which is returned by BusinessCardReader.readFromCard()
public class ContactInfo {
    private String name;
    private String email;
    private String phone;

    public ContactInfo(String namePara,String emailPara, String phonePara){
        name=namePara;
        email=emailPara;
        phone=phonePara;
    }
    public String getName(){
        return name;
    }
    public String getEmail(){
        return email;
    }
    public String getPhone(){
        return phone;
    }
}
