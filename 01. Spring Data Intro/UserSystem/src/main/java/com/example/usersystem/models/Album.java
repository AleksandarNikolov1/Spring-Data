package com.example.usersystem.models;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "albums")
public class Album extends BaseEntity{
    private String name;
    private String backgroundColor;
    @Column(name = "is_public")
    private Boolean isPublic;
    @ManyToMany
    @JoinTable(
            name = "albums_pictures",
            joinColumns = @JoinColumn(name = "album_id"),
            inverseJoinColumns = @JoinColumn(name = "picture_id")
    )
    private Set<Picture> pictures;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Boolean isPublic() {
        return isPublic;
    }

    public void setPublic(Boolean aPublic) {
        isPublic = aPublic;
    }

    public Set<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(Set<Picture> pictures) {
        this.pictures = pictures;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
