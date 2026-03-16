package ec.com.api.jwt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ec.com.api.jwt.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    /**
     * Find user by username.
     * 
     * @param username
     * @return
     */
    Optional<UserEntity> findByUsername(String username);

    /**
     * Find user by email.
     *
     * @param email
     * @return
     */
    Optional<UserEntity> findByEmail(String email);
}
