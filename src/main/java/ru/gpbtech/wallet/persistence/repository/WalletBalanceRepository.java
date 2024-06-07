package ru.gpbtech.wallet.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.gpbtech.wallet.persistence.entity.WalletBalance;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * Репозиторий для работы с сущностью WalletBalance.
 * Этот интерфейс предоставляет методы для выполнения операций CRUD с сущностью WalletBalance
 * Репозиторий использует Spring Data JPA для автоматической генерации реализаций методов на
 * основе предоставленных аннотаций и имен методов.
 */
public interface WalletBalanceRepository extends JpaRepository<WalletBalance, UUID> {
    
    /**
     * Поиск баланса кошелька по идентификатору клиента и диапазону дат.
     *
     * <p>Этот метод выполняет запрос в базу данных для получения записи WalletBalance,
     * соответствующей заданному идентификатору клиента и диапазону дат. Если запись
     * найдена, она возвращается в виде Optional. В противном случае возвращается пустой Optional.</p>
     *
     * @param clientId идентификатор клиента
     * @param dateFrom начальная дата для диапазона поиска
     * @param dateTo   конечная дата для диапазона поиска
     *
     * @return Optional, содержащий найденную запись WalletBalance, или пустой Optional, если запись не найдена
     */
    @Query("SELECT wb FROM WalletBalance wb WHERE wb.clientId = :clientId AND wb.lastUpdated BETWEEN :dateFrom AND :dateTo")
    Optional<WalletBalance> findBalance(@Param("clientId") String clientId,
            @Param("dateFrom") LocalDateTime dateFrom,
            @Param("dateTo") LocalDateTime dateTo);
}
