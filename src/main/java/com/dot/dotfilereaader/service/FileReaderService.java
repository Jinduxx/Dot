package com.dot.dotfilereaader.service;

import com.dot.dotfilereaader.model.BlockedIpTable;
import com.dot.dotfilereaader.model.UserAccessLog;
import com.dot.dotfilereaader.repo.BlockedIpTableRepository;
import com.dot.dotfilereaader.repo.IpCount;
import com.dot.dotfilereaader.repo.UserAccessLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Slf4j
public class FileReaderService {

    final Logger logger = LoggerFactory.getLogger(FileReaderService.class);

    private final UserAccessLogRepository requestRepository;
    private final BlockedIpTableRepository blockedIpTableRepository;

    public FileReaderService(UserAccessLogRepository requestRepository, BlockedIpTableRepository blockedIpTableRepository) {
        this.requestRepository = requestRepository;
        this.blockedIpTableRepository = blockedIpTableRepository;
    }

    public void readFile(final String path){
        // Read the log file and load it into a list of log entries
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                final String[] parts = line.split("\\|"); // Split the line into parts
                final UserAccessLog logEntry = UserAccessLog.builder()
                        .ipAddress(parts[1])
                        .date(convertToTimeStamp(parts[0].substring(0, parts[0].lastIndexOf('.')).replace(' ', '.')))
                        .request(parts[2])
                        .status(parts[3])
                        .userAgent(parts[4]).build();
                try{
                    requestRepository.save(logEntry);
                } catch (Exception e) {
                    logger.error("An error occurred while trying to save data : "+e.getMessage());
                }

            }
        } catch (IOException e) {
            logger.error("Exception occurred : {}",e.getMessage());
        }
        logger.info("file successfully saved to database");
    }

    /**
     * This method checks if an IP address makes a number of request for
     * a specified period of time
     */
    public void requestLimitChecker(final String accessFile, final String startTime, final String duration, final String limit){
        if(accessFile == null || startTime == null || duration == null || limit == null){
            logger.error("missing required commandLine argument");
            System.exit(1);
        }
        final Timestamp startDate = convertToTimeStamp(startTime);
        Timestamp endDate = null;
        if ("daily".equalsIgnoreCase(duration)){
            endDate = Timestamp.from(startDate.toInstant().plus(1, ChronoUnit.DAYS));
        } else if ("hourly".equalsIgnoreCase(duration)){
            endDate = Timestamp.from(startDate.toInstant().plus(1, ChronoUnit.HOURS));
        } else {
            logger.error("Invalid argument for parameter duration : {}", duration);
            System.exit(1);
        }
        readFile(accessFile);
        List<IpCount> byDateBetween = null;
        try{
            byDateBetween = requestRepository.findByDateBetween(startDate, endDate, Integer.parseInt(limit));
        } catch (Exception e) {
            logger.error("An error occurred while trying to retrieve data : "+e.getMessage());
        }

        for(IpCount record: byDateBetween){
            BlockedIpTable blockedIpTable = BlockedIpTable.builder()
                    .ip(record.getIp_address())
                    .requestNumber(Integer.parseInt(String.valueOf(record.getCount())))
                    .comment("Blocked Because exceeds limit").build();

            try{
                blockedIpTableRepository.save(blockedIpTable);
            } catch (Exception e) {
                logger.error("An error occurred while trying to save data : "+e.getMessage());
            }
            logger.info("Blocked Ip = {}",record.getIp_address());
        }
    }


    public Timestamp convertToTimeStamp(final String dateString){
        if(dateString.isBlank()){
            logger.error("Invalid date String passed, {}",dateString);
            System.exit(1);
        }

        final LocalDateTime localDateTime = LocalDateTime.parse(dateString.trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd.HH:mm:ss"));
        return Timestamp.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

}
