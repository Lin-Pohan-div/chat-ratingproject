package tw.rating.ratingproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tw.rating.ratingproject.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
