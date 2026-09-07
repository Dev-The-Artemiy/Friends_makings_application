package org.socialization.friends.makings.backend.friend.friendBuilder;

import org.socialization.friends.makings.backend.friend.Friend;

import java.util.Date;

public class FriendBuilderImpl implements FriendBuilder{
    private Friend friend;

    public FriendBuilderImpl(String name, String surname, String gender, String status, Date birthDate) {
        this.friend = new Friend(name, surname, gender, status, birthDate);
    }


    @Override
    public FriendBuilder setDateMet(Date dateMet) {
        this.friend.setDateMet(dateMet);
        return this;
    }

    @Override
    public FriendBuilder setDescription(String description) {
        this.friend.setDescription(description);
        return this;
    }

    @Override
    public Friend build() {
        return friend;
    }


}
