package nanocovax;

public class Address {
    CityProvince cityProvince;
    District district;
    Ward ward;

    Address() {
        cityProvince = new CityProvince();
        district = new District();
        ward = new Ward();
    }

    public CityProvince getCityProvince() {
        return cityProvince;
    }
    public District getDistrict() {
        return district;
    }
    public Ward getWard() {
        return ward;
    }

    public void setCityProvince(CityProvince src) {
        cityProvince = src;
    }
    public void setDistrict(District src) {
        district = src;
    }
    public void setWard(Ward src) {
        ward = src;
    }

    public static void main(String[] args) {

    }
}