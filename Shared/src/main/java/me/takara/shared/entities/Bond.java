package me.takara.shared.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.takara.shared.Entity;
import me.takara.shared.Instrument;

//import javax.xml.bind.annotation.XmlRootElement;

//@XmlRootElement(name = "bond")
public interface Bond extends Instrument {

    default Entity getType() { return Entity.BOND; }

}
