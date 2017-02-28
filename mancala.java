package hw2;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class mancala {
	static int splayer=1;
	/*public static void greedy(ArrayList<Integer>){
		ArrayList<ArrayList<Integer>> C=new ArrayList<ArrayList<Integer>>(); 
		ArrayList<ArrayList<Integer>> D=new ArrayList<ArrayList<Integer>>(); 
		C.add(0,B);
		D.add(0,A);
		//System.out.println("Initially B is" +B);
		for(int main=0;main<=B.size();main++){
			C.add(main,B);
			D.add(main,A);
		}
		for(int main=0;main<=B.size()-2;main++){
		for(int i=2;i<=C.get(main).size()-2;i++){
			////System.out.println(B);
			int value=C.get(0).renextmove(i);
			//System.out.println("the value renextmoved from "+ i+" is"+value);
			////System.out.println(B);
			C.get(0).add(i,0);
			//System.out.println("after updating it to zero"+C.get(0));
			while(value>0){
				for(int j=i+1;j<=C.get(0).size()-2;j++){
					if(value!=0){
					value--;
					int invalue=C.get(0).renextmove(j);
					//System.out.println("the value renextmoved from "+ j+" is"+invalue);
					invalue++;
					C.get(0).add(j,invalue);	
					//System.out.println("after updating it"+C.get(0));
					}
				}
				if(value!=0){
				int invalue1=C.get(0).renextmove(C.get(0).size()-1);
				//System.out.println("value renextmoved from mancala is "+invalue1);
				value--;
				invalue1++;
				C.get(0).add(C.get(0).size()-1,invalue1);
				//System.out.println("new mancala value"+C.get(0).get(C.get(0).size()-1));
				}
				for(int k=D.get(0).size()-1;k>=2;k--){
					if(value!=0){
					int invalue2=D.get(0).renextmove(k);
					//System.out.println("the value renextmoved from A "+ k+" is"+invalue2);
					invalue2++;
					D.get(0).add(k,invalue2);
					//System.out.println("after updating "+D.get(0));
					value--;
					}
					}
			}
			////System.out.println(B);
		}
		////System.out.println(B);
	}	////System.out.println(A);
	
	}*/
	
public static String tellpos(int pos,int len){
	if(pos<len)
	{
		int k=pos+2;
		StringBuilder sb = new StringBuilder();
        sb.append('B');
		sb.append(k);
		String strI = sb.toString();
		return strI;
	}
	else{
		int var=(2*(len))-pos+2;
		StringBuilder sb = new StringBuilder();
        sb.append('A');
		sb.append(var);
		String strI = sb.toString();
		return strI;
		
	}
}
public static boolean cutofftest(state se,int depth,int cutoff){
	//testing terminal state or not 
	/*int sum=0;
	for(int j=0;j<se.board.size();j++)
	{
	if(j!=se.length || j!=((2*se.length)+1))
			sum=sum+se.board.get(j);
	}
	if(sum==0)
	return true;
else  */                          
		if(depth==cutoff)
			return true;
	return false;
}

public static state minmax(state curr,int cutoff) throws FileNotFoundException, UnsupportedEncodingException{
	PrintWriter writer = new PrintWriter("traverse_log.txt", "UTF-8");
	writer.println("Node,Depth,Value");
	ArrayList<Integer> validpos=new ArrayList<Integer>();
	validpos=valid_pos(curr);
	int max=0;
	boolean updatemax=false;
	state max_state=null;
	state next_state=null;
	node result_node=null;
	int depth=0;
	writer.println("root"+","+"0"+","+"-Infinity");
	for(int i=0;i<validpos.size();i++){
		updatemax=true;
		//writer.println("nextmove called for: " + curr.board + " for " + curr.player);
		next_state=nextmove(curr,validpos.get(i));
		//writer.println("after nextmove called for: " + next_state.board + " for " + next_state.player);
		if(next_state.player==curr.player){
			writer.println("Max called for: from minmax " + next_state.board + " for " + next_state.player);
			result_node=maxi(next_state,depth+1,true,validpos.get(i),cutoff,writer);
		}
		else{
			writer.println("Min called for: from minmax" + next_state.board + " for " + next_state.player);
			result_node=mini(next_state,depth+1,false,validpos.get(i),cutoff,writer);
		}
		if(i==0){
			max=result_node.evalu;
			max_state=result_node.s;
		}
		else{
			if(max<result_node.evalu){
				max=result_node.evalu;
				max_state=result_node.s;
			}
		}
			writer.println("root"+","+"0"+","+max);
	}
	writer.close();
	//System.out.println("Main returns " + max_state.board);
	return max_state;
	
	
}
public static node mini(state ste,int depth,boolean rep,int pos,int cutoff,PrintWriter writer){
	int val=0;
	state sstate=null;
	int n_depth=0;
	state next_state=null;
	state min_state=ste;
	node result_node=null;
	int min=0;
	boolean updatemin=false;
	writer.print(rep);
	if(!rep){
		if(cutofftest(ste,depth,cutoff)){
			val=evalfn(ste);
			sstate=ste;
			//writer.println(ste.board);
			writer.println(tellpos(pos,sstate.length)+","+depth+","+val);
			node n=new node(ste,val);
			return n;
			
		}
	}
		if(!rep){
			n_depth=depth+1;
		}
		else{
			n_depth=depth;
		}
		ArrayList<Integer> validpos=new ArrayList<Integer>();
		validpos=valid_pos(ste);
		writer.println("here"+tellpos(pos,ste.length)+","+depth+","+"Infinity");
		for(int i=0;i<validpos.size();i++){
			updatemin=true;
			next_state=nextmove(ste,validpos.get(i));
			if(next_state.player==ste.player){
				writer.println("Min called for:in min method " + next_state.board + " for " + next_state.player);
				result_node=mini(next_state,n_depth,true,validpos.get(i),cutoff,writer);
			}
				else
				{
					writer.println("Max called for:in min method " + next_state.board + " for " + next_state.player);
					result_node=maxi(next_state,n_depth,false,validpos.get(i),cutoff,writer);
				}
				if(i==0){
						min=result_node.evalu;
						if(rep)
						{min_state=result_node.s;}
				}
				else{
					if(min>result_node.evalu)
					{
						min=result_node.evalu;
						if(rep)
						{min_state=result_node.s;}
						
					}
				}
				writer.println(tellpos(pos,ste.length)+","+depth+","+min);	
		}
	if(!updatemin)
	{
		min = evalfn(min_state);
		writer.println("no here"+tellpos(pos,ste.length)+","+depth+","+min);
	}
	node n=new node(min_state,min);
	//writer.println(ste.board);
//	System.out.println("Min returns " + min_state.board + " value " + min);
	return n;
}
public static node maxi(state ste,int depth,boolean rep,int pos,int cutoff,PrintWriter writer){
	int val=0;
	int n_depth=0;
	state next_state=null;
	state max_state=ste;
	node result_node=null;
	int max=0;
	boolean updatemax=false;
	writer.print(evalfn(ste));
	writer.print("depth:"+depth+"cutoff:"+cutoff);
	if(!rep){
		if(cutofftest(ste,depth,cutoff)){
			val=evalfn(ste);
			writer.println(tellpos(pos,ste.length)+","+depth+","+val);
			node n=new node(ste,val);
			return n;
			
		}
	}
		if(!rep){
			n_depth=depth+1;
		}
		else{
			n_depth=depth;
		}
		ArrayList<Integer> validpos=new ArrayList<Integer>();
		validpos=valid_pos(ste);
		writer.println("where"+tellpos(pos,ste.length)+","+depth+","+"-Infinity");
		//writer.println("nextmove called for: in maxi" + ste.board + " for " + ste.player);
		for(int i=0;i<validpos.size();i++){
			updatemax=true;
			next_state=nextmove(ste,validpos.get(i));
			//writer.println("after nextmove called for:in maxi " + next_state.board + " for " + next_state.player);
			if(next_state.player==ste.player)
			{
				writer.println("Max called for: in max method" + next_state.board + " for " + next_state.player);
				result_node=maxi(next_state,n_depth,true,validpos.get(i),cutoff,writer);
			}
			else{
				writer.println("Min called for:in max method" + next_state.board + " for " + next_state.player);
					result_node=mini(next_state,n_depth,false,validpos.get(i),cutoff,writer);
			}
			if(i==0){
					max=result_node.evalu;
					if(rep)
					{max_state=result_node.s;}
			}
			else{
					if(max<result_node.evalu)
					{
						max=result_node.evalu;
						if(rep)
						{max_state=result_node.s;}
						
					}
			}
			writer.println(tellpos(pos,ste.length)+","+depth+","+max);	
		}
	if(!updatemax){
		max = evalfn(max_state);
		writer.println("no where"+tellpos(pos,ste.length)+","+depth+","+max);
	}
	node n=new node(max_state,max);
	//System.out.println("Max returns " + max_state.board + " value " + max);
	return n;
}
public static int evalfn(state s){
	if(splayer==1){
		return (s.board.get(s.length)-s.board.get((2*s.length)+1));
	}
	else 
		return (s.board.get((2*s.length)+1))-s.board.get(s.length);
	
}
public static ArrayList<Integer> valid_pos(state tmp){
	ArrayList<Integer> a=new ArrayList<Integer>();
	if(tmp.player==1)
	{
		for(int y=0;y<tmp.length;y++)
		{
			if(tmp.board.get(y)!=0)
				a.add(y);
				
		}
		return a;
	}
	else{
		for(int y=(2*tmp.length);y>=tmp.length+1;y--){
			if(tmp.board.get(y)!=0)
				a.add(y);
		}
		return a;
	}
}
public static state alphabeta(state curr,int cutoff) throws FileNotFoundException, UnsupportedEncodingException{
	//System.out.println("calling alphabeta");
	PrintWriter writer = new PrintWriter("traverse_log.txt", "UTF-8");
	writer.println("Node,Depth,Value,Alpha,Beta");
	ArrayList<Integer> valid_pos=new ArrayList<Integer>();
	valid_pos=valid_pos(curr);
	state next_state=null;
	int max=0;
	state max_state=null;
	node result_node=null;
	int depth=0;
	int alpha=0;
	boolean updatealpha=false;
	boolean updatebeta=false;
	int beta=0;
	writer.println("root"+","+"0"+","+"-Infinity"+","+"-Infinity"+","+"Infinity");
	//System.out.println(curr.player);
	for(int i=0;i<valid_pos.size();i++){
		next_state=nextmove(curr,valid_pos.get(i));
		if(next_state.player==curr.player){
			result_node=abmaxi(next_state,depth+1,true,valid_pos.get(i),alpha,updatealpha,beta,updatebeta,writer,cutoff);
			
		}
		else{
			result_node=abmini(next_state,depth+1,false,valid_pos.get(i),alpha,updatealpha,beta,updatebeta,writer,cutoff);
		}
		if(i==0){
			max=result_node.evalu;
			max_state=result_node.s;
		}
		else{
			if(max<result_node.evalu)
			{
				max=result_node.evalu;
				max_state=result_node.s;
			}
			
		}
		if(!updatealpha){
			updatealpha=true;
			alpha=max;
		}
		else{
			if(alpha<max){
				alpha=max;
			}
		}
		String alphaString = "-Infinity";
		String betaString = "Infinity";
		if(updatealpha){
			alphaString = Integer.toString(alpha);
		}
		if(updatebeta){
			betaString = Integer.toString(beta);
		}
		writer.println("root"+","+"0"+","+max+","+alphaString+","+betaString);
	}
	writer.close();
	//System.out.println("main returns"+max_state.board+"value"+max);
	return max_state;
}
public static node abmini(state curr,int depth,boolean rep,int pos,int alpha,boolean updatealpha,int beta,boolean updatebeta,PrintWriter writer,int cutoff){
	int sum=0;
	int val=0;
	int n_depth=0;
	int min=0;
	boolean updatemin=false;
	state min_state=curr;
	state next_state=null;
	node resultnode=null;
	ArrayList<Integer> validpos=new ArrayList<Integer>();
	
	if(!rep){
		if(cutofftest(curr,depth,cutoff)){
			val=evalfn(curr);
			String alphaString = "-Infinity";
			String betaString = "Infinity";
			if(updatealpha){
				alphaString = Integer.toString(alpha);
			}
			if(updatebeta){
				betaString = Integer.toString(beta);
			}
			writer.println(tellpos(pos,curr.length)+","+depth+","+val+","+alphaString+","+betaString);
			
			node n=new node(curr,val);
			return n;
			
		}
	}
	if(!rep){
		n_depth=depth+1;
	}
	else{
		n_depth=depth;
	}
	validpos=valid_pos(curr);
	String alphaString = "-Infinity";
	String betaString = "Infinity";
	if(updatealpha){
		alphaString = Integer.toString(alpha);
	}
	if(updatebeta){
		betaString = Integer.toString(beta);
	}
	writer.println(tellpos(pos,curr.length)+","+depth+",Infinity,"+alphaString+","+betaString);
	//System.out.println("abc"+curr.board);
	for(int i=0;i<validpos.size();i++){
		updatemin=true;
		next_state=nextmove(curr,validpos.get(i));
		if(next_state.player==curr.player){
			resultnode=abmini(next_state,n_depth,true,validpos.get(i),alpha,updatealpha,beta,updatebeta,writer,cutoff);
		}
		else{
			resultnode=abmaxi(next_state,n_depth,false,validpos.get(i),alpha,updatealpha,beta,updatebeta,writer,cutoff);
		}
		// what code goes here
		if(i==0){
			min=resultnode.evalu;
			if(rep)
			{min_state=resultnode.s;}
		}
		else{
			if(min>resultnode.evalu){
				min=resultnode.evalu;
				if(rep)
				{min_state=resultnode.s;}
			}
		}
		if(updatealpha){
			if(min<=alpha){
				alphaString = "-Infinity";
				betaString = "Infinity";
				if(updatealpha){
					alphaString = Integer.toString(alpha);
				}
				if(updatebeta){
					betaString = Integer.toString(beta);
				}
				writer.println(tellpos(pos,curr.length)+","+depth+","+min+","+alphaString+","+betaString);
				return new node(min_state,min);
			}
		}
		if(!updatebeta){
			beta=min;
			updatebeta=true;
		}
		else{
			if(beta>min){
			beta=min;
			}
		}
		alphaString = "-Infinity";
		betaString = "Infinity";
		if(updatealpha){
			alphaString = Integer.toString(alpha);
		}
		if(updatebeta){
			betaString = Integer.toString(beta);
		}
		writer.println(tellpos(pos,curr.length)+","+depth+","+min+","+alphaString+","+betaString);
		// need to write logs
	}
	if(!updatemin){
		min=evalfn(min_state);
		alphaString = "-Infinity";
		betaString = "Infinity";
		if(updatealpha){
			alphaString = Integer.toString(alpha);
		}
		if(updatebeta){
			betaString = Integer.toString(beta);
		}
		writer.println(tellpos(pos,curr.length)+","+depth+","+min+","+alphaString+","+betaString);
	}
	//System.out.println("min returns"+min_state.board+"value"+min);
	return new node(min_state,min);
}
public static node abmaxi(state curr,int depth,boolean rep,int pos,int alpha,boolean updatealpha,int beta,boolean updatebeta,PrintWriter writer,int cutoff){
	int sum=0;
	int val=0;
	int n_depth=0;
	int max=0;
	boolean updatemax=false;
	state max_state=curr;
	state next_state=null;
	node resultnode=null;
	ArrayList<Integer> validpos=new ArrayList<Integer>();
	
	if(!rep){
		if(cutofftest(curr,depth,cutoff)){
			val=evalfn(curr);
			String alphaString = "-Infinity";
			String betaString = "Infinity";
			if(updatealpha){
				alphaString = Integer.toString(alpha);
			}
			if(updatebeta){
				betaString = Integer.toString(beta);
			}
			writer.println(tellpos(pos,curr.length)+","+depth+","+val+","+alphaString+","+betaString);
			node n=new node(curr,val);
			return n;
			
		}
	}
	if(!rep){
		n_depth=depth+1;
	}
	else{
		n_depth=depth;
	}
	String alphaString = "-Infinity";
	String betaString = "Infinity";
	if(updatealpha){
		alphaString = Integer.toString(alpha);
	}
	if(updatebeta){
		betaString = Integer.toString(beta);
	}
	writer.println(tellpos(pos,curr.length)+","+depth+","+"-Infinity"+","+alphaString+","+betaString);
	validpos=valid_pos(curr);
	for(int i=0;i<validpos.size();i++){
		updatemax = true;
		next_state=nextmove(curr,validpos.get(i));
		if(next_state.player==curr.player){
			resultnode=abmaxi(next_state,n_depth,true,validpos.get(i),alpha,updatealpha,beta,updatebeta,writer,cutoff);
		}
		else{
			resultnode=abmini(next_state,n_depth,false,validpos.get(i),alpha,updatealpha,beta,updatebeta,writer,cutoff);
		}
		// what code goes here
		//System.out.println("going max");
		if(i==0){
			max=resultnode.evalu;
			if(rep)
			{max_state=resultnode.s;}
		}
		else{
			if(max<resultnode.evalu){
				max=resultnode.evalu;
				if(rep)
				{max_state=resultnode.s;}
			}
		}
		if(updatebeta){
			if(max>=beta){
				alphaString = "-Infinity";
				betaString = "Infinity";
				if(updatealpha){
					alphaString = Integer.toString(alpha);
				}
				if(updatebeta){
					betaString = Integer.toString(beta);
				}
				writer.println(tellpos(pos,curr.length)+","+depth+","+max+","+alphaString+","+betaString);
				return new node(max_state,max);
			}
		}
		if(!updatealpha){
			alpha=max;
			updatealpha=true;
		}
		else{
			if(alpha<max){
				alpha=max;
				updatealpha=true;
			}
		}
		alphaString = "-Infinity";
		betaString = "Infinity";
		if(updatealpha){
			alphaString = Integer.toString(alpha);
		}
		if(updatebeta){
			betaString = Integer.toString(beta);
		}
		writer.println(tellpos(pos,curr.length)+","+depth+","+max+","+alphaString+","+betaString);
	}
	if(!updatemax){
		max=evalfn(max_state);
		alphaString = "-Infinity";
		betaString = "Infinity";
		if(updatealpha){
			alphaString = Integer.toString(alpha);
		}
		if(updatebeta){
			betaString = Integer.toString(beta);
		}
		writer.println(tellpos(pos,curr.length)+","+depth+","+max+alphaString+","+betaString);
	}
	//System.out.println("max returns"+max_state.board+"value"+max);
	return new node(max_state,max);
}

public static state nextmove(state so,int pos){
		//testing for if its a valid spot of not
	
	   int ignore=0;
	   int coins=0;
	   ArrayList<Integer> b=new ArrayList<Integer>();
	   b.addAll(so.board);
	   state tmp=new state(b,so.player,so.length);
		if( (tmp.board.get(pos)!=0) && (pos!=tmp.length) && (pos!=(2*tmp.length)+1)){
			
			
		}
		else {
			return null;
			}
		if(tmp.player==1)
		{
			ignore=(2*tmp.length)+1;
			
		}
		else 
			ignore=tmp.length;
		coins=tmp.board.get(pos);
	
		tmp.board.set(pos,0);
		int i=pos+1;
		while(coins!=0){
			
			if(i!=ignore){
				tmp.board.set(i,tmp.board.get(i)+1);
				coins--;
				if(coins==0)
					break;
				i++;
			}
			else
				i++;
			if(tmp.player==1){
				   if(i>=((2*tmp.length)+1))
					 i=0;
				   }
				else{
					if(i>(2*tmp.length)+1)
						i=0;
					}
		}
// If we ended up in empty slot		
			if(tmp.player==1){
				if(i>=0 && i<tmp.length){
					if(tmp.board.get(i)==1){
						tmp.board.set(tmp.length,(tmp.board.get(tmp.length))+(tmp.board.get((2*tmp.length)-i)+1));
						tmp.board.set(i,0);
						tmp.board.set((2*tmp.length)-i,0);
					

					//what shd I return here
					}
				}
			}
				else
				{
					if(i>=tmp.length+1 && i<=((2*tmp.length))){
						if(tmp.board.get(i)==1){
							tmp.board.set(((2*tmp.length)+1),(tmp.board.get((2*tmp.length)+1)+tmp.board.get((2*tmp.length)-i)+1));
							tmp.board.set(i,0);
							tmp.board.set((2*tmp.length)-i,0);
							
						}
						
					}
					//what shd I return here
				}
			
			
// check condition if we have reached terminal state			
		
				int sum=0;
				int value=0;
				for(int y=0;y<tmp.length;y++){
					sum=sum+tmp.board.get(y);
					
				}
				if(sum==0)
				{
					for(int y=tmp.length+1;y<=((2*tmp.length));y++){
					value=value+tmp.board.get(y);
					tmp.board.set(y,0);
					}
					tmp.board.set(((2*tmp.length)+1),tmp.board.get((2*tmp.length)+1)+value);
				}
				
				
				//what shd I return here
				


			sum=0;
			value=0;
				for(int y=tmp.length+1;y<=((2*tmp.length));y++){
					sum=sum+tmp.board.get(y);
				}
				if(sum==0){
					for(int y=0;y<tmp.length;y++){
						value=value+tmp.board.get(y);
						tmp.board.set(y,0);
						}
					tmp.board.set((tmp.length),tmp.board.get(tmp.length)+value);
				}
			    // return tmp;
				//what shd I return here
				// check if it has fallen in mancala	
			if(tmp.player==1){
				if(i==tmp.length){
					return tmp;
				}
			
			else{
				tmp.player=2;
				return tmp;
			}
			}
			else{
				if(i==(2*tmp.length)+1){
					return tmp;
				}
				else{
					tmp.player=1;
					return tmp;
				}
			}
			
				

}
public static state greedy(state tmp){
	ArrayList<Integer> validpos=new ArrayList<Integer>();
	validpos=valid_pos(tmp);
	int max=0;
	state max_state=null;
	state next_state=null;
	for(int i=0;i<validpos.size();i++){
		
		next_state=nextmove(tmp,validpos.get(i));
		//System.out.println("The next state is: " + next_state.board);
		if(next_state==null)
			continue;
		if(next_state.player==tmp.player)
		{
	
			next_state=greedy(next_state);
			
		}
		if(i==0){
			max=evalfn(next_state);
			max_state=next_state;
		}
		else{
			if(evalfn(next_state)>max){
				max=evalfn(next_state);
				max_state=next_state;
				
			}
		}
	}

	return max_state;
	}

	public static void main(String[] args)throws IOException {
		// TODO Auto-generated method stub
		String fname ="input.txt";
		String line = null;
		String line1=null;
		int count=0;
		int whichplayer=0;
		ArrayList<Integer> board=new ArrayList<Integer>();
		ArrayList<Integer> b=new ArrayList<Integer>();
		state s1=null;
		try{
			PrintStream ps=new PrintStream(new File("next_state.txt"));
        	System.setOut(ps);
            FileReader fr = new FileReader(fname);
            BufferedReader br =  new BufferedReader(fr);
            int task=Integer.parseInt(br.readLine());
           // System.out.println("the task is"+task);
            whichplayer=Integer.parseInt(br.readLine());
            //System.out.println("which player has to play is"+whichplayer);
            splayer=whichplayer;
            int cutoffdepth=Integer.parseInt(br.readLine());
            //System.out.println("cutoff depth is"+cutoffdepth);
            line=br.readLine();
            StringTokenizer st = new StringTokenizer(line, " ");
            
            //System.out.println("first"+line);
            while(st.hasMoreTokens()){
            b.add(Integer.parseInt(st.nextToken()));
            }
            line=br.readLine();
            //System.out.println("2nd"+line);
            StringTokenizer st1 = new StringTokenizer(line, " ");
            int p=0;
            while(st1.hasMoreTokens()){
            	board.add(Integer.parseInt(st1.nextToken()));
            	p++;
            }
            line1=br.readLine();
            String line3=br.readLine();
            board.add(p,Integer.parseInt(line3));
            int len=b.size()-1;
            while(len>=0){
            	board.add(b.get(len));
            	len--;
            }
            board.add(Integer.parseInt(line1));
           int boardlen=(board.size()-2)/2;
            s1=new state(board,whichplayer,boardlen);
            //System.out.println("the board is"+board);
            //System.out.println(board.get(boardlen));
            br.close();
          //  state s=new state(board,whichplayer,boardlen);
            if(task==1 || task==4)
            {
            	state finalans=greedy(s1);
            	
            	   for(int k=2*s1.length;k>=s1.length+1;k--){
               		System.out.print(finalans.board.get(k)+" ");
               	}
            	   System.out.println("");
            	for(int k=0;k<s1.length;k++)
            	{
            		System.out.print(finalans.board.get(k)+" ");
            	}
            	System.out.println("");
            	System.out.println(finalans.board.get((2*s1.length)+1));
            	System.out.println(finalans.board.get(s1.length));
            }
            else if(task==2){
            state finalans=minmax(s1,cutoffdepth);
            for(int k=2*s1.length;k>=s1.length+1;k--){
        		System.out.print(finalans.board.get(k)+" ");
        	}
            System.out.println("");
            for(int k=0;k<s1.length;k++)
        	{
        		System.out.print(finalans.board.get(k)+" ");
        	}
            System.out.println("");
        	System.out.println(finalans.board.get((2*s1.length)+1));
        	System.out.println(finalans.board.get(s1.length));
            }
            else if(task==3){
            	
            	state finalans=alphabeta(s1,cutoffdepth);
            	
            	for(int k=2*s1.length;k>=s1.length+1;k--){
            		System.out.print(finalans.board.get(k)+" ");
            	}
            	
            	System.out.println("");
            	for(int k=0;k<s1.length;k++)
            	{
            		System.out.print(finalans.board.get(k)+" ");
            	}
            	System.out.println("");
            	System.out.println(finalans.board.get((2*s1.length)+1));
            	System.out.println(finalans.board.get(s1.length));
            }
            

            
            
		}
		catch(FileNotFoundException ex) {
            //System.out.println(
//                "Unable to open file '" + 
//                fname + "'");                
        }
		catch(IOException ex) {
            //System.out.println(
//                "Error reading file '" 
//                + fname + "'");                  
        }
	}

}
