package xmlmodels;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlValue;

@XmlRootElement
public class Salary {
    @XmlAttribute
    public String currency;
    @XmlValue
    public int value;
}
