package com.recordpoint;

import io.micronaut.configuration.picocli.PicocliRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;

@CommandLine.Command(
        name = "example", subcommands = {
        ImportCommand.class, NotificationCommand.class, AuditCommand.class
}
)
public class Application implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        PicocliRunner.run(Application.class, args);
    }


    @Override
    public void run() {
        LOG.error("Subcommand is missing");
    }
}
