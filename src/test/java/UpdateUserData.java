public class UpdateUserData {
   private String name;
   private String job;
   private String updatedAt;

    public UpdateUserData(String name, String job, String updatedAt) {
        this.name = name;
        this.job = job;
        this.updatedAt = updatedAt;
    }
    public UpdateUserData(){}

    public String getName() {
        return name;
    }

    public String getJob() {
        return job;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }
}
