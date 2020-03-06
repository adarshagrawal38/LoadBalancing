package sample;

import sample.Helpers.VmDetails;

import javax.swing.event.ListDataEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Techniques {

    String techName;
    Map<String, Integer> cloudLetMapping;
    List<List<String>> cloudLogs;
    List<String> cloudHeaders;
    Map<String, Integer> vmLoadMap;
    public List<VmDetails> vmDetails;

    List<Integer> overUnderBalancedValue;
    List<String> overUnderBalanceString;

    public Techniques(String techName, Map<String, Integer> cloudLetMapping) {
        this.techName = techName;
        this.cloudLetMapping = cloudLetMapping;
    }

    public Map<String, Integer> getVmLoadMap() {
        return vmLoadMap;
    }

    public void setVmLoadMap(Map<String, Integer> vmLoadMap) {
        this.vmLoadMap = vmLoadMap;
    }

    public List<String> getCloudHeaders() {
        return cloudHeaders;
    }

    public void setCloudHeaders(List<String> cloudHeaders) {
        this.cloudHeaders = cloudHeaders;
    }

    public List<List<String>> getCloudLogs() {
        return cloudLogs;
    }

    public void setCloudLogs(List<List<String>> cloudLogs) {
        this.cloudLogs = cloudLogs;
    }

    public String getTechName() {
        return techName;
    }

    public void setTechName(String techName) {
        this.techName = techName;
    }

    public Map<String, Integer> getCloudLetMapping() {
        return cloudLetMapping;
    }

    public void setCloudLetMapping(Map<String, Integer> cloudLetMapping) {
        this.cloudLetMapping = cloudLetMapping;
    }

    public List<VmDetails> getVmDetails() {
        return vmDetails;
    }

    public void setVmDetails(List<VmDetails> vmDetails) {
        this.vmDetails = vmDetails;
    }

    public List<Integer> getOverUnderBalancedValue() {
        return overUnderBalancedValue;
    }

    public List<String> getOverUnderBalanceString() {
        return overUnderBalanceString;
    }

    public void setOverUnderBalanceString(List<String> overUnderBalanceString) {
        this.overUnderBalanceString = overUnderBalanceString;
    }

    public void setOverUnderBalancedValue(List<Integer> overUnderBalancedValue) {
        this.overUnderBalancedValue = overUnderBalancedValue;
    }
}
