package br.com.erudio.integrationtests.vo.wrappers;

import br.com.erudio.integrationtests.vo.PersonVO;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class PersonEmbeddedVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("personVOList")
    private List<PersonVO> persons;

    public PersonEmbeddedVO() {}

    public List<PersonVO> getPersons() {
        return persons;
    }

    public void setPersons(List<PersonVO> persons) {
        this.persons = persons;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((persons == null) ? 0 : persons.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PersonEmbeddedVO other = (PersonEmbeddedVO) obj;
        if (persons == null) {
            if (other.persons != null)
                return false;
        } else if (!persons.equals(other.persons))
            return false;
        return true;
    }
}
