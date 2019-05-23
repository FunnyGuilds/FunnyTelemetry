package net.dzikoysk.funnytelemetry.logs.impl;

import java.util.Collection;
import java.util.Date;
import java.util.UUID;

import net.dzikoysk.funnytelemetry.logs.ActionType;
import net.dzikoysk.funnytelemetry.logs.Log;
import net.dzikoysk.funnytelemetry.logs.LogDetailResolver;
import net.dzikoysk.funnytelemetry.logs.LogService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class LogServiceImpl implements LogService
{
    private final LogRepository                 logRepository;
    private final Collection<LogDetailResolver> detailResolvers;

    public LogServiceImpl(final LogRepository logRepository, final Collection<LogDetailResolver> detailResolvers)
    {
        this.logRepository = logRepository;
        this.detailResolvers = detailResolvers;
    }

    @Override
    public void submitLog(final ActionType type, final String details, final String username, final String ip)
    {
        this.logRepository.save(new Log(
            UUID.randomUUID(),
            username,
            type,
            details,
            ip,
            new Date()
        ));
    }

    @Override
    public Collection<Log> getAllLogs()
    {
        return this.logRepository.findAll();
    }

    @Override
    public Page<Log> getLogs(final Pageable pageable, final String ip, final String username)
    {
        if (ip == null && username == null)
        {
            return this.logRepository.findAllByOrderByDateDescIdDesc(pageable);
        }
        else if (ip == null)
        {
            return this.logRepository.findAllByUserOrderByDateDescIdDesc(pageable, username);
        }
        else if (username == null)
        {
            return this.logRepository.findAllByIpOrderByDateDescIdDesc(pageable, ip);
        }
        else
        {
            return this.logRepository.findAllByIpAndUserOrderByDateDescIdDesc(pageable, ip, username);
        }
    }

    @Override
    public String resolveLogDetails(final ActionType actionType, final String details)
    {
        return this.detailResolvers
            .stream()
            .filter(resolver -> resolver.canResolve(actionType))
            .findFirst()
            .map(resolver ->resolver.resolve(details))
            .orElse("No details");
    }
}
