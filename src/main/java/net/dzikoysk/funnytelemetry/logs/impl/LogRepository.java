package net.dzikoysk.funnytelemetry.logs.impl;

import net.dzikoysk.funnytelemetry.logs.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<Log, Integer>
{
    Page<Log> findAllByOrderByDateDescIdDesc(Pageable pageable);

    Page<Log> findAllByIpOrderByDateDescIdDesc(Pageable pageable, String ip);

    Page<Log> findAllByUserOrderByDateDescIdDesc(Pageable pageable, String user);

    Page<Log> findAllByIpAndUserOrderByDateDescIdDesc(Pageable pageable, String ip, String user);
}
