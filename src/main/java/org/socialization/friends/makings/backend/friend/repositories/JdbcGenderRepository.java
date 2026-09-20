package org.socialization.friends.makings.backend.friend.repositories;

import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JdbcGenderRepository implements GenderRepository{
    private final Environment env;
    private final JdbcTemplate template;

    public JdbcGenderRepository(Environment env, JdbcTemplate template) {
        this.env = env;
        this.template = template;
    }

    @Override
    public List<String> getTitles() {
        String sql = env.getProperty("sql.get_all_genders_titles");
        List<Map<String, Object>> mapList = template.queryForList(sql);
        List<String> titles = new ArrayList<>();

        for(Map<String, Object> map : mapList){
            String title = (String)map.get("title");
            titles.add(title);
        }
        return titles;
    }
}
