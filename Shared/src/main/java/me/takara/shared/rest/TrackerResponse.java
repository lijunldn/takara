package me.takara.shared.rest;

import lombok.Data;
import me.takara.shared.Instrument;
import me.takara.shared.SyncStamp;

import java.util.List;

@Data
public class TrackerResponse {

    /**
     * SyncStamp of the last instrument in the collection
     */
    private SyncStamp stamp;
    /**
     * Collection returned by the REST
     */
    private List<Instrument> instruments;
}
