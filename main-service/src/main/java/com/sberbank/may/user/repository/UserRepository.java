package com.sberbank.may.user.repository;

import com.sberbank.may.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("Select u from User as u " +
            "where (:name is null or u.name = :name)" +
            " and (:email is null or u.email = :email)" +
            " and (:phone is null or u.phone = :phone)")
    List<User> searchUser(@Param("name") String name, @Param("email") String email,
                                     @Param("phone") String phone);
}
