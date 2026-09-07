package org.socialization.friends.makings.backend.friend.repositories;

import org.socialization.friends.makings.backend.friend.Friend;
import org.socialization.friends.makings.backend.friend.friendBuilder.FriendBuilder;
import org.socialization.friends.makings.backend.friend.friendBuilder.FriendBuilderImpl;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class FriendRowMapper implements RowMapper<Friend> {
    @Override
    public Friend mapRow(ResultSet rs, int rowNum) throws SQLException {
        String name = rs.getString("Name");
        String surname = rs.getString("Surname");
        Date birthDate = rs.getDate("Birth_date");
        Date dateMet = rs.getDate("Date_met");
        String description = rs.getString("Description");
        String gender = rs.getString("Gender");
        String status = rs.getString("Status");

        FriendBuilder builder = new FriendBuilderImpl(name, surname, gender, status, birthDate);
        builder.setDateMet(dateMet);
        builder.setDescription(description);

        return builder.build();
    }
}