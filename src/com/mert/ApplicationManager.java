package com.mert;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class ApplicationManager {
	
	public static Board CreateBoard() {
		Board board=new Board();
		 SessionFactory sessionFactory = new Configuration().configure()
					.buildSessionFactory();
		 Session session =null;
		 
		 if(sessionFactory.isOpen())
			  session = sessionFactory.openSession();
		 else
			 session=sessionFactory.getCurrentSession();
		 
		 session.setProperty("hibernate.hbm2ddl.auto", "create");
		try {
			
				board.setStatus(0);
			session.beginTransaction();
			session.save(board);
			session.getTransaction().commit();

			// now lets pull events from the database and list them
			
			
				System.out.println(board.getBoardId());
			
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
			session.close();
			sessionFactory.close();
		} finally {
			
		}
		 return board;
	}
	
	public static String SetStatus(Board board) {
		String message = "Ok";
		if (board.getStatus()!=1) {
			message="Board u pasif hale getirmek için Status=1 gönderilmeli.";
			return message;
		}
		 SessionFactory sessionFactory = new Configuration().configure()
					.buildSessionFactory();
		 Session session =null;
		 
		 if(sessionFactory.isOpen())
			  session = sessionFactory.openSession();
		 else
			 session=sessionFactory.getCurrentSession();
		 
		
		try {
			
				
			session.beginTransaction();
			session.update(board);
			session.getTransaction().commit();

			// now lets pull events from the database and list them
			
			
				System.out.println(board.getBoardId());
			
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
			message=e.getMessage();
			session.close();
			sessionFactory.close();
		} finally {
			
		}
		 
		 	return message;
		
		
	}
	
	public static String PlayMove(List<Moves> move) {
		 String message = "Ok";
		 System.out.println("Play");
		 System.out.println(move.get(0).getBoardId());
		 List<Moves> pastMoves= new ArrayList<Moves>();
		 char[][] boardArray =new char[15][15];
		 
		 SessionFactory sessionFactory = new Configuration().configure()
					.buildSessionFactory();
		
		
			Session session = sessionFactory.openSession();
		 
		 //session.setProperty("hibernate.hbm2ddl.auto", "create");
		 	
		try {
			
			session.beginTransaction();
			
			//Board Aktif mi değil mi
			String hql = "FROM Board c WHERE c.boardId = "+move.get(0).getBoardId();
            Query query = session.createQuery(hql);
            List<Board> board = query.list();
            
            
         
            
        	if(board.size()>0) {
            if(board.get(0).getStatus()==0)
            {	System.out.println("Board aktif");
            	 //Önceki hareketleri getir
            	String hql2 = "FROM Moves c WHERE c.boardId = "+move.get(0).getBoardId();
            	Query query2 = session.createQuery(hql2);
            	pastMoves  = query2.list();
            	
            	for (int j = 0; j < move.size(); j++) {
									
            	//İlk hareket ise
            	if (pastMoves.size()==0)
            	{
            		System.out.println("İlk Hareket");
            		//Sözlük içinde var mı?
            		if(checkDictionary(move.get(0).getWord()))
            		{
            			
            			move.get(j).setScore(getWordScore(move.get(j).getWord()));
            			 System.out.println(move.get(j).getScore());
            			
            			
            		}
            		else
            		{
            			return "Sözlükte böyle bir kelime yok.";
            		}
            	}
            	//İkinci ve sonrası hareket ise
            	else {
            		System.out.println("Sonraki hareketler "+j);
            		boardArray=setBoardValues(pastMoves);
            		
            		if(pastMoves.contains(move.get(j).getWord())){
            			return "Bu sözcük daha önce kullanılmış";
            		}
            		//Sözlük içinde var mı?
            		if(checkIfAvaliable(boardArray, move.get(j)))
            		{
            			if(checkDictionary(move.get(0).getWord()))
            			{
            			
            				move.get(j).setScore(getWordScore(move.get(j).getWord()));
            				System.out.println(move.get(j).getScore());
            			
            			}
            			else
            			{
            			return "Sözlükte böyle bir kelime yok.";
            			}
            		}else
            		{
            			return "Dizilimde yanlışlık var.";
                		
            		}
            		
            	}
			
			

            }
            	
            	for (int i = 0; i < move.size(); i++) {
            		session.save(move.get(i));
            		
				}
            	session.getTransaction().commit();
			System.out.println("Move Ok");
            }
            else
            {
            	
    			System.out.println("Board Aktif Değil");
    			return "Aktif oyun bulunmamaktadır.";
                
            }
        	}else
        	{
        		return "BoardId bulunamadı";
                
        	}
			
			
		} catch (Exception e) {
			// TODO: handle exception
			message=e.getMessage();
			//session.close();
			//sessionFactory.close();
		} finally {
			//session.close();
		}
		 return message;
	}
	
	private static boolean checkDictionary(String word) {
		 Dictionary dictionary=new Dictionary();
		 	SessionFactory sessionFactory = new Configuration().configure()
					.buildSessionFactory();
			Session session = sessionFactory.openSession();

			session.beginTransaction();
			 String hql = "FROM Dictionary c WHERE c.wordName = :wordName";
             Query query = session.createQuery(hql).setString("wordName", word);
             	
             List<Dictionary> list = query.list();
			if (list.size()>0){
				session.getTransaction().commit();
				session.close();
				return true;
			}
			session.getTransaction().commit();
			session.close();
			
				 
		 return false;
	 }
	 
	 static List<Moves> getWords(int boardId) {
		
		 	SessionFactory sessionFactory = new Configuration().configure()
					.buildSessionFactory();
			Session session = sessionFactory.openSession();
			List<Moves> list=new ArrayList<Moves>();
			try {
				
			
			session.beginTransaction();
			 String hql = "FROM Moves c WHERE c.boardId = "+boardId;
             Query query = session.createQuery(hql);
             	
             list = query.list();
			
				session.getTransaction().commit();
				session.close();
			
			} catch (Exception e) {
				// TODO: handle exception
				session.close();
			}
		 return list;
	 }
	 
	 public static String CreateDictionary(String directory)
	 {
		 String message = "Ok";
		 Dictionary dictionary=new Dictionary();
		 BufferedReader br=null;
		 System.out.println(directory);
		 if(directory==null)
		  return "Directory is null";
				  //directory="/Users/mert_celenk/eclipse-workspace/Scrabble/resources/scrabble_turkish_dictionary.txt";
		 
		 SessionFactory sessionFactory = new Configuration().configure()
					.buildSessionFactory();
		 Session session =null;
		 if(sessionFactory.isOpen())
			  session = sessionFactory.openSession();
		 else
			 session=sessionFactory.getCurrentSession();
		 try {
			session.beginTransaction();
			session.createSQLQuery("truncate table Dictionary").executeUpdate();
			
				String line =null;
				
				br = new BufferedReader(new FileReader(new File(directory)));
				
				while((line = br.readLine()) != null) {
					dictionary=new Dictionary();
					dictionary.setWordName(line);
					session.save(dictionary);
				 }
				 
			
			session.getTransaction().commit();

			// now lets pull events from the database and list them
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
			message=e.getMessage();
			session.close();
			sessionFactory.close();
		} finally {
			
		}
		 
		 	return message;
	 }


	public static char[][] setBoardValues(List<Moves> moves){
		char[][] board =new char[15][15];
		
		
		int d=0;
		for (int k = 0; k < moves.size(); k++) {
			System.out.println(moves.get(k).getWord());
			//Soldan Sağa
			if(moves.get(k).getDirection()==0) {
				for (char letter : moves.get(k).getWord().toCharArray()) {
					
					board[moves.get(k).getStartXCoordinate()][moves.get(k).getStartYCoordinate()+d]=letter;
					d=d+1;
				}
			}
			//Yukarıdan Aşağıya
			else if(moves.get(k).getDirection()==1)
			{
				for (char letter : moves.get(k).getWord().toCharArray()) {
					
					board[moves.get(k).getStartXCoordinate()+d][moves.get(k).getStartYCoordinate()]=letter;
					d=d+1;
				}
			}
			
		}
		
		return board;
	}
	
	public static boolean checkIfAvaliable(char[][] board , Moves newMove){
		
		int d=0;
		int f=0;
		if(newMove.getDirection()==0) {
			for (char letter : newMove.getWord().toCharArray()) {
				if(board[newMove.getStartXCoordinate()][newMove.getStartYCoordinate()+d]==0)
					{
						//board[newMove.getStartXCoordinate()][newMove.getStartYCoordinate()+d]=letter;
					}
				else if(board[newMove.getStartXCoordinate()][newMove.getStartYCoordinate()+d]==letter)
				{
					f=f+1;
					//board[newMove.getStartXCoordinate()][newMove.getStartYCoordinate()+d]=letter;
				}
				else if(board[newMove.getStartXCoordinate()][newMove.getStartYCoordinate()+d]!=letter)
				{
					return false;
				}
						d=d+1;
			}
		}
		//Yukarıdan Aşağıya
		else if(newMove.getDirection()==1)
		{
			for (char letter : newMove.getWord().toCharArray()) {
				if(board[newMove.getStartXCoordinate()+d][newMove.getStartYCoordinate()]==0)
				{
					//board[newMove.getStartXCoordinate()][newMove.getStartYCoordinate()]=letter;
				}
			else if(board[newMove.getStartXCoordinate()+d][newMove.getStartYCoordinate()]==letter)
			{
				f=f+1;
				//board[newMove.getStartXCoordinate()][newMove.getStartYCoordinate()]=letter;
			}
			else if(board[newMove.getStartXCoordinate()+d][newMove.getStartYCoordinate()]!=letter)
			{
				return false;
			}
					d=d+1;
				
			}
		}
		if(f==newMove.getWord().length())
			return false;
		System.out.println("Kelime ok");
		return true;
	}
	
	public void GetAllBoards() {
		 Board board=new Board();
		 	SessionFactory sessionFactory = new Configuration().configure()
					.buildSessionFactory();
			Session session = sessionFactory.openSession();

			session.beginTransaction();
			List result = session.createQuery("from Board").list();
			for (Board board1 : (List<Board>) result) {
				System.out.println(board1.getStatus());
			}
			session.getTransaction().commit();

			// now lets pull events from the database and list them
			
			
				System.out.println(board.getBoardId());
			
	}
	
	 public static int getWordScore(String word) {

	        int finalScore = 0;
	        for (char letter : word.toCharArray()) {
	            finalScore += getLetterValue(letter);
	        }
	        return finalScore;
	}
	 
	 private static int getLetterValue(char letter)
	 {
		 HashMap<Character, Integer> values = new HashMap<Character, Integer>();
		 	values.put('a', 1);
	        values.put('b', 3);
	        values.put('c', 4);
	        values.put('ç', 5);
	        values.put('d', 3);
	        values.put('e', 1);
	        values.put('f', 7);
	        values.put('g', 5);
	        values.put('ğ', 8);
	        values.put('h', 5);
	        values.put('ı', 2);
	        values.put('i', 1);
	        values.put('j', 10);
	        values.put('k', 1);
	        values.put('l', 1);
	        values.put('m', 2);
	        values.put('n', 1);
	        values.put('o', 2);
	        values.put('ö', 7);
	        values.put('p', 5);
	        values.put('r', 1);
	        values.put('s', 2);
	        values.put('ş', 4);
	        values.put('t', 1);
	        values.put('u', 2);
	        values.put('ü', 3);
	        values.put('v', 7);
	        values.put('y', 3);
	        values.put('z', 4);
		 
	        return values.get(letter);
	        
	 }

}
