package com.dot.dotfilereaader.repo;

import com.dot.dotfilereaader.model.BlockedIpTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockedIpTableRepository extends JpaRepository<BlockedIpTable, Long> {
}
