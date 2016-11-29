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

        if(eventAccess.getEvents() != null)
            eventList = eventAccess.getEvents();
        else
            eventList = new ArrayList<>();
    }

    /**
     * Return the list of events after sorting them in reverse-chronological order.
     * @return ArrayList of all events.
     */
    public ArrayList<TeamEvent> getEventList(){
        return eventList;
    }


    /**
     * Sort the events in the event list by reverse-chronological order.
     */
    private void sortEventList(){
        //insertion sort
        for(int i = 1; i < eventList.size(); i++){
            TeamEvent key = eventList.get(i);
            String keyDate = "" + key.getYear() + key.getMonth() + key.getDay();
            int k = i - 1;
            TeamEvent kEvent = eventList.get(k);
            String kDate =  "" + kEvent.getYear() + kEvent.getMonth() + kEvent.getDay();
            while(k >= 0 && kDate.compareToIgnoreCase(keyDate) > 0){
                eventList.set(k + 1, eventList.get(k));
                k--;
            }
            eventList.set(k + 1, key);
        }
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
