package controllers;


public class Testings {
	public void method(){
		String s=null;
		try{
			if(true){
				s="ram";
			}
			System.out.println(s);
		}catch(Exception e){
			
		}
	}
	public static void main(String[] args) {
		new Testings().method();
	}

}
