package com.bgs.BoardGameSelector.dao;


import com.bgs.BoardGameSelector.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;



@Transactional
public interface UserDao extends CrudRepository<User, Long> {
    User findByUsername(String username);

    @Query(
            value = "Select comment.id as comment_id, comment.user_id as user_id, comment.content as content, " +
                    "comment.reply_to as reply_to, user.username as username, user.avatar as avatar," +
                    "comment.date_created as date\n" +
                    "from comment\n" +
                    " join user \n" +
                    " on comment.user_id = user.id\n" +
                    " WHERE comment.game_id = :targetId",
            nativeQuery = true)
    ArrayList<Object[]> joinUsersWithComment(@Param("targetId") int targetId);

}
