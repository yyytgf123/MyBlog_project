package lh.h.service;

import lh.h.entity.Board;
import lh.h.repository.BoardRepository;
import org.antlr.v4.runtime.RecognitionException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BoardService {
    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    public Board findById(Long id) {
        return boardRepository.findById(id).orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
    }

    public void save(Board board) {
        boardRepository.save(board);
    }

    @Transactional
    public void deleteById(Long id) {
        System.out.println("Fetching board with ID: " + id);
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시판이 없습니다: " + id));
        System.out.println("Deleting board with ID: " + board.getId());
        boardRepository.delete(board);
    }

}
