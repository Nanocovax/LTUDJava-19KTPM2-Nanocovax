package nanocovax;

public class User {
    String id, name, doB, status;
    Hospital hospital;
    Address address;

    User() {
        address = new Address();
        hospital = new Hospital();
    }
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getDoB() {
        return doB;
    }
    public String getStatus() {
        return status;
    }
    public Hospital getHospital() {
        return hospital;
    }
    public Address getAddress() {
        return address;
    }

    public void setId(String srcId) {
        id = srcId;
    }
    public void setName(String srcName) {
        name = srcName;
    }
    public void setDoB(String srcDoB) {
        doB = srcDoB;
    }
    public void setStatus(String srcStatus) {
        status = srcStatus;
    }
    public void setHospital(Hospital srcHospital) {
        hospital = srcHospital;
    }
    public void setAddress(Address srcAddress) {
        address = srcAddress;
    }
}