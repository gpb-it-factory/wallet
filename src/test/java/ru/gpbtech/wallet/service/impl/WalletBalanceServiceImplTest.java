package ru.gpbtech.wallet.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.gpbtech.wallet.model.GetWalletBalanceRequest;
import ru.gpbtech.wallet.model.GetWalletBalanceResponse;
import ru.gpbtech.wallet.persistence.entity.WalletBalance;
import ru.gpbtech.wallet.persistence.repository.WalletBalanceRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Пример unit-теста
 */
@ExtendWith(MockitoExtension.class)
class WalletBalanceServiceImplTest {
    
    @Mock
    private WalletBalanceRepository walletBalanceRepository;
    
    @InjectMocks
    private WalletBalanceServiceImpl walletBalanceService;
    
    @Test
    void testGetWalletBalanceSuccess() {
        GetWalletBalanceRequest request = new GetWalletBalanceRequest();
        request.setClientId(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"));
        request.setDateFrom(OffsetDateTime.parse("2024-06-01T12:00:00Z"));
        request.setDateTo(OffsetDateTime.parse("2024-06-01T13:00:00Z"));
        
        WalletBalance walletBalance = new WalletBalance();
        walletBalance.setBalance(BigDecimal.valueOf(100.00));
        walletBalance.setCurrency("RUB");
        walletBalance.setLastUpdated(LocalDateTime.now());
        
        when(walletBalanceRepository.findBalance(any(), any(), any()))
                .thenReturn(Optional.of(walletBalance));
        
        GetWalletBalanceResponse balance = walletBalanceService.getWalletBalance(request);
        
        assertThat(balance)
                .isNotNull()
                .hasFieldOrPropertyWithValue("balance", BigDecimal.valueOf(100.00))
                .hasFieldOrPropertyWithValue("currency", "RUB");
    }
    
    @Test
    void testGetWalletBalanceErrorByEmptyDataBase() {
        GetWalletBalanceRequest request = new GetWalletBalanceRequest();
        request.setClientId(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"));
        request.setDateFrom(OffsetDateTime.parse("2024-06-01T12:00:00Z"));
        request.setDateTo(OffsetDateTime.parse("2024-06-01T13:00:00Z"));
        
        when(walletBalanceRepository.findBalance(any(), any(), any()))
                .thenReturn(Optional.empty());
        
        assertThatThrownBy(() -> walletBalanceService.getWalletBalance(request))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("В результате работы сервиса был получен пустой ответ");
    }
    
    @Test
    void testGetWalletBalanceErrorByCreation() {
        GetWalletBalanceRequest request = new GetWalletBalanceRequest();
        
        assertThatThrownBy(() -> walletBalanceService.getWalletBalance(request))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("В запросе не найден валидный clientId");
        
        verify(walletBalanceRepository, never()).findBalance(any(), any(), any());
    }
}