package sample.Helpers;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.Vm;
import org.omg.CORBA.INTERNAL;
import sample.HomeController;

import java.util.*;

public class VMHelper {

    List<Vm> vmList;
    List<Cloudlet> cloudletList;
    public VMHelper(){
        vmList = new ArrayList<>();
        cloudletList = new ArrayList<>();
        cloudletList.addAll(HomeController.cloudletList);
        vmList.addAll(HomeController.vmlist);
    }

    public Map<String, Integer> getProcessingCapacityOfEachVm() {

        Map<String, Integer> map = new HashMap<>();

        for (Vm vm: vmList) {
            int processing = (int)(vm.getNumberOfPes() * vm.getMips() * Math.pow(10, 6));
            String key = "VM "+ vm.getId();
            map.put(key, processing);
            System.out.println(key + ": " + processing);

        }
        return map;

    }
    public int getBestVmID(Map<String,Integer> vmLoadMap) {
        /*Best Processor have maximum number of processor*/
        List<VmDetails> vmDetails = getVmDetails(vmLoadMap);
        String key =vmDetails.get(0).vmId;
        long min =vmDetails.get(0).difference;
        for (VmDetails vm: vmDetails) {
            if (vm.difference < min) {
                min = vm.difference;
                key = vm.vmId;
            }
        }
        String stripVM = key.substring(3);
        int id = Integer.valueOf(stripVM);
        System.out.println("Bset Vms id: " + id);
        return id;
    }
    public double getLoadPerCapacity() {
        /*Calculate total capacity of vm*/
        long totalLoad = getTotalCloudLetLoad();
        long totalVmCapactiy = 0;
        Map<String, Integer> map = getProcessingCapacityOfEachVm();
        for (Integer i: map.values()) {
            //System.out.println("Inside egt Load Per Capacity: "+totalVmCapactiy);
            totalVmCapactiy+=i;
        }
        System.out.println("Total Cloudlet load: " + totalLoad);
        System.out.println("Toatl Vm capacity: "+ totalVmCapactiy);
        double loadPerCapacity =(double) totalLoad/totalVmCapactiy;

        System.out.println("Load Per capacity: "+String.valueOf(loadPerCapacity));

        return loadPerCapacity;
    }

    public int getNumberOfVmOverloaded(Map<String,Integer> vmLoadMap) {
        int count = 0;
        List<VmDetails> vmDetails = getVmDetails(vmLoadMap);
        for (VmDetails vm: vmDetails) {
            if (vm.status.equals("Over Loaded"))count++;
        }
        System.out.println("Overloaded Vms: " + count);
        return count;
    }

    public List<Integer> getOverUnderBalancedVmCount(Map<String,Integer> vmLoadMap) {
        List<Integer> result = new ArrayList<>();
        int over=0, under=0, balanced=0;
        List<VmDetails> vmDetails = getVmDetails(vmLoadMap);
        for (VmDetails vm: vmDetails) {
            if (vm.status.equals("Over Loaded"))over++;
            else if(vm.status.equals("Under Loaded"))under++;
            else balanced++;
        }
        System.out.println("Overloaded Vms: " + over);
        System.out.println("UnderLoaded vm: "+ under);
        System.out.println("Balanced vm: " + balanced);
        result.add(over);
        result.add(under);
        result.add(balanced);
        return result;
    }
    public List<String> getOverUnderBalancedStringList() {
        List<String> result = new ArrayList<>();
        result.add("OverLoaded");
        result.add("UnderLoaded");
        result.add("Balanced");
        return result;
    }


    public long getTotalCloudLetLoad() {
        long totalLoad = 0;
        for(Cloudlet cloudlet: cloudletList){
            totalLoad+=cloudlet.getCloudletLength();
        }
        System.out.println("Total Load : "+totalLoad);
        return totalLoad;
    }

    public Map<String, Long> getThresholdValue() {
        Map<String, Integer> map = getProcessingCapacityOfEachVm();
        double value = 0;
        double loadPerCapacity = getLoadPerCapacity();
        Map<String, Long> thresholdMap = new HashMap<>();
        /*if vm is used 70% above capacity then it is overloaded*/
        for (Map.Entry<String, Integer> i: map.entrySet()) {
            //value = i.getValue() * 0.7;
            value =(double) i.getValue() * loadPerCapacity;

            long l =(long) value;
            thresholdMap.put(i.getKey(), l);

        }
        System.out.println("Threshold Map: "+thresholdMap);
        return thresholdMap;
    }


    public List<VmDetails> getVmDetails(Map<String,Integer> vmLoadMap) {
        List<VmDetails> result = new ArrayList<>();
        Map<String, Long> vmThresholdValueMap = getThresholdValue();
        Map<String, Integer> vmCapacityMap = getProcessingCapacityOfEachVm();
        int count = 0;

        /*Join vm*/
        for (Map.Entry<String, Integer> capacityMap: vmCapacityMap.entrySet()) {
            String vmKey = capacityMap.getKey();
            int vmCapacity = capacityMap.getValue();
            int vmLoad = vmLoadMap.get(capacityMap.getKey());
            long thv = vmThresholdValueMap.get(capacityMap.getKey());

            String status = "Balanced";
            if (vmLoad > thv)status="Over Loaded";
            if (vmLoad < thv)status="Under Loaded";
            VmDetails vmDetails = new VmDetails(vmKey,vmCapacity, vmLoad, thv, status);
            System.out.println("Vm Details: " + vmKey + " : " + vmDetails.toString());
            result.add(vmDetails);

        }
        return result;

    }

}
