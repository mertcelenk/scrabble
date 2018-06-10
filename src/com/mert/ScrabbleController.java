package com.mert;



import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ScrabbleController {
	
	 @RequestMapping(value = "/createboard", method = RequestMethod.GET)
	 @ResponseBody
	public Board CreateBoard() {
		 return ApplicationManager.CreateBoard();
				 
	}
	 
	 @RequestMapping(value = "/play", headers = "Accept=application/json",method = RequestMethod.POST)
	 @ResponseBody
	public String Play(@RequestBody List<Moves> move) {
		 return ApplicationManager.PlayMove(move);
				 
	}
	 

	 @RequestMapping(value = "/setstatus", headers = "Accept=application/json",method = RequestMethod.POST)
	 @ResponseBody
	public String setStatus(@RequestBody Board board) {
		
		 return ApplicationManager.SetStatus(board);
				 
	}
	 
	 @RequestMapping(value = "/getwords", headers = "Accept=application/json",method = RequestMethod.POST)
	 @ResponseBody
	public List<Moves> getWords(@RequestBody Board board) {
		
		 return ApplicationManager.getWords(board.getBoardId());
				 
	}
	 
	 
	 @RequestMapping(value = "/createdictionary", method = RequestMethod.GET,params = {"directory"})
	 @ResponseBody
	public String CreateDictionary(@RequestParam(value = "directory") String directory) {
		
		
		 return ApplicationManager.CreateDictionary(directory);
				 
	}
	 
	


}
