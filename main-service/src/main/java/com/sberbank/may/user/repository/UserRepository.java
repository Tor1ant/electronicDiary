package com.sberbank.may.user.repository;

import com.sberbank.may.user.enums.Role;
import com.sberbank.may.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;


/**
 * Репозиторий User для взаимодействия с базой данных.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Метод для поиска пользователя по имени, электронной почте и номеру телефона.
     * Если определенный параметр пуст, он не учитывается при поиске.
     *
     * @param name строка, содержащая имя пользователя
     * @param email строка, содержащая электронный адрес пользователя
     * @param phone строка, содержащая номер телефона пользователя
     * @returns список пользователей, соответствующих заданным параметрам
     */
    @Query("Select u from User as u " +
            "where (:name = '' or u.name = :name)" +
            " and (:email = '' or u.email = :email)" +
            " and (:phone = '' or u.phone = :phone)")
    List<User> searchUser(@Param("name") String name, @Param("email") String email,
            @Param("phone") String phone);

    /**
     * Метод для поиска пользователя по имени.
     *
     * @param name строка, содержащая имя пользователя
     * @returns опциональный объект пользователя, если такой пользователь найден
     */
    Optional<User> findUserByName(String name);

    /**
     * Метод для поиска пользователя по email.
     *
     * @param email строка, содержащая email пользователя
     * @returns опциональный объект пользователя, если такой пользователь найден
     */
    Optional<User> findUserByEmail(String email);

    /**
     * Метод для поиска пользователей по роли.
     *
     * @param role объект типа Role, представляющий роль пользователя
     * @returns набор пользователей с заданной ролью
     */
    Set<User> findUserByRole(Role role);

    /**
     * Метод для поиска пользователей по телефону.
     *
     * @param phone
     * @returns набор пользователей с заданным телефоном
     */
    Optional<User> findUserByPhone(String phone);
}
