package com.example.myapplication;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    public static class Name implements Serializable {
        private static final long serialVersionUID = 1L;

        @SerializedName("first")
        private String first;
        @SerializedName("last")
        private String last;

        public String getFirst() { return first; }
        public String getLast() { return last; }
    }

    public static class Picture implements Serializable {
        private static final long serialVersionUID = 1L;

        @SerializedName("large")
        private String large;

        public String getLarge() { return large; }
    }

    public static class Location implements Serializable {
        private static final long serialVersionUID = 1L;

        @SerializedName("street")
        private Street street;
        @SerializedName("city")
        private String city;
        @SerializedName("country")
        private String country;
        @SerializedName("coordinates")
        private Coordinates coordinates;

        public Street getStreet() { return street; }
        public String getCity() { return city; }
        public String getCountry() { return country; }
        public Coordinates getCoordinates() { return coordinates; }
    }

    public static class Street implements Serializable {
        private static final long serialVersionUID = 1L;

        @SerializedName("number")
        private int number;
        @SerializedName("name")
        private String name;

        @Override
        public String toString() {
            return number + " " + name;
        }
    }

    public static class Coordinates implements Serializable {
        private static final long serialVersionUID = 1L;

        @SerializedName("latitude")
        private double latitude;
        @SerializedName("longitude")
        private double longitude;

        public double getLatitude() { return latitude; }
        public double getLongitude() { return longitude; }
    }

    public static class Id implements Serializable {
        private static final long serialVersionUID = 1L;

        @SerializedName("value")
        private String value;

        public String getValue() { return value != null ? value : "No disponible"; }
    }

    public static class Dob implements Serializable {
        private static final long serialVersionUID = 1L;

        @SerializedName("age")
        private int age;

        public int getAge() { return age; }
    }

    @SerializedName("name")
    private Name name;
    @SerializedName("email")
    private String email;
    @SerializedName("picture")
    private Picture picture;
    @SerializedName("phone")
    private String phone;
    @SerializedName("cell")
    private String cell;
    @SerializedName("nat")
    private String nationality;
    @SerializedName("location")
    private Location location;
    @SerializedName("dob")
    private Dob dob;
    @SerializedName("id")
    private Id id;

    public Name getName() { return name; }
    public String getEmail() { return email; }
    public Picture getPicture() { return picture; }
    public String getPhone() { return phone; }
    public String getCell() { return cell; }
    public String getNationality() { return nationality; }
    public Location getLocation() { return location; }
    public int getAge() { return dob.getAge(); }
    public Id getId() { return id; }
}
