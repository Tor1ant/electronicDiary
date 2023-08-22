package com.sberbank.may.user.repository;

import com.sberbank.may.user.enums.Role;
import com.sberbank.may.user.model.User;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("Select u from User as u " +
            "where (:name = '' or u.name = :name)" +
            " and (:email = '' or u.email = :email)" +
            " and (:phone = '' or u.phone = :phone)")
    List<User> searchUser(@Param("name") String name, @Param("email") String email,
            @Param("phone") String phone);

    Optional<User> findUserByName(String name);

    Set<User> findUserByRole(Role teacher);
}
