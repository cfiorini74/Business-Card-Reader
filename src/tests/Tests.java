package tests;

import main.BusinessCardReader;
import main.ContactInfo;
import org.junit.Test;
import org.junit.Assert;
public class Tests {
    @Test
    public void testFileOne() throws Exception{
        BusinessCardReader reader= new BusinessCardReader("testfiles/testFile1.txt");
        ContactInfo contactInfo= reader.readFromCard();
        org.junit.Assert.assertEquals("James Harden",contactInfo.getName());
        org.junit.Assert.assertEquals("cfiorini74@gmail.com",contactInfo.getEmail());
        org.junit.Assert.assertEquals("11152686888",contactInfo.getPhone());
    }
    @Test
    public void testFileTwo() throws Exception{
        BusinessCardReader reader= new BusinessCardReader("testfiles/testFile2.txt");
        ContactInfo contactInfo= reader.readFromCard();
        org.junit.Assert.assertEquals("Mike Smith",contactInfo.getName());
        org.junit.Assert.assertEquals("msmith@asymmetrik.com",contactInfo.getEmail());
        org.junit.Assert.assertEquals("4105551234",contactInfo.getPhone());
    }
    @Test
    public void testFileThree() throws Exception{
        BusinessCardReader reader= new BusinessCardReader("testfiles/testFile3.txt");
        ContactInfo contactInfo= reader.readFromCard();
        org.junit.Assert.assertEquals("Lisa Haung",contactInfo.getName());
        org.junit.Assert.assertEquals("lisa.haung@foobartech.com",contactInfo.getEmail());
        org.junit.Assert.assertEquals("4105551234",contactInfo.getPhone());
    }
    @Test
    public void testFileFour() throws Exception{
        BusinessCardReader reader= new BusinessCardReader("testfiles/testFile4.txt");
        ContactInfo contactInfo= reader.readFromCard();
        org.junit.Assert.assertEquals("Arthur Wilson",contactInfo.getName());
        org.junit.Assert.assertEquals("awilson@abctech.com",contactInfo.getEmail());
        org.junit.Assert.assertEquals("17035551259",contactInfo.getPhone());
    }


}
