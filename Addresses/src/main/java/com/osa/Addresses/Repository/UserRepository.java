package com.osa.Addresses.Repository;

import com.osa.Addresses.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
