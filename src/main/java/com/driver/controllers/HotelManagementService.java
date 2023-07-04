package com.driver.controllers;

import com.driver.controllers.HotelManagementRepository;
import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;

import java.util.List;

public class HotelManagementService {

    HotelManagementRepository hotelManagementRepository = new HotelManagementRepository();

    public String addHotel(Hotel hotel) {


        return hotelManagementRepository.addHotel(hotel);
    }

    public Integer addUser(User user) {

        //You need to add a User Object to the database
        //Assume that user will always be a valid user and return the aadharCardNo of the user
          return  hotelManagementRepository.addUser(user);
    }

    public String getHotelswithmostFacilities() {

        //Out of all the hotels we have added so far, we need to find the hotelName with most no of facilities
        //Incase there is a tie return the lexicographically smaller hotelName
        //Incase there is not even a single hotel with atleast 1 facility return "" (empty string)

          return hotelManagementRepository.getHotelswithmostFacilities();
    }

    public int bookAroom(Booking booking) {
        //The booking object coming from postman will have all the attributes except bookingId and amountToBePaid;
        //Have bookingId as a random UUID generated String
        //save the booking Entity and keep the bookingId as a primary key
        //Calculate the total amount paid by the person based on no. of rooms booked and price of the room per night.
        //If there arent enough rooms available in the hotel that we are trying to book return -1
        //in other case return total amount paid

         return hotelManagementRepository.bookAroom(booking);
    }

    public int getBookings(Integer aadharCard) {

        return  hotelManagementRepository.getBookings(aadharCard);
    }

    public Hotel updateFacilities(List<Facility> newFacilities, String hotelName) {


        return hotelManagementRepository.updateFacilities(newFacilities,hotelName);
    }
}
