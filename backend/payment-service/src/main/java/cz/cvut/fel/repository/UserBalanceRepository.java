package cz.cvut.fel.repository;

import cz.cvut.fel.entity.UserBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserBalanceRepository extends JpaRepository<UserBalance,Integer> {
    Optional<UserBalance> findByUserId(String userId);
}
