package vendingMachine.challenge;

public class ServicePerson {
    private int accessKey = 123;
    private boolean accessed;

    public boolean verifyAccess(int accessKey) {
        accessed = false;
        if (accessKey == this.accessKey){
            accessed = true;
        }
        return accessed;
    }

}
