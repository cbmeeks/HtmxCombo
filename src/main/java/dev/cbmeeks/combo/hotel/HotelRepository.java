package dev.cbmeeks.combo.hotel;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface HotelRepository
        extends CrudRepository<Hotel, Integer>, PagingAndSortingRepository<Hotel, Integer> {

    @Query("select h from Hotel h where lower(h.name) like lower(concat('%',:name,'%')) order by h.name ")
    Page<Hotel> findByNameLike(String name, Pageable pageable);

}
