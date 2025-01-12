package lh.h.interfaces;

import lh.h.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardService {

    List<Board> findAll();

    Board findById(Long id);

    void save(Board board);

    void deleteById(Long id);

    Page<Board> boardList(String searchKeyword, Pageable pageable);


    Page<Board> getAllBoards(Pageable pageable);
}
