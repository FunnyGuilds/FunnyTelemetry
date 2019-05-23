package net.dzikoysk.funnytelemetry.ban.impl;

import java.util.Optional;
import java.util.UUID;

import net.dzikoysk.funnytelemetry.ban.Ban;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BanRepository extends JpaRepository<Ban, Integer>
{
    Optional<Ban> findByIpAndRevoked(String ip, boolean revoked);

    Optional<Ban> findByUniqueId(UUID uniqueId);

    Page<Ban> findAllByRevokedOrderByDateDescIdDesc(Pageable pageable, boolean revoked);

    Page<Ban> findAllByOrderByDateDescIdDesc(Pageable pageable);
}
