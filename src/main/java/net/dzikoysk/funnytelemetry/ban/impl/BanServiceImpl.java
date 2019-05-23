package net.dzikoysk.funnytelemetry.ban.impl;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

import net.dzikoysk.funnytelemetry.ban.Ban;
import net.dzikoysk.funnytelemetry.ban.BanService;
import net.dzikoysk.funnytelemetry.ban.IpAddressIsBannedException;
import net.dzikoysk.funnytelemetry.ban.IpAddressIsInvalid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BanServiceImpl implements BanService
{
    private static final Pattern       IP_PATTERN = Pattern.compile("\\b(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\b");
    private final        BanRepository banRepository;

    @Autowired
    public BanServiceImpl(final BanRepository banRepository)
    {
        this.banRepository = banRepository;
    }

    @Override
    public boolean isBanned(final String userAddress)
    {
        return this.findBan(userAddress).isPresent();
    }

    @Override
    public Optional<Ban> findBan(final String userAddress)
    {
        return this.banRepository.findByIpAndRevoked(userAddress, false);
    }

    @Override
    public void ensureNotBanned(final String userAddress) throws IpAddressIsBannedException
    {
        if (this.isBanned(userAddress))
        {
            throw new IpAddressIsBannedException("IP address " + userAddress + " is banned.");
        }
    }

    @Override
    public Ban ban(final String userAddress, final String reason, final String issuer)
    {
        if (!IP_PATTERN.matcher(userAddress).matches())
        {
            throw new IpAddressIsInvalid("The provided IP address is invalid");
        }

        return this.banRepository.save(new Ban(
            UUID.randomUUID(),
            userAddress,
            issuer,
            reason,
            new Date(),
            false,
            null,
            null,
            null
        ));
    }

    @Override
    public void revokeBan(final Ban ban, final String reason, final String revoker)
    {
        ban.setRevoked(true);
        ban.setRevocationReason(reason);
        ban.setRevokedBy(revoker);
        ban.setRevocationDate(new Date());

        this.banRepository.save(ban);
    }

    @Override
    public Page<Ban> getAllActiveBans(final Pageable pageable)
    {
        return this.banRepository.findAllByRevokedOrderByDateDescIdDesc(pageable, false);
    }

    @Override
    public Page<Ban> getAllBans(final Pageable pageable)
    {
        return this.banRepository.findAllByOrderByDateDescIdDesc(pageable);
    }

    @Override
    public Optional<Ban> findBan(final UUID uuid)
    {
        return this.banRepository.findByUniqueId(uuid);
    }
}
