package ru.gpbtech.wallet.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.OffsetDateTime;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class VerifiedWalletBalanceTest {
    
    private static Stream<Arguments> provideInvalidRequests() {
        GetWalletBalanceRequest nullClientId = new GetWalletBalanceRequest();
        nullClientId.setClientId(null);
        nullClientId.setDateFrom(OffsetDateTime.now());
        nullClientId.setDateTo(OffsetDateTime.now().plusHours(1));
        
        GetWalletBalanceRequest nullDateForm = new GetWalletBalanceRequest();
        nullDateForm.setClientId(UUID.randomUUID());
        nullDateForm.setDateFrom(null);
        nullDateForm.setDateTo(OffsetDateTime.now().plusHours(1));
        
        GetWalletBalanceRequest nullDateTo = new GetWalletBalanceRequest();
        nullDateTo.setClientId(UUID.randomUUID());
        nullDateTo.setDateFrom(OffsetDateTime.now());
        nullDateTo.setDateTo(null);
        
        GetWalletBalanceRequest incorrectDate = new GetWalletBalanceRequest();
        incorrectDate.setClientId(UUID.randomUUID());
        incorrectDate.setDateFrom(OffsetDateTime.now().plusHours(1));
        incorrectDate.setDateTo(OffsetDateTime.now());
        
        return Stream.of(
                Arguments.of(nullClientId, NoSuchElementException.class, "В запросе не найден валидный clientId"),
                Arguments.of(nullDateForm, NoSuchElementException.class, "В запросе не найден валидный dateFrom"),
                Arguments.of(nullDateTo, NoSuchElementException.class, "В запросе не найден валидный dateTo"),
                Arguments.of(incorrectDate, IllegalArgumentException.class, "Начальная дата не может быть больше конечной даты"));
    }
    
    @ParameterizedTest
    @MethodSource("provideInvalidRequests")
    void testCreateThrowsException(GetWalletBalanceRequest request, Class<? extends Throwable> expectedException, String errorMessage) {
        assertThatThrownBy(() -> VerifiedWalletBalance.create(request))
                .isInstanceOf(expectedException)
                .hasMessage(errorMessage);
    }
    
    @Test
    void successCreation() {
        GetWalletBalanceRequest request = new GetWalletBalanceRequest();
        request.setClientId(UUID.randomUUID());
        request.setDateFrom(OffsetDateTime.now());
        request.setDateTo(OffsetDateTime.now().plusHours(1));
        
        assertThatCode(() -> VerifiedWalletBalance.create(request))
                .doesNotThrowAnyException();
    }
}