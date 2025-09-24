package com.example.myapplication;


import com.google.gson.annotations.SerializedName;

public class CountryResponse {
    @SerializedName("name")
    private Name name;

    @SerializedName("flags")
    private Flags flags;

    public static class Name {
        @SerializedName("common")
        private String common;

        public String getCommon() {
            return common;
        }
    }

    public static class Flags {
        @SerializedName("png")
        private String png;

        public String getPng() {
            return png;
        }
    }

    public String getCommonName() {
        return name.getCommon();
    }

    public String getFlag() {
        return flags.getPng();
    }
}

