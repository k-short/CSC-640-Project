import java.util.ArrayList;

/**
 * Created by ken12_000 on 10/10/2016.
 */
public class EventScheduleManagement {
    ArrayList<TeamEvent> eventList;
    EventAccess eventAccess;

    /**
     * Constructor that creates the EventAccess object and retrieves event list from it.
     */
    public EventScheduleManagement(){
        eventAccess = new EventAccess();

        //Create default event list
        eventList = new ArrayList<>();
        TeamEvent dummy1 = new TeamEvent("Daytona 500", "Daytona Speedway", "Florida", "6/24/16", "5:00", "Cool race.");
        TeamEvent dummy2 = new TeamEvent("Daytona 500", "Daytona Speedway", "Florida", "6/24/16", "5:00", "Cool race.");
        TeamEvent dummy3 = new TeamEvent("Daytona 500", "Daytona Speedway", "Florida", "6/24/16", "5:00", "Cool race.");
        TeamEvent dummy4 = new TeamEvent("Daytona 500", "Daytona Speedway", "Florida", "6/24/16", "5:00", "Cool race.");
        TeamEvent dummy5 = new TeamEvent("Daytona 500", "Daytona Speedway", "Florida", "6/24/16", "5:00", "Cool race.");
        TeamEvent dummy6 = new TeamEvent("Daytona 500", "Daytona Speedway", "Florida", "6/24/16", "5:00", "Cool race.");
        TeamEvent dummy7 = new TeamEvent("Daytona 500", "Daytona Speedway", "Florida", "6/24/16", "5:00", "Cool race.");
        TeamEvent dummy8 = new TeamEvent("Daytona 500", "Daytona Speedway", "Florida", "6/24/16", "5:00", "Cool race.");
        TeamEvent dummy9 = new TeamEvent("Daytona 500", "Daytona Speedway", "Florida", "6/24/16", "5:00", "Cool race.");
        TeamEvent dummy10 = new TeamEvent("Daytona 500", "Daytona Speedway", "Florida", "6/24/16", "5:00", "Cool race.");
        //eventList.add(dummy1);eventList.add(dummy2);eventList.add(dummy3);eventList.add(dummy4);eventList.add(dummy5);
        //eventList.add(dummy6);eventList.add(dummy7);eventList.add(dummy8);eventList.add(dummy9);eventList.add(dummy10);
        //eventAccess.saveEvents(eventList);

        eventList = eventAccess.getEvents();
    }

    /**
     * Return the list of events after sorting them in reverse-chronological order.
     * @return ArrayList of all events.
     */
    public ArrayList<TeamEvent> getEventList(){
       // sortEventList();
        return eventList;
    }


    /**
     * Sort the events in the event list by reverse-chronological order.
     */
    private void sortEventList(){
        //eventList.sort();
    }

    /**
     * Add a new event to the event array.
     */
    public void addEvent(TeamEvent event){
        eventList.add(event);

        //Rewrites the event list array to the file
        eventAccess.saveEvents(eventList);
    }

    /**
     * Update the event list
     */
    public void updateEventList(ArrayList<TeamEvent> list){
        eventAccess.saveEvents(list);
        eventList = list;
        sortEventList();
    }
}
