package net.dzikoysk.funnytelemetry.ban;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BanService
{
    boolean isBanned(String userAddress);

    Optional<Ban> findBan(final String userAddress);

    Optional<Ban> findBan(UUID uuid);

    void ensureNotBanned(String userAddress) throws IpAddressIsBannedException;

    Ban ban(String userAddress, String reason, String issuer);

    void revokeBan(Ban ban, String reason, String revoker);

    Page<Ban> getAllActiveBans(Pageable pageable);

    Page<Ban> getAllBans(Pageable pageable);
}
