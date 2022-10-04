/* 
  Name: Daxon Dennis 
  Course: CNT 4714 Fall 2022 
  Assignment title: Project 2 – Multi-threaded programming in Java 
  Date:  October 9, 2022 
 
  Class:  Enterprise Computing 
*/ 
import java.util.*;
import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.Scanner;

public class Driver 
{
	static String fileName = "src/config.txt";
	static int NumberofStations;
	static int Max;
	static int[] workloads; //each index represents a station
	static Conveyers[] conveyers;
	static Station[] stations;
	public static void main(String[] args) throws FileNotFoundException 
	{
		//read in file using scanner
		File file = new File(fileName);
		Scanner read = new Scanner(file);

		//place workloads in array
		NumberofStations = read.nextInt();
		workloads = new int[NumberofStations];
		
		//create conveyers
		conveyers = new Conveyers[NumberofStations];
		
		//create instances of conveyers
		for(int i = 0; i < conveyers.length; i++)
			//create instance of conveyer
			conveyers[i] = new Conveyers(i);
			 
		//create stations
		stations = new Station[NumberofStations];
		
		//create a thread pool with the size being number of stations
		ExecutorService Simulation = Executors.newFixedThreadPool(NumberofStations);
		
		System.out.println("* * * * * * * * * PACKAGE MANAGEMENT FACILITY SIMULATION BEGINS * * * * * * * * * \n\n ");
		System.out.println("\tThe parameters for this simulation are: \n");
		System.out.println("\tRouting Station 0 Has Total Workload of 2 Package Groups,");
		System.out.println("\tRouting Station 1 Has Total Workload of 3 Package Groups,");
		System.out.println("\tRouting Station 2 Has Total Workload of 4 Package Groups,\n\n");
		
		for(int i = 0; i < workloads.length && i < conveyers.length && i < stations.length; i++) 
		{
			//Scanner scans next line and sets that integer to workloads
			workloads[i] = read.nextInt();
			//create instance of stations
			stations[i] = new Station(workloads[i], i, NumberofStations);
			//set input and output conveyers
			stations[i].setInput(conveyers[stations[i].getInputConNum()]);
			stations[i].setOutput(conveyers[stations[i].getOutputConNum()]);
			
			//start up the stations
			try
			{
				Simulation.execute(stations[i]);
			}
			catch (Exception exc)
			{
				exc.printStackTrace();
			}

		}
				
		
		Simulation.shutdown();
		//System.out.println("- - - - - - SIMULATION TERMINATED - - - - - - ");
		
	}

}