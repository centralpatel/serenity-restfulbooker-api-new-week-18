package com.herokuapp.restful_booker.bookinginfo;

import com.herokuapp.restful_booker.model.BookingDatesPojo;
import com.herokuapp.restful_booker.testbase.TestBaseBooking;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class BookingCURDTestWithSteps extends TestBaseBooking {
    static String firstname = "Nehal";
    static String lastname = "Patel";
    static Integer totalprice = 150;
    static Boolean depositpaid = true;
    static Date checkin = new Date(2018, 01, 01);
    static Date checkout = new Date(2019, 01, 01);
    static String additionalneeds = "Breakfast";
    static int bookingID;

    @Steps
    BookingSteps bookingSteps;

    @Title("This will create new booking")
    @Test
    public void test001() {
        BookingDatesPojo bookingdates = new BookingDatesPojo();
        bookingdates.setCheckin(checkin);
        bookingdates.setCheckout(checkout);
        bookingSteps.createBooking(firstname, lastname, totalprice, depositpaid, bookingdates, additionalneeds).statusCode(200).log().all();

    }

    @Title("Verify if the Booking was done correctly")
    @Test
    public void test002() {
        firstname = "Bella";
        ArrayList<HashMap<String, Object>> value = bookingSteps.getBookingInfoByFirstname(firstname);
        Assert.assertThat(value.get(0), hasValue(firstname));
        bookingID = (Integer) value.get(0).get("id");
        System.out.println(bookingID);
    }


    @Title("Update the Booking and verify the updated information")
    @Test
    public void test003() {
        firstname = "Neha";
        lastname = "Patil";
        additionalneeds = "Vegan Meal";
        bookingID =  4840;

        BookingDatesPojo bookingdates = new BookingDatesPojo();
        bookingdates.setCheckin(checkin);
        bookingdates.setCheckout(checkout);
        bookingSteps.updateBooking(bookingID, firstname, lastname, totalprice, depositpaid, bookingdates, additionalneeds).statusCode(200).log().body().body("firstname", equalTo(firstname), "lastname", equalTo(lastname));
        ArrayList<HashMap<String, Object>> value = bookingSteps.getBookingInfoByFirstname(firstname);
        Assert.assertThat(value.get(0), hasValue(firstname));
    }


    @Title("Delete the Booking and verify if the Booking is deleted!")
    @Test
    public void test004() {
        bookingSteps.deleteBooking(bookingID).statusCode(204);
        bookingSteps.getBookingById(bookingID).statusCode(404);
    }
}

