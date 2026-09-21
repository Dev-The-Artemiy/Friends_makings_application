package org.socialization.friends.makings.backend.friend.services;

public enum BackendErrorState {
     OK(0)
    ,LongDescription(1)
    ,BadStatus(2)
    ,BadGender(3)
    ,BadFriendId(4)
    ,NullBirthDate(5);

    private int code;
    BackendErrorState(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
