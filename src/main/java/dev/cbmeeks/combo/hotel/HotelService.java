package dev.cbmeeks.combo.hotel;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * This service is just for demonstration.
 * We use this to query our sample data.
 * Obviously, you would use your own service and repositories in a real project.
 *
 * @author cbmeeks
 */
@Service
public class HotelService {

    private final HotelRepository repository;

    public HotelService(HotelRepository repository) {
        this.repository = repository;
    }

    public Page<Hotel> search(String name, Pageable pageable) {
        return repository.findByNameLike(name, pageable);
    }

    public Hotel findById(Integer id) {
        if (id == null || id < 1) return null;

        return repository.findById(id).orElseThrow(() -> new RuntimeException("Hotel not found"));
    }
}
