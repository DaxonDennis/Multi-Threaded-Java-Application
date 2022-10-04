/* 
  Name: Daxon Dennis 
  Course: CNT 4714 Fall 2022 
  Assignment title: Project 2 – Multi-threaded programming in Java 
  Date:  October 9, 2022 
 
  Class:  Enterprise Computing 
*/ 
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;


public class Conveyers {

private int conveyorNum;
	
	//lock to control synchronization with the conveyer
	public ReentrantLock accessLock = new ReentrantLock();

	private boolean busy = false; //whether conveyer is occupied
	
	
	public Conveyers(int conveyerNum)
	{
		
		this.conveyorNum = conveyerNum;
		
	}
	
	
	public void inputConnection(int stationNum) 
	{	
		
		System.out.println("\tRouting Station " + stationNum + ": successfully moves packages on conveyer C" + this.conveyorNum);

		
	}
	
	
	public void outputConnection(int stationNum) 
	{	
		
		System.out.println("\tRouting Station " + stationNum + ": successfully moves packages on conveyer C" + this.conveyorNum);
		
	}
	
}