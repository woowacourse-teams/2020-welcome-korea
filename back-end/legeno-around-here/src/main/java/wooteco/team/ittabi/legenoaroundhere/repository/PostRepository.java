package wooteco.team.ittabi.legenoaroundhere.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wooteco.team.ittabi.legenoaroundhere.domain.post.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findAllBy(Pageable pageable);

    @Query(value = ""
        + "SELECT * "
        + "  FROM post "
        + " WHERE deleted_at IS NULL "
        + "   AND area_id IN (SELECT id "
        + "                     FROM area "
        + "                    WHERE deleted_at IS NULL "
        + "                      AND full_name LIKE (SELECT CONCAT(full_name,'%') "
        + "                                            FROM area "
        + "                                           WHERE deleted_at IS NULL "
        + "                                             AND id = :areaId))", nativeQuery = true)
    Page<Post> findAllByAreaId(Pageable pageable, @Param("areaId") Long areaId);

    @Query("SELECT p FROM Post p WHERE p.sector.id IN :sectorIds")
    Page<Post> findAllBySectorIds(Pageable pageable, @Param("sectorIds") List<Long> sectorIds);

    @Query(value = ""
        + "SELECT * "
        + "  FROM post "
        + " WHERE deleted_at IS NULL "
        + "   AND area_id IN (SELECT id "
        + "                     FROM area "
        + "                    WHERE deleted_at IS NULL "
        + "                      AND full_name LIKE (SELECT CONCAT(full_name,'%') "
        + "                                            FROM area "
        + "                                           WHERE deleted_at IS NULL "
        + "                                             AND id = :areaId)) "
        + "   AND sector_id IN (SELECT id "
        + "                       FROM sector "
        + "                      WHERE deleted_at IS NULL "
        + "                        AND id IN :sectorIds)", nativeQuery = true)
    Page<Post> findAllByAreaIdsAndSectorIds(Pageable pageable, @Param("areaId") Long areaId,
        @Param("sectorIds") List<Long> sectorIds);

    @Query(value = "SELECT * FROM post", nativeQuery = true)
    Page<Post> findRankingBy(Pageable pageable, @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate);

    @Query(value = "SELECT * FROM post", nativeQuery = true)
    Page<Post> findRankingByAreaId(Pageable pageable, @Param("areaId") Long areaId,
        @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query(value = "SELECT * FROM post", nativeQuery = true)
    Page<Post> findRankingBySectorIds(Pageable pageable, @Param("sectorIds") List<Long> sectorIds,
        @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query(value = "SELECT * FROM post", nativeQuery = true)
    Page<Post> findRankingByAreaIdAndSectorIds(Pageable pageable, @Param("areaId") Long areaId,
        @Param("sectorIds") List<Long> sectorIds, @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate);
}
