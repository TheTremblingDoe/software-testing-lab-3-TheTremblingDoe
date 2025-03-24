package org.itmo.testing.lab2.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserStatusServiceTest {

    private UserAnalyticsService userAnalyticsService;
    private UserStatusService userStatusService;

    @BeforeEach
    void setUp() {
        userAnalyticsService = mock(UserAnalyticsService.class);
        userStatusService = new UserStatusService(userAnalyticsService);
    }

    @Test
    public void testGetUserStatus_Active() {
        when(userAnalyticsService.getTotalActivityTime("user123")).thenReturn(90L);

        String status = userStatusService.getUserStatus("user123");

        assertEquals("Active", status);
        verify(userAnalyticsService, times(1)).getTotalActivityTime("user123");
    }

    @Test
    public void testGetUserStatus_HighlyActive() {
        when(userAnalyticsService.getTotalActivityTime("user123")).thenReturn(150L);

        String status = userStatusService.getUserStatus("user123");

        assertEquals("Highly active", status);
        verify(userAnalyticsService, times(1)).getTotalActivityTime("user123");
    }

    @Test
    public void testGetUserStatus_Inactive() {
        when(userAnalyticsService.getTotalActivityTime("user123")).thenReturn(30L);

        String status = userStatusService.getUserStatus("user123");

        assertEquals("Inactive", status);
        verify(userAnalyticsService, times(1)).getTotalActivityTime("user123");
    }

    /*@Test
    public void testGetUserLastSessionDate_NoSessions() {
        when(userAnalyticsService.getUserSessions("user123")).thenReturn(Arrays.asList());

        Optional<String> lastSessionDate = userStatusService.getUserLastSessionDate("user123");

        assertFalse(lastSessionDate.isPresent());
        verify(userAnalyticsService, times(2)).getUserSessions("user123");
    }*/

    @Test
    public void testGetUserLastSessionDate() {
        LocalDateTime logoutTime = LocalDateTime.now();
        UserAnalyticsService.Session session = new UserAnalyticsService.Session(logoutTime.minusHours(1), logoutTime);

        when(userAnalyticsService.getUserSessions("user123")).thenReturn(Arrays.asList(session));

        Optional<String> lastSessionDate = userStatusService.getUserLastSessionDate("user123");

        assertTrue(lastSessionDate.isPresent());
        assertEquals(logoutTime.toLocalDate().toString(), lastSessionDate.get());
        verify(userAnalyticsService, times(1)).getUserSessions("user123");
    }

}
