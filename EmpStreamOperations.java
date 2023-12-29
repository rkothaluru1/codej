package org.example;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EmpStreamOperations {
    record Emp(int id, String name, int age, String dept, Double sal, String gender) {
    }

    public static void main(String[] args) {
        List<Emp> empList = List.of(
                new Emp(1, "Raj", 30, "Dev", 10.0, "M"),
                new Emp(2, "Vijay", 27, "Test", 12.0, "M"),
                new Emp(3, "Suji", 66, "Dev", 16.0, "F"),
                new Emp(4, "Kumari", 45, "Test", 13.0, "F"),
                new Emp(5, "Naresh", 58, "Test", 12.0, "M"),
                new Emp(6, "Satwi", 26, "Dev", 18.0, "F"),
                new Emp(7, "Rajesh", 47, "Test", 13.0, "M"),
                new Emp(8, "Mahesh", 35, "Dev", 19.0, "M"),
                new Emp(9, "Alia", 32, "Dev", 12.0, "F"),
                new Emp(10, "Salman", 66, "Test", 20.0, "M"),
                new Emp(1, "Rajasekhar", 30, "Dev", 10.0, "M")
        );
        Emp defaultEmp = new Emp(0, null, 0, null, null, null);
        // Q1) Dept vise average salary
        Map<String, Double> deptAvgSal = empList.stream().collect(Collectors.groupingBy(Emp::dept, Collectors.averagingDouble(Emp::sal)));
        //System.out.println(deptAvgSal);

        // Q2) Dept vise average salary for Male/Female
        Map<String, Map<String, Double>> deptMFAvgSal
                = empList.stream().collect(Collectors.groupingBy(Emp::dept, Collectors.groupingBy(Emp::gender, Collectors.averagingDouble(Emp::sal))));
        //System.out.println(deptMFAvgSal);

        // Q3) Find dept vise highest Salaried person age.
        Map<String, Integer> deptMaxSalriedAge = empList.stream().collect(Collectors.groupingBy(Emp::dept, Collectors.maxBy(Comparator.comparingDouble(Emp::sal))))
                .entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().orElse(defaultEmp).age));
        //System.out.println(deptMaxSalriedAge);

        // Q4) Find top2 salaried persion Names
        List<String> top2SalariedNames = empList.stream().sorted((a, b) -> b.sal().compareTo(a.sal())).limit(2).map(Emp::name).toList();
        //System.out.println(top2SalariedNames);

        // Q5) Find names having above avg sal into map
        double avgSal = empList.stream().mapToDouble(Emp::sal).average().orElse(0);
        Map<String, Double> aboveAvgSalEmps = empList.stream().filter(emp -> empList.stream().mapToDouble(Emp::sal).average().orElse(0) <= emp.sal()).collect(Collectors.toMap(Emp::name, Emp::sal));
        //System.out.println("Avg sal: "+avgSal);
        //System.out.println(aboveAvgSalEmps);

        // Q6) Find dept vise Employee names
        Map<String, List<String>> deptEmpNames = empList.stream().collect(Collectors.groupingBy(Emp::dept, Collectors.mapping(Emp::name, Collectors.toList())));
        //System.out.println(deptEmpNames);

        // Q7) Convert empList to empMap in reverse order
        Map<Integer, Emp> empMap = empList.stream().collect(Collectors.toMap(Emp::id, Function.identity(), (oVal, nVal) -> nVal, () -> new TreeMap<>(Comparator.reverseOrder())));
        //System.out.println(empMap);

        // Q8) Convert dept vise EmpNames Sorted.
        Map<String, Set<String>> sortedDeptEmp = empList.stream().collect(Collectors.groupingBy(Emp::dept, TreeMap::new, Collectors.mapping(Emp::name, Collectors.toCollection(TreeSet::new))));
        //System.out.println(sortedDeptEmp);

        // Q9) Example of flatmapping()
        //Map<String, Set<LineItem>> itemsByCustomerName = orders.stream().collect(groupingBy(Order::getCustomerName, flatMapping(order -> order.getLineItems().stream(), toSet())));

        // Q10) Dept vise Emp names
        Map<String, String> deptEmpNameMap = empList.stream().collect(Collectors.groupingBy(Emp::dept, Collectors.mapping(Emp::name, Collectors.joining(",", "[", "]"))));
        System.out.println(deptEmpNameMap);

    }
}
