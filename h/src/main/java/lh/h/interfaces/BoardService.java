package lh.h.interfaces;

import lh.h.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BoardService {

    List<Board> findAll();

    Board findById(Long id);

    /* board update */
    void updateBoard(Long id, Board updatedBoard, MultipartFile file) throws IOException;

    /* form write(file update + write) */
    void saveFile(Board board, MultipartFile file) throws IOException;

    /* board delete */
    void deleteById(Long id);

    /* board list */
    @Transactional(readOnly = true)
    Page<Board> boardList(Pageable pageable);

    /* board search */
    @Transactional(readOnly = true)
    Page<Board> boardSearchList(String searchKeyword, Pageable pageable);

}
