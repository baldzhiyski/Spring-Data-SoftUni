package softuni.exam.models.dto.wrapper;

import softuni.exam.models.dto.JobDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "jobs")
@XmlAccessorType(XmlAccessType.FIELD)
public class JobWrapper {

    @XmlElement(name = "job")
    private List<JobDto> jobs;

    public JobWrapper() {
    }

    public List<JobDto> getJobs() {
        return jobs;
    }

    public void setJobs(List<JobDto> jobs) {
        this.jobs = jobs;
    }
}
