package com.sankalpchauhan.flipkartgridchallenge.service.model;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignUpRequest implements Serializable
{

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("role")
    @Expose
    private String role = "vendor";
    private final static long serialVersionUID = 2641676814851576542L;

    /**
     * No args constructor for use in serialization
     *
     */
    public SignUpRequest() {
    }

    /**
     *
     * @param password
     * @param name
     * @param userName
     * @param email
     */
    public SignUpRequest(String name, String userName, String email, String password) {
        super();
        this.name = name;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.role = "vendor";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}