import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Guney_Sogut_2020510066 {

    // read the given file and assign the elements into an array
    public static int[]  readFile(String fileName) throws IOException {

        FileReader fr = new FileReader(fileName);
        BufferedReader br = new BufferedReader(fr);

        int counter = -1;
        while (br.readLine() != null) {
            counter++;
        }

        int[] array = new int[counter];


        fr = new FileReader(fileName);
        br = new BufferedReader(fr);

        br.readLine();//skip the header
        for (int i = 0; i < array.length; i++) {
            String line = br.readLine();
            array[i] = Integer.parseInt(line.substring(line.indexOf("\t") + 1));
        }

        return array;
    }


    public static void Greedy(int[] playerSalary, int[] yearlyPlayerDemand, int n, int p, int c) {
        int keptPlayers = 0;
        int totalCost = 0;

        for(int i = 0; i < n; i++){ // iterate the years
            int demand = yearlyPlayerDemand[i]; // store the demand count for each year
            System.out.println("Year : " + (i+1));
            System.out.println("Demand : " +demand);

            if(i == n-1) // in order to avoid array out of bounds (exit the loop without processing the last year)
                break;

            int demandNextYear = yearlyPlayerDemand[i + 1]; // store the next years demand in order to find the best

            if(demand > p){ // if the demand is greater than the max players to be promoted free
                // store the required players
                int requiredPlayer = demand - p - keptPlayers;

                if(demandNextYear <= p){ // if the next years demand is less than or equal to the max free promote count
                    totalCost += requiredPlayer * c; // just hire coach for requirements
                    keptPlayers = 0; // the cheapest cost is 0 if the demand is less than the max players to be promoted
                }
                else{ // if the next years demand is greater than the max free promote count

                    int minCost = Integer.MAX_VALUE; // stores the minimum cost for the year
                    int cost; // store the costs of each case
                    for(int j = 0; j <= (demandNextYear-p); j++){ // iterate the loop by the next years maximum requirements
                        int salary;
                        if(j == 0) // 0 player salary is 0
                            salary = 0;
                        else
                            salary = playerSalary[j-1];

                        int coachNextYear = demandNextYear - p - j; // coach count for the next year

                        cost = (requiredPlayer * c) + (coachNextYear * c) + salary; // calculate the cost

                        // try to find the minimum cost for each case
                        if(cost>=0)
                            minCost = Integer.min(minCost,cost);
                        if (minCost == cost) // if minimum value is changed we should change the kep players count
                            keptPlayers = j;

                        requiredPlayer++; // increase the required players count to find the best case
                    }
                    totalCost += minCost - ((demandNextYear - p - keptPlayers)*c); // add the pure minimum cost(only for the current years cost) to the total cost
                }
            }

            else{ // if the demand of the current year is less than or equal to p

                if(demandNextYear <= p){  // if the next years demand is less than or equal to the max free promote count
                    totalCost += 0; // nothing is changed because the demand is fewer than the max players to be promoted free
                    keptPlayers = 0; // the cheapest cost is 0 if the demand is less than the max players to be promoted free
                }
                else{// if the next years demand is greater than the max free promote count
                    // free from 0 to demand
                    int minCost = Integer.MAX_VALUE; // stores the minimum cost for the year
                    if(demand < p) {
                        int cost;
                        for (int j = 1; j <= p-demand; j++) {

                            cost = playerSalary[j - 1] + (demandNextYear - p - j + 1)*c; // calculate the cost

                            if(j <= (demandNextYear - p)) { // in order to avoid keep unnecessary player for next years requirement
                                minCost = Integer.min(minCost, cost); // find the minimum cost
                                if (minCost == cost) // if minimum value is changed we should change the kep players count
                                    keptPlayers = j;
                            }
                        }
                        totalCost += minCost - (demandNextYear - p - keptPlayers + 1)*c; // add the pure minimum cost(only for the current years cost) to the total cost
                    }
                    else{ // if the demand is greater than the max free promote count
                        keptPlayers = 0;// the cheapest cost is 0 if the demand is less than the max players to be promoted free
                        totalCost += 0; // nothing is changed because the demand is fewer than the max players to be promoted free
                    }
                }
            }
            System.out.println("Kept Players : " + keptPlayers);
            System.out.println("Cost : " + totalCost);
            System.out.println("-----------------------");
            System.out.println();
        }


        // last year calculations

        int demand = yearlyPlayerDemand[n-1];
        int coach = demand - p - keptPlayers; // calculate coach count
        int cost = coach*c;
        totalCost += cost; // add the cost to the total cost
        keptPlayers = 0;
        System.out.println("Kept Players : " + keptPlayers);
        System.out.println("Cost : " + totalCost);
        System.out.println("-----------------------");
        System.out.println();
        System.out.println("Total Cost : " + totalCost);

    }


    // This is the really really basic approach
    // it checks only the best cost for the current year (I want to add this approach also)
    public static void Greedy2(int[] playerSalary, int[] yearlyPlayerDemand, int n, int p, int c) {
        int totalCost = 0; // total cost variable
        int keptPlayers = 0; // if any players kept in a year for the next years


        for(int i = 0; i < n; i++){ // iterate the years
            int demand = yearlyPlayerDemand[i]; // store the demand

            System.out.println("Year : " + (i+1));
            System.out.println("Demand : " +demand);

            int cost; // cost for each year's each scenario
            int minCost = Integer.MAX_VALUE; // minimum cost for the year
            if(demand > p) {
                int requiredPlayer = demand - p - keptPlayers;
                for(int j = 0; j <= requiredPlayer; j++) {
                    int salary;
                    if(j == 0) // 0 player salary is 0
                        salary = 0;
                    else
                        salary = playerSalary[j-1];

                    cost = requiredPlayer * c + salary; // calculate the cost according to the document
                    minCost = Integer.min(minCost,cost); // try to find the minimum
                    if (minCost == cost) // if minimum value is changed we should change the kep players count
                        keptPlayers = j;
                }
                totalCost += minCost; // add the minimum cost to the total cost
            }
            else { // if demand is less than the max players to be promoted
                totalCost += 0; // nothing is changed
                keptPlayers = 0; // there is no kept in this case because we need to pay salary for them(it is not the cheapest solution for the year)
            }
            System.out.println("Kept Players : " + keptPlayers);
            System.out.println("Cost : " + totalCost);
            System.out.println("-----------------------");
            System.out.println();
        }
        System.out.println("Total Cost : " + totalCost);
    }




    public static void main(String[] args) throws IOException {
        int n = 3, p = 5, c = 5; // initial values

        //create the demand and salary arrays
        int[] playerSalary = readFile("players_salary.txt");
        int[] yearlyPlayerDemand = readFile("yearly_player_demand.txt");


        // call the function
        Greedy(playerSalary,yearlyPlayerDemand,n,p,c);

    } // end main
}