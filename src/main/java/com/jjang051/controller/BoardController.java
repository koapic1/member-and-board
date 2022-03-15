package com.jjang051.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jjang051.model.MemberDto;
import com.jjang051.model.ReplyBoardDao;
import com.jjang051.model.ReplyBoardDto;
import com.jjang051.model.ReplyBoardService;
import com.jjang051.util.PageWriter;
import com.jjang051.util.ScriptWriter;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	
	@Autowired
	ReplyBoardService replyBoardDao;
	
	@Autowired
	ReplyBoardDto replyBoardDto;
	
	@Autowired
	ReplyBoardDto prevBoardDto;
	
	@Autowired
	ReplyBoardDto nextBoardDto;
	
	
	@GetMapping("/List.do")
	public String list(HttpServletRequest request, Model model) {
		String tempClickPage = request.getParameter("clickPage");
		String search_select = request.getParameter("search_select");
		String search_word = request.getParameter("search_word");
		if(tempClickPage==null) tempClickPage = "1";
		int clickPage = Integer.parseInt(tempClickPage);
		int totalPage =  replyBoardDao.getTotal(search_select, search_word);
		int listPerPage = 5; // table의 줄 수
		int pageBlock = 3; // 아래쪽 page number의 한번에 뿌려지는 갯수
		
		int start = (clickPage - 1)*listPerPage+1;
		int end = start+listPerPage - 1;
		
		List<ReplyBoardDto> boardList = replyBoardDao.getAllList(start, end, search_select, search_word);
		
		String page = 
		PageWriter.pageWrite(totalPage, listPerPage, pageBlock, 
		clickPage, "../board/List.do");
		
		model.addAttribute("boardList",boardList);
		model.addAttribute("page",page);
		model.addAttribute("totalPage",totalPage);
		model.addAttribute("listPerPage",listPerPage);
		model.addAttribute("pageBlock",pageBlock);
		model.addAttribute("clickPage",clickPage);
		
		return "board/list";
	}
	@GetMapping("/Write.do")
	public String write() {
		//섬머노트 처리....
		return "board/write";
	}
	
	@RequestMapping("/WriteProcess.do")
	public void writeProcess(ReplyBoardDto replyBoardDto, HttpServletResponse response, HttpServletRequest request, HttpSession session) throws IOException {
		replyBoardDto.setId((String)session.getAttribute("loggedId"));
		replyBoardDto.setReGroup(replyBoardDao.getMaxRegroup() + 1);
		replyBoardDto.setReLevel(1);
		replyBoardDto.setReStep(1); 
		
		int result = replyBoardDao.insertBoard(replyBoardDto);
		if(result > 0) {
			ScriptWriter.alertAndNext(response, "글이 등록되었습니다.", "../board/List.do");
		} else {
			ScriptWriter.alertAndBack(response, "시스템 오류 입니다. 잠시 후 다시 시도해 주세요.");
		}
	}
	
	@GetMapping("/ReplyWrite.do")
	public String replyWrite() {
		return "board/reply_write";
	}
	
	@RequestMapping("/ReplyWriteProcess.do")
	public void replyWriteProcess(ReplyBoardDto replyBoardDto, HttpServletResponse response, HttpSession session) throws IOException {
		replyBoardDto.setId((String)session.getAttribute("loggedId"));
		int reGroup = replyBoardDto.getReGroup();
		int reLevel = replyBoardDto.getReLevel();
		int reStep = replyBoardDto.getReStep();
		replyBoardDao.updateReLevel(replyBoardDto);
		replyBoardDto.setReGroup(reGroup);
		replyBoardDto.setReLevel(reLevel + 1);
		replyBoardDto.setReStep(reStep + 1);
		int result = replyBoardDao.insertBoard(replyBoardDto);
		if(result>0) {
			ScriptWriter.alertAndNext(response, "답글이 등록되었습니다.", "../board/List.do");
		} else {
			ScriptWriter.alertAndBack(response, "시스템 오류 입니다. 잠시 후 다시 시도해 주세요.");
		}
	}
	
	@GetMapping("/Update.do")
	public String update() {
		return "board/update";
	}
	
	@RequestMapping("/UpdateProcess.do")
	public void updateProcess(ReplyBoardDto replyBoardDto, HttpServletResponse response) throws IOException {
		int result = replyBoardDao.updateBoard(replyBoardDto);
		if(result>0) {
			ScriptWriter.alertAndNext(response, "글이 수정되었습니다.", "../board/List.do");
		} else {
			ScriptWriter.alertAndBack(response, "시스템 오류 입니다. 잠시 후 다시 시도해 주세요.");
		}
	}
	
	@GetMapping("/Delete.do")
	public String delete() {
		return "board/delete";
	}
	
	@RequestMapping("/DeleteProcess.do")
	public void deleteProcess(ReplyBoardDto replyBoardDto, HttpServletResponse response) throws IOException {
		int result = replyBoardDao.deleteBoard(replyBoardDto);
		if(result>0) {
			ScriptWriter.alertAndNext(response, "글이 삭제되었습니다.", "../board/List.do");
		} else {
			ScriptWriter.alertAndBack(response, "시스템 오류 입니다. 잠시 후 다시 시도해 주세요.");
		}
	}
	
	@GetMapping("/View.do")
	public String view(int no,int num,Model model) {
		replyBoardDao.updateHit(no);
		
		replyBoardDto = replyBoardDao.getSelectOne(no);
		prevBoardDto = replyBoardDao.getPrevSelect(num);
		nextBoardDto = replyBoardDao.getNextSelect(num);

		model.addAttribute("replyBoardDto",replyBoardDto);
		model.addAttribute("prevBoardDto",prevBoardDto);
		model.addAttribute("nextBoardDto",nextBoardDto);
		return "board/view";
	}
	
	@RequestMapping("/SummerNoteFileUpload.do")
	@ResponseBody
	public Map<String,Object> summerNoteFileUpload(MultipartFile summerNoteImage,
			                                       HttpServletRequest request,
			                                       HttpServletResponse response
												   ) {
		Map<String,Object> hashMap = new HashMap<>();
		
		String context = request.getContextPath();
		String imgFolder =  "/Users/klow_on/Desktop/TIS/galleryImage/";
		String originalFileName = summerNoteImage.getOriginalFilename();
		
		String extention = FilenameUtils.getExtension(originalFileName); // 확장자 구하기...
		
		String savedFileName = UUID.randomUUID()+"."+extention;  
		File targetFile = new File(imgFolder+savedFileName);
		String dbFileName = context+"/galleryImage/"+savedFileName;
		
		InputStream fileStream;
		try {
			fileStream = summerNoteImage.getInputStream();
			FileUtils.copyInputStreamToFile(fileStream,targetFile);
			hashMap.put("url",context+"/galleryImage/"+savedFileName);
			hashMap.put("responseCode","success");
		} catch (IOException e) {
			FileUtils.deleteQuietly(targetFile);
			hashMap.put("responseCode","error");
			e.printStackTrace();
		}
		return hashMap;
	}
}





