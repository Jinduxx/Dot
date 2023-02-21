//package com.dot.dotfilereaader;
//
//import com.dot.dotfilereaader.model.UserAccessLog;
//import com.dot.dotfilereaader.repo.IpCount;
//import com.dot.dotfilereaader.repo.UserAccessLogRepository;
//import com.dot.dotfilereaader.service.FileReaderService;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.sql.Timestamp;
//import java.time.temporal.ChronoUnit;
//import java.util.ArrayList;
//import java.util.List;
//
//@SpringBootTest
//class DotFileReaderApplicationTests {
//
//    @Autowired
//    private UserAccessLogRepository userAccessLogRepository;
//
//    @Autowired
//    private FileReaderService requestService;
//
//    private Timestamp start;
//    private Timestamp end;
//    private int limit;
//
//    @BeforeEach
//    public void setUp() {
//        String ip1 = "192.168.234.82";
//        String ip2 = "192.168.169.194";
//
//        UserAccessLog request1 = new UserAccessLog();
//        request1.setIpAddress(ip1);
//        request1.setDate(requestService.convertToTimeStamp("2022-01-01.00:00:11"));
//        request1.setRequest("GET / HTTP/1.1");
//        request1.setStatus("200");
//        request1.setUserAgent("swcd (unknown version) CFNetwork/808.2.16 Darwin/15.6.0");
//
//        UserAccessLog request2 = new UserAccessLog();
//        request2.setIpAddress(ip1);
//        request2.setDate(requestService.convertToTimeStamp("2022-01-01.00:00:21"));
//        request2.setRequest("GET / HTTP/1.1");
//        request2.setStatus("200");
//        request2.setUserAgent("swcd (unknown version) CFNetwork/808.2.16 Darwin/15.6.0");
//
//        UserAccessLog request3 = new UserAccessLog();
//        request3.setIpAddress(ip2);
//        request3.setDate(requestService.convertToTimeStamp("2022-01-01.00:00:23"));
//        request3.setRequest("GET / HTTP/1.1");
//        request3.setStatus("200");
//        request3.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) Chrome/51.0.2704.79 Safari/537.36 Edge/14.14393");
//
//        UserAccessLog request4 = new UserAccessLog();
//        request4.setIpAddress(ip1);
//        request4.setDate(requestService.convertToTimeStamp("2022-01-01.00:00:40"));
//        request4.setRequest("GET / HTTP/1.1");
//        request4.setStatus("200");
//        request4.setUserAgent("swcd (unknown version) CFNetwork/808.2.16 Darwin/15.6.0");
//
//        UserAccessLog request5 = new UserAccessLog();
//        request5.setIpAddress(ip2);
//        request5.setDate(requestService.convertToTimeStamp("2022-01-01.00:00:54"));
//        request5.setRequest("GET / HTTP/1.1");
//        request5.setStatus("200");
//        request5.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) Chrome/51.0.2704.79 Safari/537.36 Edge/14.14393");
//
//
//        userAccessLogRepository.save(request1);
//        userAccessLogRepository.save(request2);
//        userAccessLogRepository.save(request3);
//        userAccessLogRepository.save(request4);
//        userAccessLogRepository.save(request5);
//    }
//
//    @Test
//    void testProcessRequests_hourlyLimitExceeded() {
//        start = requestService.convertToTimeStamp("2022-01-01.00:00:11");
//        end = Timestamp.from(start.toInstant().plus(1, ChronoUnit.HOURS));
//        limit = 2;
//        List<IpCount> byDateBetween = userAccessLogRepository.findByDateBetween(start, end, limit);
//        List<Object> objects = new ArrayList<>();
//        for(IpCount ipCount: byDateBetween){
//            objects.add(ipCount.getIp_address());
//        }
//        Object ipString1 = "192.168.234.82";
//        Object ipString2 = "192.168.169.194";
//        Assertions.assertTrue(objects.contains(ipString1));
//        Assertions.assertFalse(objects.contains(ipString2));
//
//    }
//
//    @Test
//    void testProcessRequests_dailyLimitExceeded() {
//        start = requestService.convertToTimeStamp("2022-01-01.00:00:11");
//        end = Timestamp.from(start.toInstant().plus(1, ChronoUnit.DAYS));
//        limit = 2;
//        List<IpCount> byDateBetween = userAccessLogRepository.findByDateBetween(start, end, limit);
//        List<Object> objects = new ArrayList<>();
//        for(IpCount ipCount: byDateBetween){
//            objects.add(ipCount.getIp_address());
//        }
//        Object ipString1 = "192.168.234.82";
//        Object ipString2 = "192.168.169.194";
//        Assertions.assertTrue(objects.contains(ipString1));
//        Assertions.assertTrue(objects.contains(ipString2));
//    }
//
//}
