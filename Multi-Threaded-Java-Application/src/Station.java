/* 
  Name: Daxon Dennis 
  Course: CNT 4714 Fall 2022 
  Assignment title: Project 2 – Multi-threaded programming in Java 
  Date:  October 9, 2022 
 
  Class:  Enterprise Computing 
*/ 
import java.util.Random;

public class Station implements Runnable 
{
	private static int maxNumStations;
	private int workload = 0;
	private int inputConveyerNum;
	private int outputConveyerNum;
	private int stationNum;
	private Conveyers input;
	private Conveyers  output;
	private static Random Rand = new Random();
	
	
	//constructor
	public Station(int workload, int stationNum, int maxNumStations)
	{
		this.workload = workload;
		this.stationNum = stationNum;
		this.maxNumStations = maxNumStations;
		//System.out.println("\n# # # # # # # # Routing Station "+ stationNum + " Coming Online - Initializing Conveyers # # # # # # # #\n");
		this.setInputConNum();
		this.setOutputConNum();
		
		System.out.println("\tRouting Station " + stationNum + ": Workload set. Station " + this.stationNum + " has " + this.workload + " package groups to move\n\n");
	}
	
	
	@Override
	public void run() { 
		
		while(this.workload != 0)
		{
			//gets input lock
			if(input.accessLock.tryLock())
			{
				System.out.println("\tRouting Station " + this.stationNum + ": holds input lock to conveyer C" + this.inputConveyerNum);
				
				//gets output lock
				if(output.accessLock.tryLock())
				{
					System.out.println("\tRouting Station " + this.stationNum + ": holds output lock to conveyer C" + this.outputConveyerNum);
					doWork(); //Once both locks are obtained do work is called
					
				}
				
				if(input.accessLock.isHeldByCurrentThread() && output.accessLock.isHeldByCurrentThread()) {
					System.out.println("\n* * * * * * * * Routing Station " + this.stationNum + " holds access to both locks * * * * * * * * \n");
				}
				else
				{	
					//release input lock because we did not get output lock
					System.out.println("\tStation " + this.stationNum + ": unable to lock conveyer C" + this.inputConveyerNum);
					input.accessLock.unlock();
					
					Sleep();
					
				}
				
				//release locks
				if(input.accessLock.isHeldByCurrentThread())
				{
					
					System.out.println("\tStation " + this.stationNum + ": released input lock to conveyer C" + this.inputConveyerNum);
					input.accessLock.unlock();

				}
				
				if(output.accessLock.isHeldByCurrentThread())
				{
					
					System.out.println("\tStation " + this.stationNum + ": released output lock to conveyer C" + this.outputConveyerNum);
					output.accessLock.unlock();
					
				}
				
					Sleep();
				
			}
		}
		
		//done with all workloads
		System.out.println(); 
		System.out.println("\n= = = = = = = =  Routing Station " + this.stationNum + ": Workload successfully completed - Routing Station " + this.stationNum+ " Going Offline = = = = = = = =\n");
		System.out.println();
		
	}
	
	public void doWork() 
	{	
	
				this.input.inputConnection(this.stationNum);
				this.output.outputConnection(this.stationNum);
				this.workload--; //simulates working being completed
				System.out.println("\n+ + + + + + + + Routing Station " + this.stationNum +": CURRENTLY HARD AT WORK MOVING PACKAGES + + + + + + + +\n");
				Sleep(); //Simulates the time it would take for work to be done
				System.out.println("\tRouting Station " + this.stationNum + ": has " + this.workload + " package groups left to move");
				
	}
	
	public void Sleep()
	{
		
		try {
			Thread.sleep(Rand.nextInt(500));
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		
	}
	
	
	public Conveyers getInput() 
	{
		
		return input;
		
	}

	public void setInput(Conveyers input)
	{
		
		this.input = input;
		
	}

	public Conveyers getOutput()
	{
		
		return output;
		
	}

	public void setOutput(Conveyers output) 
	{
		
		this.output = output;
		
	}
	
	public int getInputConNum() 
	{
		
		return inputConveyerNum;
		
	}

	public void setInputConNum()
	{
		
		if(stationNum == 0)
		{
			
			this.inputConveyerNum = 0;
			
		}
		else
		{
			
			this.inputConveyerNum = this.stationNum - 1;
			
		}
		
		System.out.println("\tRouting Station " + stationNum + ": Input conveyor set to conveyer C" + this.inputConveyerNum);

	}

	public int getOutputConNum() 
	{
		
		return outputConveyerNum;
		
	}

	public void setOutputConNum() 
	{
		
		if(this.stationNum == 0)
		{
			
			this.outputConveyerNum = this.maxNumStations - 1; //chooses closest conveyer n = n - 1
			
		}
		else
		{
			
			this.outputConveyerNum = this.stationNum;
			
		}
		
		System.out.println("\tRouting Station " + stationNum + ": Output conveyer set to conveyer C" + this.outputConveyerNum);

	}
	
}
