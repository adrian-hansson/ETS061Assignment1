import java.util.*;
import java.io.*;

class State {
	//Q1
	public int numberInQueue = 0, accumulated = 0, noMeasurements = 0;
	public int maxQ1 = 10;
	public int Q1_Interarrivaltime = 1;
	
	//Q2
	public int numberInQueue2 = 0, accumulated2 = 0;

	Random slump = new Random();
	SimpleFileWriter W = new SimpleFileWriter("number.m", false);

	public void TreatEvent(Event x) {
		switch (x.eventType) {
			case G.ARRIVAL: {
				if(numberInQueue < 10){ //to prevent queue from exceeding its limit
					numberInQueue++;
					EventList.InsertEvent(G.ARRIVAL, G.time + Q1_Interarrivaltime);
					if (numberInQueue == 1) {
						EventList.InsertEvent(G.READY, G.time - (1 / 2.1) * Math.log(slump.nextDouble()));
					}
				}
			}
				break;
			case G.READY: {
				numberInQueue--;
				if (numberInQueue > 0) {
					EventList.InsertEvent(G.READY, G.time - (1 / 2.1) * Math.log(slump.nextDouble()));
				}
				//Insert ARRIVAL into Q2
				EventList.InsertEvent(G.ARRIVAL_2, G.time);
			}
				break;
			case G.MEASURE: {
				accumulated = accumulated + numberInQueue;
				accumulated2 = accumulated2 + numberInQueue2;
				noMeasurements++;
				EventList.InsertEvent(G.MEASURE, G.time + 5);
				W.println(String.valueOf(numberInQueue));
			}
				break;
			case G.ARRIVAL_2: {
				numberInQueue2++;
				if(numberInQueue2 == 1){
					EventList.InsertEvent(G.READY_2, G.time + 2);
				}
			}
				break;
			case G.READY_2: {
				numberInQueue2--;
				if (numberInQueue > 0) {
					EventList.InsertEvent(G.READY, G.time + 2);
				}
			}
				break;
		}
		
	}
}