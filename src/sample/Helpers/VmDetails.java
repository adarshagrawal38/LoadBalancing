package sample.Helpers;

import java.util.ArrayList;
import java.util.List;

public class VmDetails {

    String vmId;
    long vmCapacity, vmLoad, vmThreshold, difference;
    String status;




    public VmDetails(String vmId, long vmCapacity, long vmLoad, long vmThreshold, String status) {
        this.vmId = vmId;
        this.vmCapacity = vmCapacity;
        this.vmLoad = vmLoad;
        this.vmThreshold = vmThreshold;
        this.difference = vmLoad  - vmThreshold;
        this.status = status;
    }

    public List<String> getDetailsInListFormat() {
        List<String> result = new ArrayList<>();
        result.add(vmId);
        result.add(String.valueOf(vmCapacity));
        result.add(String.valueOf(vmLoad));
        result.add(String.valueOf(vmThreshold));
        result.add(String.valueOf(difference));
        result.add(status);
        return result;

    }

    public static List<String> getColumnNames() {
        List<String> result = new ArrayList<>();
        result.add("VM_Id");
        result.add("VM_Capacity");
        result.add("VM_Load");
        result.add("VM_Threshold");
        result.add("Diffrence");
        result.add("Status");
        return result;

    }

    @Override
    public String toString() {
        return vmId+" Capacity: "+vmCapacity+" Vm load: " + vmLoad + " " + difference + " " + status;
    }
}
