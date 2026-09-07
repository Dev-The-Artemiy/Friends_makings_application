package org.socialization.friends.makings.backend.friend.exceptions;

public class NoSuchFriendIdException extends RuntimeException{
    public NoSuchFriendIdException(String message) {
        super(message);
    }
}
