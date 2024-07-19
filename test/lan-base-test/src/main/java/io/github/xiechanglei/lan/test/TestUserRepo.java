package io.github.xiechanglei.lan.test;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestUserRepo extends JpaRepository<TestUser, String> {
}
