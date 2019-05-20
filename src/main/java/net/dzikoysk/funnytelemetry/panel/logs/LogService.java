package net.dzikoysk.funnytelemetry.panel.logs;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;

public interface LogService
{
    void submitLog(ActionType type, String details, String username, String ip);

    Collection<Log> getAllLogs();

    Page<Log> getLogs(Pageable pageable, @Nullable String ip, @Nullable String username);

    String resolveLogDetails(final ActionType actionType, String details);
}