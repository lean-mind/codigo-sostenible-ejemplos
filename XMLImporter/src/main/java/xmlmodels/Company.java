package xmlmodels;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class Company {

    public Integer id;
    @XmlAttribute
    public String name;

    @XmlElement
    public List<Staff> staff = new ArrayList<>();
}
