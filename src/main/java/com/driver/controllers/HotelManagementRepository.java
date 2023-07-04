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


        if(hotelHashMap.containsKey(hotel.getHotelName()) ||  hotel.getHotelName()==null){

             return "FAILURE";
        }

          hotelHashMap.put(hotel.getHotelName(),hotel);

        return "SUCCESS";

    }

    public Integer addUser(User user) {

        //You need to add a User Object to the database
        //Assume that user will always be a valid user and return the aadharCardNo of the user

         userHashMap.put(user.getaadharCardNo(), user);
         return user.getaadharCardNo();

    }

    public String getHotelswithmostFacilities() {

        //Out of all the hotels we have added so far, we need to find the hotelName with most no of facilities
        //Incase there is a tie return the lexicographically smaller hotelName
        //Incase there is not even a single hotel with atleast 1 facility return "" (empty string)

        int maxCount=0;

        String hotel ="";

        for(String hotels: hotelHashMap.keySet()) {

            if (hotelHashMap.get(hotels).getFacilities().size() > maxCount) {

                hotel = hotelHashMap.get(hotels).getHotelName();
            }
        }
        if(maxCount==0) return "";

        List<String> hotelNames = new ArrayList<>();
        for (String key : hotelHashMap.keySet()) {
            List<Facility> facilities = hotelHashMap.get(key).getFacilities();
            if (facilities.size() == maxCount) hotelNames.add(key);
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


       String bookingId = String.valueOf(UUID.randomUUID());

       booking.setBookingId(bookingId);

        int totalamountToBePaid =0;

        if(!hotelHashMap.containsKey(booking.getHotelName())) return -1;

        if(hotelHashMap.get(booking.getHotelName()).getAvailableRooms() < booking.getNoOfRooms()) return -1;

        else {

              int availalberooms =  hotelHashMap.get(booking.getHotelName()).getAvailableRooms();

              hotelHashMap.get(booking.getHotelName()).setAvailableRooms(availalberooms-booking.getNoOfRooms());


              totalamountToBePaid = hotelHashMap.get(booking.getHotelName()).getPricePerNight()*booking.getNoOfRooms();


              bookingHashMap.put(bookingId,booking);
              userbookings.put(bookingId,totalamountToBePaid);
              return totalamountToBePaid;

        }


    }

    public int getBookings(Integer aadharCard) {


           List<String> stringList = new ArrayList<>();

            for(String bookingId: bookingHashMap.keySet()){

                  if(aadharCard.equals(bookingHashMap.get(bookingId).getBookingAadharCard())){

                      stringList.add(bookingId);
                  }
            }

            return  stringList.size();
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
