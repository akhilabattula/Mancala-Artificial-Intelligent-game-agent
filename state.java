package hw2;

import java.util.ArrayList;

public class state {
	ArrayList<Integer> board=new ArrayList<Integer>();
	int player;
	int length;
	
	public state(ArrayList<Integer> b,int p,int len)
	{
		board=b;
		player=p;
		length=len;
	}

}
