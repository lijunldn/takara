package me.takara.gemis.operation;

import me.takara.shared.Instrument;
import me.takara.shared.SyncStamp;

import java.util.HashMap;

public interface Strategy {

    SyncStamp execute(HashMap<SyncStamp, Instrument> data, Instrument item);

}
