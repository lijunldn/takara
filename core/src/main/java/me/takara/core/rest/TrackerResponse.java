package me.takara.core.rest;

import lombok.Data;
import me.takara.core.SyncStamp;
import me.takara.core.Instrument;

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
