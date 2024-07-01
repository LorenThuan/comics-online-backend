package com.johanle.comicsonlinebackend.repository;

import com.johanle.comicsonlinebackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    @Query(value = "SELECT * FROM user WHERE name LIKE %:name% AND role LIKE 'ROLE_USER'", nativeQuery = true)
    public List<User> findUserByName(@Param("name") String name);

    @Query(value = "SELECT * FROM user WHERE role LIKE 'ROLE_USER'", nativeQuery = true)
    public List<User> getAllUserMembers();
}
