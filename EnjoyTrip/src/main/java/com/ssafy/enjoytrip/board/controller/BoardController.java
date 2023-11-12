package com.ssafy.enjoytrip.board.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrip.board.model.Board;
import com.ssafy.enjoytrip.board.model.service.BoardSerivce;

@CrossOrigin("*")
@RestController
@RequestMapping("/board")
public class BoardController {
	private final BoardSerivce boardSerivce;
	public BoardController(BoardSerivce boardSerivce) {
		this.boardSerivce = boardSerivce;
	}
	
//	@Override
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		String action = request.getParameter("action");
//		if("list".equals(action)) {
//			list(request, response);
//		}else if ("mvwrite".equals(action)) {
//			forward(request, response, "/board/write.jsp");
//		}else if("write".equals(action)) {
//			write(request, response);
//		}else if("view".equals(action)) {
//			view(request, response);
//		}else if("mvmodify".equals(action)) {
//			forward(request, response, "/board/modify.jsp");
//		}else if("modify".equals(action)) {
//			modify(request, response);
//		}else if("delete".equals(action)) {
//			delete(request, response);
//		}
//	}
	

	
//	@Override
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		request.setCharacterEncoding("utf-8");
//		doGet(request, response);
//	}
//
//	
//	private void forward(HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException {
//		RequestDispatcher rd = request.getRequestDispatcher(path);
//		rd.forward(request, response);
//	}
//	
//	private void redirect(HttpServletRequest request, HttpServletResponse response, String path) throws IOException {
//		response.sendRedirect(request.getContextPath() + path);
//	}

	@GetMapping
	public ResponseEntity<List<Board>> list() {
		return ResponseEntity.ok(boardSerivce.list()); //201 레고
	}
//	public void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		Page page = new Page(
//				request.getParameter("pageNo") == null
//				? 1
//						: Integer.parseInt(request.getParameter("pageNo"))
//				);
//		try {
//			request.setAttribute("result", boardSerivce.listBoard1(page));
//			request.getRequestDispatcher("/board/list.jsp").forward(request, response);
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new ServletException(e);
//		}
//	}
	
//	private void list(HttpServletRequest request, HttpServletResponse response)  {
//		try {
//			HashMap<String, String> params = new HashMap<>();
//			params.put("key", request.getParameter("key"));
//			params.put("word", request.getParameter("word"));
//			
//			List<Board> articles = boardSerivce.list(params);
//			
//			request.setAttribute("articles", articles);
//			forward(request, response, "/board/list.jsp");
//		} catch (Exception e) {
//			e.printStackTrace();
//		} 
//	}

	
	@PostMapping
	public ResponseEntity<String> write(@RequestBody Board board) {
		boardSerivce.write(board);
		return ResponseEntity.status(HttpStatus.CREATED).build(); //201 레고
	}
//	private void write(HttpServletRequest request, HttpServletResponse response) {
//		HttpSession session = request.getSession();
//		Member memberInfo = (Member)session.getAttribute("memberInfo");
//		try { 
//			if(memberInfo != null) {
//				Board board = new Board();
////				board.setUserId(memberInfo.getUserId());
//				board.setUserId(memberInfo.getUserId());
//				board.setSubject(request.getParameter("subject"));
//				board.setContent(request.getParameter("content"));
//				
//				boardSerivce.write(board);
//				redirect(request, response, "/board?action=list");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	

	@GetMapping("{no}")
	public ResponseEntity<Board> view(@PathVariable int no) {
		return ResponseEntity.ok(boardSerivce.view(no));
	}
//	private void view(HttpServletRequest request, HttpServletResponse response) {
//		try {
//			int articleNo = Integer.parseInt(request.getParameter("articleno"));
//			Board board = new Board();
//			board = boardSerivce.view(articleNo);
//			request.setAttribute("article", board);
//			request.getSession().setAttribute("article", board);
//			forward(request, response, "/board/view.jsp");
//		} catch (Exception e) {
//			e.printStackTrace();
//		} 
//		
//	}
	
//	@PutMapping("{no}")
//	public ResponseEntity<String> modify(@PathVariable int no, @RequestBody Board board) {
//		board.setNo(no);
//		boardSerivce.modify(board);
//		return ResponseEntity.ok("OK");
//		
//	}
//	private void modify(HttpServletRequest request, HttpServletResponse response) {
//		try {
//			Board board = new Board();
//			board.setArticleNo(Integer.parseInt(request.getParameter("articleno")));
//			board.setSubject(request.getParameter("subject"));
//			board.setContent(request.getParameter("content"));
//			boardSerivce.modify(board);
//			forward(request, response, "/board?action=view&articleno=" + board.getArticleNo());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//	}
	
	@DeleteMapping("{no}")
	public ResponseEntity<String> delete(@PathVariable int no) {
		boardSerivce.delete(no);
		return ResponseEntity.ok("OK");// 201레고
	}
//	private void delete(HttpServletRequest request, HttpServletResponse response) {
//		try {
//			boardSerivce.delete(Integer.parseInt(request.getParameter("articleno")));
//			redirect(request, response, "/board?action=list");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//	}
}

