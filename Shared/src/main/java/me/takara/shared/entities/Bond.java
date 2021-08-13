package me.takara.shared.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import me.takara.shared.Entity;
import me.takara.shared.Instrument;

//import javax.xml.bind.annotation.XmlRootElement;

//@XmlRootElement(name = "bond")
@Data
public class Bond extends InstrumentBase {

    @Override
    public Entity getType() { return Entity.BOND; }

}
