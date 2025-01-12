package lh.h.interfaces;

import lh.h.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BoardService {

    List<Board> findAll();

    Board findById(Long id);

    void save(Board board);

    void deleteById(Long id);

    @Transactional(readOnly = true)
    Page<Board> boardList(Pageable pageable);

    @Transactional(readOnly = true)
    Page<Board> boardSearchList(String searchKeyword, Pageable pageable);
}
