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
