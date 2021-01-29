package org.imishinist.coupler;

import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.connect.errors.ConnectException;
import org.apache.kafka.connect.sink.SinkRecord;
import org.apache.kafka.connect.sink.SinkTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collection;
import java.util.Map;

public class FileStreamSinkTask extends SinkTask {
    private static final Logger log = LoggerFactory.getLogger(FileStreamSinkTask.class);

    private String filename;
    private PrintStream outputStream;

    public FileStreamSinkTask() {
    }

    @Override
    public String version() {
        return new FileStreamSinkConnector().version();
    }

    @Override
    public void start(Map<String, String> props) {
        filename = props.get(FileStreamSinkConnector.FILE_CONFIG);
        if (filename == null) {
            outputStream = System.out;
        } else {
            try {
                outputStream = new PrintStream(
                        Files.newOutputStream(Paths.get(filename), StandardOpenOption.CREATE, StandardOpenOption.APPEND),
                        false,
                        StandardCharsets.UTF_8.name()
                );
            } catch (IOException e) {
                throw new ConnectException("couldn't find or create file '" + filename + "' for FileStreamSinkTask", e);
            }
        }
    }

    @Override
    public void put(Collection<SinkRecord> records) {
        for (SinkRecord record : records) {
            log.trace("writing line to {}: {}", logFilename(), record.value());
            outputStream.println(record.value());
        }
    }

    public void flush(Map<TopicPartition, OffsetAndMetadata> offsets) {
        log.trace("flushing output stream for {}", logFilename());
        outputStream.flush();
    }

    @Override
    public void stop() {
        if (outputStream != null && outputStream != System.out)
            outputStream.close();
    }

    private String logFilename() {
        return filename == null ? "stdout" : filename;
    }
}
