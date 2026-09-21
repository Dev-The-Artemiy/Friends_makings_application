package org.socialization.friends.makings.backend.friend.repositories;

import org.socialization.friends.makings.backend.friend.Friend;
import org.socialization.friends.makings.backend.friend.exceptions.*;
import org.socialization.friends.makings.backend.friend.friendBuilder.FriendBuilder;
import org.socialization.friends.makings.backend.friend.friendBuilder.FriendBuilderImpl;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameterValue;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.*;

@Repository
@Profile("jdbc")
@Transactional(propagation = Propagation.REQUIRED)
public class JdbcFriendRepository implements FriendRepository{

    private JdbcTemplate jdbcTemplate;

    private Environment env;

    private final Integer maxDescriptionLength = 50;


    public JdbcFriendRepository(JdbcTemplate jdbcTemplate, Environment env) {
        this.jdbcTemplate = jdbcTemplate;
        this.env = env;
        dateFormat.setLenient(true);
    }

    private final String dateFormatPattern = "MM/dd/yyyy";
    private final SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatPattern);



    @Override
    public int addFriend(Friend friend) {
        Integer genderId = checkGenderExists(friend.getGender());
        Integer statusId = checkStatusExists(friend.getStatus());

        String sql = env.getProperty("sql.add_friend");
        if(friend.getBirthDate() == null){
            throw new NullBirthDateException("The birth date cannot be empty");
        }

        try{
            Date dateMet =  friend.getDateMet().orElseGet(() -> null);
            String formattedDateMet = null;
            if(dateMet != null){
                formattedDateMet = dateFormat.format(dateMet);
            }
        String description = friend.getDescription().orElseGet(()->null);
        jdbcTemplate.update(sql,
                friend.getName(),
                friend.getSurname(),
                dateFormat.format(friend.getBirthDate()),
                formattedDateMet == null ? new SqlParameterValue(Types.DATE, null) : formattedDateMet,
                description == null ? new SqlParameterValue(Types.VARCHAR, null) : description,
                statusId,
                genderId);
        }catch(DataIntegrityViolationException e){
            throw new LongDescriptionException("Description cannot be longer than " + maxDescriptionLength);
        }

        sql = env.getProperty("sql.find_max_friend_id");
        Integer newFriendId = jdbcTemplate.queryForObject(sql, Integer.class);
        friend.setId(newFriendId);
        return newFriendId;
    }

    @Override
    public void updateFriendStatus(Integer friendId, String newStatus) {
        Integer newStatusId = checkStatusExists(newStatus);
        String sql = env.getProperty("sql.update_friend_status");
        jdbcTemplate.update(sql,
                newStatusId
                , friendId);

    }

    public Integer checkStatusExists(String status){
        String sql = env.getProperty("sql.find_status_by_title");
        try{
            return jdbcTemplate.queryForObject(sql, Integer.class, status);
        }catch(DataAccessException e){
            throw new NoSuchStatusException("Status with the given title does not exist");
        }
    }

    private Integer checkGenderExists(String gender){
        String sql = env.getProperty("sql.find_gender_by_title");
        try{
            return jdbcTemplate.queryForObject(sql, Integer.class, gender);
        }catch(DataAccessException e){
            throw new NoSuchGenderException("Gender with the given title does not exist");
        }
    }

    private void checkDateIsValid(String date){

    }

    @Override
    public void deleteFriend(int friendId) {
        String sql = env.getProperty("sql.id_friend_exists");
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, friendId);

        if(count.equals(0)){
            throw new NoSuchFriendIdException("No friend with id " + friendId + " found");
        }

        sql = env.getProperty("sql.delete_friend");
        jdbcTemplate.update(sql, friendId);
    }

    @Override
    public List<Friend> getAllFriends() {
        String sql = env.getProperty("sql.get_all_friends");
        List<Map<String,Object>> mapList = jdbcTemplate.queryForList(sql);
        List<Friend> friends = new ArrayList<>();
        for(var map : mapList){
            Integer id = (Integer)map.get("id");
            String name = map.get("name").toString();
            String surname = map.get("surname").toString();
            Date birthDate = (Date)map.get("birth_date");
            Date dateMet = (Date)map.get("date_met");
            Object nullableDesc = map.get("description");
            String description = nullableDesc == null ? null : nullableDesc.toString();
            String status = map.get("status").toString();
            String gender = map.get("gender").toString();

            FriendBuilder builder = new FriendBuilderImpl(name, surname, gender, status, birthDate);
            Friend friend = builder.setDateMet(dateMet).setDescription(description)
                    .build();
            friend.setId(id);
            friends.add(friend);
        }
        return friends;
    }


}


//Proposals to do:
//Querying embedded database in tests is ok, because embedded database connection is not heavy but to make it more
//realistic-looking project I can initialize an in-memory derby database and create StubRepositoryImplementation class
//where I will make queries to the in-memory database.


