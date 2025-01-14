package lh.h.service;

import lh.h.entity.Board;
import lh.h.interfaces.BoardService;
import lh.h.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    private final BoardRepository boardRepository;

    private static final String UPLOAD_DIR = Paths.get(System.getProperty("user.dir"), "files").toString();


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

    /* form write(file upload + write) */
    /** file upload saveUrl**/
    @Override
    @Transactional
    public void saveFile(Board board, MultipartFile file) throws IOException {
        // 디렉토리 생성
        File saveDir = new File(UPLOAD_DIR);
        if (!saveDir.exists()) {
            saveDir.mkdirs(); // 디렉토리가 없으면 생성
        }

        // 파일 처리
        if (file != null && !file.isEmpty()) {
            String uniqueFilename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename(); // 고유한 파일명 생성
            String filePath = Paths.get(UPLOAD_DIR, uniqueFilename).toString(); // 전체 파일 경로 설정

            // 파일 저장
            File saveFile = new File(filePath);
            file.transferTo(saveFile); // MultipartFile을 지정 경로로 저장

            // Board 객체에 파일 정보 저장
            board.setFilename(uniqueFilename);
            board.setFilepath("/files/" + uniqueFilename); // URL로 접근 가능한 경로 설정
        }

        // 게시글 정보 저장
        boardRepository.save(board);
    }

    @Override
    public void updateBoard(Long id, Board updatedBoard, MultipartFile file) throws IOException {
        // 기존 게시글 찾기
        Board existingBoard = boardRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("게시글을 찾을 수 없습니다. ID: " + id)
        );

        // 기존 게시글 정보 업데이트
        existingBoard.setTitle(updatedBoard.getTitle());
        existingBoard.setContent(updatedBoard.getContent());
        existingBoard.setWriter(updatedBoard.getWriter());

        // 파일 처리
        if (file != null && !file.isEmpty()) {
            // 기존 파일 삭제
            if (existingBoard.getFilepath() != null) {
                File existingFile = new File(Paths.get(UPLOAD_DIR, existingBoard.getFilename()).toString());
                if (existingFile.exists()) {
                    existingFile.delete();
                }
            }

            // 디렉토리 생성
            File saveDir = new File(UPLOAD_DIR);
            if (!saveDir.exists()) {
                saveDir.mkdirs();
            }

            // 새 파일 저장
            String uniqueFilename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename(); // 고유 파일명 생성
            String filePath = Paths.get(UPLOAD_DIR, uniqueFilename).toString(); // 전체 경로 생성
            File saveFile = new File(filePath);
            file.transferTo(saveFile); // 파일 저장

            // 업데이트된 파일 정보 저장
            existingBoard.setFilename(uniqueFilename);
            existingBoard.setFilepath("/files/" + uniqueFilename); // URL 경로 설정
        }

        // 변경 사항 저장
        boardRepository.save(existingBoard);
    }

    /* board delete */
    @Transactional
    @Override
    public void deleteById(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시판이 없습니다: " + id));
        boardRepository.delete(board);
    }

    /* board list*/
    @Override
    public Page<Board> boardList(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    /* board search */
    @Override
    public Page<Board> boardSearchList(String searchKeyword, Pageable pageable) {
        if (searchKeyword == null || searchKeyword.isBlank()) {
            return boardRepository.findAll(pageable); // 전체 목록 반환
        }
        return boardRepository.findByTitleContaining(searchKeyword, pageable);
    }
}
