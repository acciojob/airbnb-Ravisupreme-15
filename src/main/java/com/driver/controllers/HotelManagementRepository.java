package com.driver.controllers;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import io.swagger.models.auth.In;

import java.util.*;

public class HotelManagementRepository {


     // hotel hashmap
    //hotelname -  hotel

    HashMap<String,Hotel>  hotelHashMap = new HashMap<>();

    // user
    // adhar num, user
    HashMap<Integer, User> userHashMap = new HashMap<>();

    // bookaroom

    // booking id , booking

     HashMap<String,Booking> bookingHashMap = new HashMap<>();

     // user bookings

      HashMap<String,Integer> userbookings = new HashMap<>();



    public String addHotel(Hotel hotel) {
        //You need to add an hotel to the database
        //incase the hotelName is null or the hotel Object is null return an empty a FAILURE
        //Incase somebody is trying to add the duplicate hotelName return FAILURE
        //in all other cases return SUCCESS after successfully adding the hotel to the hotelDb.


        if (hotel.getHotelName() == null)return "FAILURE";
        if (hotelHashMap.containsKey(hotel.getHotelName()))return "FAILURE";
        String hotelName = hotel.getHotelName();
        hotelHashMap.put(hotelName, hotel);
        return "SUCCESS";

    }

    public Integer addUser(User user) {

        //You need to add a User Object to the database
        //Assume that user will always be a valid user and return the aadharCardNo of the user

        int adharNum = user.getaadharCardNo();
        userHashMap.put(adharNum, user);
        return adharNum;
    }

    public String getHotelswithmostFacilities() {

        //Out of all the hotels we have added so far, we need to find the hotelName with most no of facilities
        //Incase there is a tie return the lexicographically smaller hotelName
        //Incase there is not even a single hotel with atleast 1 facility return "" (empty string)

        int maxFacility = 0;
        for (String key : hotelHashMap.keySet()) {
            List<Facility> facilities = hotelHashMap.get(key).getFacilities();
            maxFacility = Math.max(maxFacility, facilities.size());
        }

        if (maxFacility == 0) return "";
        List<String> hotelNames = new ArrayList<>();
        for (String key : hotelHashMap.keySet()) {
            List<Facility> facilities = hotelHashMap.get(key).getFacilities();
            if (facilities.size() == maxFacility) hotelNames.add(key);
        }
        Collections.sort(hotelNames);
        return hotelNames.get(0);
    }

    public int bookAroom(Booking booking) {

        //The booking object coming from postman will have all the attributes except bookingId and amountToBePaid;
        //Have bookingId as a random UUID generated String
        //save the booking Entity and keep the bookingId as a primary key
        //Calculate the total amount paid by the person based on no. of rooms booked and price of the room per night.
        //If there arent enough rooms available in the hotel that we are trying to book return -1
        //in other case return total amount paid

        String hotelName = booking.getHotelName();
        if (!hotelHashMap.containsKey(hotelName))return -1;
        if (hotelHashMap.get(hotelName).getAvailableRooms() >= booking.getNoOfRooms()) {
            Hotel hotel = hotelHashMap.get(hotelName);
            int totalRoomAvilable = hotel.getAvailableRooms();
            totalRoomAvilable -= booking.getNoOfRooms();
            hotel.setAvailableRooms(totalRoomAvilable);
            hotelHashMap.put(hotelName, hotel);
            String bookingId = UUID.randomUUID() + "";
            System.out.println(bookingId + "bookingId");
            int amountTobePaid = hotel.getPricePerNight() * booking.getNoOfRooms();
            bookingHashMap.put(bookingId, booking);
            userbookings.put(bookingId, amountTobePaid);
            System.out.println(amountTobePaid + "Amount To Paid");
            return amountTobePaid;
        }
        return -1;
    }

    public int getBookings(Integer aadharCard) {


        int cnt = 0;
        for (String key : bookingHashMap.keySet()) {
            if (aadharCard.equals(bookingHashMap.get(key).getBookingAadharCard()))cnt++;
        }
        return cnt;
    }

    public Hotel updateFacilities(List<Facility> newFacilities, String hotelName) {


        //We are having a new facilites that a hotel is planning to bring.
        //If the hotel is already having that facility ignore that facility otherwise add that facility in the hotelDb
        //return the final updated List of facilities and also update that in your hotelDb
        //Note that newFacilities can also have duplicate facilities possible




          if(!hotelHashMap.containsKey(hotelName))return null;

          Hotel hotel = hotelHashMap.get(hotelName);

        List<Facility> list = hotel.getFacilities();

        for(int i=0;i<newFacilities.size();i++){

              if(!list.contains(newFacilities.get(i))){

                  list.add(newFacilities.get(i));
              }

        }

         hotel.setFacilities(list);

        hotelHashMap.put(hotelName,hotel);

        return  hotel;


    }
}
