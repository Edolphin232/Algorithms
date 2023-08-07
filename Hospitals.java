import java.io.*;
import java.util.*;

class Hospitals {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

//        File file = new File("C:\\Users\\edwar\\Documents\\Algorithms\\HW 1\\Hospitals\\src\\test5.txt");
//        BufferedReader br = new BufferedReader(new FileReader(file));

        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int m = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());

        int[] capacities = new int[m+1];
        int[][] hospitalPrefs = new int[m+1][n+1];
        int[][] studentPrefs = new int[n+1][m+1];
        for(int i = 1; i <= m; i++){
            capacities[i] = Integer.parseInt(br.readLine());
        }
        for(int i = 1; i <= m; i++){
            st = new StringTokenizer(br.readLine());
            for(int j = 1 ; j <= n; j++){
                hospitalPrefs[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        int[][] studentPrefMem = new int[n+1][m+1];
        for(int i = 1; i <= n; i++){
            st = new StringTokenizer(br.readLine());
            for(int j = 1 ; j <= m; j++){
                studentPrefs[i][j] = Integer.parseInt(st.nextToken());
                studentPrefMem[i][studentPrefs[i][j]] = j;
            }
        }

        int[] finalStudentHospitals = new int[n+1];
//        HashSet<Integer>[] tempHospitalFillings = new HashSet[m+1];
//        for(int i = 1; i <= m; i++){
//            tempHospitalFillings[i] = new HashSet<>();
//        }

        ArrayDeque<Integer> hospitals = new ArrayDeque<>();
        for(int i = 1; i <= m; i++){
            hospitals.add(i);
        }
        int matches = 0;
        int[] curHospPref = new int[m+1];
        while(matches < n && hospitals.size() > 0){
            int hospital = hospitals.poll();
            curHospPref[hospital]++;
            int student = hospitalPrefs[hospital][curHospPref[hospital]];
            if(capacities[hospital] == 0) continue;
            if(finalStudentHospitals[student] != 0){
                int prevHospital = finalStudentHospitals[student];
                if(studentPrefMem[student][hospital] < studentPrefMem[student][prevHospital]){
                    finalStudentHospitals[student] = hospital;
//                    tempHospitalFillings[prevHospital].remove(student);
//                    tempHospitalFillings[hospital].add(student);
                    capacities[hospital]--;
                    capacities[prevHospital]++;
                    if(capacities[prevHospital] == 1) hospitals.add(prevHospital);
                    if(capacities[hospital] > 0) hospitals.add(hospital);
                }
                else{
                    hospitals.add(hospital);
                }
            }
            else{
                finalStudentHospitals[student] = hospital;
//                tempHospitalFillings[hospital].add(student);
                capacities[hospital]--;
                if(capacities[hospital] > 0) hospitals.add(hospital);
                matches++;
            }
        }
        for(int i = 1; i < finalStudentHospitals.length; i++){
            out.println(finalStudentHospitals[i]);
        }
        out.close();
    }
}

