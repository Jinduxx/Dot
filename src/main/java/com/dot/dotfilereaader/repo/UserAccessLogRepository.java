package com.dot.dotfilereaader.repo;

import com.dot.dotfilereaader.model.UserAccessLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface UserAccessLogRepository extends JpaRepository<UserAccessLog, Long> {

    @Query(nativeQuery = true, value ="SELECT ip_address, COUNT(*) FROM user_access_log WHERE date_time BETWEEN ?1 AND ?2 GROUP BY ip_address HAVING COUNT(*) > ?3")
    List<IpCount> findByDateBetween(Timestamp startTime, Timestamp endDate, int limit);

}
