package lh.h.service;

import lh.h.entity.Board;
import lh.h.interfaces.BoardService;
import lh.h.repository.BoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    public BoardServiceImpl(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    /* 모든 게시글 조회 */
    @Override
    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    /* ID로 게시글 조회 */
    @Override
    public Board findById(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
    }

    /* 게시글 저장 */
    @Override
    public void save(Board board) {
        boardRepository.save(board);
    }

    /* 게시글 삭제 */
    @Transactional
    @Override
    public void deleteById(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시판이 없습니다: " + id));
        boardRepository.delete(board);
    }

    @Override
    public Page<Board> boardList(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    /* Paging board list*/
    @Override
    public Page<Board> boardSearchList(String searchKeyword, Pageable pageable) {
        if (searchKeyword == null || searchKeyword.isBlank()) {
            return boardRepository.findAll(pageable); // 전체 목록 반환
        }
        return boardRepository.findByTitleContaining(searchKeyword, pageable);
    }


//    /* Board Search */
//    @Override
//    public Page<Board> searchBoard(String searchKeyword, Pageable pageable) {
//        return boardRepository.findByTitle(searchKeyword, pageable);
//    }
}
